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
	var _roleOMapPath="role/ormrole";
	var _codePath = "resource/ormcode";
	var _roleCodeMapPaht = "authority/ormrolecodemap";
	var _roleId = $("#roleId").val();
	var _systemId = $("#systemId").val();
	
	var _institution = "I",_organization = "O",_position = "P",_system = "system",_codeIdx = "codeidx",_code = "code";
	
	var selectedSpecialOrgAuth = new Map();
	var selectedSpecialCodeAuth = new Map();
	
	var _curentResNode = null;
	var _orgTabFirstShow = true;//全局组织页是否第一次加载
	var _codeTabFirstShow = true;//全局代码页是否第一次加载
	
	tabs3 = new Tabs({
        element: '.ui-tabs-three',
        triggers: '.ui-tabs-nav li',
        panels: '.ui-tabs-content .panel',
        activeIndex: 0,
        triggerType:"click"
    });
	
	//页签切换事件
	tabs3.on('switched', function(toIndex, fromIndex) {
		if(1==toIndex && true == _orgTabFirstShow){//全局组织页第一次加载
			$.fn.zTree.init($("#org-tree"), globalOrgTreeSetting, getGlobalOrgTreeData());
			showGlobalOrgTreeCheckedNodes();
			_orgTabFirstShow = false;
		}
		if(2==toIndex && true == _codeTabFirstShow){//全局代码页第一次加载
        	$.fn.zTree.init($("#encode-tree"), codeTreeSetting, getCodeTreeData());
        	checknode();
        	_codeTabFirstShow = false;
		}
	  });
	
	var cascadeFnTreeNode = function(checkedValues){
		//特殊权限存在时选中功能权限
		if(checkedValues.length>0){
			if(!_curentResNode.checked){
				$.fn.zTree.getZTreeObj("fn-tree").checkNode(_curentResNode, true, true);
			}
		}
	};
	
  //组织机构下拉树数据
    getOrgAuthSelectTreeData = function(){
		var returnData = "";
		var parameter={
				url:'orm/ormrole/selectTreeOrg',
			    type:"POST",
			    async:false,
			    success:function(result){
			    	returnData = result;
			    },
			    error:function(result){
			    	Confirmbox.alert('查询失败！');
			    }
			};
		$.ajax( parameter );
		return returnData;
	};
	
	var dealCheckedSpecOrg = function(){
		var items = specOrgAuthSelectTree.element.find("[data-role=item]");
        $("#checkedSpecOrgAuth").empty();
        var checkedValues = "";
        var institution = {};
        var organizations = new Array();

		for(var i=0,j=items.length;i<j;i++){
			var temObj = items.eq(i);
			var type = temObj.attr("data-otype");
			if(_institution == type){
				institution.id = temObj.attr("data-value");
				institution.name = temObj.find("[data-role=text]").eq(0).text();
				institution.attributes = {};
				institution.attributes.oType = temObj.attr("data-otype");
				institution.pId = temObj.attr("data-poid");
			}
			if(_organization == type){
				var organization = {};
				organization.id = temObj.attr("data-value");
				organization.name = temObj.find("[data-role=text]").eq(0).text();
				organization.attributes = {};
				organization.attributes.oType = type;
				organization.pId = temObj.attr("data-poid");
				organizations.push(organization);
			}
			if(temObj.find("[data-role=check]").eq(0).is(".ui-select-tree-checked")){
				checkedValues += "," + temObj.attr("data-value");
				var nodeObj = {};
				nodeObj.id = temObj.attr("data-value");
				nodeObj.name = temObj.find("[data-role=text]").eq(0).text();
				nodeObj.attributes = {};
				nodeObj.attributes.oType = type;
				nodeObj.pId = temObj.attr("data-poid");
				
				if(_institution == type){
					createGlobalOrgHtml(nodeObj, "SO_","checkedSpecOrgAuth");
				}else {
					createGlobalOrgHtml(institution, "SO_","checkedSpecOrgAuth");
					for(var k = 0; k < organizations.length; k++){
						createGlobalOrgHtml(organizations[k], "SO_","checkedSpecOrgAuth");
					}
					createGlobalOrgHtml(nodeObj, "SO_","checkedSpecOrgAuth");
				}
			}
		};
		checkedValues = checkedValues.substring(1, checkedValues.length);
		var resId = _curentResNode.id;
		selectedSpecialOrgAuth.remove(resId);
		selectedSpecialOrgAuth.put(resId,checkedValues);
		selectedSpecialOrgAuth.size();
		cascadeFnTreeNode(checkedValues);//级联选中功能权限
	};
	
	var specOrgAuthSelectTree = new SelectTree({
        trigger: '.orgAuth',
        name:'specialOrgAuth',
        width:'160px',
        maxHeight:'200px',
        model: [{}],//getOrgAuthSelectTreeData(),
        checkAfter:function(triggers){
        	dealCheckedSpecOrg();
        },
    });
	
	//获取已分配特定组织机构
	var getOwnedOrgAuthData = function(){
		var returnData = "";
		var parameter={
				url: _authRolePath+'/getSpecialOrgAuth',
				data: {roleId: _roleId, resId: _curentResNode.id},
			    type: "POST",
			    async: false,
			    success: function(result){
			    	returnData = result;
			    },
			    error:function(result){
			    	Confirmbox.alert('查询失败！');
			    }
			};
		$.ajax( parameter );
		return returnData;
	};
	
	//代码权限下拉树数据
    var getCodeAuthSelectTreeData = function(){
		var data = "";
		var parameter={
				url:_codePath+'/getSpecCodeSelectTree',
				data: {roleId: _roleId},
			    type:"POST",
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
	
	var dealCheckedSpecCodeAuth = function(){
		var items = specCodeAuthSelectTree.element.find("[data-role=item]");
        $("#checkedSpecCodeAuth").empty();
        var checkedValues = "";
        var system = {};
        var codeIdx = {};

		for(var i=0,j=items.length;i<j;i++){
			var tempObj = items.eq(i);
			if(_system == tempObj.attr("data-type")){
				system.system = tempObj.attr("data-value");
				system.systemName = tempObj.find("[data-role=text]").eq(0).text();
			}
			if(_codeIdx == tempObj.attr("data-type")){
				codeIdx.codeIdx = tempObj.attr("data-value");
				codeIdx.codeIdxName = tempObj.find("[data-role=text]").eq(0).text();
			}
			if(tempObj.find("[data-role=check]").eq(0).is(".ui-select-tree-checked")){
				var nodeObj = {};
		        nodeObj.attributes = {};
		        checkedValues += "," + tempObj.attr("data-value");
				nodeObj.id = tempObj.attr("data-value");
				nodeObj.name = tempObj.find("[data-role=text]").eq(0).text();
				nodeObj.attributes.type = tempObj.attr("data-type");
				nodeObj.attributes.system = system.system;
				nodeObj.attributes.systemName = system.systemName;
				nodeObj.attributes.codeIdx = codeIdx.codeIdx;
    			nodeObj.attributes.codeIdxName =codeIdx.codeIdxName;
				createHtml(nodeObj, "SC_","checkedSpecCodeAuth");
			}
		}
		checkedValues = checkedValues.substring(1, checkedValues.length);
		var resId = _curentResNode.id;
		selectedSpecialCodeAuth.remove(resId);
		selectedSpecialCodeAuth.put(resId,checkedValues);
		selectedSpecialCodeAuth.size();
		cascadeFnTreeNode(checkedValues);//级联选中功能权限
	};
	
	var specCodeAuthSelectTree = new SelectTree({
        trigger: '.codeAuth',
        name:'specialCodeAuth',
        width:'160px',
        maxHeight:'200px',
        model:  [{}],//getCodeAuthSelectTreeData(),
        checkSelect:function(target,mult){
        	var type = $(target).attr("data-type");
        	if(_system == type){
        		alert("请选择代码集或代码。");
        		return false;
        	}
            return true;
        },
        checkAfter:function(triggers){
        	dealCheckedSpecCodeAuth();
        },
    });
	
	//获取已分配特殊代码权限
	getGrantedSpecCodes = function(){
		var data = "";
		var parameter={
				url:_roleCodeMapPaht+'/getGrantedSpecCodes',
				data: {roleId: _roleId, resId: _curentResNode.id},
			    type:"POST",
			    async:false,
			    success:function(result){
			    	data = result;
			    },
			    error:function(result){
			    	Confirmbox.alert('查询失败！');
			    }
			};
		//$.ajax( parameter );
		return data;
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
    		var oIds = selectedSpecialOrgAuth.get(_curentResNode.id);
    		if(oIds == null){
    			var selectTreeData = getOrgAuthSelectTreeData();
    			specOrgAuthSelectTree.syncModel(selectTreeData);
    			var specOrgData = getOwnedOrgAuthData();
    			if(specOrgData.length > 0){//特殊组织不为空时选择
        			oIds = "";
        			for(var i = 0; i<specOrgData.length; i++){
        				oIds += specOrgData[i].oid + ",";
        			}
        			oIds = oIds.substring(0, oIds.length);
    			}
    		}
    		if(oIds !="" && oIds != undefined && oIds != null){
    			specOrgAuthSelectTree.selectValue(oIds);
    		}
    		specOrgAuthSelectTree.render();
    		dealCheckedSpecOrg();
    		
    		var codeGuids = selectedSpecialCodeAuth.get(_curentResNode.id);
    		if(codeGuids == null){
    			var selectTreeData = getCodeAuthSelectTreeData();
    			specCodeAuthSelectTree.syncModel(selectTreeData);
    			var specCodeData = getGrantedSpecCodes();
    			if(specCodeData.length > 0){//特殊代码不为空时选择
    				codeGuids = "";
        			for(var i = 0; i<specCodeData.length; i++){
        				codeGuids += specCodeData[i].codeGuid + ",";
        			}
        			codeGuids = codeGuids.substring(0,codeGuids.length-1);
        		}
    		}
    		if(codeGuids != "" && codeGuids != undefined && codeGuids != null){
    			specCodeAuthSelectTree.selectValue(codeGuids);
    		}
    		specCodeAuthSelectTree.render();
    		dealCheckedSpecCodeAuth();
    		
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
	
	/*************功能权限 end*****************/
	
	/*************全局组织权限 start***************/
	//选择节点的Id集合
	var showGlobalOrgTreeCheckedNodes = function(){
		var treeObj = $.fn.zTree.getZTreeObj("org-tree");
		var nodes = treeObj.getCheckedNodes(true);
		$("#checkedGlobalOrgAuth").empty();
		for ( var i = 0; i < nodes.length; i++) {
			createGlobalOrgData(nodes[i]);
		}
	};
	
	var createGlobalOrgData = function(node){
		if(_institution == node.attributes.oType){
			createGlobalOrgHtml(node, "GO_","checkedGlobalOrgAuth");
		}else if(_organization == node.attributes.oType){
			if(node.getParentNode()){
				createGlobalOrgData(node.getParentNode());
			}
			createGlobalOrgHtml(node, "GO_","checkedGlobalOrgAuth");
		}else if(_position == node.attributes.oType){
			if(node.getParentNode()){
				createGlobalOrgData(node.getParentNode());
			}
			createGlobalOrgHtml(node, "GO_","checkedGlobalOrgAuth");
		}
	};
	
	var createGlobalOrgHtml = function(node,idPrefix,displayDomId){
		if(_institution == node.attributes.oType){
			var institution = $("#"+idPrefix+node.id);
			if(institution.length>0){
			}else{
				var html = "<dt id='"+idPrefix + node.id + "'>"
							+ "<label>" + node.name + " : </label>"
						    + "</dt>";
				$("#"+ displayDomId).append(html);
			}
		}else if(_organization == node.attributes.oType){
			var spanNode = $("#"+idPrefix+node.id);
			if(spanNode.length>0){
			}else{
				var html ="<span id='"+idPrefix+node.id+"'>" + node.name + "、</span>";
				var pNode = $("#"+idPrefix+node.pId);
				if(pNode.size() >0){
					$("#"+idPrefix+node.pId).append(html);
				}else{
					$("#"+ displayDomId).append(html);
				}
			}
		}else if(_position == node.attributes.oType){
			var spanNode = $("#"+idPrefix+node.pId);
			if(spanNode.length >0){
				var textVal = spanNode.text();
				if(textVal.indexOf("（") == -1){
					textVal = textVal.substring(0,textVal.length-1);
					spanNode.text(textVal+"（" + node.name + "）、");
				}else{
					textVal = textVal.substring(0,textVal.length-2);
					spanNode.text(textVal+"，" + node.name + "）、");
				}
			}else{
				var html ="<span id='"+idPrefix+node.pId+"'>（" + node.name + "）、</span>";
				$("#"+ displayDomId).append(html);
			}
		}
	};
	function globalOrgTreeOnCheck(event, treeId, treeNode) {
		var ischeck = treeNode.checked;
		if(ischeck){
			
		}else{
			var treeObj = $.fn.zTree.getZTreeObj("org-tree");
			var pnode = treeNode.getParentNode();
			while(pnode){
				treeObj.checkNode(pnode, false, false);
				pnode = pnode.getParentNode();
			}
		}
		showGlobalOrgTreeCheckedNodes();
	};
	getGlobalOrgTreeData = function(){
		var data = "";
		var parameter={
				url:_authRolePath + "/creatOrgTreeBean",
			    type:"POST",
			    data: {roleId:_roleId, systemId:_systemId},
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
	 var globalOrgTreeSetting = {
			callback:{
				onCheck: globalOrgTreeOnCheck
			} ,
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
	
	/*************全局组织权限 end*****************/
	
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
	
	/*************全局代码 start*****************/
	
	//全局代码查询
	getCodeTreeData = function(){
		var data = "";
		var parameter={
				url:_codePath + "/treeBeanListAll",
			    type:"POST",
			    data: {roleId:_roleId},
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
	
	//check的回调函数
	function zTreeOnCheck(event, treeId, treeNode) {
		var ischeck = treeNode.checked;
		if(ischeck){
			
		}else{
			var treeObj = $.fn.zTree.getZTreeObj("encode-tree");
			var pnode = treeNode.getParentNode();
			if(pnode){
				treeObj.checkNode(pnode, false, false);
			}
		}
		getCheckedNodesShow();
	};
	
	//初始化代码树
	var codeTreeSetting = {
			callback: {
				onCheck: zTreeOnCheck
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			check: {
				enable: true,
				chkStyle: "checkbox",
				autoCheckTrigger: true,
				chkboxType: { "Y": "s", "N": "ps" }
			}
	};
	
	//勾选树上面的节点
	var checknode = function(){
		var data = checkCodeNode();
		for ( var i = 0; i < data.length; i++) {
			var id = data[i];
			var treeObj = $.fn.zTree.getZTreeObj("encode-tree");
			var nodes = treeObj.getNodeByParam("id", id);
			if(nodes != null){
				treeObj.checkNode(nodes, true, true,false);
			}
		}
		getCheckedNodesShow();
	};
	
	//代码与角色之间关联关系的保存
	var checkCodeNode = function(){
		var result = "";
		var parameter={
				url:_authRolePath+'/grantCodeCheck',
				type:"POST",
				data:{roleId:_roleId},
				async:false,
				success:function(data){
					result = data;
				},
				error:function(result){
					Confirmbox.alert('删除失败！');
				}
		};
		//$.ajax( parameter );
		return result;
	};
	
	//对象的遍历递归（拼接HTML）
	var CreatObjData = function(obj,oldObj){
		var type = obj.attributes.type;
		var parentid = "";
		var creatObj = oldObj;
		if(type == "system"){
			creatObj.attributes.systemName = obj.name;
			creatObj.attributes.system = obj.id;
		}else if(type == "codeidx"){
			parentid = obj.attributes.system;
			oldObj.attributes.codeIdxName = obj.name;
			var treeObj = $.fn.zTree.getZTreeObj("encode-tree");
			var nobj = treeObj.getNodeByParam("id", parentid, null);
			oldObj.attributes.system = nobj.id;
			oldObj.attributes.systemName = nobj.name;
			CreatObjData(nobj,obj);
		}else if(type == "code"){
			parentid = obj.attributes.codeIdx;
			var treeObj = $.fn.zTree.getZTreeObj("encode-tree");
			var nobj = treeObj.getNodeByParam("id", parentid, null);
			CreatObjData(nobj,obj);
		}
		return creatObj;
	};
	//拼接Html
	var createHtml = function(data,idPrefix,displayDomId){
		var codeIdx = $("#"+idPrefix+data.attributes.codeIdx);
		if(codeIdx.length>0){
			var codename =  $("#"+idPrefix+data.attributes.codeIdx).find("span");
			if(codename.length>0){
				var coden =  $("#"+idPrefix+data.attributes.codeIdx).find("span").text();
				coden = coden+"、"+data.name;
				$("#"+idPrefix+data.attributes.codeIdx).find("span").text('');
				$("#"+idPrefix+data.attributes.codeIdx).find("span").text(coden);
			}else{
				var html = "";
				if(data.attributes.type == "code"){
					html += '<span>'+data.name+'</span>';
				}
				$("#"+idPrefix+data.attributes.codeIdx).append(html);
			}
		}else{
			var system = $("#"+idPrefix+data.attributes.system);
			if(system.length>0){
				var html = '<dl class = "'+idPrefix+data.attributes.system+'" ><dt id = "'+idPrefix+data.attributes.codeIdx+'">';
				html += '<label>'+data.attributes.codeIdxName+' : </label>';
				if(data.attributes.type == "code"){
					html += '<span>'+data.name+'</span>';
				}
				html += '</dt></dl>';
				$(".data_"+idPrefix+data.attributes.system).append(html);
			}else{
				var html = '<div class="main-container data_'+idPrefix+data.attributes.system+'">';
				html += '<div class="org-header" id = "'+idPrefix+data.attributes.system+'"><i class="org-line"></i>';
				html += '<h4 class="systemText">'+data.attributes.systemName+'</h4>';
				html += '</div>';
				html += '<dl class = "'+idPrefix+data.attributes.system+'" ><dt id = "'+idPrefix+data.attributes.codeIdx+'">';
				html += '<label>'+data.attributes.codeIdxName+' : </label>';
				if(data.attributes.type == "code"){
					html += '<span>'+data.name+'</span>';
				}
				html += '</dt></dl>';
				$("#"+displayDomId).append(html);
			}
		}
	};
	
	//选择节点的Id集合
	var getCheckedNodesShow = function(){
		var treeObj = $.fn.zTree.getZTreeObj("encode-tree");
		var nodes = treeObj.getCheckedNodes(true);
		$(".codeGroup").empty();
		for ( var i = 0; i < nodes.length; i++) {
			var obj = nodes[i];
			var dataObj = CreatObjData(obj,obj);
			createHtml(dataObj,"GC_","checkedGlobalCodeAuth");
		}
	};
	
	
	/*************全局代码 end*****************/
	
	/**************数据保存******************/
	var showMsg = function(msg,isSuccess){
		if(true == isSuccess){
			$(".iconfont").show();
			$(".msgText").html(msg);
		}else{
			$(".iconfont").hide();
			$(".msgText").html(msg);
		}
	};
	
	//代码与角色之间关联关系的保存
	var saveCodeMap = function(){
		var SelectData = getCheckedNodesIds("encode-tree");
		var parameter={
				url:_authRolePath+'/grantCode',
				type:"POST",
				data:{roleId:_roleId,ids:SelectData},
				async:false,
				success:function(data){
					
				},
				error:function(result){
					Confirmbox.alert('删除失败！');
				}
		};
		//$.ajax( parameter );
	};
	
	//全局组织与角色之间关联关系的保存
	var saveGlobalOrgMap = function(){
		var parameter={
				url:_roleOMapPath+"/grantOrg",
				type:"POST",
				data:{roleId:_roleId,orgIds:getCheckedNodesIds("org-tree")},
				async:false,
				success:function(data){
					showMsg("保存全局组织权限成功！",true);
				},
				error:function(result){
					showMsg("",false);
					Confirmbox.alert('保存全局组织权限失败！');
				}
		};
		//$.ajax( parameter );
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
					$("#btnSave").attr('disabled',false);
				},
				error:function(result){
					showMsg("",false);
					Confirmbox.alert('保存功能权限失败！');
				}
		};
		$.ajax( parameter );
	};
	//特定组织权限保存
	var saveSpecOrgMap = function(){
		var parameter={
				url:_roleOMapPath+"/grantSpecialOrgAuth",
				type: "POST",
				data: {
					roleId:_roleId, 
					resIds:selectedSpecialOrgAuth.joinKeys(','),
					orgIds:selectedSpecialOrgAuth.joinValues(';')
					},
				async: false,
				success: function(data){
					showMsg("保存特殊组织权限成功！",true);
				},
				error:function(result){
					showMsg("",false);
					Confirmbox.alert('保存特殊组织权限失败！');
				}
		};
		//$.ajax( parameter );
	};
	//特定代码权限保存
	var saveSpecCodeMap = function(){
		var parameter={
				url:_roleCodeMapPaht+"/grantSpecialCodeAuth",
				type: "POST",
				data: {
					roleId:_roleId, 
					resIds:selectedSpecialCodeAuth.joinKeys(','),
					codeGuIds:selectedSpecialCodeAuth.joinValues(';')
					},
				async: false,
				success: function(data){
					showMsg("保存特殊代码权限成功！",true);
				},
				error:function(result){
					Confirmbox.alert('保存特殊代码权限失败！');
				}
		};
		//$.ajax( parameter );
	};
	
	//页面保存事件
	$("#btnSave").click(function(){
		saveResourceMap();
		saveSpecOrgMap();
		saveSpecCodeMap();
		if(false == _orgTabFirstShow){
			saveGlobalOrgMap();
		}
		if(false == _codeTabFirstShow){
			saveCodeMap();
		}
	});
	
	//页面取消事件
	$("#btnCancel").click(function(){
		window.frameElement.trigger('close'); 
	});
	
});

