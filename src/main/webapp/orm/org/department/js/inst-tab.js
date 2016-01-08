define(function(require){
	var $ = require("$"),
		Tabs=require("inno/switchable/1.0.0/tabs-debug"),
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
		
		switch(thisIndex){
		case 0:
			break;
		case 1:
			break;
		case 2:
			break;
		case 3:
			break;
		}

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
	
	function loadGrid(orgId,type){
//		console.log("loadgrid");
		switch(type){
		case "I":
			break;
		case "O":
			break;
		case "P":
			break;
		case "E":
			break;
		}
		$(tableId).empty();
		$(tableId).datagrid({
			queryParams : {queryCondition : "{\"rules\" : [{\"field\" : \"parentOrgId\", \"op\" : \"equal\",\"value\":\""+instId+"\"}" +
			",{\"field\" : \"orgType\", \"op\" : \"equal\",\"value\":\""+type+"\"}" +
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
});