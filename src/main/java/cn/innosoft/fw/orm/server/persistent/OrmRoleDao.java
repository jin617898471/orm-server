package cn.innosoft.fw.orm.server.persistent;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import cn.innosoft.fw.biz.core.persistent.BaseDao;
import cn.innosoft.fw.orm.server.model.OrmRole;

public interface OrmRoleDao extends BaseDao<OrmRole, String> {
	public Long deleteBySystemId(String systemId);

	public OrmRole findByRoleId(String id);

	public List<OrmRole> findBySystemId(String systemId);
	
	@Query(value="select ROLE_ID from ORM_ROLE where VALID_SIGN = 'Y'",nativeQuery=true)
	public List<String> findRoleIds();
	
	@Query("select distinct r from OrmRole r, OrmUserRoleMap m where r.roleId=m.roleId and m.userId=?1")
	List<OrmRole> getRoleByUserid(String userId);
	
}
