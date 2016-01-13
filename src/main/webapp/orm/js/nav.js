define(function(require){
	var $ = require("$"),
		Menu = require("menu"),
		Tabs=require("inno/switchable/1.0.0/tabs-debug");
		Dialog = require("inno/dialog/1.0.0/dialog-debug");
		Confirmbox=require("inno/dialog/1.0.0/confirmbox-debug");
	
	OrmJsObj.resourceInit(document);
//	$(".sidebar-unfold").show();
//	$(".sidebar-fold").show();

	//超级管理员
	$(".user-info-admin").click(function(event) {
		$("#nav-more .sub-nav-container-white").hide();
		$("#nav-more").removeClass('nav-more-active');
		var spinner = $(this).find('.spinner'),
		    isShow = spinner.css("display");
		if(isShow == "none"){
			spinner.show();
		}else{
			spinner.hide();
		}
	});

	//new Menu({trigger:'#nav-more'});

	$(".sub-nav-content").width($(".subnav-items").length * 160);

	//更多应用
	$("#nav-more").click(function(event) {

		$(".user-info-admin .spinner").hide();

		var sub_nav = $(this).find('.sub-nav-container-white'),
		    sub_show = sub_nav.css("display");

		if(sub_show == "none"){

			$(this).addClass('nav-more-active');
			sub_nav.show();

		}else{

			$(this).removeClass('nav-more-active');
			sub_nav.hide();

		}
		
	});

	document.onclick = function(event){
		var e = event || window.event;
		var elem = e.srcElement || e.target;
		while(elem)
		{
			if(elem.className == "user-info-item user-info-admin" || elem.id == "nav-more"){
				return;
			}
			elem = elem.parentNode;
		}
		$(".spinner").hide();

		$("#nav-more .sub-nav-container-white").hide();
		$("#nav-more").removeClass('nav-more-active');
	}

	//左侧菜单栏二级
	$(".items-sub").on('click', '.items-sub-list', function(event) {

		$(".items-sub .items-sub-list").removeClass('items-sub-list-active');
		$(this).addClass('items-sub-list-active');
		var selectedMenu = $(this).attr("name");
		console.log(tabs);
		switch(selectedMenu){
		case "inst":
			var flag = checkTabExist("机构管理");
			if(!flag){
				addInstTab();
			}
			break;
		case "dep":
			break;
		case "workgroup":
			break;
		case "user":
			var flag = checkTabExist("用户管理");
			if(!flag){
				addUserTab();
			}
			break;
		case "system":
			var flag = checkTabExist("系统管理");
			if(!flag){
				addSystemTab();
			}
			break;
		case "role":
			var flag = checkTabExist("角色管理");
			if(!flag){
				addRoleTab();
			}
			break;
		case "resource":
			var flag = checkTabExist("资源管理");
			if(!flag){
				addResourceTab();
			}
			break;
		case "code":
			var flag = checkTabExist("代码管理");
			if(!flag){
				addCodeTab();
			}
			break;
		case "authorize":
			var flag = checkTabExist("授权管理");
			if(!flag){
				addAuthorizeTab();
			}
			break;
		}
		console.log();
		
	});

	$(".sidebar-items").on('click', '.items-sec', function(event) {

		var items_sub = $(this).parent().find('.items-sub'),
			items_sub_show = items_sub.css("display");

		if(items_sub_show == "none"){
			items_sub.show();
		}else{
			items_sub.hide();
		}
		
	});

	//左侧菜单栏收缩与展开
	$(".sidebar-btn").click(function(event) {

		var active = $(this).attr("active");

		if(active == "0"){

			$(this).parents(".col-main").addClass('col-fold');

		    $(this).attr('active', '1');

		}else if(active == "1"){

			$(this).parents(".col-main").removeClass('col-fold');

		    $(this).attr('active', '0');

		}
		
	});
	var tabIndex = 0;
	$(".frame-tabs-nav").on('click', 'li', function(event) {
		tabIndex = $(this).index();
		console.log("tabIndex:" + tabIndex);
	});

	tabs = new Tabs({
		element: '.frame-tabs',
        triggers: '.frame-tabs-nav li',
        panels: '.frame-tabs-content .content-panel',
        triggerType:"click",
        closeStatus:'&#xe61d;',
//        activeIndex:1
	});

	$(".content-panel iframe").css('height', $(".main-wrap").height() - 48);

	//解决IE下左侧栏高度不为100%的问题
	if(window.ActiveXObject){
		$(".col-sub").height($(".col-main").height());
	}

	//当tab关掉剩0个的时候
	$(".frame-tabs-nav").on('click', '.ui-tabs-trigger-close', function(event) {
		if($(".frame-tabs-nav .ui-tabs-trigger").length == 1){
			$(".frame-tabs-content").css('borderBottom', '1px solid #e1e3e5');
		}
	});
	
	switchToDep = function(orgId){
		var tagExist = checkTabExist("部门管理");
		if(!tagExist){
			addDepTab(orgId);
		}else{
			department.window.addDepTree(orgId);
		}
	}
	function checkTabExist(tabName){
		var nav = $(".frame-tabs-nav li");
		var tagExist = false;
		for(var i=0;i<nav.length;i++){
			if($(nav[i]).text().startsWith(tabName)){
				tagExist = true;
				tabs.switchTo(i);
				tabIndex = i;
			}
		}
		return tagExist;
	}
	function addInstTab(){
		var index = tabs.get("length");
		var content ="<iframe id='institution' name='institution' src='org/institution/frame/org-manage.jsp' frameborder='0'></iframe>";
		tabs.addPanel("机构管理",content,true,index);
		$("iframe[name=institution]").parent().addClass("content-panel");
		tabs.switchTo(index);
		$(".content-panel iframe").css('height', $(".main-wrap").height() - 48);
		tabIndex = index;
	}
	function addDepTab(orgId){
		var index = tabs.get("length");
		var content ="<iframe id='department' name='department' src='/orm-server/org/forward/department/"+orgId+"' frameborder='0'></iframe>";
		tabs.addPanel("部门管理",content,true,index);
		$("iframe[name=department]").parent().addClass("content-panel");
		tabs.switchTo(index);
		$(".content-panel iframe").css('height', $(".main-wrap").height() - 48);
		tabIndex = index;
	}
	function addUserTab(){
		var index = tabs.get("length");
		var content ="<iframe id='user' name='user' src='/orm-server/user/forward/manage' frameborder='0'></iframe>";
		tabs.addPanel("用户管理",content,true,index);
		$("iframe[name=user]").parent().addClass("content-panel");
		tabs.switchTo(index);
		$(".content-panel iframe").css('height', $(".main-wrap").height() - 48);
		tabIndex = index;
	}
	function addSystemTab(){
		var index = tabs.get("length");
		var content ="<iframe id='system' name='system' src='/orm-server/authority/system/forward/manage' frameborder='0'></iframe>";
		tabs.addPanel("系统管理",content,true,index);
		$("iframe[name=system]").parent().addClass("content-panel");
		tabs.switchTo(index);
		$(".content-panel iframe").css('height', $(".main-wrap").height() - 48);
		tabIndex = index;
	}
	function addRoleTab(){
		var index = tabs.get("length");
		var content ="<iframe id='role' name='role' src='/orm-server/authority/role/forward/manage' frameborder='0'></iframe>";
		tabs.addPanel("角色管理",content,true,index);
		$("iframe[name=role]").parent().addClass("content-panel");
		tabs.switchTo(index);
		$(".content-panel iframe").css('height', $(".main-wrap").height() - 48);
		tabIndex = index;
	}
	function addResourceTab(){
		var index = tabs.get("length");
		var content ="<iframe id='resource' name='resource' src='/orm-server/authority/resource/forward/manage' frameborder='0'></iframe>";
		tabs.addPanel("资源管理",content,true,index);
		$("iframe[name=resource]").parent().addClass("content-panel");
		tabs.switchTo(index);
		$(".content-panel iframe").css('height', $(".main-wrap").height() - 48);
		tabIndex = index;
	}
	function addCodeTab(){
		var index = tabs.get("length");
		var content ="<iframe id='code' name='code' src='/orm-server/authority/code/forward/manage' frameborder='0'></iframe>";
		tabs.addPanel("代码管理",content,true,index);
		$("iframe[name=code]").parent().addClass("content-panel");
		tabs.switchTo(index);
		$(".content-panel iframe").css('height', $(".main-wrap").height() - 48);
		tabIndex = index;
	}
	function addAuthorizeTab(){
		var index = tabs.get("length");
		var content ="<iframe id='authorize' name='authorize' src='/orm-server/authority/role/forward/authorize' frameborder='0'></iframe>";
		tabs.addPanel("授权管理",content,true,index);
		$("iframe[name=authorize]").parent().addClass("content-panel");
		tabs.switchTo(index);
		$(".content-panel iframe").css('height', $(".main-wrap").height() - 48);
		tabIndex = index;
	}
	
	
	$(window).resize(function() {
		if(window.ActiveXObject){
			$(".col-sub").height($(".col-main").height());
		}
		$(".content-panel iframe").css('height', $(".main-wrap").height() - 48);
	});
	


});