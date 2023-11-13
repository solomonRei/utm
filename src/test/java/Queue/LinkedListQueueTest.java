package Queue;

import org.utm.lab3.Impl.Queue.LinkedListQueue;

import java.util.function.Supplier;

public class LinkedListQueueTest extends AbstractQueueTest<LinkedListQueue<Integer>> {

    @Override
    protected Supplier<LinkedListQueue<Integer>> getQueueSupplier() {
        return LinkedListQueue::new;
    }
}
