package chapter04

/**
  * Created by RXLiuli on 2017/4/21.
  */
//地址类
case class Address(street: String, city: String, country: String)

//人类
//case class Person(name: String, age: Int, address: Address)

//不使用实现一个case类: 人类
//注: 此处属性全部使用val,case类所有的属性默认全部为val(不可变变量)
class Person(val name: String, val age: Int, val address: Address)

object Person {
  //Person的工厂方法
  def apply(name: String, age: Int, address: Address): Person = {
    new Person(name, age, address)
  }

  /**
    * 既然编译器知道对象是Person,为什么unapply的返回值还要用Option呢?
    * 因为Scala允许unapply方法"否决"这个匹配,返回None,这时,Scala会使用下一个case子句
    * 另外,如果我们不希望的话,可以不必暴露age.unapplySeq会有详细的细节
    * 结论: 被提取的属性将以Some返回
    * 附: 为了获得性能上的优势,Scala 2.11.1放松了unapply必须返回Option[T]的要求.
    * 只要该类型具有以下方法:
    * def isEmpty:Boolean
    * def get: T
    * 然而吾辈用的是Scala 2.11.0,2333333
    */
  //Person的解构方法
  //元组返回值的标准写法Option[Tuple3[String,Int,Address]],下面是元组字面语法
  def unapply(p: Person): Option[(String, Int, Address)] = {
    Some((p.name, p.age, p.address))
  }

  //....
}

//实验,创造一个不是Option类型的unapply方法
class B(val i: Option[Int] = Some(0), val str: Option[String] = None) {
  def isEmpty: Boolean = {
    !i.contains(0) || str.isDefined
  }

  def get(): B = {
    this
  }
}

object B {
  def unapply(b: B): B = {
    new B()
  }
}


object CaseClassMatch {
  def default(): Unit = {
    val alice =
      Person("Alice", 25, Address("1 Scala Lane", "Chicago", "USA"))
    val bob =
      Person("Bob", 29, Address("2 Java Ave.", "Miami", "USA"))
    val charlie =
      Person("Charlie", 32, Address("3 Python Ct.", "Boston", "USA"))

    /**
      * case关键字被同时用于声明一种"特殊"的类,又用于match表达式中的case表达式,
      * 这并不是偶然....case类的特性就是为更便捷地进行模式匹配而设计的
      * 除了Scala库中的类之外,我们自定义的case类也可以使用类型匹配和提取功能,甚至支持深度嵌套
      * case类有一个伴生对象,伴生对象中一个名为apply的工厂方法,用于构造对象
      * 基于"对称"的观点,我们可以推断,一定还有一个名为unapply的自动生成的方法,
      * 用于提取和"解构".事实上,确实存在这样的一个用于提取的方法
      * 当遇见如下形式的类型匹配表达式时,该方法就会被调用:
      */
    val person = Person("liuli", 15, Address("4 Lisp.", "RX", "CN"))
    person match {
      /**
        * 这儿到底发生了什么?
        * Scala找到了Person.unapply(...)和Address.unapply(...),然后调用这两个函数,
        * 所有的unapply方法都返回Option[TupleN(...)],此处的N表示可以从对象中提取的值的个数
        * 在Person类中,N=3,被提取的值的类型和相对应位置元组元素的类型一致.
        * 对于Person而言,提取值的类型分别为:String,Int和Address
        * 所以,编译器生成的Person的伴生对象是这样的
        */
      case Person("liuli", 15, Address("4 Lisp.", "RX", "CN")) =>
        println(s"name: ${person.name} age: ${person.age}")
      //....
    }


    for (person <- Seq(alice, bob, charlie)) {
      person match {
        case Person("Alice", 25, Address(_, "Chicago", _)) =>
          println("Hi Alice!")
        case Person("Bob", 29, Address("2 Java Ave.", "Miami", "USA")) =>
          println("Hi Bob!")
        case Person(name, age, _) =>
          println(s"Who are you, $age year-old person named $name?")
      }
    }
    //代码正常运行....


  }
}

