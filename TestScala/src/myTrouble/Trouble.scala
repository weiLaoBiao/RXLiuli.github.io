package myTrouble

/**
  * Created by RXLiuli on 2017/4/13.
  */
object Trouble {
    def default(): Unit = {
        myBreak()
    }

    def myBreak(): Unit = {
        //region scala.util.control.Breaks(类似于Java中的break),使用失败！
        println("\nscala.util.control.Breaks: ")
        for (i <- 1 to 10) {
            if (i > 5) scala.util.control.Breaks.break() //这儿会运行出错?
            else print(i)
        }
        println("执行到这儿了么?")
        //endregion

        //类型不匹配?
        //        def foo[A, B](f: A => List[A], b: B) = f(b)

    }
}
