define("inno/echarts/0.0.1/echarts-debug",["$","echarts"],function(require,exports,module) {
	var $ = require("$");

	var Echarts =  function(options){
		this.trigger =  $(options.trigger);
		this.setup();
	}

	Echarts.prototype = {
		_echarts:null,
		setup:function(){
			this._echarts =  echarts.init(this.trigger[0]);
		},
		setOption:function(options){
			if(this._echarts == null){
				return;
			}
			this._echarts.setOption(options);
		}
	}

	module.exports=Echarts;
});