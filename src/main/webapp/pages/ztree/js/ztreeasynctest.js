define(function(require){
	var $=require("$");

	require("gallery/ztree/3.5.2/ztree-debug");
	require("gallery/ztree/3.5.2/core-debug");
	require("gallery/ztree/3.5.2/excheck-debug");
	require("gallery/ztree/3.5.2/exedit-debug");
	//require("gallery/ztree/3.5.2/exhide-debug");
	var _path="resource/ormresource";
		var setting = {
			check: {
				enable: true
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			async: {
				enable: true,
				url: _path+"/getAsyncList",
				type: "POST",  
		        dataType:"json", 
				autoParam:["id"]
			}
		};
        $(document).ready(function(){
        	$.fn.zTree.init($("#ztreeasync"), setting);
        });
});