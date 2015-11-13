package cn.innosoft.fw.orm.server.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;


/**
 * The persistent class for the ORM_SYSTEM database table.
 * 
 */
@Entity
@Table(name="ORM_SYSTEM")
@NamedQuery(name="OrmSystem.findAll", query="SELECT o FROM OrmSystem o")
public class OrmSystem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
//	@GeneratedValue(generator = "system-uuid")
//	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name="SYSTEM_ID")
	private String systemId;

	@Column(name="CREATE_DT")
	private Date createDt;

	@Column(name="CREATE_USER_ID")
	private String createUserId;

	@Column(name="SYSTEM_CODE")
	private String systemCode;

	@Column(name="SYSTEM_DESC")
	private String systemDesc;

	@Column(name="SYSTEM_NAME")
	private String systemName;

	@Temporal(TemporalType.DATE)
	@Column(name="UPDATE_DT")
	private Date updateDt;

	@Column(name="UPDATE_USER_ID")
	private String updateUserId;

	@Column(name="VALID_SIGN")
	private String validSign;

	public OrmSystem() {
	}

	public String getSystemId() {
		return this.systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public Date getCreateDt() {
		return this.createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	public String getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getSystemCode() {
		return this.systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public String getSystemDesc() {
		return this.systemDesc;
	}

	public void setSystemDesc(String systemDesc) {
		this.systemDesc = systemDesc;
	}

	public String getSystemName() {
		return this.systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public Date getUpdateDt() {
		return this.updateDt;
	}

	public void setUpdateDt(Date updateDt) {
		this.updateDt = updateDt;
	}

	public String getUpdateUserId() {
		return this.updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getValidSign() {
		return this.validSign;
	}

	public void setValidSign(String validSign) {
		this.validSign = validSign;
	}

}