package bzh.ya2o.java;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DangerOfEqualityForBoxedTypes {
    @Test
    public void ok() {
        final Integer i = 127;
        final Integer j = 127;
        assertTrue(i == j);

    }

    @Test
    public void wtf() {
        final Integer i = 128;
        final Integer j = 128;
        assertFalse(i == j);
    }

}
