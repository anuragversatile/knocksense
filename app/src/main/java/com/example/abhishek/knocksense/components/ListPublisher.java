package com.example.abhishek.knocksense.components;

/**
 * Created by anuragdwivedi on 03/09/17.
 */

public interface ListPublisher {
    public void registerObserver(String listType, ListObserver listObserver);
    public void removeObserver(String listType, ListObserver listObserver);
    public void notifyListObservers(String listType, Integer newItemCount);
}
