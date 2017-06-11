package chapter10

/**
  * Created by RXLiuli on 2017/5/20.
  */
//Product,case类和元组
object ProductCaseTuple {
    def default(): Unit = {

        //你定义的 case 类会混入 scala.Product 特征，它提供了几个关于实例字段的通用方法。
        //例如，对于 Person 的实例：
        case class Person(name: String, age: Int) {
            val address: String = "默认地址"
        }

        val p: Product = Person("Dean", 29)
        println(s"case类主构造函数的字段数量(case类没有参数): ${p.productArity}")
        println(s"case类第一个字段: ${p.productElement(0)}") //Any类型
        //当然,我们也可以选择遍历(需要获得构造器)
        p.productIterator.foreach(x => print(s"$x\t"))
        //尽管已通用方法访问字段非常有用,但由于对各个字段是用的是Any类型,
        //而不是具体类型,这种机制受到了限制.
        //p.productElement(1) * 2 将age * 2
        /**
          * 对于不同的字段数量，也有 Product 的子类型
          * （例如： scala.Product2 ，
          * http://www.scala-lang.org/api/current/scala/Product2.html ，
          * 用于处理两个元素的场景），最大值为 22 。
          * 这些类型为特定的字段添加了一些方法，可以保持该字段的正确类型信息。
          * 例如： Product2[+T1,+T2] 增加了以下方法：
          * trait Product2[+T1, +T2] extends Product {
          * abstract def _1: T1
          * abstract def _2: T2
          * }
          */
        //定义Commodity(商品)case类,并继承trait Product2,实现了抽象方法
        case class Commodity(name: String, price: Double)
        extends Product2[String, Double] {
            override def _1: String = name

            override def _2: Double = price
        }

        val commodity: Product2[String, Double] = Commodity("漫画", 5.0)
        println(s"name length: ${commodity._1.length}")
        println(s"age twice: ${commodity._2 * 2}")
        println(s"commodity value: $commodity")

        /**
          * 这些方法返回了字段的真正类型。
          * 这里的类型参数是协变的，因为 ProductN 特征只用于不可变的类型。
          * 用类似 _1 的方法访问这些字段需要使用对应的类型参数 T1，T1处在协变的位置（即返回值类型）
          */
        //是不是感觉和Tuple(元组)很类似
        val tuple = ("灵梦", 15)
        val tuple2 = Tuple2("灵梦", 15)
        println(tuple._1)
        println(tuple2._2)
        println(s"tuple == tuple2 ? ${tuple == tuple2}") //是的,他们是同一个元组
        tuple.productIterator.foreach(println) //遍历
        /**
          * 回顾一下，这些方法与用来访问元组元素的方法是相同的。
          * 事实上，所有的 TupleN 类型都继承了对应的 ProductN 特征，
          * 并提供了 _1 到 _N方法的具体实现， N 最大可为 22 ：
          * 为什么个数的上限是 22 ？
          * 这个数字的选择有些随意，但你可以合理地认为元组中有 22 个元素无论如何都已经足够多了。
          */
        /**
          * 这对于人类来说确实如此，但不幸的是，存在一个常见的情景需要超出这个数量限制：
          * 保存大的数据 “ 记录 ” 中的字段（或列）。
          * 对于 SQL 或 NoSQL 数据集，包含超过 22 个元素的情况并非罕见。
          * 元组很有用，至少对于小数据的确如此，因为元组可以保持字段（列）的顺序和类型。
          * 所以， 22 个元素的限制是一个问题。
          */
        /*val tuple23 = (
        1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13,
        14, 15, 16, 17, 18, 19, 20, 21, 22, 23)*/
        //是的,没有明显报错,然而编译时依然出错了

        /*
        * 错误信息:
        * Error:(75, 23) object <none> is not a member of package scala
        * val tuple23 = (
         */


        /**
          * 为了方便起见，只要你编译代码，
          * Scala 编译器就会自动导入顶层 Scala 包（名为 scala ）以及在
          * java.lang 包（就像 javac 的）中的定义。
          * 因此，许多常见的 Java 和 Scala 类型都可以不经过明显地导入或提供完整的名称就可以使用。
          * 另外，编译器还导入了 Predef 对象中的一些定义，
          * 它提供了很多实用的定义，其中大部分的定义我们在之前已经讨论了。
          */


    }
}
