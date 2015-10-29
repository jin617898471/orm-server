	define(function(require){
	var Menu=require("menu"),
		Dialog=require("inno/dialog/1.0.0/dialog-debug"),
		Confirmbox=require("inno/dialog/1.0.0/confirmbox-debug"),
		Select = require("inno/select/1.0.0/select-debug"),
		Form = require("form"),
		$=require("$");
		require("easyui");
		require("gallery/ztree/3.5.2/core-debug");
		require("gallery/ztree/3.5.14/ztree-debug.css");
		
	var _pathCode="resource/ormcode";
	
	var _pathCodeIdx="resource/ormcodeidx";
	
	var sysCode = "";//系统代码合集，供翻译及下拉
	
	var presId  = "";//父节点Id ，供子页面调用
	
	var codeIdx = "";
	
	var formCodeIdx =new Form({
			trigger:".search-formCodeIdx"
	});
	
	var formCode =new Form({
		trigger:".search-formCode"
});
	
	var menu =new Menu({
		trigger:"#nav"
	});
	
	inittableByCodeIdx = function (QUERYCONDITION,presId){
		$('#tblResultCodeidx').datagrid({
			queryParams : {QUERYCONDITION:QUERYCONDITION},
			nowrap : true,
			autoRowHeight : true,
			striped : true,
			collapsible : true,
			url : _pathCodeIdx+'/list?systemid='+presId,
			sortName : 'createDt',
			sortOrder : 'desc',
			idField : 'cidxId',
			frozenColumns : getGridFrozenColumnsByCodeIdx(),
			columns : getGridColumnsByCodeIdx(),
			pagination : true,
			rownumbers : true,
			pageList:[10,20,50,100,200,500]
		});
	};
	
	inittableByCode = function (QUERYCONDITION,codeIdx,systemid){
		$('#tblResultCode').datagrid({
			queryParams : {QUERYCONDITION:QUERYCONDITION},
			nowrap : true,
			autoRowHeight : true,
			striped : true,
			collapsible : true,
			url : _pathCode+'/list?parentid='+codeIdx+'&systemid='+systemid,
			sortName : 'createDt',
			sortOrder : 'desc',
			idField : 'codeId',
			frozenColumns : getGridFrozenColumnsByCode(),
			columns : getGridColumnsByCode(),
			pagination : true,
			rownumbers : true,
			pageList:[10,20,50,100,200,500]
		});
	};
	
	var addCode = new Dialog({
        trigger: '.addCode',
        content: './'+_pathCode+"/forward/add",
        width:'800px',
        height:'300px',
        scrolling:true,
        title:"代码新增"
    });
	
	var selectCode = new Dialog({
		 	width:'800px',
	        height:'300px',
	        scrolling:true,
	        title:"代码查看"
	    }).before('show',function(id){
	    	var url='./'+_pathCode+"/forward/detail/"+id;
	    	this.set('content',url);
	});
	
	var editCode = new Dialog({
        width:'800px',
        height:'300px',
        scrolling:true,
        title:"代码编辑"
    }).before('show',function(id){
    	var url='./'+_pathCode+"/forward/edit/"+id;
    	this.set('content',url);
    });
	
	var addCodeIdx = new Dialog({
        trigger: '.addCodeidx',
        content: './'+_pathCodeIdx+"/forward/add",
        width:'800px',
        height:'340px',
        scrolling:true,
        title:"代码集新增"
    });
	
	var selectCodeIdx = new Dialog({
		 	width:'800px',
	        height:'340px',
	        scrolling:true,
	        title:"代码集查看"
	    }).before('show',function(id){
	    	var url='./'+_pathCodeIdx+"/forward/detail/"+id;
	    	this.set('content',url);
	});
	
	var editCodeIdx = new Dialog({
        width:'800px',
        height:'340px',
        scrolling:true,
        title:"代码集编辑"
    }).before('show',function(id){
    	var url='./'+_pathCodeIdx+"/forward/edit/"+id;
    	this.set('content',url);
    });
	
    
    //左侧树的点击查询，与右侧列表的联动
    function zTreeBeforeClick(treeId, treeNode, clickFlag) {
    	var type = treeNode.attributes.type;
    	var system = treeNode.attributes.system;
    	presId = treeNode.id;
    	if(type == 'system'){
    		$(".CodeIdx").css("display","");
    		$(".Code").css("display","none");
    		var QUERYCONDITION= '{"rules":[{"field":"cidxSysScope","op":"like","value":"'+treeNode.id+'"}],"groups":[],"op":"and"}';
    		inittableByCodeIdx(QUERYCONDITION,presId);
    	}else if(type == 'codeidx'){
    		$(".CodeIdx").css("display","none");
    		$(".Code").css("display","");
    		codeIdx = presId;
    		var QUERYCONDITION= '{"rules":[{"field":"pcodeId","op":"like","value":"'+treeNode.id+'"},{"field":"codeId","op":"notequal","value":"'+treeNode.id+'"}],"groups":[],"op":"and"}';
    		inittableByCode(QUERYCONDITION,codeIdx,system);
    	}else if(type == 'code'){
    		codeIdx = treeNode.pId;
    		$(".CodeIdx").css("display","none");
    		$(".Code").css("display","");
    		var QUERYCONDITION= '{"rules":[{"field":"pcodeId","op":"like","value":"'+treeNode.id+'"},{"field":"codeId","op":"notequal","value":"'+treeNode.id+'"}],"groups":[],"op":"and"}';
    		inittableByCode(QUERYCONDITION,treeNode.id,system);
    	}
    };
    
    function getAsyncUrl(treeId, treeNode) {
    	var result = "";
    	var parameter={
				url:_pathCode+'/treeBeanList',
			    type:"POST",
			    async:false,
			    success:function(data){
			    	result = data;
			    },
			    error:function(result){
			    	
			    }
			};
		$.ajax( parameter );
        return result;
    };
    
    function initTreeNodeSelect(){
		var treeObj = $.fn.zTree.getZTreeObj("tree");
		var nodes = treeObj.getSelectedNodes();
		if(nodes.length > 0){//判断是否为初始化阶段
			
		}else{
			inittableUtil(treeObj,false);
		}
	};
	
	function inittableUtil(treeObj,flag){
		var n = null;
		if(flag){
			n = treeObj.getSelectedNodes()[0];
		}else{
			n = treeObj.getNodeByParam("id","GLOBAL");
		    treeObj.selectNode(n,false);
		}
	    var QUERYCONDITION= '{"rules":[{"field":"cidxSysScope","op":"like","value":"'+n.id+'"}],"groups":[],"op":"and"}';
	    inittableByCodeIdx(QUERYCONDITION,n.id);//初始化表格
	    return n;
	};
	function inittableUtil2(treeObj){
		var n = treeObj.getSelectedNodes()[0];
	    var QUERYCONDITION= '{"rules":[{"field":"pcodeId","op":"like","value":"'+n.id+'"},{"field":"codeId","op":"notequal","value":"'+n.id+'"}],"groups":[],"op":"and"}';
		inittableByCode(QUERYCONDITION,n.id,n.attributes.system);
		return n;
	};
	
    var setting = {
    		callback: {
    			beforeClick: zTreeBeforeClick,
    		},
    		async: {
    			enable: true,
    			url: _pathCode+"/treeBeanListBySid",
    			autoParam: ["id","type"]
    		},
			data: {
				simpleData: {
					enable: true
				}
			},
	};
    $.fn.zTree.init($("#tree"), setting, getAsyncUrl());
    
    initTreeNodeSelect();
	//代码集刷新
	$(".refreshCodeidx").click(function(){
		$('#tblResultCodeidx').datagrid('reload');
	});
	
	//代码刷新
	$(".refreshCode").click(function(){
		$('#tblResultCode').datagrid('reload');
	});
	
	//代码集重置
	$(".resetCodeidx").click(function(){
		resetutilsearchlist();
	});
	
	resetutilsearchlist = function() {
		var searchForm = formCodeIdx.get("eleArray");
		searchForm.map(function (i, elem) {
			$(this).val("");
		});
		//cIdxSysScope.selectValue("");
		var treeObj = $.fn.zTree.getZTreeObj("tree");
		return inittableUtil(treeObj,true);
	};
	
	//代码重置
	$(".resetCode").click(function(){
		resetutilsearchlist2();
	});
	
	resetutilsearchlist2 = function() {
		var searchForm = formCode.get("eleArray");
		searchForm.map(function (i, elem) {
			$(this).val("");
		});
		var treeObj = $.fn.zTree.getZTreeObj("tree");
		return inittableUtil2(treeObj);
	};
	
	//代码集查询
	$(".searchCodeidx").click(function(){
		var s = {
				"rules":[],
				"groups":[],
				"op":"and"
		};
		var treeObj = $.fn.zTree.getZTreeObj("tree");
		var nodes = treeObj.getSelectedNodes();
		var system= {
				field:"cidxSysScope",
				op:"like",
				value:nodes[0].id
		};
		s["rules"].push(system);
		var QUERYCONDITION =  formCodeIdx.initSearch(s);
		inittableByCodeIdx(QUERYCONDITION,nodes[0].id);
	});
	
	//代码查询
	$(".searchCode").click(function(){
		var s = {
				"rules":[],
				"groups":[],
				"op":"and"
		};
		var treeObj = $.fn.zTree.getZTreeObj("tree");
		var nodes = treeObj.getSelectedNodes();
		var codeIdx= {
				field:"pcodeId",
				op:"like",
				value:nodes[0].id
		};
		var code= {
				field:"codeId",
				op:"notequal",
				value:nodes[0].id
		};
		s["rules"].push(code);
		s["rules"].push(codeIdx);
		var QUERYCONDITION =  formCode.initSearch(s);
		inittableByCode(QUERYCONDITION,nodes[0].id);
	});
	
	//批量删除代码集
	$(".deleteCodeidx").click(function(){
		var deleteIdArray=[];
		var selectedRow = $('#tblResultCodeidx').datagrid('getSelections');  //获取选中行  
		$.each(selectedRow, function(i, n) {
			var id = eval( "n.cidxId" );
			deleteIdArray.push(id);
		});
		if(deleteIdArray.length <= 0){ // 判断是否选中数据
			Confirmbox.alert('请选择要删除的数据！');
			return false;
		}
		Confirmbox.confirm('是否确定要删除这些记录？','',function(){
			var parameter={
					url:_pathCodeIdx+'/deletebatch/'+deleteIdArray,
				    type:"POST",
				    async:false,
				    success:function(data){
				    	showMsg("codeIdxMsg",true,"删除成功");
				    	tableRefresh("","codeidx");
				    	setTimeout('hidden("codeIdxMsg");',3000);
				    },
				    error:function(result){
				    	showMsg("codeIdxMsg",true,"删除失败");
				    	setTimeout('hidden("codeIdxMsg");',3000);
				    }
				};
			$.ajax( parameter );
		},function(){
			
		});
	});
	
	//批量删除代码
	$(".deleteCode").click(function(){
		var deleteIdArray=[];
		var selectedRow = $('#tblResultCode').datagrid('getSelections');  //获取选中行  
		$.each(selectedRow, function(i, n) {
			var id = eval( "n.codeId" );
			deleteIdArray.push(id);
		});
		if(deleteIdArray.length <= 0){ // 判断是否选中数据
			Confirmbox.alert('请选择要删除的数据！');
			return false;
		}
		Confirmbox.confirm('是否确定要删除这些记录？','',function(){
			var parameter={
					url:_pathCode+'/deletebatch/'+deleteIdArray,
				    type:"POST",
				    async:false,
				    success:function(data){
				    	showMsg("codeMsg",true,"删除成功");
				    	tableRefresh("","code");
				    	setTimeout('hidden("codeMsg");',3000);
				    },
				    error:function(result){
				    	showMsg("codeMsg",true,"删除失败");
				    	setTimeout('hidden("codeMsg");',3000);
				    }
				};
			$.ajax( parameter );
		},function(){
			
		});
	});
	
	//代码集主键
	function getGridFrozenColumnsByCodeIdx() {
		return [[{
			field : 'ck',
			checkbox : true
		},{
			field : 'cidxId',
			width : 80,
			align : 'center',
			sortable : true,
			hidden : true
		}]];
	}
	
	//代码主键
	function getGridFrozenColumnsByCode() {
		return [[{
			field : 'ck',
			checkbox : true
		},{
			field : 'codeId',
			width : 80,
			align : 'center',
			sortable : true,
			hidden : true
		}]];
	}
	
	//初始化代码集表格列表
	function getGridColumnsByCodeIdx() {
		return [[ 
			     { field : 'cidxNameCn', title : '代码集中文名', width : 140, align : 'left', sortable : true }, 
			     { field : 'cidxSerial', title : '代码集编号', width :140, align : 'right', sortable : true },
			     { field : 'cidxNamePinyin', title : '代码集拼音', width : 80, align : 'left', sortable : true },  
			     { field : 'cidxSysScope', title : '所属系统', width : 100, align : 'left', sortable : true ,formatter : formatterSysByCode}, 
			 	 { field : 'opt', title : '操作', width : 120, align : 'center', formatter : getGridOperationByCodeIdx } 
		 		]];
	}
	
	//列表系统翻译
	function formatterSysByCode(value, rec, index){
		var data = sysCode;
		if(value){
			for ( var int = 0; int < data.length; int++) {
				var sys = data[int];
				for(var i in sys){
					var name = sys.systemName;
					if(sys[i] == value){
						return name;
					}
					if("GLOBAL" == value){
						return "全局系统";
					}
				}
			}
		}
		return "";
	}
	
	//列表代码集翻译
	function formatterCodeIdxByCode(value, rec, index){
		var data = sysCode;
		if(value){
			for ( var int = 0; int < data.length; int++) {
				var sys = data[int];
				for(var i in sys){
					var name = sys.systemName;
					if(sys[i] == value){
						return name;
					}
				}
			}
		}
		return "";
	}
	
	//初始化代码表格列表
	function getGridColumnsByCode() {
		return [[ 
			     { field : 'codeValueCn', title : '代码中文名', width : 140, align : 'left', sortable : true }, 
			     { field : 'codeValue', title : '代码值', width :140, align : 'right', sortable : true },
			     { field : 'codeValuePinyin', title : '代码拼音', width : 120, align : 'left', sortable : true }, 
			 	 { field : 'opt', title : '操作', width : 120, align : 'center', formatter : getGridOperationByCode } 
		 		]];
	}
		
	// 代码集操作
	function getGridOperationByCodeIdx(value, rec, index) {
		var v = '<a href="javascript:void(0)" onclick="tableRowDetailsSeeByCodeIdx(\'' + rec.cidxId + '\' )" >查看</a> ';
		var e = '<a href="javascript:void(0)" onclick="tableRowDetailsEditByCodeIdx(\'' + rec.cidxId + '\')" >编辑</a> ';
		var p = '<a href="javascript:void(0)" onclick="tableRowDetailsDeleteByCodeIdx(\'' + rec.cidxId + '\')" >删除</a> ';
		return v + e + p;
	}
	
	// 代码操作
	function getGridOperationByCode(value, rec, index) {
		var v = '<a href="javascript:void(0)" onclick="tableRowDetailsSeeByCode(\'' + rec.codeId + '\' )" >查看</a> ';
		var e = '<a href="javascript:void(0)" onclick="tableRowDetailsEditByCode(\'' + rec.codeId + '\')" >编辑</a> ';
		var p = '<a href="javascript:void(0)" onclick="tableRowDetailsDeleteByCode(\'' + rec.codeId + '\')" >删除</a> ';
		return v + e + p;
	}
	
	//代码集查看
	tableRowDetailsSeeByCodeIdx = function(id){
		selectCodeIdx.show(id);
	};
	
	//代码集查看
	tableRowDetailsSeeByCodeIdx = function(id){
		selectCodeIdx.show(id);
	};
	
	//代码集修改
	tableRowDetailsEditByCodeIdx = function(id){
		editCodeIdx.show(id);
	};
	
	//代码修改
	tableRowDetailsEditByCode = function(id){
		editCode.show(id);
	};
	
	//代码查看
	tableRowDetailsSeeByCode = function(id){
		selectCode.show(id);
	};
	
	//删除代码集
	tableRowDetailsDeleteByCodeIdx = function(id){
		Confirmbox.confirm('是否确定要删除该记录？','',function(){
			var parameter={
					url:_pathCodeIdx+'/delete/'+id,
				    type:"POST",
				    async:false,
				    success:function(data){
				    	showMsg("codeIdxMsg",true,"删除成功");
				    	tableRefresh("","codeidx");
				    	setTimeout('hidden("codeIdxMsg");',3000);
				    },
				    error:function(result){
				    	showMsg("codeIdxMsg",false,"删除失败");
				    	setTimeout('hidden("codeIdxMsg");',3000);
				    }
				};
			$.ajax( parameter );
		},function(){
			
		});
	};
	
	//删除代码
	tableRowDetailsDeleteByCode = function(id){
		var msg = "";
		Confirmbox.confirm('是否确定要删除该记录？','',function(){
			var parameter={
					url:_pathCode+'/delete/'+id,
				    type:"POST",
				    async:false,
				    success:function(data){
				    	showMsg("codeMsg",true,"删除成功");
				    	tableRefresh("","code");
				    	setTimeout('hidden("codeMsg");',3000);
				    },
				    error:function(result){
				    	showMsg("codeMsg",true,"删除失败");
				    	setTimeout('hidden("codeMsg");',3000);
				    }
				};
			$.ajax( parameter );
		},function(){
			
		});
	};
	
	//为子页面提供方法，获取父页面点击节点的ID
	getParentId = function(){
		var parent = {
			presId	: presId,
			codeIdx : codeIdx
		};
		return parent;
	};
	
	//刷新
	tableRefresh = function(id,type){
		var n = null;
    	if(type == 'code'){
    		//右侧树列表的刷新
    		n = resetutilsearchlist2();
    	}else{
    		//右侧树列表的刷新
    		n = resetutilsearchlist();
    	}
    	//左侧树节点的刷新
    	$.fn.zTree.init($("#tree"), setting, getAsyncUrl());
    	var treeObj = $.fn.zTree.getZTreeObj("tree");
    	treeObj.selectNode(n,false);
	};
	
	showMsg = function (type,isSuccess,Msg){
		$("."+type).css("display","");
		if(isSuccess){
			$(".showColor").removeClass("error");
			$(".showColor").addClass("succeed");
			$(".showColor").html("&#xf00a1;");
		}else{
			$(".showColor").removeClass("succeed");
			$(".showColor").addClass("error");
			$(".showColor").html("&#xf0098;");
		}
		$(".Msg").html(Msg);
	};
	
	hidden = function(type){
		$("."+type).css("display","none");
	};
	
	//查询系统下拉框查询列
	getSystemList = function(){
		var result = "";
		var parameterS={
				url:'resource/ormsystem/system/list',
			    type:"POST",
			    async:false,
			    success:function(data){
			    	result = data;
			    	sysCode = result;
			    },
			    error:function(result){
			    	Confirmbox.alert('获取系统列表数据错误！');
			    }
			};
		$.ajax( parameterS );	
		return result;
	};
	
	//拼接系统下拉格式
	getSelectSystem = function(){
		var arr = [{
			value:"",
			text:"-请选择-"
		},{
			value:"GLOBAL",
			text:"全局系统"
		}];
		var data = getSystemList();
		$.each(data,function(n,systemObj) {   
			modelJSON = {
					value:systemObj.systemId,
					text:systemObj.systemName
			};
			arr.push(modelJSON);
    	});
		return arr;
	};
	getSystemList();
});