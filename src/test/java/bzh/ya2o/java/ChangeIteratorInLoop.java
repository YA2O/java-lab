package bzh.ya2o.java;

import org.junit.Test;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.fail;

public class ChangeIteratorInLoop {
    @Test
    public void canChangeElementsInList() {
        final List<StupidWrapper<String>> list = new ArrayList();

        final StupidWrapper<String> a = new StupidWrapper<String>("A");
        final StupidWrapper<String> b = new StupidWrapper<String>("B");
        final StupidWrapper<String> c = new StupidWrapper<String>("C");
        list.add(a);
        list.add(b);
        list.add(c);
        final Iterator<StupidWrapper<String>> iterator = list.iterator();
        while (iterator.hasNext()) {
            StupidWrapper<String> next = iterator.next();
            next = new StupidWrapper<String>("K");
            // It is OK
        }
    }
    @Test
    public void cannotAddElementToList() {
        final List<StupidWrapper<String>> list = new ArrayList();

        final StupidWrapper<String> a = new StupidWrapper<String>("A");
        final StupidWrapper<String> b = new StupidWrapper<String>("B");
        final StupidWrapper<String> c = new StupidWrapper<String>("C");
        list.add(a);
        list.add(b);
        list.add(c);
        final Iterator<StupidWrapper<String>> iterator = list.iterator();
        try {
            while (iterator.hasNext()) {
                StupidWrapper<String> next = iterator.next();
                list.add(new StupidWrapper<String>("K"));
            }
            fail();
        } catch (ConcurrentModificationException e) {
            // Expected exception
        }
    }
    @Test
      public void cannotRemoveElementFromList() {
        final List<StupidWrapper<String>> list = new ArrayList();

        final StupidWrapper<String> a = new StupidWrapper<String>("A");
        final StupidWrapper<String> b = new StupidWrapper<String>("B");
        final StupidWrapper<String> c = new StupidWrapper<String>("C");
        list.add(a);
        list.add(b);
        list.add(c);
        final Iterator<StupidWrapper<String>> iterator = list.iterator();
        try {
            while (iterator.hasNext()) {
                StupidWrapper<String> next = iterator.next();
                list.remove(next);
            }
            fail();
        } catch (ConcurrentModificationException e) {
            // Expected exception
        }

    }


    public static class StupidWrapper<T> {
        T value;

        public StupidWrapper(final T value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value.toString();
        }
    }
}
