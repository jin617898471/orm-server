package cn.innosoft.fw.orm.server.persistent;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import cn.innosoft.fw.biz.core.persistent.BaseDao;
import cn.innosoft.fw.orm.server.model.OrmRoleResourceRight;

public interface OrmRoleResourceRightDao extends BaseDao<OrmRoleResourceRight, String> {

	public void deleteByResourceId(String resourceId);
	
	public void deleteByResourceIdIn(ArrayList<String> idArray);

	public void deleteBySystemId(String systemId);

	public void deleteByRoleId(String id);

	public void deleteByRoleIdIn(ArrayList<String> idArray);

	@Query(value = "select role_id||resource_id as id ,role_id,resource_id,system_id from ORM_ROLE_RESOURCE_RIGHT_VIEW where role_id = ?1 ",nativeQuery=true)
	public List<OrmRoleResourceRight> findByRoleId(String roleId);

	public void deleteByRoleIdAndResourceIdIn(String roleId, List<String> needDeleteResourceIdList);
		
}
