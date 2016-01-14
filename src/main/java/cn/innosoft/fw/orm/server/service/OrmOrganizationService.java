package cn.innosoft.fw.orm.server.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.innosoft.fw.biz.core.persistent.BaseDao;
import cn.innosoft.fw.biz.core.service.AbstractBaseService;
import cn.innosoft.fw.orm.server.model.OrmOrgCodeRightView;
import cn.innosoft.fw.orm.server.model.OrmOrgRoleMap;
import cn.innosoft.fw.orm.server.model.OrmOrgUserMap;
import cn.innosoft.fw.orm.server.model.OrmOrgUserMapView;
import cn.innosoft.fw.orm.server.model.OrmOrganization;
import cn.innosoft.fw.orm.server.model.OrmUser;
import cn.innosoft.fw.orm.server.model.ZtreeBean;
import cn.innosoft.fw.orm.server.persistent.OrmOrgCodeRightViewDao;
import cn.innosoft.fw.orm.server.persistent.OrmOrgRoleMapDao;
import cn.innosoft.fw.orm.server.persistent.OrmOrgUserMapDao;
import cn.innosoft.fw.orm.server.persistent.OrmOrgUserMapViewDao;
import cn.innosoft.fw.orm.server.persistent.OrmOrganizationDao;
import cn.innosoft.fw.orm.server.persistent.OrmUserRoleMapDao;

@Service
public class OrmOrganizationService extends AbstractBaseService<OrmOrganization, String> {

	private static final Logger logger = LogManager.getLogger(OrmOrganizationService.class);
	@Autowired
	private OrmOrganizationDao ormOrganizationDao;

	@Autowired
	private OrmOrgUserMapDao ormOrgUserMapDao;

	@Autowired
	private OrmOrgUserMapViewDao ormOrgUserMapViewDao;

	@Autowired
	private OrmOrgRoleMapDao ormOrgRoleMapDao;

	@Autowired
	private OrmUserRoleMapDao ormUserRoleMapDao;

	@Autowired
	private OrmOrgCodeRightViewDao ormOrgCodeRightViewDao;

	@Autowired
	private OrmUserService ormUserService;

	@Autowired
	private OrmCodeService ormCodeService;
	@Autowired
	private OrmRoleService ormRoleService;
	@Override
	public BaseDao<OrmOrganization, String> getBaseDao() {
		return ormOrganizationDao;
	}
	
	public List<ZtreeBean> getInstitutionTree() {
		List<ZtreeBean> list = new ArrayList<ZtreeBean>();
		ZtreeBean root = new ZtreeBean();
		root.setId("ROOT");
		root.setName("ROOT");
		List<OrmOrganization> insts = ormOrganizationDao.findByOrgType("I");
		list.add(root);
		for (OrmOrganization i : insts) {
			list.add(createZtreeNode(i, false));
		}
		return list;
	}

	private ZtreeBean createZtreeNode(OrmOrganization org, boolean isParent) {
		ZtreeBean node = new ZtreeBean();
		node.setId(org.getOrgId());
		node.setpId(org.getParentOrgId());
		node.setName(org.getOrgName());
		Map<String, Object> attrs = new HashMap<String, Object>();
		attrs.put("type", org.getOrgType());
		node.setAttributes(attrs);
		if (isParent) {
			node.setIsParent(true);
		}
		return node;
	}

	public List<ZtreeBean> getInstNodeChildren(String type, String orgId) {
		List<ZtreeBean> list = new ArrayList<ZtreeBean>();
		List<OrmOrganization> insts = ormOrganizationDao.findByOrgTypeAndParentOrgId(type, orgId);
		for (OrmOrganization i : insts) {
			list.add(createZtreeNode(i, false));
		}
		return list;
	}

	/**
	 * 删除组织机构
	 *
	 * @param orgId
	 */
	 public void deleteOrganization(String orgId) {
		List<OrmOrganization> orgs = ormOrganizationDao.getOrgByParentOrg(orgId);
		for (OrmOrganization org : orgs) {
			String oId = org.getOrgId();
			ormOrganizationDao.delete(oId);
			List<OrmOrgUserMap> maps = ormOrgUserMapDao.findByUserId(oId);
			for (OrmOrgUserMap map : maps) {
				String userId = map.getUserId();
				List<OrmOrgUserMap> oou = ormOrgUserMapDao.findByUserId(userId);
				if (oou.size() == 1) {
					ormUserService.delete(userId);
				}
			}
			ormOrgUserMapDao.deleteByOrgId(oId);
			ormOrgRoleMapDao.deleteByOrgId(oId);
		}
	}

