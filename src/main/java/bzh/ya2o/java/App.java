package bzh.ya2o.java;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App {
    public static void main(String[] args) {
        final List<String> list = Arrays.asList("A", "BB", "CCC", "DDDD");

        final Stream<String> stream = list.stream();
        System.out.println(stream);

        final Stream<Integer> integerStream = stream.map(String::length);

        final Stream<Integer> filtered = integerStream.filter(i -> i % 2 == 0);

        final Stream<String> filtered2 = filtered.map(String::valueOf);

        final String result = filtered2.collect(Collectors.joining(", "));

        System.out.println(result);
    }
}
