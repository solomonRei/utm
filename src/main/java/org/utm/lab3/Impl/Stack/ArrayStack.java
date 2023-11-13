package org.utm.lab3.Impl.Stack;

import org.utm.lab3.interfaces.Stack;

import java.util.NoSuchElementException;

public class ArrayStack<T> implements Stack<T> {

    private T[] array;

    private int size;

    @SuppressWarnings("unchecked")
    public ArrayStack() {
        array = (T[]) new Object[1];
        size = 0;
    }

    @Override
    public void push(T item) {
        if (size == array.length) {
            resize(2 * array.length);
        }
        array[size++] = item;
    }

    @Override
    public T pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
        T item = array[--size];
        array[size] = null;
        if (size > 0 && size == array.length / 4) {
            resize(array.length / 2);
        }
        return item;
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
    private void resize(int capacity) {
        T[] temp = (T[]) new Object[capacity];
        System.arraycopy(array, 0, temp, 0, size);
        array = temp;
    }
}

