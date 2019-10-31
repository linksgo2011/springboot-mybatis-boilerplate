package boilerplate.core;


import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Condition;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class AbstractService<T> implements Service<T> {

    @Autowired
    protected CommonMapper<T> commonMapper;

    private Class<T> modelClass;

    public AbstractService() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        modelClass = (Class<T>) pt.getActualTypeArguments()[0];
    }

    public void save(T model) {
        commonMapper.insertSelective(model);
    }

    public void save(List<T> models) {
        commonMapper.insertList(models);
    }

    public void deleteById(Integer id) {
        commonMapper.deleteByPrimaryKey(id);
    }

    public void deleteByIds(String ids) {
        commonMapper.deleteByIds(ids);
    }

    public void update(T model) {
        commonMapper.updateByPrimaryKeySelective(model);
    }

    public T findById(Integer id) {
        return commonMapper.selectByPrimaryKey(id);
    }

    @Override
    public T findBy(String fieldName, Object value) throws TooManyResultsException {
        try {
            T model = modelClass.newInstance();
            Field field = modelClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(model, value);
            return commonMapper.selectOne(model);
        } catch (ReflectiveOperationException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public List<T> findByIds(String ids) {
        return commonMapper.selectByIds(ids);
    }

    public List<T> findByCondition(Condition condition) {
        return commonMapper.selectByCondition(condition);
    }

    public List<T> findAll() {
        return commonMapper.selectAll();
    }
}
