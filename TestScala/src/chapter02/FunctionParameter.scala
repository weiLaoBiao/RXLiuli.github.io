package chapter02

/**
  * Created by RXLiuli on 2017/4/2.
  */
object FunctionParameter {
    def default(): Unit = {
        val shape = Circle(Point(x = 3.3, y = 4.4), 5)
        //优势1. 多个参数列表的形式拥有整齐的块结构语法
        //此处省略了参数类型,这是因为编译器可以自动推断出匿名函数的参数类型
        shape.draw(shape.center) {
            str => {
                println(s"$str + $str")
                printf("直接打印: %s\n", str)
                printf("长度是: %s\n", str.length)
            }
        }
        //以下是单个参数列表多个参数的方法,相对而言块结构不如上面的多参数列表清晰
        shape.draw2(shape.center, (str: String) => {
            println(s"$str + $str")
            printf("直接打印: %s\n", str)
            printf("长度是: %s\n", str.length)
        })

        //下面稍微演示一下多参数列表和单参数列表的细微区别
        def m1[A](a: A)(f: A => String): String = f(a)

        def m2[A](a: A, f: A => String): String = f(a)


        //之所以这样,是因为Scala的类型推断是局部的类型推断第二条是错误的
        //优势2. 如果想要用单参数列表多参数并且含有函数参数时,就必须要显式的声明函数参数的类型
        printf("m1方法运行结果:%s\n", m1(100)(i => s"$i + $i"))
        //        printf("m2方法运行结果:%s\n", m2(100, i => s"$i + $i")) //这儿会出现运行时错误
        printf("m2方法运行结果:%s\n", m2(100, (i: Int) => s"$i + $i")) //这是改进后的版本,不会报错了

    }
}
