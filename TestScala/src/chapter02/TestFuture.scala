package chapter02

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by RXLiuli on 2017/4/2.
  */
object TestFuture {
    def default(): Unit = {
        def sleep(millis: Long): Long = {
            sleep(millis)
        }

        //繁忙的处理工作
        def dowork(index: Int) = {
            sleep((math.random * 1000).toLong)
            index
        }

        (1 to 5).foreach({
            index =>
                val future = Future {
                    dowork(index)
                }
                future onSuccess {
                    case answer: Int => println(s"Success! returned: $answer")
                }
                future onFailure {
                    case th: Throwable => println(s"FAILURE! returned: $th")
                }
        })

        sleep(1000)
        println("Finito!")
    }
}
