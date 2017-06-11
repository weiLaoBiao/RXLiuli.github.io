package chapter08

/**
  * Created by RXLiuli on 2017/5/7.
  */
//类和对象初步
object ObjectPreliminary {
    def default(): Unit = {
        /**
          * 类用关键字class声明,而单例对象用关键字object声明.
          * 在类声明之前加上关键字final可以避免从一个类中派生其他类.
          * abstract关键字可以阻止类的实例化(抽象类),然而可以声明抽象类.
          */
        //一个简单的Person类
        class Person(val name: String, var age: Int)
        //看看里面都是什么:
        /**
          * 在Person类名后面加上的括号实际上是主构造函数的参数列表,
          * 主构造函数指的是默认存在的构造函数(和Java不一样,Scala的主构造函数并非一定是无参的).
          * 那么在参数前面加上var是什么意思呢?
          * var使得该参数成为类的一个可变字段(读写),这在其他OO语言中也称为实例变量或属性.
          * 在构造参数前加上val使得该参数成为类的一个不可变字段(只读).
          * 如果在class前面加上case关键字构造参数默认是val字段.
          */
        //加上一些方法
        case class ImmutablePerson(name: String, age: Int) {
            def show(): Unit = println(s"$name is $age years old.")
        }
        //注意: 实例的状态时是里所有字段内当前值的总和.
        val p = new ImmutablePerson("月姬", 1000)
        println(s"name: ${p.name},age: ${p.age}") //所有的字段都被存储到实例中了
        //method(方法)指与实例绑定在一起的函数.
        //也即是说,它的参数列表中有一个"隐含"的this参数(Scala真变态,这个也用隐式转换实现...).
        p.show()

        //方法使用def关键字定义.当其他函数或方法需要一个函数作为参数时,
        //Scala会自动将可用的方法"提升"为函数,作为前者的函数列表.
        def foo[A, B](f: (A) => B, a: A): B = f(a)

        def add(i: Int): Int = i + 1

        //调用测试
        println(s"foo方法的结果: ${foo(add, 10)}")

        /**
          * 与大多数静态类型语言一样,Scala允许方法重载.只要方法签名唯一,方法就可以重名.
          * 方法签名包括返回类型,方法名称和参数类型的列表(参数名不重要).
          * 然而,在JVM中仅凭返回类型并不足以区分不同的方法.
          * 注: JVM不允许某些方法完全不同,这是因为对于"高级类型"存在类型擦除机制.
          * 其中"高级类型"是包含类型参数的类型,如List[A]:
          */
        object C {
            /*def m(seq: Seq[String]): Unit = println(s"Seq[String]: $seq")*/
            def m(seq: Seq[Int]): Unit = println(s"Seq[Int]: $seq")
        }
        //类型参数Int和String在二进制码中被擦除了.
        /**
          * 不像Java,Scala可以用type关键字声明类型成员(可以以此定义类型别名).
          * 这些类型成员是类型参数化的一种补充机制.它们经常被用来作为复杂类型的别名,以提供可读性.
          * 类型成员和参数化类型是不是两个重复的机制呢? 不是!
          */
        //术语"成员"指类的字段,方法或类型的统称.
        //与Java不同,如果方法有参数列表,该方法可以与类的字段同名:
        trait Foo {
            val x: Int

            //            def x: Int //编译错误,类型名字必须唯一!!!
            def x(i: Int): Int
        }

        //Scala没有Java中的静态成员.但是Scala用Object来保存多个实例共享的成员(如全局函数).
        //比如定义一个工具类Tool(默认就是单例模式)
        object Tool {
            //对一个值执行同一个操作两次(常见的双倍控制结构)
            def twofold[T](a: T, f: (T) => T): T = f(f(a))

            def twofold2[T](a: T)(f: (T) => T): T = f(f(a)) //稍微提一下柯里化
        }
        println("正常调用: " + Tool.twofold(1, (i: Int) => i + 1)) //无法推断
        println("柯里化调用: " + Tool.twofold2(1)((i) => i + 1)) //Scala可以推断i的类型
        //当然,你也可以定义一个类的工厂类(Java中需要静态支持)
        class Animal(var name: String, var age: Int)
        object FactoryAnimal { //工厂类(Factory:工厂)
            def getInstance(name: String, age: Int): Animal = { //获取实例
                new Animal(name, age)
            }
        }
        //使用工厂方法
        val animal = FactoryAnimal.getInstance("月姬", 4)

        //实际上,我们有更强大而优雅的方法: 伴生对象.
        //如果一个对象和一个类具有相同的名字,并在同一个文件中定义,他们的关系就是伴随的.
        //上面的工厂类改进版:
        object Animal {
            //是的,这便是工厂构造方法了
            def apply(name: String, age: Int): Animal = new Animal(name, age)

            //当然,我们一般也需要定义解构方法(用于模式匹配)
            def unapply(animal: Animal): Option[(String, Int)] = {
                Some(animal.name, animal.age)
            }
        }
        //那么,我们可以这样创建实例了:
        val animal2 = Animal("月姬", 4) //此处是Scala的语法糖,实际上调用了apply

        //对于case类,编译器会自动生成一个伴随对象(还有一大堆方法: apply/unapply...)
        case class Animal2(var name: String, var age: Int)
        //可以直接使用apply,然而我们并没有显式定义
        val caseAnimal = Animal2.apply("月姬", 4)

    }
}
