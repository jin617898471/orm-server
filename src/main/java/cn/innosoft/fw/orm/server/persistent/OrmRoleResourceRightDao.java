package cn.innosoft.fw.orm.server.persistent;

import java.util.ArrayList;
import java.util.List;

import cn.innosoft.fw.biz.core.persistent.BaseDao;
import cn.innosoft.fw.orm.server.model.OrmRoleResourceRight;

public interface OrmRoleResourceRightDao extends BaseDao<OrmRoleResourceRight, String> {

	public void deleteByResourceId(String resourceId);
	
	public void deleteByResourceIdIn(ArrayList<String> idArray);

	public void deleteBySystemId(String systemId);

	public void deleteByRoleId(String id);

	public void deleteByRoleIdIn(ArrayList<String> idArray);

	public List<OrmRoleResourceRight> findByRoleId(String roleId);

	public void deleteByRoleIdAndResourceIdIn(String roleId, List<String> needDeleteResourceIdList);
		
}
