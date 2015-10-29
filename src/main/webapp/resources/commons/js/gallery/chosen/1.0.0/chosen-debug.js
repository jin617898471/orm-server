define("chosen",["$", "widget", "base", "class", "events"],function(require, exports, module){
	var $=require("$"),
		Widget = require("widget");

	var Chonse = Widget.extend({
		attrs:{
			triggers:{
				value:'',
				getter:function(val){
					return $(val);
				}
			},
			//点击选项插入的列表容器
			inquiryTriggers:{
				value:'',
				getter:function(val){
					return $(val);
				}
			},
			// 是否包含 triggers，用于没有传入 triggers 时，是否自动生成的判断标准
            hasTriggers: true,
            // 触发类型
            triggerType: "click",
            // or 'click'
            // 触发延迟
            delay: 100,
            // 初始切换到哪个面板
            activeIndex: {
                value: 0
            }
		},
		events:{
			'click .list li':''
		},
		setup:function(){

		},
		initHtml:function(){
			var data=this.get("model"),
				strHtml ="<ul class=\"cl list\">",
				strChild="<div class=\"child\">";

			for(var i in data){
   				if(data[i]["children"].length == 0 && (i==0) ){
   					strhtml +="<li class=\"first\" id=\""+  data[i]["id"] +"\" gValue=\""+ data[i]["value"] +"\"><a class=\"hover\">"+ data[i]["name"] +"</a></li>";
   				}else{
   					strhtml +="<li id=\""+  data[i]["id"] +"\" gValue=\""+ data[i]["value"] +"\"><a>"+ data[i]["name"] +"</a></li>";
   				}

   				if(data[i]["children"].length >0){
   					strChild +="<ul class=\"month cl list-child\" id=\"ul-"+ data[i]["id"] +"\" data-parent=\""+ data[i]["name"] +"\" style=\"display:none\">";

   					for (var j in data[i]["children"]) {
   						strChild +="<li id=\"" + data[i]["value"] + data[i]["children"][j]["value"] + "\" gValue=\""+ data[i]["value"] +"\"><a>"+ data[i]["children"][j]["name"] +"</a></li>"
   					}

   					strChild +="</ul>";
   				}
   			}
   			strChild+="</div>";
   			strhtml+="</ul>";

   			strhtml +=strChild;

   			this.element.html(strhtml);
		},
		addFilter:function(){
			
		}
		

	})
})