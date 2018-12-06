package com.app.core.base.service;

import com.app.core.base.dao.BaseMapper;
import com.app.core.base.entity.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * Service - 基类
 */
public class BaseServiceImpl<T extends BaseMapper, S extends BaseEntity> implements BaseService<T, S> {
	/** baseMapper */
	@SuppressWarnings("SpringJavaAutowiredMembersInspection")
	@Autowired
	protected T baseMapper;
	/**
	 * 查询
	 */
	@Override
	@Transactional(readOnly = true)
	public S find(Long id) {
	    return (S) baseMapper.selectByPrimaryKey(id);
	}
	/*@Override
	public S selectWithLock(String id) {
		return (S) baseMapper.selectWithLock(Long.valueOf(id));
	}*/
	@Override
	@Transactional(readOnly = true)
    public S findByField(S entity) {
        return (S) baseMapper.selectOne(entity);
    }
	@Override
	@Transactional(readOnly = true)
	public List<S> findListByField(S entity) {
		return baseMapper.select(entity);
	}
	@Override
    @Transactional(readOnly = true)
    public List<S> findByExample(Example example) {
        return baseMapper.selectByExample(example);
    }
	/*@Override
    @Transactional(readOnly = true)
    public Page<S> findByField(S entity,RowBounds rowBounds) {
		return (Page<S>)baseMapper.selectByRowBounds(entity, rowBounds);
    }*/
	/*@Override
    @Transactional(readOnly = true)
    public Page<S> findByExample(Example example,RowBounds rowBounds) {
        return (Page<S>)baseMapper.selectByExampleAndRowBounds(example, rowBounds);
    }*/
	@Override
	@Transactional(readOnly = true)
	public List<S> findAll() {
		return baseMapper.selectAll();
	}
	/**
	 * 按id列表查询
	 */
	@Override
	@Transactional(readOnly = true)
	public List<S> findList(Long... ids) {
		List<S> result = new ArrayList<S>();
		if (ids != null) {
			for (Long id : ids) {
				S entity = find(id);
				if (entity != null) {
					result.add(entity);
				}
			}
		}
		return result;
	}
    /**
     * 计数(所有)
     */
    @Override
    public long count() {
        return baseMapper.selectCount(null);
    }
    /**
     * 计数(按字段查询)
     */
    @Override
    public long count(S entity) {
        return baseMapper.selectCount(entity);
    }
	/*@Override
	public Page<S> queryPage(S record) {
		RowBounds rowBounds = new RowBounds(record.getPageNo(), record.getPageSize());
		Page<S> result = (Page<S>)baseMapper.selectByRowBounds(record, rowBounds);
		return result;
	}*/
	/*@Override
	public Page<S> queryPage(int offset, int limit) {
		RowBounds rowBounds = new RowBounds(offset, limit);
		Page<S> result = (Page<S>)baseMapper.selectByExampleAndRowBounds(null, rowBounds);
		return result;
	}*/
	/*@Override
	public Page<S> queryPage(Object example, int offset, int limit) {
		RowBounds rowBounds = new RowBounds(offset, limit);
		Page<S> result = (Page<S>)baseMapper.selectByExampleAndRowBounds(example, rowBounds);
		return result;
	}*/
	@Override
    @Transactional(readOnly = true)
    public int countByExample(Example example) {
        return baseMapper.selectCountByExample(example);
    }
//	@Transactional(readOnly = true)
//	public Page<T> findPage(Pageable pageable) {
//		return baseMapper.findPage(pageable);
//	}
	/**
	 * 判断是否存在
	 */
    @Override
	@Transactional(readOnly = true)
	public boolean exists(String id) {
		return baseMapper.selectByPrimaryKey(id)!=null;
	}
	/**
	 * 保存(非空字段)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveNotNull(S entity) {
		baseMapper.insertSelective(entity);
	}
	@Override
	@Transactional
    public void save(List<S> list) {
        baseMapper.insertList(list);
    }
	@Override
    @Transactional
    public void save(S entity) {
        baseMapper.insert(entity);
    }
	/**
	 * 修改(非空字段)
	 */
	@Override
	@Transactional
	public int update(S entity) {
		return baseMapper.updateByPrimaryKeySelective(entity);
	}
	/**
	 * 修改(所有字段)
	 * @param entity
	 * @return
	 */
	@Override
	@Transactional
	public int updateAllFields(S entity) {
		Assert.notNull(entity);
		return baseMapper.updateByPrimaryKey(entity);
	}
	/**
	 * 删除
	 */
	@Override
	@Transactional
	public void delete(Long id) {
		baseMapper.deleteByPrimaryKey(id);
	}
	/**
	 * 删除(id列表)
	 */
	@Override
	@Transactional
	public void delete(Long... ids) {
		if (ids != null) {
			for (Long id : ids) {
			    baseMapper.deleteByPrimaryKey(id);
			}
		}
	}
	/**
	 * 删除(按字段查询)
	 * @return
	 */
	@Override
	@Transactional
	public int deleteByField(S entity) {
		return baseMapper.delete(entity);
	}
	/**
     * 删除(按字段查询)
	 * @return
     */
	@Override
    @Transactional
    public int deleteByExample(Example example) {
        return baseMapper.deleteByExample(example);
    }
    @Override
    public void saveOrUpdate(S entity) {
        if (((BaseEntity) entity).getId() != null) {
            baseMapper.updateByPrimaryKey(entity);
        } else {
            baseMapper.insert(entity);
        }
    }
	
}