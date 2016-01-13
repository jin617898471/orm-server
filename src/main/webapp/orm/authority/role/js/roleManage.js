define(function(require){
	var $ = require("$");
	var Dialog = require("inno/dialog/1.0.0/dialog-debug");
	var Confirmbox=require("inno/dialog/1.0.0/confirmbox-debug");
	var Select = require("inno/select/1.0.0/select-debug");
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
		url : urlBasePath+url_cfg["list"],
		queryParams:{queryCondition:null},
		frozenColumns:[[
            {field:'roleId',checkbox:true,width:20},
        ]],
        columns:[[
            {field:'roleNameCn',title:'角色名称',align:'left',halign:'center',width:600},
		    {field:'systemId',title:'所属系统',align:'left',formatter:OrmJsObj.translate,codeIndex:"system",width:300},
			{field:'opt',title:'操作',align:'center',formatter:getOptionColumn,width:100}
		]],
		onLoadSuccess:bingRowEvent,
		onDblClickRow:bindRowDbClickEvent,
		pagination : true,
		rownumbers : true,
		pageList:[15,30,50,100]
	});
	
	function getOptionColumn(value,row,index){
		var id=row[idField];
		var type=row["roleType"];
		return "<a class='operate-items items-space opt-fedit' rowid='"+id+"' roletype='"+type+"'>编辑</a><a class='operate-items opt-delete' rowid='"+id+"' roletype='"+type+"'>删除</a>";
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
		title:'查看'+modulName
	})).before('show',function(id){
		var url=urlBasePath+url_cfg["fdetail"]+'/'+id;
		this.set('content',url);
	});
	
	function bingRowEvent(){
		$('.opt-fedit').click(function(event){
			var roletype=$(this).attr("roletype");
			if("ADMIN"==roletype){
				Confirmbox.alert('该角色是系统内置的理员角色,不允许编辑');
				return false;
			}
			var id=$(this).attr("rowid");
			d_edit.show(id);
		});
		$('.opt-delete').click(function(event){
			var roletype=$(this).attr("roletype");
			if("ADMIN"==roletype){
				Confirmbox.alert('该角色是系统内置的理员角色,不允许删除');
				return false;
			}
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
	
	var i=0;
	
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
		$('.opt-reflash').click(function(event){
			gridTable.datagrid("reload");
		});
		$('.opt-fadd').click(function(event){
			d_add.show();
		});
		$('.opt-deletes').click(function(event){
			var arr=[];
			var selectedRow = gridTable.datagrid('getSelections');  
			var breakpar=false;
			$.each(selectedRow, function(index, row) {
				var rowindex = gridTable.datagrid('getRowIndex',row);
				var rownumber=$(".datagrid-body-inner tr[datagrid-row-index="+rowindex+"]").find(".datagrid-cell-rownumber").html();
				var roleType = row["roleType"];
				if("ADMIN"==roleType){
					Confirmbox.alert('第"'+rownumber+'"行的角色是系统内置的理员角色,不允许删除');
					breakpar = true;
					return false;
				}
				var id = row[idField];
				arr.push(id);
			});
			if(breakpar == true){
				return false;
			};
			if(arr.length <= 0){
				Confirmbox.alert('请选择要删除的数据');
				return false;
			}
			Confirmbox.confirm('确定要删除','',function(){
				$.ajax( {
					url:urlBasePath+url_cfg["dels"]+"/"+arr,
					error:function(){
						alert('操作,请联系管理员');
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