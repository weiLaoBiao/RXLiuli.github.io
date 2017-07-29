package chapter07

/**
  * Created by RXLiuli on 2017/5/4.
  */
//for推导式的转化规则
object ForChangeRule {
    def default(): Unit = {
        /**
          * 首先，在像 pat <- expr 这样的生成器表达式中，
          * pat 实际上是一个 模式表达式 （ pattern expression ）
          */
        val expr = List(1, 2, 3, 4, 5)
        for (pat <- expr) {

        }
    }
}
