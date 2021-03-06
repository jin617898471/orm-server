define(function(require){
	var $ = require("$");
	Dialog = require("inno/dialog/1.0.0/dialog-debug");
	Confirmbox=require("inno/dialog/1.0.0/confirmbox-debug");
	Select = require("inno/select/1.0.0/select-debug");
	require("gallery/ztree/3.5.2/core-debug");
	require("gallery/ztree/3.5.2/exedit-debug");
	require("gallery/ztree/3.5.14/ztree-debug.css");

	require("easyui");
	
	var modulName="资源";
	var idField="resourceId";
	var urlBasePath='./authority/resource/';
	var url_cfg={
		"fadd":'forward/add',
		"fedit":'forward/edit',
		"fdetail":'forward/detail',
		"del":'delete',
		"dels":'deletes',
		"list":'list',
		"tree":'tree',
		"order":'adjustmentorder',
	}
	
	var setting = {
		data:{
			simpleData: {
				enable:true,
				idKey:"id",
				pIdKey:"pId",
			}
		},
		edit:{
			drag:{
				isCopy :false,
			},
			showRemoveBtn: false,
			showRenameBtn: false,
			enable:true
		},
		key:{
			name:"text"
		},
		callback : {
			onClick : function(event, treeId, treeNode){
				setQueryConditon(treeNode);
				reload();
			},
			beforeDrop: function(treeId, treeNodes, targetNode, moveType) {
				var result=false;
				if(moveType=="prev" || moveType=="next"){
					result=treeNodes[0].attributes.nodeType==targetNode.attributes.nodeType && treeNodes[0].pId==targetNode.pId
				}
				if(!result){
					return false;
				}
				result = dragTree(treeNodes[0].id,targetNode.id,moveType);
				return result;
			},
			beforeDrag:function(treeId, treeNodes){
				for (var i=0,l=treeNodes.length; i<l; i++) {
					if (treeNodes[i].drag === false) {
						return false;
					}
				}
				return true;
			},
		}
	};
	function dragTree(sourceId,targetId,moveType){
		var result=false;
		$.ajax( {
			url:urlBasePath+url_cfg["order"],
			data:{sourceId:sourceId,targetId:targetId,moveType:moveType},
			async:false,
			error:function(){
				alert('操作失败,请联系管理员');
			},
			statusCode: { 
				200: function(msg) {
					result=true;
			  	}
			}
		});
		return result;
	}
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
		$("*[rule-field=parentResId]").val(parentResId);
		$("*[rule-field=systemId]").val(systemId);
	}
	function getFirstNode(){
		return $.fn.zTree.getZTreeObj("tree").getNodes()[0];
	}
	function getSelectNode(){
		return $.fn.zTree.getZTreeObj("tree").getSelectedNodes()[0];
	}
	
	function handlerTree(data){
		$.each(data,function(i,node){
			var type = node.attributes.nodeType;
			if("SYSTEM"==type){
				node.drag=false;
			}
		})
		return data;
	}
	var gridTable;
	function loadTree(){
		$.post( urlBasePath+url_cfg["tree"],function( data ){
			$.fn.zTree.init($("#tree"), setting, handlerTree(data) );
			var node = getFirstNode();
			$.fn.zTree.getZTreeObj("tree").selectNode( node);
			setQueryConditon(node);
			
			gridTable=$("#grid-table").datagrid({
				nowrap : true,
				autoRowHeight : true,
				striped : true,
				collapsible : true,
				url : urlBasePath+url_cfg["list"],
				queryParams:{queryCondition:getQueryCondition()},
				sortName:"orderNumber",
				sortOrder:"asc",
				frozenColumns:[[
		            {field:'resourceId',checkbox:true},
		        ]],
				columns:[[
				    {field:'resourceName',title:'资源名称',align:'left',width:150},
				    {field:'resourceCode',title:'资源代码',align:'left',width:150},
				    {field:'resourceUrl',title:'资源Url',align:'left',width:150},
				    {field:'resourceType',title:'资源类型',align:'center',formatter:OrmJsObj.translate,codeIndex:"RESOURCE_TYPE",width:100},
					{field:'opt',title:'操作',align:'center',formatter:getOptionColumn,width:100}
				]],
				onLoadSuccess:bingRowEvent,
				onDblClickRow:bindRowDbClickEvent,
				pagination : true,
				rownumbers : true,
				pageList:[15,30,50,100]
			});
		} );
	}
	function reloadTree(){
		var selectedId=getSelectNode().id;
		$.post( urlBasePath+url_cfg["tree"],function( data ){
			$.fn.zTree.init($("#tree"), setting, handlerTree(data) );
			var node = $.fn.zTree.getZTreeObj("tree").getNodeByParam("id", selectedId, null);
			$.fn.zTree.getZTreeObj("tree").selectNode(node);
			$.fn.zTree.getZTreeObj("tree").expandNode(node,true);
			reload();
		} );
	}
	loadTree();
	
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
		$("ul").find("input[rule-field]").each(function(){
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
		var col = gridTable.datagrid('getColumnOption','opt');
		$("td[field=opt]").width(col.width);
		var isRight = gridTable.datagrid('getColumnOption','resourceType');
		$("td[field=resourceType]").width(isRight.width);
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