package chapter03

/**
  * Created by RXLiuli on 2017/4/16.
  */
object TestLazy {
    def default(): Unit = {
        //惰性赋值
        println("开始惰性赋值: ")
        lazy val resource = {
            println("执行了惰性赋值")
            0
        }
        //实际调用惰性赋值
        println("调用惰性赋值: ")
        println(s"实际调用赋值: $resource") //此处才会进行初始化

        /**
          * 惰性赋值:
          * 通过"保护式"实现惰性赋值,当客户代码引用惰性值,保护式会拦截引用并检查此时是否需要初始化.
          * 由于保护式能确保惰性只在第一次访问之前便已初始化,因此增加保护式检查只有第一次引用惰性值时才是必要的.
          * 但不幸的是,很难解除之后的惰性值保护式检查,所以与"立刻"值相比惰性值具有额外的开销.
          * 适用范围: 1.保护式带来的额外开销小于初始化带来的开销时(例如开启数据库连接)
          * 2.将某些值惰性化能简化系统初始化过程并确保执行顺序满足依赖条件时(例如缩短模块的启动时间)
          * 3.确保对象其他字段的初始化过程能优先执行
          */

    }
}
