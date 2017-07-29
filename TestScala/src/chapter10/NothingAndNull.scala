package chapter10

/**
  * Created by RXLiuli on 2017/5/20.
  */
//闲话Nothing(以及null)
object NothingAndNull {
    def default(): Unit = {
        /**
          * Nothing（http://www.scala-lang.org/api/current/#scala.runtime.Nothing$)
          * 和 Null（http://www.scala-lang.org/api/current/#scala.runtime.Null$)
          * 是位于类型系统底层的两个特殊类型。
          * 其中， Nothing 是所有其他类型的子类，而 Null 是所有引用类型的子类。
          */
        val s: String = null //使用null为String类型的变量赋值
        val list = List[Nothing]()
        //注: null不能调用getClass方法,会产生编译错误
        println(s"s: $s, type: ${/*s.getClass*/}")
        println(s"list: $list, type: ${list.getClass}")
        /**
          * Null 对于大多数语言而言是个熟悉的概念。
          * 尽管这些语言通常并没有定义 Null 类型，仅仅定义了关键字 null ，
          * 用于向引用变量赋值，表示该变量实际上没有值。 Null 在编译器里的实现相当于以下声明：
          * abstract final class Null extends AnyRef
          */
        //即便是简单的调用toString方法都会产生运行错误
        //        println(s"null: ${null.toString}")
        /**
          * 为何 Null 可以同时为 final 和 abstract 呢？
          * 该声明不允许子类派生以及创建实例，但运行时环境提供了一个实例，
          * 就是我们熟悉和喜爱的null （这 …… 这 …… ）。
          * Null 被明确定义为 AnyRef 的一个子类型，但它也是所有 AnyRef 类型的子类型。
          * 这是类型系统允许你用 null 给任何引用类型赋值的正常做法。
          * 另一方面，因为 null 不是 AnyVal 的子类型，所以不可以用 null 给 Int 赋值。
          * 于是， Scala 的 null 与 Java 的 null 完全相同，
          * 因为它必须与 Java 的 null 共存于 JVM 。
          * 否则， Scala 会破坏 null 的概念，引起很多潜在的 bug 。
          */
        /**
          * 与此相反， Nothing 在 Java 中没有类似的概念，但它填补了存在于 Java 类型系统中的空白。
          * Nothing 在编译器里的实现相当于以下声明：
          * abstract final class Nothing extends Any
          * 不同于 Null ， Nothing 没有实例。
          * 但 Nothing 为类型系统提供了两种功能，这有助于实现健壮且类型安全的设计。
          */
        //编译错误: 无法实例化抽象类
        //        println(s"nothing: ${new Nothing}")
        /*
        * cannot:不能 instantiated:实例化
        * Error:(47, 30) class Nothing is abstract; cannot be instantiated
        * println(s"nothing: ${new Nothing}")
        * */
        /**
          * 我们结合熟悉的 List[\+A]
          * http://www.scala-lang.org/api/current/scala/collection/immutable/List.html
          * 类来说明第一个功能。现在我们知道 List 中的 A 是协变的，
          * 所以 List[String] 是 List[Any] 的一个子类（因为 String 是 Any 的子类 ) 。
          * 进而可以将 List[String] 的实例赋值给List[Any] 类型的变量。
          */
        /**
          * Scala 为空列表声明了一个专用的类型 Nil
          * http://www.scala-lang.org/api/current/scala/collection/immutable/Nil$.html
          * 在 Java 中， Nil 必须是像 List 那样的参数化类型，
          * 但是很不幸，根据定义 Nil 不包含任何元素，
          * 所以 Nil[String] 和 Nil[Any] 是不同的类型（实际上却并无区别）。
          */
        //Scala通过Nothing解决了这个问题,Nil的声明如下:
        //object Nil extends List[Nothing] with Product with Serializable
        /**
          * 需要注意的是， Nil 是一个继承了 List[Nothing] 的对象，
          * 它只需要一个实例，因为它没有携带任何 “ 状态 ” （元素）。
          * 由于 List 的类型参数是协变的，对于任意类型 A ， Nil 是所有 List[A] 的子类型。
          * 所以，我们不需要分开 Nil[A] 实例，一个实例就够了。
          * Nothing 和 Null 被称为底部的类型，因为它们处于类型层次结构的底端。
          * 也因为如此，它们是所有（或者大多数）类型的子类。
          */
        val emptyListString: List[String] = Nil
        println(emptyListString.getClass)
        //Nothing也可以用来终止程序
        /**
          * Nothing 的其他用途是表示终止程序，如抛出一个异常。
          * 回顾我们在 8.9 节见过的 ??? 方法。我们可以临时调用 ??? 方法来定义其他的方法，
          * 使得方法定义完整，并通过编译。但如果调用该方法，就会抛出异常。以下是 ??? 的定义：
          * object Predef {
          * def ??? : Nothing = throw new NotImplementedError
          * }
          */
        //由于 ??? “ 返回 ” 了 Nothing ，所以它可以被任何其他函数调用，无论该函数的返回值是什么。
        //以下是一个病态的例子：
        def foo: Nothing = throw new NotImplementedError //是的,我们确实可以这样返回
        def m(l: List[Int]): List[Int] = l map (_ => ???)

        //        m(List(1, 2, 3)) //会抛出异常
        //sys.exit用于强制退出程序,和下面Java中的System.exit类似,不过sys.exit返回的是Nothing
        //        println(s"result: ${sys.exit(1)}")
        //        System.exit(2) //Java中的强制退出
        //为什么在说Nothing的时候讲到sys.exit呢? 因为sys.exit返回Nothing
        //所以,我们可以将sys.exit作为返回值
        //下面是一个处理命令行参数的方法:
        object CommandArgs {
            val help =
                """
                  |usage: java ... objectsystem.CommandArgs arguments
                  |where the allowed arguments are:
                  | -h | --help Show help
                  | -i | --in | --input path Path for input
                  | -o | --on | --output path Path for input
                  |""".stripMargin

            //退出方法
            def quit(message: String, status: Int): Nothing = {
                //判断消息是否为空,不为空就打印一下
                if (message.length > 0) println(message)
                println(help) //打印帮助信息
                sys.exit(status) //终止程序,默认为0
            }

            case class Args(inputPath: String, outputPath: String)

            def parseArgs(args: Array[String]): Args = {
                //使用了递归
                def pa(args2: List[String], result: Args): Args = args2 match {
                    case Nil => result
                    case ("-h" | "--help") +: Nil => quit("", 0)
                    case ("-i" | "--in" | "--input") +: path +: tail =>
                        pa(tail, result.copy(inputPath = path))
                    case ("-o" | "--out" | "--output") +: path +: tail =>
                        pa(tail, result.copy(outputPath = path))
                    case _ => quit(s"Unrecognized argument ${args2.head}", 1)
                }

                val argz = pa(args.toList, Args("", ""))
                if (argz.inputPath == "" || argz.outputPath == "")
                    quit("Must specify input and output paths.", 1)
                argz
            }

            def mainFunction(args: Array[String]): Unit = {
                val argz = parseArgs(args)
                println(argz)
            }
        }


        //region 默认
        /**
          * 1
          * 不要经常看手册
          * XML为什么不换成json
          */
        //2.错误提示
        /**
          * 3.
          * 自信
          * 不要卡住
          * 流畅性
          * 速度
          */
        /**
          * 4
          * 经常看小抄
          * 不流畅
          * 完全是念了。。。
          */
        /**
          * 5
          * 看手册问题。。。
          * 搜索正则
          * 流畅性一般
          */
        //endregion

    }
}
