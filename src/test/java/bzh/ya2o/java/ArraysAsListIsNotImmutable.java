package bzh.ya2o.java;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ArraysAsListIsNotImmutable {

    @Test(expected = UnsupportedOperationException.class)
    public void add_throwsAtRuntime() {
        final List<String> list = Arrays.asList("A");
        list.add("B"); // throws!
    }

    @Test
    public void set_breaksImmutability() {
        final List<String> list = Arrays.asList("A");
        list.set(0, "B");
        assertEquals(Arrays.asList("B"), list);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void createImmutableList() {
        final List<String> list = Collections.unmodifiableList(Arrays.asList("A"));
        list.set(0, "B"); // Throws!
    }

    @Test(expected = UnsupportedOperationException.class)
    public void createImmutableList_guava() {
        final List<String> list = ImmutableList.of("A");
        list.set(0, "B"); // Throws!
    }

    @Test(expected = UnsupportedOperationException.class)
    public void createImmutableList_fluentGuava() {
        final List<String> list = ImmutableList.<String>builder()
                .add("A")
                .build();
        list.set(0, "B"); // Throws!
    }
}
