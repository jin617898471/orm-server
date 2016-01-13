package cn.innosoft.fw.orm.server.model;

import java.io.Serializable;

public class OrmOrgCodeRightViewPk implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codeId;
	private String orgId;

	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
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
			return this.codeId.equals(((OrmOrgCodeRightViewPk) obj).getCodeId())
					&& this.orgId.equals(((OrmOrgCodeRightViewPk) obj).getOrgId());
		}
		return false;
	}

	@Override
	public String toString() {
		return "OrmOrgCodeRightViewPk [codeId=" + codeId + ", orgId=" + orgId + "]";
	}

}
