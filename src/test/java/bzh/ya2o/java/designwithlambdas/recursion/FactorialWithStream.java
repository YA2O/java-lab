package bzh.ya2o.java.designwithlambdas.recursion;

import org.junit.Test;

import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class FactorialWithStream {

    static class Fact {
        public final long value;
        public final long fact;

        Fact(final long value, final long fact) {
            this.value = value;
            this.fact = fact;
        }

        public Fact next() {
            return new Fact(value + 1, (value + 1) * fact);
        }
    }

    long fact(long i) {
        return Stream
                .iterate(new Fact(1, 1), Fact::next)
                .skip(i - 1)
                .findFirst()
                .get()
                .fact;
    }

    @Test
    public void fact5() {
        assertEquals(120, fact(5));
    }
}
