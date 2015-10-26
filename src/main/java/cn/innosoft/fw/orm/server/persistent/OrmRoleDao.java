package cn.innosoft.fw.orm.server.persistent;

import cn.innosoft.fw.biz.core.persistent.BaseDao;
import cn.innosoft.fw.orm.server.model.OrmRole;

public interface OrmRoleDao extends BaseDao<OrmRole, String> {
	public Long deleteBySystemId(String systemId);
}
