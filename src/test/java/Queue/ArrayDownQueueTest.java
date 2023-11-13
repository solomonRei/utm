package Queue;

import org.utm.lab3.Impl.Queue.ArrayDownQueue;

import java.util.function.Supplier;

public class ArrayDownQueueTest extends AbstractQueueTest<ArrayDownQueue<Integer>> {

    @Override
    protected Supplier<ArrayDownQueue<Integer>> getQueueSupplier() {
        return ArrayDownQueue::new;
    }

}
