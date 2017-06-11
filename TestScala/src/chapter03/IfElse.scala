package chapter03

/**
  * Created by RXLiuli on 2017/4/12.
  */
object IfElse {
    def default(): Unit = {
        //Scala中的if else语句几乎和Java相同
        //只有一点,Scala中的if语句和其他几乎所有语句都是具有返回值的表达式

        //1. 直接使用if else语句,看起来好像没什么不同,但这儿有个严重错误！！
        //i是一个var可变类型,而非val不可变类型
        var i: String = ""
        if (1 > 2) i = "1 > 2"
        else i = "1 <= 2"
        println(s"i: $i")

        //2. 此处定义了一个简单的if else表达式,该表达式判断并返回String类型的值
        val i2 = if (1 > 2) "1 > 2" else "1 <= 2"
        println(s"i2: $i2")

        //3. 对上面的例子改进一下,传入一个值,然后进行判断并返回值()
        val i3 = (k: Int) => if (k > 2) s"$k > 2" else s"$k <= 2"
        //直接调用一下
        println(s"i3直接调用: $i3") //out: Chapter03.IfElse$$$Lambda$5/1914572623@28864e92 why?!
        //注意,此处i3并不是一个变量了(即一个可以使用的值),而是一个匿名表达式(lambda/匿名函数)
        println(s"i3的类型: ${i3.getClass}") //out: class Chapter03.IfElse$$$Lambda$5/1914572623
        //正确使用方法
        println(s"i3正确使用方法: ${i3(1)}")

        //4. 模仿3将另一个值也参数化
        val i4 = (k: Int, j: Int) => if (k > j) s"$k > $j" else s"$k <= $j"
        //调用一下
        println(s"i4: ${i4(1, 2)}")

        //5. 尝试柯里化函数(两个参数列表,好处很快就可以看到了)
        def compare(k: Int)(j: Int) = if (k > j) s"$k > $j" else s"$k <= $j"

        println(s"正常调用compare方法: ${compare(1)(2)}")
        //定义了一个函数字面量
        val i5 = compare(_: Int /*此处无法推断类型?*/)(2) //没错,这儿相当于重构了i3那个表达式,功能完全一样
        println(s"i5(重构i3): ${i5(1)}")
        //我们甚至可以重构i4,下面就完成了
        val i6 = (k: Int, j: Int) => compare(k)(j)
        println(s"i6(重构i4): ${i6(1, 2)}") //包括调用方法也是一样
        //思考: 柯里化函数的优点,非常容易复用,而且强大到了极点

        //6. 那么,有没有一劳永逸代替上面的那些方法呢？ 答案是绝对的！
        //此处新增了参数默认值
        def compare2(k: Int = 1)(j: Int = 2) = if (k > j) s"$k > $j" else s"$k <= $j"

        //试着直接调用一下
        println(s"compare2直接调用: ${compare2()()}") //重构了i2
        println(s"compare2有一个参数: ${compare2(2)()}") //重构了i3
        println(s"compare2 有两个参数: ${compare(1)(2)}") //重构了i4
        //优化过之后,一个方法全部解决了！


    }
}
