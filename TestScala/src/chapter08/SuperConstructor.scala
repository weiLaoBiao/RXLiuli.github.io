package chapter08

/**
  * Created by RXLiuli on 2017/5/10.
  */
//调用父类的构造器(与良好的面向对象设计)
object SuperConstructor {
    def default(): Unit = {
        val a = Address("98765", "Anytown")
        val a2 = Address("98765")
        val ceo = new Employee("Joe CEO", title = "CEO")
        println(new Employee("Buck Trends1"))
        println(new Employee("Buck Trends2", Some(20), Some(a)))
        println(new Employee("Buck Trends2", Some(20), Some(a), "Zombie Dev"))
        println(new Employee("Buck Trends2", Some(20),
            Some(a), "Zombie Dev", Some(ceo)))
        /**
          * 在 Java 中，我们会定义构造方法，并用 super 调用父类的初始化逻辑。
          * 而 Scala 中，我们用 ChildClass(…) extends ParentClass(…)
          * 的语法隐式地调用父类的构造器。
          * 注: 尽管像在Java中一样,super可以用来调用被覆写的父类方法,但它不能用来调用父类的构造器.
          */
        /**
          * 这段代码有 “ 异味 ” 。
          * Employee 的声明把带 val 关键字的参数和不带关键字的参数混杂在参数列表中。
          * 但更深层次的问题潜伏在这段代码背后。
          * 我们可以从一个 case 类派生出一个非 case 类，或者反过来。
          * 但我们无法从一个 case 类派生出另一个 case 类。
          * 这是因为，自动生成的 toString、 equals 和 hashCode 方法在子类中无法正常工作。
          * 这意味着它们忽视了这种可能性，即实例实际上可以是 case 类的子类。
          */
        //        case class A()
        //        case class B() extends A //实际上会在编译时报错...
        //        val tempA = B() //貌似是,可以的?!
        /**
          * 这种问题实际上是设计上的问题。它反映了子类继承的问题。
          * 例如：具有相同姓名、年龄、地址的 Employee 类的实例和
          * Person 类的实例是否应该被视为等价的呢？
          * 如果对对象平等做宽松地解释，我们认为它们是相等价的；
          * 如果更严格地解释，我们则会说它们不相等。
          * 事实上，相等的数学定义需要满足交换律：
          * somePerson == someEmployee 与 someEmployee == somePerson 应该返回相同的结果。
          * 灵活的等价解释将打破这种交换律，
          * 因为你绝不会想到一个 Employee 实例会与一个不是 Employee 的 Person 实例相等。
          */
        //比如说:
        val p1 = Person("月姬", Some(1000), None)
        val e1 = new Employee("月姬", Some(1000), title = "真祖")
        println(s"p1 == e1? ${p1 == e1}")
        println(s"e1 == p1? ${e1 == p1}")
        //结果全部为true,因为Employee类的equals和hashCode是从Person中继承到的
        //然而,Person中的equals方法比较的只是主构造器的参数是否相同,而不管Employee的独有字段
        //是的,我们实际上是把所有的Employee的实例当作Person的实例来对待的!!!
        /**
          * 这对于小的类型而言是非常危险的。
          * 不可避免地会有人创建 Employee 集合，在集合中对元素进行排序，或将元素作为散列映射的键。
          * 由于这会分别调用 Person.equals 和 Person.hashCode ，
          * 所以当出现两个人同名为 John Smith ,一人是 CEO ,另一人在收发室工作时,就会出现异常行为.
          * 混淆两个人的场景经常发生，但还没有经常到能够很容易地复现以帮助修复这个 bug 的程度！
          */
        /**
          * 当然，继承机制的问题存在已久。如今，好的面向对象设计更青睐于组合，而不是继承。
          * 因此，我们选择将功能单元组合起来，而不是构建一个类型继承结构。
          * Scala 的团队可以选择实现对子类友好的 equals 、 hashCode 和 toString ，
          * 但这在支持糟糕设计时会增加额外的复杂度。
          * case 类为方便且简单的域类型提供了模式匹配和实例分解，但支持继承结构并不是它们的目的。
          */
        //如果可以使用组合就不要使用继承.
        /**
          * 当使用继承时，建议遵循以下规则。
          * (1) 一个抽象的基类或 trait ，只被下一层的具体的类继承，包括 case 类。
          * (2) 具体类的子类永远不会再次被继承，除了两种情况：
          * ----a. 类中混入了定义于 trait （见第 9 章）中的其他行为。
          * 理想情况下，这些行为应该是正交的，即不重叠的。
          * ----b. 只用于支持自动化单元测试的类。
          * (3) 当使用子类继承似乎是正确的做法时，考虑将行为分离到 trait 中，然后在类里混入这些 trait 。
          * (4) 切勿将逻辑状态跨越父类和子类。
          * 对最后一条中的 “ 逻辑 ” ，我指的是，我们可能有一些私有的，专门实现的状态。
          * 这种状态不影响外部可见性、相等、散列等逻辑。
          * 例如：我们的库中可能有某些集合类型的子类，增加了私有的字段，
          * 用于实现缓存或日志功能（此时通过混入 trait 实现该特性并不是一个好的选择）。
          */
        //尝试使用改进后的Person2和Employee2
        val ceoAddress2 = Address2("1 Scala Lane", "Anytown", "CA", "98765")
        // 结果 : ceoAddress2: oop2.Address2 = Address2(1 Scala Lane,Anytown,CA,98765)
        val buckAddress2 = Address2("98765")
        // 结果 : buckAddress2: oop2.Address2 = Address2([unknown],Anytown,CA,98765)
        Employee2("", Some(1), Some(ceoAddress2), "", None)
        val ceo2 = Employee2(
            name = "Joe CEO", title = "CEO", age = Some(50),
            address2 = Some(ceoAddress2), manager = None)
        // 结果 : ceo: oop2.Employee2 = Employee2(Joe CEO,Some(50),
        // Some(Address2(1 Scala Lane,Anytown,CA,98765)),CEO,None)
        val ceoSpouse = Person2("Jane Smith", address2 = Some(ceoAddress2))
        // 结果 : ceoSpouse: oop2.Person2 = Person2(Jane Smith,None,
        // Some(Address2(1 Scala Lane,Anytown,CA,98765)))
        val buck = Employee2(
            name = "Buck Trends", title = "Zombie Dev", age = Some(20),
            address2 = Some(buckAddress2), manager = Some(ceo2))
        // 结果 : buck: oop2.Employee2 = Employee2(Buck Trends,Some(20),
        // Some(Address2([unknown],Anytown,CA,98765)),Zombie Dev,
        // Some(Employee2(Joe CEO,Some(50),
        // Some(Address2(1 Scala Lane,Anytown,CA,98765)),CEO,None)))
        val buckSpouse = Person2("Ann Collins", address2 = Some(buckAddress2))
        // 结果 : buckSpouse: oop2.Person2 = Person2(Ann Collins,None,
        // Some(Address2([unknown],Anytown,CA,98765)))

    }

