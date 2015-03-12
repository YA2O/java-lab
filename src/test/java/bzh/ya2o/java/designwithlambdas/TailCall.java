package bzh.ya2o.java.designwithlambdas;

import java.util.stream.Stream;

@FunctionalInterface
public interface TailCall<T> {
    TailCall<T> apply();

    default boolean isComplete() {
        return false;
    }

    default T result() {
        throw new Error("Not implemented!");
    }

    default T invoke() {
        return Stream.iterate(this, TailCall::apply)
                .filter(TailCall::isComplete)
                .findFirst()
                .get()
                .result();
    }

    //////////////
    static <T> TailCall<T> call(final TailCall<T> nextCall) {
        return nextCall;
    }

    static <T> TailCall<T> done(final T value) {
        return new TailCall<T>() {
            @Override
            public TailCall<T> apply() {
                throw new Error("Not implemented!");
            }

            @Override
            public boolean isComplete() {
                return true;
            }

            @Override
            public T result() {
                return value;
            }
        };
    }
}
