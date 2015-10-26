package cn.innosoft.fw.orm.server.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * The persistent class for the ORM_ROLE_CODE_RIGHT database table.
 * 
 */
@Entity
@Table(name="ORM_ROLE_CODE_RIGHT")
@NamedQuery(name="OrmRoleCodeRight.findAll", query="SELECT o FROM OrmRoleCodeRight o")
public class OrmRoleCodeRight implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@Column(name="AUTO_ASSOCIATIVE")
	private String autoAssociative;

	@Column(name="CODE_ID")
	private String codeId;

	@Column(name="HALF_SELECT")
	private String halfSelect;

	@Column(name="RESOURCE_ID")
	private String resourceId;

	@Column(name="ROLE_ID")
	private String roleId;

	@Column(name="SYSTEM_ID")
	private String systemId;

	@Column(name="\"TYPE\"")
	private String type;

	public OrmRoleCodeRight() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAutoAssociative() {
		return this.autoAssociative;
	}

	public void setAutoAssociative(String autoAssociative) {
		this.autoAssociative = autoAssociative;
	}

	public String getCodeId() {
		return this.codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	public String getHalfSelect() {
		return this.halfSelect;
	}

	public void setHalfSelect(String halfSelect) {
		this.halfSelect = halfSelect;
	}

	public String getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getSystemId() {
		return this.systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}