define(function(require){
	var $=require("$");
	require("gallery/ztree/3.5.2/core-debug");
	require("gallery/ztree/3.5.14/ztree-debug.css");
	Confirmbox=require("inno/dialog/1.0.0/confirmbox-debug");
	Dialog=require("inno/dialog/1.0.0/dialog-debug");
	AutoComplete=require("arale/autocomplete/1.3.0/autocomplete-debug");
	Calendar = require("inno/calendar/1.0.0/calendar-debug");
	var artTemplate=require("../../../org/org/js/art-template");
	
	var setting = {
		data:{
			key: {
				children:"child",
				name:"text",
			}
		},
		view: {
			addDiyDom: addDiyDom
		},
		async: {
			enable: true,
			url: function(treeId, treeNode){
				var type = treeNode.attrs.orgType;
				if("P"==type){ 
					return urlCfg["p_user_list"]+treeNode.value;
				}
				return [];
			},
			dataFilter:function( treeId, parentNode, data ){
				$.each(data,function(index,node){//把后端查询出来的用户list改造成ztree能识别的对象
					var attrs = $.extend(true,{},node);
					attrs.orgType = "RY";
					node.attrs = attrs;
					node.iconSkin = "org_RY";;
					node.value = node.userId;
					node.text = node.userName;
				});
				return data ;
			}
		},
		callback : {
			onClick : function(event, treeId, treeNode){
				showRightContent( treeNode );
			}
		}
	};
	
	
	
	var urlCfg = {
		org_tree : "org/tree",         	//查询机构树
		org_tree_inner : "org/tree/"+IOrgId,		//查询机构内部树
		p_user_list : "org/user/list/",		//查询岗位下用户
		p_role_info: "org/role/assign/",//查询岗位角色信息
		user_detail : "org/user/detail/",//查询用户的详情信息（基本信息、角色信息、组织信息）
		org_add : "org/add",//组织机构节点新增
		org_update : "org/update",//组织机构节点编辑
		org_delete : "org/delete",//组织机构节点删除
		ry_add : "org/user/add",//岗位下新增用户
		ry_update : "org/user/update",//更新用户信息
		ry_delete : "org/user/delete",//岗位下删除用户
		ry_associate : "org/user/associate",//通过用户账号或名称联想搜索用户
		p_role_assign : "org/forward/role/assign",//跳转岗位分配角色页面
		ry_role_assign : "org/forward/user/role/assign",//跳转用户分配角色页面
	}
	
	//初始化左侧的树
	$.post( getUrl("org_tree_inner"),initLeftTreee );
	
	function handleOrgData( data ){
		var setIcon = function( list ){
			var hasItem = list && list.length>0 ;
			if( !hasItem ){
				return null;
			}
			$.each(list,function(n,item){
				var otype = item["attrs"] && item["attrs"]["orgType"];
				if( !(item["attrs"].child && item["attrs"].child.length) ){
					item["isParent"] = false;
				}
				if( "I"==otype ){
					item["iconSkin"] = "org_i";
					item["open"] = true;
				}else if( "O"==otype ){
					item["iconSkin"] = "org_o";
				}else if( "P"==otype ){
					item["iconSkin"] = "org_p";
					item["isParent"] = true;
				}
				item["attrs"]["orgId"]=item.value;
				item["attrs"]["orgName"]=item.text;
				setIcon( item["child"] );
			});
		};
		setIcon( data );
		return data;
	}
	
	function getSelectNode(){
		return $.fn.zTree.getZTreeObj("tree").getSelectedNodes()[0];
	}
	function getFirstNode(){
		return $.fn.zTree.getZTreeObj("tree").getNodes()[0];
	}
	function initLeftTreee( data ){
		$.fn.zTree.init($("#tree"), setting, handleOrgData( data ) );
		showRightContent( getFirstNode() );
	}
	
	function getUrl( type ){
		return urlCfg[ type ];
	}
	
	//根据左侧树选中的节点显示右侧面板的内有
	function showRightContent( treeNode ){
		var pathname = getPathName(treeNode);
		$("#orgPath").html(pathname);
		var type = treeNode.attrs.orgType;
		$("#orgInfo .ui-box-container").hide();
		if("RY"==type){  //人员的所有信息都需要从后台查询
			var rydetail = getRyDettail(treeNode.value);
			var ryinfo = rydetail["user"];
			var orginfo = rydetail["org"];
			var roleinfo = rydetail["role"];
		}else if("P"==type){ //岗位的角色信息需要从后台查询
			var roleinfo = getPRole(treeNode.value);
		}
		$("#"+type+"Info").find(".ui-form-container[contenttype]").each(function(n,item){
			var infotype = $(this).attr("contenttype");
			if("pinfo"==infotype ||"oinfo"==infotype ||"iinfo"==infotype ||"ryinfo"==infotype){
				var cfg = contentCfg[ infotype ];
				$.each(cfg,function(n,item2){
					var obj = treeNode.attrs; //组织机构节点从左侧树读取信息
					if( "RY"==type ){  //人员需要从后台实时查询数据
						obj = ryinfo || {};
					}
					var text = obj[item2.en] || "";
					item2.text = text ;
				});
				var html = artTemplate('orgMapTemplate', {list:cfg}) ;
			}
			if("proleinfo"==infotype ||"ryroleinfo"==infotype){
				var html = artTemplate('orgList2Template', {list:roleinfo}) ;
			}
			if("ryorginfo"==infotype){
				var html = artTemplate('orgListTemplate', {list:orginfo}) ;
			}
			$(this).find(".ui-form-2item").html(html);
		});
		$("#"+type+"Info").show();
	};
	function getPathName(treeNode){
		var name = treeNode.text;
		var pnode = treeNode.getParentNode();
		if( pnode ){
			var pname = getPathName(pnode);
			return pname+">"+name;
		}
		return name;
	};
	function addDiyDom(treeId, treeNode) {
		var aObj = $("#" + treeNode.tId + "_a");
		var type = treeNode.attrs.orgType;
		var rysl = treeNode.attrs.rysl || 0;
		if("RY" == type){
			var editStr = "";
		}else{
			var editStr = " ( <a>"+rysl+"</a>)";
		}
		aObj.append(editStr);
	}
	
	function getOrgTree(){
		var parameter = {
			url : getUrl("p_user_list"),
			type : "POST",
			success : function(data) {
				var org = getOrg(data);
				
			}
		};
		$.ajax(parameter);
	}
	
	function getRyDettail( userId ){
		var data = {};
		$.ajax({
			url:urlCfg["user_detail"]+userId,
			type:"POST",
			async:false,
			success:function( msg ){
				data = msg;
			}
		});
		return data;
	}
	function getPRole( orgid ){
		var data = {};
		$.ajax({
			url:urlCfg["p_role_info"]+"?orgId="+orgid,
			type:"POST",
			async:false,
			success:function( msg ){
				data = msg;
			}
		});
		$.each(data,function(index,node){
			node.roleName=node.roleNameCn;
		});
		return data;
	}
	
	var contentCfg = {
		ryinfo:[{en:"userName",cn:"人员姓名"},
		        {en:"userAcct",cn:"人员账号",disabled:true},
		        {en:"userSex",cn:"人员性别",isSelect:true},
		        {en:"userBirth",cn:"出生日期",isCalendar:true},
		        {en:"userIdentitycard",cn:"身份证号"},
		        {en:"userMobile",cn:"手机号码"},
		        {en:"userTel",cn:"办公号码"},
		        {en:"userEmail",cn:"电子邮箱"},
		        {en:"userId",cn:"ID",hide:true}],
        
        pinfo:[{en:"orgName",cn:"岗位名称"},
                {en:"orgDesc",cn:"岗位描述",textarea:true},
		        {en:"orgId",cn:"ID",hide:true}],
        
        oinfo:[{en:"orgName",cn:"部门名称"},
                {en:"orgNameShort",cn:"部门简称"},
		        {en:"orgPhone",cn:"部门电话"},
		        {en:"orgEmail",cn:"部门邮箱"},
		        {en:"orgDesc",cn:"部门描述",textarea:true},
		        {en:"orgId",cn:"ID",hide:true}],
        
        iinfo:[{en:"orgName",cn:"机构名称"},
                {en:"orgNameShort",cn:"机构简称"},
                {en:"orgCode",cn:"机构代码"},
                {en:"orgPhone",cn:"机构电话"},
                {en:"orgEmail",cn:"机构邮箱"},
                {en:"orgAddress",cn:"机构地址"},
                {en:"orgPostcode",cn:"机构邮编"},
                {en:"orgDesc",cn:"机构描述",textarea:true},
		        {en:"orgId",cn:"ID",hide:true}],
	};
	
	//组织机构、用户的新增、编辑弹出框
	var addConfirm = new Confirmbox({
        closeTpl:"&#xf00a5;",
        message: '',
        width:"365",
        confirmTpl: '<a class="ui-button orgORuserSave">保存</a>',
        cancelTpl: '<a class="ui-button">关闭</a>'
    }).before("show",function( data ){
    	this.element.find(".ui-dialog-message").html( data.html );
    	this.element.find(".ui-dialog-title").show().html( data.title );
    	this.element.find(".orgORuserSave").attr("url",data.url);
    }).render();
	
	$(".orgORuserSave").live("click",function(){
		var url = $(this).attr("url");
		var data ={};
		var enforceUpdateField="";
		var input = $(this).parent().parent().parent().find("input").each(function(){
			var value = $(this).val();
			var name = $(this).attr("name");
			if( value ){
				data[ name ] = value;
			}else{
				enforceUpdateField+=","+name;
			}
		});
		var input = $(this).parent().parent().parent().find("textarea").each(function(){
			var value = $(this).val();
			var name = $(this).attr("name");
			if( value ){
				data[ name ] = value;
			}else{
				enforceUpdateField+=","+name;
			}
		});
		if( enforceUpdateField ){
			enforceUpdateField = enforceUpdateField.substr(1);
		}
		data.enforceUpdateField = enforceUpdateField;
		$.post(url,data,function(){
//			init();
		});
	});
	
	//组织机构、用户的删除确认框
	var deleteConfirm = new Confirmbox({
		closeTpl:"&#xf00a5;",
		message: '',
		width:"365",
		confirmTpl: '<a class="ui-button orgORuserSave">确定</a>',
		cancelTpl: '<a class="ui-button">取消</a>'
	}).before("show",function( data ){
		this.element.find(".ui-dialog-message").html( data.html );
		this.element.find(".ui-dialog-title").show().html( data.title );
		this.element.find(".orgORuserSave").attr("url",data.url);
	}).render();
	
	//组织机构、用户的角色编辑弹出框
	var ruleEditConfirm = new Dialog({
        width:'690px',
        height:'780px',
        scrolling:true,
    }).before('show',function(data){
    	this.element.find(".ui-dialog-title").show().html( data.title );
    	this.set('content',data.url);
    });
	
	var orgCfg={
		"i":"机构",	
		"o":"部门",	
		"p":"岗位",	
		"ry":"人员",
	}
	var titleCfg={
		"i-add":"新增机构",	
		"i-update":"编辑机构",	
		"i-delete":"删除机构",
		
		"o-add":"新增子部门",	
		"o-update":"编辑部门",	
		"o-delete":"删除部门",	
		
		"p-add":"新增岗位",	
		"p-update":"编辑岗位",	
		"p-delete":"删除岗位",
		
		"ry-add":"新增人员",	
		"ry-update":"编辑人员",	
		"ry-delete":"删除人员",	
	}
	var titleCfg2={
		"prole-update":"编辑岗位角色",	
		"ryrole-update":"编辑人员角色",	
		"ryorg-update":"编辑人员岗位",	
	}
	var openWidowUrllCfg={
		"prole-update":"/orm/org/org/assignRole.jsp",	
		"ryrole-update":"/orm/org/org/assignRole.jsp",	
		"ryorg-update":"/orm/org/org/assignOrg.jsp",	
	}
	$(".ui-button").bind("click",function(){
		var nr = $(this).attr("opttype");
		if( titleCfg[nr] ){ //人员或组织机构的增删改
			var opttype = nr.split("-")[1];
			var title = titleCfg[nr];
			var orgtype = nr.split("-")[0];
			if("delete"==opttype){
				var html = "&nbsp;&nbsp;&nbsp;确定要删除该"+orgCfg[orgtype]+"?";
				var url = urlCfg["org_delete"];
				var input = "<input name='orgId' value='"+getSelectNode().value+"' style='display:none;' />";
				if("ry"==orgtype){
					url = urlCfg["ry_delete"];
					var input = "<input name='orgId' value='"+getSelectNode().getParentNode().value+"' style='display:none;' />";
					input += "<input name='userId' value='"+getSelectNode().value+"' style='display:none;' />";
				}
				html+=input;
				deleteConfirm.show( {title:title,html:html,url:url} );
			}else if("update"==opttype){
				var list = contentCfg[orgtype+"info"];
				var html = artTemplate('orgAddOrUpdate', {list:list} );
				var url = urlCfg["org_update"];
				if("ry"==orgtype){
					url = urlCfg["ry_update"];
				}
				addConfirm.show( {title:title,html:html,url:url} );
				if("ry"==orgtype){
					new Calendar({
						trigger: 'input[name=\'userBirth\']'
					});
					$("input[name = 'userBirth']").keydown(function(){
					   return false;
					});
				}
			}else if("add"==opttype){
				var list = contentCfg[orgtype+"info"];
				var newlist = $.extend(true,[],list);
				if("ry-add" ==nr ){ //新增人员的面板只显示姓名
					var url = urlCfg["ry_add"];
					newlist = [ {en:"userName",cn:"人员姓名"},{en:"userId",cn:"userId",hide:true},{en:"orgId",cn:"orgId",hide:true,text:getSelectNode().value}  ];
				}else{
					var url = urlCfg["org_add"];
					$.each(newlist,function(index,node){
						node.text="";
					});
					newlist.push({en:"parentOrgId",cn:"parentOrgId",hide:true,text:getSelectNode().value});
					var rid = (getSelectNode().getParentNode() && getSelectNode().getParentNode().value) || "";
					newlist.push({en:"rootOrgId",cn:"rootOrgId",hide:true,text:rid});
					newlist.push({en:"orgType",cn:"orgType",hide:true,text:orgtype.toUpperCase()});
				}
				var html = artTemplate('orgAddOrUpdate', {list:newlist} );
				addConfirm.show( {title:title,html:html,url:url} );
				if("ry-add" ==nr ){ //新增人员的面板通过联想搜索查询已有用户
					InitAutoComplete();
				}
			}
		}else if( titleCfg2[nr] ){ //弹出新窗口
			var orgtype = nr.split("-")[0];
			var title = titleCfg2[nr];
			var ryorp = "prole"== orgtype?"p":"ry";
			var field = ryorp+"_role_assign";
			var content= contentCfg[ryorp+"info"];
			var cobj = content[content.length-1];
			ruleEditConfirm.show( {title:title,url:basePath+urlCfg[field]+"/"+cobj.text } );  //url:http://xxx:xxx/xxx?(orgId/userId)=xxx
		}
	});
	
	var userAutoComplete;
	function InitAutoComplete(){
		userAutoComplete && userAutoComplete.destroy();
		userAutoComplete = new AutoComplete({
			trigger: ".userName",
			dataSource: function( query ){
				var data = [];
				$.ajax({
					url:urlCfg["ry_associate"],
					data:{"nameOrAcct":query},
					type:"POST",
					async:false,
					success:function(msg){
						data=msg;
					}
				});
				$.each(data,function(index,node){
					node.label=node.userName;
				})
				return data;
			},
			filter:function(data){
				return data;
			},
			width: 178,
			height:150,
			classPrefix:"ui-select",
			html:"<i style='color:red'> {{userName}}</i>&nbsp;&nbsp;&nbsp;{{userAcct}}"
		}).on('itemSelected', function(data, item){
			$(".userId").val( data.userId ); //联想搜索选中时把userId存在userName的input中
		}).render();
		$(".ui-select").css("zIndex",999);
	}
	
});