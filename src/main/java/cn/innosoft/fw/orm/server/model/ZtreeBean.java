package cn.innosoft.fw.orm.server.model;

import java.io.Serializable;
import java.util.Map;

/**
 * ztree存储初始化对象bean.
 * 
 * @author sunjunchao
 * @date 2014年4月16日 上午11:16:37
 */
public class ZtreeBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;// 节点id

	private String pId;// 节点父id

	private String name;// 节点名称

	private Boolean checked = false;// 节点是否被选中，默认值false

	private Boolean open = false;// 节点是否打开，默认值false

	private Boolean isParent = false;// 节点是否是父节点，默认值false

	private String url = "";// 节点点击触发的url

	private String target = "_blank";// 设置点击节点后在何处打开 url，默认值_blank

	private String iconUrl;// 节点自定义图标的 URL 路径

	private Map<String, Object> attributes;

	private Boolean nocheck = false; // 是否隐藏单/复选框

	private Boolean chkDisabled = false; // 是否冻结复选框

	private String iconSkin = ""; // icon的自定义样式

	public ZtreeBean() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public String getUrl() {
		return url;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public Boolean getNocheck() {
		return nocheck;
	}

	public void setNocheck(Boolean nocheck) {
		this.nocheck = nocheck;
	}

	public Boolean getChkDisabled() {
		return chkDisabled;
	}

	public void setChkDisabled(Boolean chkDisabled) {
		this.chkDisabled = chkDisabled;
	}

	public String getIconSkin() {
		return iconSkin;
	}

	public void setIconSkin(String iconSkin) {
		this.iconSkin = iconSkin;
	}

}