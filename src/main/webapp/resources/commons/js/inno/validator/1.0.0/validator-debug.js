define("inno/validator/1.0.0/validator-debug",["$","validator"],function(require,exports,module){
	var $=require("$"),
		AraleValidator=require("validator");

	var Validator =AraleValidator.extend({
		attrs:{
				showMessage:function(message,element){
					message = '<i class="ui-tiptext-icon iconfont">&#xe67d;</i>\
                               <span class="ui-form-explain-text">' + message + '</span>';
                    this.getExplain(element)
                        .addClass('ui-tiptext ui-tiptext-error')
                        .html(message);
                    this.getItem(element).addClass(this.get('itemErrorClass'));
				},
				itemClass:"ui-form-item-col",
            itemHoverClass: "ui-form-item-hover",
            itemFocusClass: "ui-form-item-focus",
            itemErrorClass: "ui-form-item-error",
            inputClass: "ui-input",
            textareaClass: "ui-textarea",
            checkOnSubmit:false
		}
	});

	module.exports =Validator;
});