define(function(require,exports){
	var Menu=require("menu"),
		Dialog=require("inno/dialog/1.0.0/dialog-debug"),
		Confirmbox=require("inno/dialog/1.0.0/confirmbox-debug"),
		Form = require("form"),
		SelectTree=require("inno/select-tree/1.0.0/select-tree-debug"),
		CodeProvider = require("../../../common/js/codeProvider");
		$=require("$");

	require("easyui");
	
	var _path="organization/ormuser";
	
	var form =new Form({
		trigger:".search-form"
	});
	
	inittable = function (QUERYCONDITION){
		$('#tblResult').datagrid({
			queryParams : {QUERYCONDITION:QUERYCONDITION},
			nowrap : true,
			autoRowHeight : true,
			striped : true,
			collapsible : true,
			url : _path+'/list',
			sortName : 'CREATE_DT',
			sortOrder : 'desc',
			idField : 'USER_ID',
			frozenColumns : getGridFrozenColumns(),
			columns : getGridColumns(),
			pagination : true,
			rownumbers : true,
			pageList:[10,20,50,100,200,500]
		});
	};
	
	var add = new Dialog({
        trigger: '.ui-button-add',
        content: './'+_path+"/forward/add",
        width:'800px',
        height:'540px',
        scrolling:true,
        title:"用户新增"
    });
	
	var select = new Dialog({
		 	width:'800px',
	        height:'540px',
	        scrolling:true,
	        title:"用户查看"
	    }).before('show',function(id){
	    	var url='./'+_path+"/forward/detail/"+id;
	    	this.set('content',url);
	});
	
	var edit = new Dialog({
        width:'800px',
        height:'540px',
        scrolling:true,
        title:"用户编辑"
    }).before('show',function(id){
    	var url='./'+_path+"/forward/edit/"+id;
    	this.set('content',url);
    });
	
	CodeProvider.provider({"queryBeans":[{'codeSerial':'USER_SEX','dataType':'select'}]});
	
	inittable("");//初始化表格
	
	//查询组织机构下拉树
	getOrgSelectTree = function(){
		var data = "";
		var parameter={
				url:_path+'/selectTreeOrg',
			    type:"POST",
			    async:false,
			    success:function(result){
			    	data = result;
			    },
			    error:function(result){
			    	Confirmbox.alert('查询失败！');
			    }
			};
		$.ajax( parameter );
		return data;
	};
	
	var one= new SelectTree({
        trigger: '.O_SERIAL',
        height:'200px',
        width: '188px',
        name: 'O_SERIAL',
//        mult:false,
        cascade:false,
        checkSelect:function(target,mult){
        	//flase时表示选中,true为取消选中
        	var flag = $(target).find("span").hasClass("ui-select-tree-checked");
        	var value = "";
        	if(!flag){
        		var val_ = $("input[name = SERIAL]").val();
        		value=val_ +","+$(target).attr("data-oserial");
        	}else {
        		value = "noCheck";
        	}
            if(value){
            	if("noCheck" == value){//若该节点取消选中,则去除该节点的value值
            		var ncValue = $(target).attr("data-oserial");
            		value = $("input[name = SERIAL]").val();
            		if(!value.indexOf(",", 0)){
            			value = "";
            		}else {
            			value += ",";
            			value = value.replace(ncValue+",", "");
            			value = value.substr(0,value.length-1);
            		}
            	}else {
            		if(","==value.substring(0,1)){//第一次去掉首位的逗号
            			value = value.substr(1,value.length-1);
            		}
            	}
	            $("input[name = SERIAL]").val(value);
	            return true;
            }else{
            	alert("您勾选的节点不在您所在的权限范围内!");
            }
            return false;
        },
        model: getOrgSelectTree(),
    }).render();
	
	//刷新
	$(".ui-button-refresh").click(function(){
		tableRefresh();
	});
	
	//重置
	$(".ui-button-reset").click(function(){
		resetutilsearchlist();
	});
	
	resetutilsearchlist = function() {
		var searchForm = form.get("eleArray");
		searchForm.map(function (i, elem) {
			$(this).val("");
		});
		one.resetSelect();
		$("input[name = SERIAL]").val("");
		inittable("");//初始化表格
	};
	
	//查询
	$(".ui-button-search").click(function(){
		var s = {
				"rules":[],
				"groups":[],
				"op":"and"
		};
		var orgid = $("input[name = SERIAL]").val();
		var org= {
				field:"m.O_SERIAL",
				op:"in",
				value:orgid
		};
		if(orgid){
			s["rules"].push(org);
		}
		var QUERYCONDITION =  form.initSearch(s);
		inittable(QUERYCONDITION);
	});
	
	$(".ui-button-delete").click(function(){
		var deleteIdArray=[];
		var selectedRow = $('#tblResult').datagrid('getSelections');  //获取选中行  
		$.each(selectedRow, function(i, n) {
			var id = eval( "n.USER_ID" );
			deleteIdArray.push(id);
		});
		if(deleteIdArray.length <= 0){ // 判断是否选中数据
			Confirmbox.alert('请选择要删除的数据！');
			return false;
		}
		Confirmbox.confirm('是否确定要删除这些记录？','',function(){
			var parameter={
					url:_path+'/deletebatch/'+deleteIdArray,
				    type:"POST",
				    async:false,
				    success:function(data){
				    	Confirmbox.alert('删除成功！',function(){
				    		tableRefresh();
				    	});
				    },
				    error:function(result){
				    	Confirmbox.alert('删除失败！');
				    }
				};
			$.ajax( parameter );
		},function(){
			
		});
	});
	
	//主键
	function getGridFrozenColumns() {
		return [[{
			field : 'ck',
			checkbox : true
		},{
			field : 'USER_ID',
			width : 80,
			align : 'center',
			sortable : true,
			hidden : true
		}]];
	}
	
	// 获取系统name用于列表显示
	getOrgObjList = function() {
		var objList = null;
		var parameterS = {
			url : _path+'/getorglist',
			type : "POST",
			async : false,
			success : function(data) {
				objList = data;
			},
			error : function(result) {
				Confirmbox.alert('获取组织机构数据错误！');
			}
		};
		$.ajax(parameterS);
		return objList;
	};
	var objListName = getOrgObjList();
	getOrgName = function(serial) {
		var name = "";
		var sers = serial.split(",");
		$.each(sers, function(n, val) {
			name += ","+objListName[val];			 
		});
		return name.substring(1);
	};

	//初始化表格列表
	function getGridColumns() {
		return [[ 
			     { field : 'USER_ACCT', title : '用户账号', width : 80, align : 'left', sortable : true }, 
			     { field : 'USER_ACCT_CN', title : '用户名称', width :80, align : 'left', sortable : true },
			     { field : 'O_SERIAL', title : '组织机构', width : 260, align : 'left', sortable : true ,
			    	  formatter: function(value,row,index){
			    		  var abValue = getOrgName(value);
			    		  var abtitle = "";
			    		  if (abValue.length>=23) {  
			    			  abtitle = abValue.substring(0,20) + "...";  
			    			  var content = '<a href="javascript:void(0);" title="' + abValue + '" class="note">' + abtitle + '</a>';  
			    			  return content;  
			    		  }else{
			    			  return abValue;
			    		  }
		              }	
			     },  
			     { field : 'USER_SEX', title : '性别', width : 100, align : 'center', sortable : true ,formatter : formatterSexByCode}, 
				 { field : 'USER_MOBILE', title : '手机号码', width : 120, align : 'center', sortable : true },
				 { field : 'USER_TEL', title : '办公电话', width : 110, align : 'center', sortable : true },
			 	 { field : 'opt', title : '操作', width : 120, align : 'center', formatter : getGridOperation } 
		 		]];
	}
	
	// 操作
	function getGridOperation(value, rec, index) {
		var v = '<a href="javascript:void(0)" onclick="tableRowDetailsSee(\'' + rec.USER_ID + '\' )" >查看</a> ';
		var e = '<a href="javascript:void(0)" onclick="tableRowDetailsEdit(\'' + rec.USER_ID + '\')" >编辑</a> ';
		var p = '<a href="javascript:void(0)" onclick="tableRowDetailsDelete(\'' + rec.USER_ID + '\')" >删除</a> ';
		return v + e + p;
	}
	
	function formatterSexByCode(value, rec, index){
		return CodeProvider.translate("USER_SEX",value);
	}
	
	tableRowDetailsSee = function(id){
		select.show(id);
	};
	
	tableRowDetailsEdit = function(id){
		edit.show(id);
	};
	
	tableRowDetailsDelete = function(id){
		Confirmbox.confirm('是否确定要删除该记录？','',function(){
			var parameter={
					url:_path+'/delete/'+id,
				    type:"POST",
				    async:false,
				    success:function(data){
				    	tableRefresh();
				    },
				    error:function(result){
				    	Confirmbox.alert('删除失败！');
				    }
				};
			$.ajax( parameter );
		},function(){
			
		});
	};
	
	tableRefresh = function(){
		$('#tblResult').datagrid('reload');
	};
	
});