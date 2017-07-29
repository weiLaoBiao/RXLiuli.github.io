package chapter03

import scala.Boolean

/**
  * Created by RXLiuli on 2017/4/15.
  */
object ByNameParameter {
    def default(): Unit = {
        //双倍控制结构
        def twice(f: Double => Double, d: Double): Double = f(f(d))

        println(twice(_ * 2, 2))

        //传名参数(对于无参数方法可以采用)
        def myAssert(f: () => Boolean): Unit = println(f())

        myAssert(() => 5 > 3) //只能这样调用
        def myAssert2(f: => Boolean): Unit = println(f)

        myAssert2(5 > 3) //可以这样调用了
    }
}

