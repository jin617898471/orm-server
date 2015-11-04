define(function(require){
	var $=require("$");
	require("gallery/ztree/3.5.2/core-debug");
	require("gallery/ztree/3.5.2/excheck-debug");
	require("gallery/ztree/3.5.14/ztree-debug.css");
	Confirmbox=require("inno/dialog/1.0.0/confirmbox-debug");
	Dialog=require("inno/dialog/1.0.0/dialog-debug");
	var artTemplate=require("../../../org/org/js/art-template");
	OrmJsObj.resourceInit($,document);
	
	var setting = {
		data:{
			key: {
				children:"child",
				name:"text",
			}
		},
		check : {
			enable : true,
			chkboxType : { "Y" : "", "N" : "" }
		},
		callback : {
			onClick : function(event, treeId, treeNode){
				showRightContent( treeNode );
			}
		}
	};
	$.fn.zTree.init($("#tree"), setting,getOrg() );
	
	
	function getOrg() {
		var org = OrmJsObj.org;
		var setIcon = function( list ){
			var hasItem = list && list.length>0 ;
			if( !hasItem ){
				return null;
			}
			$.each(list,function(n,item){
				var otype = item["attrs"] && item["attrs"]["otype"];
				if( "I"==otype ){
					item["iconSkin"] = "org_i";
					item["open"] = true;
					item["nocheck"] = true;
				}else if( "O"==otype ){
					item["iconSkin"] = "org_o";
					item["open"] = true;
					item["nocheck"] = true;
				}else if( "P"==otype ){
					item["iconSkin"] = "org_p";
					item["isParent"] = false;
					item["open"] = false;
					item["checked"] = true;
				}
				setIcon( item["child"] );
			});
		};
		setIcon( org );
		return org;
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
	
});