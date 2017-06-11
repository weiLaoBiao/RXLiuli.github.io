package chapter07

import scala.util.{Failure, Success, Try}

/**
  * Created by RXLiuli on 2017/5/6.
  */
//Try类型
object TryType {
    def default(): Unit = {
        /**
          * scala.util.Try 的结构与 Either 相似，Try 是一个 sealed 抽象类。
          * 它有两个子类，分别为 Success 类和 Failure 类。
          * Success 类的使用方式与 Right 类相似，它会保存正常的返回值。
          * Failure 则与 Left 类相似，不过 Failure 总是保存 Throwable 类型的值。
          * 类型的签名信息（省略了与本章节内容无关的 trait 定义）:
          * sealed abstract class Try[+T] extends AnyRef {...}
          * final case class Success[+T](value: T) extends Try[T] {...}
          * final case class Failure[+T](exception: Throwable) extends Try[T] {...}
          */
        /**
          * 请注意，不同于 Either[+A, +B] 类，上面这些类中只包含了一种类型参数，
          * 这是因为与 Left 类型相对应的类型是 Throwable 类型。
          * 此外，不同于 Either 类， Try 很明显是非对称的类型。
          * 该类型只包含了一个“ 正常 ”类型（ T ）以及用于错误场景的 java.lang.Throwable类型。
          * 这意味着如果 Try 是一个 Success 类型， Try 就可以定义类似 map 方法这样的组合方法。
          */
        //先使用Try重写之前的for推导式
        def positive(i: Int): Try[Int] = Try {
            assert(i > 0, s"nonpositive number $i") //断言失败抛出异常AssertionError
            i
        }

        println {
            for {
                i1 <- positive(5)
                i2 <- positive(10 * i1)
                i3 <- positive(25 * i2)
                i4 <- positive(2 * i3)
            } yield i1 + i2 + i3 + i4
        }
        println {
            for {
                i1 <- positive(5)
                i2 <- positive(-1 * i1)
                i3 <- positive(25 * i2)
                i4 <- positive(-2 * i3)
            } yield i1 + i2 + i3 + i4
        }

        /**
          * 请留意 positive 方法的具体定义。假如断言失败， Try 代码块会返回 Failure 对象，
          * 该对象封装了可抛出的java.lang.AssertionError 对象。
          * 否则， Try 表达式的结果值将被封装在 Success 中。
          */
        //重写positive函数,并且更明确的表明了Try类型的处理逻辑:
        def positive2(i: Int): Try[Int] = {
            if (i > 0) Success(i)
            else Failure(new AssertionError("assertion failed"))
        }

        //Try类型封装了一个错误类型和正确类型
        val i = 1
        val k = Try[Int] {
            assert(i > 0, s"NumberFormatException: $i")
            i.toInt
        }
        println(k)
        k.foreach(println) //会忽略掉Failure


    }
}
