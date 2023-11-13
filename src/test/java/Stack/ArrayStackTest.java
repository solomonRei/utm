package Stack;

import org.junit.jupiter.api.Test;
import org.utm.lab3.Impl.Stack.ArrayStack;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArrayStackTest extends AbstractStackTest<ArrayStack<Integer>> {

    @Override
    protected Supplier<ArrayStack<Integer>> getStackSupplier() {
        return ArrayStack::new;
    }

    @Test
    public void shouldExpandCapacityWhenExceeded() {
        for (int i = 0; i < 100; i++) {
            stack.push(i);
        }

        assertEquals(100, stack.size(), "expected: 100, actual: " + stack.size());
        assertEquals(99, stack.peek(), "expected: 99, actual: " + stack.peek());
    }
}
