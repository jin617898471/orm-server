define(function(require,exports,module){
	var Menu=require("menu"),
		Dialog=require("inno/dialog/1.0.0/dialog-debug"),
		Select = require("inno/select/1.0.0/select-debug"),
		Confirmbox=require("inno/dialog/1.0.0/confirmbox-debug"),
		Validator = require("inno/validator/1.0.0/validator-debug"),
		Calendar = require("inno/calendar/1.0.0/calendar-debug"),
		AutoComplete=require("arale/autocomplete/1.3.0/autocomplete-debug"),
		CodeProvider = require("../../../common/js/codeProvider");
		SelectTree=require("inno/select-tree/1.0.0/select-tree-debug");
		Form = require("form"),
		$=require("$");
	
	//判断标识：标识为新增或者查看或者编辑
	var sign = $("#sign").val();
	
	var sexType = $("#userSex").val();
	
	var oserial = $("#oserial").val();
	
	//如果是新增，同时url中传入了默认的岗位id，则给oserial赋值
	if( !oserial && getQueryString("orgid") ){
		oserial = getQueryString("orgid");
	}
	
	if(sign == "detail"){
		$(":text").each(function() {
			this.disabled = true;
		});
		$(":password").each(function() {
			this.disabled = true;
		});
		$(".ui-textarea").attr("disabled", true);
		$(".ui-textarea-border").addClass("ui-textarea-disable");
		$("#btnSave").css("visibility","hidden");
	}else if(sign == "edit"){
		$(".userAcct").attr("disabled", true);
	}
	require("easyui");
	
	var _path="user";
	
	var validator = new Validator({
    	element: '#UserADEForm',
    	failSilently: true
	});
	
//	CodeProvider.provider({"queryBeans":[{'codeSerial':'USER_SEX','dataType':'select'}]});
	
	validator.addItem({
        element: 'input[name=\'userEmail\']',
        display:"邮箱",
        required: false,
        rule: 'email'
    }).addItem({
        element: 'input[name=\'userMobile\']',
        display:"手机号码",
        required: false,
        rule: 'mobile'
    })
    .addItem({
        element: 'input[name=\'userAcctPwd\']',
        display:"密码",
        required: true,
        rule: 'minlength{"min":5} maxlength{"max":20}',
    })
    .addItem({
        element: 'input[name=\'userAcct\']',
        display:"账号",
        required: true,
        rule: 'minlength{"min":-1}',
    }).addItem({
        element: 'input[name=\'userAcctCn\']',
        display:"用户名称",
        required: true,
        rule: 'minlength{"min":-1}',
    })
    .addItem({
        element: '[name=password-confirmation]',
        display:"密码",
        required: true,
        rule: 'confirmation{target: "#userAcctPwd"}'
    });
	
	// 验证
	
	$("input[name=userTel]").blur(
			function() {
				var isPhone=/^((0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/;
				if(!isPhone.test($("input[name=userTel]").val())){
					message = '电话格式不正确！';
					$(".msgText").html(message);
					$(".iconfonthide").hide();
					$(".iconfonthide2").show();
					return false;
		        }
				$(".iconfonthide").show();
				$(".msgText").html("");
				return true;
			}
		);
	
	var message = '';
	checkAllSelect = function() {
		var orgv = $.trim($(".oserial").text());
		if (orgv.length == 2) {
			message = '请选择组织机构！';
			$(".msgText").html(message);
			$(".iconfonthide").hide();
			$(".iconfonthide2").show();
			return false;
		}
		$(".iconfonthide").show();
		$(".msgText").html("");
		return true;
	};
	
	var type = new Select({
    	trigger: '.userAcctType',
    	width:'190px',
    	name:'userAcctType',
    	model: [
        	{value:'NORMAL', text:'普通用户',selected:true},
        	{value:'SYSTEM', text:'系统用户',selected:true},
        	{value:'ADMIN', text:'管理员', selected: true},
        	{value:'ROOT', text:'超级管理员', selected: true}
    	]
	}).render();
	
	var sex = new Select({
    	trigger: '.userSex',
    	width:'178px',
    	name:'userSex',
    	model:[{value:'UNKNOWN',text:'未知'},{value:'MALE',text:'男'},{value:'FEMALE',text:'女'}]
	}).render();
	
	new Calendar({
		trigger: 'input[name=\'userBirth\']'
	});
	
	var UserADEForm =new Form({
		trigger:"#UserADEForm",
		addUrl :  _path+"/add",
		setUrl : _path+"/edit"
	});
	
	sex.selectValue(sexType);
	
	//禁止输入
	$("input[name = 'userBirth']").keydown(function(){
	   return false;
	});
	
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
        trigger: '.oserial',
        width: '360px',
        maxHeight:'300px',
        name: 'oserial',
        cascade:false,
        checkSelect:function(target,mult){
            var value=$(target).attr("data-disabled");
            if(value){
            	var otype = $(target).attr("data-otype");
                if(otype == 'P'){
                	return true;
                }else{
                	alert("您勾选的节点必须是岗位！");
                }
            }else{
            	alert("您勾选的节点不在您所在的权限范围内!");
            }
            return false;
        },
        model: getOrgSelectTree(),
    }).render();
	
	one.selectValue(oserial);
	
	validator.element.on("submit", function(e) {
		e.preventDefault();
        validator.execute(function(err) {
        	if (checkAllSelect()) {
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
							if(result == 'true'){
								showInformation(true);
								$(".iconfonthide").show();
								$(".msgText").text(name+"成功");
								//如果是通过组织机构建模页面打开该页面，保存成功后需调用组织机构建模页面中的刷新方法
								if(parent.reflashFun){
									parent.reflashFun();
								}else{
									parent.resetutilsearchlist();
								}
							}else{
								showInformation(false);
								$(".msgText").text("账号重复！请重新输入");
								$(".iconfonthide").hide();
							}
						},
						errorFn :function(result){
							showInformation(false);
							$(".msgText").text(name+"失败");
							$(".iconfonthide").hide();
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
	
	$("#btnCancel").click(function(){
		window.frameElement.trigger('close'); 
	});
	
	//新增联想搜索事件
	var addAotoEvent = function (){
		var uid = $("input[name=userId]").val();
		if(uid){//若果是编辑或查看则没有联想搜索
			return ;
		}
		var complete =  new AutoComplete({
	 	        trigger: ".userAcctCn",
	 	        dataSource: _path+'/getuserlist?userAcctCn={{query}}',
	 	        width: 150
	 	    }).on('itemSelected', function(data, item){
	 	    	console.info(data);
		 	      initInput(data);
	 	    }).render();
	};
	addAotoEvent();
	
	var initInput = function(data){
		for(var i in data){
			var input = $("input[name="+i+"]");
			if(input.length>0){
				input.val(data[i]);
			}
		}
		$("input[name=password-confirmation]").val( data.userAcctPwd );
		$("textarea[name=userAcctDesc]").html( data.userAcctDesc );
		sex.selectValue( data.userSex);
		one.selectValue( data.oserial );
	};
	
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
	
	//获取url中参数的值
	function getQueryString(name) {
	    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	    var r = window.location.search.substr(1).match(reg);
	    if (r != null) return unescape(r[2]); return null;
    }
	if (sign == "detail") {
		$("a").unbind();
		$(".oserial").unbind();
	};
});
