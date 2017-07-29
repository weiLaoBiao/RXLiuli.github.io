package chapter10

/**
  * Created by RXLiuli on 2017/5/17.
  */
//可变类型的变异
object MutableTypeVariance {
    def default(): Unit = {
        /**
          * 到目前为止，我们讨论的参数化类型都是不可变类型。
          * 那么可变类型的变异行为是什么样的呢？
          * 答案是，可变类型只允许非变异行为。
          */
        //考虑以下示例:
        //        class ContainerPlus[+A](var value: A)
        //        class ContainerPlus2[-A](var value: A)
        //上面的两个例子都产生了编译错误,发生了什么?!
        //将其分解一下,错的真的惨不忍睹
        //        class ContainerPlus[+T](t: T) { //这儿没有报错也是挺神奇的(我错了,编译错误)
        //            private var _value: T = t
        //
        //            def value = _value //是的,协变适用于返回值
        //
        //            /**
        //              * 为什么传给 value_=(newA: A) 的 A 必须是逆变的呢？
        //              * 这看起来不对，因为我们要给 _value 赋一个新值，
        //              * 如果新值是 A 的父类实例，会出现一个类型错误。
        //              * 因为 _value 必须是类型 A ，对吧？
        //              */
        //            //这儿看起来有点奇怪,因为我们要给_value赋一个新值,如果新值是T的子类,那不是正好么?
        //            def value_=(t: T): Unit = _value = t
        //        }
        /**
          * 其实，这种考虑思路是错误的。协变 / 逆变的规则适用于子类行为，而非超类行为。
          * 假设以上的声明是有效的。我们可以用 C 、 CSub 和 CSup 实例化 ContainerPlus[C] ：
          */
        /*
        val cp = new ContainerPlus(new C) //在构造对象时确定了参数化类型的具体类型为C
        cp.value = new C //有效: 我们用的是与声明相同的类型实例
        cp.value = new CSub //按照通常的面向对象规则(里氏替换),这是有效的
        cp.value = new CSuper //编译错误.因为CSuper的实例不能替换C的实例
        */
        /**
          * 看起来上面一切正常,然而,只有考虑ContainerPlus的子类时,麻烦才出现:
          */
        //如果ContainerPlus[+T]有效,这一行就是有效的
        //(即ContainerPlus[父类]也是ContainerPlus[子类]的父类)
        //所以可以用声明父类去实例化子类的实例.
        /*
        val cp: ContainerPlus[C] = new ContainerPlus[C](new CSub)
        //根据c的类型声明,这一行有效.这也是为什么参数类型必须逆变的原因.
        //然而该实例的value_=方法无法接收C的实例,因为该字段的类型是CSub(动态绑定).
        cp.value = new C
        cp.value = new CSub //正确
        cp.value = new CSuper //错误,直接违反了里氏原则!
        */
        /**
          * 上面说明了为什么方法的参数必须是逆变的.c的用户期望他能与C的实例一起工作.
          * 通过观察value_=方法的实际实现,我们已经知道我们无法支持逆变,
          * 不过暂时忽略这一点,尝试修改变异标记:
          */
        /*
        class ContainerMinus[-T](t: T) { //此处并没有错误,因为逆变支持参数赋值
            private var _value = t //编译错误,返回值要求协变

            def value: T = _value //编译错误,不符合返回值协变

            def value_=(t: T): Unit = _value = t
        }
        */
        //        class ContainerMinus[-T](var value: T) //实验用

        //同样的,当正常使用时不会发生异常
        /*
        var cp: ContainerMinus[C] = new ContainerMinus(new C)
        cp = new ContainerMinus(new CSub) //错误,因为T是逆变的,所以参数类型必须是C或其父类
        cp = new ContainerMinus(new CSuper)
        */

        //然而,子类的行为是关键
        /*
        //如果ContainerMinus[-T]有效,那么这一行就是有效的.
        val cm: ContainerMinus[C] = new ContainerMinus(new CSuper)
        //cm.value的契约中说明了cm.value只会返回C或其子类,c也认为value方法返回值类型是C,
        //但实际上该实例的value方法返回了CSuper类型(没有显式报错,因为符合"契约")
        val c: C = cm.value
        val c2: CSuper = cm.value
        val c2: CSub = cm.value //失败原因2
        */

        //你或许想说,函数参数逆变就逆变吧,大不了直接定义公有字段啦
        /*
        class ContainerPlus[+T] {
            var value: T = _ //然而,并没有什么卵用,还是报错了呀?
        }
        */
        //改进版: 使用 val 和 convariance(协变) 就好啦(也只能这样做!)
        class ContainerPlus[+T](val value: T)
        //这样便一切正常,因为val只有读取器,而没有修改器,所以不用考虑参数逆变问题.
        //也就是因为这样,List等不可变集合才会是协变的.
        //因为它们只有读取器而没有修改器,即所有元素为val.

        //Scala 和 Java 中的变异
        /**
          * 正如我们所说的，变异行为在 Scala 中定义于类型声明过程，而在 Java 中定义于使用过程。
          * 类型的客户端定义变异类型，设置默认类型为非变异。
          * Java 不允许你在定义类型时指定变异行为，尽管你可以使用看起来类似的表达式。
          * 这些表达式定义了类型边界，我们将稍后进行讨论。
          * Java 指定变异行为时存在两个缺点。首先，库的设计者应该负责类型的编译行为并编写进库中。
          * 但现在是库的用户承担这个负担。
          * 这导致了第二个缺点，就是 Java 程序员很容易指定错误的变异注释，
          * 从而导致不安全的代码，就像我们刚刚讨论过的那样。
          */
        //Java 类型系统的另一个问题是， Array 是协变的。
        //考虑以下例子(在Java那边进行演示):
        //        src.TestJavaClass.TestJava.arrayVariance()


    }

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

}
