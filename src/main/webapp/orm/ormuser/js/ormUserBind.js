define(function(require,exports,module){
	var Menu=require("menu"),
		Dialog=require("inno/dialog/1.0.0/dialog-debug"),
		Confirmbox=require("inno/dialog/1.0.0/confirmbox-debug"),
		Validator = require("inno/validator/1.0.0/validator-debug"),
		AutoComplete=require("arale/autocomplete/1.3.0/autocomplete-debug"),
		CodeProvider = require("../../../common/js/codeProvider");
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
		$("#btnSave").css("visibility","hidden");
	}else if(sign == "edit"){
		$(".userAcct").attr("disabled", true);
	}
	require("easyui");
	
	var _path="organization/ormuser";
	
	var validator = new Validator({
    	element: '#UserADEForm',
    	failSilently: true
	});
	
	CodeProvider.provider({"queryBeans":[{'codeSerial':'USER_SEX','dataType':'select'}]});
	
    /*.addItem({
        element: '[name=password-confirmation]',
        display:"密码",
        required: true,
        rule: 'confirmation{target: "#userAcctPwd"}'
    })*/;
	
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
		var orgv = $(".oserial").text().trim();
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
	
//	var type = new Select({
//    	trigger: '.userAcctType',
//    	width:'190px',
//    	name:'userAcctType',
//    	model: [
//        	{value:'NORMAL', text:'普通用户',selected:true},
//        	{value:'SYSTEM', text:'系统用户',selected:true},
//        	{value:'ADMIN', text:'管理员', selected: true},
//        	{value:'ROOT', text:'超级管理员', selected: true}
//    	]
//	}).render();
	
//	var sex = new Select({
//    	trigger: '.userSex',
//    	width:'178px',
//    	name:'userSex',
//    	model:CodeProvider.getCode("USER_SEX")
//	}).render();
	
	var UserADEForm =new Form({
		trigger:"#UserADEForm",
		addUrl :  _path+"/edit",
		setUrl : _path+"/edit"
	});
	
	
//	getOrgSelectTree = function(){
//		var data = "";
//		var parameter={
//				url:_path+'/selectTreeOrg',
//			    type:"POST",
//			    async:false,
//			    success:function(result){
//			    	data = result;
//			    },
//			    error:function(result){
//			    	Confirmbox.alert('查询失败！');
//			    }
//			};
//		$.ajax( parameter );
//		return data;
//	};
	
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
						name = "关联";
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
	 	        width: 178,
	 	        height:150,
	 	        classPrefix:"ui-select",
	 	        html:"<i style='color:red'> {{label}}</i> {{userAcct}}"
	 	    }).on('itemSelected', function(data, item){
		 	      initInput(data);
	 	    }).render();
	};
	addAotoEvent();
	
	var initInput = function(data){
		console.info(data);
		for(var i in data){
			var input = $("input[name="+i+"]");
			if(input.length>0){
				input.val(data[i]);
			}
		}
		var oldOrgId = $("input[name=oserial]").val();
		var newOrgId = $("input[name=orgId]").val();
		$("input[name=oserial]").val( oldOrgId+","+newOrgId );
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
