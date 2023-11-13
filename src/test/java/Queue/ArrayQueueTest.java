package Queue;

import org.utm.lab3.Impl.Queue.ArrayQueue;

import java.util.function.Supplier;

public class ArrayQueueTest extends AbstractQueueTest<ArrayQueue<Integer>> {

    @Override
    protected Supplier<ArrayQueue<Integer>> getQueueSupplier() {
        return ArrayQueue::new;
    }
}
