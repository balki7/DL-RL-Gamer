package com.balki.gamer.util;

import com.google.gson.Gson;

/**
 * 
 * @author Balki
 * @since 21/12/2018
 *
 */
public final class JSONManager {
	public static final String toJson(Object o) {
		Gson gson = new Gson();
		return gson.toJson(o);
	}
	
	public static final <T> T fromJson(Class<T> clazz, String json) {
		Gson gson = new Gson();
		return gson.fromJson(json, clazz);
	}
}
