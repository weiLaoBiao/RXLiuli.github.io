package chapter02

/**
  * Created by RXLiuli on 2017/4/6.
  */
sealed abstract class TestSealed {
    //抽象方法必须被重写
    def default(str: String): Unit //返回值是可选项
    //非抽象方法可以不被重写
    def default(): Unit =
    println("执行了TestSealed重载方法!")
}

class ATestSealed extends TestSealed {
    //方法重写,必须显式声明
    override def default(str: String): Unit =
        println("执行了ATestSealed重写方法: " + str)

    //方法重载
    def default(i: Int): Unit =
        println("执行了ATestSealed重载方法:" + i)
}

class BTestSealed[T] extends TestSealed { //稍微使用了一下泛型

    override def default(str: String): Unit =
        println("执行了BTestSealed重写方法: " + str)

    //该方法并没有重载,重载方法参数列表必须有区别(返回值不同不能达成重载)
    //    def default(str: String): String = str + "月姬"
}
