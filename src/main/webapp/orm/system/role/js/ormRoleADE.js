define(function(require, exports, module) {
	var Select = require("inno/select/1.0.0/select-debug"),
		Confirmbox = require("inno/dialog/1.0.0/confirmbox-debug"),
		Validator = require("inno/validator/1.0.0/validator-debug"),
		//SelectTree = require("inno/select-tree/1.0.0/select-tree-debug"), 
		//CodeProvider = require("../../../common/js/codeProvider"),
		Form = require("form"),
		$ = require("$");

	require("./CHTOEN");
	require("easyui");
	
	var _path = "role/ormrole";

	// 判断标识：标识为新增或者查看或者编辑
	var sign = $("#sign").val();

	if (sign == "detail") {
		$("#btnSave").css("visibility", "hidden");
		$(":text").each(function() {
			this.disabled = true;
		});
		$(".ui-textarea").attr("disabled", true);
		$(".ui-textarea-border").addClass("ui-textarea-disable");
	} else {
		$("input[name='roleNameEn']").attr("disabled", true);
	}

	//var roleOrg = $("#roleOId").val();
	//var roleTypeObj = $("#roleType").val();
	var roleSystem = $("#systemId").val();

	// 中文转拼音
	CC2PY = function(l1) {
		var l2 = l1.length;
		var I1 = "";
		var reg = new RegExp('[a-zA-Z0-9\- ]');
		for (var i = 0; i < l2; i++) {
			var val = l1.substr(i, 1);
			var name = arraySearch(val, PinYin);
			if (reg.test(val)) {
				I1 += val;
			} else if (name !== false) {
				I1 += name;
			}

		}
		I1 = I1.replace(/ /g, '-');
		while (I1.indexOf('--') > 0) {
			I1 = I1.replace('--', '-');
		}
		return I1.toUpperCase();
	};
	arraySearch = function(l1, l2) {
		for ( var name in PinYin) {
			if (PinYin[name].indexOf(l1) != -1) {
				return ucfirst(name);
				break;
			}
		}
		return false;
	};
	ucfirst = function(l1) {
		if (l1.length > 0) {
			var first = l1.substr(0, 1).toUpperCase();
			var spare = l1.substr(1, l1.length);
			return first + spare;
		}
	};
//	document.blur = function(e) {
//		var act = document.activeElement.name; // 获得焦点的ID
//		if (act == "roleNameCn") {
//			var valrole = $("input[name='roleNameCn']").val();
//			$("input[name='roleNameEn']").attr("value", CC2PY(valrole));
//		}
//	};
	
	$("input[name=roleNameCn]").blur(
		function() {
			var valrole = $("input[name='roleNameCn']").val();
			$("input[name='roleNameEn']").attr("value", CC2PY(valrole));
		}
	);

	// 验证
	var validator = new Validator({
		element : '#roleADEForm',
		failSilently : true
	});

	validator.addItem({
		element : 'input[name=\'roleNameCn\']',
		display : "中文名称",
		required : true,
		rule : 'minlength{"min":-1}'
	});

	//document.onmousemove = function(e) {
		//checkAllSelect();
	//};

	// 验证
	var message = '';
	checkAllSelect = function() {
		var arrvalue = [];
		$.each($(".ui-select-selected"), function(n, datavalue) {
			arrvalue.push($(datavalue).attr("data-value"));
		});
		//arrvalue.push($(".org").text().trim());
		if (arrvalue[0] == "") {
			message = '请选择所属系统 ！';
			$(".msgText").html(message);
			$(".iconfonthide").hide();
			$(".iconfonthide2").show();
			return false;
		}
//		if (arrvalue[1] == "") {
//			message = '请选择角色类型！';
//			$(".msgText").html(message);
//			$(".iconfonthide").hide();
//			return false;
//		}
//		if (arrvalue[2] == "") {
//			message = '请选择是否反向角色！';
//			$(".msgText").html(message);
//			$(".iconfonthide").hide();
//			return false;
//		}
//		if (arrvalue[3].length == 2) {
//			message = '请选择组织机构！';
//			$(".msgText").html(message);
//			$(".iconfonthide").hide();
//			return false;
//		}
		$(".iconfonthide").show();
		$(".msgText").html("");
		return true;
	};

	//初始化系统下拉框查询列
	getSystemList = function(){
		var arr = [];
		var modelJSON= {
				value:"",
				text:"-请选择-"
		};
		arr.push(modelJSON);
		var parameterS={
				url:'resource/ormresource/systemList',
			    type:"POST",
			    async:false,
			    success:function(data){
		    		$.each(data,function(n,systemObj) {   
		    			modelJSON = {
		    					value:systemObj.systemId,
		    					text:systemObj.systemName
		    			};
		    			arr.push(modelJSON);
			    	});
			    },
			    error:function(result){
			    	Confirmbox.alert('获取系统列表数据错误！');
			    }
			};
		$.ajax( parameterS );	
		return arr;
	};
	
	// 获取系统列表，用于下列列表
	var systemId = new Select({
		trigger : '.systemId',
		width : '190px',
		name : 'systemId',
		model : getSystemList()
	}).render();

	systemId.selectValue(roleSystem);
	
	if (sign == "detail") {
		$("a").unbind();
	};

	//CodeProvider.provider({"queryBeans":[{'codeSerial':'ROLE_TYPE','dataType':'select'}]});
	
	// 初始化角色类型下拉框查询列
//	getRoleTypeList = function() {
//		var arr = [];
//		modelJSON = {
//			value : '',
//			text : '-请选择-'
//		};
//		arr.push(modelJSON);
//		var arr2 = CodeProvider.getCode("ROLE_TYPE");
//		$.each(arr2, function(i,val){
//			if(val.value != 'ROOTADMIN'){
//				arr.push(val);
//			}
//		});
//		return arr;
//	};
	
	// 初始化角色类型下拉列表
//	var roleType = new Select({
//		trigger: '.roleType',
//		width:'190px',
//		name:'roleType',
//		model: getRoleTypeList(),
//	}).render();
//
//	roleType.selectValue(roleTypeObj);
//
//	CodeProvider.provider({"queryBeans":[{'codeSerial':'ROLE_NEGATIVE_FLAG','dataType':'select'}]});
	
	// 初始化反向角色下拉框查询列
//	getNegativeFlagList = function() {
//		var arr = [];
//		modelJSON = {
//			value : '',
//			text : '-请选择-'
//		};
//		arr.push(modelJSON);
//		var arr2 = CodeProvider.getCode("ROLE_NEGATIVE_FLAG");
//		$.each(arr2, function(i,val){
//			arr.push(val);
//		});
//		return arr;
//	};
//
//	// 获得机构tree
//	getOrgSelectTree = function() {
//		var data = "";
//		var parameter = {
//			url : 'organization/ormuser/selectTreeOrg',
//			type : "POST",
//			async : false,
//			success : function(result) {
//				data = result;
//			},
//			error : function(result) {
//				Confirmbox.alert('查询失败！');
//			}
//		};
//		$.ajax(parameter);
//		return data;
//	};
//
//	var roleOrgId = new SelectTree({
//		trigger : '.org',
//		mult : false,
//		width : '190px',
//		height : '180px',
//		name : "roleOId",
//		model : getOrgSelectTree(),
//	}).render();
//
//	roleOId.selectValue(roleOrg);

	// submit
	var roleADEForm = new Form({
		trigger : "#roleADEForm",
		addUrl : "./"+_path + "/add",
		setUrl : "./"+_path + "/edit"
	});

	validator.element.on("submit", function(e) {
		e.preventDefault();
		validator.execute(function(err) {
			if (checkAllSelect()) {
				if (err) {
					!err && validator.get("autoSubmit")
							&& validator.element.get(0).submit();
				} else {
					var type;
					var name;
					if (sign == 'edit') {
						type = 2;
						name = "编辑";
					} else {
						type = 1;
						name = "新增";
					}
					roleADEForm.saveData({
						type : type,
						successFn : function(result) {
							if ("false" == result) {
								showInformation(false);
								$(".msgText").text(name+"失败");
							} else {
								showInformation(true);
								$(".msgText").text(name+"成功");
//								Confirmbox.alert(name + '成功！', function() {
//									parent.resetutilsearchlist();
//									window.frameElement.trigger("close");
//								});
								if(parent.tableRefresh){
									parent.tableRefresh();
								}else{
									parent.tableRefresh();
								}
							}
						},
						errorFn : function(result) {
							showInformation(false);
							$(".msgText").text(name+"失败");
							
						},
					});
					return false;
				}
			} else {
				if (message == "") {
					$(".iconfonthide").show();
				}
				$(".msgText").html(message);
			}
		});
	});

	$("#btnCancel").click(function() {
		window.frameElement.trigger('close');
	});
	
	var showInformation = function(type){
		if(type){
			$(".msgiconfont").removeClass("error");
			$(".msgiconfont").addClass("succeed");
			$(".msgiconfont").html("&#xf00a1;");
		}else{
			$(".msgiconfont").removeClass("succeed");
			$(".msgiconfont").addClass("error");
			$(".msgiconfont").html("&#xf0098;");
		}
	};
});
