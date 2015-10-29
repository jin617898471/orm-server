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
					enable: true,
					chkboxType: {"Y":"", "N":""}
				},
				view: {
					dblClickExpand: false
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				callback: {
					beforeClick: beforeClick,
					onCheck: onCheck
				}
			};
			
			function getTreeAllNodes(){
				var back="";
				$.ajax({   
			        type: "POST",  
			        dataType:"json", 
			        async: false,
			        url: _path+"/getList",
			        success: function(data) {
			        	back = data.result;
		        	},error:function(){
		        		alert("获取树节点集合异常，请联系管理员！");
		        	}
		    	}); 
				return back;
			}
			var zNodes =getTreeAllNodes();

			function beforeClick(treeId, treeNode) {
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				zTree.checkNode(treeNode, !treeNode.checked, null, true);
				return false;
			}
			
			function onCheck(e, treeId, treeNode) {
				var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
				nodes = zTree.getCheckedNodes(true),
				v = "";
				for (var i=0, l=nodes.length; i<l; i++) {
					v += nodes[i].name + ",";
				}
				if (v.length > 0 ) v = v.substring(0, v.length-1);
				var cityObj = $("#citySel");
				cityObj.attr("value", v);
			}

			showMenu = function () {
				var cityObj = $("#citySel");
				var cityOffset = $("#citySel").offset();
				$("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");

				$("body").bind("mousedown", onBodyDown);
			}
			function hideMenu() {
				$("#menuContent").fadeOut("fast");
				$("body").unbind("mousedown", onBodyDown);
			}
			function onBodyDown(event) {
				if (!(event.target.id == "menuBtn" || event.target.id == "citySel" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
					hideMenu();
				}
			}

			$(document).ready(function(){
				$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			});
});