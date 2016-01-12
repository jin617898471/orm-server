package cn.innosoft.fw.orm.server.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the ORM_RESOURCE database table.
 * 
 */
@Entity
@Table(name="ORM_RESOURCE")
@NamedQuery(name="OrmResource.findAll", query="SELECT o FROM OrmResource o")
public class OrmResource implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="RESOURCE_ID")
	private String resourceId;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATE_DT")
	private Date createDt;

	@Column(name="CREATE_USER_ID")
	private String createUserId;

	@Column(name="ORDER_NUMBER")
	private BigDecimal orderNumber;

	@Column(name="PARENT_RES_ID")
	private String parentResId;

	@Column(name="RESOURCE_CODE")
	private String resourceCode;

	@Column(name="RESOURCE_DESC")
	private String resourceDesc;

	@Column(name="RESOURCE_NAME")
	private String resourceName;

	@Column(name="RESOURCE_OPEN_TYPE")
	private String resourceOpenType;

	@Column(name="RESOURCE_TYPE")
	private String resourceType;

	@Column(name="RESOURCE_URL")
	private String resourceUrl;

	@Column(name="ROOT_RES_ID")
	private String rootResId;

	@Column(name="SYSTEM_ID")
	private String systemId;

	@Temporal(TemporalType.DATE)
	@Column(name="UPDATE_DT")
	private Date updateDt;

	@Column(name="UPDATE_USER_ID")
	private String updateUserId;

	@Column(name="VALID_SIGN")
	private String validSign;

	public OrmResource() {
	}

	public String getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
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

	public String getParentResId() {
		return this.parentResId;
	}

	public void setParentResId(String parentResId) {
		this.parentResId = parentResId;
	}

	public String getResourceCode() {
		return this.resourceCode;
	}

	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}

	public String getResourceDesc() {
		return this.resourceDesc;
	}

	public void setResourceDesc(String resourceDesc) {
		this.resourceDesc = resourceDesc;
	}

	public String getResourceName() {
		return this.resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getResourceOpenType() {
		return this.resourceOpenType;
	}

	public void setResourceOpenType(String resourceOpenType) {
		this.resourceOpenType = resourceOpenType;
	}

	public String getResourceType() {
		return this.resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getResourceUrl() {
		return this.resourceUrl;
	}

	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}

	public String getRootResId() {
		return this.rootResId;
	}

	public void setRootResId(String rootResId) {
		this.rootResId = rootResId;
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