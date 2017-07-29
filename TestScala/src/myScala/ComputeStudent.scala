package myScala

/**
  * Created by RXLiuli on 2017/6/3.
  */
object ComputeStudent {
    def default(): Unit = {
        /**
          * 计算一个班中至少有多少个人才能有一半的概率两个人同生日
          */

        //        //迭代原型:
        //        var bd = BigDecimal(1)
        //        for (i <- 1 to 23) {
        //            bd *= (365 - BigDecimal(i)) / 365
        //        }
        //        println(bd)
        //        println(foo())

        //迭代实现
        def foo(i: Int = 1): BigDecimal = {
            var bd = BigDecimal(1)
            for (k <- 1 to i) {
                bd *= (365 - BigDecimal(k)) / 365
            }
            println(bd)
            if (bd < 0.5) i else foo(i + 1)
        }

        //递归实现
        def foo2(i: Int = 1): BigDecimal = {
            def f(k: BigDecimal): BigDecimal = {
                if (k < 2) (365 - k) / 365
                else (365 - k) / 365 * f(k - 1)
            }

            println(f(i))
            if (f(i) < 0.5) i else foo2(i + 1)
        }


        println(foo())
        println(foo2())
    }
}
