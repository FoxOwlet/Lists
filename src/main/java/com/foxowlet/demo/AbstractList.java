package com.foxowlet.demo;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class AbstractList<T> implements List<T> {
    protected int modCount;

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private int modCount = AbstractList.this.modCount;
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return size() > currentIndex;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                if (modCount != AbstractList.this.modCount) {
                    throw new ConcurrentModificationException();
                }
                return get(currentIndex++);
            }

            @Override
            public void remove() {
                if (modCount != AbstractList.this.modCount) {
                    throw new ConcurrentModificationException();
                }
                AbstractList.this.remove(--currentIndex);
                ++modCount;
            }
        };
    }
}
