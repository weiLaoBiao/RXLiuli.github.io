package chapter02

/**
  * Created by RXLiuli on 2017/4/6.
  */
object Parameterization {
    def default(): Unit = {
        /*
        //声明一个列表,参数是Int,表示列表只允许Int类型的元素
        val list = List[Int](1, 2, 3, 4, 5, 6, 7)

        //根据方法参数化类型,由方法指定参数类型
        def addRange[T](list: List[T], i: T*): List[T] = {
            list ::: i.toList //连接两个列表
        }

        //产生一个新的List,而非改变原List
        addRange[Int](list, 8, 9, 10).foreach(printf("%d ", _))
        list.foreach(printf("%d ", _))
        */
        /* 编写通用方法

        val r: R = new AParameterization("月姬")

        def myFor(r: R): Unit = {
            r.show(12)
        }

        myFor(r)
         */
    }
}

//类型字面量
abstract class R {
    //定义了一个类型T
    type T
    //定义了一个T类型的属性t
    val t: T

    //定义了一个抽象方法
    def show(): Unit

    //定义一个重载的非抽象方法
    def show(i: Int): Unit = {
        println(s"Int类型: $i $t")
    }
}

class AParameterization extends R {
    //确定T类型的实际类型
    type T = String
    //确定T的实际值
    val t: T = "月姬"

    //实现抽象方法
    def show(): Unit = {
        println(s"类型: $t")
    }

    //重写非抽象方法
    override def show(i: Int): Unit = {
        println(s"名字: $t 值: $i")
    }
}

//使用参数化类型
abstract class R2[T] /*此处定义了参数化类型T*/ {
    //定义T类型的属性
    val t: T

    //定义抽象方法
    def show()

    //定义重载的非抽象方法
    def show[K](k: K): Unit = {
        print(s"T的值: $t K的值: $k")
    }
}

//class R2Parameterization[String](val t: String) /*此处已经实现T类型的属性*/ extends R2 {
//
//    //实现show方法
//    override def show(): Unit = {
//        println(s"R2子类的值: $t ")
//    }
//}