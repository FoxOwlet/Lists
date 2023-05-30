package com.foxowlet.demo;

public class LinkedList<T> implements List<T> {
    private Node<T> head;
    private Node<T> last;
    private int size;

    @Override
    public void add(T elem) {
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
        --size;
        if (index == 0) {
            head = head.next;
            if (size == 0) {
                last = null;
            }
            return;
        }
        Node<T> current = head;
        for (int i = 0; i < index - 1; i++) {
            current = current.next;
        }
        if (current.next == last) {
            last = current;
        }
        current.next = current.next.next;
    }


    @Override
    public int size() {
        return size;
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
