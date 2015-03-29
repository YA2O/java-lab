package bzh.ya2o.java.designwithlambdas.recursion;

import org.junit.Test;

import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class FactorialWithStateMonad {
    @Test
    public void fact5() {
        assertEquals(120L, factorial(5).longValue());
    }

    static class StateAndValue<S, V> {
        public final S state;
        public final V value;

        StateAndValue(final S state, final V value) {
            this.state = state;
            this.value = value;
        }
    }

    @FunctionalInterface
    interface StateTransition<S, V> {
        StateAndValue<S, V> run(StateAndValue<S, V> s);
    }

    private Long factorial(final int i) {
        final StateAndValue<Long, Long> initial = new StateAndValue<>(1L, 1L);

        final StateTransition<Long, Long> next = (StateAndValue<Long, Long> currentIntAndItsFactorial) ->
                new StateAndValue<>(
                        currentIntAndItsFactorial.state + 1,
                        (currentIntAndItsFactorial.state + 1) * currentIntAndItsFactorial.value
                );

        return Stream.iterate(initial, (s) -> next.run(s))
                .skip(i - 1)
                .findFirst()
                .get()
                .value;
    }


    static class Pair<A, B> {
        public final A _1;
        public final B _2;

        Pair(final A _1, final B _2) {
            this._1 = _1;
            this._2 = _2;
        }
    }

}
