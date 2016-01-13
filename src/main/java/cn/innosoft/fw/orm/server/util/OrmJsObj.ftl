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
	OrmJsObj.selected = function(data,selected) {
		if(selected){
			for(var i in data){
				var obj=data[i];
				if(selected==obj.value){
					obj.selected=true;
				}
			}
		}
		return data;
	};
	OrmJsObj.system.getHasRight = function(selected,addAllSystem) {
		var data=OrmJsObj.copy(OrmJsObj.system.hasRight);
		if(selected){
			OrmJsObj.selected(data,selected);
		}
		if(addAllSystem){
			data.unshift({"value":"","text":"所有系统"})
		}
		return data;
	};
	OrmJsObj.system.getAll = function(selected,addAllSystem) {
		var data=OrmJsObj.copy(OrmJsObj.system.all);
		if(selected){
			OrmJsObj.selected(data,selected);
		}
		if(addAllSystem){
			data.unshift({"value":"","text":"所有系统"})
		}
		return data;
	};
	OrmJsObj.copy = function( list ) {
		var data=$.extend( true,[],list );
		return data;
	};
})(window)
