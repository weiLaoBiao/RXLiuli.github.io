package chapter10

import scala.runtime.Nothing$

/**
  * Created by RXLiuli on 2017/5/17.
  */
//Scala的类型层次结构
object TypeStruction {
    def default(): Unit = {
        /**
          * 对 Scala 类型层次结构中的不少类型我们已经有了一定的了解。
          * 接下来，我们将了解该体系的一般结构，并掌握更多的详细信息。
          * Any 处于类型结构树的根部位置， Any 没有父类，但有三个子类：
          * AnyVal ，价值类型和价值类的父类。
          * AnyRef ，所有引用类型的父类。
          * 通用特征（universal trait），新引入的 trait 类型，用于我们在 8.2 节讨论的特殊用途。
          */
        var anyRef: AnyRef = "月姬" //使用一个String类型的值给AnyRef类型的anyRef赋值
        anyRef = List(1, 2, 3) //使用List(1,2,3)再次赋值
        //        anyRef = 10 //编译错误(type mismatch): 类型不匹配
        //可以看出,所有的引用类型都是可以赋值给anyRef的,即: AnyRef是所有引用类型的父类
        var anyVal: AnyVal = 10 //使用Int字面量10给AnyVal类型的anyVal赋值
        anyVal = false //使用Boolean字面量false给anyVal再次赋值
        //        anyVal = "12" //编译错误(type mismatch): 类型不匹配
        //可以看出,AnyVal是所有值类型的子类
        var any: Any = anyRef //使用AnyRef类型的变量anyRef给Any类型的变量any赋值
        any = anyVal //使用AnyVal类型的anyVal给any再次赋值
        //可以看出,Any是所有类型的父类(根类).

        /**
          * AnyVal 有九个具体子类，称为值类型。
          * 其中七个是数字值类型： Byte 、Char 、Short 、Int 、Long 、Float 和 Double 。
          * 余下的两个是非数字值类型， Unit 和 Boolean 。
          */
        //定义一些AnyVal(值类型)
        val b: Byte = 10
        val ch: Char = 'a'
        val sh: Short = 14
        val i: Int = 15
        val l: Long = 14L
        val f: Float = 15F
        val d: Double = 15D
        val boo: Boolean = false
        val u: Unit = ()
        //遍历出他们的值和类型
        List(b, ch, sh, i, l, f, d, boo, u).foreach(x =>
            println(s"$x: ${x.getClass}"))

        /**
          * Scala 2.10 引入了用户自定义的值类，该值类继承自 AnyVal 。
          * 相反，所有其他类型均为引用类型。
          * 它们派生自 AnyRef ， AnyRef 是 java.lang.Object的别名。
          * （ http://docs.oracle.com/javase/8/docs/api/java/lang/Object.html ）
          * 在 Java 的对象模型中， Object 并没有一个封装了原生类型和引用类型的父类，
          * 因为 Java 对原生类型(原语类型)做了特殊处理。
          */
        //定义一些AnyRef类型的值
        val integer: Integer = new Integer(5) //定义一个Integer的值
        val pair: (String, Double) = ("琉璃", 15) //定义一个Scala的元组
        //打印它们的值和类型
        List(integer, pair).foreach(x => println(s"$x: ${x.getClass}"))

        /**
          * 在 Scala 2.10 之前，
          * 编译器对所有 Scala 的引用类型混入了名为 ScalaObject 的 marker 特征。
          * Scala 2.11 之后，编译器将不再这么做，该 trait 将会被移除。
          */
        //正因为这样,所以我们可以编写通用程序
        //下面是一个获取AnyVal类型变量的值和类型的方法
        def getTypeValue(anyVal: AnyVal): String =
        s"${anyVal.getClass.getName}($anyVal)"

        println(getTypeValue())
        println(getTypeValue(10)) //注: 此处的类型为java.lang.Integer
        println(getTypeValue(false)) //此处的类型为java.lang.Boolean

        //然而,函数的参数默认是val
        def foo(array: Array[Int]): Unit = {
            //            array = array.map(_ * 2)
            var arr = array
            arr = array.map(_ * 2)
        }

        val array = Array(1, 2, 3, 4)
        foo(array)
        println(array.mkString("\t")) //原数组没有发生改变
        //尝试使用引用传递?
        var arr = array
        arr = arr.map(_ * 2) //此处,发生了什么?
        println(s"array: ${array.mkString("\t")}") //原数组依旧没有发生改变
        println(s"arr: ${arr.mkString("\t")}") //新的数组发生了变化
        /**
          * 思考: Scala中不存在引用传递?
          * 从上面的两个例子中可以看出来,Scala的高阶函数并没有改变原容器的值,而是返回了一个新的容器.
          */
    }
}
