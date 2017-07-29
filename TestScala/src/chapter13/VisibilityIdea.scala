package chapter13

/**
  * Created by rxliuli on 17-6-12.
  */
// 对可见性的想法
object VisibilityIdea {
  def default(): Unit = {
    /**
      * Scala 的可见性声明非常灵活,而且这些声明表现一致。
      * 从实例级( private[this] )到包级( private[P] , P 为对应的包名),
      * 这些可见性规则在各种作用域里均提供了细粒度控制约束。
      * 例如:使用这些可见性声明能够很容易地创建可重用组件,
      * 这些组件的类型对外暴露给了组件最顶层包之外的代码,但实现类型和类型成员隐藏在组件包中。
      */
    /**
      * 尽管标准库之外的代码中并未广泛使用这些细粒度的可见性约束,但它们理应得到广泛使用。
      * 假如你正在编写自己的 Scala 库,那么请思考一下：
      * 哪些类型和方法不应对客户开放,之后请为这些类型和方法添加合适的可见性规则。
      * 注：有强大的工具而不会使用！！！
      */
    //最后，稍微说一下关于 trait 中 private 成员的一个潜在问题(Scala 现在解决了。。。)
    trait A {
      private val s: String = "a"
    }
    trait B {
      private val s: String = "b"
    }
    class C extends A with B //实际上私有成员没有被继承/混入，所以不会发生成员冲突

    //如果不是私有成员。。。
    trait D {
      val i: Int = 0
    }
    trait E {
      val i: Int = 1
    }
    //    class F extends D with E
    /**
      * 如果是公有的成员同名，那么依旧会发生，当然编译器也能捕获命名冲突这一问题：
      * inherits:继承 conflicting:冲突 members:成员(复数) note:注意 resolved:解决 declaring:声明
      * Error: class C inherits conflicting members:
      * value s in trait A of type String  and
      * value s in trait B of type String
      * (Note: this can be resolved by declaring an override in class C.)
      * class C extends A with B
      */

    //解决方案很简单，在子类中覆盖掉继承的所有的同名成员
    class G(override val i: Int) extends D with E
    
  }
}
