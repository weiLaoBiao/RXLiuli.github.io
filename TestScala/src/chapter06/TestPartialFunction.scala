package chapter06

/**
  * Created by RXLiuli on 2017/4/29.
  */
//偏应用函数与偏函数
object TestPartialFunction {
    def default(): Unit = {
        //考虑如下带两个参数列表的简单方法
        def cat1(s1: String)(s2: String) = s"$s1 $s2"

        //如果我们需要一个专门的版本,要求第一个字符串总是hello,我们可以通过偏应用函数来定义
        //注: 此处的_ 是必须的
        val hello = cat1("hello") _ //此处偏函数被推断为(String => String)
        println(s"使用偏应用函数调用: ${hello("world")}")
        println(s"直接调用: ${cat1("hello")("world")}")

        /**
          * 偏应用函数对于拥有多个参数列表的参数而言,如果希望忽略其中一个或多个参数列表,
          * 可以通过定义一个新函数来实现.也就是或,你给出了部分所需要的函数,
          * 为了避免潜在的表达式歧义,Scala要求在后面加上下划线,告诉编译器你的真实目的.
          */
        /**
          * 注: 偏应用函数标识在表达式中使用了函数,但并未给出所需要的所有参数列表.
          * 系统返回了一个新的函数,函数的参数列表是原函数中没有给出的剩下的那部分的参数列表
          */
        //当然,偏应用函数和偏函数并不是同一个概念,以下为求倒数的偏函数的例子:
        val inverse: PartialFunction[Double, Double] = { //显式声明偏函数
            case d if d != 0 => 1.0 / d
        }
        println(s"2.0的倒数: ${inverse(2.0)}")
        //当0作为参数的时候由于模式匹配无法处理而出现Exception: MatchError
        //        println(s"0的倒数: ${inverse(0)}")

        /**
          * 偏应用函数是一个表达式,带部分而非全部参数列表的函数.
          * 返回值是一个新函数,心函数负责携带剩下的参数列表.
          * 偏函数则是单参数的函数,并未对该类型的所有值都有定义.
          * 偏函数的字面量语法由包围在花括号中的一个或多个case语句构成.
          */
    }
}
