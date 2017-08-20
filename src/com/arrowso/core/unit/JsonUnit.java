package com.arrowso.core.unit;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUnit {

	private static ObjectMapper objectMapper = new ObjectMapper();
	
	static{
		//config objectMapper
		//...
		
	}
	
	public static ObjectMapper getObjectMapper(){
		return objectMapper;
	}
}
