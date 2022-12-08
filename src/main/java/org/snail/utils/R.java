package org.snail.utils;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class R {
	
	private Boolean success;
	private Integer code;
	private String message;
	
	private Map<String, Object> data = new HashMap<>();
	
	private R(){}
	
	public static R ok(){
		R r = new R();
		
		r.setSuccess(true);
		r.setCode(20000);
		r.setMessage("success");
		
		return r;
	}
	
	public static R error(){
		R r = new R();
		
		r.setSuccess(false);
		r.setCode(20001);
		r.setMessage("fail");
		
		return r;
	}

}