	public List<ZtreeBean> getDepTree(String orgId, boolean isNeedRoot) {
		List<ZtreeBean> nodes = new ArrayList<ZtreeBean>();
		if (isNeedRoot) {
			OrmOrganization root = ormOrganizationDao.findOne(orgId);
			nodes.add(createZtreeNode(root, true));
		}
		List<OrmOrganization> orgs = ormOrganizationDao.getChildDeps(orgId, "I");
		List<OrmOrgUserMapView> maps = ormOrgUserMapViewDao.findByOrgId(orgId);
		List<String> userIds = new ArrayList<String>();
		for (OrmOrgUserMapView oou : maps) {
			String userId = oou.getUserId();
			if (userId != null) {
				userIds.add(oou.getUserId());
			}
		}
		List<OrmUser> users = ormUserService.getUserByIds(userIds);
		for (OrmUser user : users) {
			nodes.add(createEmpZtreeNode(user, orgId));
		}
		for (OrmOrganization org : orgs) {
			nodes.add(createZtreeNode(org, true));
		}
		return nodes;
	}

	private ZtreeBean createEmpZtreeNode(OrmUser user, String orgId) {
		ZtreeBean node = new ZtreeBean();
		node.setId(user.getUserId());
		node.setpId(orgId);
		node.setName(user.getUserName());
		Map<String, Object> attrs = new HashMap<String, Object>();
		attrs.put("type", "E");
		node.setAttributes(attrs);
		return node;
	}

	public List<Map<String, Object>> getInstOptions() {
		List<OrmOrganization> insts = ormOrganizationDao.findByOrgType("I");
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		result.add(orgToOption(null, false));
		for (OrmOrganization org : insts) {
			result.add(orgToOption(org, true));
		}
		return result;
	}

	public Map<String, Object> getInstChildrenOptions(String instId) {
		List<OrmOrganization> deps = ormOrganizationDao.getOrgByParentOrgAndOrgType("O", instId);
		List<OrmOrganization> posts = ormOrganizationDao.findByOrgTypeAndParentOrgId("P", instId);
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> depOptions = new ArrayList<Map<String, Object>>();
		if (deps.size() > 1) {
			depOptions.add(orgToOption(null, false));
			for (OrmOrganization dep : deps) {
				depOptions.add(orgToOption(dep, true));
			}
		}
		result.put("depOptions", depOptions);
		List<Map<String, Object>> postOptions = new ArrayList<Map<String, Object>>();
		if (posts.size() > 1) {
			postOptions.add(orgToOption(null, false));
			for (OrmOrganization post : posts) {
				postOptions.add(orgToOption(post, true));
			}
		}

		result.put("postOptions", postOptions);
		return result;
	}

	public List<Map<String, Object>> getPostOptions(String depId) {
		List<OrmOrganization> posts = ormOrganizationDao.findByOrgTypeAndParentOrgId("P", depId);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		if (posts.size() > 1) {
			result.add(orgToOption(null, false));
			for (OrmOrganization org : posts) {
				result.add(orgToOption(org, true));
			}
		}

		return result;
	}

	private Map<String, Object> orgToOption(OrmOrganization org, boolean isOrg) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (isOrg) {
			map.put("value", org.getOrgId());
			map.put("text", org.getOrgName());
		} else {
			map.put("value", null);
			map.put("text", "请选择");
		}
		return map;
	}

	public List<ZtreeBean> generateOrgCodeTree(String orgId) {
		List<OrmOrgCodeRightView> ocrs = ormOrgCodeRightViewDao.findByOrgId(orgId);
		List<ZtreeBean> nodes = ormCodeService.findHasRightTrees();
		Map<String, ZtreeBean> map = new HashMap<String, ZtreeBean>();
		for (ZtreeBean node : nodes) {
			map.put(node.getId(), node);
		}
		for (OrmOrgCodeRightView ocr : ocrs) {
			String codeId = ocr.getCodeId();
			ZtreeBean n = map.get(codeId);
			if (n != null) {
				n.setChecked(true);
			}
		}
		return nodes;
	}
	public List<ZtreeBean> getOrgRight(String orgId) {
		return ormRoleService.findOrgResourceTrees(orgId);
	}

	@SuppressWarnings("unchecked")
	public void addRoles(String orgId, String roles) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		List<Map<String, String>> list = mapper.readValue(roles, List.class);
		for (Map<String, String> map : list) {
			OrmOrgRoleMap oor = new OrmOrgRoleMap();
			oor.setOrgId(orgId);
			oor.setRoleId(map.get("roleId"));
			oor.setSystemId(map.get("systemId"));
			ormOrgRoleMapDao.add(oor);
		}
	}

	public void deleteRoles(String orgId, String[] roles) {
		List<String> list = new ArrayList<String>();
		list.addAll(Arrays.asList(roles));
		ormOrgRoleMapDao.deleteByOrgIdAndRoleIdIn(orgId, list);
	}
}
