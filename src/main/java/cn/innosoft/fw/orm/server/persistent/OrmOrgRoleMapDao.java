package cn.innosoft.fw.orm.server.persistent;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import cn.innosoft.fw.biz.core.persistent.BaseDao;
import cn.innosoft.fw.orm.server.model.OrmOrgRoleMap;
import cn.innosoft.fw.orm.server.model.OrmRole;

public interface OrmOrgRoleMapDao extends BaseDao<OrmOrgRoleMap, String> {
	public List<OrmOrgRoleMap> findByOrgId(String orgId);
	
	List<String> findByOrgIdIn(List<String> orgIds);

	public List<OrmOrgRoleMap> findByRoleId(String roleId);

	public Long deleteByOrgId(String orgId);

	public Long deleteByRoleId(String roleId);

	public Long deleteBySystemId(String systemId);
	
	Long deleteByOrgIdAndRoleId(String orgId,String roleId);

	@Query(value="select r.* from ORM_ORG_ROLE_MAP m,ORM_ROLE r where m.ORG_ID=?1 and m.ROLE_ID = r.ROLE_ID",nativeQuery=true)
	public List<OrmRole> findRoleByOrgId(String orgId);

	public void deleteByRoleIdIn(ArrayList<String> idArray);

	Long deleteByOrgIdAndRoleIdIn(String orgId, List<String> roleIds);
}
