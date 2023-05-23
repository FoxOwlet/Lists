package com.foxowlet.demo;

public interface List<T> {
    void add(T elem);
    void add(int index, T elem);

    T get(int index);

    void remove(int index);

    int size();
}
