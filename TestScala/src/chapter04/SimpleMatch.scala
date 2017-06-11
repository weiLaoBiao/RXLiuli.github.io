package chapter04

import scala.annotation.tailrec

/**
  * Created by RXLiuli on 2017/4/19.
  */
object SimpleMatch {
    def default(): Unit = {
        //简单的模式匹配
        val bools = Seq(true, false)
        for (bool <- bools) {
            bool match {
                case true => println("Got heads")
                case false => println("Got tails")
            }
        }

        //使用旧式的if语句
        for (bool <- bools) {
            println(if (bool) "Got heads" else "Got tails")
        }

        //使用递归试一下,很明显,对于尾递归,应该站在不同的角度看
        @tailrec
        def boolf(bools: Seq[Boolean]): Unit = {
            if (bools.nonEmpty) {
                println(if (bools.head) "Got heads" else "Got tails")
                boolf(bools.drop(1))
            }
        }

        boolf(bools)
    }
}
