package com.klipwallet.app2app.network;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class Utility {
    public static Map<String, Object> convertObjToMap(Object obj, Class type) {
        Map<String, Object> map = new HashMap<String,Object>();

        try {
            Field[] allFields = type.getDeclaredFields();
            for (Field field : allFields) {
                field.setAccessible(true);
                Object value = field.get(obj);
                map.put(field.getName(), value);
            }
        } catch(IllegalAccessException ignore) {

        }

        return map;
    }

    public static String getQueryString(final Map<String, Object> params, String charset) throws UnsupportedEncodingException {
        final StringBuilder result = new StringBuilder();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (result.length() > 0) {
                result.append("&");
            }
            result.append(URLEncoder.encode(entry.getKey(), charset));
            result.append("=");
            if (entry.getValue() instanceof String)
                result.append(URLEncoder.encode(entry.getValue().toString(), charset));
            else if (entry.getValue() instanceof Integer)
                result.append(entry.getValue());
            else {
                // ignore
            }
        }

        return result.toString();
    }
}
