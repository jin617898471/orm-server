package cn.innosoft.fw.orm.server.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;


/**
 * The persistent class for the ORM_USER database table.
 * 
 */
@Entity
@Table(name="ORM_USER")
@NamedQuery(name="OrmUser.findAll", query="SELECT o FROM OrmUser o")
public class OrmUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name="USER_ID")
	private String userId;

	@Column(name="CREATE_DT")
	private Date createDt;

	@Column(name="CREATE_USER_ID")
	private String createUserId;

	@Column(name="UPDATE_DT")
	private Date updateDt;

	@Column(name="UPDATE_USER_ID")
	private String updateUserId;

	@Column(name="USER_ACCT")
	private String userAcct;

	@Temporal(TemporalType.DATE)
	@Column(name="USER_BIRTH")
	private Date userBirth;

	@Column(name="USER_EMAIL")
	private String userEmail;

	@Column(name="USER_FAX")
	private String userFax;

	@Column(name="USER_IDENTITYCARD")
	private String userIdentitycard;

	@Column(name="USER_MOBILE")
	private String userMobile;

	@Column(name="USER_NAME")
	private String userName;

	@Column(name="USER_PWD")
	private String userPwd;

	@Column(name="USER_SEX")
	private String userSex;

	@Column(name="USER_TEL")
	private String userTel;

	@Column(name="VALID_SIGN")
	private String validSign;
	
	@Transient
	private String orgIds;

	public OrmUser() {
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public Date getUpdateDt() {
		return updateDt;
	}

	public void setUpdateDt(Date updateDt) {
		this.updateDt = updateDt;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getUserAcct() {
		return userAcct;
	}

	public void setUserAcct(String userAcct) {
		this.userAcct = userAcct;
	}

	public Date getUserBirth() {
		return userBirth;
	}

	public void setUserBirth(Date userBirth) {
		this.userBirth = userBirth;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserFax() {
		return userFax;
	}

	public void setUserFax(String userFax) {
		this.userFax = userFax;
	}

	public String getUserIdentitycard() {
		return userIdentitycard;
	}

	public void setUserIdentitycard(String userIdentitycard) {
		this.userIdentitycard = userIdentitycard;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getUserSex() {
		return userSex;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	public String getUserTel() {
		return userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	public String getValidSign() {
		return validSign;
	}

	public void setValidSign(String validSign) {
		this.validSign = validSign;
	}

	public String getOrgIds() {
		return orgIds;
	}

	public void setOrgIds(String orgIds) {
		this.orgIds = orgIds;
	}
	
}