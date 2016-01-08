package cn.innosoft.fw.orm.server.persistent;

import java.util.ArrayList;

import cn.innosoft.fw.biz.core.persistent.BaseDao;
import cn.innosoft.fw.orm.server.model.OrmOrgCodeRight;

public interface OrmOrgCodeRightDao extends BaseDao<OrmOrgCodeRight, String> {

	public void deleteByCodeId(String id);

	public void deleteByCodeIdIn(ArrayList<String> idArray);

	public void deleteBySystemId(String systemId);

}
