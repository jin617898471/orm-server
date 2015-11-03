define(function(require){
	var $=require("$");
	require("gallery/ztree/3.5.2/core-debug");
	require("gallery/ztree/3.5.14/ztree-debug.css");
	Confirmbox=require("inno/dialog/1.0.0/confirmbox-debug");
	Dialog=require("inno/dialog/1.0.0/dialog-debug");
	AutoComplete=require("arale/autocomplete/1.3.0/autocomplete-debug");
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
				var type = treeNode.attrs.otype;
				if("P"==type){ 
					return getUrl("p_user_list");
				}
			},
			autoParam: ["value=orgId"]
		},
		callback : {
			onClick : function(event, treeId, treeNode){
				showRightContent( treeNode );
			}
		}
	};
	var urlCfg = {
		org_tree : "aaa",         	//查询机构树
		org_tree_inner : "aaa",		//查询机构内部树
		p_user_list : "aaa",		//查询岗位下用户
		p_role_info: "aaa",//查询岗位角色信息
		user_detail : "aaa",//查询用户的详情信息（基本信息、角色信息、组织信息）
		org_add : "aaa",//组织机构节点新增
		org_update : "aaa",//组织机构节点编辑
		org_delete : "aaa",//组织机构节点删除
		ry_add : "aaa",//岗位下新增用户
		ry_update : "aaa",//更新用户信息
		ry_delete : "aaa",//岗位下删除用户
	}
	function getUrl( type ){
		return urlCfg( type );
	}
	
	//根据左侧树选中的节点显示右侧面板的内有
	function showRightContent( treeNode ){
		var pathname = getPathName(treeNode);
		$("#orgPath").html(pathname);
		var type = treeNode.attrs.otype;
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
		var type = treeNode.attrs.otype;
		var rysl = treeNode.attrs.rysl || 0;
		if("RY" == type){
			var editStr = "";
		}else{
			var editStr = " ( <a>"+rysl+"</a>)";
		}
		aObj.append(editStr);
	}
	$.fn.zTree.init($("#tree"), setting,getOrg() );
	
	function getOrg(){
		return OrmJsObj.getOrmOrg();
	}
	function getRyDettail(){
		return {user:OrmJsObj.getOrmOrg()[0].child[0].child[0].child[0].attrs};
	}
	function getPRole(){
		return {};
	}
	
	var contentCfg = {
		ryinfo:[{en:"userName",cn:"人员姓名"},
		        {en:"userAcct",cn:"人员账号"},
		        {en:"userSex",cn:"人员性别"},
		        {en:"userBirth",cn:"出生日期"},
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
        confirmTpl: '<a class="ui-button">保存</a>',
        cancelTpl: '<a class="ui-button">关闭</a>'
    }).before("show",function( data ){
    	this.element.find(".ui-dialog-message").html( data.html );
    	this.element.find(".ui-dialog-title").show().html( data.title );
    }).render();
	
	//组织机构、用户的删除确认框
	var deleteConfirm = new Confirmbox({
		closeTpl:"&#xf00a5;",
		message: '',
		width:"365",
		confirmTpl: '<a class="ui-button">确定</a>',
		cancelTpl: '<a class="ui-button">取消</a>'
	}).before("show",function( data ){
		this.element.find(".ui-dialog-message").html( data.html );
		this.element.find(".ui-dialog-title").show().html( data.title );
	}).render();
	
	//组织机构、用户的角色编辑弹出框
	var ruleEditConfirm = new Dialog({
        width:'690px',
        height:'800px',
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
	var urlCfg={
		"prole-update":"/orm/org/org/assignRole.jsp",	
		"ryrole-update":"/orm/org/org/assignRole.jsp",	
		"ryorg-update":"/orm/org/org/assignOrg.jsp",	
	}
	$(".ui-button").bind("click",function(){
		var nr = $(this).attr("opttype");
		if( titleCfg[nr] ){
			var opttype = nr.split("-")[1];
			var title = titleCfg[nr];
			var orgtype = nr.split("-")[0];
			if("delete"==opttype){
				var html = "&nbsp;&nbsp;&nbsp;确定要删除该"+orgCfg[orgtype]+"?";
				deleteConfirm.show( {title:title,html:html} );
			}else if("update"==opttype){
				var list = contentCfg[orgtype+"info"];
				var html = artTemplate('orgAddOrUpdate', {list:list} );
				addConfirm.show( {title:title,html:html} );
			}else if("add"==opttype){
				var list = contentCfg[orgtype+"info"];
				var newlist = $.extend(true,[],list);
				$.each(newlist,function(index,node){
					node.text="";
				});
				if("ry-add" ==nr ){ //新增人员的面板只显示姓名
					newlist = [ newlist[0] ];
				}
				var html = artTemplate('orgAddOrUpdate', {list:newlist} );
				addConfirm.show( {title:title,html:html} );
				if("ry-add" ==nr ){ //新增人员的面板通过联想搜索查询已有用户
					InitAutoComplete();
				}
			}
		}else if( titleCfg2[nr] ){
			var orgtype = nr.split("-")[0];
			var title = titleCfg2[nr];
			var ryorp = "prole"== orgtype?"p":"ry";
			var content= contentCfg[ryorp+"info"];
			var cobj = content[content.length-1];
			ruleEditConfirm.show( {title:title,url:basePath+urlCfg[nr]+"?"+cobj.en+"="+cobj.text } );  //url:http://xxx:xxx/xxx?(orgId/userId)=xxx
		}
	});
	
	var userAutoComplete;
	function InitAutoComplete(){
		userAutoComplete && userAutoComplete.destroy();
		userAutoComplete = new AutoComplete({
			trigger: ".userName",
			dataSource: getUserList,
			filter:function(data){
				return data;
			},
			width: 178,
			height:150,
			classPrefix:"ui-select",
			html:"<i style='color:red'> {{userId}}</i>&nbsp;&nbsp;&nbsp;{{userAcct}}"
		}).on('itemSelected', function(data, item){
			$(".userName").val( data.userId ); //联想搜索选中时把userId存在userName的input中
		}).render();
		$(".ui-select").css("zIndex",999);
	}
	
	function getUserList(){
		return [{"label":"金石锋","userAcct":"jinsf","userId":"0001"},{"label":"曹珊珊","userAcct":"css","userId":"0002"}];
	}
});