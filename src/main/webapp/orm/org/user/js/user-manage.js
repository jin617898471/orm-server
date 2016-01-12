define(function(require){
	var $ = require("$");
	Form = require("form");
	require("easyui");
	Dialog = require("inno/dialog/1.0.0/dialog-debug");
	Confirmbox=require("inno/dialog/1.0.0/confirmbox-debug");
	
	var basePath = "/orm-server/";
	var urlcfg= {
		listUser : basePath + "user/list",
		forwardEditUser : basePath + "user/forward/edit/",
		forwardAddUser : basePath + "user/forward/add",
		forwardChangePwd : basePath + "user/forward/changepwd/",
		cancelUser : basePath + "user/cancel/",
		activeUser : basePath + "user/active/",
		deleteUser : basePath + "user/delete/",
	}
	var form =new Form({
		trigger:"#searchFrom"
	});
	inittable = function (QUERYCONDITION){
		$("#grid-table").datagrid({
			queryParams : {queryCondition:QUERYCONDITION},
			nowrap : true,
			autoRowHeight : true,
			striped : true,
			collapsible : true,
			url : urlcfg.listUser,
			columns:[[
				{field:'id',title:'序号',align:'center',
					formatter: function(value,row,index){
						var num = index + 1;
						return "<span class='id_number'>"+num+"</span><input type='checkbox' class='id_checkbox' />";
					}
				},
				{field:'userName',title:'用户名称',align:'center'},
				{field:'userAcct',title:'登录账户',align:'center'},
				{field:'userStatus',title:'用户状态',align:'center'},
				{field:'userSex',title:'性别',align:'center',
					formatter: function(value,row,index){
						if(row.userSex == 'MALE'){
							return "男";
						}else{
							return "女";
						}
					}
				},
				{field:'userMobile',title:'手机号码',align:'center'},
				{field:'institution',title:'所属机构',align:'center'},
				{field:'department',title:'所属部门',align:'center'},
				{field:'operate',title:'操作',align:'center',
					formatter: function(value,row,index){
						if(row.userStatus == '正常'){
							return "<a class='operate-items items-space' onclick='editUser(\""+row.userId +"\")'>编辑</a><a class='operate-items items-space' onclick='cancelUser(\""+row.userId +"\")'>注销</a><a class='operate-items items-space' onclick='changePwd(\""+row.userId +"\")'>修改密码</a><a class='operate-items'  onclick='deleteUser(\""+row.userId +"\")'>删除</a>"
						}else{
							return "<a class='operate-items items-space' onclick='activeUser(\""+row.userId +"\")'>激活</a><a class='operate-items' onclick='deleteUser(\""+row.userId +"\")'>删除</a>";
						}
					}
				}
			]],
			pagination : true,
			rownumbers : false
		});
	}
	var edit = new Dialog({
		width:'760px',
		height:'565px',
		hasMask:false,
		title:'用户编辑',
		ifEsc:false,
		closeTpl:'&#xe62a;'
	}).before('show',function(id){
    	var url=urlcfg.forwardEditUser + id;
    	this.set('content',url);
    });
	var change = new Dialog({
	 	width:'400px',
        height:'200px',
        hasMask:false,
        scrolling:true,
        title:"修改密码"
    }).before('show',function(id){
    	var url=urlcfg.forwardChangePwd+id;
    	this.set('content',url);
    });
	changePwd = function (userId){
		change.show(userId);
		$(".ui-dialog").css('boxShadow', '0px 4px 16px #a8adb2');
	}
	editUser = function (userId){
		edit.show(userId);
		$(".ui-dialog").css('boxShadow', '0px 4px 16px #a8adb2');
	}
	cancelUser = function(userId){
		Confirmbox.confirm('是否确定要注销该用户？','',function(){
			var parameter={
					url:urlcfg.cancelUser + userId,
				    type:"POST",
				    async:false,
				    success:function(data){
				    	refreshTable();
				    },
				    error:function(result){
				    	Confirmbox.alert('删除失败！');
				    }
				};
			$.ajax( parameter );
		},function(){
			
		});
	}
	activeUser = function(userId){
		Confirmbox.confirm('是否确定激活该用户？','',function(){
			var parameter={
					url:urlcfg.activeUser + userId,
				    type:"POST",
				    async:false,
				    success:function(data){
				    	refreshTable();
				    },
				    error:function(result){
				    	Confirmbox.alert('删除失败！');
				    }
				};
			$.ajax( parameter );
		},function(){
			
		});
	}
	deleteUser = function(userId){
		Confirmbox.confirm('是否确定要删除该用户？','',function(){
			var parameter={
					url:urlcfg.deleteUser + userId,
				    type:"POST",
				    async:false,
				    success:function(data){
				    	refreshTable();
				    },
				    error:function(result){
				    	Confirmbox.alert('删除失败！');
				    }
				};
			$.ajax( parameter );
		},function(){
			
		});
	}
	inittable(null);
	
	$("#search").click(function(){
		var s = {
				"rules":[],
				"groups":[],
				"op":"and"
		};
		var QUERYCONDITION =  form.initSearch(s);
		inittable(QUERYCONDITION);
	});
	$("#reset").click(function(){
		var searchForm = form.get("eleArray");
		searchForm.map(function (i, elem) {
			$(this).val("");
		});
		inittable(null);
	});
	$("#refresh").click(function(){
		refreshTable();
	});
	
	new Dialog({
		trigger:'#addUser',
		width:'760px',
		height:'525px',
		content:urlcfg.forwardAddUser,
		hasMask:false,
		title:'用户新增',
		ifEsc:false,
		closeTpl:'&#xe62a;'
	})
	refreshTable = function(){
		$("#grid-table").datagrid('reload');
	}
	
	$(window).resize(function(event) {
		$("#grid-table").datagrid("resize");
	});

});