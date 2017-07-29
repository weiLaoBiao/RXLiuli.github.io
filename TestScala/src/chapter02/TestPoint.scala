package chapter02

/**
  * Created by RXLiuli on 2017/4/2.
  */
object TestPoint {
    def default(): Unit = {
        //显示的使用命名参数列表,让代码可读性更好
        val p1 = Point(x = 3.3, y = 4.4)
        //指定新的y值,创建新实例
        val p2 = p1.copy(y = 6.6)
        //打印两个点的位置
        printf("%g\t%g\n", p1.x, p1.y)
        printf("%g\t%g\n", p2.x, p2.y)

    }

    def parameterList(): Unit = {
        
    }
}
