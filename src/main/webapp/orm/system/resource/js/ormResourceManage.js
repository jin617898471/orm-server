define(function(require) {
	var Menu = require("menu"), 
	Dialog = require("inno/dialog/1.0.0/dialog-debug"), 
	Confirmbox = require("inno/dialog/1.0.0/confirmbox-debug"),
	Select = require("inno/select/1.0.0/select-debug"), 
	Form = require("form"),
	// CodeProvider = require("../../../common/js/codeProvider"),
	$ = require("$");
	require("easyui");
	require("gallery/ztree/3.5.2/core-debug");
	require("gallery/ztree/3.5.14/ztree-debug.css");
	// CodeProvider.provider({"queryBeans":[{'codeSerial':'RESOURCE_TYPE','dataType':'select'}]});

	var _path = "resource/ormresource";

	var parentResId = "";// 父节点Id ，供子页面调用

	var typeRes = ""; // 用于子页面过滤系统

	var sysCode = "";// 系统代码合集，供翻译及下拉

	var systemId = "";// 用于子页面继承父节点的所属系统

	var resourceType = "";// 用于资源类型下拉限制

	var nocheck = "";// 判断是否显示新增

	var menu = new Menu({
		trigger : "#nav"
	});

	inittable = function(QUERYCONDITION, id) {
		$('#tblResult').datagrid({
			queryParams : {
				QUERYCONDITION : QUERYCONDITION
			},
			nowrap : true,
			autoRowHeight : true,
			striped : true,
			collapsible : true,
			url : _path + '/list?systemId=' + id,
			sortName : 'createDt',
			sortOrder : 'desc',
			idField : 'resourceId',
			frozenColumns : getGridFrozenColumns(),
			columns : getGridColumns(),
			pagination : true,
			rownumbers : true,
			pageList : [ 10, 20, 50, 100, 200, 500 ]
		});
	};

	var add = new Dialog({
		trigger : '.ui-button-add',
		content : './' + _path + "/forward/add",
		width : '800px',
		height : '340px',
		scrolling : true,
		title : "资源新增"
	});

	var select = new Dialog({
		width : '800px',
		height : '340px',
		scrolling : true,
		title : "资源查看"
	}).before('show', function(id) {
		var url = './' + _path + "/forward/detail/" + id;
		this.set('content', url);
	});

	var edit = new Dialog({
		width : '800px',
		height : '340px',
		scrolling : true,
		title : "资源编辑"
	}).before('show', function(id) {
		var url = './' + _path + "/forward/edit/" + id;
		this.set('content', url);
	});

	// 获取系统列表（下拉框和列表）
//	getSystemList = function() {
//		var result = "";
//		var parameterS = {
//			url : 'resource/ormresource/systemList',
//			type : "POST",
//			async : false,
//			success : function(data) {
//				result = data;
//				sysCode = result;
//			},
//			error : function(result) {
//				Confirmbox.alert('获取系统列表数据错误！');
//			}
//		};
//		$.ajax(parameterS);
//		return result;
//	};

	// 拼接系统下拉格式
//	getSelectSystem = function() {
//		var arr = [];
//		var modelJSON = {
//			value : "",
//			text : "-请选择-"
//		};
//		arr.push(modelJSON);
//		var data = getSystemList();
//		$.each(data, function(n, systemObj) {
//			modelJSON = {
//				value : systemObj.systemId,
//				text : systemObj.systemName
//			};
//			arr.push(modelJSON);
//		});
//		return arr;
//	};

	// 资源类型代码，资源类型：000代表系统，100代表，200代表，300代表模块，400代表功能点，500代表WEB功能页面，600代表页面上的操作，900代表其他。
	getResTypeList = function() {
		var result = [ {
			"resourceType" : "000",
			"resourceTypeName" : "系统",
		}, {
			"resourceType" : "100",
			"resourceTypeName" : "一级子系统",
		}, {
			"resourceType" : "200",
			"resourceTypeName" : "二级子系统",
		}, {
			"resourceType" : "300",
			"resourceTypeName" : "模块",
		}, {
			"resourceType" : "400",
			"resourceTypeName" : "功能点",
		}, {
			"resourceType" : "500",
			"resourceTypeName" : "WEB功能页面",
		}, {
			"resourceType" : "600",
			"resourceTypeName" : "表页面上的操作",
		}, {
			"resourceType" : "900",
			"resourceTypeName" : "其他",
		}, ]
		return result;
	};
	// 拼接资源类型下拉格式
	getSelectResType = function() {
		var arr = [];
		var modelJSON = [ {
			value : "",
			text : "-请选择-"
		}, {
			value : "000",
			text : "子系统"
		} ];
		arr.push(modelJSON);
		var data = getResTypeList();
		$.each(data, function(n, resourceObj) {
			modelJSON = {
				value : resourceObj.resourceType,
				text : resourceObj.resourceTypeName
			};
			arr.push(modelJSON);
		});
		// var arr2 = CodeProvider.getCode("RESOURCE_TYPE");
		// $.each(arr2, function(i,val){
		// arr.push(val);
		// });
		return arr;
	};

	var form = new Form({
		trigger : ".search-form"
	});

	// 左侧树的点击查询，与右侧列表的联动
	function zTreeBeforeClick(treeId, treeNode, clickFlag) {
		typeRes = treeNode.type;
		systemId = treeNode.system;
		parentResId = treeNode.id;
		resourceType = treeNode.resourceType;
		nocheck = treeNode.nocheck;
		if (nocheck == true) {
			$(".ui-button-add").hide();
		} else {
			$(".ui-button-add").show();
		}
		var QUERYCONDITION = '{"rules":[{"field":"parentResId","op":"like","value":"'
				+ parentResId + '"}],"groups":[],"op":"and"}';
		inittable(QUERYCONDITION, systemId);
	}
	;

	// 获取属性节点集合
	function getTreeAllNodes() {
		var back = "";
		$.ajax({
			type : "POST",
			dataType : "json",
			async : false,
			url : _path + "/treeBeanList",
			success : function(data) {
				back = data;
			},
			error : function(data) {
				alert("获取树节点集合异常，请联系管理员！");
			}
		});
		return back;
	}

	var setting = {
		callback : {
			beforeClick : zTreeBeforeClick
		}
	};

	var zNodes = getTreeAllNodes();
	//初始化资源树
	$.fn.zTree.init($("#tree"), setting, zNodes);
	
	// 初始化列表
	initTreeNodeSelect();
	function initTreeNodeSelect() {
		var treeObj = $.fn.zTree.getZTreeObj("tree");
		var nodes = treeObj.getSelectedNodes();
		if (nodes.length > 0) {// 判断是否为初始化阶段

		} else {
			inittableUtil(treeObj, false);
		}
	}
	;

	function inittableUtil(treeObj, flag) {
		var node = treeObj.getNodesByParam("type", "system", null);
		var nodes = treeObj.getSelectedNodes();
		var id = "";
		var systemid = "";
		if (flag) {
			id = nodes[0].id;
			systemid = nodes[0].system;
		} else {
			id = node[0].id;
			systemid = node[0].system;
			resourceType = node[0].resourceType;
			var n = treeObj.getNodeByParam("id", id);
			treeObj.selectNode(n, false);
		}
		var QUERYCONDITION = '{"rules":[{"field":"parentResId","op":"equal","value":"'
				+ id
				+ '"},{"field":"resourceId","op":"notequal","value":"'
				+ id + '"}],"groups":[],"op":"and"}';
		inittable(QUERYCONDITION, systemid);// 初始化表格
	}
	;

	// 刷新
	$(".ui-button-refresh").click(function() {
		$('#tblResult').datagrid('reload');
	});

	// 重置
	$(".ui-button-reset").click(function() {
		resetutilsearchlist();
	});

	resetutilsearchlist = function() {
		var searchForm = form.get("eleArray");
		searchForm.map(function(i, elem) {
			$(this).val("");
		});
		var treeObj = $.fn.zTree.getZTreeObj("tree");
		inittableUtil(treeObj, true);
	};

	// 查询
	$(".ui-button-search").click(function() {
		var s = {
			"rules" : [],
			"groups" : [],
			"op" : "and"
		};
		var treeObj = $.fn.zTree.getZTreeObj("tree");
		var nodes = treeObj.getSelectedNodes();
		var system = {
			field : "parentResId",
			op : "like",
			value : nodes[0].id
		};
		var res = {
			field : "resourceId",
			op : "notequal",
			value : nodes[0].id
		};
		s["rules"].push(res);
		s["rules"].push(system);
		var QUERYCONDITION = form.initSearch(s);
		inittable(QUERYCONDITION, nodes[0].id);
	});

	$(".ui-button-delete").click(function() {
		var deleteIdArray = [];
		var selectedRow = $('#tblResult').datagrid('getSelections'); // 获取选中行
		$.each(selectedRow, function(i, n) {
			var id = eval("n.resourceId");
			deleteIdArray.push(id);
		});
		if (deleteIdArray.length <= 0) { // 判断是否选中数据
			Confirmbox.alert('请选择要删除的数据！');
			return false;
		}
		Confirmbox.confirm('是否确定要删除这些记录？', '', function() {
			var parameter = {
				url : _path + '/deletebatch/' + deleteIdArray,
				type : "POST",
				async : false,
				success : function(data) {
					showMsg("resxMsg", true, "删除成功");
					tableRefresh(null, deleteIdArray);
					setTimeout('hidden("resxMsg");', 3000);
				},
				error : function(result) {
					showMsg("resxMsg", true, "删除失败");
					setTimeout('hidden("resxMsg");', 3000);
				}
			};
			$.ajax(parameter);
		}, function() {

		});
	});

	// 主键
	function getGridFrozenColumns() {
		return [ [ {
			field : 'ck',
			checkbox : true
		}, {
			field : 'resourceId',
			width : 80,
			align : 'center',
			sortable : true,
			hidden : true
		} ] ];
	}

	// 初始化表格列表
	function getGridColumns() {
		return [ [ {
			field : 'resourceName',
			title : '资源名称',
			width : 120,
			align : 'left',
			sortable : true
		}, {
			field : 'resourceUrl',
			title : 'URL',
			width : 240,
			align : 'left',
			sortable : true
		}, {
			field : 'resourceType',
			title : '类型',
			width : 80,
			align : 'left',
			sortable : true
		},// ,formatter
		// :
		// formatterTypeByResource},
//		{
//			field : 'systemId',
//			title : '所属系统',
//			width : 100,
//			align : 'left',
//			sortable : true,
//			formatter : formatterSysByResource
//		}, 
		{
			field : 'opt',
			title : '操作',
			width : 140,
			align : 'center',
			formatter : getGridOperation
		} ] ];
	}

	// 列表系统翻译
//	function formatterSysByResource(value, rec, index) {
//		var data = getSystemList();
//		if (value) {
//			for (var int = 0; int < data.length; int++) {
//				var sys = data[int];
//				for ( var i in sys) {
//					var name = sys.systemName;
//					if (sys[i] == value) {
//						return name;
//					}
//				}
//			}
//		}
//		return "";
//	}

	// 列表类型翻译
	function formatterTypeByResource(value, rec, index) {
		return value;
		// return CodeProvider.translate("RESOURCE_TYPE",value);
	}
	// 操作
	function getGridOperation(value, rec, index) {
		var v = '<a href="javascript:void(0)" onclick="tableRowDetailsSee(\''
				+ rec.resourceId + '\' )" >查看</a> ';
		var e = '<a href="javascript:void(0)" onclick="tableRowDetailsEdit(\''
				+ rec.resourceId + '\')" >编辑</a> ';
		var p = '<a href="javascript:void(0)" onclick="tableRowDetailsDelete(\''
				+ rec.resourceId + '\')" >删除</a> ';
		return v + e + p;
	}

	tableRowDetailsSee = function(id) {
		select.show(id);
	};

	tableRowDetailsEdit = function(id) {
		edit.show(id);
	};

	tableRowDetailsDelete = function(id) {
		Confirmbox.confirm('是否确定要删除该记录？', '', function() {
			var parameter = {
				url : _path + '/delete/' + id,
				type : "POST",
				async : false,
				success : function(data) {
					showMsg("resxMsg", true, "删除成功");
					tableRefresh(null, id);
					setTimeout('hidden("resxMsg");', 3000);
				},
				error : function(result) {
					showMsg("resxMsg", true, "删除失败");
					setTimeout('hidden("resxMsg");', 3000);
				}
			};
			$.ajax(parameter);
		}, function() {

		});
	};

	showMsg = function(type, isSuccess, Msg) {
		$("." + type).css("display", "");
		if (isSuccess) {
			$(".showColor").removeClass("error");
			$(".showColor").addClass("succeed");
			$(".showColor").html("&#xf00a1;");
		} else {
			$(".showColor").removeClass("succeed");
			$(".showColor").addClass("error");
			$(".showColor").html("&#xf0098;");
		}
		$(".Msg").html(Msg);
	};

	hidden = function(type) {
		$("." + type).css("display", "none");
	};

	// 为子页面提供方法，获取父页面点击节点的ID
	getParentId = function() {
		var parent = {
			parentResId : parentResId,
			systemId : systemId,
			type : typeRes,
			resourceType : resourceType
		};
		return parent;
	};

	// 刷新
	tableRefresh = function(pid, id, name, type, system, resourceType) {
		// 左侧树节点的刷新
		var treeObj = $.fn.zTree.getZTreeObj("tree");
		var pnodes = "";
		var nodes = "";
		var snodes = "";
		if (pid) {
			pnodes = treeObj.getNodesByParam("id", pid, null);
		}
		if (pnodes.length > 0) {
			if (type == 1) {
				snodes = {
					id : id,
					name : name,
					system : system,
					resourceType : resourceType
				};
				treeObj.addNodes(pnodes[0], snodes);
			} else {
				nodes = treeObj.getNodesByParam("id", id, null);
				nodes[0].name = name;
				treeObj.updateNode(nodes[0]);
			}
		} else {
			if (id instanceof Array) {
				$.each(id, function(i, val) {
					nodes = treeObj.getNodesByParam("id", val, null);
					treeObj.removeNode(nodes[0]);
				});
			} else {
				nodes = treeObj.getNodesByParam("id", id, null);
				treeObj.removeNode(nodes[0]);
			}
		}
		// 右侧树列表的刷新
		resetutilsearchlist();
		$('#tblResult').datagrid('clearSelections');
	};
});