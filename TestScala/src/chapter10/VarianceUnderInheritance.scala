package chapter10

/**
  * Created by RXLiuli on 2017/5/17.
  */
//参数化类型: 继承转化
object VarianceUnderInheritance {
    def default(): Unit = {
        /**
          * Scala 参数化类型和 Java 参数化类型（在 Java 中，通常称为 泛型 ， generic ）
          * 的一个重要区别在于，继承差异机制如何工作。
          */
        //假设方法参数类型为List[AnyRef],我们可以传入List[String]么?
        //换句话说,List[String]是否应该被看作List[AnyRef]的一个子类型呢?
        //定义一个参数为List[AnyRef]的函数
        def f(list: List[AnyRef]): List[String] = list.map(_.toString + "\t")

        //在这儿List[String]确实被看作了List[AnyRef]的子类型
        f(List[String]("1", "2", "3", "4")).foreach(print)
        println()
        /** 这种转化称为 covariance(协变).
          * 因为容器(被参数化的类型)的继承关系与参数类型的继承关系方向一致.
          * 同样存在的是 contravariant(逆变),对于特定类型X,X[String]是X[Any]的父类.
          * 如果参数化类型既不是协变的,也不是逆变的,我们称之为 invariant(非转化).
          */
        //协变,非转化,逆变的简单定义
        class W[+T] //协变,A[父类]也将会是A[子类]的父类
        class X[T] //非转化,A[父类]和A[子类]没有任何关系
        class Y[-T] //逆变,A[父类]也将会是A[子类]的子类
        class Z[-A, B, +C] //混合

        /**
          * Java 和 Scala 均支持协变，逆变和非转化类型。
          * 然而，在 Scala 中，转化行为的定义是类型声明的一部分，
          * 称为 转化标记 （ variance annotation ）。
          * 我们使用 + 来表示协变类型；使用 - 表示逆变类型；非转化类型不需要添加标记。
          * 换句话说，类型的设计者决定该类型在继承体系中如何进行转化。
          */
        //当List只有一个协变的类型参数时,你经常会听到一种简称: 列表是协变的.
        //相应的,对于逆变类型也有类似的叫法.
        //协变和非转化类型很容易理解,那么,逆变类型呢?

        /**
          * Hood下的函数
          * 逆变的最好例子是一组 trait FunctionN ，例如：scala.Function2
          * （http://www.scala-lang.org/api/current/scala/Function2.html）
          * 其中 N 是一个介于 0 和 22 （含 22 ）之间的数字，并且与函数所带的参数个数相对应。
          * Scala 使用这些 trait 来实现匿名函数。
          */
        //我们一直在使用匿名函数,匿名函数(lambda function)也称为函数字面量.
        List(1, 2, 3, 4) foreach (i => print(s"$i\t"))
        println()
        //函数表达式i => print(s"$i\t")
        //实际上是一个语法糖,编译器将其转化为scala.Function1的匿名子类,实现如下:
        val f1: Int => Unit = new Function1[Int, Unit] {
            //我们实现了apply方法
            override def apply(i: Int): Unit = print(s"$i\t")
        }
        List(1, 2, 3, 4) foreach f1
        //先让我们试验一下f
        f1(10)
        f1.apply(7)
        /**
          * 当对象后面跟上参数列表时，就会调用默认的 apply 函数。
          * 这一约定源于函数应用（ function application ）的思想。
          * 例如：一旦定义了 f ，我们就通过指定参数列表的方式调用它，如 f(1) 。
          * 实际上 f(1) 是 f.apply(1) 。
          * 从历史上看， JVM 不允许字节码中出现 “ 裸 ” 函数。一切都必须包装在对象中。
          * Java 的最近版本，特别是 Java 8 ，放宽了这种限制，
          * 但为了使 Scala还能在旧版本的 JVM 中工作，
          * 编译器会将匿名函数转为 FunctionN 特征的匿名子类。
          * 在 Java 项目中，你或许已经为 Java 的接口写过很多这样的匿名子类了。
          * Java 8 增加了对函数字面量的支持，称为 Lambda 函数。
          * 但不同于 Scala 的实现方式，Java 采用了另一种实现方式,因为Scala还要支持旧版的JVM。
          */
        /**
          * 然后,让我们看一下逆变
          * 下面是 scala.Function2的声明
          * trait Function2[-T1, -T2, +R] extends AnyRef
          * 参数类型逆变,返回值类型是协变的(其他的Function也一样).
          * 所以,函数在继承时都具有混合变异行为.
          */
        //那么,这些究竟是什么意思呢?
        //定义一个三层的类继承结构
        class CSuper {
            def mspuer(): Unit = println("CSuper")
        }
        class C extends CSuper {
            def m(): Unit = println("C")
        }
        class CSub extends C {
            def msub(): Unit = println("CSub")
        }

        //我们将函数f定义为var变量,完成对f重复赋值.
        //所有有效的函数实例必须是C=>C的形式,换句话说,便是Function1[C,C]的形式.
        //我们用来赋值的变量还必须满足函数继承变异规则的约束.
        var f2: (C) => C = (c: C) => new C //此处完全满足左边定义的"契约"
        f2 = (c: CSuper) => new CSub //参数满足逆变,返回值满足协变
        f2 = (c: CSuper) => new C
        f2 = (c: C) => new CSub
        //        f2 = (c: CSub) => new Csuper //编译错误
        /**
          * 契约式设计解释了为什么这些规则是有意义的。
          * （ http://archive.eiffel.com/doc/manuals/technology/contract/ ）
          * 这是里氏替换原则的一种表现形式，我们会将它作为一个编程工具在 23.5 节进行简要讨论。
          * 现在，我们凭直觉尝试理解这些规则如此运行的原因。
          */
        /**
          * 函数变量f的类型是 C => C (that is, Function1[-C,+C]).
          * 对f的第一次赋值与该签名完全相符.
          * 第二次赋值,(c:CSuper)=>CSub,符合声明,即参数逆变,返回值协变.
          * 然而,这个声明为什么是安全的呢?
          * 关键是了解f是如何调用的,以及我们可以对f背后的真正函数做那些假设.
          * 当我们说f的类型是C => C时,我们其实定义了一个"契约":
          * 任何有效的C值都可以传给f(即f能够处理所有C的值),f永远也不会返回除C类值以外的任何值.
          *
          * 如果我们实际上实际的函数类型为(c:CSuper)=>CSub,该函数不仅可以接受任何C类型的值,
          * 或其父类型的其他字类型的实例(如果有的话).
          * 所以,由于只传入C的实例,我们永远不会传入超出 f 允许范围外的参数。
          * 从某种意义上说， f 比我们需要的更加 “ 宽容 ” 。
          * 同样，当它只返回 Csub 时，这也是安全的。
          * 因为调用方可以处理 C 的实例，所以也一定可以处理 CSub 的实例。
          * 在这个意义上说， f 比我们需要的更加 “ 严格 ” 。
          *
          * 示例的最后一行同时打破了关于输入和输出类型的两个规则。
          * 如果允许这个函数合法地赋值给 f ，我们考虑一下会发生什么：
          * 在这种情况下，实际的 f 函数只知道如何处理 CSub 实例。
          * 但调用者对此一无所知，认为任何 C 实例都可以传入 f ，
          * 所以当 f 运行出现 “ 意外 ” 时，就可能导致失败。
          * 即调用者试图把 C 实例传入到一个只接受 CSub 而不是 C 的函数。
          * 同样地，如果实际的 f 能够返回一个 CSuper 实例，
          * 这将超出调用者预期的返回值范围（预期返回 C 的实例）。
          * 这解释了为什么函数的参数必须是逆变的，而返回值必须是协变的。
          */
        println("\n===========================================================" +
        "\n===========================分割线===========================" +
        "\n===========================================================")
        //稍微做一点实验
        f2 = (c: CSuper) => new CSub
        //编译错误,因为f2的参数违反了"契约"(只允许C类型的值),即便f2实际允许的的参数是CSuper类型
        //        f2(new CSuper) //注: 此处涉及了编译期和运行期(编译期报错,运行时正常)
        //然而,如果这样就好了
        f2(new C)
        f2(new CSub)
        val c1: C = f2(new CSub) //c1的声明类型是C,然而实际上却是CSub类型的实例.
        //那么,让我们来一个具体的例子吧
        val maiden = new Maiden("琉璃", false) //创建一个Maiden的实例
        //以下是两种完全符合标准的调用情况
        println(maiden.show((m: Maiden) => m).myToString)
        println(maiden.show((_: Maiden) => new Maiden("月姬", false)).myToString)
        //如果是下面的呢?
        println {
            maiden.show { //调用show方法
                //下面是一个lambda function(匿名函数)
                (_: Person) => { //参数是Person类型(Maiden的父类,符合参数逆变)
                    //返回值是一个Student类型的实例(Maiden的子类,符合返回值协变)
                    new Student("琉璃", false, 9)
                }
            }.myToString //此处调用的仍然是Maiden类中的myToString(测试用)
        }
        //事实上,即便我们使用参数化类型定义更通用的方法,这条法则仍然是绝对的!
        /*
        println {
            Maiden.show( //调用show方法
                maiden,
                //下面是一个lambda function(匿名函数)
                //此处,参数化类型T被推断为Person(最小公共父类型),此处违反了参数逆变(编译正常)
                (_: Student) => {
                    //返回值是一个Person类型的实例,此处违反了返回值协变
                    new Person("琉璃")
                }
            ).myToString //编译错误,因为返回值类型Person中并没有myToString方法
        }
        */

        /**
          * 变异标记只有在类型声明中的类型参数里才有意义，对参数化的方法没有意义，
          * 因为该标记影响的是子类继承行为，而方法没有子类。
          * 最后，编译器会检查你所用的编译标记是否有效。
          * 如果你试图在自定义的函数中加上错误的标记，以下就是发生的结果：
          */
        trait MyFunction2[+T1, +T2, -R] {
            //将会产生三个编译错误!
            //            def apply(t1: T1, t2: T2): R = ???
        }
        //警告: 编译器要求函数(方法<=函数)参数为逆变,返回值为协变!
    }

    //定义人类
    class Person(var name: String)

    //定义一个少女类型
    class Maiden(name: String, var sex: Boolean) extends Person(name) {
        //定义了一个方法show,接受一个Maiden类型参数,并返回一个Maiden类型的值
        def show(f: Maiden => Maiden): Maiden = f(this)

        //自定义一个myToString()
        //之所以自定义一个myToString,是因为直接重写toString的话无法看出效果
        def myToString: String = s"Person($name, $sex)"
    }

    object Maiden {
        def show[T](t: T, f: T => T): T = f(t)
    }


    //定义了一个Student类型
    class Student(name: String, sex: Boolean, var grade: Int)
    extends Maiden(name, sex)

}
