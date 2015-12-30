package cn.innosoft.fw.orm.server.common.result;

import cn.innosoft.fw.orm.server.common.entity.InfoWrap;

public class Result {
	
	public static InfoWrap generateSuccessStatus(){
		return new InfoWrap("200");
	}
	public static InfoWrap generateFail(String status, String errorMessage){
		InfoWrap wrap = new InfoWrap(status);
		wrap.setMessage(errorMessage);
		return wrap;
	}
	public static InfoWrap generateSuccess(String message, Object obj){
		InfoWrap wrap = new InfoWrap("200");
		wrap.setMessage(message);
		wrap.setData(obj);
		return wrap;
	}
	public static InfoWrap generateSuccessWithoutData(String message){
		InfoWrap wrap = new InfoWrap("200");
		wrap.setMessage(message);
		return wrap;
	}
}
