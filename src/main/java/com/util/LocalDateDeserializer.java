package com.util;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

public class LocalDateDeserializer implements JsonDeserializer<LocalDate> {

	@Override
	public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) {
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy");
		return LocalDate.parse(json.getAsJsonPrimitive().getAsString(), formatter);
	}

}