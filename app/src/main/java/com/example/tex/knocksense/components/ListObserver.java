package com.example.tex.knocksense.components;

import java.util.List;

/**
 * Created by anuragdwivedi on 03/09/17.
 */

public interface ListObserver {
    public void updateList(List<Article> articleList, boolean hasLoaded, boolean isLoading);
}
