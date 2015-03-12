package bzh.ya2o.java.designwithlambdas;

import java.math.BigDecimal;
import java.util.function.Function;

public class CalculateNav {
    final private Function<String, BigDecimal> priceFinder;

    public CalculateNav(final Function<String, BigDecimal> priceFinder) {
        this.priceFinder = priceFinder;
    }

    BigDecimal computeStockWorth(final String ticker, final int shares) {
        return priceFinder.apply(ticker).multiply(BigDecimal.valueOf(shares));
    }
}
