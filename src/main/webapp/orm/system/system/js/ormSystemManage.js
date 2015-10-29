define(function(require, exports, module) {
	var Dialog = require("inno/dialog/1.0.0/dialog-debug"),
		Confirmbox = require("inno/dialog/1.0.0/confirmbox-debug"),
		Form = require("form"),
		$ = require("$");

	require("easyui");

	var _path = "/orm-server/ormsystem";
	var form = new Form({
		trigger : ".search-form"
	});

	inittable = function(QUERYCONDITION) {
		$('#tblResult').datagrid({
			queryParams : {
				QUERYCONDITION : QUERYCONDITION
			},
			nowrap : true,
			autoRowHeight : false,
			striped : true,
			collapsible : true,
			url : _path + '/findByPage',
			sortName : 'systemName',
			sortOrder : 'asc',
			remoteSort : true,
			idField : 'systemId',
			frozenColumns : getGridFrozenColumns(),
			columns : getGridColumns(),
			pagination : true,
			rownumbers : true,
			pageList : [ 10, 20, 50, 100, 200, 500 ]
		});
	};
	inittable("");// 初始化表格

	// 查看
	var select = new Dialog({
		width : '650px',
		height : '290px',
		scrolling : true,
		title : "系统查看"
	}).before('show', function(id) {
		var url = _path + "/forward/details/" + id;
		this.set('content', url);
	});

	// 编辑
	var edit = new Dialog({
		width : '650px',
		height : '290px',
		scrolling : true,
		title : "系统编辑"
	}).before('show', function(id) {
		var url = _path + "/forward/edit/" + id;
		this.set('content', url);
	});

	// 查询
	$(".ui-more-button").click(function() {
		$(".search-more-box").toggleClass("fn-hide");
	});
	
	$(".ui-button-search").click(function() {
		var s = {
			"rules" : [],
			"groups" : [],
			"op" : "and"
		};
		var QUERYCONDITION = form.initSearch(s);
		inittable(QUERYCONDITION);
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
		inittable("");// 初始化表格
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
				var id = eval("n.systemId");
				deleteIdArray.push(id);
			});
			if (deleteIdArray.length <= 0) { // 判断是否选中数据
				Confirmbox.alert('请选择要删除的数据！');
				return false;
			}
			Confirmbox.confirm('这些系统记录可能被其他资源所引用,是否确定要删除？', '', function() {
				var name = "";
				var parameter = {
					url :  _path + '/deletebatch/' + deleteIdArray,
					type : "POST",
					async : false,
					success : function(data) {
						tableRefresh();
					},
					error : function(result) {
						Confirmbox.alert('删除失败！');
					}
				};
				$.ajax(parameter);
			});
		});
	
	// 新增
	new Dialog({
		trigger : '.ui-button-add',
		content : _path + '/forward/add',
		width : '650px',
		height : '290px',
		scrolling : true,
		title : "系统新增"
	});

	// 主键
	function getGridFrozenColumns() {
		return [ [ {
			field : 'ck',
			checkbox : true
		}, {
			field : 'systemId',
			width : 80,
			align : 'center',
			sortable : true,
			hidden : true
		} ] ];
	}

	// 初始化表格列表
	function getGridColumns() {
		return [[ 
			{ field : 'systemName', title : '系统名称', width : 335, align : 'left', sortable : true },
			{ field : 'systemCode', title : '系统标示符', width : 335, align : 'left', sortable : true },
			{ field : 'opt', title : '操作', width : 200, align : 'center', formatter : getGridOperation } 
			]];
	}

	// 操作
	function getGridOperation(value, rec, index) {
		var v = '<a class="ui-button-look" href="javascript:void(0)" onclick="tableRowDetailsSee(\'' + rec.systemId + '\')" >查看</a> ';
		var e = '<a class="ui-button-edit" href="javascript:void(0)" onclick="tableRowDetailsEdit(\'' + rec.systemId + '\')" >编辑</a> ';
		var p = '<a href="javascript:void(0)" onclick="tableRowDelete(\'' + rec.systemId + '\')" >删除</a> ';
		return v+e+p;
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
	tableRowDelete = function(id) {
		Confirmbox.confirm('系统信息可能被其他资源所应用,是否确定要删除该记录？', '', function() {
			var name = "";
			
			var parameter = {
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
			$.ajax(parameter);
		}, function() {

		});
	};

	// 刷新公用方法
	tableRefresh = function() {
		$('#tblResult').datagrid('reload');
	};

});