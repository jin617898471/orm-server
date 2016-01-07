package cn.innosoft.fw.orm.server.persistent;

import java.util.ArrayList;

import cn.innosoft.fw.biz.core.persistent.BaseDao;
import cn.innosoft.fw.orm.server.model.OrmUserRoleMap;

public interface OrmUserRoleMapDao extends BaseDao<OrmUserRoleMap, String> {

	void deleteByRoleId(String id);
	// public List<OrmUserRoleMap> findByUserIdAndRoleId(String userId, String
	// roleId);
	//
	// public List<OrmUserRoleMap> findByUserIdAndOrgId(String userId, String
	// orgId);
	//
	// public List<OrmUserRoleMap> findByUserId(String userId);
	//
	// public List<OrmUserRoleMap> findByRoleId(String roleId);
	//
	// @Modifying
	// @Query(value = "delete OrmUserRoleMap where userId=?1 and roleId=?2 and
	// mapType=?3")
	// public void deleteByUserIdRoleIdAndType(String userId, String roleId,
	// String mapType);
	//
	// public Long deleteByUserIdAndOrgId(String userId, String orgId);
	//
	// public Long deleteByUserIdAndRoleIdAndMapType(String userId, String
	// roleId,String mapType);
	//
	// public Long deleteByUserIdAndRoleIdAndOrgId(String userId, String
	// roleId,String orgId);
	// public Long deleteByUserId(String userId);
	//
	// public Long deleteByUserIdIn(List<String> userIds);
	// public Long deleteByRoleId(String roleId);
	//
	// public Long deleteBySystemId(String systemId);
	//
	// /**
	// * 根据用户Id查找系统Id
	// * @param userId
	// * @return
	// */
	// @Query(value="select system_Id from Orm_User_Role_Map where
	// user_Id=1?",nativeQuery=true)
	// public List<String> findSystemIdByUserId(String userId);
	//
	// @Query("select m.mapType,o from OrmUserRoleMap m,OrmRole o where m.roleId
	// = o.roleId and m.userId=?1")
	// public List<Object[]> findMapeTypeAndRole(String userId);

	void deleteByRoleIdIn(ArrayList<String> idArray);

	void deleteBySystemId(String systemId);
}
