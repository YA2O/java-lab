package bzh.ya2o.java.designwithlambdas;

import org.junit.Test;

import java.awt.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public class FunctionCompositionTest {

    static class Camera {
        private Function<Color, Color> filter;

        public Camera() {
            this.filter = Function.identity();
        }

        Color process(Color input) {
            return filter.apply(input);
        }


        void setFilters(Function<Color, Color>... filters) {
            this.filter = Stream.of(filters)
                    .reduce((firstFilter, nextFilter) -> firstFilter.andThen(nextFilter))
                    .orElse(Function.identity());
//                    .orElseGet(Function::identity);
            //                    .orElse(color -> color);
            //                    .orElseGet(() -> color -> color);
        }


    }

    @Test
    public void filter() {
        final Camera camera = new Camera();
        Consumer<String> printProcessed = printInfo -> System.out.println(String.format("With %s: %s", printInfo, camera.process(new Color(200, 100, 200))));
        printProcessed.accept("no filter");
    }
}
