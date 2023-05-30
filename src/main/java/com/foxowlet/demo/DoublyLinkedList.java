package com.foxowlet.demo;

public class DoublyLinkedList<T> implements List<T> {
    private Node<T> head;
    private Node<T> last;
    private int size;

    @Override
    public void add(T elem) {
        ++size;
        Node<T> node = new Node<>(elem, null, last);
        if (head == null) {
            head = node;
        } else {
            last.next = node;
        }
        last = node;
    }

    @Override
    public void add(int index, T elem) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("Invalid index for list of size " + size);
        }
        if (index == size) {
            add(elem);
            return;
        }
        ++size;
        Node<T> current = findNode(index);
        Node<T> node = new Node<>(elem, current, current.prev);
        if (index == 0) {
            head = node;
        } else {
            current.prev.next = node;
        }
        current.prev = node;
    }

    private Node<T> findNode(int index) {
        Node<T> current;
        if (index <= size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = last;
            for (int i = 0; i < size - index - 1; i++) {
                current = current.prev;
            }
        }
        return current;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Invalid index for list of size " + size);
        }
        return findNode(index).value;
    }

    @Override
    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Invalid index for list of size " + size);
        }
        --size;
        Node<T> node = findNode(index);
        if (index == 0) {
            head = node.next;
        } else {
            node.prev.next = node.next;
        }
        if (index == size) {
            last = node.prev;
        } else if (index != 0) {
            node.next.prev = node.prev;
        }
    }

    @Override
    public int size() {
        return size;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        private Node(T value, Node<T> next, Node<T> prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }
}
