package cn.innosoft.fw.orm.server.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ORM_ORG_ROLE_MAP database table.
 * 
 */
@Entity
@Table(name="ORM_ORG_ROLE_MAP")
@NamedQuery(name="OrmOrgRoleMap.findAll", query="SELECT o FROM OrmOrgRoleMap o")
public class OrmOrgRoleMap implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name="ORG_ID")
	private String orgId;

	@Column(name="RELATE_TYPE")
	private String relateType;

	@Column(name="ROLE_ID")
	private String roleId;

	@Column(name="SYSTEM_ID")
	private String systemId;

	public OrmOrgRoleMap() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getRelateType() {
		return this.relateType;
	}

	public void setRelateType(String relateType) {
		this.relateType = relateType;
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

}