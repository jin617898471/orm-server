package cn.innosoft.fw.orm.server.persistent;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import cn.innosoft.fw.biz.core.persistent.BaseDao;
import cn.innosoft.fw.orm.server.model.OrmResource;

public interface OrmResourceDao extends BaseDao<OrmResource, String> {


	public void deleteBySystemId(String systemId);
	
	@Query(value = "SELECT * FROM ORM_RESOURCE WHERE SYSTEM_ID IN( ?1 ) CONNECT BY PRIOR RESOURCE_ID = PARENT_RES_ID START WITH PARENT_RES_ID = 'ROOT'",nativeQuery=true)
	public List<OrmResource> findAllBySystemId(List<String> rightsytem);

	@Query(value = "SELECT A.* FROM ORM_RESOURCE A ,ORM_ROLE_RESOURCE_RIGHT_VIEW B ,ORM_USER_ROLE_MAP_VIEW C WHERE A.RESOURCE_ID = B.RESOURCE_ID AND B.ROLE_ID=C.ROLE_ID AND C.USER_ID= ?1 ",nativeQuery=true)
	public List<OrmResource> findUserResource(String userId);

	@Query(value = "SELECT A.* FROM ORM_RESOURCE A ,ORM_ROLE_RESOURCE_RIGHT_VIEW B ,ORM_ORG_ROLE_MAP_VIEW C WHERE A.RESOURCE_ID = B.RESOURCE_ID AND B.ROLE_ID=C.ROLE_ID AND C.ORG_ID= ?1 ",nativeQuery=true)
	public List<OrmResource> findOrgResource(String orgId);

	@Query(value = "SELECT * FROM ORM_RESOURCE CONNECT BY PRIOR RESOURCE_ID=  PARENT_RES_ID START WITH RESOURCE_ID IN (?1)",nativeQuery=true)
	public List<OrmResource> getAllChildrenNode(List<String>  resourceId);

}
