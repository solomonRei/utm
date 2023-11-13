package Stack;

import org.junit.jupiter.api.Test;
import org.utm.lab3.Impl.Stack.LinkedListStack;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LinkedListStackTest extends AbstractStackTest<LinkedListStack<Integer>> {

    @Override
    protected Supplier<LinkedListStack<Integer>> getStackSupplier() {
        return LinkedListStack::new;
    }

    @Test
    public void shouldHandleDeepStacksEfficiently() {
        for (int i = 0; i < 10000; i++) {
            stack.push(i);
        }

        assertEquals(10000, stack.size(), "expected: 10000, actual: " + stack.size());

        for (int i = 9999; i >= 0; i--) {
            int actual = stack.pop();
            assertEquals(i, actual, "expected: " + i + ", actual: " + actual);
        }
    }


    @Test
    public void shouldHandleMultiplePushAndPop() {
        stack.push(1);
        stack.push(2);
        stack.push(3);

        assertAll("Stack should pop items in LIFO order",
                () -> {
                    int actual = stack.pop();
                    assertEquals(3, actual, "expected: 3, actual: " + actual);
                },
                () -> {
                    int actual = stack.pop();
                    assertEquals(2, actual, "expected: 2, actual: " + actual);
                },
                () -> {
                    int actual = stack.pop();
                    assertEquals(1, actual, "expected: 1, actual: " + actual);
                }
        );

        assertTrue(stack.isEmpty(), "expected: true, actual: " + stack.isEmpty());
    }

    @Test
    public void shouldClearStackCorrectly() {
        stack.push(1);
        stack.push(2);

        stack.pop();
        stack.pop();

        assertTrue(stack.isEmpty(), "Stack should be empty after popping all items");

        stack.push(3);

        assertEquals(1, stack.size(), "expected: 1, actual: " + stack.size());
        assertEquals(3, stack.peek(), "expected: 3, actual: " + stack.peek());
    }

}