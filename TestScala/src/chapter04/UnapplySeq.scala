package chapter04

/**
  * Created by RXLiuli on 2017/4/23.
  */
object UnapplySeq {
    def default(): Unit = {
        //问: 如果需要更灵活一些,希望提取序列时返回非固定数量的元素,该怎么办呢?
        /**
          * unapplySeq方法可以做到这点,除了apply方法以外,Seq伴生对象还实现了unapplySeq(),
          * 而非普通的unapply()
          * def apply[A](elems: A*): Seq[A] //A*代表elems是一个可变参数列表
          * def unapplySeq[A](x:Seq[A]):Some[Seq[A]]
          */
        //触发unapplySeq方法调用,并考察所提取元素的"滑动窗口"
        //定义一个非空List,一个Nil,一个Map
        val nonEmptyList = List(1, 2, 3, 4, 5)
        val emptyList = Nil
        val nonEmptyMap = Map("one" -> 1, "two" -> 2, "three" -> 3)

        //Process pairs Process:加工,处理 pairs:一对,一副
        def windows[T](seq: Seq[T]): String = seq match {
            /**
              * 看起来我们会隐含调用Seq.apply(...),但实际上调用的是Seq.unapplySeq
              * 我们提取了前两个元素,忽略了用_*表示的其他可变参数,*于正则中的*类似.
              */
            case Seq(head1, head2, _*) =>

                /**
                  * 用匹配到的前两个元素格式化字符串,同时调用该seq.tail将提取的"窗口"向后移动一位
                  * 注: 在这次匹配中,我们并没有提取尾部元素
                  */
                s"($head1,$head2)," + windows(seq.tail)

            /**
              * 我们还需要匹配只有一个元素的序列,否则匹配就不完全.
              * 用_表示不存在的"第二个"元素,已知调用windows(seq.tail)会返回Nil,
              * 但为了避免将字符串再重复一遍,我们再次调用了windows方法
              */
            case Seq(head, _*) =>
                s"($head,_)," + windows(seq.tail)
            case Nil => "Nil"
        }

        //使用尾递归实现"滑动窗口"
        def windows2[T](seq: Seq[T], result: String = ""): String = seq match {
            //此处使用了+:方法,这种写法更优雅
            case head1 +: head2 +: _ =>
                windows2(seq.tail, result + s"($head1,$head2),")
            case head +: _ =>
                windows2(seq.tail, result + s"($head,_),")
            case Nil => result + "Nil"
        }

        for (seq <- Seq(nonEmptyList, emptyList, nonEmptyMap.toSeq)) {
            println(windows2(seq))
            println(windows(seq))
        }

        //滑动窗口如此有用,Seq甚至提供了两个方法用于创建窗口
        val nonEmptySeq = Seq(1, 2, 3, 4, 5)
        val slide = nonEmptySeq.sliding(2)
        //这两个sliding方法都返回迭代器,他们是"惰性"的
        //由于对大的序列进行复制的代价太过昂贵,这两个函数都没有立即对所操作的列表进行复制
        println(s"slide: $slide")
        //对返回的迭代器调用toSeq方法,可以将迭代器转为一个collection.immutable.Stream
        //这是一个惰性列表,创建时即对列表头部元素求值,但只在需要的时候才对尾部元素求值
        println(s"slide.toSeq: ${slide.toSeq}") //第1次调用toSeq只有头部元素
        println(slide.toSeq.mkString(",")) //第2次调用toSeq发生了神奇的事情,少了头部元素
        println(slide.toSeq.mkString(",")) //第3次则是什么都没有了
        //调用toList则是在创建List时就求出了所有元素的值
        println(s"slide.toList: ${slide.toList}")
        println(s"nonEmptyList.sliding(3, 2).toList: ${
            nonEmptySeq.sliding(3, 2).toList
        }")
        //注: 输出的结果和之前的例子有一点点不同,例如,在这里输出的结尾没有Nil

        //实验: toSeq
        val seq = nonEmptySeq.sliding(2).toSeq
        println(s"toSeq实验.直接打印: $seq")
        println(s"seq的长度: length(${seq.length}) size(${seq.size})")
        println(s"转换成字符串: ${seq.mkString(",")}") //注: 此处是List
        println(s"seq的类型: ${seq.getClass}")


    }
}
