package cn.innosoft.fw.orm.server.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.innosoft.fw.biz.core.persistent.BaseDao;
import cn.innosoft.fw.biz.core.service.AbstractBaseService;
import cn.innosoft.fw.orm.server.model.OrmOrganization;
import cn.innosoft.fw.orm.server.model.ZtreeBean;
import cn.innosoft.fw.orm.server.persistent.OrmOrgRoleMapDao;
import cn.innosoft.fw.orm.server.persistent.OrmOrgUserMapDao;
import cn.innosoft.fw.orm.server.persistent.OrmOrganizationDao;
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
	private OrmUserRoleMapDao ormUserRoleMapDao;

	@Autowired
	private OrmUserService ormUserService;

	@Override
	public BaseDao<OrmOrganization, String> getBaseDao() {
		return ormOrganizationDao;
	}
	
	public List<ZtreeBean> getInstitutionTee() {
		List<ZtreeBean> list = new ArrayList<ZtreeBean>();
		ZtreeBean root = new ZtreeBean();
		root.setId("ROOT");
		root.setName("ROOT");
		List<OrmOrganization> insts = ormOrganizationDao.findByOrgType("I");
		list.add(root);
		for (OrmOrganization i : insts) {
			list.add(createZtreeNode(i));
		}
		return list;
	}

	private ZtreeBean createZtreeNode(OrmOrganization org) {
		ZtreeBean node = new ZtreeBean();
		node.setId(org.getOrgId());
		node.setpId(org.getParentOrgId());
		node.setName(org.getOrgName());
		return node;
	}
	// /**
	// * 添加组织机构
	// *
	// * @param org
	// */
	// public void addOrganization(OrmOrganization org) {
	// String parentId = org.getParentOrgId();
	// OrmOrganization pOrg = findOne(parentId);
	// // pOrg.setIsLeaf("N");
	// update(pOrg);
	// // org.setIsLeaf("Y");
	// org.setValidSign("Y");
	// org.setCreateDt(new Date());
	// org.setCreateUserId(LoginUserContext.getUser().getUserId());
	// String id = StringUtil.getUUID();
	// if("I".equals(org.getOrgType())){
	// org.setRootOrgId(id);
	// }
	// org.setOrgId(id);
	// add(org);
	// }
	// public void updateOrg(OrmOrganization org,String[] enforceUpdateField){
	// List<String> fields = new ArrayList<String>();
	// fields.addAll(Arrays.asList(enforceUpdateField));
	// fields.add("updateDt");fields.add("updateUserId");
	// org.setUpdateDt(new Date());
	// org.setUpdateUserId(LoginUserContext.getUser().getUserId());
	// updateSome(org,Arrays.asList(enforceUpdateField));
	// }
	// /**
	// * 删除组织机构
	// *
	// * @param orgId
	// */
	// public void deleteOrganization(String orgId) {
	// List<OrmOrganization> orgs = ormOrganizationDao.getOrgByParentOrg(orgId);
	// for(OrmOrganization org : orgs){
	// String oId = org.getOrgId();
	// ormOrganizationDao.delete(oId);
	// ormOrgUserMapDao.deleteByOrgId(oId);
	// ormOrgRoleMapDao.deleteByOrgId(oId);
	// }
	//
	// }
	//
	// public List<OrmOrganization> getOrgByUserId(String userId){
	// return ormOrganizationDao.getOrgByUserId(userId);
	// }
	//
	// /**
	// * 关联用户组织机构
	// *
	// * @param userId
	// * @param orgId
	// */
	// public String addUserToOrg(String userId, String orgId) {
	// List<OrmOrgUserMap> maps = ormOrgUserMapDao.findByUserIdAndOrgId(userId,
	// orgId);
	// if(maps.size() > 0 ){
	// return "用户已添加在岗位下";
	// }
	// createOrgUserMap(userId, orgId);
	// List<OrmOrgRoleMap> orms = ormOrgRoleMapDao.findByOrgId(orgId);
	// if (orms.size() == 0) {
	// return "新增成功";
	// }
	// for(OrmOrgRoleMap orm : orms){
	// ormUserService.createUserRoleMap(userId, orm.getRoleId(), orgId,
	// orm.getSystemId());
	// }
	// return "新增成功";
	// }
	// public void createOrgRoleMap(String orgId,String roleId,String systemId){
	// OrmOrgRoleMap orm = new OrmOrgRoleMap();
	// orm.setOrgId(orgId);
	// orm.setRoleId(roleId);
	// orm.setSystemId(systemId);
	// ormOrgRoleMapDao.save(orm);
	// List<OrmUser> users = ormUserService.getUserByOrgId(orgId);
	// for(OrmUser user : users){
	// ormUserService.createUserRoleMap(user.getUserId(), roleId, orgId,
	// systemId);
	// }
	// }
	// public void deleteOrgRoleMap(String orgId,String roleId){
	// ormOrgRoleMapDao.deleteByOrgIdAndRoleId(orgId, roleId);
	// List<OrmUser> users = ormUserService.getUserByOrgId(orgId);
	// for(OrmUser user : users){
	// ormUserRoleMapDao.deleteByUserIdAndRoleIdAndOrgId(user.getUserId(),
	// roleId, orgId);
	// }
	// }
	// /**
	// * 创建用户组织机构关联
	// *
	// * @param userId
	// * @param orgId
	// */
	// private void createOrgUserMap(String userId, String orgId) {
	// OrmOrgUserMap oum = new OrmOrgUserMap();
	// oum.setOrgId(orgId);
	// oum.setUserId(userId);
	// ormOrgUserMapDao.save(oum);
	// }
	//
	// /**
	// * 删除用户组织机构关联
	// *
	// * @param userId
	// * @param orgId
	// */
	// public void deleteUserFromOrg(String userId, String orgId) {
	// List<OrmOrgUserMap> maps = ormOrgUserMapDao.findByUserId(userId);
	// if(maps.size()==1){
	// ormUserService.deleteUser(userId);
	// return;
	// }
	// ormOrgUserMapDao.deleteByUserIdAndOrgId(userId, orgId);
	// List<OrmOrgRoleMap> orms = ormOrgRoleMapDao.findByOrgId(orgId);
	// if (orms.size() == 0) {
	// return;
	// }
	// for (OrmOrgRoleMap orm : orms) {
	// ormUserRoleMapDao.deleteByUserIdRoleIdAndType(userId, orm.getRoleId(),
	// "USER_ORG_TO_ROLE");
	// }
	// }
	//
	// public List<TreeNode> createOrgTreeByInstitution(String instId){
	// List<TreeNode> result = new ArrayList<TreeNode>();
	// Map<String, Object> args = new HashMap<String, Object>();
	// args.put("instId", instId);
	// List<Map<String, Object>> list = findMapBySql("orgService-orgTree",
	// args);
	// boolean[] flags = new boolean[list.size()];
	// for (int i = 0; i < list.size(); i++) {
	// if (instId.equals(list.get(i).get("ORG_ID"))) {
	// Map<String, Object> org = list.get(i);
	// TreeNode root = createOrgTreeNode(org);
	// flags[i] = true;
	// grenerateOrgTree(list, root, flags);
	// result.add(root);
	// }
	// }
	// System.out.println(result.size());
	// return result;
	// }
	//
	// public List<TreeNode> createInstitutionTree(){
	// List<TreeNode> result = new ArrayList<TreeNode>();
	// List<Map<String, Object>> list =
	// findMapBySql("orgService-institutionTree");
	// boolean[] flags = new boolean[list.size()];
	// for (int i = 0; i < list.size(); i++) {
	// if ("ROOT".equals(list.get(i).get("PARENT_ORG_ID"))) {
	// Map<String, Object> org = list.get(i);
	// TreeNode root = createOrgTreeNode(org);
	// flags[i] = true;
	// grenerateOrgTree(list, root, flags);
	// result.add(root);
	// }
	// }
	// System.out.println(result.size());
	// return result;
	// }
	//
	// /**
	// * 根据自定的集合和根节点来生成组织机构树
	// *
	// * @param list
	// * @param parentNode
	// * @param flags
	// */
	// private static void grenerateOrgTree(List<Map<String, Object>> list,
	// TreeNode parentNode, boolean[] flags) {
	// for (int i = 0; i < list.size(); i++) {
	// if (flags[i]) {
	// continue;
	// }
	// if (parentNode.getValue().equals(list.get(i).get("PARENT_ORG_ID"))) {
	// Map<String, Object> org = list.get(i);
	// List<TreeNode> children = parentNode.getChild();
	// if (children == null) {
	// children = new ArrayList<TreeNode>();
	// parentNode.setChild(children);
	// }
	// TreeNode node = createOrgTreeNode(org);
	// flags[i] = true;
	// children.add(node);
	// if (!"Y".equals(org.get("IS_LEAF"))) {
	// grenerateOrgTree(list, node, flags);
	// }
	// }
	// }
	// }
	// /**
	// * 生成组织机构树的节点
	// * @param org
	// * @return
	// */
	// private static TreeNode createOrgTreeNode(Map<String, Object> org) {
	// TreeNode node = new TreeNode();
	// node.setValue((String)org.get("ORG_ID"));
	// node.setText((String)org.get("ORG_NAME"));
	// Map<String, Object> attrs = new HashMap<String, Object>();
	// attrs.put("orgCode", org.get("ORG_CODE"));
	// attrs.put("orgAddress",org.get("ORG_ADDRESS"));
	// attrs.put("orgDesc",org.get("ORG_DESC"));
	// attrs.put("orgEmail",org.get("ORG_EMAIL"));
	// attrs.put("orgNameShort",org.get("ORG_NAME_SHORT"));
	// attrs.put("orgPhone",org.get("ORG_PHONE"));
	// attrs.put("orgPostcode",org.get("ORG_POSTCODE"));
	// attrs.put("orgType", org.get("ORG_TYPE"));
	// attrs.put("rysl", org.get("NUM"));
	// node.setAttrs(attrs);
	// return node;
	// }
	//
	//
	//
	//
	//
	// /**
	// * 生成组织机构的下拉树
	// *
	// * @return
	// */
	// public List<SelectTreeBean> createSelectTree() {
	// List<SelectTreeBean> result = new ArrayList<SelectTreeBean>();
	// List<OrmOrganization> orgs = ormOrganizationDao.findAll();
	// boolean[] flags = new boolean[orgs.size()];
	// for (int i = 0; i < orgs.size(); i++) {
	// if ("ROOT".equals(orgs.get(i).getParentOrgId())) {
	// OrmOrganization org = orgs.get(i);
	// SelectTreeBean root = createOrgTreeNode(org);
	// flags[i] = true;
	// grenerateOrgTree(orgs, root, flags);
	// result.add(root);
	// }
	// }
	// return result;
	// }
	//
	// /**
	// * 根据自定的集合和根节点来生成组织机构树
	// *
	// * @param list
	// * @param parentNode
	// * @param flags
	// */
	// private static void grenerateOrgTree(List<OrmOrganization> list,
	// SelectTreeBean parentNode, boolean[] flags) {
	// for (int i = 0; i < list.size(); i++) {
	// if (flags[i]) {
	// continue;
	// }
	// if (parentNode.getValue().equals(list.get(i).getParentOrgId())) {
	// OrmOrganization org = list.get(i);
	// List<SelectTreeBean> children = parentNode.getChild();
	// if (children == null) {
	// children = new ArrayList<SelectTreeBean>();
	// parentNode.setChild(children);
	// }
	// SelectTreeBean node = createOrgTreeNode(org);
	// flags[i] = true;
	// children.add(node);
	// // if (!"Y".equals(org.getIsLeaf())) {
	// grenerateOrgTree(list, node, flags);
	// // }
	// }
	// }
	// }
	// private static SelectTreeBean createOrgTreeNode(OrmOrganization org) {
	// SelectTreeBean node = new SelectTreeBean();
	// node.setValue(org.getOrgId());
	// node.setText(org.getOrgName());
	// node.setIcon("orm/org/org/css/imgs/" + org.getOrgType() + ".png");
	// node.setOpen(true);
	// List<Map<String, Object>> attrs = new ArrayList<Map<String, Object>>();
	// attrs.add(getAttr("orgCode", org.getOrgCode()));
	// attrs.add(getAttr("orderNumber", org.getOrderNumber()));
	// attrs.add(getAttr("orgType", org.getOrgType()));
	// node.setAttrs(attrs);
	// return node;
	// }
	// private static Map<String, Object> getAttr(String name, Object value) {
	// Map<String, Object> attr = new HashMap<String, Object>();
	// attr.put("name", name);
	// attr.put("value", value);
	// return attr;
	// }
}
