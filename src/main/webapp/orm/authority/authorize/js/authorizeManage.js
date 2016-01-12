define(function(require){
	var $ = require("$");
	Dialog = require("inno/dialog/1.0.0/dialog-debug");
	Confirmbox=require("inno/dialog/1.0.0/confirmbox-debug");
	Select = require("inno/select/1.0.0/select-debug");
	require("gallery/ztree/3.5.2/core-debug");
	require("gallery/ztree/3.5.2/excheck-debug");
	require("gallery/ztree/3.5.14/ztree-debug.css");
	Scroll = require("inno/scroll/1.0.0/scroll-debug");
	
	$(".role-list-items").css('height', $(".colsub-container").height() - 75);
	var roleScroll = new Scroll({
		trigger:'.role-list-items .ui-scroll',
		container:'.role-list-items .ui-scroll-container'
	});
	$(window).resize(function(event) {
		$(".role-list-items").css('height', $(".colsub-container").height() - 75);
		roleScroll.reset();
	});


	
	var modulName="资源";
	var idField="resourceId";
	var urlBasePath='./authority/role/';
	var url_cfg={
		"list":'listnopage',
		"gettree":'getRoleResourceTrees',
		"save":'saveRoleResourceRight'
	}
	
	var select_system=new Select({
		name:"systemId",
		trigger:'#systemId',
		width:'220px',
		model:OrmJsObj.system.getHasRight()
	}).on('change', function(target, prev) {
		loadRoleList(target.attr("data-value"));
	}).render();
	
	loadRoleList( OrmJsObj.system.getHasRight()[0].value );
	
	function loadRoleList(systemId){
		statedisplay(false);
		var queryCondition=null;
		if(systemId){
			queryCondition='{"rules":[{"field":"systemId","value":"'+systemId+'","op":"equal"}],"groups":[],"op":"and"}';
		}
		$.ajax({
			url:urlBasePath+url_cfg["list"],
			data:{queryCondition:queryCondition},
			type:"POST",
			error:function(){
				alert('失败,请联系管理员');
			},
			statusCode: { 
				200: function(msg) {
					var html='';
					for(var i in msg){
						var obj=msg[i];
						html+='<li class="role-li" id="'+obj.roleId+'" roleType="'+obj.roleType+'">'+obj.roleNameCn+'</li>';
					}
					$("#roleList").html(html);
					var li =$("#roleList").find("li").eq(0);
					showResource($(li));
			  	}
			}
			
		});
	}
	$(document).on("click",".role-li",function(){
		var _this=$(this);
		showResource(_this);
	});
	
	var roleId;
	var roleType;
	function showResource(_this){
		roleId=_this.attr("id");
		roleType=_this.attr("roleType");
		var text =_this.html();
		var systemName=$("*[data-role=trigger-content]").html();
		$("#roleName").html(systemName+"/"+text);
		loadTree(roleId);
		statedisplay(false);
	}
	var setting = {
		data:{
			simpleData: {
				enable:true,
				idKey:"id",
				pIdKey:"pId",
			}
		},
		check:{
			enable:true,
			chkboxType:{Y:"ps",N:"ps"}
		},
		key:{
			name:"text"
		},
	};
	function getFirstNode(){
		return $.fn.zTree.getZTreeObj("tree").getNodes()[0];
	}
	function getSelectNode(){
		return $.fn.zTree.getZTreeObj("tree").getSelectedNodes()[0];
	}
	function loadTree(roleId){
		$.post( urlBasePath+url_cfg["gettree"]+"?roleId="+roleId,function( data ){
			if("ADMIN"==roleType){
				for(var i in data){
					var node=data[i];
					node.chkDisabled=true;
				}
			}
			$.fn.zTree.init($("#tree"), setting, data );
			getLevel1Nodes();
		} );
	}
	function getLevel1Nodes(){
		var nodes = $.fn.zTree.getZTreeObj("tree").getNodes();
		for(var i in nodes){
			var node=nodes[i];
			if(node.attributes.oldPid=="ROOT"){
				$.fn.zTree.getZTreeObj("tree").expandNode(node);
			}
		}
	}
	
	function statedisplay(flag){
		if(flag){
			$(".subadd-bottom-status").show();
		}else{
			$(".subadd-bottom-status").hide();
		}
	}
	
	
	function bingGlobalEvent(){
		$('.items-save').click(function(event){
			var arr=$.fn.zTree.getZTreeObj("tree").getCheckedNodes(true);
			var resourceId=[];
			for(var i in arr){
				var node=arr[i];
				resourceId.push(node.id);
			}
			$.ajax({
				url:urlBasePath+url_cfg["save"],
				data:{roleId:roleId,resourceId:resourceId.join(",")},
				type:"POST",
				error:function(){
					alert('失败,请联系管理员');
				},
				statusCode: { 
					200: function(msg) {
						statedisplay(true);
				  	}
				}
			});
		});
	}
	bingGlobalEvent();
});