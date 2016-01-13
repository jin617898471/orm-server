define(function(require){
	var $ = require("$");
	Dialog = require("inno/dialog/1.0.0/dialog-debug");
	Confirmbox=require("inno/dialog/1.0.0/confirmbox-debug");

	require("easyui");
	
	var modulName="系统";
	var idField="systemId";
	var urlBasePath='./authority/system/';
	var url_cfg={
		"fadd":'forward/add',
		"fedit":'forward/edit',
		"fdetail":'forward/detail',
		"del":'delete',
		"dels":'deletes',
		"list":'list',
	}

	var gridTable=$("#grid-table").datagrid({
		nowrap : true,
		autoRowHeight : true,
		striped : true,
		collapsible : true,
		url : urlBasePath+url_cfg["list"],
		queryParams:{queryCondition:null},
		frozenColumns:[[
            {field:'systemId',checkbox:true},
        ]],
		columns:[[
		    {field:'systemName',title:'系统名称',align:'left',width:600},
		    {field:'systemCode',title:'系统代码',align:'left',width:300},
			{field:'opt',title:'操作',align:'center',formatter:getOptionColumn,width:100}
		]],
		onLoadSuccess:bingRowEvent,
		onDblClickRow:bindRowDbClickEvent,
		pagination : true,
		rownumbers : true,
		pageList:[15,30,50,100]
	});
	
	function getOptionColumn(value,row,index){
		var id=row.systemId
    	return "<a class='operate-items items-space opt-fedit' rowid='"+id+"'>编辑</a><a class='operate-items opt-delete' rowid='"+id+"'>删除</a>";
	}
	
	function getQueryCondition(){
		var arr={rules:[],groups:[],op:"and"};
		$("input[rule-field]").each(function(){
			var value=$(this).val();
			if(value){
				var field=$(this).attr("rule-field");
				var op=$(this).attr("rule-op");
				arr.rules.push({field:field,op:op,value:value});
			}
		});
		 var str = JSON.stringify(arr) ;
		 return str;
	}
	
	
	var dialog_ade_cfg={
		trigger:'',
		width:'760px',
		height:'425px',
		hasMask:false,
		ifEsc:false,
		closeTpl:'&#xe62a;'	
	};
	
	var d_add = new Dialog($.extend({},dialog_ade_cfg,{
		content:urlBasePath+url_cfg["fadd"],
		title:'新增'+modulName
	}));
	var d_edit = new Dialog($.extend({},dialog_ade_cfg,{
		content:urlBasePath+url_cfg["fedit"],
		title:'编辑'+modulName
	})).before('show',function(id){
		var url=urlBasePath+url_cfg["fedit"]+'/'+id;
		this.set('content',url);
	});
	var d_detail = new Dialog($.extend({},dialog_ade_cfg,{
		content:urlBasePath+url_cfg["fdetail"],
		title:'编辑'+modulName
	})).before('show',function(id){
		var url=urlBasePath+url_cfg["fdetail"]+'/'+id;
		this.set('content',url);
	});
	
	function bingRowEvent(){
		$('.opt-fedit').click(function(event){
			var id=$(this).attr("rowid");
			d_edit.show(id);
		});
		$('.opt-delete').click(function(event){
			var id=$(this).attr("rowid");
			Confirmbox.confirm('确定要删除','',function(){
				$.ajax( {
					url:urlBasePath+url_cfg["del"]+"/"+id,
					error:function(){
						alert('操作失败,请联系管理员');
					},
					statusCode: { 
						200: function(msg) {
							reloadGrid();
					  	}
					}
				});
			})
		});
		var col = gridTable.datagrid('getColumnOption','opt');
		$("td[field=opt]").width(col.width);
	}
	
	function reset(){
		$("input[rule-field]").each(function(){
			$(this).val("");
			seach();
		});
	}
	function seach(){
		var query=getQueryCondition();
		gridTable.datagrid("load",{"queryCondition":query});
	}
	function bingGlobalEvent(){
		$('.opt-reset').click(function(event){
			reset();
		});
		$('.opt-reflash').click(function(event){
			gridTable.datagrid("reload");
		});
		$('.opt-seach').click(function(event){
			seach()
		});
		$('.opt-fadd').click(function(event){
			d_add.show();
		});
		$('.opt-deletes').click(function(event){
			var arr=[];
			var selectedRow = gridTable.datagrid('getSelections');  
			$.each(selectedRow, function(index, row) {
				var id = row[idField];
				arr.push(id);
			});
			if(arr.length <= 0){
				Confirmbox.alert('请选择要删除的数据');
				return false;
			}
			Confirmbox.confirm('确定要删除','',function(){
				$.ajax( {
					url:urlBasePath+url_cfg["dels"]+"/"+arr,
					error:function(){
						alert('操作失败,请联系管理员');
					},
					statusCode: { 
						200: function(msg) {
							reloadGrid();
					  	}
					}
				});
			});
		});
	}
	function bindRowDbClickEvent(index,row){
		var id=row[idField];
		d_detail.show(id);
	}
	bingGlobalEvent();
	
	function reloadGrid(){
		var query=getQueryCondition();
		gridTable.datagrid("reload",{"queryCondition":query});
		gridTable.datagrid("clearSelections");
	}
	window.reloadGrid=reloadGrid;
	
	$(window).resize(function(event) {
		$("#grid-table").datagrid("resize");
	});

});