package org.utm.lab3.Impl.Queue;

import org.utm.lab3.interfaces.Queue;

import java.util.NoSuchElementException;

public class ArrayDownQueue<T> implements Queue<T> {

    private T[] array;

    private int front, rear, size;

    @SuppressWarnings("unchecked")
    public ArrayDownQueue() {
        array = (T[]) new Object[10];
        front = 0;
        rear = -1;
        size = 0;
    }

    @Override
    public void enqueue(T item) {
        if (size == array.length) {
            resize(array.length * 2);
        }
        rear = (rear + 1) % array.length;
        array[rear] = item;
        size++;
    }

    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        T item = array[front];
        front = (front + 1) % array.length;
        size--;
        int decrementThreshold = 4;
        if (size > 0 && size == array.length / decrementThreshold) {
            resize(array.length / 2);
        }
        return item;
    }

    @Override
    public T front() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return array[front];
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
        var newArray = (T[]) new Object[newCapacity];
        for (int i = 0, j = front; i < size; i++, j = (j + 1) % array.length) {
            newArray[i] = array[j];
        }
        array = newArray;
        front = 0;
        rear = size - 1;
    }
}

