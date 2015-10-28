package cn.innosoft.fw.orm.server.model;

import java.io.Serializable;
import javax.persistence.*;


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
	private String id;

	@Column(name="CODE_ID")
	private String codeId;

	@Column(name="HALF_SELECT")
	private String halfSelect;

	@Column(name="MAP_TYPE")
	private String mapType;

	@Column(name="ORG_O_ID")
	private String orgOId;

	@Column(name="ORG_P_ID")
	private String orgPId;

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

	public String getHalfSelect() {
		return this.halfSelect;
	}

	public void setHalfSelect(String halfSelect) {
		this.halfSelect = halfSelect;
	}

	public String getMapType() {
		return this.mapType;
	}

	public void setMapType(String mapType) {
		this.mapType = mapType;
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

	public String getSystemId() {
		return this.systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

}