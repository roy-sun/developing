package com.arrowso.core.unit;

import java.util.ArrayList;
import java.util.Iterator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class InvokingHandle {

	public static Object[] parseParam(String paramStr,Class<?>[] paramTypes) throws Exception{
		ObjectMapper objectMapper = JsonUnit.getObjectMapper();
		if((paramStr==null||paramStr.trim().length()<1)){
			if(paramTypes.length==0)
				return null;
			else
				throw new Exception("Param count error");
		}
		JsonNode readTree = objectMapper.readTree(paramStr);
		Iterator<JsonNode> elements = readTree.elements();
		if(readTree.size()!=paramTypes.length)
			throw new Exception("Param count error");
		Object[] param = new Object[readTree.size()];
		int i=0;
		while(elements.hasNext()){
			JsonNode jsonNode = elements.next();
			Object readValue = objectMapper.readValue(jsonNode.toString(), paramTypes[i]);
			param[i++] = readValue;
		}
		return param;
	}
}
