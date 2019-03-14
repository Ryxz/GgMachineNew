package utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 封装的是使用Gson解析json的方法
 */
public class JsonUtil {
	private JsonUtil() {
	}

	/**
	 * 把一个map变成json字符串
	 * @param map
	 * @return
	 */
	public static String parseMapToJson(Map<?, ?> map) {
		try {
			Gson gson = new Gson();
			return gson.toJson(map);
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 把一个json字符串变成对象
	 * @param json
	 * @param cls
	 * @return
	 */
	public static <T> T parseJsonToBean(String json, Class<T> cls) {
		Gson gson = new Gson();
		T t = null;
		try {
			t = gson.fromJson(json, cls);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	/**
	 * 把json字符串变成map
	 * @param json
	 * @return
	 */
	public static HashMap<String, Object> parseJsonToMap(String json) {
		Gson gson = new Gson();
		Type type = new TypeToken<HashMap<String, Object>>() {}.getType();
		HashMap<String, Object> map = null;
		try {
			map = gson.fromJson(json, type);
		} catch (Exception e) {
		}
		return map;
	}

	/**
	 * 把json字符串变成集合
	 * @return
	 */
	public static <T> List<T> parseJsonToList(String json) {
		Gson gson = new Gson();
		Type type = new TypeToken<List<T>>() {}.getType();
		List<T> list = gson.fromJson(json, type);
		return list;
	}
	public static <T> List<T> jsonToDto(String json ,Class<T> cls){ //这里是Class<T>
//		JsonObject jsonObject = new JsonParser().parse(message).getAsJsonObject();
//		JsonArray jsonArray = jsonObject.getAsJsonArray(jsonHead);

		JsonArray array=new JsonParser().parse(json).getAsJsonArray();
		Gson gson = new Gson();
		List<T> list = new ArrayList<>();
		for (Object object : array) {
			list.add(gson.fromJson((JsonObject) object,cls)); //cls
		}
		return list;
	}

	/**
	 * 
	 * 获取json串中某个String字段的值，注意，只能获取同一层级的value
	 * @param json
	 * @param key
	 * @return
	 */
	public static String getStringFieldValue(String json, String key) {
		if (TextUtils.isEmpty(json))
			return null;
		if (!json.contains(key))
			return "";
		JSONObject jsonObject = null;
		String value = null;
		try {
			jsonObject = new JSONObject(json);
			value = jsonObject.getString(key);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return value;
	}



	/**
	 *
	 * 获取json串中某个int字段的值，注意，只能获取同一层级的value，注意-100为初始值，代表并未成功
	 * @param json
	 * @param key
	 * @return
	 */
	public static int getIntFieldValue(String json, String key) {
		if (TextUtils.isEmpty(json))
			return -100;
		if (!json.contains(key))
			return -100;
		JSONObject jsonObject = null;
		int value = -100;
		try {
			jsonObject = new JSONObject(json);
			value = jsonObject.getInt(key);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return value;
	}

}
