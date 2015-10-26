package cn.innosoft.fw.orm.server.persistent;

import java.util.List;

import cn.innosoft.fw.biz.core.persistent.BaseDao;
import cn.innosoft.fw.orm.server.model.OrmRoleResourceRight;

public interface OrmRoleResourceRightDao extends BaseDao<OrmRoleResourceRight, String> {
	public List<OrmRoleResourceRight> findByRoleId(String roleId);

	public Long deleteByRoleId(String roleId);

	public List<OrmRoleResourceRight> findByResourceId(String resourceId);

	public List<OrmRoleResourceRight> findByRoleIdAndResourceId(String roleId, String resourceId);

	public Long deleteByResourceId(String resourceId);

	public Long deleteBySystemId(String systemId);
}
