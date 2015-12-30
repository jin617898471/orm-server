package cn.innosoft.fw.orm.server.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ORM_ORG_CODE_RIGHT database table.
 * 
 */
@Entity
@Table(name="ORM_ORG_CODE_RIGHT")
@NamedQuery(name="OrmOrgCodeRight.findAll", query="SELECT o FROM OrmOrgCodeRight o")
public class OrmOrgCodeRight implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name="CODE_ID")
	private String codeId;

	@Column(name="ORG_ID")
	private String orgId;

	@Column(name="RELATE_TYPE")
	private String relateType;

	@Column(name="SYSTEM_ID")
	private String systemId;

	public OrmOrgCodeRight() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCodeId() {
		return this.codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
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

	public String getSystemId() {
		return this.systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

}