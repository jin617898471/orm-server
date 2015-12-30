package cn.innosoft.fw.orm.server.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the ORM_USER_ROLE_MAP_VIEW database table.
 * 
 */
@Entity
@IdClass(OrmUserRoleMapViewPK.class)
@Table(name="ORM_USER_ROLE_MAP_VIEW")
@NamedQuery(name="OrmUserRoleMapView.findAll", query="SELECT o FROM OrmUserRoleMapView o")
public class OrmUserRoleMapView implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ORG_ID")
	private String orgId;

	@Column(name="ORG_SOURCE_ID")
	private String orgSourceId;

	@Column(name="RELATE_TYPE")
	private String relateType;

	@Id
	@Column(name="ROLE_ID")
	private String roleId;

	@Column(name="SYSTEM_ID")
	private String systemId;

	@Id
	@Column(name="USER_ID")
	private String userId;

	public OrmUserRoleMapView() {
	}

	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgSourceId() {
		return this.orgSourceId;
	}

	public void setOrgSourceId(String orgSourceId) {
		this.orgSourceId = orgSourceId;
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

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}