package com.ivanxc.netcracker.lab.collection;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MyLinkedListTest {
    Integer[] values;
    ILinkedList<Integer> filledList;
    ILinkedList<Integer> emptyList;

    @BeforeEach
    void initLists() {
        values = new Integer[] {1, 2, 3, 4};
        filledList = new MyLinkedList<>();
        emptyList = new MyLinkedList<>();

        for(Integer value : values) {
            filledList.add(value);
        }
    }

    @Test
    void sizeWhenCreated() {
        assertEquals(0, emptyList.size());
    }

    @Test
    void sizeWhenElementsWasAdded() {
        emptyList.add(1);
        assertEquals(1, emptyList.size());
        emptyList.add(2);
        assertEquals(2, emptyList.size());
        emptyList.add(1);
        assertEquals(3, emptyList.size());
    }

    @Test
    void sizeAfterRemoveElement() {
        int size = filledList.size();

        filledList.remove(3);
        assertEquals(--size, filledList.size());
        filledList.remove(2);
        assertEquals(--size, filledList.size());
        filledList.remove(1);
        assertEquals(--size, filledList.size());
        filledList.remove(0);
        assertEquals(--size, filledList.size());
    }

    @Test
    void sizeAfterClear() {
        filledList.clear();
        assertEquals(0, filledList.size());
    }

    @Test
    void elementsAddedAtEnd() {
        emptyList.add(1);
        assertEquals(1, emptyList.get(0));
        emptyList.add(2);
        assertEquals(2, emptyList.get(1));
        emptyList.add(3);
        assertEquals(3, emptyList.get(2));
        emptyList.add(4);
        assertEquals(4, emptyList.get(3));
    }

    @Test
    void addElementToTheHeadOfList() {
        emptyList.add(0, 1);
        assertEquals(1, emptyList.get(0));

        emptyList.add(0, 2);
        assertEquals(2, emptyList.get(0));

        emptyList.add(0, 3);
        assertEquals(3, emptyList.get(0));
    }

    @Test
    void addElementToTheMiddleOfList() {
        filledList.add(1, 10);
        assertEquals(10, filledList.get(1));

        filledList.add(2, 20);
        assertEquals(20, filledList.get(2));
    }

    @Test
    void addElementToTheTailOfList() {
        filledList.add(4, 5);
        assertEquals(5, filledList.get(4));

        filledList.add(5, 6);
        assertEquals(6, filledList.get(5));

        filledList.add(6, 7);
        assertEquals(7, filledList.get(6));
    }

    @Test
    void addElementAtIndexGreaterThanSize() {
        int size = filledList.size();
        assertThrows(IndexOutOfBoundsException.class, () -> filledList.add(size + 1, 4));
    }

    @Test
    void addElementAtNegativeIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> filledList.add(-1, 2));
    }

    @Test
    void indexOfReturnsCorrectIndex() {
        int index = 0;
        Integer thousand = 1000;
        Integer anotherThousand = 1000;
        filledList.add(thousand);
        filledList.add(anotherThousand);

        assertEquals(index, filledList.indexOf(values[index]));
        index++;
        assertEquals(index, filledList.indexOf(values[index]));
        index++;
        assertEquals(index, filledList.indexOf(values[index]));
        index++;
        assertEquals(index, filledList.indexOf(values[index]));
    }

    @Test
    void indexOfReturnsFirstFoundedElementWithSpecifiedReference() {
        Integer integer = 100;
        emptyList.add(10);
        emptyList.add(integer);
        emptyList.add(integer);
        emptyList.add(integer);
        emptyList.add(integer);

        assertEquals(1, emptyList.indexOf(integer));
    }

    @Test
    void indexOfAbsentElement() {
        assertEquals(-1, filledList.indexOf(Integer.MAX_VALUE));
    }

    @Test
    void removeElementFromTheHeadOfList() {
        filledList.remove(0);
        assertArrayEquals(new Integer[]{2, 3, 4}, filledList.toArray());
        filledList.remove(0);
        assertArrayEquals(new Integer[]{3, 4}, filledList.toArray());
        filledList.remove(0);
        assertArrayEquals(new Integer[]{4}, filledList.toArray());
        filledList.remove(0);
        assertArrayEquals(new Integer[]{}, filledList.toArray());
    }

    @Test
    void removeElementFromTheMiddleOfList() {
        filledList.remove(2);
        assertArrayEquals(new Integer[]{1, 2, 4}, filledList.toArray());
        filledList.remove(1);
        assertArrayEquals(new Integer[]{1, 4}, filledList.toArray());
    }

    @Test
    void removeElementFromTheTailOfList() {
        filledList.remove(3);
        assertArrayEquals(new Integer[]{1, 2, 3}, filledList.toArray());
        filledList.remove(2);
        assertArrayEquals(new Integer[]{1, 2}, filledList.toArray());
        filledList.remove(1);
        assertArrayEquals(new Integer[]{1}, filledList.toArray());
        filledList.remove(0);
        assertArrayEquals(new Integer[]{}, filledList.toArray());
    }

    @Test
    void removeElementAtIndexGreaterOrEqualThanSize() {
        int size = filledList.size();
        assertThrows(IndexOutOfBoundsException.class, () -> filledList.remove(size));
        assertThrows(IndexOutOfBoundsException.class, () -> filledList.remove(size + 1));
    }

    @Test
    void removeElementAtNegativeIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> filledList.remove(-1));
    }

    @Test
    void setElementReturnsOldValue() {
        int oldValue = filledList.get(1);
        assertEquals(oldValue, filledList.set(1, 3));
    }

    @Test
    void setElementReplaceSpecifiedElement() {
        filledList.set(1, 100);
        assertArrayEquals(new Integer[]{1, 100, 3, 4}, filledList.toArray());
    }

    @Test
    void setElementAtIndexGreaterOrEqualSize() {
        int size = filledList.size();
        assertThrows(IndexOutOfBoundsException.class, () -> filledList.set(size, 100));
        assertThrows(IndexOutOfBoundsException.class, () -> filledList.set(size + 1, 100));
    }

    @Test
    void setElementAtNegativeIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> filledList.set(-1, 100));
    }

    @Test
    void toArrayWithoutParam() {
        assertArrayEquals(values, filledList.toArray());
    }

    @Test
    void toArrayWhereParameterIsArrayHasSizeGreaterThanListSize() {
        assertArrayEquals(values, filledList.toArray(new Integer[0]));
        assertArrayEquals(new Integer[]{1, 2, 3, 4, null}, filledList.toArray(new Integer[filledList.size() + 1]));
    }

    @Test
    void iteratorReturnsElementsAtAddedOrder() {
        int index = 0;

        for(Integer num : filledList) {
            assertSame(values[index++], num);
        }
    }

}