package chapter13

/**
  * Created by rxliuli on 17-6-12.
  */
// 默认访问修饰符
object DefaultVisibility {
  def default(): Unit = {
    // Scala 中的默认访问修饰符是 public，所以无需加任何东西都可以直接在任意地方访问它们
    class Dog(val name: String) {
      override def toString: String = s"Dog($name)"

      // 然而，我们不能加上公有的 public 访问修饰符。。。
      //      public def show(): Unit = println(name)
    }
    // 这样我们便可以随意访问其公开成员
    val dog = new Dog("旺旺")
    println(dog.name)
    println(dog)

    //如果我们加上一个访问修饰符
    class Dog2(val name: String) {
      override protected def toString: String = s"Dog2($name)"
    }

    //使用了访问修饰符之后
    val dog2 = new Dog2("旺旺2")
    println(dog2.name)
    println(dog2) //虽然没有直接报错，然而实际上程序在编译期还是发生了错误
  }
}
