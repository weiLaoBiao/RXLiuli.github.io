package chapter08

/**
  * Created by RXLiuli on 2017/5/9.
  */
//类的字段
object ClassField {
    def default(): Unit = {
        /**
          * 在本章的开头我们曾提醒大家，如果在主构造函数参数的前面加上 val 或 var 关键字，
          * 该参数就成为实例的一个字段。对于 case 类， val 是默认的。
          * 这样可以大大减少冗余的代码，但是它是如何转化成字节码的呢？
          */
        /**
          * 其实，不过是 Scala 偷偷地干了 Java 代码中明显做的事情。
          * 类会创建一个私有的字段，并生产对应的 getter 和 setter 访问方法。
          */
        //考虑下面这个简单的类
        class Name(var value: String)
        //从概念上讲,上面的代码和下面的是等价的(Java中能且只能这样写):
        class Name2(s: String) {
            private var _value: String = s //类的可变的私有字段_value

            def value: String = _value //getter

            def value_=(newValue: String): Unit = _value = newValue //setter
        }
        //注意value_=这个方法名,当编译器看到时允许不写_转而使用中缀表达式.
        //虽然看起来就像是在为裸字段赋值一样,然而实际上只是Scala的语法糖.
        //先使用正常的类
        val name = new Name("月姬")
        println(name)
        println(name.value) //貌似在直接获得字段的值?
        name.value = "灵梦" //貌似在直接设置字段的值?
        println(name.value)
        //使用我们定义的setter和getter方法
        val name2 = new Name2("楚轩")
        println(name2.value) //可以通过getter获得value
        name2.value = "亚当" //可以通过我们自定义的setter修改
        println(name2.value)

        /**
          * 如果我们用 val 关键字声明一个不可变字段，那就不会自动生成写方法，只会生成读方法。
          * 如果你想在读方法或写方法内实现自定义逻辑，可以按以下规范来实现。
          * 对于非 case 类，如果我们向构造器传递参数时不希望参数成为类的字段，
          * 可以在构造器中省略 val 或 var 。
          * 例如，我们虽然需要向构造器传递一个参数，但却希望构造器退出后丢弃它。
          * 注意，该值仍然在类体的作用范围内。
          * 但这些参数大多没有被声明为类的字段。
          */
        //Person:
        class Person(val name: String, var age: Int, _sex: Boolean) {
            var sex: Boolean = _sex

            override def toString: String = s"Person name: $name,age: $age,sex: $sex"
        }
        val p = new Person("琉璃", 15, false)
        println(p)
        //编译错误,_sex只是主构造函数的参数,而非一个公开字段
        //        println(s"_sex: ${p._sex}")
        //编译错误,val字段没有生成setter方法
        //        p.name = "灵梦"
        //然而,即便是age和sex在赋值时也是有很大区别的
        p.age = 18 //使用了setter方法
        p.sex = true //直接为字段赋值
        println(p) //是的,实例p改变了

        /** 那么,为什么不总将主构造器的参数设置成字段呢?
          * 因为除非字段被声明称私有的或保护的,客户端都可以看到字段.
          * 而这些构造器的参数只有在确实需要暴露给用户状态的情况下，才会变成字段；
          * 否则它们不应该成为字段，而是属于类体私有的。
          */

        //统一访问原则
        /**
          * 你可能会对 Scala 没有遵循 JavaBeans 的约定规范，
          * 也就是把 value 字段的读方法和写方法分别命名为 getValue 和 setValue 感到疑惑不解。
          * 事实上， Scala 遵循统一访问的原则。
          * 正如我们在 Name 这个例子中看到的，
          * 客户端似乎可以不经过方法就对 “ 裸 ” 字段值进行读和写的操作，但实际上它们调用了方法。
          * 另一方面，我们可以用默认的公开可见性声明一个字段，然后像裸字段一样访问该字段：
          */
        class Name3(s: String) {
            var value: String = s
        }
        //现在,value 是一个公开字段,没有访问方法.
        val name3 = new Name3("琉璃")
        println(name3.value)
        name3.value = "灵梦"
        println(name3.value)
        //我们也可以将裸字段修改为访问方法,例如: 我们要验证名字长度不得小于2或者大于5
        class Name4(s: String) {
            private var _value: String = s

            def value: String = _value

            def value_=(s: String): Unit =
                if (s.length >= 2 && s.length <= 4) _value = s
                else _value = "默认"
        }
        val name4 = new Name3("琉璃")
        println(name4.value)
        name4.value = "灵梦"
        println(name4.value)

        class Name5(var value: String) {
            //验证主构造器的方法,不满足条件就会抛出异常: IllegalArgumentException
            require(value.length >= 2 && value.length <= 4)
        }
        val name5 = new Name5("灵梦")
        //        val name5 = new Name5("default") //运行错误

        /**
          * 请注意，用户的 “ 体验 ” 是一致的。
          * 用户代码不了解实现，这使我们可以在需要的时候，自由地将直接操作裸字段改为通过访问方法来操作。
          * 例如：我们要在写操作中添加某些验证工作，或者为了提高效率，在读取某个结果时采用惰性求值。
          * 这些情况下，我们可以通过访问方法来操作裸字段。
          * 相反地，我们也可以用公开可见性的字段代替访问方法，以消除该方法调用的开销
          * （尽管 JVM 可能会消除这种开销）。
          * 因此，统一访问原则的重要益处在于，
          * 它能最大程度地减少客户端代码对类的内部实现所必须了解的知识。
          * 尽管重新编译仍然是必需的，我们可以改变具体实现，而不必强制客户端代码跟着做出改变。
          * Scala 实现统一访问原则的同时，没有牺牲访问控制功能，
          * 并且满足了在简单的读写基础上增加其他逻辑的需求。
          */
        //统一访问原则让我们可以随意替换类的内部实现方式,并且提供统一的访问接口.
        /**
          * 统一访问原则固然很好,然而有时为了与 Java 库交互，也会需要 JavaBeans 风格的访问方法。
          * 这时可以用 scala.reflect.BeanProperty 或 BooleanBeanProperty 给类做标记。
          */

        //一元方法
        /**
          * 我们已经看到，编译器允许我们为字段 foo 定义赋值方法 foo_= ，
          * 然后使用简便的 myinstance.foo = value 。
          * 另一种操作符 —— 一元操作符，我们还不知道如何实现。
          */
        //Complex:复杂,合成 unary:一元的
        case class Complex(real: Double, imag: Double) {
            var i: Int = _

            //定义了一个一元操作符方法
            //当然,即便是操作符方法也是有一些限制的,比如,不能使用多个字符定义一元操作符
            def unary_- : Complex = Complex(-real, imag)

            //定义了一个二元操作符方法
            def -(than: Complex): Complex =
                Complex(real - than.real, imag - than.imag)

        }
        val c = Complex(6, 4)
        val c3 = c.unary_-
        val c2 = -c //注意,我们可以这样调用方法,这是Scala的语法糖
        //是的,我们可以像使用一个真的一元操作符一样使用该方法
        println(-c - -c2) //默认重写了toString方法
        //比较一下c2和c3的值
        println(s"c2 == c3? ${c2 == c3}")
        println(s"c2 equals c3? ${c2 equals c3}")
        c2.i = 10
        c3.i = 5
        //注: case类自动生成的equals方法只会比较主构造器的参数(unapply),对实例解构之后比较元组
        println(s"改变后的 c2 == c3? ${c2 == c3}") //还是true
        /**
          * 我们一旦定义了一元操作符，就可以将操作符放在实例之前，就像我们在定义 c2 时所做的那样。
          * 也可以像定义 c3 时那样，将一元操作符当做其他方法一般进行调用。
          */


    }
}
