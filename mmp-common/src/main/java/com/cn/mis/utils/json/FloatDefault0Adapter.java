package com.cn.mis.utils.json;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;

public class FloatDefault0Adapter implements JsonSerializer<Float>, JsonDeserializer<Float>{

	public Float deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
		try {
			   if (arg0.getAsString().equals("") || arg0.getAsString().equals("null")) {//定义为float类型,如果后台返回""或者null,则返回0.0
			    return (float) 0.0;
			  }
			   } catch (Exception ignore) {
			  }
			  try {
			   return arg0.getAsFloat();
			  } catch (NumberFormatException e) {
			   throw new JsonSyntaxException(e);
			  }
	}

	public JsonElement serialize(Float arg0, Type arg1, JsonSerializationContext arg2) {
		// TODO Auto-generated method stub
		return new JsonPrimitive(arg0);
	}

}

