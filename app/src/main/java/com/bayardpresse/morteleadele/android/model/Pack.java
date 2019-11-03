package com.bayardpresse.morteleadele.android.model;

import androidx.annotation.NonNull;

public class Pack {
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