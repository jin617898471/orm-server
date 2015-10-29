define(function(require){
	var $=require("$");

	require("gallery/ztree/3.5.2/ztree-debug");
	require("gallery/ztree/3.5.2/core-debug");
	require("gallery/ztree/3.5.2/excheck-debug");
	require("gallery/ztree/3.5.2/exedit-debug");
	//require("gallery/ztree/3.5.2/exhide-debug");
		var _path="resource/ormresource";
		
		function canPrev(treeId, nodes, targetNode) {
			return !targetNode.isParent;
		}
		function canNext(treeId, nodes, targetNode) {
			return !targetNode.isParent;
		}
		function beforeDrag(treeId, treeNodes, targetNode, moveType, isCopy){
			 return !(targetNode == null || (moveType != "inner" && targetNode.parentTId !== treeNodes[0].parentTId));
		}
		var setting = {
			callback: {
				beforeDrop: beforeDrag
			},
			edit: {
				enable:true,
				drag:{
					autoExpandTrigger: true,
					isMove:true,
					isCopy:true,
					prev: canPrev,
					next: canNext,
					inner: false,
					borderMax: 20,
					borderMin: -10,
					minMoveSize: 10,
					maxShowNodeNum: 10,
					beforeDrag:beforeDrag,
				}
			},
			check: {
				enable: true
			},
			data: {
				simpleData: {
					enable: true
				}
			}
		};
		//获取属性节点集合
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
		$(document).ready(function(){
			var zNodes = getTreeAllNodes();
			$.fn.zTree.init($("#ztree"), setting, zNodes);
		});
});