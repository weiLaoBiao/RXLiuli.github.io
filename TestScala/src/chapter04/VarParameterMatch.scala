package chapter04

/**
  * Created by RXLiuli on 2017/4/23.
  */
//可变参数列表的匹配
object VarParameterMatch {
    def default(): Unit = {
        case class Value[T](columnName: String, value: T)
        case class Values[T](columnName: String, values: T*)

        for {x <- Seq(Value("月姬", 15), Value("神", ""),
            Values[Int]("灵梦", 15, 0, 1000), Values("灵梦")
        )} {
            x match {
                case Value("月姬", value) => println(s"name: 月姬 other: $value")
                //此处匹配了多个参数(values @ _*) [0,n]
                case Values("灵梦", values@_*) => println(s"name: 灵梦 other: $values")
                //匹配其余值
                case _ => println("not match")
            }
        }
    }
}
