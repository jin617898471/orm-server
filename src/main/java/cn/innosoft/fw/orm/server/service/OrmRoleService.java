package cn.innosoft.fw.orm.server.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.innosoft.fw.biz.base.querycondition.FilterGroup;
import cn.innosoft.fw.biz.base.querycondition.QueryConditionHelper;
import cn.innosoft.fw.biz.base.web.PageRequest;
import cn.innosoft.fw.biz.base.web.PageResponse;
import cn.innosoft.fw.biz.core.persistent.BaseDao;
import cn.innosoft.fw.biz.core.service.AbstractBaseService;
import cn.innosoft.fw.orm.server.model.OrmOrgRoleMap;
import cn.innosoft.fw.orm.server.model.OrmOrgUserMap;
import cn.innosoft.fw.orm.server.model.OrmResource;
import cn.innosoft.fw.orm.server.model.OrmRole;
import cn.innosoft.fw.orm.server.model.OrmRoleResourceRight;
import cn.innosoft.fw.orm.server.persistent.OrmOrgRoleMapDao;
import cn.innosoft.fw.orm.server.persistent.OrmOrgUserMapDao;
import cn.innosoft.fw.orm.server.persistent.OrmRoleDao;
import cn.innosoft.fw.orm.server.persistent.OrmRoleResourceRightDao;
import cn.innosoft.fw.orm.server.persistent.OrmUserRoleMapDao;

@Service
public class OrmRoleService extends AbstractBaseService<OrmRole, String> {

	@Autowired
	private OrmRoleDao ormRoleDao;
	@Autowired
	private OrmOrgRoleMapDao ormOrgRoleMapDao;
	@Autowired
	private OrmRoleResourceRightDao ormRoleResourceRightDao;
	@Autowired
	private OrmUserRoleMapDao ormUserRoleMapDao;
	@Autowired
	private OrmOrgUserMapDao ormOrgUserMapDao;
	@Autowired
	private OrmResourceService ormResourceService;
	@Autowired
	private OrmCodeService ormCodeService;
	@Autowired
	private OrmOrganizationService ormOrganizationService;
	@Autowired
	private OrmUserService ormUserService;

	@Override
	public BaseDao<OrmRole, String> getBaseDao() {
		return ormRoleDao;
	}

	/**
	 * 添加角色
	 * @param ormRole
	 */
	public void addRole(OrmRole ormRole) {
		ormRoleDao.save(ormRole);
	}

	public void updateRole(OrmRole ormRole) {
		ormRoleDao.update(ormRole);
	}

	/**
	 * 删除角色和对应的其他表中记录
	 * @param roleId
	 */
	public void deleteRole(String roleId) {
		ormRoleDao.delete(roleId);
		ormOrgRoleMapDao.deleteByRoleId(roleId);
		ormRoleResourceRightDao.deleteByRoleId(roleId);
		ormUserRoleMapDao.deleteByRoleId(roleId);
	}

	public void editRoleResourceRight(Map<String, OrmResource> map, String roleId) {
		List<OrmRoleResourceRight> rrrs = ormRoleResourceRightDao.findByRoleId(roleId);
		for (OrmRoleResourceRight rrr : rrrs) {
			if (map.containsKey(rrr.getResourceId())) {
				map.remove(rrr.getResourceId());
			} else {
				ormRoleResourceRightDao.delete(rrr);
			}
		}
		for (OrmResource res : map.values()) {
			String parentResId = res.getParentResId();
			List<OrmRoleResourceRight> list = ormRoleResourceRightDao.findByResourceId(parentResId);
			if(list.size()==0){
				ormResourceService.createOrmRoleResourceRight(roleId, parentResId, "Y", res.getSystemId());
			}
			ormResourceService.createOrmRoleResourceRight(roleId, res.getResourceId(), "N", res.getSystemId());
		}
	}

	public void editRoleOrgMap(List<String> orgIds, OrmRole role) {
		String roleId = role.getRoleId();
		List<OrmOrgRoleMap> maps = ormOrgRoleMapDao.findByRoleId(roleId);
		for (OrmOrgRoleMap orm : maps) {
			String orgId = orm.getOrgId();
			if (orgIds.contains(orgId)) {
				orgIds.remove(orgId);
			} else {
				ormOrgRoleMapDao.delete(orm);
				List<OrmOrgUserMap> list = ormOrgUserMapDao.findByOrgId(orgId);
				for (OrmOrgUserMap oum : list) {
					ormUserRoleMapDao.deleteByUserIdRoleIdAndType(oum.getUserId(), roleId, "USER_ORG_TO_ROLE");
				}
			}
		}
		for (String orgId : orgIds) {
			ormOrganizationService.createOrgRoleMap(orgId, role);
			List<OrmOrgUserMap> list = ormOrgUserMapDao.findByOrgId(orgId);
			for (OrmOrgUserMap oum : list) {
				ormUserService.createUserRoleMap(oum.getUserId(), role.getRoleId(), orgId, role.getSystemId());
			}
		}
	}

	public void addRoleUserMap(String userId, OrmRole role) {
		ormUserService.createUserRoleMap(userId, role.getRoleId(), null, role.getSystemId());
	}

	public void deleteRoleUserMap(String userId, String roleId) {
		ormUserRoleMapDao.deleteByUserIdAndRoleId(userId, roleId);
	}

	public OrmRole findByRoleId(String id) {
		return ormRoleDao.findByRoleId(id);
	}

	/**
	 * 批量删除
	 * @param idArray
	 */
	public void deleteByIds(ArrayList<String> idArray) {
		for (String string : idArray) {
			deleteRole(string);
		}
	}

	/**
	 * 分页查询有效数据
	 * @param pageRequest
	 * @return
	 */
	public PageResponse<OrmRole> findValid(PageRequest pageRequest) {
		FilterGroup group = QueryConditionHelper.add(pageRequest.getFilterGroup(), new String[] { "validSign" },
				new String[] { "Y" }, new String[] { "equal" });
		PageResponse<OrmRole> page = findAll(group, pageRequest);
		return page;
	}
	
	public List<String> findValidRoleIdList() {
		return ormRoleDao.findRoleIds();
	}

	public List<OrmRole> findRoleBySystemId(String systemId) {
		return ormRoleDao.findBySystemId(systemId);
	}

}
