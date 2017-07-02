package chapter06

import scala.collection.immutable.NumericRange

/**
  * Created by RXLiuli on 2017/4/28.
  */
object FunctionalProgramming {
  def default(): Unit = {
    /**
      * 函数式编程
      * 主要用途:
      * Concurrent(高并发)
      * bigData(大数据)
      * NoBugProgram(无Bug程序)
      * 函数实际上随处可见,不管它们被称为方法(method),程序(produce),还是GOTO,
      * 本质上而言都相当于函数,所有的语言都有函数.
      */
    //数学中的函数
    //        y = sin(x)
    //Scala中的函数
    val f = (i: Int) => i * 2
    println(f(10))
    /** 因为f函数与上下文没有关系,引用是透明的,这带来了两个结果:
      * 1.可以在任何地方调用函数,并确信行为与上下文无关,每次的行为都能确保安全
      * 2.在任何地方表达式计算得出的值和表达式都是可以相互替换的.
      */
    //打印: 下面两句话是完全等价的
    val isEven = (i: Int) => if (f(i) % 2 == 0) "偶数" else "奇数"
    val isEven2 = (i: Int) => if (i * 2 % 2 == 0) "偶数" else "奇数"
    //甚至于函数的参数也是如此
    println(isEven(f(f(10))))
    println(isEven2(f(20)))
    /**
      * 不可变变量
      * "变量"一词在函数式编程中有新的含义.传统的面向对象编程是面向过程的一个子集.
      * 在面向过程中,变量就是可变的(用于存储状态,状态当然要能改变).
      * 而在函数式编程中,变量是不可变的.然而,这并不意味着函数式编程完全没有状态.
      * 你可以用新的对象或新开的栈空间表示状态.
      */
    /**
      * 输入和输出是有副作用的.
      * readline每次都会取出不同的结果,println会传入不同的值,但返回值恒为Unit
      */
    //使用不可变变量递归代替可变循环的例子: 计算阶乘
    def factorial(i: BigInt): BigInt = {
      if (i < 2) i
      else i * factorial(i - 1)
    }

    println(factorial(2))

    //当然.我们可以直接上高阶函数(这确实有点犯规了呀23333)
    def factorial2(i: BigInt): BigInt = {
      if (i < 2) i
      else NumericRange[BigInt](1, 1000, 1).product
    }

    println(factorial2(5))


    //Scala中不支持尾递归函数?
    //        //Scala中的函数(下面定义的实际上是一个尾递归函数)
    //        val foo: (Int => Int) = (i: Int) => //定义有一个Int类型的参数Int返回值的函数
    //            if (i < 10) foo(i * 2) else i //如果i<10就将i*2传入函数中,否则返回i
  }
}
