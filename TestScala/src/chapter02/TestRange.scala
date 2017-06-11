package chapter02

import scala.annotation.tailrec

/**
  * Created by RXLiuli on 2017/4/2.
  */
object TestRange {
    def default(): Unit = {
        //无限大的整数
        val bi = BigInt(1)
        println(bi.getClass)
        //无限大的浮点数
        val bd = BigDecimal(1)
        println(bd.getClass)
        val range = 1 to 10 by 1
        range.foreach(printf("%s ", _))
    }

    //使用循环实现计算阶乘
    def JieCheng(i: Int): BigDecimal = {
        if (i <= 1) 1
        else {
            var sum: BigDecimal = 1
            for (k <- 1 to i) {
                sum *= k
            }
            sum
        }
    }

    //使用尾递归计算阶乘
    def JieCheng2(i: Int): BigInt = {
        @tailrec
        def JieCheng3(i: Int, sum: BigInt): BigInt = {
            if (i <= 1) sum
            else JieCheng3(i - 1, sum * i)
        }

        JieCheng3(i, 1)
    }
}
