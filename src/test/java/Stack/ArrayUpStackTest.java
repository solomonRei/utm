package Stack;

import org.utm.lab3.Impl.Stack.ArrayUpStack;

import java.util.function.Supplier;

public class ArrayUpStackTest extends AbstractStackTest<ArrayUpStack<Integer>> {

    @Override
    protected Supplier<ArrayUpStack<Integer>> getStackSupplier() {
        return ArrayUpStack::new;
    }

}
