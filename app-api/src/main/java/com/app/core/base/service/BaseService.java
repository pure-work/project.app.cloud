package com.app.core.base.service;

import com.app.core.base.dao.BaseMapper;
import com.app.core.base.entity.BaseEntity;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Service - 基类
 * 
 * 
 * 
 */
public interface BaseService<T extends BaseMapper, S extends BaseEntity> {

	/**
	 * 查找实体对象
	 * 
	 * @param id
	 *            ID
	 * @return 实体对象，若不存在则返回null
	 */
	S find(Long id);

	/**
	 * 根据主键查询（带悲观锁，请确定你要加锁才调用）
	 * @param id
	 * @return
	 */
	//S selectWithLock(String id);
	
	S findByField(S entity);
	
	List<S> findListByField(S entity);
	
	//Page<S> findByField(S entity, RowBounds rowBounds);

	List<S> findByExample(Example example);

	//Page<S> findByExample(Example example, RowBounds rowBounds);

	int countByExample(Example example);
	
	void saveNotNull(S entity);
	
	void save(List<S> list);
	
	/**
	 * 查找所有实体对象集合
	 * 
	 * @return 所有实体对象集合
	 */
	List<S> findAll();

	/**
	 * 查找实体对象集合
	 * 
	 * @param ids
	 *            ID
	 * @return 实体对象集合
	 */
	List<S> findList(Long... ids);


	/**
	 * 查找实体对象分页
	 * 
	 * @param pageable
	 *            分页信息
	 * @return 实体对象分页
	 */
	//Page<S> findPage(Pageable pageable);

	/**
	 * 查询实体对象总数
	 * 
	 * @return 实体对象总数
	 */
	long count();

	/**
	 * 查询实体对象数量
	 * 
	 * @param filters
	 *            筛选
	 * @return 实体对象数量
	 */
	//long count(Filter... filters);

	/**
	 * 判断实体对象是否存在
	 * 
	 * @param id
	 *            ID
	 * @return 实体对象是否存在
	 */
	boolean exists(String id);

	/**
	 * 判断实体对象是否存在
	 * 
	 * @param filters
	 *            筛选
	 * @return 实体对象是否存在
	 */
	//boolean exists(Filter... filters);

	/**
	 * 保存实体对象
	 * 
	 * @param entity
	 *            实体对象
	 */
	void save(S entity);

	/**
	 * 更新实体对象
	 * 
	 * @param entity
	 *            实体对象
	 * @return 实体对象
	 */
	int update(S entity);
	
	int updateAllFields(S entity);

	/**
	 * 更新实体对象
	 * 
	 * @param entity
	 *            实体对象
	 * @param ignoreProperties
	 *            忽略属性
	 * @return 实体对象
	 */
	//int update(S entity, String... ignoreProperties);

	/**
	 * 删除实体对象
	 * 
	 * @param id
	 *            ID
	 */
	void delete(Long id);

	/**
	 * 删除实体对象
	 * 
	 * @param ids
	 *            ID
	 */
	void delete(Long... ids);

	/**
	 * 删除实体对象
	 * 
	 * @param entity
	 *            实体对象
	 */
	int deleteByField(S entity);
	
	/**
     * 删除(按字段查询)
     * @return 
     */
	int deleteByExample(Example example);
	/** 
	* @Title: saveOrUpdate 
	* @Description: 保存或更新实体
	* @param entity
	* void    返回类型 
	* @throws 
	*/
	void saveOrUpdate(S entity);
	/** 
	* @Title: findAll 
	* @Description: 根据字段类型动态查询数据
	* @param conditions
	* @param pageable
	* @return
	* Page<T>    返回类型 
	* @throws 
	*/
	//public Page<S> findAll(Map<String, Object> conditions,Pageable pageable);
	
	/**
	 * 统计(按非空字段为条件查询)
	 * @param entity
	 * @return
	 */
	long count(S entity);

	/**
	 * 根据条件分页查询
	 * @param record
	 * @return
	 */
	//Page<S> queryPage(S record);

	/**
	 * 分页显示数据
	 * @param page
	 * @param row
	 * @return
	 */
	//Page<S> queryPage(int page, int row);

	/**
	 * 根据条件分页查询
	 * @param example
	 * @param page
	 * @param row
	 * @return
	 */
	//Page<S> queryPage(Object example, int page, int row);

}