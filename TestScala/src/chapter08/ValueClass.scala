package chapter08

/**
  * Created by RXLiuli on 2017/5/8.
  */
//价值类
object ValueClass {
    def default(): Unit = {
        /**
          * 正如我们所看到的， Scala 中经常引入包装类型来实现新类型，这也被称为扩展方法。
          * 不幸的是，对值类型的包装，会将值类型变成引用类型，从而失去了原生类型的良好性能。
          * Scala 2.10 推出了一个解决方案，称为 价值类 （ value class ），
          * 和一个附带的特性，称为 通用特征 （ universal trait ）。
          * 这些类型限制了可声明的范围，但同样带来了好处：避免封装分配在堆上。
          */
        class Dollar(val value: Float) extends AnyRef { //Dollar:美元($)
            override def toString: String = f"$value%.2f"
        }

        val benjamin = new Dollar(100)
        println(benjamin)

        /**
          * 要成为一个有效的价值类，必须遵守以下的规则。
          * (1) 价值类有且只有一个公开的 val 参数（对于 Scala 2.11 ，该参数也可以是不公开的）。
          * (2) 参数的类型不能是价值类本身。
          * (3) 价值类被参数化时，不能使用 @specialized 标记。
          * (4) 价值类没有定义其他构造器。
          * (5) 价值类只定义了一些方法，没有定义其他的 val 和 var 变量。
          * (6) 然而，价值类不能重载 equals 和 hashCode 方法。
          * (7) 价值类定义没有嵌套的特征、类或对象。
          * (8) 价值类不能被继承。
          * (9) 价值类只能继承自通用特征。
          * (10) 价值类必须是对象可引用的一个顶级类型或对象的一个成员。
          * 这是一个长长的清单，不过，当我们不遵守规则时，编译器会抛出错误消息。
          * 注:
          * 由于 Scala 丰富的类型系统，并非所有的类型都可以像在 Java 中那样，
          * 在正常的变量和方法声明中引用（不过，我们到目前为止看到的所有例子都可以正常进行引用）。
          * 在第 14 章中，我们将* 探索新的类型，并学习类型不能被引用的相关规则。
          */
        //本例中， Dollar 在编译时是一个外部类型。在运行时，该类型是被包装的类型，即 Float 。
        //通常，被包装的类型是 AnyVal 的子类型之一，但并不是必须如此。
        //如果换成引用类型，我们仍然可以受益于内存不在堆上分配的优势。
        {
            //之所以放到花括号中是因为下面要重写
            class USPhoneNumber(val s: String) extends AnyRef {
                //重载toString
                override def toString: String = {
                    val digs = digits(s)
                    val areaCode = digs.substring(0, 3)
                    val exchange = digs.substring(3, 6)
                    val subnumber = digs.substring(6, 10)
                    s"($areaCode) $exchange-$subnumber"
                }

                //移除所有的非数字(\D代表正则表达式中\d的反义,即非数字)
                private def digits(str: String): String =
                    str.replaceAll("""\D""", "")
            }
            println("139 19311fsd 1 23 ".replaceAll(" ", "")) //移除了空格
            println("139 19311 1 23 ".replaceAll("""\D""", "")) //移除了所有的非数字字符
        val phone = new USPhoneNumber("139 193 1112 ")
            println(s"电话号码的包装实例: $phone")
            val phoneNumber = phone.toString //将格式化后的电话号码保存起来
            println(s"phoneNumber: $phoneNumber")
        }
        /**
          * 价值类可以是一个 case 类，但是许多生成的额外方法和伴随对象不大可能被用到，
          * 导致产生的 class 文件就白白浪费了一些空间。
          * 一个通用特征具有以下特性:
          * (1) 它可以从 Any 派生（而不能从其他通用特征派生）。
          * (2) 它只定义方法。
          * (3) 它没有对自身做初始化。
          */

        {
            //USPhoneNumber改进版:
            trait Digitizer extends Any {
                //分离出了清除电话号码中的杂质的功能
                def digits(s: String): String = s.replaceAll("""\D""", "")
            }
            trait Formatter extends Any {
                //分离出了电话格式化的功能
                def format(areaCode: String, exchange: String, subnumber: String):
                String = s"($areaCode) $exchange-$subnumber"
            }
            class USPhoneNumber(val s: String)
            extends AnyRef with Digitizer with Formatter {
                override def toString: String = {
                    val digs = digits(s) //也许,这就是组合
                    val areaCode = digs.substring(0, 3)
                    val exchange = digs.substring(3, 6)
                    val subnumber = digs.substring(6, 10)
                    //Formatter 特征按我们想要的格式对电话号码进行格式化。
                    //如果不需要这种格式,也可以换另外一种格式,而我们只需要增加一个格式化的方法.
                    format(areaCode, exchange, subnumber)
                }
            }
            //测试调用
            //此处,我们也创建了USPhoneNumber类型的实例呀,难道说并没有?
            val phone = new USPhoneNumber("139 193 1112 ")
            println(s"电话号码的包装实例2: $phone")
            val phoneNumber = phone.toString //将格式化后的电话号码保存起来
            println(s"phoneNumber2: $phoneNumber")
            //使用体验完全一样,但是,耦合度相当低,同样低的还有效率...是为了可以轻易地改变和重组?
            /**
              * Formatter 实际上解决了一个设计上的问题。
              * 我们可能要给 USPhoneNumber 指定另一个参数作为格式字符串，
              * 或需要一些机制去配置toString 的格式，因为流行的格式可能有很多。
              * 但是，我们只允许传递一个参数给 USPhoneNumber 。
              * 针对这种情况，我们可以在通用特征中去配置我们想要的格式！
              * 然而，由于 JVM 的限制，通用特征有时会触发实例化（即实例的内存分配于堆中）。
              * 这里将需要实例化的情况总结如下:
              * (1) 当价值类的实例被传递给函数作参数，而该函数预期参数为通用特征且需要被实例实现。
              * 不过，如果函数的预期参数是价值类本身，则不需要实例化。
              * (2) 价值类的实例被赋值给数组。
              * (3) 价值类的类型被用作类型参数。
              */
            //例如：当用 USPhoneNumber 调用以下方法时，我们会创建 USPhoneNumber 的一个实例：
            //接收一个Digitizer trait类型,并且调用trait中的digits函数
            //然而,trait不能实例化,所以使用混入trait的类的实例去调用,然而这是动态决定的.
            //没有人知道方法的参数是哪个集成类型的实例.
            def toDigits(d: Digitizer, str: String) = d.digits(str)

            //这儿发生了什么?方法需要一个Digitizer类型的参数,而我们传入了一个USPhoneNumber实例!
            val digs = toDigits(new USPhoneNumber("987-654-3210"), "123-Hello!-456")
            println(digs)
            //没错,我们可以声明一个trait的实例(虽然不能实例化就是了),然后用其的子类为其赋值
            val test: Digitizer = new USPhoneNumber("") //类的参数无所谓
            println(s"测试: ${test.digits("1234567890")}")

            //同样,当用USPhoneNumber调用以下参数化类型的方法时,也不得不产生USPhoneNumber的实例:
            def show[T](t: T): Unit = println(t.toString)

            //调用了show函数,并且传入了一个USPhoneNumber类型的实例(实例化了)
            show(new USPhoneNumber("1234567890")) //格式化了
            /**
              * 总之，价值类提供了一个低开销的技术，用于定义扩展方法，
              * 并为类型定义有意义的领域名称（如 Dollar ），这利用了被包装值的类型安全性。
              */
        }
        /**
          * “ 值类型 ” 一词指代 Short 、 Int 、 Long 、 Float、 Double 、 Boolean 、
          * Char 、 Byte 和 Unit 等 Scala 早就有的类型。
          * 而 “ 价值类 ” 一词指代继承 AnyVal 的自定义类。
          */

        /**
          * 思考: 集成和组合,到底要选择哪个?
          */

    }
}
