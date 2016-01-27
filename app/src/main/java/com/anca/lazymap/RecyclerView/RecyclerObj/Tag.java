package com.anca.lazymap.RecyclerView.RecyclerObj;

import java.util.List;

/**
 * Created by MinhDuc on 26/01/2016.
 */
public class Tag {
    private int icon;
    private List<String> types;

    public Tag(int icon, List<String> types) {
        this.icon = icon;
        this.types = types;
    }

    public List<String> getTypes() {
        return types;
    }

    public int getIcon() {
        return icon;
    }
}
