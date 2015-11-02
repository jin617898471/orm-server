define(function(require, exports, module) {
	var Class = require('arale/class/1.1.0/class');
	var Map = Class.create({
		initialize: function() {
            this.elements = new Array();
        },
        attrs: {
        	elements: new Array()
        },
		size : function() {
			return this.elements.length;
		},
		isEmpty : function() {// 判断Map是否为空
			return (this.elements.length < 1);
		},
		clear : function() {// 删除Map所有元素
			this.elements = new Array();
		},
		put : function(_key, _value) {// 向Map中增加元素（key, value)
			if (this.containsKey(_key) == true) {
				if (this.containsValue(_value)) {
					if (this.remove(_key) == true) {
						this.elements.push({
							key : _key,
							value : _value
						});
					}
				} else {
					this.elements.push({
						key : _key,
						value : _value
					});
				}
			} else {
				this.elements.push({
					key : _key,
					value : _value
				});
			}
		},
		remove : function(_key) {// 删除指定key的元素，成功返回true，失败返回false
			var bln = false;
			try {
				for ( var i = 0; i < this.elements.length; i++) {
					if (this.elements[i].key == _key) {
						this.elements.splice(i, 1);
						return true;
					}
				}
			} catch (e) {
				bln = false;
			}
			return bln;
		},
		get : function(_key) {// 获取指定key的元素值value，失败返回null
			try {
				for ( var i = 0; i < this.elements.length; i++) {
					if (this.elements[i].key == _key) {
						return this.elements[i].value;
					}
				}
			} catch (e) {
				return null;
			}
		},
		element : function(_index) {// 获取指定索引的元素（使用element.key，element.value获取key和value），失败返回null
			if (_index < 0 || _index >= this.elements.length) {
				return null;
			}
			return this.elements[_index];
		},
		containsKey : function(_key) {// 判断Map中是否含有指定key的元素
			var bln = false;
			try {
				for ( var i = 0; i < this.elements.length; i++) {
					if (this.elements[i].key == _key) {
						bln = true;
					}
				}
			} catch (e) {
				bln = false;
			}
			return bln;
		},
		containsValue : function(_value) {// 判断Map中是否含有指定value的元素
			var bln = false;
			try {
				for ( var i = 0; i < this.elements.length; i++) {
					if (this.elements[i].value == _value) {
						bln = true;
					}
				}
			} catch (e) {
				bln = false;
			}
			return bln;
		},
		keys : function() {// 获取Map中所有key的数组（array）
			var arr = new Array();
			for ( var i = 0; i < this.elements.length; i++) {
				arr.push(this.elements[i].key);
			}
			return arr;
		},
		values : function() {// 获取Map中所有value的数组（array）
			var arr = new Array();
			for ( var i = 0; i < this.elements.length; i++) {
				arr.push(this.elements[i].value);
			}
			return arr;
		},
		joinKeys : function(_separator){//按指定的分隔符连接keys
			var arr = this.keys();
			var str = '';
			for(var i = 0; i < arr.length; i++){
				str += arr[i] + _separator;
			}
			return str;
		},
		joinValues : function(_separator){//按指定的分隔符连接values
			var arr = this.values();
			var str = '';
			for(var i = 0; i < arr.length; i++){
				str += arr[i] + _separator;
			}
			return str;
		}
	});
	
	module.exports = Map;
});