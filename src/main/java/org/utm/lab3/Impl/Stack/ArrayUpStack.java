package org.utm.lab3.Impl.Stack;

import org.utm.lab3.interfaces.Stack;

import java.util.NoSuchElementException;

public class ArrayUpStack<T> implements Stack<T> {
    private T[] array;
    private int size;
    private final int incrementSize = 10;

    @SuppressWarnings("unchecked")
    public ArrayUpStack() {
        array = (T[]) new Object[incrementSize];
        size = 0;
    }

    @Override
    public void push(T item) {
        if (size == array.length) {
            resize(array.length + incrementSize);
        }
        array[size++] = item;
    }

    @Override
    public T pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
        return array[--size];
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
        return array[size - 1];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {
        T[] newArray = (T[]) new Object[newCapacity];
        System.arraycopy(array, 0, newArray, 0, size);
        array = newArray;
    }
}

