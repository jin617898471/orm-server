define(function(require,exports){
	var Dialog=require("inno/dialog/1.0.0/dialog-debug"),
		Confirmbox=require("inno/dialog/1.0.0/confirmbox-debug"),
		Form = require("form"),
		Select = require("inno/select/1.0.0/select-debug"),
		$=require("$");

	require("easyui");

	var _path = "role/ormrole";

	var form = new Form({
		trigger : ".search-form"
	});

	// 新增
	new Dialog({
		trigger : '.ui-button-add',
		content : './' + _path + '/forward/add',
		width : '800px',
		height : '310px',
		scrolling : true,
		title : "角色新增"
	});

	// 查看
	var select = new Dialog({
		width : '850px',
		height : '310px',
		scrolling : true,
		title : "角色查看"
	}).before('show', function(id) {
		var url = './' + _path + "/forward/detail/" + id;
		this.set('content', url);
	});

	// 编辑
	var edit = new Dialog({
		width : '850px',
		height : '310px',
		scrolling : true,
		title : "角色编辑"
	}).before('show', function(id) {
		var url = './' + _path + "/forward/edit/" + id;
		this.set('content', url);
	});

	// 建模
	var showGrant =function(id,roleNameCn){
		var grant = new Dialog({
		width : '820px',
		height : '500px',
		scrolling : true,
		title : "角色建模 - "+roleNameCn
	}).before('show', function(id) {
		var url = './' + _path + "/forward/grant/" + id;
		this.set('content', url);
	});
		grant.show(id);
	};

	inittable = function(QUERYCONDITION) {
		$('#tblResult').datagrid({
			queryParams : {
				QUERYCONDITION : QUERYCONDITION
			},
			nowrap : true,
			autoRowHeight : false,
			striped : true,
			collapsible : true,
			url : _path + '/list',
			sortName : 'roleNameCn',
			sortOrder : 'asc',
			remoteSort : true,
			idField : 'roleId',
			frozenColumns : getGridFrozenColumns(),
			columns : getGridColumns(),
			pagination : true,
			rownumbers : true,
			pageList : [ 10, 20, 50, 100, 200, 500 ]
		});
	};

	setuputil = function(arrvalue,roleids) {
		var s = {
				"rules" : [],
				"groups" : [],
				"op" : "and"
			};
			var modeljson = {
				field : "systemId",
				op : "equal",
				value : arrvalue[0]
			};
			s["rules"].push(modeljson);
			modeljson = {
				field : "roleId",
				op : "in",
				value : roleids
			};
			s["rules"].push(modeljson);
			modeljson = {
					field : "roleType",
					op : "notequal",
					value : "ROOTADMIN"
			};
			s["rules"].push(modeljson);
			var QUERYCONDITION = form.initSearch(s);
			inittable(QUERYCONDITION);
	};
	
	var roleSystem = "";
	var roleids = "";
	setupajax = function() {
		var parameterS = {
				url : 'role/ormrole/system/roleids',
				type : "POST",
				async : false,
				success : function(data) {
					roleSystem = data[0];
					roleids = data[1];
				},
				error : function(result) {
					Confirmbox.alert('获取角色ids数据错误！');
				}
		};
		$.ajax(parameterS);
	};
	setuplist = function() {
		setupajax();
		var arrvalue = [];
		arrvalue.push(roleSystem);
		setuputil(arrvalue,roleids);
	};
	setuplist();// 初始化表格

	// 查询
	$(".ui-more-button").click(function() {
		$(".search-more-box").toggleClass("fn-hide");
	});
	$(".ui-button-search").click(function() {
		searchlist();
	});
	
	searchlist = function() {
		setupajax();
		var arrvalue = [];
		$.each($(".ui-select-selected"), function(n, datavalue) {
			arrvalue.push($(datavalue).attr("data-value"));
		});
		setuputil(arrvalue,roleids);
	};
	
	// 重置
	$(".ui-button-reset").click(function() {
		resetutilsearchlist();
	});
	
	resetutilsearchlist = function() {
		var searchForm = form.get("eleArray");
		searchForm.map(function(i, elem) {
			$(this).val("");
		});
		resetSelect();
		searchlist();
	};

	// 刷新
	$(".ui-button-refresh").click(function() {
		$('#tblResult').datagrid('reload');
	});

	// 批量删除
	$(".ui-button-delete").click(
			function() {
				var deleteIdArray = [];
				var selectedRow = $('#tblResult').datagrid('getSelections'); // 获取选中行
				$.each(selectedRow, function(i, n) {
					var id = eval("n.roleId");
					deleteIdArray.push(id);
				});
				if (deleteIdArray.length <= 0) { // 判断是否选中数据
					Confirmbox.alert('请选择要删除的数据！');
					return false;
				}
				Confirmbox.confirm('是否确定要删除这些记录？', '', function() {
					var name = "";
					var parameter = {
						url : _path + '/userOrg/' + deleteIdArray,
						type : "POST",
						async : false,
						success : function(data) {
							name = data;
						},
						error : function(result) {
							Confirmbox.alert('删除失败！');
						}
					};
					$.ajax(parameter);
					parameter = {
						url : _path + '/deletebatch/' + deleteIdArray,
						type : "POST",
						async : false,
						success : function(data) {
							tableRefresh();
						},
						error : function(result) {
							Confirmbox.alert('删除失败！');
						}
					};
					if (name.length > 0) {
						Confirmbox.confirm('该角色被' + name + '所引用，是否继续删除？', '',
								function() {
									$.ajax(parameter);
								}, function() {

								});
					} else {
						$.ajax(parameter);
					}
				}, function() {

				});
			});

	// 初始化系统下拉框查询列
	getSystemList = function() {
		var arr = [];
		var parameterS = {
			url : 'resource/ormresource/systemList',
			type : "POST",
			async : false,
			success : function(data) {
				$.each(data, function(n, systemObj) {
					modelJSON = {
						value : systemObj.systemId,
						text : systemObj.systemName
					};
					arr.push(modelJSON);
				});
			},
			error : function(result) {
				Confirmbox.alert('获取系统列表数据错误！');
			}
		};
		$.ajax(parameterS);
		return arr;
	};

	// 初始化系统下列列表
	var systemId = new Select({
		trigger : '.systemId',
		width : '190px',
		name : 'systemId',
		model : getSystemList(),
	}).render();

	// 重置下列列表方法
	resetSelect = function() {
		systemId.selectValue(roleSystem);
	};

	// 主键
	function getGridFrozenColumns() {
		return [ [ {
			field : 'ck',
			checkbox : true
		}, {
			field : 'roleId',
			width : 80,
			align : 'center',
			sortable : true,
			hidden : true
		} ] ];
	}

	// 获取系统name用于列表显示
	getSystemObjList = function() {
		var objList = null;
		var parameterS = {
			url : 'role/ormrole/list/',
			type : "POST",
			async : false,
			success : function(data) {
				objList = data;
			},
			error : function(result) {
				Confirmbox.alert('获取系统name数据错误！');
			}
		};
		$.ajax(parameterS);
		return objList;
	};

	var objListName = getSystemObjList();
	getSystemName = function(id) {
		var name = "";
		$.each(objListName, function(n, value) {
			if (value.systemId == id) {
				name = value.systemName;
			}
		});
		return name;
	};

	// 初始化表格列表
	function getGridColumns() {
		return [[ 
		         { field : 'roleNameCn', title : '中文名称', width : 250, align : 'center', sortable : true }, 
		         { field : 'roleNameEn', title : '英文名称', width : 250, align : 'center', sortable : true },  
			     { field : 'systemId', title : '所属系统', width : 250, align : 'center', sortable : true ,
			        	  formatter: function(value,row,index){
			            	  return getSystemName(value);
			              }
			     }, 
			 	 { field : 'opt', title : '操作', width : 120, align : 'center', formatter : getGridOperation } 
		 		]];
	}

	// 操作
	function getGridOperation(value, rec, index) {
		var v = '<a href="javascript:void(0)" onclick="tableRowDetailsSee(\'' + rec.roleId + '\' )" >查看</a> ';
		var e = '<a href="javascript:void(0)" onclick="tableRowDetailsEdit(\'' + rec.roleId + '\')" >编辑</a> ';
		var p = '<a href="javascript:void(0)" onclick="tableRowDetailsDelete(\'' + rec.roleId + '\')" >删除</a> ';
		var g = '<a href="javascript:void(0)" onclick="tableRowRoleGrant(\'' + rec.roleId + '\',\'' + rec.roleNameCn+ '\')" >建模</a> ';
		return v + e + p + g;
	}

	// 编辑
	tableRowDetailsEdit = function(id) {
		edit.show(id);
	};

	// 查看
	tableRowDetailsSee = function(id) {
		select.show(id);
	};

	// 删除
	tableRowDetailsDelete = function(id) {
		Confirmbox.confirm('是否确定要删除该记录？', '', function() {
			var name = "";
			var parameter = {
				url : _path + '/userOrg/' + id,
				type : "POST",
				async : false,
				success : function(data) {
					name = data;
				},
				error : function(result) {
					Confirmbox.alert('删除失败！');
				}
			};
			$.ajax(parameter);
			parameter = {
				url : _path + '/delete/' + id,
				type : "POST",
				async : false,
				success : function(data) {
					tableRefresh();
				},
				error : function(result) {
					Confirmbox.alert('删除失败！');
				}
			};
			if (name.length > 0) {
				Confirmbox.confirm('该角色被' + name + '所引用，是否继续删除？', '',
						function() {
							$.ajax(parameter);
						}, function() {

						});
			} else {
				$.ajax(parameter);
			}
		}, function() {

		});
	};

	// 建模
	tableRowRoleGrant = function(id,roleNameCn) {
		showGrant(id,roleNameCn);
	};

	// 刷新公共方法
	tableRefresh = function() {
		searchlist();
	};

});