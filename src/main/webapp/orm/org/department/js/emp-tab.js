define(function(require){
	var $ = require("$"),
		Tabs=require("inno/switchable/1.0.0/tabs-debug"),
		Select = require("inno/select/1.0.0/select-debug"),
		Scroll = require("inno/scroll/1.0.0/scroll-debug");
		require("easyui");
	
	
	var depTab = new Tabs({
		element: '.dep-tabs',
        triggers: '.dep-tabs-nav li',
        panels: '.dep-tabs-content .content-panel',
        triggerType:"click",
        closeStatus:false,
        activeIndex:0
	});

	new Select({
		trigger:'#cond-system',
		width:'200px',
		model:[
			{value:'0', text:'组织权限管理系统'},
			{value:'1', text:'综合管理'}
		]
	}).render();

	function addressInput(){

		itemsW = $(".basic-list-items")[0].offsetWidth,
		labelW = $(".list-items-label")[0].offsetWidth,
		inpW = $(".list-items-inp")[0].offsetWidth,
		fullWidth = $(".inp-address").parent().width();

		$(".inp-address").css('width', fullWidth - labelW - (itemsW - labelW - inpW) - 12);

	}
	addressInput();
	$(".codeinf-title").on('click', '.title-ico', function(event) {

		var _detail = $(this).parents(".codeinf-sec"),
			_detail_act = _detail.attr("active");

		if(_detail_act == "0"){

			_detail.addClass('codeinf-sec-fold');
			_detail.attr('active', '1');

		}else if(_detail_act == "1"){

			_detail.removeClass('codeinf-sec-fold');
			_detail.attr('active', '0');

		}		
		
	});

	//待选角色滚动条
	var one = new Scroll({
		trigger:"#col-tbody-wait .ui-scroll",
		container:"#col-tbody-wait .ui-scroll-container"
	});

	//已选角色滚动条
	var two = new Scroll({
		trigger:"#col-tbody-already .ui-scroll",
		container:"#col-tbody-already .ui-scroll-container"
	});

	
	$(".dep-tabs-nav").on('click', 'li', function(event) {

		var thisIndex = $(this).index();

		if(thisIndex == 1){
			$("#grid-table").datagrid({
				nowrap : true,
				autoRowHeight : true,
				striped : true,
				collapsible : true,
//				url : 'datagrid_data.json',
				columns:[[
					{field:'id',title:'序号',align:'center'},
					{field:'name',title:'机构名称',align:'center'},
					{field:'code',title:'机构代码',align:'center'},
					{field:'operate',title:'操作',align:'center'}
				]],
				pagination : true,
				rownumbers : false
			});

			setTimeout(function(){
				$("#grid-table").datagrid("resize");
			},500);
			
		}else if(thisIndex == 0){
			addressInput();
		}
		
	});
});