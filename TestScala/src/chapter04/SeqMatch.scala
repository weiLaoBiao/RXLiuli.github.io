package chapter04

/**
  * Created by RXLiuli on 2017/4/20.
  */
/**
  * 注: 普通递归是一种消耗资源并可能造成栈溢出的方法,决定必须要慎用.
  * 一般而言,尾递归会被优化为迭代,所以尾递归在适当的情况下可以使用(比如阶乘,使用尾递归实现相当容易)
  * 任何递归都绝对比不上迭代的效率高(因为方法调用也会消耗资源)
  */
object SeqMatch {
    def default(): Unit = {
        /**
          * Seq(序列): 是具体集合类型的父类型,这些集合类型支持以确定的顺序遍历其元素
          * 比如List(前追加)和Vector(后追加)
          */
        ////////////////////////////////////////////////////////////////////////////////
        ////////////////////////使用模式匹配和递归方法遍历Seq的传统方法////////////////////////
        ////////////////////////////////////////////////////////////////////////////////
        val nonEmptySeq = Seq(1, 2, 3, 4, 5) //构造了一个非空Seq[Int](事实上返回的是List)
        val emptySeq = Seq.empty[Int] //以惯用方法构造了一个空的Seq[Int]
        val nonEmptyList = List(1, 2, 3, 4, 5) //构造了一个非空List[Int](Seq子类型)
        val emptyList = Nil //Scala库的专用对象Nil,表示任意类型的空List
        val nonEmptyVector = Vector(1, 2, 3, 4, 5) //构造了一个非空的Vector[Int]
        val emptyVector = Vector.empty[Int] //构造一个空的Vector[Int]
        //构造了一个非空的Map[String,Int],这不是Seq的子类型,key: String value: Int
        val nonEmptyMap = Map("one" -> 1, "two" -> 2, "three" -> 3)
        val emptyMap = Map.empty[String, Int] //构造了一个空的Map[String,Int]

        //递归实现
        //定义了一个递归方法,从Seq[T]中构造String,T为某种待定类型,方法体是用来与输入的Seq[T]匹配
        def seqToString[T](seq: Seq[T]): String = seq match {
            /** 此处是两个互斥的match子句,第一个匹配非空的Seq,提取其头部(第一个元素)以及尾部(除头部以外的元素)
              * Seq有head和tail方法,但此处按case子句的惯例被解释为变量
              * 在case子句中,用提取的头部元素加上"+:",以及尾部的字符串来构造一个字符串
              * 尾部的字符串表示由调用seqToString产生
              */
            case head +: tail => s"$head +: " + seqToString(tail)
            case Nil => "Nil" //另外一个case只能是空Seq,我们用表示空List专用的对象Nil来匹配
            //注: 任何Seq的尾部都可以认为是一个相应类型的空Seq,事实上List就是这么实现的
        }


        //for推导式实现
        //将这些Seq作为元素放进另外一个大Seq中(对其中的Map调用toSeq,将其转化为键-值对组成的序列)
        for (seq <- Seq(
            nonEmptySeq, emptySeq, nonEmptyList, emptyList, //此处的空集表明了seqToString()对空集也能正常工作
            nonEmptyVector, emptyVector, nonEmptyMap.toSeq, emptyMap.toSeq)
        ) {
            println(seqToString(seq)) //遍历并打印seqToString()返回的结果
        }
        /** Map并不是Seq的子类型,因为Map不保证遍历的顺序是固定的
          * +: : 序列的构造操作符,与:: 类似是右结合的
          * 第一条case语句只匹配至少包含一个头部元素的非空序列,它将头部元素和其他元素分别提取到可变变量head和tail中
          * 这里的head和tail是两个任意的变量名(Seq确实有head和tail方法),
          * 通常情况下我们可以通过上下文清晰地看出这两个标识符是方法还是变量
          * 注: 对空序列调用head和tail方法编译器会抛出异常
          * Seq很像链表,链表中头节点包含自身的值并且指向链表的尾部(即链表中剩下的元素)，从而创建了一种层级结构
          * (node1, (node2, (node3, (node4, (end))))
          * Nil对象匹配所有的空序列
          */

        //////////////////////////////////////////////////////////////////
        ////////////////////////程序的变体: 增加了括号////////////////////////
        //////////////////////////////////////////////////////////////////
        println("\n============================================================\n")
        val nonEmptySeq2 = Seq(1, 2, 3, 4, 5)
        val emptySeq2 = Seq.empty[Int]
        val nonEmptyMap2 = Map("one" -> 1, "two" -> 2, "three" -> 3)

        def seqToString2[T](seq: Seq[T]): String = seq match {
            case head +: tail => s"($head +: ${seqToString2(tail)})" // ➊
            case Nil => "(Nil)"
        }

        for (seq <- Seq(nonEmptySeq2, emptySeq2, nonEmptyMap2.toSeq)) {
            println(seqToString2(seq))
        }
        //可以使用结果重新构造一个相同的Seq
        val resultSeq = (1 :: (2 :: (3 :: (4 :: (5 :: (Nil))))))

        //注: Seq要么为空,要么非空

        ////////////////////////////////////////////////////////////////////
        ////////////////////////Scala2.10之前的惯用方法////////////////////////
        ////////////////////////////////////////////////////////////////////
        println("\n============================================================\n")
        val nonEmptyList3 = List(1, 2, 3, 4, 5)
        val emptyList3 = Nil

        def listToString[T](list: List[T]): String = list match {
            case head :: tail => s"($head :: ${listToString(tail)})" // ➊
            case Nil => "(Nil)"
        }

        for (l <- List(nonEmptyList3, emptyList3)) {
            println(listToString(l))
        }

        //使用Seq序列还原出Map
        println("\n============================================================\n")
        val resultMap = (("one", 1) +: (("two", 2) +: (("three", 3) +: (Nil))))
        //Map.apply工厂方法需要可变参数列表,其中的参数是由两个元素组成的元组
        val m = Map(resultMap: _*)
        println(s"m type: ${m.getClass}")

    }
}
