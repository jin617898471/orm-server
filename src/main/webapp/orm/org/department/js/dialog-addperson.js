define(function(require){
	var $ = require("$"),
		Tabs=require("inno/switchable/1.0.0/tabs-debug"),
		AutoComplete =  require("autocomplete"),
		Select = require("inno/select/1.0.0/select-debug"),
		Calendar = require('inno/calendar/1.0.0/calendar-debug');

	new Tabs({
		element:'.add-tabs',
		triggers:'.add-tabs-nav li',
		panels:'.add-tabs-content .content-panel',
		triggerType:'click',
		closeStatus:false,
		activeIndex:0
	});

	$(".password-ico").click(function(event) {

		var pwd_active = $(this).attr("active"),
			pwd_p = $(this).parent().find(".inps-pwd-p"),
			pwd_t = $(this).parent().find('.inps-pwd-t'),
			pwd_value;

		if(pwd_active == "0"){

			$(this).attr('active', '1');
			$(this).parent().addClass('password-show');
			
			//IE下无法得到type属性，不支持该命令
			if(window.ActiveXObject){

				pwd_value = pwd_p.val();
				pwd_t.val(pwd_value);
				pwd_p.addClass('hidden');
				pwd_t.removeClass('hidden');

			}else{
				pwd_p[0].type = 'text';
			}

		}else if(pwd_active == "1"){

			$(this).attr('active', '0');
			$(this).parent().removeClass('password-show');
			
			if(window.ActiveXObject){

				pwd_value = pwd_t.val();
				pwd_p.val(pwd_value);
				pwd_t.addClass('hidden');
				pwd_p.removeClass('hidden');

			}else{
				pwd_p[0].type = 'password';
			}

		}
		
	});

	new AutoComplete({
		trigger:'.inps-name',
		width:'220px',
		dataSource:['aaa','abc','add','agh']
	}).render();

	//证件类型
	new Select({
		trigger:'#sel-certype',
		width:'220px',
		model:[
			{value:'0', text:'身份证'},
			{value:'1', text:'驾驶证'},
			{value:'2', text:'港澳通行证'}
		]
	}).render();

	//政治面貌
	new Select({
		trigger:'#sel-political',
		width:'220px',
		model:[
			{value:'0', text:'党员'},
			{value:'1', text:'团员'}
		]
	}).render();

	//员工状态
	new Select({
		trigger:'#employee-status',
		width:'220px',
		model:[
			{value:'0', text:'正式工'},
			{value:'1', text:'试用期'},
			{value:'2', text:'实习生'}
		]
	}).render();

	//员工职级
	new Select({
		trigger:'#employee-level',
		width:'220px',
		model:[
			{value:'0', text:'P1'},
			{value:'1', text:'M1'}
		]
	}).render();

	//入职日期
	var entryTime = new Calendar({
		trigger:'.times-entry'
	});

	$(".times-entry-ico").click(function(event) {
		entryTime.show();
		entryTime.autohide();
	});

	//离职日期
	var quitTime = new Calendar({
		trigger:'.times-quit'
	});

	$(".times-quit-ico").click(function(event) {
		quitTime.show();
		quitTime.autohide();
	});

	//所属机构
	new Select({
		trigger:'#sel-org',
		width:'220px',
		model:[
			{value:'0', text:'机构1'},
			{value:'1', text:'机构2'}
		]
	}).render();

	//所属部门
	new Select({
		trigger:'#sel-dep',
		width:'220px',
		model:[
			{value:'0', text:'综合管理部'},
			{value:'1', text:'财务部'},
			{value:'2', text:'智慧交通事业部'},
			{value:'3', text:'研发中心'},
			{value:'4', text:'低碳发展事业部'}
		]
	}).render();

	//所属岗位
	new Select({
		trigger:'#sel-post',
		width:'220px',
		model:[
			{value:'0', text:'人事主管'},
			{value:'1', text:'Java 工程师'}
		]
	}).render();

});