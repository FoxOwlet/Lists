package com.foxowlet.demo;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<T> extends AbstractList<T> {
    private Node<T> head;
    private Node<T> last;
    private int size;

    @Override
    public void add(T elem) {
        ++modCount;
        ++size;
        Node<T> node = new Node<>(elem);
        if (last == null) {
            head = node;
        } else {
            last.next = node;
        }
        last = node;
    }

    @Override
    public void add(int index, T elem) {
        if (index > size || index < 0) {
            throw new IllegalArgumentException("Invalid index for list of size " + size);
        }
        ++modCount;
        ++size;
        if (index == 0) {
            addFirst(elem);
            return;
        }
        Node<T> current = head;
        for (int i = 0; i < index - 1; i++) {
            current = current.next;
        }
        Node<T> node = new Node<>(elem, current.next);
        if (current.next == null) {
            last = node;
        }
        current.next = node;
    }

    private void addFirst(T elem) {
        head = new Node<>(elem, head);
        if (last == null) {
            last = head;
        }
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            throw new IllegalArgumentException("Invalid index for list of size " + size);
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.value;
    }

    @Override
    public void remove(int index) {
        if (index >= size || index < 0) {
            throw new IllegalArgumentException("Invalid index for list of size " + size);
        }
        ++modCount;
        if (index == 0) {
            head = head.next;
            --size;
            if (size == 0) {
                last = null;
            }
            return;
        }
        Node<T> current = head;
        for (int i = 0; i < index - 1; i++) {
            current = current.next;
        }
        removeNode(current);
    }

    private void removeNode(Node<T> node) {
        if (node.next == last) {
            last = node;
        }
        if (node != last) {
            node.next = node.next.next;
        }
        --size;
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iter();
    }

    private class Iter implements Iterator<T> {
        private int initialModCount = modCount;
        private Node<T> currentNode = head;
        private int currentIndex;

        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            if (initialModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            T value = currentNode.value;
            ++currentIndex;
            currentNode = currentNode.next;
            return value;
        }

        @Override
        public void remove() {
            LinkedList.this.remove(currentIndex - 1);
            initialModCount++;
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;

        private Node(T value) {
            this(value, null);
        }

        private Node(T value, Node<T> next) {
            this.value = value;
            this.next = next;
        }
    }
}
