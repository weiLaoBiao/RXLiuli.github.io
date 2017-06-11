package chapter06

import scala.annotation.tailrec

/**
  * Created by RXLiuli on 2017/4/29.
  */
//递归
object Recursion {
    def default(): Unit = {
        //在函数式编程中,递归比在命令式编程中更重要.
        //递归式实现循环的唯一方法,因为你无法修改循环变量.
        //以下是使用循环实现的阶乘
        def factorial(i: Int): BigInt = {
            if (i < 2) i
            var result = 1
            for (k <- 2 to i) {
                result *= k
            }
            result
        }

        println(factorial(10))

        //普通递归实现(有可能栈溢出,不信你把参数改成10000试试)
        def factorial2(i: BigInt): BigInt =
            if (i < 2) i else i * factorial2(i - 1)

        println(factorial(10))
        //尾部调用和尾部调用优化
        /**
          * 有一种特殊的递归被称为尾递归.在递归中,函数可以调用自身,
          * 并且该调用时函数的最后一个("尾部")操作.
          * 尾递归可以消除潜在的栈溢出风险,同时消除了函数调用开销而提升了效率.
          */
        //计算阶乘尾递归改进版(默认参数为结果,不用修改即可使用)
        @tailrec
        def factorial3(i: BigInt, accumulator: BigInt = 1): BigInt = {
            if (i < 2) accumulator
            else factorial3(i - 1, i * accumulator)
        }

        println(factorial3(10))
        /**
          * 注: 当一个调用了自身的方法,有可能被子类型中的同名方法覆盖时,尾递归是无效的.
          * 所以尾递归方法必须用private或final关键字声明,或者将其嵌套其他方法里面
          */

        //尾递归的trampoline优化
        /**
          * trampoline(原意为"蹦床")是指通过依次调用各个函数完成一系列函数之间的循环,
          * 暗喻在多个函数之间反复来回调用.
          */
        //以下是确定一个数是否为偶数的方法,但效率不高(因为isEven和isOdd互相引用)
        import scala.util.control.TailCalls._

        def isEven(xs: List[Int]): TailRec[Boolean] =
            if (xs.isEmpty) done(true) else tailcall(isOdd(xs.tail))

        def isOdd(xs: List[Int]): TailRec[Boolean] =
            if (xs.isEmpty) done(false) else tailcall(isEven(xs.tail))

        for (i <- 1 to 5) {
            val even = isEven((1 to i).toList).result
            println(s"$i is even? $even")
        }

    }
}
