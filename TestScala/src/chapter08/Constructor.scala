package chapter08

/**
  * Created by RXLiuli on 2017/5/9.
  */
//Scala的构造器
object Constructor {
    def default(): Unit = {
        /**
          * Scala 将主构造器与零个或多个辅助构造器区分开，辅助构造器也被称为次级构造器。
          * 在 Scala 中，主构造器是整个类体。构造器所需的所有参数都被罗列在类名称后面。
          * StringBulkReader 和 FileBulkReader 就是两个例子。
          */
        {
            //回顾第五章的case类Person和Address,尝试使用辅助构造器改进
            //street:街道 city:城市名 state:州名 zip:邮政编码
            class Address(street: String, city: String,
                          state: String, zip: String) {
                // 次级构造器只带一个参数，即邮政编码。
                // 内部调用了其他辅助函数，通过邮政编码得到城市名和州名，但无法得到街道名。
                def this(zip: String) = {
                    this("[unknown]", Address.zipToCity(zip),
                        Address.zipToState(zip), zip)
                }
            }
            object Address {
                //通过邮政编码查找城市和州的辅助函数（至少假设该辅助函数能做到这一点）。
                //至少假设能够做到这一点2333
                def zipToCity(zip: String) = "Anytown"

                def zipToState(zip: String) = "CA"
            }
            case class Person(name: String, age: Option[Int],
                              address: Option[Address]) { //使得年龄和地址成为可选参数。
                //提供次级构造器的便利接口，让用户指定部分或全部参数值。
                def this(name: String) = this(name, None, None)

                def this(name: String, age: Int) = this(name, Some(age), None)

                def this(name: String, age: Int, address: Address) =
                    this(name, Some(age), Some(address))

                def this(name: String, address: Address) =
                    this(name, None, Some(address))
            }

            /**
              * 需要注意的是，辅助构造被命名为 this ，
              * 它的第一个表达式必须调用主构造器或其他辅助构造器。
              * 编译器还要求被调用的构造器在代码中先于当前构造器出现。
              * 所以，我们在代码中必须小心地排列构造器的顺序。
              * 通过强制让所有构造器最终都调用主构造器，可以将代码冗余最小化，
              * 并确保新实例的初始化逻辑的一致性。Address 的次级构造器包含了一些具体的有用逻辑，
              * 这不同于 Person 的次级构造器，Person 的初次构造器只是多提供了几个可调用的便利函数。
              */
            //尝试使用
            val a1 = new Address("1 Scala Lane", "Anytown", "CA", "98765")
            val a2 = new Address("98765")
            println("result1: " + new Person("Buck Trends1"))
            println("result1: " + Person("Buck Trends2", Some(20), Some(a1)))
            println("result1: " + new Person("Buck Trends3", 20, a1))
            println("result1: " + new Person("Buck Trends4", 20))
        }
        /**
          * 虽然上面的代码运行良好,但实际上还是存在一些问题.
          * Person有很多参数相似的辅助构造器(冗余).
          * 事实上我们可以使用默认值方法参数,用户调用方法时也可以使用命名参数.
          */
        {
            //使用默认值和命名参数列表重写上面的类
            class Address(val street: String, val city: String,
                          val state: String, val zip: String) {
                // 次级构造器只带一个参数，即邮政编码。
                // 内部调用了其他辅助函数，通过邮政编码得到城市名和州名，但无法得到街道名。
                def this(zip: String) = {
                    this("[unknown]", Address.zipToCity(zip),
                        Address.zipToState(zip), zip)
                }
            }
            object Address {
                //通过邮政编码查找城市和州的辅助函数（至少假设该辅助函数能做到这一点）。
                def zipToCity(zip: String) = "Anytown"

                def zipToState(zip: String) = "CA"

                def apply(zip: String): Address = {
                    new Address("[unknown]", zipToCity(zip),
                        zipToState(zip), zip)
                }
            }
            case class Person(name: String, age: Option[Int] = None,
                              address: Option[Address] = None) {
                //未提供次级构造器的便利接口,让用户通过命名参数列表指定部分或全部参数值
            }
            //尝试使用
            val a1 = new Address("1 Scala Lane", "Anytown", "CA", "98765")
            val a2 = new Address("98765")
            println("result2: " + new Person("Buck Trends1"))
            println("result2: " + Person("Buck Trends2", Some(20), Some(a1)))
            println("result2: " + new Person("Buck Trends3", Some(20)))
            println("result2: " + Person("Buck Trends4", address = Some(a2)))
            //虽然用户多些了一点点代码,
            //但对于库的开发者而言维护负担的显著下降是一件好事,这也是一种平衡.
        }
        /**
          * 我们决定使用减小用户负担的那种选择。
          * 第二个问题是，在我们的实现中，用户必须使用 new 来创建实例。
          * 也许你已经注意到了，实例中就是用new 来构造实例的。
          * 如果尝试把 new 关键字去掉，会发生什么呢？除非调用的是主构造器，不然你会得到一个编译错误。
          */
        //注: 编译器不会自动为case类的次级构造器创建apply方法.
        //然而,我们可以在伴生对象中重载apply,就可以得到便利的构造器了,并且避免了使用new.
        //使用体验
        val address = Address("1 Scala Lane", "Anytown", "CA", "98765")
        println("result3: " + Person("Buck Trends1"))
        println("result3: " + Person("Buck Trends2", Some(20), Some(address)))
        println("result3: " + Person("Buck Trends3", 20))
        println("result3: " + Person("Buck Trends4", address = address))
        /**
          * 事实上，在 Scala 代码中定义次级构造器并不是很常见。
          * 因为还有其他替代的技术，它们通常为用户提供同样灵活的构造选项，同时减少样板代码。
          * 注意要合理使用 Scala 中的命名参数和可选参数，
          * 并科学地使用对象中重载的 apply “ 工厂 ” 方法。
          */
    }

    //Person最终实现方式:
    case class Address(street: String, city: String,
                       state: String, zip: String)

    case class Person(name: String,
                      age: Option[Int] = None,
                      address: Option[Address] = None)

    object Person { //噗,也就是说,伴生对象不能定义在类里面么?
        //由于我们重载的是普通方法，而不是构造器，
        //所以必须明确地指定返回类型，在这里返回类型是 Person。
        def apply(name: String): Person = new Person(name)

        def apply(name: String, age: Int): Person = new Person(name, Some(age))

        def apply(name: String, age: Int, address: Address): Person =
            new Person(name, Some(age), Some(address))

        def apply(name: String, address: Address): Person =
            new Person(name, address = Some(address))
    }
}
