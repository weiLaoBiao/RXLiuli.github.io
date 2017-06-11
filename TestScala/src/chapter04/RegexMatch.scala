package chapter04

import scala.io.StdIn

/**
  * Created by RXLiuli on 2017/4/25.
  */
//正则表达式的匹配
object RegexMatch {
    def default(): Unit = {
        //简单的正则表达式匹配
        val phoneRE = //使用三重双引号表示正则表达式字符串(不用转义)
            """^(\d{3})\d{4}(\d{4})$""".r
        val nameRE = """^([\u4e00-\u9fa5])([\u4e00-\u9fa5]{1,2})$""".r

        val seq = Seq("12345678910", "月姬", "八", "13939621143", None)
        seq.foreach { //方法体并没有参数,默认存在了
            //噗,已废弃?!
            case phoneRE(title, tail) =>
                println(s"Phone: $title****$tail")
            case nameRE(surname, name) =>
                println(s"Name: $surname${if (name.length == 1) "*" else "**"}")
            case _ => println("not match")
        }
    }
}
