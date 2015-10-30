package cn.innosoft.fw.orm.server.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.innosoft.fw.biz.core.persistent.BaseDao;
import cn.innosoft.fw.biz.core.service.AbstractBaseService;
import cn.innosoft.fw.orm.server.model.OrmOrgRoleMap;
import cn.innosoft.fw.orm.server.model.OrmOrgUserMap;
import cn.innosoft.fw.orm.server.model.OrmOrganization;
import cn.innosoft.fw.orm.server.model.OrmRole;
import cn.innosoft.fw.orm.server.model.ZtreeBean;
import cn.innosoft.fw.orm.server.persistent.OrmOrgRoleMapDao;
import cn.innosoft.fw.orm.server.persistent.OrmOrgUserMapDao;
import cn.innosoft.fw.orm.server.persistent.OrmOrganizationDao;
import cn.innosoft.fw.orm.server.persistent.OrmRoleDao;
import cn.innosoft.fw.orm.server.persistent.OrmUserRoleMapDao;

@Service
public class OrmOrganizationService extends AbstractBaseService<OrmOrganization, String> {

	private static final Logger logger = LogManager.getLogger(OrmUserService.class);
	@Autowired
	private OrmOrganizationDao ormOrganizationDao;

	@Autowired
	private OrmOrgUserMapDao ormOrgUserMapDao;

	@Autowired
	private OrmOrgRoleMapDao ormOrgRoleMapDao;

	@Autowired
	private OrmRoleDao ormRoleDao;

	@Autowired
	private OrmUserRoleMapDao ormUserRoleMapDao;

	@Autowired
	private OrmUserService ormUserService;

	@Override
	public BaseDao<OrmOrganization, String> getBaseDao() {
		return ormOrganizationDao;
	}

	/**
	 * 添加组织机构
	 * 
	 * @param org
	 */
	public void addOrganization(OrmOrganization org) {
		ormOrganizationDao.save(org);
	}

	/**
	 * 更新组织机构
	 * 
	 * @param org
	 */
	public void updateOrganization(OrmOrganization org) {
		ormOrganizationDao.update(org);
	}

	/**
	 * 删除组织机构
	 * 
	 * @param orgId
	 */
	public void deleteOrganization(String orgId) {
		ormOrganizationDao.delete(orgId);
		ormOrgUserMapDao.deleteByOrgId(orgId);
		ormOrgRoleMapDao.deleteByOrgId(orgId);
	}

	/**
	 * 生成ZTREE，权限建模的时候用
	 * 
	 * @param roleId
	 * @return
	 */
	public List<ZtreeBean> createZtree(String roleId) {
		List<ZtreeBean> result = new ArrayList<ZtreeBean>();
//		List<OrmOrganization> orgs = ormOrganizationDao.findAll();
//		List<OrmRoleOrgRight> rights = ormRoleOrgRightDao.findByRoleId(roleId);
//		for (OrmOrganization org : orgs) {
//			ZtreeBean node = orgToTreeNode(org);
//			String orgId = org.getOrgId();
//			for (OrmRoleOrgRight ror : rights) {
//				if (orgId.equals(ror.getOrgId()) && "N".equals(ror.getHalfSelect())) {
//					node.setChecked(true);
//				}
//			}
//			result.add(node);
//		}
		return result;
	}

	/**
	 * 将组织机构实体类转换为zTree的节点
	 * 
	 * @param org
	 * @return
	 */
	private ZtreeBean orgToTreeNode(OrmOrganization org) {
		ZtreeBean node = new ZtreeBean();
		node.setId(org.getOrgId());
		node.setpId(org.getParentOrgId());
		node.setName(org.getOrgName());
		boolean isParent = org.getIsLeaf() == "Y" ? false : true;
		node.setOpen(isParent);
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("oType", org.getOrgType());
		node.setAttributes(attributes);
		node.setNocheck(false);
		node.setChecked(false);
		return node;
	}

	/**
	 * 生成组织机构的下拉树
	 * 
	 * @return
	 */
//	public List<SelectTreeBean> createSelectTree() {
//		return LoginUserContext.getOrgTree();
//	}

	/**
	 * 关联用户组织机构
	 * 
	 * @param userId
	 * @param orgId
	 */
	public void connectOrgToUser(String userId, String orgId) {
		createOrgUserMap(userId, orgId);
		List<OrmOrgRoleMap> orms = ormOrgRoleMapDao.findByOrgId(orgId);
		if (orms.size() == 0) {
			return;
		}
		for(OrmOrgRoleMap orm : orms){
			ormUserService.createUserRoleMap(userId, orm.getRoleId(), orgId, orm.getSystemId());
		}
	}

	/**
	 * 创建用户组织机构关联
	 * 
	 * @param userId
	 * @param orgId
	 */
	private void createOrgUserMap(String userId, String orgId) {
		OrmOrgUserMap oum = new OrmOrgUserMap();
		oum.setOrgId(orgId);
		oum.setUserId(userId);
		ormOrgUserMapDao.save(oum);
	}

	/**
	 * 删除用户组织机构关联
	 * 
	 * @param userId
	 * @param orgId
	 */
	public void disconnectOrgToUser(String userId, String orgId) {
		ormOrgUserMapDao.deleteByUserIdAndOrgId(userId, orgId);
		List<OrmOrgRoleMap> orms = ormOrgRoleMapDao.findByOrgId(orgId);
		if (orms.size() == 0) {
			return;
		}
		for (OrmOrgRoleMap orm : orms) {
			ormUserRoleMapDao.deleteByUserIdRoleIdAndType(userId, orm.getRoleId(), "USER_ORG_TO_ROLE");
		}
	}

	/**
	 * 编辑组织机构和角色的关联
	 * 
	 * @param orgId
	 * @param roleIds
	 */
	public void editOrgRoleMap(String orgId, List<String> roleIds) {
		List<OrmOrgRoleMap> orgRoleMaps = ormOrgRoleMapDao.findByOrgId(orgId);
		List<OrmOrgUserMap> orgUserMaps = ormOrgUserMapDao.findByOrgId(orgId);
		for (OrmOrgRoleMap orm : orgRoleMaps) {
			String roleId = orm.getRoleId();
			if (roleIds.contains(roleId)) {
				roleIds.remove(roleId);
			} else {
				ormOrgRoleMapDao.delete(orm);
				for (OrmOrgUserMap oum : orgUserMaps) {
					ormUserRoleMapDao.deleteByUserIdRoleIdAndType(oum.getUserId(), roleId, "USER_ORG_TO_ROLE");
				}
			}
		}
		for (String roleId : roleIds) {
			OrmRole role = ormRoleDao.findOne(roleId);
			createOrgRoleMap(orgId, role);
			for (OrmOrgUserMap oum : orgUserMaps) {
				ormUserService.createUserRoleMap(oum.getUserId(), role.getRoleId(), orgId, role.getSystemId());
			}
		}
	}

	/**
	 * 创建组织机构角色关联
	 * 
	 * @param orgId
	 * @param role
	 */
	public void createOrgRoleMap(String orgId, OrmRole role) {
		OrmOrgRoleMap orm = new OrmOrgRoleMap();
		orm.setOrgId(orgId);
		orm.setRoleId(role.getRoleId());
		orm.setSystemId(role.getSystemId());
		ormOrgRoleMapDao.save(orm);
	}
}
