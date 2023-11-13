package org.utm.lab3.Impl.Stack;

import org.utm.lab3.interfaces.Stack;

import java.util.NoSuchElementException;

public class LinkedListStack<T> implements Stack<T> {

    private Node top;

    private int size;

    private class Node {

        T data;

        Node next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    public LinkedListStack() {
        top = null;
        size = 0;
    }

    @Override
    public void push(T item) {
        Node newNode = new Node(item);
        newNode.next = top;
        top = newNode;
        size++;
    }

    @Override
    public T pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
        T item = top.data;
        top = top.next;
        size--;
        return item;
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
        return top.data;
    }

    @Override
    public boolean isEmpty() {
        return top == null;
    }

    @Override
    public int size() {
        return size;
    }
}


