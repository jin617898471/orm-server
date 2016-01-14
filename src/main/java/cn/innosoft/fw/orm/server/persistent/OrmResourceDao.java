package cn.innosoft.fw.orm.server.persistent;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.innosoft.fw.biz.core.persistent.BaseDao;
import cn.innosoft.fw.orm.server.model.OrmResource;

public interface OrmResourceDao extends BaseDao<OrmResource, String> {

	public void deleteBySystemId(String systemId);

	@Query(value = "SELECT * FROM ORM_RESOURCE WHERE SYSTEM_ID IN( ?1 ) CONNECT BY PRIOR RESOURCE_ID = PARENT_RES_ID START WITH PARENT_RES_ID = 'ROOT' ORDER BY ORDER_NUMBER ASC", nativeQuery = true)
	public List<OrmResource> findAllBySystemId(List<String> rightsytem);

	@Query(value = "SELECT A.* FROM ORM_RESOURCE A ,ORM_ROLE_RESOURCE_RIGHT_VIEW B ,ORM_USER_ROLE_MAP_VIEW C WHERE A.RESOURCE_ID = B.RESOURCE_ID AND B.ROLE_ID=C.ROLE_ID AND C.USER_ID= ?1  connect by prior a.resource_id = a.parent_res_id start with a.parent_res_id='ROOT' ORDER BY ORDER_NUMBER ASC", nativeQuery = true)
	public List<OrmResource> findUserResource(String userId);

	@Query(value = "SELECT A.* FROM ORM_RESOURCE A ,ORM_ROLE_RESOURCE_RIGHT_VIEW B ,ORM_ORG_ROLE_MAP_VIEW C WHERE A.RESOURCE_ID = B.RESOURCE_ID AND B.ROLE_ID=C.ROLE_ID AND C.ORG_ID= ?1  connect by prior a.resource_id = a.parent_res_id start with a.parent_res_id='ROOT' ORDER BY ORDER_NUMBER ASC ", nativeQuery = true)
	public List<OrmResource> findOrgResource(String orgId);

	@Query(value = "SELECT * FROM ORM_RESOURCE CONNECT BY PRIOR RESOURCE_ID=  PARENT_RES_ID START WITH RESOURCE_ID IN (?1)", nativeQuery = true)
	public List<OrmResource> getAllChildrenNode(List<String> resourceId);

	@Query(value = "select new cn.innosoft.fw.orm.server.model.OrmResource(max(orderNumber)) from OrmResource where parentResId=?1", nativeQuery = false)
	public OrmResource getMaxOrderNumber(String parentResId);

	@Modifying
	@Query(value = "update ORM_resource set order_number=order_number-1 where ORDER_NUMBER >?1 and ORDER_NUMBER<?2 and PARENT_res_ID=?3 ", nativeQuery = true)
	public void substractOrderNumber(BigDecimal sourorder, BigDecimal tarorder, String pid);

	@Modifying
	@Query(value = "update ORM_resource set order_number=order_number+1 where ORDER_NUMBER >?1 and ORDER_NUMBER<?2 and PARENT_res_ID=?3 ", nativeQuery = true)
	public void addOrderNumber(BigDecimal sourorder, BigDecimal tarorder, String pid);

	@Modifying
	@Query(value = "update ORM_resource set order_number=?1 where resource_ID=?2", nativeQuery = true)
	public void updateOrderNumber(BigDecimal subtract, String sourceId);

}
