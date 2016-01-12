define(function(require){
	var $ = require("$");
	Dialog = require("inno/dialog/1.0.0/dialog-debug");
	Confirmbox=require("inno/dialog/1.0.0/confirmbox-debug");
	Select = require("inno/select/1.0.0/select-debug");

	require("easyui");
	
	var modulName="角色";
	var idField="roleId";
	var urlBasePath='./authority/role/';
	var url_cfg={
		"fadd":'forward/add',
		"fedit":'forward/edit',
		"fdetail":'forward/detail',
		"del":'delete',
		"dels":'deletes',
		"list":'list',
	}
	
	var select_system=new Select({
		name:"systemId",
		trigger:'#systemId',
		width:'220px',
		model:OrmJsObj.system.getHasRight(null,true)
	}).render();

	var gridTable=$("#grid-table").datagrid({
		nowrap : true,
		autoRowHeight : true,
		striped : true,
		collapsible : true,
		url : urlBasePath+url_cfg["list"],
		queryParams:{queryCondition:null},
		columns:[[
		    {field:'roleId',checkbox:true},
		    {field:'roleNameCn',title:'角色名称',align:'center'},
		    {field:'systemId',title:'所属系统',align:'center',formatter:Dm2Mc},
			{field:'opt',title:'操作',align:'center',formatter:getOptionColumn}
		]],
		onLoadSuccess:bingRowEvent,
		onDblClickRow:bindRowDbClickEvent,
		pagination : true,
		rownumbers : false,
		pageList:[15,30,50,100]
	});
	
	function Dm2Mc(value,row,index){
		var list = OrmJsObj.system.getHasRight();
		for(var ind in list){
			var obj=list[ind];
			if(value==obj.value){
				return obj.text;
			}
		}
    	return "";
	}
	
	function getOptionColumn(value,row,index){
		var id=row[idField];
		return "<a class='operate-items items-space opt-fedit' rowid='"+id+"'>编辑</a><a class='operate-items opt-delete' rowid='"+id+"'>删除</a>";
	}
	
	function getQueryCondition(){
		var arr={rules:[],groups:[],op:"and"};
		$("*[rule-field]").each(function(){
			var tagName = this.tagName;
			if("INPUT"==tagName){
				var value=$(this).val();
			}else if("DIV"==tagName && $(this).hasClass("ui-select-trigger")){
				var value=$(this).next("input[name]").val();
			}
			if(value){
				var field=$(this).attr("rule-field");
				var op=$(this).attr("rule-op");
				arr.rules.push({field:field,op:op,value:value});
			}
		});
		 var str = JSON.stringify(arr) ;
		 return str;
	}
	function reset(){
		$("*[rule-field]").each(function(){
			var tagName = this.tagName;
			if("INPUT"==tagName){
				$(this).val("");
			}
		});
		select_system.selectValue("");
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
						Confirmbox.alert('删除失败,请联系管理员');
					},
					statusCode: { 
						200: function(msg) {
							reloadGrid();
					  	}
					}
				});
			})
		});
	}
	
	function seach(){
		var query=getQueryCondition();
		gridTable.datagrid("load",{"queryCondition":query});
	}
	function bingGlobalEvent(){
		$('.opt-reset').click(function(event){
			reset();
			seach();
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
						Confirmbox.alert('删除失败,请联系管理员');
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