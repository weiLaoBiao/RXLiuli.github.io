package chapter06

/**
  * Created by RXLiuli on 2017/5/1.
  */
//扁平映射
object FlatMapping {
    def default(): Unit = {
        /**
          * flatMap是Map操作的一种推广.
          * 在flatMap中,对原始集合中的每个元素,都分别产生零或多个元素.
          * 我们传入一个函数,该函数对每个输入返回一个集合,而非一个元素,
          * 然后flatMap把生成的多个集合压扁为一个集合
          * def flatMap[B](f:(A)=>GenTraversableOnce[B]):Traversable[B]
          * def map[B](f:(A)=>B):Traversable[B]
          */
        //定义一个List
        val list = List("now", "is", "", "the", "time")
        //调用flatMap方法
        println(list.flatMap(_.toList))
        /**
          * 实际过程是,对每个字符串调用toList,生成List[Char].
          * 这些嵌套的列表随后被压扁为最终的List[Char].
          * 变量list中的空字符串对最终生成的字符串没有贡献,
          * 但其他字符串却分别共享了两个或更多字符.
          */
        //我们也可以先调用map,在调用flatten实现该效果:
        println(list.map(_.toList).flatten)
        //注: flatMap不能处理超过一层的集合.如果是深层嵌套的集合则只会压扁一层!
        val list2 = List(List("1"), List("1", "2"), List("1", "2", "3"))
        //像这个的内层集合依然是集合,并没有被压扁(这个例子并不好可以打我吧QAQ)
        println(list2.flatMap(x => List(List(1), List(2))))

        //然后是...flatMap的实现
        def myFlatMap[A, B](seq: Seq[A])(f: (A) => Seq[B]): Seq[B] = {
            def myMap[A, B](seq: Seq[A])(f: (A) => B): Seq[B] = seq match {
                case head +: tail => f(head) +: myMap(tail)(f)
                case Nil => Nil
            }

            myMap(seq)(f).flatten
        }

        println(myFlatMap(list)(_.toList))
    }
}
