package chapter04

/**
  * Created by RXLiuli on 2017/4/19.
  */
object ValueMatch {
    def default(): Unit = {
        //match中的值和类型
        for (x /*x的类型为Any*/ <- Seq(1, 2, 2.7, "one", "two", "four")) {
            val str = x match {
                case 1 => "Int i" //匹配int类型的变量 1
                case i: Int => s"other Int: $i" //匹配任意Int类型的值并"安全"赋值给i
                case Double => s"a Double: $x" //匹配任意Double类型的值(大写开头的单词会被默认为类型)
                case "one" => s"String one" //匹配String类型的值one
                case _: /*去掉_编译出错*/ String => s"other String: $x" //匹配任意String类型的值
                case _ => s"unexpected value: $x" //匹配任意输入
            }
            println(str) //打印一下
        }

        /**
          * 像所有表达式一样,match也返回一个值
          * 编译器会推断所有case子句返回值类型的最近公共父类型(也称最小公共上限)
          * 匹配是按照顺序进行的,因此具体的字句应出现在宽泛的字句之前,否则具体的语句将不可能被匹配
          * 编译器能够识别这种错误?!
          * 注: 浮点数存在舍入误差,可能造成意外的不匹配
          * 警告: 除偏函数以外,所有的match语句都必须是完全覆盖所有输入的,当输入类型为Any时需要用 _
          */

        //match中的变量
        def checkY(y: Int): Unit = {
            for (i <- Seq(99, 100, 101)) {
                val str = i match {
                    case y => "found y!" //此处匹配的并不是参数y,而是变量y,相当于_
                    case i: Int => s"Int: $i"
                }
                println(str)
            }
        }

        checkY(100)

        /*理想输出是:
        * Int: 99
        * found y!
        * Int: 101
        * 实际输出:
        * found y!
        * found y!
        * found y!*/
        //如果想要匹配已经存在的变量
        def checkY2(y: Int): Unit = {
            for (i <- Seq(99, 100, 101)) {
                val str = i match {
                    case `y` => "found y!" //将y加上反引号,表示想要匹配的是已存在的变量y
                    case i: Int => s"Int: $i"
                }
                println(str)
            }
        }

        checkY2(100)

        /**
          * 注: 在case子句中,以小写字母开头的标识符被认为是用来提取待匹配值的新变量,
          * 如果需要引用之前已经定义的变量,应使用反引号将其包围.
          * 与此相对,以大写字母开头的标识符被认为是类型名称.
          */
    }
}
