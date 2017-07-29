package chapter14


/**
  * Created by rxliuli on 17-6-12.
  */
// 参数化类型
object ParameterizationTypes {
  def default(): Unit = {
    // 变异标记
    class CSup
    class C extends CSup
    class CSub extends C
    class D[+T] // 协变
    class F[-T] // 逆变
    class E[T] // 不变
    // 如果类是协变的，那么，使用类的参数的子类型的参数会使类的实例也变成子类
    // 此处定义了一个方法，接受一个 D[C] 类型的参数也同时返回一个 D[C] 类型的实例
    def foo(d: D[C]): D[C] = new D[C]

    foo(new D[CSub])
    //    foo(new D[CSup])
    foo(new D[C])
    // 因为 D 类型是协变的，所以我们可以使用 C 和 C 的子类作为 D 类型的参数，
    // 以此 D 的实例自然也会变成 D[C] 的实例，那么也就可以作为函数 foo 的参数了

    // 如果是逆变就正好相反了
    // 此处定义了一个接受 F[C] 类型
    def foo2(d: F[C]): F[C] = new F[C]

    //    foo2(new F[CSub])
    foo2(new F[CSup])
    foo2(new F[C])


    //类型构造器
    /**
      * 有时候,你会在参数化类型中遇到类型构造器 ( type constructor )这一术语。
      * 它反映了参数化类型创建特定类型的方式,这与用于生成类的实例构造器大致类似。
      * 例如: List 是 List[String] 和 List[Int] 的类型构造器,通过 List 构造了两个不同的类型。
      * 事实上,你可以说,所有的类都是类型构造器。
      * 那些不带类型参数的,可以看做带了零个类型参数的 “ 参数化类型 ” 。
      */
    val listInt = List(1, 2, 3)
    val listString = List("1", "2", "3")
    println(s"listInt: ${listInt.getClass},listString: ${listString.getClass}")

    //类型参数的名称
    /**
      * 请使用描述性的词来命名类型参数。
      * Scala 初学者往往抱怨,在 Scala 的实现以及 Scaladoc 中类型参数名称太过简单,
      * 如:给 List.+: 方法的类型参数命名为 A 和 B 。
      * 不过,你很快就能学会如何解释这些符号,它遵循一些简单的规则。
      * (1) 为非常通用的类型参数(例如:表示容器元素的类型参数),使用 A 、 B 、 T1 、 T2 等单字母或双字母的名字。
      * 请注意,元素的类型与容器的类型并没有太紧密的联系。
      * 无论 List 保存的是字符串、数字或其他字符串,都不影响其工作方式。
      * 这一解耦( decouple )使得 “ 泛型编程 ” 成为可能。
      * (2) 对那些与容器密切相关的类型使用更具描述性的名称。
      * 也许,当你第一次遇到 List.+: 这个方法签名时,它并没有表现出明显的意义,
      * 但一旦学习了 12.3 节中我们对设计常用方法的讨论,你就会明白 List.+: 的含义。
      */

  }
}
