package com.foxowlet.demo;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
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
    @MethodSource("com.foxowlet.demo.ListsTest#getImplementations")
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

    @Nested
    class IteratorTests {
        @ListTest
        void hasNext_shouldReturnFalse_whenListIsEmpty(List<String> list) {
            Iterator<String> iterator = list.iterator();

            assertFalse(iterator.hasNext());
        }

        @ListTest
        void hasNext_shouldReturnTrue_whenListIsNotEmpty(List<String> list) {
            list.add("test");
            Iterator<String> iterator = list.iterator();

            assertTrue(iterator.hasNext());
        }

        @ListTest
        void next_shouldReturnElement_whenListIsNotEmpty(List<String> list) {
            list.add("test");
            Iterator<String> iterator = list.iterator();

            assertEquals("test", iterator.next());
        }

        @ListTest
        void next_shouldReturnSubsequentElements_whenListIsNotEmpty(List<String> list) {
            list.add("test1");
            list.add("test2");
            Iterator<String> iterator = list.iterator();

            assertEquals("test1", iterator.next());
            assertEquals("test2", iterator.next());
        }

        @ListTest
        void hasNext_shouldReturnFalse_whenAllElementsAreConsumed(List<String> list) {
            list.add("test");
            Iterator<String> iterator = list.iterator();
            iterator.next();

            assertFalse(iterator.hasNext());
        }

        @ListTest
        @Timeout(value = 1, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
        void iterator_shouldSupportLargeLists(List<String> list) {
            for (int i = 0; i < 100_000; i++) {
                list.add(String.valueOf(i));
            }
            Iterator<String> iterator = list.iterator();
            while (iterator.hasNext()) {
                iterator.next();
            }
        }

        @ListTest
        void next_shouldThrowNoSuchElementException_whenNoAvailableElements(List<String> list) {
            Iterator<String> iterator = list.iterator();
            assertThrows(NoSuchElementException.class, iterator::next);
        }

        @ListTest
        void next_shouldThrowConcurrentModificationException_whenListIsModifiedAfterIteratorCreation(List<String> list) {
            list.add("test");
            Iterator<String> iterator = list.iterator();
            list.add("test2");

            assertTrue(iterator.hasNext());
            assertThrows(ConcurrentModificationException.class, iterator::next);
        }

        @ListTest
        void remove_shouldRemoveElementFromCollection(List<String> list) {
            list.add("test");
            Iterator<String> iterator = list.iterator();
            iterator.next();

            iterator.remove();

            assertEquals(0, list.size());
        }

        @ListTest
        void remove_shouldUpdateModCount(List<String> list) {
            list.add("test1");
            list.add("test2");
            Iterator<String> iterator = list.iterator();
            iterator.next();

            iterator.remove();

            assertEquals(1, list.size());
            assertEquals("test2", iterator.next());
            assertEquals("test2", list.get(0));
        }
    }
}