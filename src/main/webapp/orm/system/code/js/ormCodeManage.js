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
		
	var _pathCode="/orm-server/ormCode";
	
	
	var sysCode = "";//系统代码合集，供翻译及下拉
	
	var presId  = "";//父节点Id ，供子页面调用
	
	var codeIdx = "";
	
	
	var formCode =new Form({
		trigger:".search-formCode"
	});
	
	var menu =new Menu({
		trigger:"#nav"
	});
	
	inittableByCode = function (QUERYCONDITION,codeId,systemid){
		$('#tblResultCode').datagrid({
			queryParams : {QUERYCONDITION:QUERYCONDITION},
			nowrap : true,
			autoRowHeight : true,
			striped : true,
			collapsible : true,
			url : _pathCode+'/findCode/parentCodeId='+codeId,
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
        content: _pathCode+"/forward/addCode",
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
	    	var url = _pathCode+"/forward/details/"+id;
	    	this.set('content',url);
	});
	
	var editCode = new Dialog({
        width:'800px',
        height:'300px',
        scrolling:true,
        title:"代码编辑"
    }).before('show',function(id){
    	var url= _pathCode+"/forward/editCode/"+id;
    	this.set('content',url);
    });
	
	
//    var setting = {
//    		callback: {
//   			beforeClick: zTreeBeforeClick,
//    		},
//    		async: {
//    			enable: true,
//    			url: _pathCode+"/getCodeTree",
//    			autoParam: ["id","type"]
//    		},
//			data: {
//				simpleData: {
//					enable: true
//				}
//			},
//	};
//    $.fn.zTree.init($("#tree"), setting);
	
    var setting = {  
       		view: {
       			  //dblClickExpand: false,
       			  expandSpeed: 100 //设置树展开的动画速度
       		},
       		//获取json数据
            async : {
                enable : true, 
                url :   _pathCode+"/findCode/", // Ajax 获取数据的 URL 地址  
                autoParam : [ "id", "type" ] //ajax提交的时候，传的是id值
            }, 
            data:{ // 必须使用data  
                simpleData : {  
                    enable : true,  
                }  
            },  
            // 回调函数  
            //treeId
            callback : {
            	
                onClick : function(event, treeId, treeNode, clickFlag) {  
                    if(true) {
                        alert(" 节点id是：" + treeNode.id + ", 节点文本是：" + treeNode.name);      
                    }
                },  
                //捕获异步加载出现异常错误的事件回调函数 和 成功的回调函数  
                onAsyncSuccess : function(event, treeId, treeNode, msg){ 
                }  
            }  
        };  
      
	    function getAsyncUrl(treeId, treeNode) {
	    	var result = "";
	    	var parameter={
					url:_pathCode+'/getCodeTree',
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
    
        $.fn.zTree.init($("#tree"), setting, getAsyncUrl());  
        
    
	//代码刷新
	$(".refreshCode").click(function(){
		$('#tblResultCode').datagrid('reload');
	});
	
	resetutilsearchlist2 = function() {
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
	
	
	//代码查询
	$(".searchCode").click(function(){
		var s = {
				"rules":[],
				"groups":[],
				"op":"and"
		};
		var treeObj = $.fn.zTree.getZTreeObj("tree");
		var nodes = treeObj.getSelectedNodes();
		var code= {
				field:"codeId",
				op:"notequal",
				value:nodes[0].id
		};
		s["rules"].push(code);
		var QUERYCONDITION =  formCode.initSearch(s);
		inittableByCode(QUERYCONDITION,nodes[0].id);
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
	
	
	//初始化代码表格列表
	function getGridColumnsByCode() {
		return [[ 
			     { field : 'codeName', title : '代码中文名', width : 140, align : 'left', sortable : true }, 
			     { field : 'codeValue', title : '代码值', width :140, align : 'right', sortable : true },
			 	 { field : 'opt', title : '操作', width : 120, align : 'center', formatter : getGridOperationByCode } 
		 		]];
	}
		
	
	// 代码操作
	function getGridOperationByCode(value, rec, index) {
		var v = '<a href="javascript:void(0)" onclick="tableRowDetailsSeeByCode(\'' + rec.codeId + '\' )" >查看</a> ';
		var e = '<a href="javascript:void(0)" onclick="tableRowDetailsEditByCode(\'' + rec.codeId + '\')" >编辑</a> ';
		var p = '<a href="javascript:void(0)" onclick="tableRowDetailsDeleteByCode(\'' + rec.codeId + '\')" >删除</a> ';
		return v + e + p;
	}
		
	//代码修改
	tableRowDetailsEditByCode = function(id){
		editCode.show(id);
	};
	
	//代码查看
	tableRowDetailsSeeByCode = function(id){
		selectCode.show(id);
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
				url:'/orm-sever/ormsystem/findAll',
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