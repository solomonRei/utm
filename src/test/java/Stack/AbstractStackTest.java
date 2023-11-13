package Stack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.utm.lab3.interfaces.Stack;

import java.util.NoSuchElementException;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class AbstractStackTest<T extends Stack<Integer>> {

    protected T stack;

    protected abstract Supplier<T> getStackSupplier();

    @BeforeEach
    public void setup() {
        stack = getStackSupplier().get();
    }

    @Test
    public void shouldPushItem() {
        stack.push(1);
        assertAll("Stack should push item correctly",
                () -> assertEquals(1, stack.size(), "expected: 1, actual: " + stack.size()),
                () -> assertEquals(1, stack.peek(), "expected: 1, actual: " + stack.peek())
        );
    }

    @Test
    public void shouldCheckIfStackIsEmpty() {
        assertTrue(stack.isEmpty(), "expected: true, actual: " + stack.isEmpty());
        stack.push(1);
        assertFalse(stack.isEmpty(), "expected: false, actual: " + stack.isEmpty());
    }

    @Test
    public void shouldReturnSizeOfStack() {
        assertEquals(0, stack.size(), "expected: 0, actual: " + stack.size());
        stack.push(1);
        assertEquals(1, stack.size(), "expected: 1, actual: " + stack.size());
    }

    @Test
    public void shouldHandleNullElements() {
        stack.push(null);
        assertAll("Stack should handle null elements",
                () -> assertFalse(stack.isEmpty(), "expected: false, actual: " + stack.isEmpty()),
                () -> assertEquals(1, stack.size(), "expected: 1, actual: " + stack.size()),
                () -> assertNull(stack.peek(), "expected: null, actual: " + stack.peek()),
                () -> {
                    var popped = stack.pop();
                    assertNull(popped, "expected: null, actual: " + popped);
                },
                () -> assertTrue(stack.isEmpty(), "expected: true, actual: " + stack.isEmpty())
        );
    }

    @Test
    public void shouldRemainStableAfterManyOperations() {
        for (int i = 0; i < 1000; i++) {
            stack.push(i);
        }
        assertEquals(1000, stack.size(), "expected: 1000, actual: " + stack.size());

        for (int i = 0; i < 500; i++) {
            stack.pop();
        }
        assertEquals(500, stack.size(), "expected: 500, actual: " + stack.size());
    }

    @Test
    public void shouldKeepConsistencyBetweenPopAndPeek() {
        stack.push(10);
        stack.push(20);

        int topItem = stack.peek();
        assertEquals(20, topItem, "expected: 20, actual: " + topItem);
        assertEquals(20, stack.pop(), "expected: 20, actual: " + stack.pop());
    }

    @Test
    public void shouldResizeWhenExceedingCapacity() {
        for (int i = 0; i < 20; i++) {
            stack.push(i);
        }

        assertAll("Stack should resize correctly and maintain proper order of elements",
                () -> assertEquals(20, stack.size(), "expected: 20, actual: " + stack.size()),
                () -> assertEquals(19, stack.peek(), "expected: 19, actual: " + stack.peek()),

                () -> {
                    for (int i = 19; i >= 0; i--) {
                        int actual = stack.pop();
                        assertEquals(i, actual);
                    }
                },

                () -> assertTrue(stack.isEmpty(), "expected: true, actual: " + stack.isEmpty())
        );
    }

    @Test
    public void shouldThrowExceptionWhenPopOnEmptyStack() {
        assertThrows(NoSuchElementException.class, stack::pop);
    }

    @Test
    public void shouldThrowExceptionWhenPeekOnEmptyStack() {
        assertThrows(NoSuchElementException.class, stack::peek);
    }
}

