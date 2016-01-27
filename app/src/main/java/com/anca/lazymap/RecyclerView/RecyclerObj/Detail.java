package com.anca.lazymap.RecyclerView.RecyclerObj;

import android.support.annotation.Nullable;

/**
 * Created by MinhDuc on 26/01/2016.
 */
public class Detail {

    private int icon;
    private String detail;

    public Detail(int icon, String detail) {
        this.icon = icon;
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }

    public int getIcon() {
        return icon;
    }
}
