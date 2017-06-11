package chapter07

/**
  * Created by RXLiuli on 2017/5/3.
  */
//for推导式: 内部机制
object InternalMechanism {
    def default(): Unit = {
        /**
          * for 推导式的语法实际上是编译器提供的语法糖，
          * 它会调用容器方法 foreach 、 map 、 flatMap 以及 withFilter 方法。
          * 对于那些 非平凡序列 （ nontrival sequence ）而言，
          * 使用 for 推导式比调用相关 API 编写的代码更加易读易写。
          */
        //一个简单的for推导式
        val states = List("Alabama", "Alaska", "Virginia", "Wyoming")
        for (s <- states) println(s)
        states foreach println //使用foreach重写
        /**
          * 在推导式之后存在一个不含 yield 表达式的生成器表达式，
          * 该表达式对应了容器 foreach 方法中执行的表达式。
          */

        //那么,如果我们使用了yield呢?
        println(for (s <- states) yield s.toUpperCase)
        println(states map (_.toUpperCase)) //使用map重写
        /**
          * 生成器表达式中包含了 yield 表达式，该生成器对应了一次 map 操作。
          * 那么 for 推导式是在什么时候利用 yield 操作构造出新的容器的呢？
          * 第一个生成器表达式决定了最终的结果容器类型。通过观察对应的 map 表达式的行为，
          * 你会发现这是合理的。如果将输入的 List 类型修改为 Vector 类型，
          * 你会发现这将生成一个新的 Vector 容器。
          */

        //定义多个生成器看一下....
        println {
            for {
                s <- states
                c <- s
            } yield s"$c-${c.toUpper}"
        }
        println(states flatMap (_ map (c => s"$c-${c.toUpper}"))) //flatMap
        /**
          * 第二个生成器会遍历字符串 s 中的每一个字符。
          * 而设计的 yield 语句将返回各个字符及对应的大写字符，这两个字符通过横线分隔。
          * 如果存在多个生成器，那么除最后一个之外，
          * 其他所有的生成器都会被转化成 flatMap 调用。最后一个生成器对应了一次map调用。
          * 上述代码也将产生 List 对象。你也可以尝试使用其他输入容器类型，
          * 看看输出结果是什么类型。
          */
        //比如说这样
        println {
            for {
                s <- states
                c <- s
                i <- 1 to c.hashCode
            } yield s"$c-${c.toUpper} $i"
        }
        //能够使用容器方法重写,不过可读性奇差!!!
        println {
            states flatMap (_ flatMap (c => (1 to c.hashCode())
            map (i => s"$c-${c.toUpper} $i")))
        }

        //之后,如果我们再加上一个guard(保护式)呢?
        println {
            for {
                s <- states
                c <- s
                if c.isLower
            } yield s"$c-${c.toUpper}"
        }
        println { //filter
            states flatMap (_ filter (_.isLower)
            map (c => s"$c-${c.toUpper}"))
        }
        println { //withFilter
            states flatMap (_ withFilter (_.isLower)
            map (c => s"$c-${c.toUpper}"))
        }
        //filter和withFilter好像没什么区别?
        (1 to 20) filter (_ % 2 == 0) foreach print //遍历了过滤后的集合
        println()
        (1 to 20) withFilter (_ % 2 == 0) foreach print //遍历了过滤后的集合
        println()
        println((1 to 20) filter (_ % 2 == 0)) //输出了新的集合
        println((1 to 20) withFilter (_ % 2 == 0)) //并没有产生新的集合?!
        /**
          * 思考: 什么时候要用withFilter?
          * 当需要在后面继续使用容器方法时,可以使用withFilter而非filter.
          * 因为withFilter不会生成新的集合,所以效率应该较高?
          */

    }
}
