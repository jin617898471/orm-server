define(function(require){
	var $ = require("$");
	Select = require("inno/select/1.0.0/select-debug");

	//新增下属机构之机构地址输入框宽度
	var itemsW = $(".subadd-items")[0].offsetWidth,
		labelW = $(".subadd-items-label")[0].offsetWidth,
		inputW = $(".subadd-items-inp")[0].offsetWidth,
		fullWidth = $(".inp-address").parent().width();

	$(".inp-address").css('width', fullWidth - labelW - (itemsW - labelW - inputW) - 12);

	if(sign!="detail"){
		$(".subadd-bottom").show();
	}
	var urlBasePath='./authority/code/';
	var url_cfg={
		"add":'add',
		"edit":'edit',
	}
	var isfalse=false;
	if($("#isRight").attr("value")=="N"){
		isfalse=true;
	}
		
	var select_disabled=true;
	if( "ROOT" == $("input[name=parentCodeId]").val() ){
		select_disabled = false;
	}
	new Select({
		name:"isRight",
		trigger:'#isRight',
		disabled:select_disabled,
		width:'220px',
		model:OrmJsObj.getCode( "IS_RIGHT", $("#isRight").attr("value") ) 
	}).render();
	
	function getSubmitData(){
		var obj={};
		var updateField=[];
		$("input").each(function(index, el) {
			var value = $(this).val();
			var name = $(this).attr("name");
			if(name){
				updateField.push(name);
				obj[name]=value;
			}
		});
		obj.updateField=updateField.join(",");
		return obj;
	}
	
	//保存
	$(".items-save").click(function(event) {
		var save=true;
		$(".subadd-items-required").each(function(index, el) {
			var text = $(this).find('.subadd-items-inp').val();
			if(text == ""){
				$(this).addClass('subadd-items-error');
				$(this).find('.items-error-text').show();
				save=false;
				return false;
			}
		});
		if(save){
			$.ajax( {
				url:urlBasePath+url_cfg[sign],
				data:getSubmitData(),
				type:"POST",
				error:function(){
					alert('删除失败,请联系管理员');
				},
				statusCode: { 
					200: function(msg) {
						parent.reloadGrid && parent.reloadGrid();
				  	}
				}
			});
		}
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