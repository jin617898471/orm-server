define("inno/scroll/0.0.1/scroll-debug",["$","./mousewheel-debug"],function (require, exports, module) {
	var $ = require("$");

    function Scroll(options) {
    	this.init(options);  
    }

    Scroll.prototype = {
    	constructor:Scroll,
    	init:function(options){
    		var set = _extend({
    			trigger:"",
    			container:"",
    			speed:40,
                scrollbarFlag:true
    		},options||{});
    		this.trigger = $(set.trigger); 
    		this.container = $(set.container);
    		this.speed = set.speed;
            this.scrollbarFlag = set.scrollbarFlag;
    		this.triggerHeight = this.trigger.height();
    		this.containerHeight = this.container.height();
    		this._calculate(); 
    		this._addWarpper();
    		this._bindEvents();
            if(this.scrollbarFlag){
                this.show();
            }else{
                this.hide();
            }
    	},
    	_calculate:function(){
    		var triggerHeight = this.triggerHeight,
    			containerHeight = this.containerHeight;

    		if(triggerHeight < containerHeight){
    			this.maxTop = containerHeight - triggerHeight;
    		}else{
    			this.maxTop = 0;
    		}
    	},
    	_addWarpper:function(){
    		if(this.wrapper){
    			return;
    		}
    		var vScrollHeight = parseInt(this.triggerHeight / this.containerHeight * this.triggerHeight);

    		this.wrapper = _createEl("<div class='ui-scroll-bar' style='height:"+ this.triggerHeight  +"px'></div>",this.trigger[0]);
    		this.vScroll = _createEl("<div class='ui-scroll-bar-y' style='height:"+ vScrollHeight  +"px'></div>",this.wrapper[0]);
    	},
        show:function(){
            this.scrollbarFlag = true;
            this.wrapper.show();
        },
        hide:function(){
            this.scrollbarFlag = false;
            this.wrapper.hide();
        },
    	_bindEvents:function(){
    		var that = this;
    		this.trigger.bind("mousewheel",function(event, delta, deltaX, deltaY){
    			that._mousewheel(delta);
    		});
    		this.trigger.bind("mouseover",function(){
    			$(that.wrapper).addClass("in-scrolling");
    		});    	
    		this.trigger.bind("mouseout",function(){
    			$(that.wrapper).removeClass("in-scrolling");
    		});
    		this.vScroll.bind("mousedown",function(e){
    			that.flag = true;
    		});
    		this.vScroll.bind("mousemove",function(e){
    			console.log(that.flag);
    			if(!(that.flag)){
    				return;
    			}
    			console.log(e);
    		});
    		this.vScroll.bind("mousedown",function(e){
    			that.flag = false;
    		});
    	},
    	_mousedown:function(){

    	},
    	_mousewheel:function(delta){
    		var top,barTop;
    		var containerTop = parseInt(this.container.css("top")),
    			triggerHeight = this.triggerHeight,
    			offsetHeight = this.container.get(0).offsetHeight,
    			wrapperHeight = this.wrapper.height(),
    			speed =this.speed ,
    			containerHeight = this.containerHeight; //  container  offsetHeight

    		if(delta>0){
    		 	top = containerTop + Math.round(triggerHeight * (speed  / offsetHeight)) + 'px';
    		 	if(parseInt(top)>0){
    		 		top=0;
    		 	}

    		}else{
    		 	top = containerTop - Math.round(triggerHeight * (speed / offsetHeight)) + 'px';
    		 	if(-parseInt(top) > this.maxTop){
    		 		top = - (this.maxTop);
    		 	}
    		 	
    		}
    		barTop = -(parseInt(top)/containerHeight) * parseInt(wrapperHeight);
    		this.container.css("top",top);
    		this.vScroll.css("top",barTop);

    	},
    	_setPos:function(){

    	},
    	reset:function(){
    		this.triggerHeight = this.trigger.height();
    		this.containerHeight = this.container.height();

    		var triggerHeight = this.triggerHeight,
    			containerHeight = this.containerHeight;

    		if(triggerHeight < containerHeight){
    			this.maxTop = containerHeight - triggerHeight;
    		}else{
    			this.maxTop = 0;
    		}
    		var vScrollHeight = parseInt(triggerHeight / containerHeight * triggerHeight);
    		this.vScroll.css("height", vScrollHeight + "px");
    	}
    }
    function _extend(target,source){
        for(var key in source) target[key] = source[key];
        return target;
    };
    function _createEl(html,parent){
        var div = document.createElement('div');
        div.innerHTML = html;
        el = div.firstChild;
        parent && parent.appendChild(el);
        return $(el);
    };

    module.exports = Scroll;
});
 