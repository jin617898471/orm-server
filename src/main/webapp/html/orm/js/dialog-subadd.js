define(function(require){
	var $ = require("$");

	//新增下属机构之机构地址输入框宽度
	var itemsW = $(".subadd-items")[0].offsetWidth,
		labelW = $(".subadd-items-label")[0].offsetWidth,
		inputW = $(".subadd-items-inp")[0].offsetWidth,
		fullWidth = $(".inp-address").parent().width();

	$(".inp-address").css('width', fullWidth - labelW - (itemsW - labelW - inputW) - 12);

	//保存
	$(".items-save").click(function(event) {

		$(".subadd-items-required").each(function(index, el) {

			var text = $(this).find('.subadd-items-inp').val();

			if(text == ""){
				$(this).addClass('subadd-items-error');
				$(this).find('.items-error-text').show();
			}
			
		});
		
	});

	$(".subadd-items-required .subadd-items-inp").focus(function(event) {

		var requireLi = $(this).parent();
		requireLi.removeClass('subadd-items-error');
		requireLi.find('.items-error-text').hide();

	});

	$(window).resize(function(event) {

		itemsW = $(".subadd-items")[0].offsetWidth,
		labelW = $(".subadd-items-label")[0].offsetWidth,
		inputW = $(".subadd-items-inp")[0].offsetWidth,
		fullWidth = $(".inp-address").parent().width();
		$(".inp-address").css('width', fullWidth - labelW - (itemsW - labelW - inputW) - 12);
		
	});

});