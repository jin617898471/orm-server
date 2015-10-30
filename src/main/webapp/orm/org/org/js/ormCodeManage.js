define(function(require){
	var $=require("$");
	require("gallery/ztree/3.5.2/core-debug");
	require("gallery/ztree/3.5.14/ztree-debug.css");
	OrmJsObj.resourceInit($,document);
	
	var setting = {
			data:{
				key: {
					children:"child",
					name:"text",
				}
			}
	};
	$.fn.zTree.init($("#tree"), setting, OrmJsObj.getOrg());
});