package cn.innosoft.fw.orm.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.innosoft.fw.biz.base.querycondition.FilterGroup;
import cn.innosoft.fw.biz.base.querycondition.QueryConditionHelper;
import cn.innosoft.fw.biz.core.persistent.BaseDao;
import cn.innosoft.fw.biz.core.service.AbstractBaseService;
import cn.innosoft.fw.orm.server.model.OrmRoleResourceRight;
import cn.innosoft.fw.orm.server.persistent.OrmRoleResourceRightDao;

@Service("ormRoleRresourceMapService")
public class OrmRoleResourceRightService extends AbstractBaseService<OrmRoleResourceRight, String> {

	@Autowired
	private OrmRoleResourceRightDao ormRoleResourceRightDao;
	@Override
	public BaseDao<OrmRoleResourceRight, String> getBaseDao() {
		return ormRoleResourceRightDao;
	}
	
	public List<OrmRoleResourceRight> getRolesByRoleId(String roleId) {
		FilterGroup filtergroup = QueryConditionHelper.add(new String[] { "roleId" }, new String[] { roleId });
		List<OrmRoleResourceRight> list = getBaseDao().findAll(filtergroup);// 该角色下的所有资源权限
		return list;
	}
	
}
