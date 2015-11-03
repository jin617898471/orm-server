/*!
 * ORM JavaScript Object 1.0.0
 * system、getSystem只对orm-server可见
 */
var OrmJsObj = {
	code : {
	   xb : [ 
	          {value : "0",text : "男"} , 
		      {value : "1",text : "女"}],
	   xzqh : [ 
            {value : "330100",text : "杭州"} , 
		    {value : "330200",text : "宁波"}]
	},
	org : [{value :"0001",text : "杭州久拓软件有限公司",attrs:{otype : "I"} , child : [
                                                                                       {value:"0002",text:"交通部",attrs:{otype : "O"},child : [
                              	                                                                                           {value : "0003",text : "产品经理",attrs:{otype : "P"}},
                              	                                                                                           {value : "0004",text : "java开发工程师",attrs:{otype : "P"}}]},
                                                                                       {value:"0005",text:"气候部",attrs:{otype : "O"},child : [
                                            	                                                                                           {value : "0006",text : "产品经理",attrs:{otype : "P"}},
                                            	                                                                                           {value : "0007",text : "java开发工程师",attrs:{otype : "P"}}]}]
		}],
	system : [ {value : "0001",text : "权限管理系统" , attrs : {code : "ORM"} } , 
	           {value : "0002",text : "项目管理系统" , attrs : {code : "PMS"} }],
    resource : [ "RoleAdd" ,"UserAdd" ],
	getCode : function( codeIndexValue ) {
		return this.code[ codeIndexValue ];
	},
	getCodeText : function( codeIndexValue , codeValue ){
		var code = this.getCode();
		var getText = function( list , value ){
			var hasItem = list && list.length>0 ;
			if( !hasItem ){
				return null;
			}
			for( var item in list ){
				if( value == item["value"] ){
					return item["text"];
				}else{
					var text =  getText( item["child"]  );
					if( text ){
						return text ;
					}
				}
			}
		};
		return getText( code );
	},
	getOrg : function() {
		return this.org;
	},
	getSystem : function() {
		return this.system;
	},
	resourceInit : function( $,docuemnt ){
		var resources = this.resource;
		var isHasRight = function( code ){
			var index = $.inArray( code,resources );
			if(index<0){
				return false;
			}else{
				return true;
			}
		}
		$("*[resource-code]",docuemnt).hide().each(function(){
			var code = $(this).attr("resource-code");
			var right = isHasRight(code);
			if( right ){
				$(this).show();
			}
		});
	}
};
