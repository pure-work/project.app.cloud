package com.gozi.core.base.util;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {
	private static RedisTemplate redisTemplate;
	@Resource
	public void setRedisTemplate(RedisTemplate template){ RedisUtil.redisTemplate = template; }
	/**
	 * 生成序列化key
	 * @param key
	 * @return
	 */
	private static byte[] rawKey(Object key) {
		// Assert.notNull(key, "non null key required");
		if (redisTemplate.getKeySerializer() == null && key instanceof byte[]) {
			return (byte[]) key;
		}
		return redisTemplate.getKeySerializer().serialize(key);
	}

	/**
	 * byte[]转long
	 * @return
	 */
	public static String bytes2string(byte[] bytes) {
		if (bytes != null)
			return new String(bytes);
		return null;
	}

	/**
	 * long 转byte[]
	 * 
	 * @param num
	 * @return
	 */
	public static byte[] string2bytes(Object num) {
		if (num == null)
			return null;
		return num.toString().getBytes();
	}

	public static void init() {
		// redisTemplate = (RedisTemplate) SpringUtils.getBean("redisTemplate");
	}

	public RedisTemplate getRedisTemplate() {
		return redisTemplate;
	}

	/**
	 * 设置指定key,value到redis
	 * 
	 * @param key
	 * @param value
	 */
	public static void set(String key, Object value) {
		set(key, value, 0, TimeUnit.SECONDS);
	}

	/**
	 * 设置指定key,value到redis
	 * 
	 * @param key
	 * @param value
	 * @param timeout
	 *            单位s
	 */
	public static void set(String key, Object value, long timeout) {
		set(key, value, timeout, TimeUnit.SECONDS);
	}

	/**
	 * 设置指定key,value到redis
	 * 
	 * @param key
	 * @param value
	 * @param timeout
	 * @param unit
	 *            可指定时间单位
	 */
	public static void set(String key, Object value, long timeout, TimeUnit unit) {
		if (timeout > 0)
			redisTemplate.opsForValue().set(key, value, timeout, unit);
		else {
			redisTemplate.opsForValue().set(key, value);
		}
	}

	/**
	 * 对应key的值减1操作(带乐观锁) key对应的值要通过setLong设置进去
	 * 
	 * @param key
	 * @return
	 */
	public static List<Object> decrWithLock(final String key) {
		List<Object> txResults = (List<Object>) redisTemplate.execute(new SessionCallback<List<Object>>() {
			public List<Object> execute(RedisOperations operations) throws DataAccessException {
				operations.watch(key);
				operations.multi();
				operations.opsForValue().increment(key, -1);
				return operations.exec();
			}
		});
		return txResults;
	}

	/**
	 * @Title: setWithLock
	 * @Description: 设值时加乐观锁
	 * @param key
	 * @param object
	 * @param timeout
	 * @return
	 */
	public static List<Object> setWithLock(final String key, final Object object, final Long timeout) {
		List<Object> txResults = (List<Object>) redisTemplate.execute(new SessionCallback<List<Object>>() {
			public List<Object> execute(RedisOperations operations) throws DataAccessException {
				operations.watch(key);
				operations.multi();
				operations.opsForValue().set(key, object, timeout);
				return operations.exec();
			}
		});
		return txResults;
	}

	/**
	 * 设置指定的Long值到redis
	 * 
	 * @param key
	 * @param value
	 */
	public static void setLong(String key, Long value) {
		setBasic(key, value);
	}

	/**
	 * 设置指定的Long值到redis
	 * 
	 * @param key
	 * @param value
	 * @param timeout
	 *            指定超时时间
	 */
	public static void setLong(String key, Long value, final long timeout) {
		setBasic(key, value, timeout);
	}

	/**
	 * 设置指定的Long值到redis
	 * 
	 * @param key
	 * @param value
	 * @param timeout
	 *            指定超时时间单位s
	 * @return
	 */
	public static Boolean setLongIfAbsent(String key, Long value, final long timeout) {
		return setBasicIfAbsent(key, value, timeout);
	}

	/**
	 * 设置基本类型数据
	 * 
	 * @param key
	 * @param value
	 */
	public static void setBasic(String key, Object value) {
		final byte[] rawKey = rawKey(key);
		final byte[] rawValue = string2bytes(value);
		redisTemplate.execute(new RedisCallback() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				connection.set(rawKey, rawValue);
				return null;
			}
		}, true);
	}

	/**
	 * 设置基本类型数据
	 * 
	 * @param key
	 * @param value
	 * @param timeout
	 */
	public static void setBasic(String key, Object value, final long timeout) {
		final byte[] rawKey = rawKey(key);
		final byte[] rawValue = string2bytes(value);
		redisTemplate.execute(new RedisCallback() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				connection.setEx(rawKey, timeout, rawValue);
				return null;
			}
		}, true);
	}

	/**
	 * 设置指定的Long值到redis
	 * 
	 * @param key
	 * @param value
	 * @param timeout
	 *            指定超时时间单位s
	 * @return
	 */
	public static Boolean setBasicIfAbsent(String key, Object value, final long timeout) {
		final byte[] rawKey = rawKey(key);
		final byte[] rawValue = string2bytes(value);
		return (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				boolean setFlag = connection.setNX(rawKey, rawValue);
				if (setFlag)
					connection.expire(rawKey, timeout);
				return setFlag;
			}
		}, true);
	}

	/**
	 * 获取基本类型数据
	 * 
	 * @param key
	 * @return
	 */
	public static String getBasic(String key) {
		final byte[] rawKey = rawKey(key);

		byte[] bytes = (byte[]) redisTemplate.execute(new RedisCallback() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.get(rawKey);
			}
		}, true);

		if (bytes == null)
			return null;
		return new String(bytes);
	}

	/**
	 * 获取指定key的long类型值,与setLong配合
	 * 
	 * @param key
	 * @return
	 */
	public static Long getLong(String key) {
		String data = getBasic(key);
		if (data == null)
			return null;
		return Long.parseLong(data);
	}

	/**
	 * 获取double类型值
	 * 
	 * @param key
	 * @return
	 */
	public static Double getDouble(String key) {
		String data = getBasic(key);
		if (data == null)
			return null;
		return Double.parseDouble(data);
	}

	/**
	 * 设置指定key值到redis(当key不存在时)
	 * 
	 * @param key
	 * @param value
	 * @param timeout
	 * @return
	 */
	public static boolean setIfAbsent(String key, Object value, long timeout) {
		boolean setFlag = redisTemplate.opsForValue().setIfAbsent(key, value);
		if (setFlag)
			redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
		return setFlag;
	}

	/**
	 * 获取指定key的值
	 * 
	 * @param key
	 * @return
	 */
	public static Object get(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	/**
	 * 开启redis事务,监控指定key
	 * 
	 * @param key
	 */
	public static void watch(Object key) {
		redisTemplate.watch(key);
	}

	/**
	 * 开启事务
	 */
	public static void multi() {
		redisTemplate.multi();
	}

	/**
	 * 执行事务,当返回为null标识执行失败
	 * 
	 * @return
	 */
	public static List exec() {
		return redisTemplate.exec();
	}

	/**
	 * 判断key是否存在
	 * 
	 * @param key
	 * @return
	 */
	public static boolean hasKey(String key) {
		return redisTemplate.hasKey(key);
	}

	/**
	 * 获取指定key过期时间
	 * 
	 * @param key
	 * @return
	 */
	public static Long getExpire(String key) {
		return redisTemplate.getExpire(key);
	}

	/**
	 * 获取指定前缀的key
	 * 
	 * @param pattern
	 *            包含前缀加*号
	 * @return
	 */
	public static Set keys(String pattern) {
		return redisTemplate.keys(pattern);
	}

	/**
	 * 从0开始累加,调用一次加1
	 * 
	 * @param key
	 * @param timeout
	 *            单位秒
	 * @return
	 */
	public static Long incr(String key, Long timeout) {
		boolean hasKey = redisTemplate.hasKey(key);
		Long count = redisTemplate.opsForValue().increment(key, 1);
		if (!hasKey && timeout != null) {
			redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
		}
		return count;
	}

	/**
	 * @Title: incr
	 * @Description: 从0开始累加,可传入累加值
	 * @param key
	 * @param value
	 * @param timeout
	 * @return
	 */
	public static Long incr(String key, Long value, Long timeout) {
		boolean hasKey = redisTemplate.hasKey(key);
		Long count = redisTemplate.opsForValue().increment(key, value);
		if (!hasKey && timeout != null) {
			redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
		}
		return count;
	}

	/**
	 * @Title: incrDouble
	 * @Description: double类型增加
	 * @param key
	 * @param value
	 * @param timeout
	 * @return
	 */
	public static Double incr(String key, Double value, Long timeout) {
		boolean hasKey = redisTemplate.hasKey(key);
		Double count = redisTemplate.opsForValue().increment(key, value);
		if (!hasKey && timeout != null) {
			redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
		}
		return count;
	}

	/**
	 * 指定key减1
	 * 
	 * @param key
	 * @return
	 */
	public static Long decr(String key) {
		long count = redisTemplate.opsForValue().increment(key, -1L);
		return count;
	}

	/**
	 * 删除指定key
	 * 
	 * @param key
	 */
	public static void delete(String key) {
		redisTemplate.delete(key);
	}

	/**
	 * 删除一批key
	 * 
	 * @param keys
	 */
	public static void delete(Collection keys) {
		redisTemplate.delete(keys);
	}

	/**
	 * 删除指定前缀的key
	 * 
	 * @param pattern
	 *            前缀加*号
	 */
	public static void deleteByPattern(String pattern) {
		Set keys = RedisUtil.keys(pattern);
		if (keys != null && keys.size() > 0) {
			redisTemplate.delete(keys);
		}
	}

	/**
	 * 删除hash指定keys的实体
	 * 
	 * @param key
	 * @param hashKeys
	 * @return
	 */
	public static Long hdel(String key, String... hashKeys) {
		return redisTemplate.opsForHash().delete(key, (Object[]) hashKeys);
	}

	/**
	 * 判断hash指定值是否存在
	 * 
	 * @param key
	 * @param hashKey
	 * @return
	 */
	public static Boolean hexists(String key, String hashKey) {
		return redisTemplate.opsForHash().hasKey(key, hashKey);
	}

	/**
	 * 获取hash指定key的值
	 * 
	 * @param key
	 * @param hashKey
	 * @return
	 */
	public static Object hget(String key, String hashKey) {
		return redisTemplate.opsForHash().get(key, hashKey);
	}

	/**
	 * 获取指定hash
	 * @param key
	 * @return
	 */
	public static Map hgetall(String key) {
		return redisTemplate.opsForHash().entries(key);
	}

	/**
	 * 对hash指定key增加一个值
	 * @param key
	 * @param hashKey
	 * @param delta
	 * @return
	 */
	public static Object hincrby(String key, String hashKey, long delta) {
		return redisTemplate.opsForHash().increment(key, hashKey, delta);
	}

	/**
	 * 对hash指定key增加一个值
	 * 
	 * @param key
	 * @param hashKey
	 * @param delta
	 * @return
	 */
	public static Object hincrby(String key, String hashKey, double delta) {
		return redisTemplate.opsForHash().increment(key, hashKey, delta);
	}

	/**
	 * 获取hash所有key
	 * 
	 * @param key
	 * @return
	 */
	public static Set hkeys(String key) {
		return redisTemplate.opsForHash().keys(key);
	}

	/**
	 * 为hash增加一个实体
	 * 
	 * @param key
	 * @param hashKey
	 * @param value
	 */
	public static void hset(String key, String hashKey, Object value) {
		redisTemplate.opsForHash().put(key, hashKey, value);
	}

	/**
	 * 获取hash多个keys的值
	 * 
	 * @param key
	 * @param hashKeys
	 * @return
	 */
	public static List hmget(String key, Collection hashKeys) {
		return redisTemplate.opsForHash().multiGet(key, hashKeys);
	}

	/**
	 * 为hash写入多个值
	 * 
	 * @param key
	 * @param map
	 */
	public static void hmset(String key, Map map) {
		redisTemplate.opsForHash().putAll(key, map);
	}

	/**
	 * 当hash不存在该hashKey插入值
	 * 
	 * @param key
	 * @param hashKey
	 * @param value
	 * @return
	 */
	public static Boolean hsetnx(String key, String hashKey, Object value) {
		return redisTemplate.opsForHash().putIfAbsent(key, hashKey, value);
	}

	/**
	 * 获取hash表长度
	 * 
	 * @param key
	 * @return
	 */
	public static Object hlen(String key) {
		return redisTemplate.opsForHash().size(key);
	}

	/**
	 * 获取hash表所有values
	 * 
	 * @param key
	 * @return
	 */
	public static List hvals(String key) {
		return redisTemplate.opsForHash().values(key);
	}

	/**
	 * 根据条件,获取hash的值
	 * 
	 * @param key
	 * @param options
	 * @return
	 */
	public static Cursor hscan(String key, ScanOptions options) {
		return redisTemplate.opsForHash().scan(key, options);
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * 集合-新增
	 * 
	 * @param key
	 * @param values
	 * @return
	 */
	public static Long sadd(String key, Object... values) {
		return redisTemplate.opsForSet().add(key, (Object[]) values);
	}

	/**
	 * 集合中元素个数
	 * 
	 * @param key
	 * @return
	 */
	public static Long scard(String key) {
		return redisTemplate.opsForSet().size(key);
	}

	/**
	 * 是否是集合的成员
	 * 
	 * @param key
	 * @param o
	 * @return
	 */
	public static Boolean sismember(String key, Object o) {
		return redisTemplate.opsForSet().isMember(key, o);
	}

	/**
	 * 返回集合所有成员
	 * 
	 * @param key
	 * @return
	 */
	public static Set smembers(String key) {
		return redisTemplate.opsForSet().members(key);
	}

	/**
	 * 删除集合中的元素
	 * 
	 * @param key
	 * @param values
	 * @return
	 */
	public static Long srem(String key, Object... values) {
		return redisTemplate.opsForSet().remove(key, (Object[]) values);
	}

	/**
	 * 两个集合差集
	 * 
	 * @param key
	 * @param otherKey
	 * @return
	 */
	public static Set sdiff(String key, String otherKey) {
		return redisTemplate.opsForSet().difference(key, otherKey);
	}

	/**
	 * 两个集合交集
	 * 
	 * @param key
	 * @param otherKey
	 * @return
	 */
	public static Set sinter(String key, String otherKey) {
		return redisTemplate.opsForSet().intersect(key, otherKey);
	}

	/**
	 * 两个集合并集
	 * 
	 * @param key
	 * @param otherKey
	 * @return
	 */
	public static Set sunion(String key, String otherKey) {
		return redisTemplate.opsForSet().union(key, otherKey);
	}

	/**
	 * 迭代集合中元素
	 * 
	 * @param key
	 * @param scanOptions
	 * @return
	 */
	public static Cursor sscan(String key, ScanOptions scanOptions) {
		return redisTemplate.opsForSet().scan(key, scanOptions);
	}

	/**
	 * 将指定key,value加入到队列
	 * 
	 * @param key
	 * @param value
	 */
	public static void push(String key, Object value) {
		redisTemplate.opsForList().leftPush(key, value);
	}

	/**
	 * 从指定key的队列取出一个值
	 * 
	 * @param key
	 * @return
	 */
	public static Object pop(String key) {
		return redisTemplate.opsForList().rightPop(key);
	}

	/**
	 * 获取队列操作对象
	 * 
	 * @return
	 */
	public static ListOperations getQueueObject() {
		return redisTemplate.opsForList();
	}

	/**
	 * 获取指定key的队列的长度
	 * 
	 * @param key
	 * @return
	 */
	public static Long getQueueSize(String key) {
		return redisTemplate.opsForList().size(key);
	}

	/**
	 * 将指定key的集合放入队列
	 * 
	 * @param key
	 * @param values
	 */
	public static void pushAll(String key, Collection values) {
		redisTemplate.opsForList().leftPushAll(key, values);
	}

	/**
	 * 将指定key的对象数组放入队列
	 * 
	 * @param key
	 * @param values
	 */
	public static void pushAll(String key, Object... values) {
		redisTemplate.opsForList().leftPushAll(key, values);
	}

	/**
	 * 移除指定位置的对象
	 * 
	 * @param key
	 * @param index
	 * @param value
	 */
	public static void remove(String key, long index, Object value) {
		redisTemplate.opsForList().remove(key, index, value);
	}

}
