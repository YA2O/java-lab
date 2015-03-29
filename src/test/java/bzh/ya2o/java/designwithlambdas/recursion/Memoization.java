package bzh.ya2o.java.designwithlambdas.recursion;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

public class Memoization {


    @Test
    public void maxProfit5() {
        final Callable<Integer> computation = () -> maxProfit(5);
        final Integer result = execute(computation);
        assertEquals(10, result.intValue());
    }

    @Test
    public void maxProfit44() {
        final Callable<Integer> computation = () -> maxProfit(22);
        final Integer result = execute(computation);
        assertEquals(44, result.intValue());
    }

    static <T> T execute(Callable<T> chunk) {
        final long start = System.nanoTime();
        T t = null;
        try {
            t = chunk.call();
            System.out.println(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
        final long stop = System.nanoTime();
        System.out.printf("%ss%n", (stop - start) / 1000000d);
        return t;
    }

    final List<Integer> prices = Arrays.asList(2, 1, 1, 2, 2, 2, 1, 8, 9, 15);

    int maxMoney(final int rodLength) {
        int maxMoney = rodLength <= prices.size() ? prices.get(rodLength - 1) : 0;
//        IntStream.range(0, prices.size()).
        for (int i = 1; i < rodLength; i++) {
            int priceWhenCut = maxMoney(i) + maxMoney(rodLength - i);
            if (priceWhenCut > maxMoney)
                maxMoney = priceWhenCut;
        }
        return maxMoney;
    }


    int maxProfit(final int rodLength) {

        BiFunction<Function<Integer, Integer>, Integer, Integer> compute = (f, length) -> {
            int profit = (length <= prices.size()) ? (prices.get(length - 1)) : 0;
            for (int i = 1; i < length; i++) {
                int priceWhenCut = f.apply(i) + f.apply(length - i);
                if (priceWhenCut > profit)
                    profit = priceWhenCut;
            }
            return profit;
        };
        return Memoizer.callMemoized(compute, rodLength);
    }

    static class Memoizer {

        static <T, R> R callMemoized(BiFunction<Function<T, R>, T, R> compute, T input) {

            final Function<T, R> memoized = new Function<T, R>() {
                private final Map<T, R> map = new HashMap<>();

                @Override
                public R apply(final T t) {
                    return map.computeIfAbsent(t, key -> compute.apply(this, key));
                }
            };

            return memoized.apply(input);
        }
    }
}

