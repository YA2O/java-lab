package bzh.ya2o.java.designwithlambdas;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

public class FileStuff {
    @Test
    public void apa() {
        Stream.of("usr", "tmp")
                .map(path -> {
                    try {
                        return new File(path).getCanonicalPath();
                    } catch (IOException e) {
                        return e.getMessage();
                    }
                })
                .forEach(System.out::println);
    }

//    @Test
//    public void banan() {
//        Stream.of("usr", "tmp")
//                .map(File::new)
//                .map(uncheck(File::getCanonicalPath))
//                .forEach(System.out::println);
//    }
//
//    static <R, X extends Throwable> Supplier<R>  uncheck(Supplier<R> f) {
//        return new Supplier<R>() {
//            @Override
//            public R get() {
//                try {
//                    return f.get();
//                } catch (X ex) {
//                    return null;
//                }
//            }
//        };
//    }

}
