package chapter06

/**
  * Created by RXLiuli on 2017/5/3.
  */
//组合器: 软件最佳组件抽象
object Combiner {
    def default(): Unit = {
        /**
          * 除了 foreach 方法，所有操作都是纯净的高阶函数，没有副作用，
          * 且都接受函数作为参数，具体工作由该函数完成，如：过滤、转换原始集合中的元素。
          * 这种高阶函数与离散数据中的组合器概念非常接近。我们可以将这些组合器串联起来，
          * 从而用很少的代码完成复杂的功能。对于特定问题，可以将数据和需要实现的行为分离。
          * 这与通常的面向对象编程的方法正好相反，面向对象总是将数据和行为绑定在一起。
          * 在自定义的类中创建所需逻辑的临时实现，是面向对象里的典型做法。
          */

        //复制通过结构共享进行的高效的赋值,复制对原始对象并不会产生影响,从而进行安全的多进程
        //这样，只要对旧版本有一个或多个引用，就可以创建一个 Vector 的 “ 历史 ” 版本。
        //直到对旧版本的引用消失为止，旧版本才会被垃圾回收。
        //由于历史可以一直保留，使用了结构共享的数据结构被称为持久性数据结构。
        val list = List(1, 2, 3, 4, 5)
        println(s"list: $list")
        //此处直接复制了原列表,不用担心对象被修改,因为List默认是不可变的.
        //如果你想修改一个列表,那你只能创建一个新的列表.
        val list2 = 0 +: list
        //此处只增加了一个新的元素0,并没有全部创建,只是共享了list的数据
        //当然这种方法毕竟比不上可变集合在原地对元素修改,然而讽刺的是:
        //这并不意味着可变集合就一定高效!!!
        println(s"list2: $list2")

        /**
          * 注:
          * 传统的面向对象的语言已经增加了不少相同的功能。
          * 它们中的大多数语言现在已经支持集合中的组合器。
          * Java8 为 Java 带来了匿名函数(称为 Lambda 表达式)，
          * 集合类型也增加了高阶函数,从而大大增强了集合的功能。
          */


    }
}