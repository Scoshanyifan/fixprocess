package com.scosyf.fix.process.lh;

import java.util.Date;

/**
 * <p>
 *     mongodb 基础Model
 * </p>
 *
 * @author 刘宸玮
 * @date 2018/8/7
 */
public abstract class BaseMongoEntity {

    private String  createUser;

    private Date    createDate;

    private String  modifyUser;

    private Date    modifyDate;

    private Integer version;

    private Integer status;

    private String  remark;

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
