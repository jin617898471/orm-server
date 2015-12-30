package cn.innosoft.fw.orm.server.model;

import java.io.Serializable;

public class OrmUserRoleMapViewPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String orgId;

	private String roleId;

	private String userId;

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
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
		if (obj instanceof OrmUserRoleMapViewPK) {
			return this.orgId.equals(((OrmUserRoleMapViewPK) obj).getOrgId())
					&& this.roleId.equals(((OrmUserRoleMapViewPK) obj).getRoleId())
					&& this.userId.equals(((OrmUserRoleMapViewPK) obj).getRoleId());
		}
		return false;
	}
}
