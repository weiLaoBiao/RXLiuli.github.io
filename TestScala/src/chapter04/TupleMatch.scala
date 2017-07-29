package chapter04

/**
  * Created by RXLiuli on 2017/4/21.
  */
object TupleMatch {
    def default(): Unit = {
        //定义一个三元组的序列
        val langs = Seq(
            ("Scala", "Martin", "Odersky"),
            ("Clojure", "Rich", "Hickey"),
            ("Lisp", "John", "McCarthy"),
            ("Java", "C#", "Html", "T-SQL"),
            Nil,
            ("Scala", "Python"))
        for (tuple <- langs) {
            tuple match {
                //匹配一个三元素元组,其中第一个元素为Scala,忽略第二和第三个元素
                case ("Scala", _, _) => println("Found Scala")
                //匹配任意三元素元组,其中的元素可以为任意类型,三个元素被提取到变量lang,first,last中
                case (lang, first, last) =>
                    println(s"Found other language: $lang ($first,$last)")
                //匹配任意长度的元祖,包括空元祖
                case (_) => println("not Match")
            }
        }
        //元祖可以拆分为各个组成的元素,我们可以匹配元组中任意位置的字面量,同时忽略不需要的值
    }
}
