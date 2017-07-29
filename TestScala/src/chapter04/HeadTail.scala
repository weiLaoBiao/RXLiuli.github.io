package chapter04

import scala.annotation.tailrec

/**
  * Created by RXLiuli on 2017/4/23.
  */
object HeadTail {
    def default(): Unit = {
        //+: 操作符,右结合
        //实际过程是先将4追加到Nil中,然后依次向左,凭空构造整个序列
        val list = 1 +: 2 +: 3 +: 4 +: Nil

        /**
          * Scala希望尽可能地支持构造和解构/提取的标准语法.这一点在序列,列表和元组中已然实现了
          * 另外,这些操作都是成对的,互为逆操作
          * 如果用+:方法完成了序列的构造,那么如何用相同的语法形式完成解构呢?
          * 刚才也看了unapply,但unapply知道他们在实例中有多少东西,但序列的元素数量是未知的
          * 为了解决问题,Scala库定义了一个特殊的单例对象,名为+:
          * 这个类型只有一个方法,即编译器用来在case语句进行解构操作的unapply方法
          */
        /**
          * unapply方法签名(简化)
          * def unapply[T, Coll](collection: Coll): Option[(T, Coll)]
          * 头部的类型被推断为T,尾部被推断为某种集合类型Coll.
          * Coll同时也是输入的集合类型,于是,方法返回了一个Option,
          * 其内容为输入集合头部和尾部组成的两元素元组
          */
        //事实上,我们可以写成如下形状,将序列完全解构了....
        @tailrec
        def processSeq2[T](l: Seq[T]): Unit = l match {
            case +:(head, tail) =>
                print(s"$head +:")
                processSeq2(tail)
            case Nil => print("Nil")
        }

        //调用测试
        println(processSeq2(list))
        //当然,case语句中也可使用中缀表达式(推荐),head +: tail
        case class With[A, B](a: A, b: B) //表示With类的参数化类型
        val with1: With[String, Int] = With("Foo", 1) //类型签名写法1
        val with2: String With Int = With("Bar", 2) //类型签名写法2(中缀)
        Seq(with1, with2) foreach {
            case s With i => println(s"$s with $i")
            case w => println(s"Unknown: $w")
        }
        //虽然类型签名可以用中缀表达式书写,不过并不能用类似的语法形式初始化一个值
        //        val w = "one" With 2
        /**
          * List也有一个类似的对象 ::,如果希望你需处理序列中的每个元素,则可使用:+方法
          * 可以让你匹配List最后一个元素,然后向前依次访问各元素
          */
        //例:
        val nonEmptyList = List(1, 2, 3, 4, 5)
        val nonEmptyVector = Vector(1, 2, 3, 4, 5)
        val nonEmptyMap = Map("one" -> 1, "two" -> 2, "three" -> 3)

        //递归
        def reverseSeqToString[T](l: Seq[T]): String = l match {
            case prefix :+ end => reverseSeqToString(prefix) + s" :+ $end"
            case Nil => "Nil"
        }



        //尾递归遍历,注意,尾递归比普通递归多了一个"结果"参数保存遍历的结果
        @tailrec
        def reverseSeqToString2[T](l: Seq[T], result: String = ""): String = l match {
            case prefix :+ end => reverseSeqToString2(prefix, s" :+ $end" + result)
            case Nil => "Nil" + result
        }

        //尝试调用,两个递归实现了相同的结果
        for (seq <- Seq(nonEmptyList, nonEmptyVector, nonEmptyMap.toSeq)) {
            println(reverseSeqToString2(seq))
            println(reverseSeqToString(seq))
        }
        /**
          * 注: 对于List,用于追加元素的:+方法及用与模式匹配的:+方法均需要O(n)的时间复杂度
          * 这两个方法都需要从列表的头部遍历一遍找到最后一个元素
          * 而对于其他某些序列,如Vector,则需要O(1)的时间复杂度
          * List和Vector实现相反?!
          */
        //使用+:也可以构造一个序列
        println(Nil :+ 1 :+ 2 :+ 3 :+ 4 :+ 5)

    }
}
