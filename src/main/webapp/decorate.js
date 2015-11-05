
define(function(require){
	var Menu=require("menu");
	var menu2 =new Menu({
		trigger:"#nav"
	});
	
	//菜单点击事件
	$(".url").on("click",function(){
		var url = $(this).attr("resUrl");
		window.location.href=url;
	});
});