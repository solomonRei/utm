package org.utm.lab3.Impl.Queue;

import org.utm.lab3.interfaces.Queue;

import java.util.NoSuchElementException;

public class ArrayQueue<T> implements Queue<T> {

    private final T[] array;

    private int front = 0;

    private int rear = -1;

    private int size = 0;

    private static final int DEFAULT_CAPACITY = 1000;

    @SuppressWarnings("unchecked")
    public ArrayQueue() {
        array = (T[]) new Object[DEFAULT_CAPACITY];
    }

    public void enqueue(T item) {
        if (size == array.length) {
            throw new NoSuchElementException("Queue is full");
        }
        rear = (rear + 1) % array.length;
        array[rear] = item;
        size++;
    }

    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        T item = array[front];
        array[front] = null;
        front = (front + 1) % array.length;
        size--;
        return item;
    }

    public T front() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return array[front];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }
}

