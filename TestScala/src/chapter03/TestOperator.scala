package chapter03

import scala.util.Random

/**
  * Created by RXLiuli on 2017/4/11.
  */
object TestOperator {
    def default(): Unit = {
        //操作符重载,下面两种方法效果完全相同
        println(s"直接使用+ : ${1 + 2}")
        println(s"使用.+ : ${1.+(2)}")

        //无参数方法,如果方法省略了括号,则调用方法时也不能使用括号
        def getRandom: Int = Random.nextInt(100)

        println(s"获取的随机数: $getRandom")

        //斯巴达式的方法调用链
        List[Int](1, 2, 3, 4, 5, 6, 7) filter (_ % 2 == 0) map (_ * 2 - 1) foreach println
        List[Int](1, 2, 3, 4, 5, 6, 7) filter (_ % 2 == 0) map (_ * 2 - 1) foreach (printf("%d\t", _))

        //尝试使用for推导式实现
        for {
            i <- List[Int](1, 2, 3, 4, 5, 6, 7)
            if i % 2 == 0
            k = i * 2 - 1
        } {
            print(s"$k\t")
        }
        //右结合操作符
        //例:通过::(或+:)方法将某一元素放置到列表前面,为cons(constructor)操作
        //下面三种形式实质上的效果是相同的
        val list = List(1, 2, 3, 4)
        val list2 = 1 +: 2 +: 3 +: 4 +: Nil //凭空构造列表
        val list3 = 1 :: 2 :: 3 :: 4 :: Nil
        println(s"直接构造: $list ,type: ${list.getClass}")
        println(s"使用+:构造: $list2 ,type: ${list2.getClass}")
        println(s"使用::构造: $list3 ,type: ${list3.getClass}")
    }
}
