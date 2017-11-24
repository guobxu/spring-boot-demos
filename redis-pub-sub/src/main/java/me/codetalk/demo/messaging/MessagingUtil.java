package me.codetalk.demo.messaging;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class MessagingUtil {

	private static ObjectMapper MAPPER = new ObjectMapper();
	
	public static MesgObj convertAsMesgObj(String mesgType, Object obj) {
		Map<String, Object> data = toMap(obj);
		
		return convertAsMesgObj(mesgType, data);
	}
	
	public static MesgObj convertAsMesgObj(String mesgType, Map<String, Object> data) {
		return _convertAsMesgObj(mesgType, data);
	}

	public static MesgObj convertAsMesgObj(String mesgType, List<Map<String, Object>> data) {
		return _convertAsMesgObj(mesgType, data);
	}
	
	private static MesgObj _convertAsMesgObj(String mesgType, Object data) {
		MesgObj mo = new MesgObj();
		mo.setId(UUID.randomUUID().toString());
		mo.setType(mesgType);
		mo.setData(data);
		mo.setCreateDate(System.currentTimeMillis());
		
		return mo;
	}
	
	public static Map<String, Object> toMap(Object obj) {
		Map<String, Object> map = MAPPER.convertValue(obj, new TypeReference<Map<String, Object>>() {});
		
		return map;
	}	
	
}
