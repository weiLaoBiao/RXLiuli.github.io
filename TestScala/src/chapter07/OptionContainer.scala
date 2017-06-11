package chapter07

/**
  * Created by RXLiuli on 2017/5/4.
  */
//Option容器
object OptionContainer {
    def default(): Unit = {
        /**
          * Option 是一个二元容器，其中也许包含了一个元素，也许不包含任何元素。
          * Option 提供了我们所需的四个方法。
          */
        /*
        sealed abstract class Option[+A] {
def isEmpty: Boolean // Some 和 None 类型会实现该变量。
final def foreach[U](f: A => U): Unit =
if (!isEmpty) f(this.get)
final def map[B](f: A => B): Option[B] =
if (isEmpty) None else Some(f(this.get))
final def flatMap[B](f: A => Option[B]): Option[B] =
if (isEmpty) None else f(this.get)
final def filter(p: A => Boolean): Option[A] =
if (isEmpty || p(this.get)) this else None
final def withFilter(p: A => Boolean): WithFilter = new WithFilter(p)
/** 为了能够遵守 “ 不创建新容器 ” 的约定，我们需要声明 WithFilter 类。
* 尽管 Option 容器的最大元素数为 1 ，创建新容器似乎也不会对性能造成多大影响。
*/
class WithFilter(p: A => Boolean) {
def map[B](f: A => B): Option[B] = self filter p map f // ➋
def flatMap[B](f: A => Option[B]): Option[B] = self filter p flatMap f
def foreach[U](f: A => U): Unit = self filter p foreach f
def withFilter(q: A => Boolean): WithFilter =
new WithFilter(x => p(x) && q(x))
}
}
        */

        /**
          * Option 方法具有一个重要的特性：只有当 Option 非空时，
          * 那些方法才会使用传入的函数参数。
          * 利用这一特性，我们能够优雅地解决一个常见的设计问题。
          * 分布式计算领域中有一个常见的模式，即将计算分解为小任务，
          * 再将这些任务分发到集群中，之后再收集这些任务的执行结果。
          * 如果遇到None(错误)时,错误将被忽略掉,继续执行下去。
          */
        val results: Seq[Option[Int]] = Vector(Some(1), None, Some(20))
        println {
            //过滤了None,而我们并没有使用卫语句(不愧是语法糖)
            for (Some(i) <- results) yield i * 2
        }
        println {
            for { //实际上for循环是这样的,之所以不用写只是语法糖而已
                Some(i) <- results withFilter (_.isDefined)
            } yield 2 * i
        }
        println {
            //使用高阶函数的方式(比较繁琐)
            results withFilter (_.isDefined) map (_.get * 2)
        }

        /**
          * 现在让我们再思考另一个设计难题。
          * 这个难题并不是关于忽略各个独立任务中毫无关联的空值并组合非空值的问题。
          * 问题是在执行一组非独立的操作步骤中，我们希望在获得了一个 None 值时，
          * 能够尽快停止整个相互关联的处理过程。
          */
        //接受一个Int类型的参数,如果大于0则使用Some包装,否则返回None
        def positive(i: Int): Option[Int] = if (i > 0) Some(i) else None

        //测试调用
        val i = for { //正常流程
            i1 <- positive(5) //遍历容器Some(5),并赋值给i1,以下等同
            i2 <- positive(10 * i1)
            i3 <- positive(25 * i2)
            i4 <- positive(2 * i3)
        } yield i1 + i2 + i3 + i4
        println(s"i: $i")
        val i2 = for { //非正常流程
            i1 <- positive(5)
            i2 <- positive(-1 * i1) //此处返回了None,可以被遍历,但没有结果
            i3 <- positive(25 * i2) //
            i4 <- positive(-2 * i3) //失败 !
        } yield i1 + i2 + i3 + i4
        println(s"i2: $i2")
        /**
          * 上面究竟发生了什么???
          * 先进行一些实验看看.....
          */
        //没错,Some也是容器,所以,可以使用容器方法
        Some(10) foreach println //遍历Some容器的所有元素(只有一个Int类型的10)
        //        Some(10) foreach (println(_ + 1)) //编译错误?!
        Some(10) foreach (i => println(s"i + 1 = ${i + 1}")) //这样就行...
        None foreach (i => println(s"None $i")) //因为没有值所以不可能有结果
        val none: Option[Int] = None //定义一个Option[Int]的None
        none foreach (i => println(s"None + 1 = ${i + 1}"))
        //那么,可以回去看一下for推导式了

        //将for推导式转化为容器方法链(这次反倒是这个更加清晰一点,但代码量...)
        val i3 = positive(5) flatMap { i1 =>
            positive(10 * i1) flatMap { i2 =>
                positive(25 * i2) flatMap { i3 =>
                    positive(2 * i3) map { i4 =>
                        i1 + i2 + i3 + i4
                    }
                }
            }
        }
        println(s"i3: $i3")
        val i4 = positive(5) flatMap { i1 =>
            positive(-1 * i1) flatMap { i2 => //实际上这儿就已经停止了,返回了None
                positive(25 * i2) flatMap { i3 =>
                    positive(-2 * i3) map { i4 =>
                        i1 + i2 + i3 + i4
                    }
                }
            }
        }
        println(s"i4: $i4")
        /**
          * 我们认为第一个 for 推导式能正常执行。而第二个 for 推导式也能正常执行！
          * 一旦返回了 None 值，后续的表达式将会停止运行。
          * 这是因为map 或 flatMap 不会对这些函数字面量进行处理。
          */


    }
}
