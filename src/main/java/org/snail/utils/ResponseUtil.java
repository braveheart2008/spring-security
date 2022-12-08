package org.snail.utils;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ResponseUtil {
	
	public static void out(HttpServletResponse response, R r){
		ObjectMapper om = new ObjectMapper();
		response.setStatus(HttpStatus.OK.value());
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		
		try {
			om.writeValue(response.getWriter(), r);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
