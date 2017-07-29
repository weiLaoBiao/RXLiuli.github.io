package chapter04

/**
  * Created by RXLiuli on 2017/4/21.
  */
object CaseGuard {
    def default(): Unit = {
        for (i <- Seq(1, 2, "Scala", 3, 4)) {
            i match {
                //匹配Int类型,条件是 偶数 && 小于3
                case k: Int if k % 2 == 0 && k < 3 => println(s"even: $i")
                //匹配Int类型 条件是 奇数
                case k: Int if k % 2 == 1 => println(s"odd: $k")
                //匹配任意Int类型
                case k: Int => println(s"other Int: $k")
                //匹配任意输入
                case _ => println(s"other: $i")
            }
        }
    }
}
