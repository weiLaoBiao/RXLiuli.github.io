package chapter06

import scala.annotation.tailrec

/**
  * Created by RXLiuli on 2017/5/1.
  */
//遍历
object Traverse {
    def default(): Unit = {
        /**
          * Scala容器类型的标准遍历方法是foreach,foreach定义在:
          * scala.collection.IterableLike
          * 签名为: trait IterableLike[A]{ //省略细节部分
          * def foreach[U](f:A=>U):Unit={...}
          * }
          * IterableLike的部分子类型可能会重新定义该方法,利用程序的本地信息获得更好的性能
          * 注: foreach是高阶函数,复杂度为O(N),N为元素个数.
          */
        //Seq
        println("Seq使用foreach(List和Vector): ")
        Seq(1, 2, 3, 4).foreach(s => print(s"$s\t"))
        //Map
        println("\nMap使用foreach: ")
        Map(1 -> "one", 2 -> "two", 3 -> "three").foreach {
            //此处实际上是一个偏函数,但这个函数实际上并不偏,因为它可以匹配所有的输入.
            case (k, v) => print(s"$k $v\t")
        }
        //Set
        println("\nSet使用foreach: ")
        Set(1, 2, 3, 4).foreach(s => print(s"$s\t"))

        //那么,foreach是如何实现的呢?
        println("\nforeach实现: ")

        //仅支持Seq遍历的foreach(此处没有使用隐式转换所以只能作为工具方法)
        //注: 因为foreach整体的返回值是Unit,所以f函数的返回值类型并没有什么影响.
        @tailrec
        def myForeach[A, B](seq: Seq[A])(f: A => B): Unit = seq match {
            case head +: tail => f(head); myForeach(tail)(f)
            case Nil => println("None")
        }

        //使用自己实现的foreach,相当自然(得益于柯里化)
        myForeach(Seq(1, 2, 3, 4)) { i =>
            if (i % 2 == 0) println(s"$i is Even!")
            else println(s"$i is odd!")
        }

    }
}
