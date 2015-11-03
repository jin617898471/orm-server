define(function(require,exports,module){
	var Menu=require("menu"),
		Select = require("inno/select/1.0.0/select-debug"),
		Confirmbox=require("inno/dialog/1.0.0/confirmbox-debug"),
		Validator = require("inno/validator/1.0.0/validator-debug"),
		Form = require("form"),
		$=require("$");
	
		//判断标识：标识为新增或者查看或者编辑
		var sign = $("#sign").val();
		
		var systemId = ""; // 用于过滤下拉系统
		
		var type = new Select({
	    	trigger: '.resourceType',
	    	width:'190px',
	    	name:'resourceType',
	    	model:getResTypeList()
		}).render();
		
	    type.selectValue($("#resourceType").val());
	    
		var sys = new Select({
	    	trigger: '.systemId',
	    	width:'190px',
	    	name:'systemId',
	    	model: getSysList()
		}).render();
		
		sys.selectValue($("#systemId").val());
		
		if(sign == "detail"){
			$(":text").each(function() {
				this.disabled = true;
			});
			$(".ui-textarea").attr("disabled", true);
			$(".ui-textarea-border").addClass("ui-textarea-disable");
			$("#btnSave").css("visibility","hidden");
		}else if(sign == "add"){
			var checkObj = parent.getParentId();
			var parentResId = checkObj.parentResId;
			systemId = checkObj.systemId;
			if(parentResId){
				$("input[name='parentResId']").val(parentResId);
				sys.selectValue(systemId);
			}else{
				$("input[name='parentResId']").val(systemId);
			}
		}
		require("easyui");
		
		var _path="resource/ormresource";
		
		var validator = new Validator({
	    	element: '#ResourceADEForm',
	    	failSilently: true
		});
		
		validator.addItem({
	        element: 'input[name=\'resourceName\']',
	        display:"资源名称",
	        required: true,
	        rule: 'minlength{"min":-1}'
	    });
		
		sys.on("change",function(target){
			var checkObj = parent.getParentId();
			var parentResId = checkObj.parentResId;
			alert(parentResId);//////////////////////////////////////////////////////////////////////////////////
			var systemId = target.attr("data-value");
			if(!parentResId){
				$("input[name='parentResId']").val(systemId);
			}
	    });
		
	    function getSysList(){
	    	var system  = parent.getSelectSystem();
	    	var checkObj = parent.getParentId();
	    	var type = checkObj.type;
			var id = checkObj.systemId;
			//if(type == "system"){
			//	return system;
			//}
	    	if(sign == "edit"){
	    		for ( var int = 0; int < system.length; int++) {
					var sys = system[int];
					sys.disabled = true;
					sys.selected = false;
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
							sys.selected = false;
						}
					}
				}else{
					return system;
				}
	    	}
	    	return system;
	    };
	    
	    function getResTypeList(){
	    	var resource  = parent.getSelectResType();
	    	var checkObj = parent.getParentId();
	    	var resourceType = checkObj.resourceType;
	    	if(sign == "edit"){
	    		for ( var int = 0; int < resource.length; int++) {
					var res = resource[int];
					res.disabled = true;
					res.selected = false;
				}
	    	}else{
	    		if(resourceType){
					for ( var int = 0; int < resource.length; int++) {
						var res = resource[int];
						var typeId = res.value;
						if(parseInt(typeId)>parseInt(resourceType)){
							if(resourceType !== '400'){
								if(typeId == '600'){
									res.disabled = true;
									res.selected = false;
								}else{
									res.disabled = false;
									res.selected = true;
								}
							}else{
								res.disabled = false;
								res.selected = true;
							}
						}else{
							res.disabled = true;
							res.selected = false;
						}
					}
				}else{
					return resource;
				}
	    	}
	    	return resource;
	    };
		 
		var UserADEForm =new Form({
			trigger:"#ResourceADEForm",
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
				message = '请选择资源类型！';
				$(".msgText").html(message);
				return false;
			}
			if(arrvalue[0] =="600"||arrvalue[0] =="400"){
				var url = $("input[name='resourceUrl']").val();
				if(!url){
					$(".msgText").html("根据您选择的类型，请填写url属性！");
					return false;
				}
			}
			if(arrvalue[1]==""){
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
						UserADEForm.saveData({
							type:type,
							successFn :function(result){
								if(result){
									if(result == 'N'){
										showInformation(false);
										$(".msgText").text("同一层已存在其他类型的资源，请重新选择");
									}else{
										showInformation(true);
										$(".msgText").text(name+"成功");
										var arr = result.split(",");
										parent.tableRefresh($("input[name = parentResId]").val(),arr[0],$("input[name = resourceName]").val(),type,arr[1],arr[2]);
									}
								}
							},
							errorFn :function(result){
								if(result){
									
								}else{
									showInformation(false);
									$(".msgText").text(name+"失败");
								}
							},
					});
						return false;
					} 
	        	}else{
	        		showInformation(false);
	        	}
	        });
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
		if (sign == "detail" || sign == "edit") {
			$("a").unbind();
		};
		$("#btnCancel").click(function(){
			window.frameElement.trigger('close'); 
		});
		
});
