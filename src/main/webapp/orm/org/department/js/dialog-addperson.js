define(function(require){
	var $ = require("$"),
		Tabs=require("inno/switchable/1.0.0/tabs-debug"),
		AutoComplete =  require("autocomplete"),
		Select = require("inno/select/1.0.0/select-debug"),
		Calendar = require('inno/calendar/1.0.0/calendar-debug');
	Form = require("form");

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
		name: 'userCardtype',
		model:[
			{value:'0', text:'身份证'},
			{value:'1', text:'驾驶证'},
			{value:'2', text:'港澳通行证'}
		]
	}).render();

	//政治面貌
	new Select({
		trigger:'#sel-political',
		width: '220px',
		name: 'empParty',
		model:[
			{value:'0', text:'党员'},
			{value:'1', text:'团员'}
		]
	}).render();

	//员工状态
	new Select({
		trigger:'#employee-status',
		width:'220px',
		name: 'empStatus',
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
		name: 'empDegree',
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

	var ulIndex = 0;
	//离职日期
	var quitTime = new Calendar({
		trigger:'.times-quit'
	});

	$(".times-quit-ico").click(function(event) {
		quitTime.show();
		quitTime.autohide();
	});

//	//所属机构
//	new Select({
//		trigger:'#sel-org',
//		width:'220px',
//		name:'institution',
//		model:[
//			{value:'0', text:'机构1'},
//			{value:'1', text:'机构2'}
//		]
//	}).render().on('change',function(target,prev){
//		console.log("select change");
//		console.log(target);
//		console.log(prev);
//		var t = $(this);
//		console.log(t);
//	});;
//	
//	//所属部门
//	new Select({
//		trigger:'#sel-dep',
//		width:'220px',
//		name:'department',
//		model:[
//			{value:'0', text:'综合管理部'},
//			{value:'1', text:'财务部'},
//			{value:'2', text:'智慧交通事业部'},
//			{value:'3', text:'研发中心'},
//			{value:'4', text:'低碳发展事业部'}
//		]
//	}).render();
//
//	//所属岗位
//	new Select({
//		trigger:'#sel-post',
//		width:'220px',
//		name:'post',
//		model:[
//			{value:'0', text:'人事主管'},
//			{value:'1', text:'Java 工程师'}
//		]
//	}).render();

	
	var basePath = "/orm-server/";
	var urlcfg= {
		addUser : basePath + "user/add",
		getInstOptions : basePath + "org/institution/options",
		getInstChildrenOptions : basePath + "org/institution/children/options/",
		getPostOptions : basePath + "org/post/options/",
	}
	var sign = $("#sign").val();
	var userForm = new Form({
		trigger: "#userInfo",
		addUrl: urlcfg.addUser
	});
	if(sign == "org"){
		newSelects(ulIndex,true);
	}else{
		newSelects(ulIndex,false);
	}
	function newSelects(index,init){
		$.ajax({
			url: urlcfg.getInstOptions,
            type:"post",
            success:function(result){
				if(result.status == 200){
					inst.syncModel(result.data);
					if(init){
						var rootId = parent.getRootNodeId();
						console.log("rootId:" + rootId);
						inst.selectValue(rootId);
					}
//					var instId = $("#select-institution"+index).val();
//					$.ajax({
//						url: urlcfg.getInstChildrenOptions + instId,
//			            type:"post",
//			            success:function(result){
//							if(result.status == 200){
//								var depOptions = result.data.depOptions;
//								var postOptions = result.data.postOptions;
//								if(depOptions && depOptions!=""){
//									dep.syncModel(depOptions);
//									dep.attrs.disabled = {value:false};
//								}
//								if(postOptions && postOptions!=""){
//									post.syncModel(postOptions);
//									post.attrs.disabled = {value:false};
//								}
//							}else{
//								console.log(result.message);
//							}
//			            },
//			            error:function(result, err){
//			            }
//					});
				}else{
					console.log(result.message);
				}
            },
            error:function(result, err){
            }
		});
		//所属机构
		var inst = new Select({
			trigger:'#sel-inst'+index,
			width:'220px',
			name:'institution'+index,
			model:[
				{value:'', text:'选择机构'},
			]
		}).render().on('change',function(target,prev){
			var instId = $("#select-institution"+index).val();
//			var t = $(this);
//			console.log(this);
			$.ajax({
				url: urlcfg.getInstChildrenOptions + instId,
	            type:"post",
	            success:function(result){
					if(result.status == 200){
						var depOptions = result.data.depOptions;
						var postOptions = result.data.postOptions;
						if(depOptions && depOptions!=""){
							dep.syncModel(depOptions);
							dep.attrs.disabled = {value:false};
							console.log(dep);
						}else{
							dep.syncModel([{value:null, text:'请先选择机构'}]);
							dep.attrs.disabled = {value:true};
						}
						if(postOptions && postOptions!=""){
							post.syncModel(postOptions);
							post.attrs.disabled = {value:false};
						}else{
							post.syncModel([{value:null, text:'请先选择机构或部门'}]);
							post.attrs.disabled = {value:true};
							console.log(post);
						}
						if(init){
							var node = parent.getSelectNode();
							var type = node.attributes.type;
							console.log(type);
							if(type == "O"){
								dep.selectValue(node.id);
							}else if(type == "P"){
								dep.selectValue(node.getParentNode().id)
							}
						}
						
					}else{
						console.log(result.message);
					}
	            },
	            error:function(result, err){
	            }
			});
		});
		
		//所属部门
		var dep = new Select({
			trigger:'#sel-dep'+index,
			width:'220px',
			disabled: true,
			name:'department'+index,
			model:[
				{value:null, text:'请先选择机构'},
			],
		}).render().on('change',function(target,prev){
			var depId = $("#select-department"+index).val();
//			var t = $(this);
//			console.log(this);
			$.ajax({
				url: urlcfg.getPostOptions + depId,
	            type:"post",
	            success:function(result){
					if(result.status == 200){
						var postOptions = result.data
						if(postOptions && postOptions!=""){
							post.syncModel(postOptions);
							post.attrs.disabled = {value:false};
						}else{
							post.syncModel([{value:null, text:'请先选择机构或部门'}]);
							post.attrs.disabled = {value:true};
							console.log(post);
						}
						if(init){
							var node = parent.getSelectNode();
							var type = node.attributes.type;
							if(type == "P"){
								post.selectValue(node.id);
							}
						}
					}else{
						console.log(result.message);
					}
	            },
	            error:function(result, err){
	            }
			});
		});;

		//所属岗位
		var post = new Select({
			trigger:'#sel-post'+index,
			width:'220px',
			disabled: true,
			name:'post'+index,
			model:[
				{value:null, text:'请先选择机构或部门'},
			]
		}).render();
		
		
		ulIndex++;
	}
	function createUl(index){
		var html='<ul class="clearfix info-organize">' + 
					'<li class="info-items">'+
						'<label class="info-label">所属机构：</label>'+
						'<div class="ui-select-trigger ui-select-trigger-none w218 institution" id="sel-inst'+index+'">'+
							'<span data-role="trigger-content"></span>'+
							'<span class="trigger-ico"></span>'+
						'</div>'+
					'</li>'+
					'<li class="info-items">'+
						'<label class="info-label">所属部门：</label>'+
						'<div class="ui-select-trigger ui-select-trigger-none w218 department" id="sel-dep'+index+'">'+
							'<span data-role="trigger-content"></span>'+
							'<span class="trigger-ico"></span>'+
						'</div>'+
					'</li>'+
					'<li class="info-items">'+
						'<label class="info-label">所属岗位：</label>'+
						'<div class="ui-select-trigger ui-select-trigger-none w218 post" id="sel-post'+index+'">'+
							'<span data-role="trigger-content"></span>'+
							'<span class="trigger-ico"></span>'+
						'</div>'+
					'</li>'+
					'<li class="info-items">'+
						'<input type="button" class="org-delete" value="删除该组织" />'+
					'</li>'+
				'</ul>';
		return html;
	}
	
	$(".organize-add").click(function(event){
		console.log("add org");
		var ul = $("#orgInfo .info-organize:last");
		var html = createUl(ulIndex);
//		console.log(html)
		ul.after("</br>"+html);
		newSelects(ulIndex);
		$(".org-delete").off("click"); 
		$(".org-delete").on("click",function(event){
			var ul = $(this).parent().parent();
			var next = $(this).parent().parent().next();
			var prev = $(this).parent().parent().prev();
			ul.remove();
			console.log(next);
			console.log(prev);
			if(next[0].nodeName=="BR"){
				next.remove();
			}else{
				prev.remove();
			}
		});
		;
			
	});
	$(".org-delete").on("click",function(event){
		var ul = $(this).parent().parent();
		var next = $(this).parent().parent().next();
		var prev = $(this).parent().parent().prev();
		ul.remove();
		if(next[0].nodeName=="BR"){
			next.remove();
		}
	});
	$("#save").click(function(event){
//		var form = $("#orgInfo");
		userForm.getData();
		var json =userForm.get("json");
		console.log(json);
		var arrayOrg = new Array();
		$("#orgInfo .info-organize").each(function(){
			var inst = $(this).children("li:first-child").children("input").val();
			var dep = $(this).children("li:eq(1)").children("input").val();
			var post = $(this).children("li:eq(2)").children("input").val();
			var org = {
					institution: inst,
					department: dep,
					post: post
			}
			arrayOrg.push(org);
		});
		var o = window.JSON.stringify(arrayOrg);
		json.orgs = o;
		console.log(json);
		$.ajax({
			url: urlcfg.addUser,
            data: json,
            type:"post",
            success:function(result){
            	if(result.status == 200){
            		if(sign == "org"){
            			parent.refreshTree("E");
            		}else{
            			parent.refreshTable();
            		}
            	}else{
            		console.log(result.message);
            	}
            },
            error:function(result, err){
            	options.errorFn(result);					
            }
		});
	});
	
	$("#btnCancel").click(function(){
		window.frameElement.trigger('close'); 
	});
});