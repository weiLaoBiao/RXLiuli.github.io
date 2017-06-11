package chapter01

/**
  * Created by RXLiuli on 2017/4/1.
  */
object TestPartialFunction {
    def default(): Unit = {
        /**
          * 偏函数
          * 无法处理所有的可能的输入的函数，当出现无法处理的输入时便会抛出异常MatchError
          */
        //只匹配String类型的偏函数
        val pf1: PartialFunction[Any, Boolean] = {
            case s: String => true
        }
        //只匹配Double数字的偏函数(不会向下兼容)
        val pf2: PartialFunction[Any, Boolean] = {
            case d: Double => true
        }
        //链式连接偏函数并返回一个新的偏函数
        val pf = pf1 orElse pf2

        println("是否可以匹配到偏函数pf" + pf.isDefinedAt(1))

        //辅助函数，用于try一个偏函数，然后将可能产生的MatchError异常捕捉到
        def tryPE(x: Any, f: PartialFunction[Any, Boolean]): Boolean = {
            try {
                f(x)
            } catch {
                case _: MatchError => false
            }
        }

        try {
            println("是否为String类型：" + pf1(""))
            println("是否为Double类型：" + pf2(""))
            println("是否为String或Double类型：" + pf(""))
        } catch {
            //异常处理，使用模式匹配去判断属于什么异常
            case _: MatchError => println("出现异常！")
        }
        //以下就不会出现异常MatchError
        println("是否为String类型：" + tryPE("", pf1))
        println("是否为Double类型：" + tryPE("", pf2))
        println("是否为String或Double类型：" + tryPE("", pf))
    }
}
