package com.bayardpresse.morteleadele.android.model;

import android.content.Context;
import android.content.res.AssetManager;

import com.bayardpresse.morteleadele.android.ContentFileParser;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PackStore {

    private static final String CONTENT_FILE_NAME = "contents.json";

    private static List<StickerPack> PACKS = null;
    private static Map<String, StickerPack> MAP_PACKS = null;

    public static StickerPack getPackById(String id) {
        if(MAP_PACKS != null) {
            return MAP_PACKS.get(id);
        }
        return null;
    }

    public static List<StickerPack> fetchPacks(Context context) {
        if(PACKS == null) {
            synchronized (PackStore.class) {
                if (PACKS == null) {
                    try {
                        AssetManager assetManager = context.getAssets();
                        InputStream contentsInputStream = context.getAssets().open(CONTENT_FILE_NAME);
                        PACKS = ContentFileParser.parseStickerPacks(contentsInputStream);
                        MAP_PACKS = new HashMap<>();
                        for (StickerPack pack : PACKS) {
                            MAP_PACKS.put(pack.identifier, pack);
                            for (Sticker sticker : pack.getStickers()) {
                                sticker.size = assetManager.openFd(pack.identifier + "/"
                                        + sticker.imageFileName).getLength();
                            }
                            pack.updateSize();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return PACKS;

    }

}
