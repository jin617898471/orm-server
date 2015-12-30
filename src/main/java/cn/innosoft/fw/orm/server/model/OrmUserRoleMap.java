package cn.innosoft.fw.orm.server.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ORM_USER_ROLE_MAP database table.
 * 
 */
@Entity
@Table(name="ORM_USER_ROLE_MAP")
@NamedQuery(name="OrmUserRoleMap.findAll", query="SELECT o FROM OrmUserRoleMap o")
public class OrmUserRoleMap implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name="ROLE_ID")
	private String roleId;

	@Column(name="SYSTEM_ID")
	private String systemId;

	@Column(name="USER_ID")
	private String userId;

	public OrmUserRoleMap() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}