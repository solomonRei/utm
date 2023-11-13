package Queue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.utm.lab3.interfaces.Queue;

import java.util.NoSuchElementException;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class AbstractQueueTest<T extends Queue<Integer>> {

    protected T queue;

    protected abstract Supplier<T> getQueueSupplier();

    @BeforeEach
    public void setup() {
        queue = getQueueSupplier().get();
    }

    @Test
    public void shouldEnqueueItem() {
        queue.enqueue(1);
        assertAll("Queue should enqueue item correctly",
                () -> assertEquals(1, queue.size(), "expected: 1, actual: " + queue.size()),
                () -> assertEquals(1, queue.front(), "expected: 1, actual: " + queue.front())
        );
    }

    @Test
    public void shouldDequeueItem() {
        queue.enqueue(1);
        queue.enqueue(2);
        assertEquals(1, queue.dequeue());
        assertEquals(1, queue.size());
    }

    @Test
    public void shouldReturnFrontItem() {
        queue.enqueue(1);
        assertEquals(1, queue.front());
    }

    @Test
    public void shouldCheckIfQueueIsEmpty() {
        assertTrue(queue.isEmpty());
        queue.enqueue(1);
        assertFalse(queue.isEmpty());
    }

    @Test
    public void shouldReturnSizeOfQueue() {
        assertEquals(0, queue.size());
        queue.enqueue(1);
        assertEquals(1, queue.size());
    }

    @Test
    public void shouldResizeWhenQueueSizeDropsBelowThreshold() {
        for (int i = 0; i < 20; i++) {
            queue.enqueue(i);
        }

        for (int i = 0; i < 15; i++) {
            queue.dequeue();
        }

        assertEquals(5, queue.size());
    }

    @Test
    public void shouldHandleContinuousEnqueueAndDequeue() {
        for (int i = 0; i < 10; i++) {
            queue.enqueue(i);
            assertEquals(i, queue.dequeue(), "Dequeued item should match the enqueued item");
        }
        assertTrue(queue.isEmpty(), "Queue should be empty after continuous enqueue dequeue operations");
    }

    @Test
    public void shouldMaintainOrderAfterMultipleOperations() {
        for (int i = 0; i < 5; i++) {
            queue.enqueue(i);
        }

        assertEquals(0, queue.dequeue(), "First dequeued item should be 0");
        queue.enqueue(5);

        for (int i = 1; i <= 5; i++) {
            assertEquals(i, queue.dequeue(), "DeQueuing should follow FIFO order");
        }
    }


    @Test
    public void shouldRemainStableAfterManyOperations() {
        for (int i = 0; i < 1000; i++) {
            queue.enqueue(i);
        }
        assertEquals(1000, queue.size(), "expected: 1000, actual: " + queue.size());

        for (int i = 0; i < 500; i++) {
            queue.dequeue();
        }
        assertEquals(500, queue.size(), "expected: 500, actual: " + queue.size());
    }

    @Test
    public void shouldThrowExceptionWhenDequeueOnEmptyQueue() {
        assertThrows(NoSuchElementException.class, queue::dequeue);
    }

    @Test
    public void shouldThrowExceptionWhenFrontOnEmptyQueue() {
        assertThrows(NoSuchElementException.class, queue::front);
    }
}

