define(function(require){
	var $ = require("$"),
		Tabs=require("inno/switchable/1.0.0/tabs-debug"),
		Dialog = require("inno/dialog/1.0.0/dialog-debug");
	require("gallery/ztree/3.5.2/core-debug");
	require("gallery/ztree/3.5.2/exedit-debug");
	require("gallery/ztree/3.5.14/ztree-debug.css");
	Validator = require("inno/validator/1.0.0/validator-debug");
	Confirmbox=require("inno/dialog/1.0.0/confirmbox-debug");
	Form = require("form");

	var orgTab = new Tabs({
		element: '.org-tabs',
        triggers: '.org-tabs-nav li',
        panels: '.org-tabs-content .content-panel',
        triggerType:"click",
        closeStatus:false
	});

	$(".operate-status").hide();

	var itemsW,
		labelW,
		inpW,
		fullWidth;
	function addressInput(){

		itemsW = $(".basic-list-items")[0].offsetWidth,
		labelW = $(".list-items-label")[0].offsetWidth,
		inpW = $(".list-items-inp")[0].offsetWidth,
		fullWidth = $(".inp-address").parent().width();

		$(".inp-address").css('width', fullWidth - labelW - (itemsW - labelW - inpW) - 12);

	}

	var tabInstId = new Array("","");
	var basePath = "/orm-server/";
	var urlcfg= {
		instTree : basePath + "org/institutionTree",
		orgInfo : basePath + "org/info",
		childInst : basePath + "org/children",
		updateInst : basePath + "org/update",
		deleteInst : basePath + "org/delete/",
		getNode : basePath + "org/children/",
		forwardAdd : basePath + "org/institution/forward/add/",
		forwardEdit : basePath + "org/institution/forward/edit/",
	}
	//基本信息之地址输入框的宽度
	addressInput();

	//datagrid
	require("easyui");
	var tagIndex = 0;
	//重新加载datagrid
	$(".org-tabs-nav").on('click', 'li', function(event) {
		var treeNode = $.fn.zTree.getZTreeObj("tree").getSelectedNodes()[0];
		var instId = treeNode.id;
		var thisIndex = $(this).index();
		tagIndex = thisIndex;
		switch(thisIndex){
		case 0: 
			if(tabInstId[thisIndex] != instId){
				getInstInfo(instId)
				addressInput();
			}
			break;
		case 1:
			if(tabInstId[thisIndex] != instId){
				loadGrid(instId);
				setTimeout(function(){
					$("#grid-table").datagrid("resize");
				},500);
			}
			
			break;
		}
		tabInstId[thisIndex] = instId;
		
	});

	editInst = function (id){
		dialog.show(urlcfg.forwardEdit + id);
	}
	//删除子机构
	deleteInst = function(id){
		Confirmbox.confirm('是否确定要删除该记录？','',function(){
			var parameter={
				url:urlcfg.deleteInst + id,
				type:"POST",
				async:false,
				success:function(data){
					refreshInst();
				},
				error:function(result){
					Confirmbox.alert('删除失败！');
				}
			};
			$.ajax( parameter );
		},function(){

		});
	};
	//刷新组织机构树节点，以及子机构列表
	refreshInst = function(){
		loadGrid(selectedNode.id);
		$.fn.zTree.getZTreeObj("tree").reAsyncChildNodes(selectedNode,"refresh");
	}
	
	//载入datagrid
	function loadGrid(instId){
//		console.log("loadgrid");
		$("#grid-table").empty();
		$("#grid-table").datagrid({
			queryParams : {queryCondition : "{\"rules\" : [{\"field\" : \"parentOrgId\", \"op\" : \"equal\",\"value\":\""+instId+"\"}" +
			",{\"field\" : \"orgType\", \"op\" : \"equal\",\"value\":\"I\"}" +
			"]," +
			" \"groups\" : [],\"op\" :\"and\"}"},
			nowrap : true,
			autoRowHeight : true,
			striped : true,
			collapsible : true,
			url : urlcfg.childInst,
			columns:[[
				{field:'id',title:'序号',align:'center',
					formatter: function(value,row,index){
						return index + 1;
					}
				},
				{field:'orgName',title:'机构名称',align:'center'},
				{field:'orgCode',title:'机构代码',align:'center'},
				{field:'operate',title:'操作',align:'center',
					formatter: function(value,row,index){
						return '<a href="javascript:void(0)" class="operate-items operate-items-edit" onclick="editInst(\''+row.orgId +'\')">编辑</a>' +
								'<a href="javascript:void(0)" class="operate-items" onclick="deleteInst(\''+row.orgId +'\')">删除</a>';
					}
				}
			]],
			pagination : true,
			rownumbers : false,
			pageList : [15,25,50,100],
			onLoadSuccess : function (data) {
				$(this).datagrid("fixColumnSize");
			}
		});
	}
	function getInstInfo(orgId){
//		console.log("getinstinfo");
		var url = urlcfg.orgInfo + "/" + orgId;
		$.post(url, function (data) {
//			console.log(data);
			var info = data.data;
			$("input[name='orgName']").val(info.orgName);
			$("input[name='orgNameShort']").val(info.orgNameShort);
			$("input[name='orgCode']").val(info.orgCode);
			$("input[name='orgArea']").val(info.orgArea);
			$("input[name='orgPhone']").val(info.orgPhone);
			$("input[name='orgLinkman']").val(info.orgLinkman);
			$("input[name='orgEmail']").val(info.orgEmail);
			$("input[name='orgWeburl']").val(info.orgWeburl);
			$("input[name='orgPostcode']").val(info.orgPostcode);
			$("input[name='orgAddress']").val(info.orgAddress);
			$("input[name='orgId']").val(info.orgId);
		})
	}
	var dialog = new Dialog({
		//trigger:'.sub-operate',
		width:'760px',
		height:'425px',
		//content:'./dialog-subadd.html',
		hasMask:false,
		title:'新增下属机构',
		ifEsc:false,
		closeTpl:'&#xe62a;'
	}).before('show',function(url){
//		var url = urlcfg.forwardAdd pId;
		this.set("content",url);
	});

	$(".subordinate").on('click', '.sub-operate', function(event) {
		dialog.show(urlcfg.forwardAdd + $.fn.zTree.getZTreeObj("tree").getSelectedNodes()[0].id);
		$(".ui-dialog").css('boxShadow', '0px 4px 16px #a8adb2');
		$(".ui-dialog-content").css('height', 425 - 40);
	});

	
	$(window).resize(function(event) {

		addressInput();

		//console.log(orgTab.get("activeIndex"));
		var tabActive = orgTab.get("activeIndex");
		if(tabActive == 1){

			$("#grid-table").datagrid("resize");

		}
		
	});

	//表单验证
	//var validator = new Validator({
	//	element: "#org-info",
	//	faisilently: true
	//});
    //
	//validator.addItem({
	//	element:'input[name=\'orgName\']',
	//	display: "组织机构名称",
	//	required: true,
	//	rule: 'minlength{"min":-1}',
	//}).addItem({
	//	element:'input[name=\'orgCode\']',
	//	display: "组织机构名称",
	//	required: true,
	//	rule: 'minlength{"min":-1}',
	//	rule: 'minlength{"min":-1}',
	//});
	/*
	document.onclick = function(event){

		window.parent.$(".spinner").hide();
		window.parent.$("#nav-more .sub-nav-container-white").hide();
		window.parent.$("#nav-more").removeClass('nav-more-active');

	}*/	//这段代码在本地运行时会报错
	//url

	var selectedNode = null;
	//创建机构树
	var setting = {
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "pId",
				rootPId : null
			}
		},
		view : {
			addHoverDom: addHoverDom,
			removeHoverDom: removeHoverDom,
			selectedMulti: false,
			showLine : false,
			fontCss: {width:"88%",height:"25px","line-height": "25px",overflow: "hidden","padding-top":"2px"}
		},
		callback : {
			onClick : ztreeNodeAction
		},
		async: {
			autoParam : [],
			//contentType : "application/json",
			dataFilter : function(treeId, parentNode, responseData){
				var data = responseData.data;
				if (data) {
					for(var i =0; i < data.length; i++) {
						data[i].icon = "../css/imgs/I.png";
					}
				}
				return data;
			},
			dataType : "text",
			enable : true,
			otherParam : [],
			type : "post",
			url: function(treeId, treeNode){
				return urlcfg.getNode + "I/" + treeNode.id;
			}
		}
	}
	//鼠标悬停事件
	function addHoverDom(treeId, treeNode) {
		
		var aObj = $("#" + treeNode.tId + "_a");
		aObj.css("background","#eeeeee");
		if ($("#diyBtn_"+treeNode.id).length>0) return;
		var editStr =  "<button type='button' class='diyBtn1' id='diyBtn_" + treeNode.id
			+ "' title='"+treeNode.name+"' onfocus='this.blur();' style='float:right;height:22px;width:40px;background-color:#ff6c00;border:0px'>详情</button>";
		aObj.append(editStr);
		var btn = $("#diyBtn_"+treeNode.id);
		if (btn) btn.bind("click", function(){
			parent.switchToDep(treeNode.id);
		});
	};
	function removeHoverDom(treeId, treeNode) {
		$("#" + treeNode.tId + "_a").css("background","none");
		$("#diyBtn_"+treeNode.id).unbind().remove();
		$("#diyBtn_space_" +treeNode.id).unbind().remove();
	};

	
	//获取组织机构信息
	function ztreeNodeAction (event, treeId, treeNode){
		$("#" + treeNode.tId + "_a").css("border","none");
		selectedNode = treeNode;
		var orgId = treeNode.id;
		switch(tagIndex){
		case 0:
			if(treeNode.id != "ROOT" || treeNode.name != "ROOT") {
				getInstInfo(orgId);
			}
			break;
		case 1:
			loadGrid(orgId);
			break;
		}
		tabInstId[tagIndex] = orgId;
		
	}
	function addTree(){
		$.post(urlcfg.instTree,function(data){
			if(data.status == 200){
				var nodes = data.data;
				var defaultSelect = null;
				for(var i=0;i<nodes.length;i++){
					nodes[i].icon = "/orm-server/orm/css/imgs/I.png";
					nodes[i].open = true;
					nodes[i].iconSkin="ztreeIcon";
				}
				$.fn.zTree.init($("#tree"), setting, data.data);
				var defaultSelect = $.fn.zTree.getZTreeObj("tree").getNodes()[0].children[0];
				$.fn.zTree.getZTreeObj("tree").selectNode(defaultSelect);
				ztreeNodeAction(null,null,defaultSelect);
			}else {
				console.log(data.message);
			}

		});
	}
	addTree();

	//更新组织机构信息
	var instForm = new Form({
		trigger: "#org-info",
		addUrl: urlcfg.updateInst
	});
	$("#save").click(function(){
		instForm.saveData({
			type: 1,
			successFn: function(result){
				if(result.status == 200){
//					var selected = $.fn.zTree.getZTreeObj("tree").getSelectedNodes()[0];
					ztreeNodeAction(null,null,selectedNode);
					$(".operate-status .status-message").text(result.message);
					$(".operate-status").show();
//					console.log("selected: " + selectedNode);
					$(".operate-status .status-message").text(result.message);
					$(".operate-status").show();
				}else{
					console.log(result.message);
				}
			},
			errorFn: function(result){
				console.log(result)
			}
		});
	})
//	getSelectOrgId = function (){
//		if(selectedNode){
//			return selectedNode.id;
//		}
//		return null;
//	}
});