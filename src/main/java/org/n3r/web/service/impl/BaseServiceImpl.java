package org.n3r.web.service.impl;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.n3r.core.lang.RClose;
import org.n3r.eql.Eql;
import org.n3r.eql.EqlTran;
import org.n3r.web.common.utils.UUIDUtils;
import org.n3r.web.entity.AddressInfo;
import org.n3r.web.entity.BaseEntity;
import org.n3r.web.service.BaseService;
import org.springframework.util.Assert;

import com.google.common.base.Throwables;

public class BaseServiceImpl<T, PK extends Serializable> implements BaseService<T, PK> {

    private Class<T> entityClass;

    public BaseServiceImpl() {
        Class c = getClass();
        Type type = c.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
            this.entityClass = (Class<T>) parameterizedType[0];
        }
    }

    @Override
    public T get(PK id) {
        Assert.notNull(id, "id is required");
        return new Eql().useSqlFile(getClass())
                .selectFirst(BaseEntity.ON_GET_METHOD_NAME + entityClass.getSimpleName())
                .params(id)
                .returnType(AddressInfo.class)
                .execute();
    }

    @Override
    public List<T> getAllList() {
        return null;
    }

    @Override
    public Long getTotalCount() {
        return null;
    }

    @Override
    public PK save(T entity) {
        Assert.notNull(entity, "entity is required");
        try {

            String id = ((BaseEntity) entity).getId();
            if (StringUtils.isEmpty(id))
                ((BaseEntity) entity).setId(UUIDUtils.getUUID());

            Method method = entity.getClass().getMethod(BaseEntity.ON_SAVE_METHOD_NAME);
            method.invoke(entity);
        } catch (Exception e) {
            Throwables.propagate(e);
            return null;
        }
        new Eql().useSqlFile(getClass())
                .update(BaseEntity.ON_SAVE_METHOD_NAME + entity.getClass().getSimpleName())
                .params(entity).execute();
        return (PK) ((BaseEntity) entity).getId();
    }

    @Override
    public void update(T entity) {
        new Eql().useSqlFile(getClass())
                .update(BaseEntity.ON_UPDATE_METHOD_NAME + entity.getClass().getSimpleName())
                .params(entity).execute();

    }

    @Override
    public void delete(T entity) {
    }

    @Override
    public void delete(PK id) {
        Assert.notNull(id, "id is required");
        new Eql().useSqlFile(getClass())
                .update(BaseEntity.ON_DELETE_METHOD_NAME + entityClass.getSimpleName())
                .params(id).execute();
    }

    @Override
    public void delete(PK[] ids) {
        Assert.notEmpty(ids, "ids must not be empty");
        Eql eql = new Eql().useSqlFile(getClass());
        EqlTran tran = eql.newTran();
        try {
            tran.start();
            for (PK id : ids) {
                eql.update(BaseEntity.ON_DELETE_METHOD_NAME + entityClass.getSimpleName()).params(id).execute();
            }
            tran.commit();
        } catch (Exception ex) {
            tran.rollback();
            Throwables.propagate(ex);
        } finally {
            RClose.closeQuietly(tran);
        }

    }
}
