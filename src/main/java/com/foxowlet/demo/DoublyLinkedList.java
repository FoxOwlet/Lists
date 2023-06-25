package com.foxowlet.demo;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class DoublyLinkedList<T> extends AbstractList<T> {
    private Node<T> head;
    private Node<T> last;
    private int size;

    @Override
    public void add(T elem) {
        ++size;
        ++modCount;
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
        ++modCount;
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
        ++modCount;
        Node<T> node = findNode(index);
        removeNode(node);
    }

    private void removeNode(Node<T> node) {
        if (node == head) {
            head = node.next;
        } else {
            node.prev.next = node.next;
        }
        if (node == last) {
            last = node.prev;
        } else if (node != head) {
            node.next.prev = node.prev;
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
        private Node<T> lastNode;

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
            lastNode = currentNode;
            currentNode = currentNode.next;
            return value;
        }

        @Override
        public void remove() {
            if (initialModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            removeNode(lastNode);
        }
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
