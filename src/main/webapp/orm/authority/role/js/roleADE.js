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
	var urlBasePath='./authority/role/';
	var url_cfg={
		"add":'add',
		"edit":'edit',
	}
	
	new Select({
		name:"systemId",
		trigger:'#systemId',
		width:'220px',
		model:OrmJsObj.system.getHasRight( $("#systemId").attr("value") )
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
		showstatu(true,"保存中...");
		if(save){
			$.ajax( {
				url:urlBasePath+url_cfg[sign],
				data:getSubmitData(),
				type:"POST",
				error:function(){
					alert('操作失败,请联系管理员');
				},
				statusCode: { 
					200: function(msg) {
						parent.reloadGrid && parent.reloadGrid();
						showstatu(true);
				  	},
					210: function(msg) {
						showstatu(false,msg.message);
					}
				}
			});
		}
	});
	$(".items-cacel").click(function(event) {
		window.frameElement.trigger('close'); 
	});
	function showstatu(res,msg){
		$(".subadd-bottom-status").hide();
		if(res){
			msg = msg || "保存成功!";
			$(".bottom-status-success").find("span").html(msg);
			$(".bottom-status-success").show();
		}else{
			msg = msg || "保存失败!";
			$(".bottom-status-error").find("span").html(msg);
			$(".bottom-status-error").show();
		}
	}

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