package org.utm.lab3.interfaces;

public interface Queue<T> {

    void enqueue(T item);

    T dequeue();

    T front();

    boolean isEmpty();

    int size();
}


