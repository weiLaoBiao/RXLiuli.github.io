package chapter09

/**
  * Created by RXLiuli on 2017/5/16.
  */
//构造trait
object ConstructorTrait {
    def default(): Unit = {
        /**
          * 尽管 trait 定义体起到了主构造函数的作用，
          * 不过 trait 主构造函数并不允许为其提供参数列表，而你也无法为其定义辅助构造函数。
          */
        println(s"Creating C12:")
        new C12 //线性化的执行方式
        //从左到右依次执行构造方法()
        println(s"After Creating C12")
        /**
          * 因此，尽管我们无法向 trait 传递构造参数，但我们可以在特征体中初始化字段、方法和类型。
          * 线性化算法则允许声明中后续列出的 trait 或类覆写这些定义。
          * 假如某个trait或抽象父类中存在某些抽象成员,后续出现的trait或类必须对这些成员进行定义。
          * 这是因为具体类中不允许出现任何抽象成员。
          */
        //例:
        trait A {
            def foo(): Unit
        }
        trait B extends A
        trait C extends A {
            override def foo(): Unit = println("C execute!")
        }
        class D
        //        val d = new D with A //编译错误,因为存在抽象成员
        //编译错误,虽然B继承了A,然而并没有重写A的抽象方法foo
        //        val d2 = new D with A with B
        //可以正常运行
        val d3 = new D with A with B with C
        d3.foo() //确实,trait C 中的 foo 将前面混入的特征的方法覆盖了
        //当然,即便特征已经被后续特征实现,你依旧可以重写这些方法
        val d4 = new D with A with B with C {
            override def foo(): Unit = println("d4 execute!")
        }
        //然而,如果你混入特征的顺序写错了,后面特征的抽象方法也可以覆盖前面特征的具体方法
        //        val d5 = new D with B with A

        /**
          * 请不要在 trait 中声明那些无法在初始化时指定合适默认值的具体字段。
          * 如果需要声明这类字段，请使用抽象字段，或者将该 trait 改成含有构造函数的类。
          * 当然，对于那些不包含状态信息的 trait 而言，初始化时不会遇到这些问题。
          */
    }

    trait T1 {
        println(s" in T1: x = $x")
        val x = 1
        println(s" in T1: x = $x")
    }

    trait T2 {
        println(s" in T2: y = $y")
        val y = "T2"
        println(s" in T2: y = $y")
    }

    class Base12 {
        println(s" in Base12: b = $b")
        val b = "Base12"
        println(s" in Base12: b = $b")
    }

    class C12 extends Base12 with T1 with T2 {
        println(s" in C12: c = $c")
        val c = "C12"
        println(s" in C12: c = $c")
    }

}
