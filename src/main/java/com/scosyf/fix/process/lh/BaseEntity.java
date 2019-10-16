package com.scosyf.fix.process.lh;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class BaseEntity implements Serializable {
	
	private static final long serialVersionUID = -1216688482288911355L;
	public static final String idField = "id";
	public static final String createUserField = "createUser";
	public static final String createDateField = "createDate";
	public static final String updateUserField = "modifyUser";
	public static final String updateDateField = "modifyDate";
	public static final String versionField = "version";
	public static final String statusField = "status";
	public static final String remarkField = "remark";

	private String id;
	
	private String createUser;
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss.S")
	private Date createDate;
	
	private String modifyUser;
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss.S")
	private Date modifyDate;
	
	private Integer version;
	
	private Integer status;
	
	private String remark;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateDate() {
		return DateUtils.toString(this.createDate);
	}

	public Date getRealCreateDate() {
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

	public String getModifyDate() {
		return DateUtils.toString(modifyDate);
	}

	public Date getRealModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean isEmpty() {
		return false;
	}

	@Override
	public String toString() {
		return "{id:" + id + ", createUser:" + createUser
				+ ", createDate:" + createDate + ", modifyUser:" + modifyUser
				+ ", modifyDate:" + modifyDate + ", version:" + version
				+ ", status:" + status + ", remark:" + remark + "}";
	}
}