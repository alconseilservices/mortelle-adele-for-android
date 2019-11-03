package com.bayardpresse.morteleadele.android.model;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PackStore {

    private static List<Pack> PACKS = null;
    private static Map<String, Pack> MAP_PACKS = null;

    public static Pack getPackById(String id) {
        if(MAP_PACKS != null) {
            return MAP_PACKS.get(id);
        }
        return null;
    }

    public static List<Pack> fetchPacks() {
        if(PACKS == null) {
            synchronized (PackStore.class) {
                if (PACKS == null) {
                    try {
                        InputStream is = PackStore.class.getClassLoader().getResourceAsStream("res/raw/packs_json.json");
                        Writer writer = new StringWriter();
                        char[] buffer = new char[1024];
                        try {
                            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                            int n;
                            while ((n = reader.read(buffer)) != -1) {
                                writer.write(buffer, 0, n);
                            }
                        } finally {
                            is.close();
                        }
                        String packsString = writer.toString();
                        Gson gson = new Gson();
                        Pack[] packsArray =  gson.fromJson(packsString, Pack[].class);
                        MAP_PACKS = new HashMap<>();
                        for (Pack item : packsArray) {
                            MAP_PACKS.put(item.id, item);
                        }
                        return Arrays.asList(packsArray);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            }
        }
        return PACKS;

    }

}
