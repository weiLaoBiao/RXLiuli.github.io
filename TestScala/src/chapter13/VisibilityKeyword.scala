package chapter13

/**
  * Created by rxliuli on 17-6-12.
  */
// 可见性关键字
object VisibilityKeyword {
  def default(): Unit = {
    /**
      * Scala 中,你会在类型的class 或 trait 关键字之前、字段的 val 或 var 之前、
      * 方法定义的 def 关键字之前找到这些可见性关键字。
      */
    // 看如下示例：
    class Employee(private val name: String)
    val e = new Employee("旺旺")
    // 无法访问类的私有成员
    //    println(e.name)

    // 甚至，我们可以在类的主构造函数中使用访问修饰符
    class Employee2 private(val name: String) {
      override def toString: String = s"Employee2($name)"
    }
    object Employee2 {
      def apply(name: String): Employee2 = new Employee2(name)

      def unapply(arg: Employee2): Option[String] =
        if (arg == null) None else Some(arg.name)
    }
    val e2 = Employee2("旺旺2")
    println(e2)

    /**
      * 可见性
      * public            无关键          字任何作用域内均能访问公有成员或公有类型, public 可见性无视所有限制
      * protected         protected      受保护( protected )成员对本类型、继承类型以及嵌套类型可见。而受保护的类型则只对相同包内或子包内的某些类型可见
      * private           私有(private)   成员只对本类型和嵌套类型可见,而且可以访问私有成员的类型必须位于相同包内
      * protected[scope]  作用域内受保护    例,如果类型使用该可见性,则 this 表示该类型所在的包)。如果想了解更多细节,请阅读下面的文字
      * private[scope]    作用域内私有      除了对具有继承关系的类的可见性不同之外(会在后续说明),与作用域内受保护可见性相同
      */

    // public 可见性
    class EmployeePublic(val name: String) {
      override def toString: String = s"EmployeePublic($name)"
    }
    // 因为默认是可见，所以可以直接访问
    val employeePublic = new EmployeePublic("琉璃 EmployeePublic")
    println(employeePublic.name)
    println(employeePublic.toString) // 不管是字段还是方法都可以直接访问

    // protected 可见性
    class EmployeeProtected(protected val name: String) {
      override def toString: String = s"EmployeePublic($name)"
    }
    val employeeProtected = new EmployeeProtected("琉璃 EmployeeProtected")
    //    println(employeeProtected.name) // 无法直接访问 protected 修饰的 name 字段
    //然而。。。
    class EmployeeProtectedSub(override val name: Predef.String) //注：此处我们重写了字段 name
      extends EmployeeProtected(name) {
      //是的，在子类里面我们可以访问到父类的字段 name
      override def toString: String = s"EmployeeProtected($name)"
    }
    val employeeProtectedSub = new EmployeeProtectedSub("琉璃 EmployeeProtectedSub")
    println(employeeProtectedSub.name) //现在我们可以直接访问字段 name
    println(employeeProtectedSub.toString)

    // private 可见性
    class EmployeePrivate(private val name: String)
    val employeePrivate = new EmployeePrivate("琉璃 EmployeePrivate")
    //    println(employeePrivate.name) // 类的私有成员也是无法访问的
    //使用子类继承也不能继承类的私有成员
    class EmployeePrivateSub(val name: String) // 注：我们又声明了一个名为 name 的字段
      extends EmployeePrivate(name) { // 继承 EmployeePrivate 类
      override def toString: String = s"EmployeePrivateSub($name)"

      //当然，使用 super 关键字也绝对无法访问类的私有成员！
      //      def showSup(): Unit = println(s"Super: ${super.name}")
    }
    //然而，我们可以正常访问子类的字段 name，虽然和父类的同名字段 name 完全没什么关系就是了
    val employeePrivateSub = new EmployeePrivateSub("琉璃 EmployeePrivateSub")
    println(employeePrivateSub.name)
    println(employeePrivateSub.toString)

    // 作用域内私有和作用域内受保护
    /**
      * Scala 为用户提供了一些额外方法,以帮助用户以更小的粒度对可见性的作用域进行调整。
      * 从这一点看, Scala 超过了大多数的语言。
      * Scala 提供了作用域内私有( scoped private )可见性声明和作用域内受保护( scoped protected )可见性声明。
      * 请注意,在具有继承关系的情况下,对类成员应用这两类可见性后表现不同。
      * 但除此之外,这两类可见性的表现完全一致,因此在同一个作用域内,私有可见性可以和受保护可见性交换使用。
      */

    //最后，稍微试验一下：
    class TestDog {
      //测试空值是否可以调用方法
      def show(): Unit = println("hello world")
    }
    val testDog: TestDog = null
    //    testDog.show() //看来空指针引用是不可行的
  }
}

//之所以要写在类的外面是因为我们需要写包 package
package Test {

  //那么，先看一下两者的主要差异部分：继承
  class Person {
    private[Test] val name: String = ""
    protected[Test] val name2: String = ""
    private[Person] val age: Int = 0
    protected[Person] val age2: Int = 0
    private[this] val sex: Boolean = false
    protected[this] val sex2: Boolean = false

    def equalsPrivateField(than: Person): Boolean =
      if (this == null) false
      else name == than.name && //包私有化是最开放的私有化（private）了
        age == than.age // 是的，private[Person] 和直接使用 private 并没有什么区别，只要在同一个类就可以正常访问
    /* && sex = than.sex */
    //最强私有化，只要不是同一个对象就不能访问
  }

  class Student extends Person {
    val nameSub: String = name //因为是在同一个包，所以可以访问 private[包]
    val nameSub2: String = name2
    //      val ageSub: Int = age //不在同一个类就不能访问了
    val ageSub2: Int = age2
    //      val sexSub: Boolean = sex
    val sexSub2: Boolean = sex2
  }

}

//另外一个包
package Test2 {

  class Student2 extends Test.Person {
    //现在，这个也发生了错误。。。
    //    val nameSub: String = name //不在同一个的话包子类连包私有的字段都不能访问
    val nameSub2: String = name2
    //      val ageSub: Int = age
    val ageSub2: Int = age2
    //      val sexSub: Boolean = sex
    val sexSub2: Boolean = sex2
  }

}

//然而，如果是子包的话也是可以访问 private[包] 的字段的
package Test.Test3 { // Test3 是 Test 的子包

  class Student2 extends Test.Person {
    val nameSub: String = name // 现在子类又可以进行访问了
    val nameSub2: String = name2
    //      val ageSub: Int = age
    val ageSub2: Int = age2
    //      val sexSub: Boolean = sex
    val sexSub2: Boolean = sex2
  }

}
