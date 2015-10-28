package cn.innosoft.fw.orm.server.service;

import java.util.ArrayList;
import java.util.HashMap;
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
import cn.innosoft.fw.orm.server.model.ZtreeBean;
import cn.innosoft.fw.orm.server.persistent.OrmCodeDao;

@Service
public class OrmCodeService extends AbstractBaseService<OrmCode, String> {

	@Autowired
	private OrmCodeDao ormCodeDao;
	@Override
	public BaseDao<OrmCode, String> getBaseDao() {
		return ormCodeDao;
	}

	/**
	 * 代码查询
	 * 
	 * @param pageRequest
	 * @return
	 */
	public PageResponse<OrmCode> find(PageRequest pageRequest) {
		FilterGroup group = QueryConditionHelper.add(pageRequest.getFilterGroup(), new String[] { "validSign" },
				new String[] { "Y" }, new String[] { "equal" });
		PageResponse<OrmCode> page = findAll(group, pageRequest);
		return page;
	}

	public void addCode(OrmCode ormCode) {
		ormCodeDao.save(ormCode);
	}

	public void updateCode(OrmCode ormCode) {
		ormCodeDao.update(ormCode);
	}

	public void deleteCode(String codeId) {
		ormCodeDao.delete(codeId);
	}




	/**
	 * 生成代码ztree，建模时用
	 * 
	 * @param res
	 * @return
	 */
	private ZtreeBean codeToTreeNode(OrmCode code) {
		ZtreeBean node = new ZtreeBean();
		node.setId(code.getCodeId());
		node.setpId(code.getParentCodeId());
		node.setName(code.getCodeName());
		boolean isParent = code.getIsLeaf() == "Y" ? false : true;
		node.setOpen(isParent);
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("rootCodeId", code.getRootCodeId());
		node.setAttributes(attributes);
		node.setNocheck(false);
		node.setChecked(false);
		return node;
	}
}
