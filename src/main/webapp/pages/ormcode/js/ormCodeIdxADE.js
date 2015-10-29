define(function(require,exports,module){
	var Menu=require("menu"),
		Select = require("inno/select/1.0.0/select-debug"),
		Confirmbox=require("inno/dialog/1.0.0/confirmbox-debug"),
		Validator = require("inno/validator/1.0.0/validator-debug"),
		Form = require("form"),
		$=require("$");
	
		//判断标识：标识为新增或者查看或者编辑
		var sign = $("#sign").val();
		
		if(sign == "detail"){
			$(":text").each(function() {
				this.disabled = true;
			});
			$(".ui-textarea").attr("disabled", true);
			$(".ui-textarea-border").addClass("ui-textarea-disable");
			$("#btnSave").css("visibility","hidden");
		}else if(sign == "add"){
			
		}
		require("easyui");
		
		var _path="resource/ormcodeidx";
		
		var validator = new Validator({
	    	element: '#OrmCodeIdxADEForm',
	    	failSilently: true
		});
		
		validator.addItem({
	        element: 'input[name=\'cidxNameCn\']',
	        display:"代码集中文",
	        required: true,
	        rule: 'minlength{"min":-1}',
	    }).addItem({
	        element: 'input[name=\'cidxSerial\']',
	        display:"代码集编号",
	        required: true,
	        rule: 'minlength{"min":-1}',
	    });
		
		function getSysList(){
	    	var system  = parent.getSelectSystem();
	    	var checkObj = parent.getParentId();
			var id = checkObj.presId;
	    	if(sign == "edit"){
	    		for ( var int = 0; int < system.length; int++) {
					var sys = system[int];
					sys.disabled = true;
				}
	    	}else{
				if(id){
					for ( var int = 0; int < system.length; int++) {
						var sys = system[int];
						var sid = sys.value;
						if(sid == id){
							sys.disabled = false;
							sys.selected = true;
						}else{
							sys.disabled = true;
						}
					}
				}else{
					return system;
				}
	    	}
	    	return system;
	    };
		
		//初始化系统下列列表
		var cIdxSysScope = new Select({
	    	trigger: '.cidxSysScope',
	    	width:'190px',
	    	name:'cidxSysScope',
	    	model: getSysList(),
		}).render();
		
		cIdxSysScope.selectValue($("#cidxSysScope").val());
		
		var OrmCodeIdxADEForm =new Form({
			trigger:"#OrmCodeIdxADEForm",
			addUrl :  _path+"/add",
			setUrl : _path+"/edit"
		});
		
		//验证下拉框是否被选择
		checkAllSelect = function(){
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
		
		getCodeIdxValueOnly = function(){
			var result = true;
			var cidxSerial = $("input[name = cidxSerial]").val();
			var parameterS={
					url: _path+'/getCodeIdxValueOnly',
				    type:"POST",
				    data:{cidxSerial:cidxSerial},
				    async:false,
				    success:function(data){
				    	result = data;
				    },
				    error:function(result){
				    	Confirmbox.alert('校验唯一性数据错误！');
				    }
				};
			$.ajax( parameterS );	
			return result;
		};
		
		validator.element.on("submit", function(e) {
			e.preventDefault();
	        validator.execute(function(err) {
	        	if(checkAllSelect()){
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
									if(result == 'true'){
										showInformation(true);
										$(".msgText").text(name+"成功");
										parent.tableRefresh($("input[name = presId]").val());
									}else{
										showInformation(false);
										$(".msgText").text("该代码集编号已存在！");
									}
								},
								errorFn :function(result){
									showInformation(false);
									$(".msgText").text(name+"失败");
								},
						});
							return false;
						} 
	        		}else{
	        			showInformation(false);
	        			$(".msgText").text("");
	        		}
	        });
	    });
		if (sign == "detail") {
			$("a").unbind();
		};
		$("#btnCancel").click(function(){
			window.frameElement.trigger('close'); 
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
