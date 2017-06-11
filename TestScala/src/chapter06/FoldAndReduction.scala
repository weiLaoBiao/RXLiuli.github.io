package chapter06

/**
  * Created by RXLiuli on 2017/5/2.
  */
//折叠与归约
object FoldAndReduction {
    def default(): Unit = {
        /**
          * 之所以把折叠和归约放在一起是因为两者很相似,
          * 他们都是将集合"缩小"成一个更小的集合或一个值的操作.
          */
        /**
          * 折叠从一个初始的"种子"值开始,然后以该值作为上下文,处理集合中的每个元素.
          * 不同的是,归约不需要调用者提供一个初始值.它将集合的其中一个元素当做初始值,
          * 通常这个值是集合的第一个元素或最后一个元素:
          */
        val list = List(1, 2, 3, 4, 5)
        //累加集合中的所有元素,集合的元素为Int类型时等效于 list.sum()
        println(list.reduce(_ + _)) //折叠
        //累计集合中的所有元素,还有初始值,为集合中所有元素的值+初始种子
        println(list.fold(10)(_ + _)) //归约
        println((list fold 10) (_ * _)) //归约中缀写法

        //那么,其中到底发生了什么呢?
        (list fold 10) (_ + _)
        //上面和下面等价
        list.fold(10)((i, k) => i + k)
        //创建一个偏函数看一下
        val fold2: ((Int, Int) => Int) => Int = list fold 10
        println(fold2(_ + _))

        //可能发生异常
        //会出现异常,因为它没有值可以返回
        //        println(List.empty[Int].reduce(_ * _))
        println(List.empty[Int].sum) //尽可能地使用sum替代reduce,会返回0
        println(List.empty[Int].fold(10)(_ + _)) //不会出现异常,可以返回种子
        //如果不确定集合是否为空(比如集合是传入你函数的一个参数时),可以使用OptionReduce
        //即便是Nil也不会出现异常,只会返回None.
        println(List.empty[String].reduceOption(_ + _))
        //下面这种方法也不会报错,但会返回一个空字符串String.empty
        println(List.empty[String].fold("")(_ + _))

        /**
          * reduce返回集合中各元素的最近公共父类型(最小上界或 LUB).如果元素均为同一类型,
          * 则reduce返回的元素就是该类型的.fold方法则有初始种子值,
          * 因为对最终结果的处理有更多选项.以下是一个折叠操作,事实上相当于映射操作:
          */
        //不明白这儿发生了什么....
        val list2 = (list foldRight List.empty[String]) {
            (x, list) => ("[" + x + "]") +: list
        }
        println(list2)
        /**
          * 官方解释:
          * 首先，我们使用 fold 的一个变体 foldRight 从右到左遍历集合，
          * 这样可以保证我们构造的新序列中元素的顺序是正确的。
          * 也就是说，元素 6 首先被处理，并插入到空序列头部；随后元素 5 被处理，
          * 插入到该序列的头部，以次类推。这样，累计值相当于这个匿名函数的第二个参数。
          * 事实上，所有其他的操作均可用 fold 实现，包括 foreach 。
          * 如果你只能拥有我们讨论的众多函数中的一个，那么你可以选择 fold ，
          * 然后用 fold 重新实现其他函数
          */
        /**
          * 以下我们就给出关于 fold 和 reduce 方法的签名和介绍，
          * 它们被声明于 scala.collection.TraversableOnce 和
          *scala.collection.TraversableLike 中。下面的介绍是从 Scaladoc 中摘录的。
          */
        /**
          * def fold[A1 >: A](z: A1)(op: (A1, A1) & A1): A1
          * 遍历集合，使用指定的二元操作符 op 对集合元素做折叠。
          * 对元素执行操作时，其顺序是未指定的，因此结果是不能确定的。
          * 不过，对于多数顺序固定的集合如 List ，fold 的作用与 foldLeft 相同。
          */
        val list3 = List("a", "b", "c", "d", "e")
        println((list3 fold "result:") (_ + " " + _)) //连接全部字符串
        //而至于顺序不固定的问题,请考虑以下情景:
        println(Set("a", "b", "c", "d", "e").fold("result2:")(_ + " " + _))
        //是的...结果是eabcd,Set就是顺序不固定的集合!!!
        //但如果只是简单的连接的话实际上有更好的解决方案....
        println(list3.mkString("result3: ", " ", "\t"))
        //即便是空的集合也可以呢!!!
        println(List.empty[String].mkString("result4: ", " ", "\t"))
        /**
          * def foldLeft[B](z: B)(op: (B, A) & B): B
          * 对初始值和集合中的所有元素做操作，顺序从左到右。
          */
        println((list3 foldLeft "result:") (_ + " " + _))
        /**
          * def foldRight[B](z: B)(op: (A, B) & B): B
          * 对初始值和集合中的所有元素做操作，顺序从右到左。
          */
        //输出是: a b c d e result: ,是不是很奇怪呀?
        println((list3 foldRight "result:") (_ + " " + _)) //从右向左
        /**
          * def /:[B](z: B)(op: (B, A) & B): B = foldLeft(z)(op)
          * foldLeft 的别名。调用形式例如： (0 /: List(1,2,3))(_ + _) 。
          * 不过，大部分人认为用操作符 /: 来表示 foldLeft 太隐晦不易记忆，
          * 所以，写代码时不要忘记与代码阅读者进行交流。
          */
        println(("result: " /: list3) (_ + " " + _)) //等效于fold
        /**
          * def :\[B](z: B)(op: (A, B) & B): B = foldRight(z)(op)
          * foldRight 的别名。调用形式例如： (List(1,2,3) :\ 0)(_ + _) 。
          * 不过，大部分人认为用操作符 /: 来表示 foldLeft 太隐晦不易记忆。
          */
        println((list3 :\ "result: ") (_ + " " + _)) //等效于foldRight
        /**
          * def reduce[A1 >: A](op: (A1, A1) & A1): A1
          * 遍历集合，使用指定的二元操作符 op 对集合元素做归约。对元素执行操作时，
          * 其顺序是未指定的，因此结果是不能确定的。不过，
          * 对于多数顺序固定的集合如 List ， reduce 的作用与 reduceLeft 相同。
          * 如果集合为空，则抛出异常。
          */
        println(list3 reduce (_ + " " + _)) //还是那句话,空集合有危险
        /**
          * def reduceLeft[A1 >: A](op: (A1, A1) & A1): A1
          * 对初始值和集合中的所有元素做操作，顺序从左到右。如果集合为空，则抛出异常。
          */
        println(list3 reduceLeft (_ + " " + _)) //等效于reduce
        /**
          * def reduceRight[A1 >: A](op: (A1, A1) & A1): A1
          * 对初始值和集合中的所有元素做操作，顺序从右到左。如果集合为空，则抛出异常。
          */
        println(list3 reduceRight (_ + " " + _)) //和reduceLeft相反
        /**
          * def optionOption[A1 >: A](op: (A1, A1) & A1): Option[A1]
          * 类似 reduce ，但当集合为空时，返回 None ；集合不空时，返回 Some(…) 。
          */
        println(list3 reduceOption (_ + " " + _)) //更安全的折叠
        /**
          * def reduceLeftOption[B >: A](op: (B, A) & B): Option[B]
          * 类似 reduceLeft ，但当集合为空时，返回 None ；集合不空时，返回 Some(…)。
          */
        println(list3 reduceLeftOption (_ + " " + _)) //更安全的reduceLeft折叠
        /**
          * def reduceRightOption[B >: A](op: (B, A) & B): Option[B]
          * 类似 reduceRight ，但当集合为空时，返回 None ；集合不空时，返回 Some(…)。
          */
        println(list3 reduceLeftOption (_ + " " + _)) //更安全的reduceRight折叠
        /**
          * def aggregate[B](z: B)(seqop: (B, A) & B, combop: (B, B) & B): B
          * 对后面的元素进行操作，并聚合结果。该函数比 fold 和 reduce 形式更加通用，
          * 具有相似的语法，但并不要求结果的类型是元素的公共父类型。
          * 函数将集合分为不同的分片，顺序遍历各个分片，用 seqop 更新计算结果，
          * 最后对各个分片的计算结果调用 combop 。该操作可能操作任意个分片，
          * 因此 combop 可能被调用任意次。
          */
        /**
          * def scan[B >: A](z: B)(op: (B, B) & B): TraversableOnce[B]
          * 扫描、计算集合元素的一个前缀。中间的元素 z 可能会被多次操作。
          * （在本节末尾，会给出一个示例。）
          */
        println(list.scan(10)(_ + _)) //很奇怪的方法
        /**
          * def scanLeft[B >: A](z: B)(op: (B, B) & B): TraversableOnce[B]
          * 从左到右遍历集合，对元素执行 op 操作，得到一个包含一系列累计值的集合。
          */
        /**
          * def scanRight[B >: A](z: B)(op: (B, B) & B): TraversableOnce[B]
          * 从右到左遍历集合，对元素执行 op 操作，得到一个包含一系列累计值的集合。
          */
        /**
          * def product: A
          * 计算集合元素的累乘值。只要集合元素可以隐式转换为 Numeric[A] （
          * http://www.scala-lang.org/api/current/scala/math/Numeric.html ）
          * （例如： Int 、 Long 、 Float 、 Double 和 BigInt ），
          * 就可以返回元素的累乘值。实际上，函数的完整签名为:
          * def product[B >: A](implicit num: Numeric[B]): B
          * 参见 5.2.3 节，了解关于使用隐式转换来约束方法使用范围的细节。
          */
        println(list.product)
        /**
          * def mkString: String
          * 将集合中的所有元素序列化到字符串中。该方法是 fold 的一个自定义实现，
          * 用于方便地从集合生成字符串。在集合的元素之间没有分隔符。
          */
        println(list.mkString)
        /**
          * def mkString(sep: String): String
          * 从集合生成字符串，分隔符为字符串参数 sep 。
          * def mkString(start: String, sep: String, end: String): String
          * 从集合生成字符串，分隔符为字符串参数 sep ，前缀为 start ，后缀为 end 。
          */
        println(list.mkString(" "))
        println(list.mkString("head ", " ", " tail"))
        /**
          * 特别留心传递给 reduce 、 fold 和 aggregate 的匿名函数的参数。
          * 对于 Left 系列的函数，如 foldLeft ，其中第一个参数为累计值，
          * 集合遍历的方向为从左向右。对于 Right 系列的函数，如 foldRight ，
          * 其中第二个参数为累计值，集合遍历的方向为从左向右。对于 fold 和reduce等方法，
          * 遍历方向既不是从左到右，也不是从右到左，其遍历方向与初始值是未定义的
          * （不过一般都采用了从左到右的方式）。
          */
        /**
          * fold 系列方法可以输出一个与集合元素完全不同类型的值；
          * 而 reduce 系列方法总是返回与元素相同类型或元素父类型的值。
          */

        //向左和向右遍历
        println((1 to 10).foldLeft("result: ")("(" + _ + " " + _ + ")"))
        println((1 to 10).foldRight("result: ")("(" + _ + " " + _ + ")"))
        //遍历顺序正好相反,但结果顺序相同
        //foldLeft是尾递归
        //foldRight是普通递归

    }
}
