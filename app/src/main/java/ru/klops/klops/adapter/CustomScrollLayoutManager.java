package ru.klops.klops.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

public class CustomScrollLayoutManager extends LinearLayoutManager {
    private boolean isScrollEnabled = false;
    public CustomScrollLayoutManager(Context context) {
        super(context);
    }
   public void setScrollEnabled(boolean flag){
       this.isScrollEnabled = flag;
   }

    @Override
    public boolean canScrollVertically() {
        return false;
    }

    @Override
    public boolean canScrollHorizontally() {
        return false;
    }
}
