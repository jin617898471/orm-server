package cn.innosoft.fw.orm.server.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


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
	@Column(name="USER_ID")
	private String userId;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATE_DT")
	private Date createDt;

	@Column(name="CREATE_USER_ID")
	private String createUserId;

	@Column(name="EMP_CODE")
	private String empCode;

	@Column(name="EMP_DEGREE")
	private String empDegree;

	@Column(name="EMP_FAXNO")
	private String empFaxno;

	@Column(name="EMP_HADDRESS")
	private String empHaddress;

	@Column(name="EMP_HTEL")
	private String empHtel;

	@Column(name="EMP_HZIPCODE")
	private String empHzipcode;

	@Column(name="EMP_ID")
	private String empId;

	@Temporal(TemporalType.DATE)
	@Column(name="EMP_INDATE")
	private Date empIndate;

	@Column(name="EMP_OADDRESS")
	private String empOaddress;

	@Column(name="EMP_OEMAIL")
	private String empOemail;

	@Column(name="EMP_OTEL")
	private String empOtel;

	@Temporal(TemporalType.DATE)
	@Column(name="EMP_OUTDATE")
	private Date empOutdate;

	@Column(name="EMP_OZIPCODE")
	private String empOzipcode;

	@Column(name="EMP_PARTY")
	private String empParty;

	@Column(name="EMP_QQ")
	private String empQq;

	@Column(name="EMP_REMARK")
	private String empRemark;

	@Column(name="EMP_STATUS")
	private String empStatus;

	@Column(name="EMP_WEIBO")
	private String empWeibo;

	@Temporal(TemporalType.DATE)
	@Column(name="UPDATE_DT")
	private Date updateDt;

	@Column(name="UPDATE_USER_ID")
	private String updateUserId;

	@Column(name="USER_ACCT")
	private String userAcct;

	@Temporal(TemporalType.DATE)
	@Column(name="USER_BIRTH")
	private Date userBirth;

	@Column(name="USER_CARDNO")
	private String userCardno;

	@Column(name="USER_CARDTYPE")
	private String userCardtype;

	@Column(name="USER_EMAIL")
	private String userEmail;

	@Column(name="USER_MOBILE")
	private String userMobile;

	@Column(name="USER_NAME")
	private String userName;

	@Column(name="USER_NAME_ENG")
	private String userNameEng;

	@Column(name="USER_PWD")
	private String userPwd;

	@Column(name="USER_SEX")
	private String userSex;

	@Column(name="USER_SOURCE")
	private String userSource;

	@Column(name="USER_STATUS")
	private String userStatus;

	@Column(name="VALID_SIGN")
	private String validSign;

	public OrmUser() {
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getEmpCode() {
		return this.empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getEmpDegree() {
		return this.empDegree;
	}

	public void setEmpDegree(String empDegree) {
		this.empDegree = empDegree;
	}

	public String getEmpFaxno() {
		return this.empFaxno;
	}

	public void setEmpFaxno(String empFaxno) {
		this.empFaxno = empFaxno;
	}

	public String getEmpHaddress() {
		return this.empHaddress;
	}

	public void setEmpHaddress(String empHaddress) {
		this.empHaddress = empHaddress;
	}

	public String getEmpHtel() {
		return this.empHtel;
	}

	public void setEmpHtel(String empHtel) {
		this.empHtel = empHtel;
	}

	public String getEmpHzipcode() {
		return this.empHzipcode;
	}

	public void setEmpHzipcode(String empHzipcode) {
		this.empHzipcode = empHzipcode;
	}

	public String getEmpId() {
		return this.empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public Date getEmpIndate() {
		return this.empIndate;
	}

	public void setEmpIndate(Date empIndate) {
		this.empIndate = empIndate;
	}

	public String getEmpOaddress() {
		return this.empOaddress;
	}

	public void setEmpOaddress(String empOaddress) {
		this.empOaddress = empOaddress;
	}

	public String getEmpOemail() {
		return this.empOemail;
	}

	public void setEmpOemail(String empOemail) {
		this.empOemail = empOemail;
	}

	public String getEmpOtel() {
		return this.empOtel;
	}

	public void setEmpOtel(String empOtel) {
		this.empOtel = empOtel;
	}

	public Date getEmpOutdate() {
		return this.empOutdate;
	}

	public void setEmpOutdate(Date empOutdate) {
		this.empOutdate = empOutdate;
	}

	public String getEmpOzipcode() {
		return this.empOzipcode;
	}

	public void setEmpOzipcode(String empOzipcode) {
		this.empOzipcode = empOzipcode;
	}

	public String getEmpParty() {
		return this.empParty;
	}

	public void setEmpParty(String empParty) {
		this.empParty = empParty;
	}

	public String getEmpQq() {
		return this.empQq;
	}

	public void setEmpQq(String empQq) {
		this.empQq = empQq;
	}

	public String getEmpRemark() {
		return this.empRemark;
	}

	public void setEmpRemark(String empRemark) {
		this.empRemark = empRemark;
	}

	public String getEmpStatus() {
		return this.empStatus;
	}

	public void setEmpStatus(String empStatus) {
		this.empStatus = empStatus;
	}

	public String getEmpWeibo() {
		return this.empWeibo;
	}

	public void setEmpWeibo(String empWeibo) {
		this.empWeibo = empWeibo;
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

	public String getUserAcct() {
		return this.userAcct;
	}

	public void setUserAcct(String userAcct) {
		this.userAcct = userAcct;
	}

	public Date getUserBirth() {
		return this.userBirth;
	}

	public void setUserBirth(Date userBirth) {
		this.userBirth = userBirth;
	}

	public String getUserCardno() {
		return this.userCardno;
	}

	public void setUserCardno(String userCardno) {
		this.userCardno = userCardno;
	}

	public String getUserCardtype() {
		return this.userCardtype;
	}

	public void setUserCardtype(String userCardtype) {
		this.userCardtype = userCardtype;
	}

	public String getUserEmail() {
		return this.userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserMobile() {
		return this.userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserNameEng() {
		return this.userNameEng;
	}

	public void setUserNameEng(String userNameEng) {
		this.userNameEng = userNameEng;
	}

	public String getUserPwd() {
		return this.userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getUserSex() {
		return this.userSex;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	public String getUserSource() {
		return this.userSource;
	}

	public void setUserSource(String userSource) {
		this.userSource = userSource;
	}

	public String getUserStatus() {
		return this.userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getValidSign() {
		return this.validSign;
	}

	public void setValidSign(String validSign) {
		this.validSign = validSign;
	}

}