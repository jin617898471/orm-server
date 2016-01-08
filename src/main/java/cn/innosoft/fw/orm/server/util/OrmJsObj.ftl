(function(window){
	var OrmJsObj = window.OrmJsObj;
	if(null==OrmJsObj){
		OrmJsObj={};
		window.OrmJsObj = OrmJsObj;
	}
	OrmJsObj.system = {
		hasRight:[
					<#list all as sys>
						{"value":"${sys.systemId}","text":"${sys.systemName}"}<#if sys_has_next>,</#if>
					</#list>
		          ],
          	 all:[
					<#list all as sys>
						{"value":"${sys.systemId}","text":"${sys.systemName}"}<#if sys_has_next>,</#if>
					</#list>
	              ]
	};
	OrmJsObj.getHasRightSystem = function() {
		return this.copy(this.system.hasRight);
	};
	OrmJsObj.getAllSystem = function() {
		var data=this.copy( this.system.all );
		data.unshift({"value":"","text":"所有系统"});
		return data;
	};
	OrmJsObj.copy = function( list ) {
		var data=[];
		for(var index in list){
			var obj= list[index];
			var nobj={};
			for(var key in obj){
				nobj[key]=obj[key];
			}
			data.push(nobj);
		}
		return data;
	};
})(window)
