package com.foxowlet.demo;

import java.util.Arrays;

public class ArrayList<T> extends AbstractList<T> {
    private static final int DEFAULT_INITIAL_SIZE = 16;
    private T[] elems;
    private int size;

    public ArrayList() {
        this(DEFAULT_INITIAL_SIZE);
    }

    public ArrayList(int initialSize) {
        this.elems = (T[]) new Object[initialSize];
        this.size = 0;
    }

    @Override
    public void add(T elem) {
        ++modCount;
        resizeIfNeeded();
        elems[size++] = elem;
    }

    @Override
    public void add(int index, T elem) {
        if (index > size || index < 0) {
            throw new IllegalArgumentException("Invalid index for list of size " + size);
        }
        ++modCount;
        resizeIfNeeded();
        System.arraycopy(elems, index, elems, index + 1, size - index);
        elems[index] = elem;
        ++size;
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            throw new IllegalArgumentException("Invalid index for list of size " + size);
        }
        return elems[index];
    }

    @Override
    public void remove(int index) {
        if (index >= size || index < 0) {
            throw new IllegalArgumentException("Invalid index for list of size " + size);
        }
        ++modCount;
        System.arraycopy(elems, index + 1, elems, index, size - index - 1);
        --size;
    }

    @Override
    public int size() {
        return size;
    }

    private void resizeIfNeeded() {
        if (size == elems.length) {
            elems = Arrays.copyOf(elems, size * 2);
        }
    }
}
