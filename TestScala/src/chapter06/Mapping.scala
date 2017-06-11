package chapter06

import scala.annotation.tailrec
import scala.io.StdIn

/**
  * Created by RXLiuli on 2017/5/1.
  */
//映射
object Mapping {
    def default(): Unit = {
        /**
          * map方法返回一个与原集合类型大小相同的新集合,
          * 其中的每个元素均由元集合的对应元素转换得到.
          * map方法被定义于scala.collection.TraversableLike,
          * 被大部分集合类型继承.其签名如下:
          * trait TraversableLike[A]{ //省略部分细节
          * def map[B](f:A=>B):Traversable[B]
          * }
          * map的真实签名是:
          * def map[B,That](f:A=>B)(implicit bf:CanBuildFrom[Repr,B,That]):That
          * (隐式转换很恐怖吧23333)
          */
        //我们来定义一下map方法
        //此处为了简化问题使用了Seq.map来实现这个函数,最后再去讨论map的具体实现
        def myMap[A, B](seq: Seq[A])(f: A => B): Seq[B] = seq.map(f)

        //如果交换一下列表
        def myMap2[A, B](f: A => B)(seq: Seq[A]): Seq[B] = seq.map(f)

        //调用测试一下(类型可以从REPL中很容易地得到)
        //intToString类型为Int=>String,它对Seq一无所知.
        val intToString: Int => String = (i: Int) => s"N=$i"
        //flist是一个偏应用函数,类型是Seq[Int] => Seq[String],没有看错,
        //我们用map将一个类型为Int=>String的函数提升为Seq[Int]=>Seq[String]的函数
        val flist: Seq[Int] => Seq[String] = myMap2(intToString)
        val seq = flist(List(1, 2, 3, 4)) //可正常使用
        println(s"seq: $seq")

        /**
          * 通常认为,map方法总是将元素类型为A的集合转为大小相同但元素类型为B的集合,
          * 使用的转换函数f:A=>B对该集合一无所知.现在我们知道了,
          * map也可以被看作一个将函数f:A=>B提升为新函数flist:List[A]=>List[B]的工具.
          * 实际上虽然我们用Seq做例子,但这对有map方法的任何容器类型均适用.
          * 然而...我们并不能直接用Scala标准库的map方法达成该目的,
          * 因为map是实例方法,我们无法用它来为所有的List实例创建提升函数.
          * 或许这便是Scala采用面向对象和函数式混合范式的结果.但这个用法并不常见!
          */
        //map实现,不是尾递归稍微有点麻烦呢
        def myMap3[A, B](seq: Seq[A])(f: (A) => B): Seq[B] = seq match {
            case head +: tail => f(head) +: myMap3(tail)(f)
            case Nil => Nil
        }

        //递归不需要可变性!!!然而...尾递归太难看了
        @tailrec
        def myMap4[A, B](seq: Seq[A], result: Seq[B] = Nil)(f: (A) => B): Seq[B] = seq match {
            case head +: tail => myMap4(tail, result :+ f(head))(f)
            case Nil => result
        }

        //for推导式
        def myMap5[A, B](seq: Seq[A])(f: (A) => B): Seq[B] = {
            var result: Seq[B] = Nil
            for (x <- seq) {
                result = result :+ f(x)
            }
            result
        }

        //调用测试
        println(myMap3(Seq("liuli", "kamisama", "sakura"))(_.length))
        println(myMap4(Seq("liuli", "kamisama", "sakura"))(_.length))
        println(myMap5(Seq("liuli", "kamisama", "sakura"))(_.length))
        //事实证明,大型的迭代,循环还是尾递归都要跪....
        //        println(myMap4(1 to 100000)(_ * 2))

        //for推导式中yield几乎和map一样的效果(两种方法对比)
        val list = List(1, 2, 3, 4, 5)
        val list2 = list.map(_ * 2 - 1)
        val list3 = for (x <- list) yield {
            x * 2 - 1
        }
        println(list2)
        println(list3)
    }
}