    case class Address(zip: String, city: String = "[unknown]")

    //Person类
    case class Person(name: String, age: Option[Int], address: Option[Address])

    class Employee(name: String,
                   age: Option[Int] = None,
                   address: Option[Address] = None,
                   val title: String = "[unknown]",
                   val manager: Option[Employee] = None)
    extends Person(name, age, address) {
        override def toString: String =
            s"Employee($name, $age, $address, $title)"
    }

    //如果我们真的需要区分Person和Employee的概念,那么可以使用下面的实现:
    case class Address2(street: String, city: String, state: String, zip: String)

    object Address2 {
        //重载apply方法
        def apply(zip: String): Address2 =
            new Address2("[unknown]", zipToCity(zip), zipToState(zip), zip)

        //通过邮政编码获得城市名(假设能做到)
        def zipToCity(zip: String) = "Anytown"

        //通过邮政编码后的州名
        def zipToState(zip: String) = "CA"
    }

    trait PersonState { //为Person拥有的状态(我们希望的状态)定义一个trait
        val name: String
        val age: Option[Int]
        val address2: Option[Address2]

        //此处定义一些公共的方法
    }

    //当只有Person类时,使用这个case类来实现PersonState.
    case class Person2(name: String,
                       age: Option[Int] = None,
                       address2: Option[Address2] = None)
    extends PersonState

    /**
      * 对 Employee 采用相同的做法，尽管在这里声明单独的 trait 和 case 类没有那么大用处。
      * 虽然一致性增加了引入单独的 trait 和 case 类的 “ 仪式 ” ，但保持一致性有其可取之处。
      */
    trait EmployeeState {
        val title: String
        val manager: Option[Employee2]
    }

    /**
      * 请注意，我们必须为 Person 和 Employee 共享的字段定义两次默认值。
      * 这是一个小缺点（除非我们真的需要使用不同的默认值）。
      */
    case class Employee2(name: String,
                         age: Option[Int] = None,
                         address2: Option[Address2],
                         title: String = "[unknown]",
                         manager: Option[Employee2])
    extends PersonState with EmployeeState

    /**
      * 此时,Employee2并非是Person2的子类,它们都是PersonState的子类
      */

}
