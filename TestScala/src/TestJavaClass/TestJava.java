package TestJavaClass;


import java.math.BigDecimal;
import java.util.ArrayList;

public class TestJava {

    public static void myDefault() {
        TestDog testDog = null;
        testDog.show(); //还是发生了空指针异常
        //region 废弃代码

//        int i = 1 > 0 ? 1 :  getInt();

//        for (int i = 2; i < 365; i++) {
//            BigDecimal bd = new BigDecimal(0);
//            for (int j = 1; j < i; j++) {
//
//            }
//
//        }


//        paserXML();

//        TestJvm<? extends C> tv = new TestJvm<C>();
////        tv = new TestJvm<CSuper>();
//        tv = new TestJvm<CSub>();
//        TestJvm<? super C> tv2 = new TestJvm<C>();
//        tv2 = new TestJvm<CSuper>();
//        tv2 = new TestJvm<CSub>();


//        List<String> list = new ArrayList<>();
//        list.add("a");
//        list.add("ab");
//        list.add("abc");
//        list.add("abcd");
//        list.add("abcde");
//        //遍历
//        list.forEach(s -> System.out.print(s + "\t"));
//        //转大写(映射)
//        list.replaceAll(String::toUpperCase);
//        System.out.println();
//        list.forEach(s -> System.out.print(s + "\t"));
//        System.out.println();
//        //去掉长度是偶数的元素(过滤)
//        list.removeIf(s -> s.length() % 2 == 1);
//        list.forEach(s -> System.out.print(s + "\t"));


        //        S s = new A(); //可以声明接口类型,然后用实现接口的类去赋值
//        s.show();

//        System.out.println(Singleton.INSTANCE);
//        Singleton.INSTANCE.whateverMethod();
//
//        Singleton1 instance = Singleton1.getInstance();
//        instance.whateverMethod();

        //endregion
    }

    class TestDog {
        public void show() {
            System.out.println("null 也可以调用？");
        }
    }

    static void show() {
    }

    //    @Contract(pure = true)
    static int getInt() {
        return 1;
    }

    //region 正在使用的方法
    public static void paserXML() {
        /*
        Element element = null;
        // 可以使用绝对路劲
        File f = new File("D:\\Text\\Scala\\TestText\\TestXML.xml");

        // documentBuilder为抽象不能直接实例化(将XML文件转换为DOM文件)
        DocumentBuilder db = null;
        DocumentBuilderFactory dbf = null;
        try {
            // 返回documentBuilderFactory对象
            dbf = DocumentBuilderFactory.newInstance();
            // 返回db对象用documentBuilderFatory对象获得返回documentBuildr对象
            db = dbf.newDocumentBuilder();

            // 得到一个DOM并返回给document对象
            Document dt = db.parse(f);
            // 得到一个elment根元素
            element = dt.getDocumentElement();
            // 获得根节点
            System.out.println("根元素：" + element.getNodeName());

            // 获得根元素下的子节点
            NodeList childNodes = element.getChildNodes();

            // 遍历这些子节点
            for (int i = 0; i < childNodes.getLength(); i++) {
                // 获得每个对应位置i的结点
                Node node1 = childNodes.item(i);
                if ("Account".equals(node1.getNodeName())) {
                    // 如果节点的名称为"Account"，则输出Account元素属性type
                    System.out.println("\r\n找到一篇账号. 所属区域: " + node1.getAttributes().getNamedItem("type").getNodeValue() + ". ");
                    // 获得<Accounts>下的节点
                    NodeList nodeDetail = node1.getChildNodes();
                    // 遍历<Accounts>下的节点
                    for (int j = 0; j < nodeDetail.getLength(); j++) {
                        // 获得<Accounts>元素每一个节点
                        Node detail = nodeDetail.item(j);
                        if ("code".equals(detail.getNodeName())) // 输出code
                            System.out.println("卡号: " + detail.getTextContent());
                        else if ("pass".equals(detail.getNodeName())) // 输出pass
                            System.out.println("密码: " + detail.getTextContent());
                        else if ("name".equals(detail.getNodeName())) // 输出name
                            System.out.println("姓名: " + detail.getTextContent());
                        else if ("money".equals(detail.getNodeName())) // 输出money
                            System.out.println("余额: " + detail.getTextContent());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
    }

    public static void arrayVariance() {
        /**
         * Java中的数组是协变的.
         * 编译器允许我们对Array[Number]类型的引用赋一个Array[Integer]类型的值
         * 因为Number是Integer的父类,所以Array[Number]是Array[Integer]的父类.
         * 然而,实际上"知道"它只能接受Integer类型的值,
         * 所以它会抛出一个运行异常,破坏静态类型的检查机制.
         * 注: 尽管Scala中的数组是对Java数组的包装,
         * 但scala.Array的类型参数是非变异的,所以它可以防止这个漏洞发生.
         */
        Number[] arr2 = new Integer[]{1, 2, 3, 4};
        arr2[0] = 1.5; //编译期不会出错,运行时将会产生ArrayStoreException(数组存储异常)
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
//        ArrayList<Number> list2 = list; //直接编译错误23333
    }
    //endregion

    //region 废弃方法

    //泛型
//    static <T> void foo(T t) {
//        System.out.println("result: " + t);
//    }

    //endregion

}

//region 废弃类型

//class CSuper {
//}
//
//class C extends CSuper {
//}
//
//class CSub extends C {
//}

//interface S {
//    public void show();
//}
//
//class A implements S {
//    public void show() {
//        System.out.println("打印了");
//    }
//}

//abstract class f implements s {
//    public void foo() {
//
//    }
//}
//
//class Singleton1 {
//    private final static Singleton1 INSTANCE = new Singleton1();
//
//    private Singleton1() {
//    }
//
//    static Singleton1 getInstance() {
//        return INSTANCE;
//    }
//
//    //成员方法
//    public void whateverMethod() {
//        System.out.println("hello Java");
//    }
//}
//
//enum Singleton {
//    INSTANCE;
//
//    //成员方法
//    public void whateverMethod() {
//        System.out.println("hello Java");
//    }
//}

//endregion