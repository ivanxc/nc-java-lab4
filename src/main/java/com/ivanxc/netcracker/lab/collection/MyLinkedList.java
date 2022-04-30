package com.ivanxc.netcracker.lab.collection;

import java.lang.reflect.Array;
import java.util.Iterator;

public class MyLinkedList<E> implements ILinkedList<E> {
    private int size;
    private Node<E> first;
    private Node<E> last;

    @Override
    public void add(E element) {
        if (first == null) {
            addFirst(element);
            last = first;
        } else {
            addLast(element);
        }
    }

    @Override
    public void add(int index, E element) {
        checkPositionIndex(index);

        if (first == null || index == size) {
            add(element);
        } else if (index == 0) {
            addFirst(element);
        } else {
            Node<E> beforeIndex = first; // A B C D E
            for(int i = 0; i < index - 1; i++) {
                beforeIndex = beforeIndex.next;
            }
            beforeIndex.next = new Node<>(element, beforeIndex.next);
            size++;
        }
    }

    public void addFirst(E element) {
        first = new Node<>(element, first);
        size++;
    }

    public void addLast(E element) {
        Node<E> newNode = new Node<>(element, null);
        last.next = newNode;
        last = newNode;
        size++;
    }

    @Override
    public void clear() {
        first = null;
        last = null;
        size = 0;
    }

    @Override
    public E get(int index) {
        checkElementIndex(index);
        return getNodeByIndex(index).element;
    }

    @Override
    public int indexOf(E element) {
        Node<E> current = first;

        for(int i = 0; i < size; i++) {
            if (current.element == element) {
                return i;
            }
            current = current.next;
        }

        return -1;
    }

    @Override
    public E remove(int index) {
        checkElementIndex(index);

        E removedValue;

        if (index == 0) {
            removedValue = removeFirst();
        } else if (index == size - 1) {
            removedValue = removeLast();
        } else {
            Node<E> beforeToRemove = getNodeByIndex(index - 1);
            Node<E> toRemove = beforeToRemove.next;
            beforeToRemove.next = toRemove.next;
            removedValue = toRemove.element;
            size--;
        }

        return removedValue;
    }

    public E removeFirst() {
        E removedValue = first.element;
        first = first.next;
        size--;

        return removedValue;
    }

    public E removeLast() {
        E removedValue = last.element;
        if (size <= 1) {
            clear();
        } else {
            last = getNodeByIndex(size - 2);
            last.next = null;
            size--;
        }

        return removedValue;
    }

    @Override
    public E set(int index, E element) {
        checkElementIndex(index);

        Node<E> beforeSet = getNodeByIndex(index - 1);
        E oldValue = beforeSet.next.element;
        beforeSet.next = new Node<>(element, beforeSet.next.next);

        return oldValue;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[this.size];
        int i = 0;

        for(Node<E> x = this.first; x != null; x = x.next) {
            result[i++] = x.element;
        }

        return result;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < this.size) {
            a = (T[]) Array.newInstance(a.getClass().getComponentType(), this.size);
        }

        int i = 0;
        Object[] result = a;

        for(MyLinkedList.Node x = this.first; x != null; x = x.next) {
            result[i++] = x.element;
        }

        if (a.length > this.size) {
            a[this.size] = null;
        }

        return a;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            Node<E> current = new Node<>(null, first);
            @Override
            public boolean hasNext() {
                return current.next != null;
            }

            @Override
            public E next() {
                current = current.next;
                return current.element;
            }
        };
    }

    private Node<E> getNodeByIndex(int index) {
        Node<E> currentNode = first;

        for(int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }

        return currentNode;
    }

    private boolean isValidPositionIndex(int index) {
        return index >= 0 && index <= size;
    }

    private void checkPositionIndex(int index) {
        if (!isValidPositionIndex(index)) {
            throw new IndexOutOfBoundsException();
        }
    }

    private boolean isValidElementIndex(int index) {
        return index >= 0 && index < size;
    }

    private void checkElementIndex(int index) {
        if (!isValidElementIndex(index)) {
            throw new IndexOutOfBoundsException();
        }
    }

    private class Node<E> {
        E element;
        Node<E> next;

        public Node(E element, Node<E> nextNode) {
            this.element = element;
            this.next = nextNode;
        }
    }
}
