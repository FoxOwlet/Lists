package com.foxowlet.demo;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ListsTest {

    static Stream<Arguments> getImplementations() {
        return Stream.of(
                Arguments.of(new LinkedList<>()),
                Arguments.of(new DoublyLinkedList<>()),
                Arguments.of(new ArrayList<>()),
                Arguments.of(new ArrayList<>(1))
        );
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @ParameterizedTest
    @MethodSource("getImplementations")
    @interface ListTest {}

    @ListTest
    void get_shouldReturnValue_whenSavedWithAdd(List<String> list) {
        list.add("test");

        assertEquals("test", list.get(0));
    }

    @ListTest
    void get_shouldReturnSavedValue_whenMultipleSaved(List<String> list) {
        list.add("test1");
        list.add("test2");
        list.add("test3");

        assertEquals("test1", list.get(0));
        assertEquals("test2", list.get(1));
        assertEquals("test3", list.get(2));
    }

    @ListTest
    void add_shouldInsertElementByIndex(List<String> list) {
        list.add("test1");
        list.add("test3");

        list.add(1, "test2");

        assertEquals("test1", list.get(0));
        assertEquals("test2", list.get(1));
        assertEquals("test3", list.get(2));
    }

    @ListTest
    void add_shouldUpdateHead_whenIndexIsZero(List<String> list) {
        list.add("test2");

        list.add(0, "test1");

        assertEquals("test1", list.get(0));
        assertEquals("test2", list.get(1));
    }

    @ListTest
    void add_shouldAppendToList_whenIndexedAddIsUsed(List<String> list) {
        list.add(0, "test1");

        list.add("test2");

        assertEquals("test1", list.get(0));
        assertEquals("test2", list.get(1));
    }

    @ListTest
    void add_shouldAppendToList_whenNonZeroIndexedAddIsUsed(List<String> list) {
        list.add(0, "test1");
        list.add(1, "test2");

        list.add("test3");

        assertEquals("test1", list.get(0));
        assertEquals("test2", list.get(1));
        assertEquals("test3", list.get(2));
    }

    @ListTest
    void remove_shouldRemoveElementByIndex(List<String> list) {
        list.add("test1");
        list.add("test2");
        list.add("test3");

        list.remove(1);

        assertEquals("test3", list.get(1));
    }

    @ListTest
    void remove_shouldUpdateHead_whenZeroIndex(List<String> list) {
        list.add("test1");
        list.add("test2");

        list.remove(0);

        assertEquals("test2", list.get(0));
    }

    @ListTest
    void add_shouldAddElement_whenRemoveIsUsed(List<String> list) {
        list.add("test1");
        list.remove(0);

        list.add("test2");

        assertEquals("test2", list.get(0));
    }

    @ListTest
    void size_shouldReturnZero_whenListIsEmpty(List<String> list) {
        assertEquals(0, list.size());
    }

    @ListTest
    void size_shouldReturnNumberOfAddedElements(List<String> list) {
        list.add("test1");
        list.add("test2");

        assertEquals(2, list.size());
    }

    @ListTest
    void add_shouldUpdateSize(List<String> list) {
        list.add(0, "test1");

        assertEquals(1, list.size());
    }

    @ListTest
    void remove_shouldUpdateSize(List<String> list) {
        list.add("test");
        list.add("test2");
        list.remove(0);

        assertEquals(1, list.size());
    }

    @ListTest
    void add_shouldThrowIllegalArgumentException_whenIndexIsInvalid(List<String> list) {
        list.add("test");

        assertThrows(IllegalArgumentException.class, () -> list.get(2));
    }

    @ListTest
    void add_shouldAppendElement_whenLastIsRemoved(List<String> list) {
        list.add("test1");
        list.add("test2");
        list.remove(1);

        list.add("test3");

        assertEquals("test1", list.get(0));
        assertEquals("test3", list.get(1));
    }

    @ListTest
    void add_shouldAppendElement_whenHeadIsRemoved(List<String> list) {
        list.add("test1");
        list.add("test2");
        list.remove(0);

        list.add("test3");

        assertEquals("test2", list.get(0));
        assertEquals("test3", list.get(1));
    }
}