package chapter06

import scala.annotation.switch

/**
  * Created by RXLiuli on 2017/4/30.
  */
//Curry化与函数的其他转换
object TestCurry {
    def default(): Unit = {
        //曾经稍微提到过多个参数列表(柯里化)
        def cat1(s1: String)(s2: String)(s3: String) = s1 + s2 + s3

        //Curry将一个带有多参数的函数转化为一系列函数,每个函数都只有一个参数
        //所以,我们可以用下面的语法来定义curry化的函数
        def cat2(s1: String) = (s2: String) => (s3: String) => s1 + s2 + s3

        //当然,最可喜可贺的是Curry可以用函数而非方法实现
        val cat3: String => String => String => String //类型签名
        = (s1: String) => (s2: String) => (s3: String) => s1 + s2 + s3
        //以下是另外一种实现方式
        val cat4: (String, String, String) => String = _ + _ + _

        //测试调用
        println(s"cat1: ${cat1("hello ")("world ")("! ")}")
        println(s"cat2: ${cat2("hello ")("world ")("! ")}")
        println(s"cat3: ${cat3("hello ")("world ")("! ")}")
        println(s"cat4: ${cat4("hello ", "world ", "! ")}")

        //是的,就目前来看实现的效果是完全相同的,那么,Curry化的函数到底发生了什么呢?
        def cat5 = (s1: String) => {
            (s2: String) => { //这是什么? Lambda匿名函数?
                val f = (s3: String) => { //你也可以给函数命名,不过没什么意义
                    s1 + s2 + s3
                }
                f(_) //而且你还必须要在这儿再调用一次,得不偿失!!!
            }
        }

        println(s"cat5: ${cat5("hello ")("world ")("!")}") //结果一样

        //柯里化的语法更具可读性,而Curry化的函数在作为偏应用函数时,不需要在后面加上下划线
        val cat1hello = cat1("hello ") _
        val cat2hello = cat2("hello ") //没有 _
        //调用的时候完全一样,返回结果也相同,不太明白柯里化和Curry化的意义
        println(s"cat1hello: ${cat1hello("world ")("!")}")
        println(s"cat2hello: ${cat2hello("world ")("!")}")

        //Curry化有什么简单的方法么? 当然是,有的...
        //将一个带有多参数的函数转为Curry化的形式
        val cat4Curried = cat4.curried //Curry化
        println(s"cat4.curried: ${cat4Curried("hello ")("world ")("!")}")
        //将一个带有多个参数列表的函数转为Curry化的形式
        println(s"Function.uncurried(cat4Curried): ${
            Function.uncurried(cat4Curried)("hello ", "world ", "!")
        }")

        //Curry的一个实际用处是对特定类型的数据函数做特殊化.函数可以接受通用类型,
        //而Curry化的函数形式则只接受特定的类型
        //以下就是一个实例,原函数用于计算连乘,Curry花的函数是原函数的特化版本.
        def multiplier(i: Int)(factor: Int) = i * factor

        val byFive = multiplier(5) _
        val byTen = multiplier(10) _
        println(s"byFive: ${byFive(2)}")
        println(s"byTen: ${byTen(2)}")
        //是的,偏应用函数和Curry是紧密相关的两个概念

        //除此之外,还有一些其他的函数转换形式:
        //除此之外,可能会遇到以下场景,有一个元组,却需要调用多参数函数
        def mult(d1: Double, d2: Double, d3: Double): Double = d1 * d2 * d3

        val d3 = (2.2, 3.3, 4.4)
        println(s"mult: ${mult(d3._1, d3._2, d3._3)}")
        //代码不太优雅,但需要使用元组字面量语法,例如: (2.2,3.3,4.4)进行转换,
        //元组元素与函数的参数列表变的相称.我们需要一个新版的mult函数,
        // 其参数是一个三元素的元组.幸运的是,Function对象已经有了转化方法
        val multTupled = Function.tupled(mult _) //将参数列表转化为元祖,[2,5]
        println(s"multTupled: ${multTupled(d3)}")
        //当然,根据对称原理,也存在着untupled函数
        val multUntupled = Function.untupled(multTupled)
        println(s"multUntupled: ${mult(d3._1, d3._2, d3._3)}")
        /**
          * 注: 当我们将mult传递给Function.tupled时,是如何做偏应用化的(加了 _).
          * 但是加入我们将得到的函数值传递给Function对象的其他方法时,就不需要这种语法了.
          * 这种语法是面向对象的方法和函数式编程的函数组合相混合的结果.
          * 幸运的是大部分情况可以对方法和函数一视同仁.
          */

        //最后,偏函数与返回Option函数之间是可以相互转化的
        val finicky: PartialFunction[String, String] = {
            case "finicky" => "FINICKY"
        }
        println(s"finicky: ${finicky("finicky")}")
        //        println(finicky("other")) //Exception: MatchError

        //将偏函数转化为返回Option的函数(避免了偏函数匹配不完全出现异常)
        val finickyLift = finicky.lift
        println(s"finickyOption: ${finickyLift("finicky")}")
        println(s"finickyOption: ${finickyLift("other")}")
        //于是,上面发生了什么好玩的事情呢?
        //事实上,lift函数把我们的偏函数变成下面的样子了
        @switch //switch注解,表明是和Java中一样使用switch,可以提高效率
        val finickyLift2: (String) => Option[String] = {
            case "finicky" => Some("FINICKY")
            case _ => None
        }
        println(s"finickyLift2: ${finickyLift2("finicky")}")

        //对称的函数unlift,将返回Option的函数转化为偏函数(效率会稍有提高)
        val finickyUnlift = Function.unlift(finickyLift)
        println(s"finickyUnlift: ${finickyUnlift("finicky")}")
        //        println(s"finickyUnlift: ${finickyUnlift("other")}")
        //事实上,我们写的也可以使用该方法
        Function.unlift(finickyLift2)("finicky")

        /**
          * 这是函数提升的另一个用法.如果我们有一个偏函数,同时又不希望发生抛出异常的情况,
          * 可以将偏函数提升为一个返回Option的函数,也可以将返回Option的函数降级为偏函数
          */
    }
}

