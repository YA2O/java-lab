package bzh.ya2o.java.designwithlambdas.recursion;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class Recursion {
    public static final int factorialRecursion(final int number) {
        if (number == 1) {
            return 1;
        }
        return number * factorialRecursion(number - 1);
    }

    @Test
    public void fact5() {
        assertEquals(120, factorialRecursion(5));
    }

    @Test
    public void fact20000() {
        try {
            factorialRecursion(20000);
        } catch (StackOverflowError e) {
            fail();
        }
    }
///////////////////
    public static final Integer factorial(final int number) {
        return factorialTailRecursion(1, number).invoke();
    }

    private static final TailCall<Integer> factorialTailRecursion(final int factorial, final int number) {
        if (number == 1) {
            return TailCall.done(factorial);
        }
        return TailCall.call(() -> factorialTailRecursion(number * factorial, number - 1));
    }

    @Test
    public void fact500() {
        final Integer result = factorial(10);
        System.out.println(result);
    }
}
