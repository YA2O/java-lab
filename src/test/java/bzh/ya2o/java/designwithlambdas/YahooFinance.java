package bzh.ya2o.java.designwithlambdas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;

public class YahooFinance {
    static BigDecimal getPrice(final String ticker) {
        try {
            final URL url = new URL("http://ichart.finance.yahoo.com/table.csv?s=" + ticker);
            final BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

            final String data = reader.lines().skip(1).findFirst().get();
            final String[] items = data.split(",");
            return new BigDecimal(items[items.length - 1]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
