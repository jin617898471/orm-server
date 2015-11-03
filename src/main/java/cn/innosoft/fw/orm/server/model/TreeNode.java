package cn.innosoft.fw.orm.server.model;

import java.util.List;
import java.util.Map;

public class TreeNode {

	private String value; // 节点id

	private String text; // 节点名称

	private List<TreeNode> child;

	private Map<String, Object> attrs;

	public List<TreeNode> getChild() {
		return child;
	}

	public void setChild(List<TreeNode> child) {
		this.child = child;
	}

	public TreeNode() {

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

	public Map<String, Object> getAttrs() {
		return attrs;
	}

	public void setAttrs(Map<String, Object> attrs) {
		this.attrs = attrs;
	}


}
