package cn.innosoft.fw.orm.server.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.innosoft.fw.biz.base.exception.ObjectMessageException;
import cn.innosoft.fw.biz.core.persistent.BaseDao;
import cn.innosoft.fw.biz.core.service.AbstractBaseService;
import cn.innosoft.fw.biz.utils.Identities;
import cn.innosoft.fw.orm.server.model.OrmCode;
import cn.innosoft.fw.orm.server.model.OrmSystem;
import cn.innosoft.fw.orm.server.model.ZtreeBean;
import cn.innosoft.fw.orm.server.persistent.OrmCodeDao;
import cn.innosoft.fw.orm.server.persistent.OrmOrgCodeRightDao;

@Service
public class OrmCodeService extends AbstractBaseService<OrmCode, String> {

	@Autowired
	private OrmCodeDao ormCodeDao;

	@Autowired
	private OrmOrgCodeRightDao ormOrgCodeRightDao;

	@Autowired
	private OrmSystemService ormSystemService;

	@Override
	public BaseDao<OrmCode, String> getBaseDao() {
		return ormCodeDao;
	}

	public List<ZtreeBean> findAllTrees() {
		List<OrmSystem> ormSystems = ormSystemService.getHasRight();
		List<ZtreeBean> systemtree = transferSystemTreeNode(ormSystems);
		List<String> rightsytem = getSystemId(systemtree);
		List<OrmCode> codes = ormCodeDao.findAllBySystemId(rightsytem);
		List<ZtreeBean> codetrees = transferCodeTreeNode(codes);
		systemtree.addAll(codetrees);
		return systemtree;
	}

	public List<ZtreeBean> findHasRightTrees() {
		List<OrmSystem> ormSystems = ormSystemService.getAll();
		List<ZtreeBean> systemtree = transferSystemTreeNode(ormSystems);
		List<String> rightsytem = getSystemId(systemtree);
		List<OrmCode> codes = ormCodeDao.findHasRightBySystemId(rightsytem);
		List<ZtreeBean> codetrees = transferCodeTreeNode(codes);
		systemtree.addAll(codetrees);
		return systemtree;
	}

	private List<ZtreeBean> transferCodeTreeNode(List<OrmCode> codes) {
		List<ZtreeBean> list = new ArrayList<ZtreeBean>();
		for (OrmCode code : codes) {
			ZtreeBean node = createCodeTreeNode(code);
			list.add(node);
		}
		return list;
	}

	private List<String> getSystemId(List<ZtreeBean> ormSystems) {
		List<String> list = new ArrayList<String>();
		for (ZtreeBean sys : ormSystems) {
			list.add(sys.getId());
		}
		return list;
	}

	private List<ZtreeBean> transferSystemTreeNode(List<OrmSystem> ormSystems) {
		List<ZtreeBean> list = new ArrayList<ZtreeBean>();
		ZtreeBean node = new ZtreeBean();
		node.setId("GLOBAL");
		node.setpId("ROOT");
		node.setName("公共代码");
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("nodeType", "SYSTEM");
		node.setAttributes(attributes);
		node.setOpen(true);
		node.setIconSkin((String) attributes.get("nodeType"));
		list.add(node);
		for (OrmSystem sys : ormSystems) {
			ZtreeBean tree = createSytemTreeNode(sys);
			list.add(tree);
		}
		return list;
	}

	private ZtreeBean createSytemTreeNode(OrmSystem sys) {
		ZtreeBean node = new ZtreeBean();
		node.setId(sys.getSystemId());
		node.setpId("ROOT");
		node.setName(sys.getSystemName());
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("nodeType", "SYSTEM");
		node.setAttributes(attributes);
		node.setOpen(true);
		node.setIconSkin((String) attributes.get("nodeType"));
		return node;
	}

	private ZtreeBean createCodeTreeNode(OrmCode code) {
		ZtreeBean node = new ZtreeBean();
		node.setId(code.getCodeId());
		String pId = code.getParentCodeId();
		Map<String, Object> attributes = new HashMap<String, Object>();
		if ("ROOT".equals(pId)) {
			attributes.put("nodeType", "CODEINDEX");
			node.setpId(code.getSystemId());
		} else {
			attributes.put("nodeType", "CODE");
			node.setpId(pId);
		}
		attributes.put("rootCodeId", code.getRootCodeId());
		attributes.put("systemId", code.getSystemId());
		node.setName(code.getCodeName());
		node.setAttributes(attributes);
		node.setIconSkin((String) attributes.get("nodeType"));
		return node;
	}

	public void deleteCode(String id) {
		List<String> list = new ArrayList<String>();
		list.add(id);
		List<OrmCode> children = ormCodeDao.getAllChildrenNode(list);
		List<String> ids = getResId(children);
		delete(ids);
		ormOrgCodeRightDao.deleteByCodeId(id);
	}

	private List<String> getResId(List<OrmCode> children) {
		List<String> ids = new ArrayList<String>();
		for (OrmCode child : children) {
			ids.add(child.getCodeId());
		}
		return ids;
	}

