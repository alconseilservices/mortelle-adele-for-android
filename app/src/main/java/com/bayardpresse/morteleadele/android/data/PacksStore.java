package com.bayardpresse.morteleadele.android.data;

import android.content.res.Resources;

import androidx.annotation.NonNull;

import com.bayardpresse.morteleadele.android.R;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class PacksStore {

    /**
     * An array of pack stickers
     */
    public static List<Pack> PACKS = null;

    /**
     * A map of sample packs_json items, by ID.
     */
    public static final Map<String, Pack> ITEM_MAP = new HashMap<>();

    private static final int COUNT = 25;

    static {

        try {

            InputStream is = Resources.getSystem().openRawResource(R.raw.packs_json);
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
            PACKS = Arrays.asList(packsArray);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * pack of stickers
     */
    public static class Pack {
        public String id;
        public String name;
        public String size;
        public String iconName;
        public String dataFileName;
        public String[] stickersNames;

        public Pack(String id, String name, String size, String iconName, String dataFileName, String[] stickersNames) {
            this.id = id;
            this.name = name;
            this.size = size;
            this.iconName = iconName;
            this.dataFileName = dataFileName;
            this.stickersNames = stickersNames;
        }

        @NonNull
        @Override
        public String toString() {
            return name;
        }
    }
}
