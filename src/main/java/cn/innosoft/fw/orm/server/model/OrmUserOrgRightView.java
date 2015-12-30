package cn.innosoft.fw.orm.server.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the ORM_USER_ORG_RIGHT_VIEW database table.
 * 
 */
@Entity
@IdClass(OrmUserOrgRightViewPK.class)
@Table(name="ORM_USER_ORG_RIGHT_VIEW")
@NamedQuery(name="OrmUserOrgRightView.findAll", query="SELECT o FROM OrmUserOrgRightView o")
public class OrmUserOrgRightView implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ORG_ID")
	private String orgId;

	@Id
	@Column(name="ORG_RIGHT_ID")
	private String orgRightId;

	@Column(name="ORG_SOURCE_ID")
	private String orgSourceId;

	@Id
	@Column(name="USER_ID")
	private String userId;

	public OrmUserOrgRightView() {
	}

	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgRightId() {
		return this.orgRightId;
	}

	public void setOrgRightId(String orgRightId) {
		this.orgRightId = orgRightId;
	}

	public String getOrgSourceId() {
		return this.orgSourceId;
	}

	public void setOrgSourceId(String orgSourceId) {
		this.orgSourceId = orgSourceId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}