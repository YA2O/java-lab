package bzh.ya2o.java.designwithlambdas;

import java.util.function.Supplier;

public class Lazy {

    private volatile Supplier<Heavy> heavy = () -> createAndCacheHeavy();

    public Heavy getHeavy() {
        return heavy.get();
    }

    private synchronized Heavy createAndCacheHeavy() {
        class HeavyFactory implements Supplier<Heavy> {
            final Heavy heavyInstance = new Heavy();
            @Override
            public Heavy get() {
                return heavyInstance;
            }
        }

        if (!HeavyFactory.class.isInstance(heavy)) {
            heavy = new HeavyFactory();
        }
        return heavy.get();
    }


    public static class Heavy {
        Heavy() {
            System.out.println("Create heavy object");
        }
    }
}
