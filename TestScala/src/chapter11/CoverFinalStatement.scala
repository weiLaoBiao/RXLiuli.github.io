package chapter11

/**
  * Created by RXLiuli on 2017/5/22.
  */
//尝试覆盖final声明
object CoverFinalStatement {
    def default(): Unit = {
        /**
          * 在 Scala 类库中，某些类型被声明为 final 类型。
          * 这些类型包括某些 JDK 类（如 String 类型），
          * 以及继承自 AnyVal 类型的所有 “ 值 ” 类型（请参考 10.2 节）。
          */
        /**
          * 实现抽象方法时，是否应该使用 override 关键字呢？
          * 我认为不应使用。假设 Widget 类的维护人员将来决定提供默认的 draw 实现方法，
          * 实现该方法的目的也许是为了记录每次调用的情况。
          * 如果你已经对 draw 方法进行了覆写，编译器会默认你目前确实要对该具体方法进行覆写，
          * 而你永远不会知道父类的这个变更。
          * 不过，如果你未使用 override 关键字，那么当抽象方法 draw 突然有了实现体之后，
          * 你的代码会无法通过编译，因此你知道这个变更。
          */
    }

    //fixed:确定的 illegally:非法的 inheritance:继承 from:从,来自
    //被final修饰的类无法被继承
    final class NotFixed {
        def fixedMethod = "fixed"
    }

    class Animal {
        final val i = 0 //final修饰的字段不能被重写
        val k = ""

        //final修饰的方法不能被重写
        final def eat(): Unit = println("Animal is eat!")
    }

    class Dog extends Animal {
        //        override val i = 10
        override val k: String = "liuli"
        //        override def eat(): Unit = println("Dog is eat!")
    }

    //    class Fixed extends NotFixed


}