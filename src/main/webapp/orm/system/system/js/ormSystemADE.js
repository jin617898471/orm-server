define(function(require, exports, module) {
	var Confirmbox = require("inno/dialog/1.0.0/confirmbox-debug"),
		Validator = require("inno/validator/1.0.0/validator-debug"), 
		Form = require("form"),
		$ = require("$");

	var _path = "ormsystem";

	// 验证
	var validator = new Validator({
		element : '#systemADEForm',
		failSilently : true
	});

	validator.addItem({
		element : 'input[name=\'systemName\']',
		display : "系统名称",
		required : true,
		rule : 'minlength{"min":-1}',
	}).addItem({
		element : 'input[name=\'systemCode\']',
		display : "系统标示符",
		required : true,
		rule : 'minlength{"min":-1}',
	});

	// 验证
	var checkAll = true;
	var message = '';

	$("#systemCode").blur(function() {
		var systemCode = this.value;
		if (systemCode.length > 0) {
			if (systemCode == 'GLOBAL') {
				message = '系统标识重复！GLOBAL为默认系统名！';
				$(".msgText").html(message);
				$(".iconfonthide").hide();
				$(".iconfonthide2").show();
				checkAll = false;
				return;
			}
			if (!(/^[A-Za-z]+$/.test(systemCode))) {
				message = '请输入纯英文字母 ！';
				$(".msgText").html(message);
				$(".iconfonthide").hide();
				$(".iconfonthide2").show();
				checkAll = false;
				return;
			}
			var check = true;
			var parameter = {
				url : _path + '/checkOnlyOne/' + systemCode,
				type : "POST",
				async : false,
				success : function(data) {
					check = data;
				},
				error : function(result) {
					Confirmbox.alert('数据异常！');
				}
			};
			$.ajax(parameter);
			if (!check) {
				message = '系统标识重复！';
				$(".msgText").html(message);
				$(".iconfonthide").hide();
				$(".iconfonthide2").show();
				checkAll = false;
				return;
			}
			checkAll = true;
			$(".iconfonthide").show();
			$(".msgText").html("");
		} else {
			$(".iconfonthide").hide();
			$(".msgText").html("");
		}
	});
	$("#systemCode").keyup(function() {
		var upper = $("#systemCode").val();
		$("#systemCode").val(upper.toUpperCase());
	});
	// 判断标识：标识为新增或者查看或者编辑
	var sign = $("#sign").val();
	if (sign == "details") {
		$("#btnSave").css("visibility", "hidden");
		$(":text").each(function() {
			this.disabled = true;
		});
		$("#systemDesc").attr("disabled", true);
		$(".ui-textarea-border").addClass("ui-textarea-disable");
	} else if (sign == "edit") {
		$("#systemCode").attr("disabled", true);
	}

	require("easyui");

	// submit
	var systemADEForm = new Form({
		trigger : "#systemADEForm",
		addUrl : _path + "/addSystem",
		setUrl : _path + "/editSystem"
	});

	validator.element.on("submit", function(e) {
		$('#btnSave').attr("disabled", true);
		e.preventDefault();
		validator.execute(function(err) {
			if (checkAll) {
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
					var option = {
						type : type,
						successFn : function() {
							showInformation(true);
							$(".msgText").text(name+"成功");
							if(parent.resetutilsearchlist){
								parent.resetutilsearchlist();
							}
							$('#btnSave').attr("disabled", false);
						},
						errorFn : function() {
							showInformation(false);
							$(".msgText").text(name+"失败");
							$('#btnSave').attr("disabled", false);
						},
					};
					systemADEForm.saveData(option);
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
	
	//显示提示信息
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