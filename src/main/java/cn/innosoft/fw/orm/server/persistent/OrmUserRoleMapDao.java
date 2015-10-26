package cn.innosoft.fw.orm.server.persistent;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.innosoft.fw.biz.core.persistent.BaseDao;
import cn.innosoft.fw.orm.server.model.OrmUserRoleMap;

public interface OrmUserRoleMapDao extends BaseDao<OrmUserRoleMap, String> {
	public List<OrmUserRoleMap> findByUserIdAndRoleId(String userId, String roleId);

	public List<OrmUserRoleMap> findByUserIdAndOrgId(String userId, String orgId);

	public List<OrmUserRoleMap> findByUserId(String userId);

	public List<OrmUserRoleMap> findByRoleId(String roleId);

	@Modifying
	@Query(value = "delete OrmUserRoleMap where userId=?1 and roleId=?2 and mapType=?3")
	public void deleteByUserIdRoleIdAndType(String userId, String roleId, String mapType);

	public Long deleteByUserIdAndOrgId(String userId, String orgId);

	public Long deleteByUserIdAndRoleId(String userId, String roleId);

	public Long deleteByUserId(String userId);

	public Long deleteByRoleId(String roleId);

	public Long deleteBySystemId(String systemId);

}
