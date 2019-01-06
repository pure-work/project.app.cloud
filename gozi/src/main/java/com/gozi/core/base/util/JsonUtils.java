package com.gozi.core.base.util;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Utils - JSON
 * 
 * 
 * 
 */
public final class JsonUtils {

	/** ObjectMapper */
	private static ObjectMapper mapper = new ObjectMapper();
	private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);
	
	static{
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	/**
	 * 不可实例化
	 */
	private JsonUtils() {
	}

	/**
	 * 将对象转换为JSON字符串
	 * 
	 * @param value
	 *            对象
	 * @return JSOn字符串
	 */
	public static <T> String toJson(T value) {
		try {
			return mapper.writeValueAsString(value);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}
	
	
	/**
	 * 将json转换为List集合对象
	 * 
	 * @param <T>
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> toCollection(String json, Class<T> clazz)
			throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		List<T> objs = mapper.readValue(json,getCollectionType(List.class, clazz));
		return objs;
	}
	
	/**
	 * 将JSON字符串转换为对象
	 * 
	 * @param json
	 *            JSON字符串
	 * @param valueType
	 *            对象类型
	 * @return 对象
	 */
	public static <T> T toObject(String json, Class<T> valueType) {
		Assert.hasText(json);
		Assert.notNull(valueType);
		try {
			return mapper.readValue(json, valueType);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 将JSON字符串转换为对象
	 * 
	 * @param json
	 *            JSON字符串
	 * @param typeReference
	 *            对象类型
	 * @return 对象
	 */
	public static <T> T toObject(String json, TypeReference<?> typeReference) {
		Assert.hasText(json);
		Assert.notNull(typeReference);
		try {
			return mapper.readValue(json, typeReference);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 将JSON字符串转换为对象
	 * 
	 * @param json
	 *            JSON字符串
	 * @param javaType
	 *            对象类型
	 * @return 对象
	 */
	public static <T> T toObject(String json, JavaType javaType) {
		Assert.hasText(json);
		Assert.notNull(javaType);
		try {
			return mapper.readValue(json, javaType);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
		return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);   
	} 
	
	/**
	 * 将对象转换为JSON流
	 * 
	 * @param writer
	 *            writer
	 * @param value
	 *            对象
	 */
	public static void writeValue(Writer writer, Object value) {
		try {
			mapper.writeValue(writer, value);
		} catch (JsonGenerationException e) {
			logger.error(e.getMessage());
		} catch (JsonMappingException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	public static String map2Json(Map map) {
		// 转换null
		Iterator it = map.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			Object value = map.get(key);
			if (value == null) {
				map.put(key, "");
			}
		}
		
		JSONObject j = JSONObject.fromObject(map);
		return j.toString();
	}
	
	public static JSONObject map2JSONObject(Map map) {
		// 转换null
		Iterator it = map.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			Object value = map.get(key);
			if (value == null) {
				map.put(key, "");
			}
		}
		JSONObject j = JSONObject.fromObject(map);
		return j;
	}
    public static Map jsonToMap(String json){
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		Map map = null;
		try {
			map = mapper.readValue(json, Map.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Iterator it=map.keySet().iterator();
		while(it.hasNext()){
			String key = (String)it.next();
			Object value = map.get(key);
			if (value == null) {
				map.put(key, null);
			}
			if (value instanceof JSONNull) {
				map.put(key, null);
			}
		}
		return map;
	}
	
}