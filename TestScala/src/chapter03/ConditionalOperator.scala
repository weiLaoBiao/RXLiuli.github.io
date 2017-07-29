package chapter03

/**
  * Created by RXLiuli on 2017/4/13.
  */
object ConditionalOperator {
    def default(): Unit = {
        /**
          * conditionalOperator(条件操作符)
          * && 逻辑与(短路)
          * || 逻辑或(短路)
          * > 大于
          * >= 大于等于
          * < 小于
          * <= 小于等于
          * == 等于
          * != 不等于
          * 注: 短路(short circuiting)操作符,一旦得知结果,这些操作便会停止对表达式固执
          **/

        def getFalse(): Boolean = {
            println("getFalse执行")
            false
        }

        def getTrue(): Boolean = {
            println("getTrue执行")
            true
        }

        //true和false都是短路
        println(s"false && true: ${getFalse() && getTrue()} \n")
        println(s"true && false: ${getTrue() && getFalse()} \n")
        println(s"true || false: ${getTrue() || getFalse()} \n")
        println(s"false || true: ${getFalse() || getTrue()} \n")
    }
}
