package chapter09

/**
  * Created by RXLiuli on 2017/5/15.
  */
//混入trait
object MixinTrait {
    def default(): Unit = {
        /**
          * 为了能够理解 trait 在模块化 Scala 代码的作用，我们首先学习一下下面的这段代码。
          * 图形用户界面 （ GUI ）中的按钮会使用这段代码，
          * 当点击事件发生时，这段代码会通过回调机制通知客户：
          */
        //虽然知道了trait如何使用,然而在什么地方使用trait还未可知!
        //稍微写一点mix in(混入)
        val stu: Person = new Student
        stu.run()
        val emp: Person = new Employee
        emp.run()
        //上面使用了多态,确实正常的运行了
        //然而,如果我们调用了work方法
        stu.work() //调用了Person特征的work方法
        val stu2 = new Student with StudentState //混入了StudentState
        stu2.work() //调用了StudentState中的work方法
        //如果你需要其他的行为,也可以混入其他trait
        //不过混入的必须要是同一类型(trait的子类)
        //        val stu3 = new Student with StudentState2
        //        stu3.run()
        //即便是间接继承Person,trait StudentState3依然有效
        val stu4 = new Student with StudentState3
        stu4.work()
        /**
          * 是的,我们可以随意的混入(Mixin)任意的特征(Trait),
          * 相比于公有的静态方法,这种成员方法更容易使用
          * 虽然我们没有写,然而实际上我们可以混入任意多个特征！这让模块化成为了可能.
          */
        trait B {
            def foo(): Unit = println("B")
        }
        class A extends B
        trait B2 extends B {
            override def foo(): Unit = println("B2")

            def fooB2(): Unit = println("fooB2")
        }
        trait B3 extends B {
            override def foo(): Unit = println("B3")

            def fooB3(): Unit = println("fooB3")

            def fooB(): Unit
        }

        val a = new A with B2 with B3 { //可以同时混入多个trait
            //然而,抽象方法必须被重写
            def fooB(): Unit = println("重写trait B3的抽象方法fooB")
        }
        a.foo() //是的,从左到与,后面重写的方法会覆盖掉前面重写的
        a.fooB2() //当然,非重写方法也全部继承了
        a.fooB3()
        a.fooB() //重写的抽象方法
        /**
          * Scala使用 线性化（linearization）算法解决具体类继承树中 trait 和类的优先级问题。
          * 这两类的按钮实现都很直观。 trait 优先级遵循从右到左的原则.
          */
    }

    //Person特征
    trait Person {
        def run(): Unit //定义抽象方法run
        def work(): Unit = println("人在做工作")
    }

    //Student类
    class Student extends Person {
        //实现Student类的run方法
        def run(): Unit = println("学生在跑步")
    }

    //StudentState特征
    trait StudentState extends Person {
        //重写work
        override def work(): Unit = println("学生在学习")
    }

    //StudentState2特征
    trait StudentState2 {
        def work(): Unit = println("学生在继续学习")
    }

    //StudentState3特征(当然,也可以是间接继承)
    trait StudentState3 extends StudentState {
        override def work(): Unit = println("学生在图书馆看书")
    }

    //Employee类
    class Employee extends Person {
        //实现Employee类的run方法
        def run(): Unit = println("员工在跑步")
    }

}
