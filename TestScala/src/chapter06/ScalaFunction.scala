package chapter06

/**
  * Created by RXLiuli on 2017/4/29.
  */
//Scala中的函数式编程
object ScalaFunction {
    def default(): Unit = {
        //高阶函数的算子
        //product等价于reduce(_*_),而sum等价于reduce(_+_)
        println((1 to 10).filter(_ % 2 == 0).map(_ * 2).reduce(_ * _))
        //匿名函数,lambda与闭包
        //对刚才的例子做以下修改
        var factor = 2
        val multiplier = (i: Int) => i * factor
        println((1 to 10) filter (_ % 2 == 0) map multiplier product)
        //改变了factor的值,所以函数multiplier也被修改,这便是所谓的幽灵超距作用么...
        factor = 3
        println((1 to 10) filter (_ % 2 == 0) map multiplier product)

        /**
          * 尽管multiplier是一个不可变函数字面量,当factor改变时,multiplier也跟着改变
          * 在multiplier函数中有两个变量i和factor.i是一个函数的参数,所以每次调用时,
          * i都绑定了一个新的值.
          * 然而,factor并不是参数,而是一个自由变量,是一个当前作用域中某个值的引用.
          * 所以编译器创建了一个闭包,用于包含(或"覆盖")multiplier与它引用的外部变量的上下文信息,
          * 从而也就绑定了外部变量本身.
          * 即使factor处于某个局部作用域中,而我们将multiplier传递给其他作用域,这一机制仍然有效.
          */
        def m1(multiplier2: Int => Int) = {
            (1 to 10) filter (_ % 2 == 0) map multiplier2 product
        }

        def m2: Int => Int = {
            val factor2 = 4
            val multiplier2 = (i: Int) => i * factor2
            multiplier2
        }

        println(m1(m2)) //结果是3922160,factor的影响仍然存在

        /**
          * 函数:
          * 一种具有名或匿名的操作.其代码直到被调用才执行.在函数的定义中,
          * 可能有也可能没有引用外部的未绑定变量
          * Lambda:
          * 一种匿名函数.在它的定义中,可能有也可能没有引用外部的未绑定变量
          * 闭包:
          * 是一个函数,可能匿名或具有名称,在定义中包含了自由变量,函数中包含了环境信息,
          * 以绑定其引用的自由变量
          */
        //当然,在Scala中函数和方法几乎没什么差别
        object Multiplier {
            var factor3 = 2

            def multiplier3(i: Int) = i * factor3
        }
        println((1 to 10) filter (_ % 2 == 0) map Multiplier.multiplier3 product)

        Multiplier.factor3 = 3
        println((1 to 10) filter (_ % 2 == 0) map Multiplier.multiplier3 product)

    }
}
