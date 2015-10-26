package cn.innosoft.fw.orm.server.service;

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
import cn.innosoft.fw.orm.server.model.OrmCode;
import cn.innosoft.fw.orm.server.model.OrmOrgRoleMap;
import cn.innosoft.fw.orm.server.model.OrmOrgUserMap;
import cn.innosoft.fw.orm.server.model.OrmOrganization;
import cn.innosoft.fw.orm.server.model.OrmResource;
import cn.innosoft.fw.orm.server.model.OrmRole;
import cn.innosoft.fw.orm.server.model.OrmRoleCodeRight;
import cn.innosoft.fw.orm.server.model.OrmRoleOrgRight;
import cn.innosoft.fw.orm.server.model.OrmRoleResourceRight;
import cn.innosoft.fw.orm.server.persistent.OrmOrgRoleMapDao;
import cn.innosoft.fw.orm.server.persistent.OrmOrgUserMapDao;
import cn.innosoft.fw.orm.server.persistent.OrmRoleCodeRightDao;
import cn.innosoft.fw.orm.server.persistent.OrmRoleDao;
import cn.innosoft.fw.orm.server.persistent.OrmRoleOrgRightDao;
import cn.innosoft.fw.orm.server.persistent.OrmRoleResourceRightDao;
import cn.innosoft.fw.orm.server.persistent.OrmUserRoleMapDao;

@Service
public class OrmRoleService extends AbstractBaseService<OrmRole, String> {

	@Autowired
	private OrmRoleDao ormRoleDao;
	@Autowired
	private OrmOrgRoleMapDao ormOrgRoleMapDao;
	@Autowired
	private OrmRoleCodeRightDao ormRoleCodeRightDao;
	@Autowired
	private OrmRoleOrgRightDao ormRoleOrgRightDao;
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

	public PageResponse<OrmRole> find(PageRequest pageRequest) {
		FilterGroup group = QueryConditionHelper.add(pageRequest.getFilterGroup(), new String[] { "validSign" },
				new String[] { "Y" }, new String[] { "equal" });
		PageResponse<OrmRole> page = findAll(group, pageRequest);
		return page;
	}

	public void addRole(OrmRole ormRole) {
		ormRoleDao.save(ormRole);
	}

	public void updateRole(OrmRole ormRole) {
		ormRoleDao.update(ormRole);
	}

	public void deleteRole(String roleId) {
		ormRoleDao.delete(roleId);
		ormOrgRoleMapDao.deleteByRoleId(roleId);
		ormRoleCodeRightDao.deleteByRoleId(roleId);
		ormRoleOrgRightDao.deleteByRoleId(roleId);
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

	public void editRoleCodeRight(Map<String, OrmCode> map, String roleId) {
		List<OrmRoleCodeRight> rcrs = ormRoleCodeRightDao.findByRoleId(roleId);
		for (OrmRoleCodeRight rcr : rcrs) {
			if (map.containsKey(rcr.getResourceId())) {
				map.remove(rcr.getResourceId());
			} else {
				ormRoleCodeRightDao.delete(rcr);
			}
		}
		for (OrmCode code : map.values()) {
			String parentCodeId = code.getParentCodeId();
			List<OrmRoleCodeRight> list = ormRoleCodeRightDao.findByCodeId(parentCodeId);
			if(list.size() == 0){
				ormCodeService.createOrmRoleCodeRight(roleId, parentCodeId, "Y", "GLOBAL", null,
						code.getSystemId());
			}
			ormCodeService.createOrmRoleCodeRight(roleId, code.getCodeId(), "N", "GLOBAL", null,
					code.getSystemId());
		}
	}

	public void editRoleOrgRight(Map<String, OrmOrganization> map, OrmRole role) {
		String roleId = role.getRoleId();
		String systemId = role.getSystemId();
		List<OrmRoleOrgRight> rors = ormRoleOrgRightDao.findByRoleId(roleId);
		for (OrmRoleOrgRight ror : rors) {
			if (map.containsKey(ror.getResourceId())) {
				map.remove(ror.getResourceId());
			} else {
				ormRoleOrgRightDao.delete(ror);
			}
		}
		for (OrmOrganization org : map.values()) {
			String parentOrgId = org.getParentOrgId();
			List<OrmRoleOrgRight> list = ormRoleOrgRightDao.findByOrgId(parentOrgId);
			if(list.size() == 0){
				ormOrganizationService.createOrmRoleOrgRight(roleId, parentOrgId, "Y", "GLOBAL", null, systemId);
			}
			ormOrganizationService.createOrmRoleOrgRight(roleId, org.getOrgId(), "N", "GLOBAL", null, systemId);
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
}
