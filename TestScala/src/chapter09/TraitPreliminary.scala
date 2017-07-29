package chapter09

/**
  * Created by RXLiuli on 2017/5/13.
  */
//特征初步
object TraitPreliminary {
    def default(): Unit = {
        /**
          * 通常，接口中定义了一些与实现类中的其他成员无关联的成员（这些成员具有 “ 正交性 ” ）。
          * 而 混入 （ mixin ）一词便适用于这类聚焦的、可重用的状态和行为。
          * 理想情况下，我们应单独维护这些公用的行为，而不应该依赖于任何使用这些行为的具体类型。
          */
        val b: A = new B() //声明A类型变量,实际上是B类型
        val c: C = new C() //声明A类型变量,实际上是C类型

        val d = new D(b)
        d.foo() //多态应用
        val d2 = new D(c)
        d2.foo()
        d.foo2(d.toString) //里氏替换
        /**
          * Java 8 做出了改变。
          * 现在我们可以在接口中定义方法，这些方法被称为 defender 方法或默认方法。
          * 实现类仍可以提供自己的实现。如果实现类未提供自己的实现的话， defender 方法会被调用。
          * 因此， Java 8 中的接口行为更接近于 Scala 中的 trait 。
          * 但是， Java 8 中的接口与 Scala 中的 trait 仍有不同之处。
          * Java 8 中的接口只能定义静态字段，而 Scala 中的 trait 则可以定义实例级字段。
          * 这意味着Java 8 中的接口无法管理实例状态。接口实现类必须提供字段以记录状态。
          * 这也意味着 defender 方法无法访问接口实现体的状态信息，从而限制了defender 方法的用途。
          */
    }

    //特征是什么?
    trait A {
        type T //定义类型T(抽象)
        val t: T //声明T类型的变量
        val s = "灵梦" //定义具体的字段s

        def show(): Unit //可以定义抽象方法
        //也可以定义非抽象的(不需要强制重写)
        def show(s: String): Unit = println(s"show $s") //重载
    }

    class B extends A {
        type T = String //确定T的具体类型(override不是必须的)
        val t = "亚当" //确定t字段的具体值
        override val s: String = "月姬" //重写非抽象字段,必须使用override
        def show(): Unit = println("hello B") //重写抽象字override不是必须的
    }

    class C extends A {
        override type T = Int
        override val t = 10 //确定T类型不是必须的(类型推断)(好像是必须的...)
        def show(): Unit = println("hello C")

        //重写非抽象方法override是必须的
        override def show(s: String): Unit = println(s"show C: $s")
    }

    class D(a: A) {
        def foo(): Unit = a.show() //使用字段a调用show方法
        def foo2(s: String): Unit = a.show(s) //动态绑定
    }

}
