package chapter08

/**
  * Created by RXLiuli on 2017/5/28.
  */
//内部类
object InnerClass {
    def default(): Unit = {
        //创建一个外部类的实例
        val outer = new Outer("outer")
        //是的,Inner从属于对象而非类,每个外部类的对象都可以有不同的内部类对象
        val inner = new outer.Inner("inner")
        //使用内部类的实例 inner 去访问 foo()
        inner.foo(inner)

        //使用伴生对象中的 apply 方法
        val person = Person()
        //是的,即便是使用 Person 类中的 apply 方法,也是不需要显式的写出方法名 apply 的.
        val person2 = person()
        person2()
    }

    //外部类
    class Outer(private[this] val name: String) {
        //注: 即便是 private[this] 修饰内部类也是可以访问的
        outer => //Outer类型实例的别名,类似于this,不过在内部类中也可以使用该别名访问外部类的成员
        //内部类
        class Inner(val name: String) {
            def foo(b: Inner): Unit = println(s"Outer: ${outer.name} Inner: ${b.name}")
        }

    }

    //Person类
    class Person {
        //在 class Person 中定义apply方法,会发生什么呢?
        def apply(): Person = {
            println("class Person")
            new Person
        }
    }

    //伴生对象Person
    object Person {
        def apply(): Person = {
            println("object Person")
            new Person
        }
    }

}
