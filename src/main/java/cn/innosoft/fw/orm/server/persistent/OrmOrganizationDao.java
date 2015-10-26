package cn.innosoft.fw.orm.server.persistent;

import java.util.Collection;
import java.util.List;

import cn.innosoft.fw.biz.core.persistent.BaseDao;
import cn.innosoft.fw.orm.server.model.OrmOrganization;

public interface OrmOrganizationDao extends BaseDao<OrmOrganization, String> {

	public List<OrmOrganization> findByOrgIdIn(Collection<String> orgIds);
}
