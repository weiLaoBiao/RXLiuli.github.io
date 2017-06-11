package chapter11

/**
  * Created by RXLiuli on 2017/5/26.
  */
//覆写抽象和具体字段
object CoverField {
    def default(): Unit = {
        val obj = new AbstractT2 {
            println("In obj: ")
            val value: Int = 10
        }
        //即便field value被实现了,然而在此之前inverse就被计算过了
        println(s"obj.value: ${obj.value}, obj.inverse: ${obj.inverse}")
        /**
          * 正如你所预计的那样， inverse 变量过早被计算了。
          * 尽管没有抛出 除零异常 （ divide-by-zero exception ），
          * 但是编译器仍认为 inverse 值无穷大。
          */
        //使用lazy延迟加载...
        val obj3 = new AbstractT3 {
            println("In obj: ")
            val value: Int = 10
        }
        //在field实现后确定调用时才会计算inverse的值
        println(s"obj.value: ${obj3.value}, obj.inverse: ${obj3.inverse}")

        //解决方案02: 预先初始化
        val obj4 = new { //此处是匿名类?
            //预先初始化块中只允许出现类型定义或具体字段定义
            //            println("In onj4: ")
            /**
              * 在 with AbstractT2 子句执行之前，
              * 我们便已实例化了一个匿名内部类并在代码块中初始化了该内部类的值字段。
              * 这确保了在执行AbstractT2 特征体之前， value 字段已初始化完毕。
              */
            val value = 10 //预先初始化
        } with AbstractT2
        println(s"obj.value: ${obj4.value}, obj.inverse: ${obj4.inverse}")

        //测试: 覆写类字段
        val c1 = new C1
        println(s"name: ${c1.name}, count: ${c1.count}")
        val c2 = new ClassWithC1
        println(s"name: ${c2.name}, count: ${c2.count}") //确实都被覆写了

        //测试: 覆写抽象类型
        val bulkReader: BulkReader = new StringBulkReader("liuli")
        println(s"source: ${bulkReader.read}")

        val bulkReader2: BulkReader2[List[String]] =
            new ListBulkReader2(List("1", "2", "3"))
        println(s"source: ${bulkReader2.read}")

    }

    //region 覆写抽象和具体字段

    //字段初始化之前调用该字段
    trait AbstractT2 {
        //事实上在构造对象时inverse的值就确定了,即便value是抽象的...
        println("In AbstractT2: ")
        val value: Int
        val inverse: Double = 1.0 / value
        println(s"value: $value, inverse: $inverse")
    }

    //那么,有什么解决方案么？
    //解决方案01: lazy value(惰性值)
    trait AbstractT3 {
        println("In AbstractT3: ")
        val value: Int
        //因为没有被调用,所以将不会对inverse进行计算
        lazy val inverse: Double = 1.0 / value
        //然而,我们使用println打印inverse的值,否则会得到和刚才一样的结果
        //        println(s"value: $value, inverse: $inverse")
        //所以,假如某val变量是惰性值,请确保尽可能的推迟对改val值的使用.
    }

    //endregion

    //region 覆写类字段

    class C1 {
        val name = "C1"
        var count = 0
    }

    class ClassWithC1 extends C1 {
        //重写val字段必须使用override关键字
        override val name = "ClassWithC1"
        //使用var则不然,使用override会产生编译错误
        //        override var count = 1
        count = 1
        /**
          * 注: Scala得重写规则:
          * 具体方法和val字段: 使用override def(val) method.
          * 使用字段名重新赋值是用以重写具体var字段.
          * 使用var或者val加字段名是用以实现抽象字段.
          */
    }

    //endregion

    //region 覆写抽象类型

    abstract class BulkReader {
        type In
        val source: In

        def read: String //该方法会读取数据源内容,并返回字符串
    }

    class StringBulkReader(val source: String) extends BulkReader {
        /**
          * 注意: 我们并没有确定抽象类型In的值,也没有显式的实现source
          * /*实际上在我们主构造参数中的source字段被认为是实现了source抽象字段,
          * 并以此推断出了In的类型是String.*/
          * 推论失误...抽象类型In也必须要进行实现.
          */
        type In = String

        def read: String = source //此处实现了read抽象方法
    }

    //当然,实际上是用trait和参数化类型更好
    trait BulkReader2[In] {
        val source: In

        def read: String
    }

    class ListBulkReader2(val source: List[String])
    extends BulkReader2[List[String]] {
        /**
          * 然而,在这儿我们需要显式的确定trait参数化类型的具体类型.
          */
        def read: String = source.mkString(", ")
    }

    //endregion

}
