package cn.innosoft.fw.orm.server.persistent;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import cn.innosoft.fw.biz.core.persistent.BaseDao;
import cn.innosoft.fw.orm.server.model.OrmOrganization;

public interface OrmOrganizationDao extends BaseDao<OrmOrganization, String> {

	public List<OrmOrganization> findByOrgIdIn(Collection<String> orgIds);
	
	@Query("select o.orgName from OrmOrganization o where o.orgId in (?1)")
	List<String> getOrgNameById(List<String> orgIds);
}
