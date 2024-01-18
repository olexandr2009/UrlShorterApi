package com.url.shorter.config;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class Prefs {
    public static final String DEFAULT_PREFS_FILENAME = "/prefs.json";
    public static final String NAME_OF_RESOURCE = "nameOfSite";
    public static final String LINK_SIZE = "linkSize";
    private Map<String, Object> prefs = new HashMap<>();
    private InputStream inputStream;

    public Prefs(){
        this(Prefs.DEFAULT_PREFS_FILENAME);
    }
    public Prefs(String filename){
        try {
            this.inputStream = Prefs.class.getResourceAsStream(filename);
            String json = new String(inputStream.readAllBytes());

            TypeToken<?> typeToken = TypeToken.getParameterized(Map.class, String.class, Object.class);

            prefs = new Gson().fromJson(json, typeToken.getType());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getString(String key){
        return getPref(key).toString();
    }
    public Object getPref(String key){
        return prefs.get(key);
    }
}
