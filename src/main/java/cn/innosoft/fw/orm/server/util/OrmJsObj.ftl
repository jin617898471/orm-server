(function(window){
	var OrmJsObj = window.OrmJsObj;
	if(null==OrmJsObj){
		OrmJsObj={};
		window.OrmJsObj = OrmJsObj;
	}
	OrmJsObj.system = {
		hasRight:[
					<#list all as sys>
						{"systemId":"${sys.systemId}","systemName":"${sys.systemName}"}<#if sys_has_next>,</#if>
					</#list>
		          ],
		          all:[
					<#list all as sys>
						{"systemId":"${sys.systemId}","systemName":"${sys.systemName}"}<#if sys_has_next>,</#if>
					</#list>
		               ]
	};
	OrmJsObj.getHasRightSystem = function() {
		return this.system.hasRight;
	};
	OrmJsObj.getAllSystem = function() {
		var all = this.system.all;
		all.unshift({"systemId":"","systemName":"所有系统"});
		return all;
	};
})(window)
