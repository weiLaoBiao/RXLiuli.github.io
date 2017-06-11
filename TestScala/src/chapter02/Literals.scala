package chapter02

import java.util
import java.util._

/**
  * Created by RXLiuli on 2017/4/4.
  */
object Literals {
    def default(): Unit = {
        //整数字面量
        val i = 10 //10进制如果不显式声明类型或者加上标注,或默认推断为Int类型
        val i16 = 0x10 //16进制需要在数字前面加上0x或者0X
        printf("i的类型:%s\ti16的类型:%s\n", i.getClass, i16.getClass)
        //        val i8 = 010 //八进制在新版本中被废弃

        //浮点数字面量
        val f = 1.2f //显式的说明为Float类型
        val d = 1.2 //默认会推断为Double类型
        printf("f的类型:%s\td的类型:%s\n", f.getClass, d.getClass)

        //布尔型字面量
        val b1 = true //Boolean类型只有两个值
        val b2 = false
        printf("b1的类型:%s\tb2的类型:%s\n", b1.getClass, b2.getClass)

        //字符字面量
        val ch1 = 'A' //字符'A'
        val ch2 = '\u0041' //Unicode中的'A'
        val ch3 = '\t' //转义字符'\t'
        val ch4 = '\012' //八进制里面的'\n'
        printf("ch1:%s\tch2:%s\tch3:%s\tch4:%s\n", ch1, ch2, ch3, ch4)
        //字符串字面量
        val str: String = "月姬" //普通字符串,不可变的字符序列
        val strMuchLine =
            """
              |name:月姬 /*多行字符串字面量,适用于大量的纯粹的文本使用 */
              |sex:女 /* | 垂直分割符,移除每行字符串开头的空格和第一个遇到的垂直分割符 */
              |    address:型月世界 /*如果需要空格必须要在垂直分割符后面添加 */
              |\t"" /*多行字符串内的\反斜杠不会构成Unicode字符,也不会构成有效的转义序列 */
            """.stripMargin //移除多行字符串内多余的空格
            .stripPrefix("前缀").stripSuffix("后缀") //移除整个字符串的前缀和后缀
        //注: 多行字符串内包括注释都是无效的
        printf("str的值: %s\nstrMuchLine的值:%s\n", str, strMuchLine)

        //符号字面量
        val id1 = scala.Symbol(" i d ") //创建一个符号,可以包含空格
        val id2 = 'id //创建一个符号(注意,并非字符!),但无法包含空格
        //上面两句话完全一样,下面的为简写形式
        //下面的是创建包含空格的符号
        printf("符号 id1:%s\tid2:%s\n", id1, id2)

        //函数字面量
        //将右边的匿名函数字面量赋值给左边的f1
        val f1 = (i: Int, s: String) => s + i
        //当然,也可以使用函数字面量声明变量
        val f2: (Int, String) => String = f1
        //上面的函数字面量完整写法是,Function2代表的是该函数有两个参数,方括号中的最后一个就是返回值类型
        val f3: Function2[Int, String, String] = f1

        //元祖字面量
        //完整写法
        val tuple2_1: Tuple2[Int, String] = Tuple2(1, "月姬")
        //简洁写法(相比于Java,Scala的类型推断实在太强大啦)
        val tuple2_2 = (1, "月姬")
        printf("元祖字面量 tuple2_1: %s\ttuple2_2: %s\n", tuple2_1, tuple2_2)
        printf("tuple2_2元素组中的元素: %d\t%s\t%s\n", tuple2_2._1, tuple2_2._2, tuple2_2.getClass)

        //作用: 可以变相地让方法拥有多个返回值
        def show(): (Int, String) = (1, "月姬")

        val tuple2_3 = show()
        println(tuple2_3)

        //两元素元组(pair)
        printf("pair的值:%s\tpair的类型:%s\n", 1 -> "月姬", (1 -> "月姬").getClass)
        var tu = (0, -1) //注意:这儿声明的是读写类型
        //此处确认元祖不可变
        for (i <- 1 to 3) {
            1 -> tu
            printf("pair的值:%s\tpair的类型:%s\n", tu, tu.getClass)
        }
        //注:->操作符只能用于两元素的元祖
        //尝试递归,递归增加必须不能固定返回值的大小,但是返回值的元组长度是固定的
        //所以,目前解决不了

    }
}
