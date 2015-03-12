package bzh.ya2o.java;

public class OuterClass {
    private String s;

    public OuterClass(final String s) {
        this.s = s;
    }

    class InnerClass {
        void method() {
            System.out.println(OuterClass.this.s);
        }
    }

}
