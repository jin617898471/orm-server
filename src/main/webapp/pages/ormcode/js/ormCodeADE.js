define(function(require,exports,module){
	var Menu=require("menu"),
		Confirmbox=require("inno/dialog/1.0.0/confirmbox-debug"),
		Validator = require("inno/validator/1.0.0/validator-debug"),
		Form = require("form"),
		$=require("$");
	
		//判断标识：标识为新增或者查看或者编辑
		var sign = $("#sign").val();
		//判断标识:false为保存,true为保存后继续;
		var refresh = false;
		
		if(sign == "detail"){//查看
			$(":text").each(function() {
				this.disabled = true;
			});
			$(".ui-textarea").attr("disabled", true);
			$(".ui-textarea-border").addClass("ui-textarea-disable");
			$("#btnSave").css("visibility","hidden");
		}else {//新增
			var checkObj = parent.getParentId();
			for(var i in checkObj){
				var presId = checkObj.presId;
				var codeIdx = checkObj.codeIdx;
				if(presId){
					$("input[name='pcodeId']").val(presId);
				}
				if(codeIdx){
					$("input[name='cidxId']").val(codeIdx);
				}
			}
		}
		require("easyui");
		
		var _path="resource/ormcode";
		
		var validator = new Validator({
	    	element: '#OrmCodeADEForm',
	    	failSilently: true
		});
		
		validator.addItem({
	        element: 'input[name=\'codeValueCn\']',
	        display:"代码中文",
	        required: true,
	        rule: 'minlength{"min":-1}'
	    }).addItem({
	        element: 'input[name=\'codeValue\']',
	        display:"代码值",
	        required: true,
	        rule: 'minlength{"min":-1}'
	    });
	    
		var OrmCodeIdxADEForm =new Form({
			trigger:"#OrmCodeADEForm",
			addUrl :  _path+"/add",
			setUrl : _path+"/edit"
		});
		
		//验证下拉框是否被选择
		checkonly = function(){
			var message = '';
			var arrvalue = [];
			$.each($(".ui-select-selected"),function(n,datavalue) {   
				arrvalue.push($(datavalue).attr("data-value"));
			});
			if(arrvalue[0]==""){
				message = '请选择所属系统！';
				$(".msgText").html(message);
				return false;
			}
			$(".msgText").html("");
			return true;
		};
		
		validator.element.on("submit", function(e) {
			e.preventDefault();
	        validator.execute(function(err) {
	        	if(checkonly()){
						if(err){
							!err && validator.get("autoSubmit") && validator.element.get(0).submit(); 
						}else{
							var type ;
							var name;
							if(sign == 'edit'){
								type = 2;
								name = "编辑";
							}else {
								type = 1;
								name = "新增";
							}
							OrmCodeIdxADEForm.saveData({
								type:type,
								successFn :function(result){
									if( refresh ){
										$(".ui-input").val(""); 
										$(".ui-textarea").val(""); 
									}
									if(result == 'true'){
										showInformation(true);
										$(".msgText").text(name+"成功");
										parent.tableRefresh($("input[name = pcodeId]").val(),"code");
									}else{
										showInformation(false);
										$(".msgText").text("代码值已存在，请重新输入！");
									}
								},
								errorFn :function(result){
									showInformation(false);
									$(".msgText").text(name+"失败");
								},
						});
							return false;
						} 
	        	}
	        });
	    });
		
		$("#btnCancel").click(function(){
			window.frameElement.trigger('close'); 
		});
		$("#btnSaveContinue").click(function(){
			refresh = true;
		});
		
		showInformation = function(type){
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
