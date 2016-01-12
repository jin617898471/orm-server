define(function(require){
	var $ = require("$");
	Dialog = require("inno/dialog/1.0.0/dialog-debug");
	Confirmbox=require("inno/dialog/1.0.0/confirmbox-debug");
	Select = require("inno/select/1.0.0/select-debug");
	require("gallery/ztree/3.5.2/core-debug");
	require("gallery/ztree/3.5.14/ztree-debug.css");

	require("easyui");
	
	var modulName="代码";
	var idField="codeId";
	var urlBasePath='./authority/code/';
	var url_cfg={
		"fadd":'forward/add',
		"fedit":'forward/edit',
		"fdetail":'forward/detail',
		"del":'delete',
		"dels":'deletes',
		"list":'list',
		"tree":'tree',
	}
	
	var gridTable=$("#grid-table").datagrid({
		nowrap : true,
		autoRowHeight : true,
		striped : true,
		collapsible : true,
		url : urlBasePath+url_cfg["list"],
		queryParams:{queryCondition:null},
		columns:[[
		    {field:'codeId',checkbox:true},
		    {field:'codeName',title:'代码名称',align:'center'},
		    {field:'codeValue',title:'代码值',align:'center'},
		    {field:'isRight',title:'是否数据权限代码',align:'center',formatter:Dm2Mc},
			{field:'opt',title:'操作',align:'center',formatter:getOptionColumn}
		]],
		onLoadSuccess:bingRowEvent,
		onDblClickRow:bindRowDbClickEvent,
		pagination : true,
		rownumbers : false,
		pageList:[15,30,50,100]
	});
	
	var setting = {
		data:{
			simpleData: {
				enable:true,
				idKey:"id",
				pIdKey:"pId",
			}
		},
		key:{
			name:"text"
		},
		callback : {
			onClick : function(event, treeId, treeNode){
				setQueryConditon(treeNode);
				reload();
			}
		}
	};
	function setQueryConditon(treeNode){
		var type = treeNode.attributes.nodeType;
		var id = treeNode.id;
		var parentResId="";
		var systemId="";
		if("SYSTEM"==type){
			parentResId="ROOT";
			systemId=id
		}else{
			parentResId=id;
			systemId=""
		}
		$("*[rule-field=parentCodeId]").val(parentResId);
		$("*[rule-field=systemId]").val(systemId);
	}
	function getFirstNode(){
		return $.fn.zTree.getZTreeObj("tree").getNodes()[0];
	}
	function getSelectNode(){
		return $.fn.zTree.getZTreeObj("tree").getSelectedNodes()[0];
	}
	function loadTree(){
		$.post( urlBasePath+url_cfg["tree"],function( data ){
			$.fn.zTree.init($("#tree"), setting, data );
			var node = getFirstNode();
			$.fn.zTree.getZTreeObj("tree").selectNode( node);
		} );
	}
	function reloadTree(){
		var selectedId=getSelectNode().id;
		$.post( urlBasePath+url_cfg["tree"],function( data ){
			$.fn.zTree.init($("#tree"), setting, data );
			var node = $.fn.zTree.getZTreeObj("tree").getNodeByParam("id", selectedId, null);
			$.fn.zTree.getZTreeObj("tree").selectNode(node);
			$.fn.zTree.getZTreeObj("tree").expandNode(node);
			reload();
		} );
	}
	loadTree();
	
	function Dm2Mc(value,row,index){
		var list = [{"text":"是","value":"Y"},{"text":"否","value":"N"}];
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
	})).before('show',function(){
		var treeNode = getSelectNode();
		var parentId=treeNode.id;
		var parentType=treeNode.attributes.nodeType;
		var url=urlBasePath+url_cfg["fadd"]+'?parentId='+parentId+'&parentType='+parentType;
		this.set('content',url);
	});
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
		reloadTree()
	}
	function reload(){
		var query=getQueryCondition();
		gridTable.datagrid("reload",{"queryCondition":query});
		gridTable.datagrid("clearSelections");
	}
	window.reloadGrid=reloadGrid;
	
	$(window).resize(function(event) {
		$("#grid-table").datagrid("resize");
	});

});