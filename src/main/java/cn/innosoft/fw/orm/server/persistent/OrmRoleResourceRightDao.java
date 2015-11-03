package cn.innosoft.fw.orm.server.persistent;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.innosoft.fw.biz.core.persistent.BaseDao;
import cn.innosoft.fw.orm.server.model.OrmRoleResourceRight;

public interface OrmRoleResourceRightDao extends BaseDao<OrmRoleResourceRight, String> {
	public List<OrmRoleResourceRight> findByRoleId(String roleId);

	public Long deleteByRoleId(String roleId);

	public List<OrmRoleResourceRight> findByResourceId(String resourceId);

	public List<OrmRoleResourceRight> findByRoleIdAndResourceId(String roleId, String resourceId);

	public Long deleteByResourceId(String resourceId);

	public Long deleteBySystemId(String systemId);

	//@Query(value="delete from ORM_ROLE_RESOURCE_RIGHT where ROLE_Id=?1 and RESOURCE_Id=?2",nativeQuery=true)
	public Long deleteByRoleIdAndResourceId(String roleId, String resourceId);

	@Modifying
	@Query(value="update ORM_ROLE_RESOURCE_RIGHT set HALF_SELECT=?3 where ROLE_ID=?1 and RESOURCE_ID=?2",nativeQuery=true)
	public void updateHalfSelect(String roleId,String parentResId, String halfSelect);
		
}