	public void deleteCode(ArrayList<String> idArray) {
		List<OrmCode> children = ormCodeDao.getAllChildrenNode(idArray);
		List<String> ids = getResId(children);
		delete(ids);
		ormOrgCodeRightDao.deleteByCodeIdIn(idArray);
	}

	public void deleteBySystemId(String systemId) {
		ormCodeDao.deleteBySystemId(systemId);
		ormOrgCodeRightDao.deleteBySystemId(systemId);
	}

	public OrmCode findParentNode(String parentId, String parentType) {
		OrmCode code = new OrmCode();
		if ("SYSTEM".equals(parentType)) {
			code.setParentCodeId("ROOT");
			code.setSystemId(parentId);
		} else {
			code.setParentCodeId(parentId);
			code.setIsRight(findOne(parentId).getIsRight());
		}
		return code;
	}

	public void addCode(OrmCode ormCode) {
		ormCode.setCodeId(Identities.uuid2());
		if ("ROOT".equals(ormCode.getParentCodeId())) {
			checkCodeIndexValue(ormCode.getCodeValue(), ormCode.getCodeId());
			ormCode.setRootCodeId(ormCode.getCodeId());
		} else {
			OrmCode parent = findOne(ormCode.getParentCodeId());
			ormCode.setRootCodeId(parent.getRootCodeId());
			ormCode.setSystemId(parent.getSystemId());
			checkCodeValue(ormCode.getRootCodeId(), ormCode.getCodeValue(), ormCode.getCodeId());
		}
		BigDecimal order = getMaxOrderNumber(ormCode.getParentCodeId());
		ormCode.setOrderNumber(order);
		add(ormCode);
	}

	private BigDecimal getMaxOrderNumber(String parentCodeId) {
		OrmCode ormCode = ormCodeDao.getMaxOrderNumber(parentCodeId);
		BigDecimal order = ormCode.getOrderNumber();
		if (null == order) {
			order = new BigDecimal(0);
		}
		return order.add(new BigDecimal(1));
	}

	public void updateCode(OrmCode ormCode, List<String> updateField) {
		if ("ROOT".equals(ormCode.getParentCodeId())) {
			checkCodeIndexValue(ormCode.getCodeValue(), ormCode.getCodeId());
			OrmCode old = findOne(ormCode.getCodeId());
			if (!ormCode.getIsRight().equals(old.getIsRight())) {
				ormCodeDao.updateIsRight(ormCode.getIsRight(), ormCode.getCodeId());
			}
		} else {
			checkCodeValue(ormCode.getRootCodeId(), ormCode.getCodeValue(), ormCode.getCodeId());
		}
		updateSome(ormCode, updateField);
	}

	public void adjustmentOrder(String sourceId, String targetId, String moveType) {
		OrmCode targ = findOne(targetId);
		String pid = targ.getParentCodeId();
		BigDecimal tarorder = targ.getOrderNumber();
		OrmCode sour = findOne(sourceId);
		BigDecimal sourorder = sour.getOrderNumber();
		int result = tarorder.compareTo(sourorder);
		if (1 == result) {// 目标的序号比源的序号大
			ormCodeDao.substractOrderNumber(sourorder, tarorder, pid);
			if ("prev".equals(moveType)) {
				ormCodeDao.updateOrderNumber(tarorder.subtract(new BigDecimal(1)), sourceId);
			} else if ("next".equals(moveType)) {
				ormCodeDao.updateOrderNumber(tarorder, sourceId);
				ormCodeDao.updateOrderNumber(tarorder.subtract(new BigDecimal(1)), targetId);
			}
		} else if (-1 == result) {// 目标的序号比源的序号小
			ormCodeDao.addOrderNumber( tarorder,sourorder, pid);
			if ("prev".equals(moveType)) {
				ormCodeDao.updateOrderNumber(tarorder, sourceId);
				ormCodeDao.updateOrderNumber(tarorder.add(new BigDecimal(1)), targetId);
			} else if ("next".equals(moveType)) {
				ormCodeDao.updateOrderNumber(tarorder.add(new BigDecimal(1)), sourceId);
			}
		} else {// 目标的序号和源的序号相等
			if ("prev".equals(moveType)) {
				ormCodeDao.updateOrderNumber(tarorder.subtract(new BigDecimal(1)), sourceId);
			} else if ("next".equals(moveType)) {
				ormCodeDao.updateOrderNumber(tarorder.add(new BigDecimal(1)), sourceId);
			}
		}
	}

	private void checkCodeValue(String rootCodeId, String codeValue, String codeId) {
		Integer cout = ormCodeDao.countByRootCodeIdAndCodeValueAndCodeIdNot(rootCodeId, codeValue, codeId);
		if (cout > 0) {
			throw new ObjectMessageException("存在值为" + codeValue + "的代码");
		}
	}

	private void checkCodeIndexValue(String codeValue, String codeId) {
		Integer cout = ormCodeDao.countByParentCodeIdAndCodeValueAndCodeIdNot("ROOT", codeValue, codeId);
		if (cout > 0) {
			throw new ObjectMessageException("存在值为" + codeValue + "的代码集");
		}
	}
}
