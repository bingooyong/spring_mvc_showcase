package org.n3r.web.entity;

import java.io.Serializable;
import java.util.Date;

public class BaseEntity implements Serializable {

    public static final String CREATE_DATE_PROPERTY_NAME = "createDate";// "创建日期"属性名称
    public static final String MODIFY_DATE_PROPERTY_NAME = "modifyDate";// "修改日期"属性名称
    public static final String ON_SAVE_METHOD_NAME = "onSave";// "保存"方法名称
    public static final String ON_UPDATE_METHOD_NAME = "onUpdate";// "更新"方法名称
    public static final String ON_DELETE_METHOD_NAME = "onDelete";// "删除"方法名称
    public static final String ON_GET_METHOD_NAME = "onGet";// "查询"方法名称

    protected String id;// ID
    protected Date createDate;// 创建日期
    protected Date modifyDate;// 修改日期

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public void onSave() {
    }

    public void onUpdate() {
    }
}
