package chapter07

/**
  * Created by RXLiuli on 2017/5/3.
  */
//for推导式组成元素
object ForElement {
    def default(): Unit = {
        /**
          * for 推导式中包含一个或多个生成器表达式，
          * 外加可选的 保护表达式 （ guard expression ，用于过滤数据）以及值定义。
          * 推导式的输出可以用于 “ 生成 ” 新的容器，
          * 也可以在每次遍历时执行具有副作用的代码块，如打印输出。
          */

        val list = for {
            i <- 0 to 100 by 3 //定义一个Range(范围)[0,100],步长为3
            if i % 5 == 0 //过滤出是5的倍数的数字
            k = if (i % 2 == 0) i * 2 else i //如果是2的倍数便 *2
        } yield s"$i -> $k" //返回的元素
        //返回的是Vector类型?!
        println(list)

        //上面的for推导式可以写成函数调用链
        val list2 = (0 to 100 by 3) filter (_ % 5 == 0) map { i =>
            s"$i -> ${if (i % 2 == 0) i * 2 else i}"
        }
        println(list2)
        /**
          * 思考: 既然有容器的高阶函数,为什么还要有for推导式?
          * for推导式实际上是Scala的语法糖,所有的for推导式都会被转换成高阶函数的调用.
          * 然而,在些时候for推导式比高阶函数的代码可读性更强.
          */
        /**
          * 卫语句 <=> filter
          * not yield <=> foreach
          * yield <=> map
          */

    }
}
