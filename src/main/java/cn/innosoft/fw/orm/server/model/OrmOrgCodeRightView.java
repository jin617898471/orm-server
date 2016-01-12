package cn.innosoft.fw.orm.server.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the ORM_ORG_CODE_RIGHT_VIEW database table.
 * 
 */
@Entity
@IdClass(OrmOrgCodeRightViewPk.class)
@Table(name="ORM_ORG_CODE_RIGHT_VIEW")
@NamedQuery(name="OrmOrgCodeRightView.findAll", query="SELECT o FROM OrmOrgCodeRightView o")
public class OrmOrgCodeRightView implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODE_ID")
	private String codeId;

	@Column(name="CODE_INDEX")
	private String codeIndex;

	@Column(name="CODE_NAME")
	private String codeName;

	@Column(name="CODE_VALUE")
	private String codeValue;

	@Id
	@Column(name="ORG_ID")
	private String orgId;

	@Column(name="ORG_SOURCE_ID")
	private String orgSourceId;

	@Column(name="SYSTEM_ID")
	private String systemId;

	public OrmOrgCodeRightView() {
	}

	public String getCodeId() {
		return this.codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	public String getCodeIndex() {
		return this.codeIndex;
	}

	public void setCodeIndex(String codeIndex) {
		this.codeIndex = codeIndex;
	}

	public String getCodeName() {
		return this.codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getCodeValue() {
		return this.codeValue;
	}

	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
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

	public String getSystemId() {
		return this.systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

}