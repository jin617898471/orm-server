var reloadRightContent;//让子页面调用

define(function(require){
	var $=require("$");
	require("gallery/ztree/3.5.2/core-debug");
	require("gallery/ztree/3.5.14/ztree-debug.css");
	var Confirmbox=require("inno/dialog/1.0.0/confirmbox-debug");
	var Dialog=require("inno/dialog/1.0.0/dialog-debug");
	AutoComplete=require("arale/autocomplete/1.3.0/autocomplete-debug");
	var Calendar = require("inno/calendar/1.0.0/calendar-debug");
	var Select = require("inno/select/1.0.0/select-debug");
	var artTemplate=require("../../../org/org/js/art-template");
	
	
	//左侧树-配置信息
	var setting = {
		data:{
			key: {
				children:"child",
				name:"text",
			}
		},
		view: {
			addDiyDom: addDiyDom,
			selectedMulti: false
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
	
	
	//后端请求的url配置对象
	var urlCfg = {
		org_tree : "org/tree",         	//查询机构树
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
	
	function getTreeUrl(){
		if(IOrgId){
			$(".ui-button-add[opttype=i-add").hide();
			$(".ui-button-add[opttype=i-delete").hide();
			return urlCfg["org_tree"]+"/"+IOrgId;
		}else{
			$(".ui-button-add[opttype=o-add").hide();
			return urlCfg["org_tree"]
		}
	}
	
	
	
	//左侧树-启动加载左侧树，并默认在右侧面板显示第一个节点的数据
	function addTree(){
		$.post( getTreeUrl(),function( data ){
			$.fn.zTree.init($("#tree"), setting, handleOrgData( data ) );
			showRightContent( getFirstNode() );
			$.fn.zTree.getZTreeObj("tree").selectNode( getFirstNode());
		} );
	}
	
	addTree();
	
	function reflashTree(type,attrs){
		var selectNode = getSelectNode();
		var obj = type.split("-")[0];
		var opt = type.split("-")[1];
		if("update"==opt){
			$.extend(selectNode.attrs,attrs);
			var textField = (obj=="ry"?"user":"org")+"Name";
			selectNode.text = attrs[textField];
			var treeObj = $.fn.zTree.getZTreeObj("tree");
			treeObj.updateNode(selectNode);
			treeObj.selectNode(selectNode);
			showRightContent( selectNode );
		}else if("delete"==opt){
			$.post( getTreeUrl(),function(data){
				var openNodes = getParentNodesValue( selectNode ,[] )
				$.fn.zTree.init($("#tree"), setting, handleOrgData( data,openNodes ) );
				var treeObj = $.fn.zTree.getZTreeObj("tree");
				var node = treeObj.getNodeByParam("value", openNodes[0], null);//获取父节点
				if(obj=="ry"){//人员删除时刷新岗位树
					treeObj.reAsyncChildNodes(node, "refresh",false);
				}
				showRightContent( node );
				treeObj.selectNode(node);
			});
		}else if("add"==opt){
			$.post( getTreeUrl(),function(data){
				var openNodes = getParentNodesValue( selectNode ,[selectNode.value] )
				$.fn.zTree.init($("#tree"), setting, handleOrgData( data,openNodes ) );
				var treeObj = $.fn.zTree.getZTreeObj("tree");
				var node = treeObj.getNodeByParam("value", selectNode.value, null);
				$.fn.zTree.getZTreeObj("tree").selectNode(node);
				if(obj=="ry"){//人员新增时刷新岗位树
					treeObj.reAsyncChildNodes(node, "refresh",false);
				}
				showRightContent( node );
			});
		}
	}
	
	//刷新右侧面板的信息
	reloadRightContent = function(){
		showRightContent( getSelectNode() );
	}
	
	//左侧树-强制重新加载岗位下的用户
	function forceReloadNodes( value ){
		var treeObj = $.fn.zTree.getZTreeObj("tree");
		var node = treeObj.getNodeByParam("value", value, null);
		treeObj.reAsyncChildNodes(node, "refresh",false);
		return node;
	}
	
	//左侧树-处理后端取过来的数据，设置默认的收缩状态、样式等
	function handleOrgData( data,openNodes ){
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
				if( openNodes && openNodes.indexOf(item.value)>=0 ){
					item["open"] = true;
				}
				item["attrs"]["orgId"]=item.value;
				item["attrs"]["orgName"]=item.text;
				setIcon( item["child"] );
			});
		};
		setIcon( data );
		return data;
	}
	
	//左侧树-获取选中的节点对象
	function getSelectNode(){
		return $.fn.zTree.getZTreeObj("tree").getSelectedNodes()[0];
	}
	
	//左侧树-获取选中节点的所有父节点的value值
	function getParentNodesValue( node ,arr ){
		var pnodes = node.getParentNode();
		if(pnodes){
			arr.push( pnodes.value );
			getParentNodesValue(pnodes,arr);
		}
		return arr;
	}
	
	//左侧树-获取根节点数据
	function getFirstNode(){
		return $.fn.zTree.getZTreeObj("tree").getNodes()[0];
	}
	
	
	
	//右侧面板-根据左侧树选中的节点显示右侧面板的内有
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
					if(item2.en=="userSex"){
						item2.textCn=getSexValue( text);
					}
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
	
	//左侧树-获取指定节点的完整路径
	function getPathName(treeNode){
		var name = treeNode.text;
		var pnode = treeNode.getParentNode();
		if( pnode ){
			var pname = getPathName(pnode);
			return pname+">"+name;
		}
		return name;
	};
	
	//左侧树-为左侧树的显示内容中新增每个节点下的用户数量
	function addDiyDom(treeId, treeNode) {
		var aObj = $("#" + treeNode.tId + "_a");
		var type = treeNode.attrs.orgType;
		var rysl = treeNode.attrs.rysl || 0;
		if("RY" == type){
			var editStr = "";
		}else{
			var editStr = "&nbsp;( <a>"+rysl+"</a>)";
			if(!IOrgId){
				editStr+="&nbsp;<img title='进入机构内部' class='iorgjr' src='orm/org/org/css/imgs/jr.jpg' />";
			}
		}
		aObj.append(editStr);
	}
	
	//右侧面板-查询用户的详细信息：基本信息、组织信息、角色信息
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
	
	//右侧面板-查询岗位的角色信息
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
	
	
	//右侧面板-组织机构、用户在查看、新增、编辑时显示字段的配置信息
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
	
	//右侧面板-组织机构、用户的新增、编辑弹出框
	var addConfirm = new Confirmbox({
        closeTpl:"&#xf00a5;",
        message: '',
        width:"370",
        confirmTpl: '<a class="ui-button orgORuserSave">保存</a>',
        cancelTpl: '<a class="ui-button">关闭</a>'
    }).before("show",function( data ){
    	this.element.find(".ui-dialog-message").html( data.html );
    	this.element.find(".ui-dialog-title").show().html( data.title );
    	this.element.find(".orgORuserSave").attr("url",data.url);
		this.element.find(".orgORuserSave").attr("cdopttype",data.opttype);
    }).render();
	
	//右侧面板-组织机构、用户的新增、编辑、删除时保持按钮事件注册
	
	$(".iorgjr").live("click",function(){
		window.open(basePath+"org/forward/manage?orgId="+getSelectNode().value);
	});
		
	$(".orgORuserSave").live("click",function(){
		var url = $(this).attr("url");
		var opttype = $(this).attr("cdopttype");
		var attrs = {};
		var data ={};
		var enforceUpdateField="";
		var isbreak = false;
		var input = $(this).parent().parent().parent().find("input").each(function(){
			var value = $(this).val();
			var name = $(this).attr("name");
			if( value ){
				data[ name ] = value;
			}else{
				if("userId"==name){//如果用户id不存在，说明用户关联时没有选择，不能保存
					isbreak = true;
					return false;
				}
				enforceUpdateField+=","+name;
			}
			attrs[ name ] = value;
		});
		if(isbreak){
			return ;
		}else{
			if("ry-add"==opttype && data.userId){ //解决用户联想搜索选中用户后又删除的场景
				var tarr = data.userId.split("_split_");
				var tid = tarr[0];
				var tname = tarr[1];
				if(tname!=data.userName){
					return ;
				}else{
					data.userId = tid;
				}
			}
		}
		var input = $(this).parent().parent().parent().find("textarea").each(function(){
			var value = $(this).val();
			var name = $(this).attr("name");
			if( value ){
				data[ name ] = value;
			}else{
				enforceUpdateField+=","+name;
			}
			attrs[ name ] = value;
		});
		if( enforceUpdateField ){
			enforceUpdateField = enforceUpdateField.substr(1);
		}
		data.enforceUpdateField = enforceUpdateField;
		$.post(url,data,function(){
			reflashTree(opttype,attrs);
			var optlx = opttype.split("-")[1];
			if( optlx=="delete" ){
				deleteConfirm.hide();
			}else if( optlx=="update" ){
				addConfirm.hide();
			}
		});
	});
	
	//右侧面板-组织机构、用户的删除确认框
	var deleteConfirm = new Confirmbox({
		closeTpl:"&#xf00a5;",
		message: '',
		width:"370",
		confirmTpl: '<a class="ui-button orgORuserSave">确定</a>',
		cancelTpl: '<a class="ui-button">取消</a>'
	}).before("show",function( data ){
		this.element.find(".ui-dialog-message").html( data.html );
		this.element.find(".ui-dialog-title").show().html( data.title );
		this.element.find(".orgORuserSave").attr("url",data.url);
		this.element.find(".orgORuserSave").attr("cdopttype",data.opttype);
	}).render();
	
	//右侧面板-组织机构、用户的角色编辑弹出框
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
	
	//右侧面板-弹出框标题的配置信息
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
	
	//右侧面板-弹出框标题的配置信息
	var titleCfg2={
		"prole-update":"编辑岗位角色",	
		"ryrole-update":"编辑人员角色",	
		"ryorg-update":"编辑人员岗位",	
	}
	
	function bindComponent( list){
		new Calendar({
			trigger: 'input[name=\'userBirth\']'
		});
		$("input[name = 'userBirth']").keydown(function(){
		   return false;
		});
		var sexSelect = new Select({
	    	trigger: '.userSex',
	    	width:'190px',
	    	name:'userSex',
	    	model:getSexDm()
		}).render();
		$.each(list,function(index,node){
			if(node.en=="userSex"){
				sexSelect.selectValue(node.text);
			}
		})
		$(".ui-select").css("zIndex",999);
	}
	
	function getSexDm(){
		return [{value:'UNKNOWN',text:'未知'},{value:'MALE',text:'男'},{value:'FEMALE',text:'女'}];
	}
	
	function getSexValue( dm ){
		var arr = getSexDm();
		var text="";
		$.each(arr,function(index,node){
			if(dm==node.value){
				text=node.text;
				return false;
			}
		})
		return text;
	}
	
	function getSelectRootNode( node ){
		var pnode = node.getParentNode();
		if( pnode && pnode.value ){
			return getSelectRootNode(pnode);
		}
		return node.value;
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
				deleteConfirm.show( {title:title,html:html,url:url,opttype:nr} );
			}else if("update"==opttype){
				var list = contentCfg[orgtype+"info"];
				var html = artTemplate('orgAddOrUpdate', {list:list} );
				var url = urlCfg["org_update"];
				if("ry"==orgtype){
					url = urlCfg["ry_update"];
				}
				addConfirm.show( {title:title,html:html,url:url,opttype:nr} );
				if("ry"==orgtype){
					bindComponent(list);
				}
			}else if("add"==opttype){
				var list = contentCfg[orgtype+"info"];
				var newlist = $.extend(true,[],list);
				if("ry-add" ==nr ){ //新增人员的面板只显示姓名
					var url = urlCfg["ry_add"];
					newlist = [ {en:"userName",cn:"人员姓名"},{en:"userId",cn:"userId",hide:true,required:true},{en:"orgId",cn:"orgId",hide:true,text:getSelectNode().value}  ];
				}else{
					var url = urlCfg["org_add"];
					$.each(newlist,function(index,node){
						node.text="";
					});
					newlist.push({en:"parentOrgId",cn:"parentOrgId",hide:true,text:getSelectNode().value});
					var rid = getSelectRootNode(getSelectNode());
					newlist.push({en:"rootOrgId",cn:"rootOrgId",hide:true,text:rid});
					newlist.push({en:"orgType",cn:"orgType",hide:true,text:orgtype.toUpperCase()});
				}
				var html = artTemplate('orgAddOrUpdate', {list:newlist} );
				addConfirm.show( {title:title,html:html,url:url,opttype:nr} );
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
			$(".userId").val( data.userId+"_split_"+data.userName ); //联想搜索选中时把userId存在userName的input中
		}).render();
		$(".ui-select").css("zIndex",999);
	}
	
});