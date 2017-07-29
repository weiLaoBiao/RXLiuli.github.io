package chapter11

/**
  * Created by RXLiuli on 2017/5/28.
  */
//对象层次结构的线性化算法
object LinearAlgorithm {
    def default(): Unit = {
        /**
          * 由于采用了单继承模型，假如我们不使用可混入的 trait ，
          * 继承层次结构将会变成一条直线，将各个祖先节点依次连接。
          * 如果引入 trait 的话，由于每个类型都有可能继承自其他 trait 或类，
          * 继承层次关系便形成了一个有向无环图。
          * “ 线性化算法 ” 是一类用于对层级结构图进行 “ 扁平化 ” 处理的算法，
          * 引入该算法是为了解决方法查找的优先级问题、
          * 构造函数调用顺序、 super 关键字绑定问题等一系列问题。
          */


    }

    //region 方法的调用顺序

    //测试: 普通方法的调用顺序
    val c2 = new C2
    //trait中的m方法将依照声明顺序,从右到左的被调用.
    c2.m()

    /**
      * 之所以方法调用会出现这样的情况是因为"广度优先算法".
      * 完整版: C2.m(), T3.m() C1.m(), T2.m() C1.m(), T1.m() C1.m()
      * 简化后就是: C2.m(), T3.m(), T2.m(), T1.m(), C1.m()
      * 实际上就是把所有子类都放置好,再将共享类型放到线性化列表的右端.
      */


    class C1 {
        def m(): Unit = print("C1")
    }

    trait T1 extends C1 {
        override def m(): Unit = {
            print("T1")
            super.m()
        }
    }

    trait T2 extends C1 {
        override def m(): Unit = {
            print("T2")
            super.m()
        }
    }

    trait T3 extends C1 {
        override def m(): Unit = {
            print("T3")
            super.m()
        }
    }

    class C2 extends T1 with T2 with T3 {
        override def m(): Unit = {
            print("C2")
            super.m()
        }
    }

    //endregion

    //region 构造函数的调用顺序


    //测试: 构造函数的调用顺序
    println("\n构造函数调用顺序: ")
    val c4 = new C4

    /**
      * 可以看出来构造函数调用顺序与之前的方法调用顺序恰恰相反,
      * 因为在继承结构中构造子类的实例之前必须先构造父类的实例.
      * 所以构造函数的调用是"深度优先算法".
      */


    class C3 {
        print("C3")
    }

    trait T4 extends C3 {
        print("T4")
    }

    trait T5 extends C3 {
        print("T5")
    }

    trait T6 extends C3 {
        print("T6")
    }

    class C4 extends T4 with T5 with T6 {
        print("C4")
    }

    //endregion

}