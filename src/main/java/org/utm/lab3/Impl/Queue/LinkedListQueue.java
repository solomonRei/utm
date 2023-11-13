package org.utm.lab3.Impl.Queue;

import org.utm.lab3.interfaces.Queue;

import java.util.NoSuchElementException;

public class LinkedListQueue<T> implements Queue<T> {

    private Node front, rear;

    private int size;

    private class Node {

        T data;

        Node next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    public LinkedListQueue() {
        front = null;
        rear = null;
        size = 0;
    }

    @Override
    public void enqueue(T item) {
        Node newNode = new Node(item);
        if (rear == null) {
            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }

    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        T item = front.data;
        front = front.next;
        if (front == null) {
            rear = null;
        }
        size--;
        return item;
    }

    @Override
    public T front() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return front.data;
    }

    @Override
    public boolean isEmpty() {
        return front == null;
    }

    @Override
    public int size() {
        return size;
    }
}

