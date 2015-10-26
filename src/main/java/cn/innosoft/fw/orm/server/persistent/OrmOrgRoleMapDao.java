package cn.innosoft.fw.orm.server.persistent;

import java.util.List;

import cn.innosoft.fw.biz.core.persistent.BaseDao;
import cn.innosoft.fw.orm.server.model.OrmOrgRoleMap;

public interface OrmOrgRoleMapDao extends BaseDao<OrmOrgRoleMap, String> {
	public List<OrmOrgRoleMap> findByOrgId(String orgId);

	public List<OrmOrgRoleMap> findByRoleId(String roleId);

	public Long deleteByOrgId(String orgId);

	public Long deleteByRoleId(String roleId);

	public Long deleteBySystemId(String systemId);
}
