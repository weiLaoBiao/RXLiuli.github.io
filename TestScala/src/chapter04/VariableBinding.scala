package chapter04

/**
  * Created by RXLiuli on 2017/4/25.
  */
object VariableBinding {
    def default(): Unit = {
        //变量绑定
        //测试用的类
        case class Address(street: String, city: String, country: String)
        case class Person(name: String, age: Int, address: Address)
        //测试值
        val alice = Person("Alice", 25, Address("1 Scala Lane", "Chicago", "USA"))
        val bob = Person("Bob", 29, Address("2 Java Ave.", "Miami", "USA"))
        val charlie = Person("Charlie", 32, Address("3 Python Ct.", "Boston", "USA"))
        //测试匹配
        for (person <- Seq(alice, bob, charlie)) {
            person match {
                //此处同时实现了构造函数模式匹配和变量绑定,
                // p@Person(...)在匹配的同时将对象整个Person实例赋值给变量p
                case p@Person("Alice", 25, _) => println(s"Hi Alice! $p")
                //这儿甚至使用了两次变量绑定
                case p@Person("Bob", 29, a@Address(_, _, _)) =>
                    println(s"Hi ${p.name}! age ${p.age}, in ${a.city}")
                case p@Person(name, age, _) =>
                    println(s"Who are you, $age year-old person named $name? $p")
            }
        }
        /**
          * 思考: object@class(...)适用范围?
          * 当既需要进行构造函数模式匹配时,又需要在后面使用该对象并且无法直接使用匹配对象时,
          * 特别适合于构造函数深度匹配中的对象提取
          * 注: 如果不需要使用构造函数模式匹配(解构提取属性值),最好使用p:Person进行匹配
          */
    }
}
