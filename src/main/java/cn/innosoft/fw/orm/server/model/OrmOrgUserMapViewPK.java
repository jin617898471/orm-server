package cn.innosoft.fw.orm.server.model;

import java.io.Serializable;

public class OrmOrgUserMapViewPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String userId;

	private String orgId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof OrmOrgUserMapViewPK) {
			return this.userId.equals(((OrmOrgUserMapViewPK) obj).getUserId())
					&& this.orgId.equals(((OrmOrgUserMapViewPK) obj).getOrgId());
		}
		return false;
	}
}
