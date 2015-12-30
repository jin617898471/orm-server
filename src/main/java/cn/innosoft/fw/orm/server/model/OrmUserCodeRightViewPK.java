package cn.innosoft.fw.orm.server.model;

import java.io.Serializable;

public class OrmUserCodeRightViewPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String orgId;

	private String userId;

	private String codeId;

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof OrmUserCodeRightViewPK) {
			return this.orgId.equals(((OrmUserCodeRightViewPK) obj).getOrgId())
					&& this.userId.equals(((OrmUserCodeRightViewPK) obj).getUserId())
					&& this.codeId.equals(((OrmUserCodeRightViewPK) obj).getCodeId());
		}
		return false;
	}
}
