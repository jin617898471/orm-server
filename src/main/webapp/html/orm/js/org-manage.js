define(function(require){
	var $ = require("$"),
		Tabs=require("inno/switchable/1.0.0/tabs-debug"),
		Dialog = require("inno/dialog/1.0.0/dialog-debug");
	require("gallery/ztree/3.5.2/core-debug");
	require("gallery/ztree/3.5.14/ztree-debug.css");
	Validator = require("inno/validator/1.0.0/validator-debug");

	var orgTab = new Tabs({
		element: '.org-tabs',
        triggers: '.org-tabs-nav li',
        panels: '.org-tabs-content .content-panel',
        triggerType:"click",
        closeStatus:false
	});

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

	//基本信息之地址输入框的宽度
	addressInput();

	//datagrid
	require("easyui");
	var tagIndex = 0;
	//重新加载datagrid
	$(".org-tabs-nav").on('click', 'li', function(event) {
		var treeNode = $.fn.zTree.getZTreeObj("tree").getSelectedNodes()[0];
		var instId = treeNode.id;
		console.info("id:" + instId);
		var thisIndex = $(this).index();
		tagIndex = thisIndex;
		if(thisIndex == 1){
			loadGrid(instId);
			setTimeout(function(){
				$("#grid-table").datagrid("resize");
			},500);
			
		}else if(thisIndex == 0){
			addressInput();
		}
		
	});

	function loadGrid(instId){
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
				{field:'operate',title:'操作',align:'center'}
			]],
			pagination : true,
			rownumbers : false,
			pageList : [15,25,50,100],
			onLoadSuccess : function (data) {
				$(this).datagrid("fixColumnSize");
			}
		});
	}
	new Dialog({
		trigger:'.sub-operate',
		width:'760px',
		height:'425px',
		content:'./dialog-subadd.html',
		hasMask:false,
		title:'新增下属机构',
		ifEsc:false,
		closeTpl:'&#xe62a;'
	});

	$(".subordinate").on('click', '.sub-operate', function(event) {
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
	var validator = new Validator({
		element: "#org-info",
		faisilently: true
	});

	validator.addItem({
		element:'#org-name',
		display: "组织机构名称",
		required: true,
		rule: 'minlength{"min":-1}',
	}).addItem({
		element:'#org-code',
		display: "组织机构名称",
		required: true,
		rule: 'minlength{"min":-1}',
	});
	/*
	document.onclick = function(event){

		window.parent.$(".spinner").hide();
		window.parent.$("#nav-more .sub-nav-container-white").hide();
		window.parent.$("#nav-more").removeClass('nav-more-active');

	}*/	//这段代码在本地运行时会报错

	var basePath = "/orm-server/";
	var urlcfg= {
		instTree : basePath + "org/institutionTree",
		orgInfo : basePath + "org/info",
		childInst : basePath + "org/institution/children"
	}

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
			selectedMulti: false,
			showLine : false
		},
		callback : {
			onClick : getInstInfo
		}
	}
	function addTree(){
		$.post(urlcfg.instTree,function(data){
			var nodes = data.data;
			var defaultSelect = null;
			for(var i=0;i<nodes.length;i++){
				nodes[i].icon = "../css/imgs/I.png";
				nodes[i].open = true;
			}
			$.fn.zTree.init($("#tree"), setting, data.data);
			var defaultSelect = $.fn.zTree.getZTreeObj("tree").getNodes()[0].children[0];
			$.fn.zTree.getZTreeObj("tree").selectNode(defaultSelect);
			getInstInfo(null,null,defaultSelect);
		});
	}
	addTree();

	//获取组织机构信息
	function getInstInfo (event, treeId, treeNode){
		var url = urlcfg.orgInfo + "/" + treeNode.id;
		if(treeNode.id != "ROOT" || treeNode.name != "ROOT") {
			$.post(url, function (data) {
				console.log(data);
				var info = data.data;
				$("#org-name").val(info.orgName);
				$("#org-name-short").val(info.orgNameShort);
				$("#org-code").val(info.orgCode);
				$("#org-region").val(info.orgArea);
				$("#org-phone").val(info.orgPhone);
				$("#org-contact-people").val(info.orgLinkman);
				$("#org-email").val(info.orgEmail);
				$("#org-web").val(info.orgWeburl);
				$("#org-postcode").val(info.orgPostcode);
				$("#org-address").val(info.orgAddress);
			})
		}
		if(tagIndex == 1){
			loadGrid(treeNode.id);
		}

	}
});