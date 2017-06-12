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
      //重写的方法不能比父类的方法可访问性更低
      //      override protected def toString: String = s"Dog2($name)"
    }

    //使用了访问修饰符之后
    val dog2 = new Dog2("旺旺2")
    println(dog2.name)
    //    println(dog2) //虽然没有直接报错，然而实际上程序在编译期还是发生了错误

    /**
      * 传统的面向对象设计认为,字段应该是 private 或 protected 可见性。
      * 假如需要对该字段进行访问,我们应该提供访问方法,而不是把所有的字段默认为可访问。
      * 存在两个理由支撑这一传统。
      * 第一个理由是:这样做能够阻止用户未经许可便对可变变量进行修改。不过,使用不可变值也可以消除这一顾虑。
      * 而第二个理由是:字段只是实现的一部分,它们并不是你希望对外暴露的公有抽象体。
      */
    /**
      * 如果允许直接访问字段,那么统一访问原则的优势便体现出来了。
      * 运用统一访问原则,我们可以让用户使用公用字段访问语法对字段进行访问,
      * 而在实现时可以根据情况,采用方法访问或直接访问的方式,用户则无需知道具体的实现方式。
      * 尽管仍然需要重新编译代码,但我们可以在不告知用户的情况下修改具体的实现。
      */
    //考虑以下示例：
    class Dog3(_name: String) {
      //我们可以将 name 更改为一个公开的字段
      var name: String = _name

      //即便在方法内部使用的也是字段
      override def toString: String = s"Dog3($name)"
    }
    val dog3 = new Dog3("旺旺3")
    println(dog3.name) //我们直接访问了字段
    println(dog3)

    /**
      * 使用类型的 “ 用户 ” 存在两种:
      * 组合：将该类型的一个实例作为一个字段成员并以此访问该类的所有成员
      * 继承：继承自该类型的类型,以及使用该类型实例的代码。与操作实例的用户相比,继承类型往往需要对父类型成员进行更多次的访问。
      */
    // 一个用于测试的 Home 类
    case class Home(city: String, detail: String)
    // 组合：
    class Dog4(val name: String, city: String, detail: String) {
      //声明了一个可变的字段
      var home = Home(city, detail)

      override def toString: String = s"Dog4($name,$home)"
    }
    //使用组合的效果
    val dog4 = new Dog4("旺旺4", "广州", "天源路")
    println(s"${dog4.home.city},${dog4.home.detail}")
    println(dog4)

    // 继承：
    class Dog5(val name: String, city: String, detail: String) extends Home(city, detail) {
      override def toString: String = s"Dog5($name,${Home(city, detail)})"
    }
    //使用继承的效果
    val dog5 = new Dog5("旺旺4", "广州", "天源路")
    println(s"Dog5(${dog5.city},${dog5.detail})")
    println(dog5)
  }
}
