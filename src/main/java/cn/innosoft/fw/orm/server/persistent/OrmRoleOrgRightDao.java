package cn.innosoft.fw.orm.server.persistent;

import java.util.List;

import cn.innosoft.fw.biz.core.persistent.BaseDao;
import cn.innosoft.fw.orm.server.model.OrmRoleOrgRight;

public interface OrmRoleOrgRightDao extends BaseDao<OrmRoleOrgRight, String> {
	public List<OrmRoleOrgRight> findByOrgId(String orgId);

	public List<OrmRoleOrgRight> findByRoleId(String roleId);

	public Long deleteByOrgId(String orgId);

	public Long deleteByResourceId(String resourceId);

	public Long deleteByRoleId(String roleId);

	public Long deleteBySystemId(String systemId);
}
