package bzh.ya2o.java.designwithlambdas;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class CalculateNavTest {

    @Test
    public void computeStockWorth() {
        final CalculateNav calculateNav = new CalculateNav(ticker -> {
            assertEquals("AAA",ticker);
            return new BigDecimal("6.01");
        });
        assertEquals(0, calculateNav.computeStockWorth("AAA", 1000).compareTo(new BigDecimal("6010.00")), 0.001);
    }

    @Test
    public void computeStockWorth_integrationYahoo() {
        final CalculateNav calculateNav = new CalculateNav(YahooFinance::getPrice);
        final BigDecimal google = calculateNav.computeStockWorth("GOOG", 1);
        System.out.println(google);
    }
}
