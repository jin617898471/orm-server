define(function(require){
	var Tabs=require("inno/switchable/1.0.0/tabs-debug"),
		$=require("$"),
		Confirmbox=require("inno/dialog/1.0.0/confirmbox-debug");
	require("gallery/ztree/3.5.2/core-debug");
	require("gallery/ztree/3.5.14/ztree-debug.css");
	require("gallery/ztree/3.5.2/excheck-debug");
	SelectTree=require("inno/select-tree/1.0.0/select-tree-debug");
	var Map = require("./MapUtil");

	var _authRolePath="role/ormrole";

	var _roleId = $("#roleId").val();
	var _systemId = $("#systemId").val();
	
	var _institution = "I",_organization = "O",_position = "P",_system = "system",_codeIdx = "codeidx",_code = "code";
	
	tabs3 = new Tabs({
        element: '.ui-tabs-three',
        triggers: '.ui-tabs-nav li',
        panels: '.ui-tabs-content .panel',
        activeIndex: 0,
        triggerType:"click"
    });
	
	var cascadeFnTreeNode = function(checkedValues){
		//特殊权限存在时选中功能权限
		if(checkedValues.length>0){
			if(!_curentResNode.checked){
				$.fn.zTree.getZTreeObj("fn-tree").checkNode(_curentResNode, true, true);
			}
		}
	};
		
	/*************功能权限 start***************/
	function fnTreeOnCheck(event, treeId, treeNode) {
		var ischeck = treeNode.checked;
		if(ischeck){
			
		}else{
			var treeObj = $.fn.zTree.getZTreeObj("fn-tree");
			var pnode = treeNode.getParentNode();
			while(pnode){
				treeObj.checkNode(pnode, false, false);
				pnode = pnode.getParentNode();
			}
		}
	};
	function fnTreeBeforeClick(treeId, treeNode, clickFlag){
    	var resourceType = treeNode.attributes['resourceType'];
    	_curentResNode = treeNode;
    	specOrgAuthSelectTree.resetSelect();
    	specCodeAuthSelectTree.resetSelect();
    	 $("#checkedSpecOrgAuth").empty();
    	$("#checkedSpecCodeAuth").empty();
    	
    	if(("400" == resourceType || "600" == resourceType) && false == treeNode.nocheck){
    		$("#fnAuthDetails").css("visibility","visible");
    		$("#clkickNodeName_lbl").empty().append(treeNode.name);
    	}else{
    		$("#fnAuthDetails").css("visibility","hidden");
    	}
    };
    getFnTreeData = function(){
		var data = "";
		var parameter={
				url:_authRolePath+"/creatResourceTreeBean",
			    type:"POST",
			    data: {roleId:_roleId, systemId: _systemId},
			    async:false,
			    success:function(result){
			    	data = result;
			    },
			    error:function(result){
			    	Confirmbox.alert('查询失败！');
			    }
			};
		$.ajax( parameter );
		return data;
	};
	 var fnTreeSetting = {
	    		callback: {
	    			beforeClick: fnTreeBeforeClick,
	    			onCheck: fnTreeOnCheck
	    		},
				data: {
					simpleData: {
						enable: true
					}
				},
				check: {
					enable: true,
					chkStyle: "checkbox",
					chkboxType: {"Y" : "s", "N" : "ps"} 
				}
		};
	$.fn.zTree.init($("#fn-tree"), fnTreeSetting, getFnTreeData());	
	
	//选择节点的Id集合
	var getCheckedNodesIds = function(_treeDomId){
		var data = "";
		var treeObj = $.fn.zTree.getZTreeObj(_treeDomId);
		var nodes = treeObj.getCheckedNodes(true);
		for ( var i = 0; i < nodes.length; i++) {
			var obj = nodes[i];
			var id = obj.id;
			data += id+",";
		}
		data = data.substring(0, data.length-1);
		return data;
	};

	var showMsg = function(msg,isSuccess){
		if(true == isSuccess){
			$(".iconfont").show();
			$(".msgText").html(msg);
		}else{
			$(".iconfont").hide();
			$(".msgText").html(msg);
		}
	};

	//功能权限与角色之间关联关系的保存
	var saveResourceMap = function(){
		var parameter={
				url: _authRolePath+"/grantResource",
				type: "POST",
				data: {roleId:_roleId, resourceIds:getCheckedNodesIds("fn-tree")},
				async: false,
				success: function(data){
					showMsg("保存功能权限成功！",true);
				},
				error:function(result){
					showMsg("",false);
					Confirmbox.alert('保存功能权限失败！');
				}
		};
		$.ajax( parameter );
	};
	
	//页面保存事件
	$("#btnSave").click(function(){
		saveResourceMap();
	});
	
	//页面取消事件
	$("#btnCancel").click(function(){
		window.frameElement.trigger('close'); 
	});
	
});

