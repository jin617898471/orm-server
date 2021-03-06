package cn.innosoft.fw.orm.server.persistent;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import cn.innosoft.fw.biz.core.persistent.BaseDao;
import cn.innosoft.fw.orm.server.model.OrmOrganization;

public interface OrmOrganizationDao extends BaseDao<OrmOrganization, String> {

	// public List<OrmOrganization> findByOrgIdIn(Collection<String> orgIds);
	//
	// @Query("select o.orgName from OrmOrganization o where o.orgId in (?1)")
	// List<String> getOrgNameById(List<String> orgIds);
	//
	// @Query("select o from OrmOrganization o, OrmOrgUserMap m where
	// o.orgId=m.orgId and m.userId=?1")
	// List<OrmOrganization> getOrgByUserId(String userId);
	//
	@Query(value = "SELECT DISTINCT * FROM ORM_ORGANIZATION START WITH ORG_ID"
			+ "=?1 CONNECT BY PRIOR ORG_ID=PARENT_ORG_ID", nativeQuery = true)
	List<OrmOrganization> getOrgByParentOrg(String orgId);

	@Query(value = "SELECT DISTINCT * FROM ORM_ORGANIZATION  WHERE ORG_TYPE=?1 START WITH ORG_ID"
			+ "=?2 CONNECT BY PRIOR ORG_ID=PARENT_ORG_ID", nativeQuery = true)
	List<OrmOrganization> getOrgByParentOrgAndOrgType(String orgType, String orgId);

	List<OrmOrganization> findByOrgType(String orgType);

	List<OrmOrganization> findByParentOrgId(String parentOrgId);

	@Query(value = "from OrmOrganization o where o.parentOrgId=?1 and o.orgType<>?2")
	List<OrmOrganization> getChildDeps(String parentOrgId, String orgType);

	List<OrmOrganization> findByOrgTypeAndParentOrgId(String orgType, String parentOrgId);
}
