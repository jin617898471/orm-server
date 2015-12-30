define(function(require){
	var $ = require("$"),
		Menu = require("menu"),
		Tabs=require("inno/switchable/1.0.0/tabs-debug");

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

	new Tabs({
		element: '.frame-tabs',
        triggers: '.frame-tabs-nav li',
        panels: '.frame-tabs-content .content-panel',
        triggerType:"click",
        closeStatus:'&#xe61d;',
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

	$(window).resize(function() {
		if(window.ActiveXObject){
			$(".col-sub").height($(".col-main").height());
		}
		$(".content-panel iframe").css('height', $(".main-wrap").height() - 48);
	});

});