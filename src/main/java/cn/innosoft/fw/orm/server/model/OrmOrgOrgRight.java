package cn.innosoft.fw.orm.server.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ORM_ORG_ORG_RIGHT database table.
 * 
 */
@Entity
@Table(name="ORM_ORG_ORG_RIGHT")
@NamedQuery(name="OrmOrgOrgRight.findAll", query="SELECT o FROM OrmOrgOrgRight o")
public class OrmOrgOrgRight implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name="ORG_ID")
	private String orgId;

	@Column(name="ORG_RIGHT_ID")
	private String orgRightId;

	@Column(name="RELATE_TYPE")
	private String relateType;

	public OrmOrgOrgRight() {
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

	public String getOrgRightId() {
		return this.orgRightId;
	}

	public void setOrgRightId(String orgRightId) {
		this.orgRightId = orgRightId;
	}

	public String getRelateType() {
		return this.relateType;
	}

	public void setRelateType(String relateType) {
		this.relateType = relateType;
	}

}