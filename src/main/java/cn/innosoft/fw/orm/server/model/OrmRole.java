package cn.innosoft.fw.orm.server.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the ORM_ROLE database table.
 * 
 */
@Entity
@Table(name = "ORM_ROLE")
@NamedQuery(name = "OrmRole.findAll", query = "SELECT o FROM OrmRole o")
public class OrmRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ROLE_ID")
	private String roleId;

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATE_DT")
	private Date createDt;

	@Column(name = "CREATE_USER_ID")
	private String createUserId;

	@Column(name = "ROLE_DESC")
	private String roleDesc;

	@Column(name = "ROLE_NAME_CN")
	private String roleNameCn;

	@Column(name = "ROLE_NAME_EN")
	private String roleNameEn;

	@Column(name = "ROLE_TYPE")
	private String roleType;

	@Column(name = "SYSTEM_ID")
	private String systemId;

	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATE_DT")
	private Date updateDt;

	@Column(name = "UPDATE_USER_ID")
	private String updateUserId;

	@Column(name = "VALID_SIGN")
	private String validSign;

	public OrmRole() {
	}

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public Date getCreateDt() {
		return this.createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	public String getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getRoleDesc() {
		return this.roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public String getRoleNameCn() {
		return this.roleNameCn;
	}

	public void setRoleNameCn(String roleNameCn) {
		this.roleNameCn = roleNameCn;
	}

	public String getRoleNameEn() {
		return this.roleNameEn;
	}

	public void setRoleNameEn(String roleNameEn) {
		this.roleNameEn = roleNameEn;
	}

	public String getRoleType() {
		return this.roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getSystemId() {
		return this.systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public Date getUpdateDt() {
		return this.updateDt;
	}

	public void setUpdateDt(Date updateDt) {
		this.updateDt = updateDt;
	}

	public String getUpdateUserId() {
		return this.updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getValidSign() {
		return this.validSign;
	}

	public void setValidSign(String validSign) {
		this.validSign = validSign;
	}

}