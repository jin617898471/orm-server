package cn.innosoft.fw.orm.server.model;

import java.io.Serializable;

public class OrmUserOrgRightViewPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String orgId;

	private String orgRightId;

	private String userId;

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgRightId() {
		return orgRightId;
	}

	public void setOrgRightId(String orgRightId) {
		this.orgRightId = orgRightId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof OrmUserOrgRightViewPK) {
			return this.orgId.equals(((OrmUserOrgRightViewPK) obj).getOrgId())
					&& this.orgRightId.equals(((OrmUserOrgRightViewPK) obj).getOrgRightId())
					&& this.userId.equals(((OrmUserOrgRightViewPK) obj).getUserId());
		}
		return false;
	}
}
