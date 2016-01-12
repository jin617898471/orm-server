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
 * The persistent class for the ORM_ORG_CODE_MAP database table.
 * 
 */
@Entity
@Table(name="ORM_ORG_CODE_MAP")
@NamedQuery(name="OrmOrgCodeMap.findAll", query="SELECT o FROM OrmOrgCodeMap o")
public class OrmOrgCodeMap implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@Column(name="CODE_ID")
	private String codeId;

	@Column(name="ORG_ID")
	private String orgId;

	@Column(name="RELATE_TYPE")
	private String relateType;

	@Column(name="SYSTEM_ID")
	private String systemId;

	public OrmOrgCodeMap() {
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