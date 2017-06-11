package chapter10

/**
  * Created by RXLiuli on 2017/5/22.
  */
//判断对象的相等
object ObjectEquals {
    def default(): Unit = {
        /**
          * 事实上，我从来没有写过我自己的 equals 和 hashCode 方法。
          * 我觉得，对于任何要使用的对象，
          * 如果可能需要测试相等性或需要作为 Map 的键（此时会调用 hashCode ）的话，
          * 它们都应该定义为 case 类！
          * 注: 有的相等方法与其他语言中的相等方法名称相同，但语义有时是不同的！
          * 比如equals比较的便是对象的值,而非对象的引用.如果相比较引用可以使用eq和ne方法.
          */
        println(s"使用 == 比较: ${List(1, 2, 3) == List(1, 2, 3)}")
        println(s"使用 equals 比较: ${List(1, 2, 3) equals List(1, 2, 3)}")
        println(s"使用 eq 比较: ${List(1, 2, 3) eq List(1, 2, 3)}")
        println(s"使用 ne 比较: ${List(1, 2, 3) ne List(1, 2, 3)}")
        //是的,我们可以看出,==和equals比较的都是具体的值,eq和ne比较的是实例的引用

        //equals方法
        case class Commodity(name: String, price: BigDecimal)
        //定义Commity的三个实例
        val commodity = Commodity("瓜子", 20)
        val commodity2 = Commodity("瓜子", 20)
        val commodity3 = Commodity("漫画", 5.0)
        println(s"commodity == commodity2: ${commodity == commodity2}")
        println(s"commodity equals commodity2: ${commodity equals commodity2}")
        println(s"commodity eq commodity2: ${commodity equals commodity2}")
        println(s"commodity ne commodity2: ${commodity equals commodity2}")
        println(s"commodity equals null: ${commodity equals null}")
        //        println(s"null equals commodity2: ${null equals commodity2}")
        //        println(s"null equals null: ${null equals null}")
        println(s"commodity == null: ${commodity == null}")
        println(s"null == commodity2: ${null == commodity2}")
        println(s"null == null: ${null == null}")
        //从上面可以看出来,==比equals更加安全
        /**
          * == 在很多语言中是一个操作符，但在 Scala 中是一个方法。
          * 在 Scala 2.10 中， == 在 Any 中被定义为 final ，
          * 用来代表 equals 。在 2.11 版本中改变了实现，但行为保持不变：
          */

        //数组相等和 sameElements 方法
        //例如比较两个数组，在 Scala 中并不能得出我们认为的显而易见的结果：
        println(s"数组比较 ==: ${Array(1, 2, 3) == Array(1, 2, 3)}") //false
        //所以我们使用sameElements比较数组具体具体的值
        println(s"数组比较 sameElements: ${Array(1, 2, 3) sameElements Array(1, 2, 3)}")
        /**
          * 实际上，我们最好要记住， Array 是我们熟知和喜爱的，
          * 它是可变的原始 Java 数组，与 Scala 库中我们习惯使用的集合类型有着不同的方法。
          * 所以，如果你试图比较数组，考虑一下用序列来比较是否会更好。
          * （不使用序列来代替的一个理由是，你有时真的需要数组相对于序列的优势性能。）
          * 与数组相反，序列（比如 Vector ）的相等性的行为就符合你的期望：
          */
        println(s"序列比较 ==: ${Seq(1, 2, 3) == Seq(1, 2, 3)}")
        println(s"序列比较 sameElements: ${Seq(1, 2, 3) sameElements Seq(1, 2, 3)}")

    }

    //我们来实现一个较为完美的类(失败了呢...)
    //    class Person(name: String, age: Int, sex: Sex.Value) {
    //        def ==(obj: Any): Boolean = this equals obj
    //
    //        //重写
    //        override def toString: String = s"Person($name, $age, $sex)"
    //
    //        override def equals(obj: scala.Any): Boolean = obj match {
    //            case that: Person =>
    //                name == that.name && age == that.age && sex == that.sex
    //            case _ => false
    //        }
    //
    //        //hashCode完全不理解,所以不知道该怎么重写...
    //        override def hashCode(): Int = super.hashCode()
    //
    //    }

    //性别枚举类
    object Sex extends Enumeration {
        type sexState = Value
        //两种定义方式
        val man = Value("男")
        val woman = Value("女")
        //        val 男, 女 = Value //键值一体化
    }

}
