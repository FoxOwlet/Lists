package com.foxowlet.demo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListTest {
    List<String> list = new LinkedList<>();

    @Test
    void get_shouldReturnValue_whenSavedWithAdd() {
        list.add("test");

        assertEquals("test", list.get(0));
    }

    @Test
    void get_shouldReturnSavedValue_whenMultipleSaved() {
        list.add("test1");
        list.add("test2");
        list.add("test3");

        assertEquals("test1", list.get(0));
        assertEquals("test2", list.get(1));
        assertEquals("test3", list.get(2));
    }

    @Test
    void add_shouldInsertElementByIndex() {
        list.add("test1");
        list.add("test3");

        list.add(1, "test2");

        assertEquals("test1", list.get(0));
        assertEquals("test2", list.get(1));
        assertEquals("test3", list.get(2));
    }

    @Test
    void add_shouldUpdateHead_whenIndexIsZero() {
        list.add("test2");

        list.add(0, "test1");

        assertEquals("test1", list.get(0));
        assertEquals("test2", list.get(1));
    }

    @Test
    void add_shouldAppendToList_whenIndexedAddIsUsed() {
        list.add(0, "test1");

        list.add("test2");

        assertEquals("test1", list.get(0));
        assertEquals("test2", list.get(1));
    }

    @Test
    void add_shouldAppendToList_whenNonZeroIndexedAddIsUsed() {
        list.add(0, "test1");
        list.add(1, "test2");

        list.add("test3");

        assertEquals("test1", list.get(0));
        assertEquals("test2", list.get(1));
        assertEquals("test3", list.get(2));
    }

    @Test
    void remove_shouldRemoveElementByIndex() {
        list.add("test1");
        list.add("test2");
        list.add("test3");

        list.remove(1);

        assertEquals("test3", list.get(1));
    }

    @Test
    void remove_shouldUpdateHead_whenZeroIndex() {
        list.add("test1");
        list.add("test2");

        list.remove(0);

        assertEquals("test2", list.get(0));
    }

    @Test
    void add_shouldAddElement_whenRemoveIsUsed() {
        list.add("test1");
        list.remove(0);

        list.add("test2");

        assertEquals("test2", list.get(0));
    }

    @Test
    void size_shouldReturnZero_whenListIsEmpty() {
        assertEquals(0, list.size());
    }

    @Test
    void size_shouldReturnNumberOfAddedElements() {
        list.add("test1");
        list.add("test2");

        assertEquals(2, list.size());
    }

    @Test
    void add_shouldUpdateSize() {
        list.add(0, "test1");

        assertEquals(1, list.size());
    }

    @Test
    void remove_shouldUpdateSize() {
        list.add("test");
        list.add("test2");
        list.remove(0);

        assertEquals(1, list.size());
    }

    @Test
    void add_shouldThrowIllegalArgumentException_whenIndexIsInvalid() {
        list.add("test");

        assertThrows(IllegalArgumentException.class, () -> list.get(2));
    }
}