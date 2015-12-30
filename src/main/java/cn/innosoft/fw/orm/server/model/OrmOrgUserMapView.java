package cn.innosoft.fw.orm.server.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the ORM_ORG_USER_MAP_VIEW database table.
 * 
 */
@Entity
@IdClass(OrmOrgUserMapViewPK.class)
@Table(name="ORM_ORG_USER_MAP_VIEW")
@NamedQuery(name="OrmOrgUserMapView.findAll", query="SELECT o FROM OrmOrgUserMapView o")
public class OrmOrgUserMapView implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="ORG_I_ID")
	private String orgIId;

	@Id
	@Column(name="ORG_ID")
	private String orgId;

	@Column(name="ORG_O_ID")
	private String orgOId;

	@Column(name="ORG_P_ID")
	private String orgPId;

	@Column(name="ORG_TYPE")
	private String orgType;

	@Id
	@Column(name="USER_ID")
	private String userId;

	public OrmOrgUserMapView() {
	}

	public String getOrgIId() {
		return this.orgIId;
	}

	public void setOrgIId(String orgIId) {
		this.orgIId = orgIId;
	}

	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgOId() {
		return this.orgOId;
	}

	public void setOrgOId(String orgOId) {
		this.orgOId = orgOId;
	}

	public String getOrgPId() {
		return this.orgPId;
	}

	public void setOrgPId(String orgPId) {
		this.orgPId = orgPId;
	}

	public String getOrgType() {
		return this.orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}