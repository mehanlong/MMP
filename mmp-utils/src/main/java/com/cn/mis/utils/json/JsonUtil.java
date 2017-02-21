package com.cn.mis.utils.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;
public class JsonUtil {
	public static <T> T fromJson(Class<T> tclass, String json) {
		if (StringUtils.isEmpty(json) == false) {
			Gson gson = new GsonBuilder()
				    .registerTypeAdapter(Integer.class, new IntegerDefault0Adapter())
				    .registerTypeAdapter(int.class, new IntegerDefault0Adapter())
				    .registerTypeAdapter(Double.class, new DoubleDefault0Adapter())
				    .registerTypeAdapter(double.class, new DoubleDefault0Adapter())
				    .registerTypeAdapter(Float.class, new FloatDefault0Adapter())
				    .registerTypeAdapter(float.class, new FloatDefault0Adapter())
				    .create();
			return (T) gson.fromJson(json, tclass);
		}
		return null;
	}
	
	public static Object fromJson(Type t,String json){
		
		if (StringUtils.isEmpty(json) == false) {
			Gson gson = new GsonBuilder()
				    .registerTypeAdapter(Integer.class, new IntegerDefault0Adapter())
				    .registerTypeAdapter(int.class, new IntegerDefault0Adapter())
				    .registerTypeAdapter(Double.class, new DoubleDefault0Adapter())
				    .registerTypeAdapter(double.class, new DoubleDefault0Adapter())
				    .registerTypeAdapter(Float.class, new FloatDefault0Adapter())
				    .registerTypeAdapter(float.class, new FloatDefault0Adapter())
				    .create();
			return gson.fromJson(json,t);
		}
		return null;
	}
	
	public static <T> String toJson(T t){
		Gson gson = new GsonBuilder()
			    .registerTypeAdapter(Integer.class, new IntegerDefault0Adapter())
			    .registerTypeAdapter(int.class, new IntegerDefault0Adapter())
			    .registerTypeAdapter(Double.class, new DoubleDefault0Adapter())
			    .registerTypeAdapter(double.class, new DoubleDefault0Adapter())
			    .registerTypeAdapter(Float.class, new FloatDefault0Adapter())
			    .registerTypeAdapter(float.class, new FloatDefault0Adapter())
			    .create();
		return gson.toJson(t);
	}
}
