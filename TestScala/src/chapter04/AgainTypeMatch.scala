package chapter04

import scala.io.Source

/**
  * Created by RXLiuli on 2017/4/26.
  */
object AgainTypeMatch {
    def default(): Unit = {
        val seq = Seq(List(1.2, 3.4, 5.6, 53), List("a", "b"), Nil, List("c", 1, 1.5))
        seq.foreach {
            //实际输出是:
            /*seq Double: List(1.2, 3.4, 5.6, 53.0)
            seq Double: List(a, b)
            seq Double: List()
            seq Double: List(c, 1, 1.5)*/
            case seqd: Seq[Double] => println(s"seq Double: $seqd")
            case seqs: Seq[String] => println(s"seq String: $seqs")
            case default => println(s"unknown: $default")
        }

        /** Scala运行于JVM中,这些警告来源于JVM的类型擦除,类型擦除是Java5引入泛型后的一个历史遗留.
          * 为了避免与旧版本代码断代,JVM的字节码不会记住一个泛型实例（如 List ）中实际传入的类型参数的信息.
          * 所以,当编译器只能识别输入对象为List,但无法在运行时识别它是List[Double]还是List[String]时,
          * 编译器就会发出警告,事实上,编译器认为第二个匹配List[String]的case子句是不可达代码,
          * 意味着第一个匹配List[Double]的case子句可以匹配任意List.
          * 输出显示,对于四个输入,都打印出了seqDouble
          */

        //不太好的解决方案: 首先匹配集合,然后匹配头元素以确定集合类型
        def doSeqMatch[T](seq: Seq[T]): String = seq match {
            //单独处理空的序列Nil
            case Nil => "Nothing"
            //如果是Any则不行
            //比如最后一次匹配会得到这样的输出: seq[String]: List(c, 1, 1.5) 23333
            case head +: _ => head match {
                case _: Double => "Double"
                case _: String => "String"
                case _ => "Unmatched seq element"
            }
        }

        //循环遍历序列seq
        seq.foreach {
            case seq: Seq[_] => println(s"seq[${doSeqMatch(seq)}]: $seq")
            case default => println(s"unknown: $default")
        }

        //解决方案02: 如何获得集合的类型?


    }
}
