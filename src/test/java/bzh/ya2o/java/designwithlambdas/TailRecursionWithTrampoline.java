package bzh.ya2o.java.designwithlambdas;

import org.junit.Test;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class TailRecursionWithTrampoline {

    @Test
    public void notTrampolining() {
        decrementFrom(20000);
    }

    static int decrementFrom(int val) {
        if (val % 1000 == 0) {
            System.out.println(val);
        }

        if (val == 0) {
            return 0;
        } else {
            return decrementFrom(val - 1);
        }
    }

    public interface Trampoline<T> {
        Trampoline<T> next();

        boolean isDone();

        T getResult();

        static <A> A trampoline(final Trampoline<A> trampoline) {
            return Stream.iterate(trampoline, Trampoline::next)
                    .filter(Trampoline::isDone)
                    .findFirst()
                    .get()
                    .getResult();
        }
    }

    public static class Done<T> implements Trampoline<T> {
        private final T result;

        private Done(final T result) {
            this.result = result;
        }

        @Override
        public boolean isDone() {
            return true;
        }

        @Override
        public T getResult() {
            return this.result;
        }

        @Override
        public Trampoline<T> next() {
            throw new Error("Don't call next() on a terminated trampoline!");
        }
    }

    public static class More<T> implements Trampoline<T> {
        private final Supplier<Trampoline<T>> next;

        private More(Supplier<Trampoline<T>> next) {
            this.next = next;
        }

        @Override
        public boolean isDone() {
            return false;
        }

        @Override
        public T getResult() {
            throw new Error("Calling getResult() on a non-terminated trampoline!");
        }

        @Override
        public Trampoline<T> next() {
            return next.get();
        }
    }

    Trampoline<Integer> decrement(final Integer integer) {
        if (integer % 1000 == 0) {
            System.out.println(integer);
        }
        if (integer == 0) {
            return new Done(0);
        }
        return new More(() -> decrement(integer - 1));
    }

    @Test
    public void trampolining() {
        Trampoline.<Integer>trampoline(decrement(100000000));
    }
}
