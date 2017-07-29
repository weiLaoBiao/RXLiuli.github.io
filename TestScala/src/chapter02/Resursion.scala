package chapter02

import scala.annotation.tailrec

/**
  * Created by RXLiuli on 2017/4/2.
  */
object Resursion {
  //使用循环实现计算阶乘
  def JieCheng(i: Int): BigInt = {
    if (i <= 0) -1
    else {
      var sum: BigInt = 1
      for (k <- 1 to i) {
        sum *= k
      }
      sum
    }
  }

  //使用尾递归计算阶乘
  def JieCheng2(i: Int): BigInt = {
    if (i <= 0) return -1

    @tailrec
    def JieCheng3(i: Int, sum: BigInt): BigInt = {
      if (i == 1) sum
      else JieCheng3(i - 1, sum * i)
    }

    JieCheng3(i, 1)
  }

}
