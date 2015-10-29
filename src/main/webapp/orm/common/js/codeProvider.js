window.GLOBAL_CODE = {};
define(["$"],function(require, exports, module){
	var $=require("$"),
	Confirmbox=require("inno/dialog/1.0.0/confirmbox-debug");
	var code = {
		provider : function(jsonData) {
			var parameterS = {
				url : 'code/provider/queryCode',
				type : "POST",
				dataType : "json",
				contentType : "application/json",
				data : JSON.stringify(jsonData),
				async : false,
				success : function(data) {
					window.GLOBAL_CODE = data;
				},
				error : function(result) {
					Confirmbox.alert('获取代码数据出错.');
				}
			};
			$.ajax(parameterS);
		},
		
		translate : function(codeSerial,value) {
			var _codedata = GLOBAL_CODE[codeSerial];
			if(_codedata) {
				for (var i = 0; i < _codedata.length; i++) {
					if(_codedata[i] && _codedata[i]['value'] == value) {
						return _codedata[i]['text'];
					}
				}
			}
			return value;
		},
		
		getCode : function(codeSerial,value) {
			return GLOBAL_CODE[codeSerial];
		}
	};
	
	module.exports = code;
});