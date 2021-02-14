package com.klipwallet.sample.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonHelper {
    public static String toPrettyFormat(String jsonObj)
    {
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(jsonObj).getAsJsonObject();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = gson.toJson(json);

        return prettyJson;
    }
}
