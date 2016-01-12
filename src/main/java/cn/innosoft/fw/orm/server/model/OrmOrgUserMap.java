package cn.innosoft.fw.orm.server.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * The persistent class for the ORM_ORG_USER_MAP database table.
 * 
 */
@Entity
@Table(name="ORM_ORG_USER_MAP")
@NamedQuery(name="OrmOrgUserMap.findAll", query="SELECT o FROM OrmOrgUserMap o")
public class OrmOrgUserMap implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@Column(name="ORDER_NUMBER")
	private BigDecimal orderNumber;

	@Column(name="ORG_ID")
	private String orgId;

	@Column(name="USER_ID")
	private String userId;

	public OrmOrgUserMap() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getOrderNumber() {
		return this.orderNumber;
	}

	public void setOrderNumber(BigDecimal orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}