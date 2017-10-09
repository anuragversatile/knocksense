package com.example.tex.knocksense.components;

import java.util.List;

/**
 * Created by anuragdwivedi on 03/09/17.
 */

public interface ListPublisher {
    public void registerObserver(String listType, ListObserver listObserver);
    public void removeObserver(String listType, ListObserver listObserver);
    public void notifyListObservers(String listType, List<Article> articles, boolean hasLoaded, boolean isLoading);
}
