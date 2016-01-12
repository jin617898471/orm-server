define(function(require){
	var $ = require("$");
	Form = require("form");


	$(".subadd-bottom-status").hide();
	//新增下属机构之机构地址输入框宽度
	var itemsW = $(".subadd-items")[0].offsetWidth,
		labelW = $(".subadd-items-label")[0].offsetWidth,
		inputW = $(".subadd-items-inp")[0].offsetWidth,
		fullWidth = $(".inp-address").parent().width();

	$(".inp-address").css('width', fullWidth - labelW - (itemsW - labelW - inputW) - 12);
	
	var sign = $("input[name=sign]").val();
	var basePath = "/orm-server/";
	var urlcfg= {
		addOrg : basePath + "org/add",
		updateInst : basePath + "org/update"
	}
	var instForm = new Form({
		trigger: "#orgForm",
		addUrl: urlcfg.addOrg,
		setUrl: urlcfg.updateInst
	});
	
	//保存
	$(".items-save").click(function(event) {
		var flag = true;
		$(".subadd-items-required").each(function(index, el) {

			var text = $(this).find('.subadd-items-inp').val();

			if(text == ""){
				flag = false;
				$(this).addClass('subadd-items-error');
				$(this).find('.items-error-text').show();
			}
			
		});
		if(flag){
			var type = sign == 'add' ? 1 : 2;
			instForm.saveData({
				type: type,
				successFn: function(result){
					if(result.status == 200){
						$(".subadd-bottom-status span").text(result.message);
						$(".subadd-bottom-status").show();
						if(type == 1){
							parent.refreshTree("P");
						}
					}else{
						$(".subadd-bottom-status span").text(result.message);
						$(".subadd-bottom-status").show();
					}
				},
				errorFn: function(){
					console.log(result)
				}
			});
		}
	});

	$(".subadd-items-required .subadd-items-inp").focus(function(event) {

		var requireLi = $(this).parent();
		requireLi.removeClass('subadd-items-error');
		requireLi.find('.items-error-text').hide();

	});
	$("#btnCancel").click(function(){
		window.frameElement.trigger('close'); 
	});

	$(window).resize(function(event) {

		itemsW = $(".subadd-items")[0].offsetWidth,
		labelW = $(".subadd-items-label")[0].offsetWidth,
		inputW = $(".subadd-items-inp")[0].offsetWidth,
		fullWidth = $(".inp-address").parent().width();
		$(".inp-address").css('width', fullWidth - labelW - (itemsW - labelW - inputW) - 12);
		
	});

});