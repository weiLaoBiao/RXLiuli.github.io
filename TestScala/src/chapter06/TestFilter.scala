package chapter06

/**
  * Created by RXLiuli on 2017/5/1.
  */
//过滤
object TestFilter {
    def default(): Unit = {
        //遍历一个集合,抽取其中满足特定条件的元素,组成一个新的集合,这是一种常见的需求:
        val stateCapitals = Map(
            "Alabama" -> "Montgomery",
            "Alaska" -> "Juneau",
            "Wyoming" -> "Cheyenne"
        )
        //startsWith是判断字符串是否存在指定的子字符串
        val map2 = stateCapitals.filter(_._1.startsWith("A"))
        println(s"map2: $map2")
        //当然,for推导式也实现了该功能(卫语句):
        val map3 = for (x <- stateCapitals if x._1.startsWith("A")) yield x
        println(s"map3: $map3")

        //使用递归实现map
        def myMap[A](seq: Seq[A])(f: (A) => Boolean): Seq[A] = seq match {
            case head +: tail if f(head) => head +: myMap(tail)(f)
            case head +: tail if !f(head) => myMap(tail)(f) //尾递归,所以,双倍
            case Nil => Nil
        }

        def myMap2[A](seq: Seq[A], result: Seq[A] = Nil)(f: (A) => Boolean):
        Seq[A] = seq match {
            case head +: tail if f(head) => myMap2(tail, result :+ head)(f)
            case head +: tail if !f(head) => myMap2(tail, result)(f)
            case Nil => result
        }

        //调用测试
        //        println(myMap(1 to 11000)(_ % 2 != 0))
        //        println(myMap2(1 to 7000)(_ % 2 != 0))
        /**
          * 警告: 不要滥用模式匹配!!!
          */
        val list = Seq(1, 2, 3, 4)
        val x = list match {
            case head +: _ => head
            case Nil => 0
        }
        //可以替换为
        val x2 = list.headOption.getOrElse(0)
        println(s"$x\t$x2")


        /**
          * 在scala.collection.TraversableLike中有若干不同的方法,
          * 用于完成集合的过滤作用或者返回原始集合中的一部分元素.
          * 一些方法在输入无限集合时不会返回.一些方法在输入同一个集合时,
          * 除非集合的遍历顺序固定,否则多次运行的情况下会产生不同的输出.
          * 以下是集合的一些过滤方法:
          */
        /**
          * def drop (n : Int) : TraversableLike.Repr
          * 除起始的 n 个元素，选择其他所有的元素组成一个新的集合并返回。
          * 如果原始集合包含的元素个数小于 n ，则方法会返回一个空集合。
          */
        println(s"Range drop(): ${(1 to 20).drop(5)}") //丢弃5个头元素
        println(s"Map drop(): ${stateCapitals.drop(2)}") //Map也适用
        println(s"dropRight(): ${(1 to 20).dropRight(5)}") //丢弃5个尾元素
        /**
          * def dropWhile (p : (A) => Boolean) : TraversableLike.Repr
          * 从头遍历，丢弃满足一定谓词的最长集合前缀。返回一个最长集合后缀，
          * 其第一个元素不满足指定的谓词 p 。
          */
        println((1 to 20).dropWhile(_ <= 10)) //丢弃满足谓词的前缀
        println((1 to 20).filterNot(_ <= 10)) //返回满足不谓词的元素
        //注意: 他们的返回值类型并不一样!
        //看起来dropWhile和filterNot差不多,实际上并非如此:
        println(Seq(1, 3, 4, 5, 6, 7).dropWhile(_ % 2 == 1))
        println(Seq(1, 3, 4, 5, 6, 7).filterNot(_ % 2 == 1))
        //效果出来了吧,dropWhile只能丢弃前缀(必须是从头开始连续存在,比如5就被4"挡住"了)
        //而filterNot则是全部丢弃,不需要连续存在(就像5和7都被丢弃了)
        /**
          * def exists (p : (A) => Boolean) : Boolean
          * 测试在集合中是否至少有一个元素满足给定的谓词,
          * 如果存在则返回true,否则返回false.
          */
        println { //判断是否存在满足谓词的值
            if (Seq(1, 3, 5, 7).exists(_ % 2 == 0)) "存在偶数"
            else "不存在偶数"
        }
        //当然,如果只要判断是否存在某个值得话,我们有更好的选择...
        println { //判断是否存在某个值
            if (Seq(1, 3, 5, 7).contains(4)) "4 存在"
            else "4 不存在"
        }
        //其实,我们可以联用正则...发挥更强大的效果:
        //学校的学生名单(不重复)
        val stuSet = Set("liuli", "kamisama",
            "nikosannsann", "sakura", "kasuganosora", "line")
        //查询是否有以l 开头的学生,isDefined: 判断Option不等于None
        println(stuSet.exists("^l".r.findFirstIn(_).isDefined))
        //查询是否有以l 开头的学生,并且名字是4位长度
        println(stuSet.exists(
            "^l.{3}$" //是的,联合正则表达式使用相当强大
            .r.findFirstIn(_).isDefined))
        /**
          * def filter (p : (A) => Boolean) : TraversableLike.Repr
          * 选择集合中所有满足一定谓词的元素,返回的新集合中包含了所有满足该谓词 p 的元素.
          * 元素在原集合中的顺序可以得到保持.
          */
        println((1 to 20).filter(_ % 2 == 0)) //保留所有偶数
        println((1 to 20).filterNot(_ % 2 == 0)) //舍弃所有偶数
        /**
          * def find (p : (A) => Boolean) : Option[A]
          * 遍历原集合,寻找第一个满足给定谓词的元素.如果存在这一元素,返回Option,
          * 且Option中包含满足谓词p的第一个元素.否则返回None.
          */
        println((1 to 20).find(_ > 10)) //第一个大于10的元素(Option类型: Some)
        println((1 to 10).find(_ < 0)) //None
        println("Linux".find(_ > 'o')) //字符串也可以(实际上发生了隐式转换)
        /**
          * def forall (p : (A) => Boolean) : Boolean
          * 测试集合中所有元素是否均满足给定的谓词.如果所有元素均满足谓词p,
          * 则返回true,否则返回false.
          */
        println((1 to 20).forall(_ > 0)) //判断集合中的所有元素是否都大于0
        /**
          * def partition(p:(A)=>Boolean):
          * (TraversableLike.Repr, TraversableLike.Repr)
          * 根据谓词,将可遍历集合分成两个子集合.返回值是两个集合:
          * 第一个集合包含所有满足谓词p的元素,而第二个集合包含所有不满足谓词p的元素.
          * 两个集合中元素间的顺序均与原集合保持一致.
          */
        val newSeq = (1 to 20).partition(_ % 2 == 0) //注意,返回值类型为Tupled
        println(s"${newSeq._1}\t${newSeq._2}") //分而治之,将奇数和偶数分离
        /**
          * def take(n:Int):TraversableLike.Repr
          * 选择前n个元素。返回一个可遍历集合,包含原集合的前n个元素,
          * 如果原集合包含的元素小于n个,则返回原集合本身.
          */
        println((1 to 20).take(5)) //返回前5个元素
        //返回前25个元素,因为原集合都没有25个,所以返回本身
        println((1 to 20).take(25))
        /**
          * def takeWhile(p:(A)=>Boolean):TraversableLike.Repr
          * 选择满足特定谓词的最长集合前缀.返回的可遍历集合包含一个最长集合前缀,
          * 其中的每个元素均满足谓词p.
          */
        //和dropWhile相反,但只能截取头部连续的前缀元素,7被6挡住了
        println(Seq(1, 3, 11, 5, 6, 7).takeWhile(_ % 2 != 0))
        println(Seq(1, 3, 11, 5, 6, 7).filter(_ % 2 != 0))
        /**
          * 注: 特定的集合还有特定的过滤高阶函数
          */

    }
}
