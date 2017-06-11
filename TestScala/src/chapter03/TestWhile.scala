package chapter03

import scala.annotation.tailrec

/**
  * Created by RXLiuli on 2017/4/13.
  */
object TestWhile {
    def default(): Unit = {
        //Scala中的while循环实质上也是一个方法
        //正常使用while循环,看起来与Java并无不同
        var i = 0
        while (i < 10) {
            print(i)
            i += 1
        }
        println()

        //使用尾递归重构上面的while循环
        @tailrec
        def show(i: Int): Unit = {
            if (i < 10) {
                print(i)
                show(i + 1)
            }
        }

        //调用show方法
        show(0)
        println()

        //使用方法重写while循环
        @tailrec
        def myWhile(boo: => Boolean)(body: => Unit): Unit = {
            if (boo) {
                body
                myWhile(boo)(body)
            }
        }


        //可以像是使用while循环使用该方法
        var k = 0
        myWhile(k < 10) {
            print(k)
            k += 1
        }

        //do while和while循环同理
        var j = 0
        do {
            println(j)
            j += 1
        } while (j < 0)
    }
}
