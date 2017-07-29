package chapter03

/**
  * Created by RXLiuli on 2017/4/12.
  */
object ImplicitConversion {
    def default(): Unit = {
        val x = 1.2
        val y = 1.1
        import MyDouble._
        println(s"$x ~= $y 么? ${x ~= y}")
        println(s"$x ~= $y 么? ${MyDouble.isAlmostEquals(x, y)}")
        println(s"$x ~= $y 么? ${MyDouble.isAlmostEquals(x, y, 0.1)}")
    }
}

//大致比较浮点数是否相等
object MyDouble {

    //1. 给Double类型添加方法"~="
    //定义了一个隐式转换的类型,任意Double类型都可以调用方法
    implicit class double(x: Double) {
        def ~=(y: Double): Boolean = (x - y).abs < 0.1
    }

    //2. 添加工具方法
    def isAlmostEquals(x: Double, y: Double): Boolean = {
        (x - y).abs < 0.1
    }

    //3. 更精确的工具方法,现在可以自定义误差值了(重载)
    def isAlmostEquals(x: Double, y: Double, deviation: Double): Boolean = {
        (x - y).abs < deviation
    }

}
