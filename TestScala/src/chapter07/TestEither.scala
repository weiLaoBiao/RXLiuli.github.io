package chapter07

import scala.io.StdIn

/**
  * Created by RXLiuli on 2017/5/4.
  */
//Either: Option类型的逻辑扩展
object TestEither {
    def default(): Unit = {
        /**
          * 我们注意到 Option 类型有一个弊端，即None对象不能提供任何信息告诉我们为什么不返回值
          * 与 Either 的英文字面意思一样， Either 是一类能且只能持有两种事物中一种的容器。
          * 换言之， Option 能持有 0 个或 1 个元素，而 Either 则持有这个或那个元素项。
          */
        /**
          * Either 概念的产生时间早于 Scala 。很长时间以来它被认为是抛出异常的一种替代方案。
          * 为了尊重历史习惯，当 Either 用于表示错误标志或某一对象值时，
          * Left 值用于表示错误标志，如：信息字符串或下层库抛出的异常；
          * 而正常返回时则使用 Right 对象。
          * 很明显， Either 可以用于任何需要持有某一个或另一个对象的场景中，
          * 而这两个对象的类型可能不同。
          */
        //那么,我们先重写一下前面的实例:
        def positive(i: Int): Either[String, Int] =
        if (i < 0) Left(s"nonpositive number $i") else Right(i)

        println {
            for {
                i1 <- positive(5).right
                i2 <- positive(10 * i1).right
                i3 <- positive(25 * i2).right
                i4 <- positive(2 * i3).right
            } yield i1 + i2 + i3 + i4
        }
        //正常的输出
        println {
            for {
                i1 <- positive(5).right
                i2 <- positive(-1 * i1).right
                i3 <- positive(25 * i2).right
                i4 <- positive(-2 * i3).right
            } yield i1 + i2 + i3 + i4
        }
        //Left(nonpositive number -5): 输出了错误的原因.不过,我们只能看到第一个错误
        //稍微对Either做一些实验
        val l: Either[String, Int] = Left("boo")
        println(s"l left: ${l.left},right: ${l.right}")
        val r: Either[String, Int] = Right(12)
        println(s"r left: ${l.left},right: ${l.right}")
        //如果一个类型中包含两个参数,那么它可以使用中缀表示法表示类型说明.因此我们也可以这样声明:
        val l2: String Either Int = Left("boohoo")
        //为了更好地表示类型,我们可以使用类型别名:
        type Or[A, B] = Either[A, B]
        val l3: String Or Int = Left("better?")
        /**
          * Either 本身并未定义组合方法 map 、 fold 等，我们只能访问 Either.left
          * 或 Either.right 中的组合方法。之所以如此，
          * 是因为我们的组合方法只接受单个函数参数，但我们需要为 Either 容器指定两个函数参数。
          * 当 Either 值为 Left 值时调用一个，而 Either 值为 Right值时调用另外一个。
          * Either 对象提供了 left 和 right 方法，
          * 这两个方法会构建出一个提供组合方法的 投影对象 （ projection ）：
          */
        println(s"l left: ${l.left},right: ${l.right}")
        println(s"r left: ${l.left},right: ${l.right}")
        //Either.RightProjection 对象与 Either.LeftProjection 相同
        println(l.left.map(_.length)) //Either[Int,Int] = Left(3)
        println(r.left.map(_.length)) //Either[Int,Int] = Right(12)
        println(l.right.map(_.toDouble)) //Either[String,Double] = Left(boo)
        println(r.right.map(_.toDouble)) //Either[String,Double] = Right(12.0)
        //注意返回值类型由于 l.left.map(_.size) 方法将 String 对象转化成了 整型（Int）对象，
        //新生成的 Either 对象类型变成Either[Int,Int] 。
        //由于该函数不会对 Right[Int] 对象进行操作，因此第二个类型参数保持不变。
        /**
          * 如果调用 LeftProjection.map 方法时，
          * Either 对象持有 Left 实例，map 方法会作用于 Left 实例所持有的对象。
          * 这与 Option.map方法处理 Some 对象的方式类似。不过，
          * 如果你调用 LeftProjection 对象的 map 方法，但 Either 对象却持有 Right 实例时，
          * Either 对象会向 map 方法传递 Right 实例持有的对象。
          * 这与 Option.map 方法处理 None 对象的方式是类似的。
          */
        //当然,我们也可以使用for推导式
        println(l.left map (_.length))
        println(for (s <- l.left) yield s.length)
        //那么,我们应该抛出异常还是返回 Either 值?
        /**
          * Either 类型有其可取之处，不过如果代码出错的话，直接抛出异常不是更简单吗？
          * 在某些时候，抛出异常当然是更合理的。抛出异常能避免对错误数据进行计算；
          * 而有时候调用栈中的某些对象捕获异常可以对故障执行合理的恢复。
          */
        //但是,抛出异常会破坏引用的透明性!
        def addInts(i: String, k: String): Int = i.toInt + k.toInt

        for { //正常调用毫无压力
            i <- 1 to 3
            k <- 1 to i
        } println(s"$k + $i = ${addInts(i.toString, k.toString)}")
        //        println(addInts("0", "x")) //但如果是用户输入的就不一定了
        /**
          * 使用 Either 对象，我们可以保障引用透明性，并通过类型签名提醒调用者可能会出现错误。
          */
        //使用Either重写addInts,而且类型签名提醒调用者可能出现错误
        def addInts2(i: String, k: String): Either[NumberFormatException, Int] = {
            try {
                Right(i.toInt + k.toInt)
            } catch {
                case nfe: NumberFormatException => Left(nfe)
            }
        }

        /**
          * 现在， addInts2 方法的类型签名能够提示可能会出现错误。
          * 它不再通过抛出异常来捕获调用堆栈中某些应用的控制权，
          * 而是将异常作为调用堆栈中的结果值返回，以此来消除程序错误。
          */
        //不会抛出异常了,但是异常并没有消失!!!只是被封装到Left里面了
        println(addInts2("0", "x").left.get.getClass)
        //        throw { //异常也可以重新抛出
        //            addInts2("0", "x").left.get
        //        }
        /**
          * 在 Java 和 Scala 类库中，抛出异常是一种很常见的做法，
          * 因此我们也会编写出 try{...} catch{...}这样的样板代码，
          * 并将好的和不好的结果都封装在 Either 对象中。为了处理异常，
          * 我们也许应该使用某些类型将这些样板代码进行封装，而无论操作成功还是失败，
          * 这些类型名能够更清楚地表达这当前的状况。 Try 类型就做到了这点。
          */

        //个人理解
        //以这个为例子
        def addInts3(s1: String, s2: String): Either[NumberFormatException, Int] =
        try {
            Right(s1.toInt + s2.toInt) //此处返回了正确的结果Right
        } catch { //发生了异常则会返回到Left
            case nfe: NumberFormatException => Left(nfe)
        }

        //测试调用
        println {
            //            (1 to 10).map(_ => StdIn.readLine).
            //            reduce(addInts3(_, _).right.get.toString)
        }

        //思考另外一种情况: 当需要对一组相互无关的数据进行处理
        //比如根据网页中每个用户输入的数字将其 * 2 并返回
        def twice(s: String): Either[String, Int] = {
            try {
                Right(s.toInt)
            } catch {
                case nfe: NumberFormatException => Left("数据异常...")
            }
        }

        //        println(twice("2").right.get)
        //        println(twice("x").right.get)

        Seq("1", "2", "a", "3", "4", "b", "5") map { s =>
            val temp = twice(s)
            temp.right.getOrElse(temp.left.get)
        } foreach (s => println(s"你的积分已提升为: $s"))
        //在这儿,我们从网页中获取到了用户输入的数字,
        //并将之*2返回给网页中,发生异常的数据将会返回数据异常的信息.

    }
}
