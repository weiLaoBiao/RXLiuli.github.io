package TestJavaClass;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestJava extends Date {
    private void test() {
        System.out.println(super.getClass().getName());
    }

    public static void myDefault() {
        new TestJava().test();
        List<String> list = new ArrayList<String>();
    }
}