package cn.innosoft.fw.orm.server.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ORM_CODE database table.
 * 
 */
@Entity
@Table(name="ORM_CODE")
@NamedQuery(name="OrmCode.findAll", query="SELECT o FROM OrmCode o")
public class OrmCode implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODE_ID")
	private String codeId;

	@Column(name="CODE_DESC")
	private String codeDesc;

	@Column(name="CODE_NAME")
	private String codeName;

	@Column(name="CODE_VALUE")
	private String codeValue;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATE_DT")
	private Date createDt;

	@Column(name="CREATE_USER_ID")
	private String createUserId;

	@Column(name="IS_RIGHT")
	private String isRight;

	@Column(name="ORDER_NUMBER")
	private BigDecimal orderNumber;

	@Column(name="PARENT_CODE_ID")
	private String parentCodeId;

	@Column(name="ROOT_CODE_ID")
	private String rootCodeId;

	@Column(name="SYSTEM_ID")
	private String systemId;

	@Temporal(TemporalType.DATE)
	@Column(name="UPDATE_DT")
	private Date updateDt;

	@Column(name="UPDATE_USER_ID")
	private String updateUserId;

	@Column(name="VALID_SIGN")
	private String validSign;

	public OrmCode() {
	}

	public String getCodeId() {
		return this.codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	public String getCodeDesc() {
		return this.codeDesc;
	}

	public void setCodeDesc(String codeDesc) {
		this.codeDesc = codeDesc;
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

	public String getIsRight() {
		return this.isRight;
	}

	public void setIsRight(String isRight) {
		this.isRight = isRight;
	}

	public BigDecimal getOrderNumber() {
		return this.orderNumber;
	}

	public void setOrderNumber(BigDecimal orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getParentCodeId() {
		return this.parentCodeId;
	}

	public void setParentCodeId(String parentCodeId) {
		this.parentCodeId = parentCodeId;
	}

	public String getRootCodeId() {
		return this.rootCodeId;
	}

	public void setRootCodeId(String rootCodeId) {
		this.rootCodeId = rootCodeId;
	}

	public String getSystemId() {
		return this.systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
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