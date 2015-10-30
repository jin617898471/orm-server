package cn.innosoft.fw.orm.server.model;

import java.util.List;
import java.util.Map;

public class SelectTreeBean {

	private String value; // 节点id

	private String text; // 节点名称

	private boolean open; // 节点是否打开，默认值true

	private List<SelectTreeBean> child;

	private List<Map<String, Object>> attrs;

	private String icon;

	public List<SelectTreeBean> getChild() {
		return child;
	}

	public void setChild(List<SelectTreeBean> child) {
		this.child = child;
	}

	public SelectTreeBean() {

	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public List<Map<String, Object>> getAttrs() {
		return attrs;
	}

	public void setAttrs(List<Map<String, Object>> attrs) {
		this.attrs = attrs;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

}
