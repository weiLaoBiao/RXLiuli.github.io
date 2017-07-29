package chapter03

/**
  * Created by RXLiuli on 2017/4/16.
  */
object InterpolatedString {
    def default(): Unit = {
        val d = 123.456789
        println(s"i的值: $d") //插入单个变量引用不需要花括号
        println(s"i + 1的值: ${d + 1}") //插入表达式必须使用

        //如果想要插入美元符号,可以连续输入两个美元符号
        println(s"美元符号: $$$d")

        //f格式化字符串
        println(f"f格式化字符串: $$$d%.2f")
        val i = 123456789
        println(f"f格式化Int=>Double: $i%.2f") //此处实际上使用了隐式转换
        //        println(f"f格式化Double=>Int: $d%10d") //此处会出现编译错误
        //注: 通常不使用原生的浮点数和双精度浮点数表示货币,因为表示货币金额时需要满足各种记账规则(如舍入规则)

        //raw原生字符串
        val str = raw"raw原生字符串: \n\t\w" //此处编译器报错(注: 非编译错误!!)
        println(str) //output: raw原生字符串: \n\t\w,原生字符串不会对控制字符进行扩展

        //三重双引号字符串,默认包含换行符
        val str2 =
            """select
              |* /*此处的注释将会被视为字符串的一部分! */
              |from StuInfo
            """.stripMargin
        println(s"三重双引号: \n$str2")


    }
}
