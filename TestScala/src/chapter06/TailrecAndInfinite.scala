package chapter06

/**
  * Created by RXLiuli on 2017/5/3.
  */
//尾递归与遍历无限集合
object TailrecAndInfinite {
    def default(): Unit = {
        //计算机程序的有限性,无法处理无限的事物
        def foo[T](seq: Seq[T]): String = {
            seq.mkString("Seq(", ", ", ")")
        }

        /**
          * 事实证明foldLeft和reduceLeft有一个比foldRight和reduceRight重大的优势:
          * 它们是尾递归的，所以可以从 Scala 的尾递归优化中获益。
          */
        val list = (1 to 5).toList
        println((list foldLeft "0") ("(" + _ + " +" + _ + ")"))
        println((list foldRight "0") ("(" + _ + " +" + _ + ")"))
        //刚才构造的对列表进行相加的表达式
        ((((1 + 2) + 3) + 4) + 5) // = 15 (reduceLeft 的例子 )
        (1 + (2 + (3 + (4 + 5)))) // = 15 (reduceRight 的例子 )
        /**
          * 尾递归必须是一次递归中最后一个运行的操作。在 reduceRight 中，
          * 最外层的(1+…)在内层嵌套部分完成之前，无法执行，所以这个操作不能被优化为循环，
          * 也不是尾递归。在列表中，我们受限于列表的构造方式，只能从头部遍历到尾部。
          * 与此相反， reduceLeft 中，我们可以首先对前两个元素执行相加，然后对第三个、
          * 第四个做相加，以此类推。换句话说，我们可以将其转为循环，因为这是尾递归。
          */

    }
}
