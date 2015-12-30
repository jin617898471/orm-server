package cn.innosoft.fw.orm.server.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ORM_ORGANIZATION database table.
 * 
 */
@Entity
@Table(name="ORM_ORGANIZATION")
@NamedQuery(name="OrmOrganization.findAll", query="SELECT o FROM OrmOrganization o")
public class OrmOrganization implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ORG_ID")
	private String orgId;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATE_DT")
	private Date createDt;

	@Column(name="CREATE_USER_ID")
	private String createUserId;

	@Column(name="ORDER_NUMBER")
	private BigDecimal orderNumber;

	@Column(name="ORG_ADDRESS")
	private String orgAddress;

	@Column(name="ORG_AREA")
	private String orgArea;

	@Column(name="ORG_CODE")
	private String orgCode;

	@Column(name="ORG_DEGREE")
	private String orgDegree;

	@Column(name="ORG_DESC")
	private String orgDesc;

	@Column(name="ORG_EMAIL")
	private String orgEmail;

	@Column(name="ORG_LEVEL")
	private String orgLevel;

	@Column(name="ORG_LINKMAN")
	private String orgLinkman;

	@Column(name="ORG_NAME")
	private String orgName;

	@Column(name="ORG_NAME_SHORT")
	private String orgNameShort;

	@Column(name="ORG_PHONE")
	private String orgPhone;

	@Column(name="ORG_POSTCODE")
	private String orgPostcode;

	@Column(name="ORG_TYPE")
	private String orgType;

	@Column(name="ORG_WEBURL")
	private String orgWeburl;

	@Column(name="PARENT_ORG_ID")
	private String parentOrgId;

	@Column(name="ROOT_ORG_ID")
	private String rootOrgId;

	@Temporal(TemporalType.DATE)
	@Column(name="UPDATE_DT")
	private Date updateDt;

	@Column(name="UPDATE_USER_ID")
	private String updateUserId;

	@Column(name="VALID_SIGN")
	private String validSign;

	public OrmOrganization() {
	}

	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
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

	public BigDecimal getOrderNumber() {
		return this.orderNumber;
	}

	public void setOrderNumber(BigDecimal orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getOrgAddress() {
		return this.orgAddress;
	}

	public void setOrgAddress(String orgAddress) {
		this.orgAddress = orgAddress;
	}

	public String getOrgArea() {
		return this.orgArea;
	}

	public void setOrgArea(String orgArea) {
		this.orgArea = orgArea;
	}

	public String getOrgCode() {
		return this.orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgDegree() {
		return this.orgDegree;
	}

	public void setOrgDegree(String orgDegree) {
		this.orgDegree = orgDegree;
	}

	public String getOrgDesc() {
		return this.orgDesc;
	}

	public void setOrgDesc(String orgDesc) {
		this.orgDesc = orgDesc;
	}

	public String getOrgEmail() {
		return this.orgEmail;
	}

	public void setOrgEmail(String orgEmail) {
		this.orgEmail = orgEmail;
	}

	public String getOrgLevel() {
		return this.orgLevel;
	}

	public void setOrgLevel(String orgLevel) {
		this.orgLevel = orgLevel;
	}

	public String getOrgLinkman() {
		return this.orgLinkman;
	}

	public void setOrgLinkman(String orgLinkman) {
		this.orgLinkman = orgLinkman;
	}

	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgNameShort() {
		return this.orgNameShort;
	}

	public void setOrgNameShort(String orgNameShort) {
		this.orgNameShort = orgNameShort;
	}

	public String getOrgPhone() {
		return this.orgPhone;
	}

	public void setOrgPhone(String orgPhone) {
		this.orgPhone = orgPhone;
	}

	public String getOrgPostcode() {
		return this.orgPostcode;
	}

	public void setOrgPostcode(String orgPostcode) {
		this.orgPostcode = orgPostcode;
	}

	public String getOrgType() {
		return this.orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getOrgWeburl() {
		return this.orgWeburl;
	}

	public void setOrgWeburl(String orgWeburl) {
		this.orgWeburl = orgWeburl;
	}

	public String getParentOrgId() {
		return this.parentOrgId;
	}

	public void setParentOrgId(String parentOrgId) {
		this.parentOrgId = parentOrgId;
	}

	public String getRootOrgId() {
		return this.rootOrgId;
	}

	public void setRootOrgId(String rootOrgId) {
		this.rootOrgId = rootOrgId;
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