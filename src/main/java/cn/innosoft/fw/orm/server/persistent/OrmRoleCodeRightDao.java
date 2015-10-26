package cn.innosoft.fw.orm.server.persistent;

import java.util.List;

import cn.innosoft.fw.biz.core.persistent.BaseDao;
import cn.innosoft.fw.orm.server.model.OrmRoleCodeRight;

public interface OrmRoleCodeRightDao extends BaseDao<OrmRoleCodeRight, String> {
	public Long deleteByResourceId(String resourceId);

	public Long deleteByCodeId(String codeId);

	public Long deleteByRoleId(String roleId);

	public List<OrmRoleCodeRight> findByCodeId(String codeId);

	public List<OrmRoleCodeRight> findByRoleId(String roleId);

	public List<OrmRoleCodeRight> findByCodeIdAndRoleId(String codeId, String RoleId);

	public Long deleteBySystemId(String systemId);
}
