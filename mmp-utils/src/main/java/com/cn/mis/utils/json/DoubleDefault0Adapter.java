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

public class DoubleDefault0Adapter implements JsonSerializer<Double>, JsonDeserializer<Double>{

	public Double deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
		try {
			   if (arg0.getAsString().equals("") || arg0.getAsString().equals("null")) {//定义为double类型,如果后台返回""或者null,则返回0.00
			    return 0.00;
			  }
			   } catch (Exception ignore) {
			  }
			  try {
			   return arg0.getAsDouble();
			  } catch (NumberFormatException e) {
			   throw new JsonSyntaxException(e);
			  }
	}

	public JsonElement serialize(Double arg0, Type arg1, JsonSerializationContext arg2) {
		// TODO Auto-generated method stub
		return new JsonPrimitive(arg0);
	}

}
