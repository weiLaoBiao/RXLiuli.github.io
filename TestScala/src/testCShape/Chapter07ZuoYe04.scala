package testCShape

/**
  * Created by RXLiuli on 2017/5/16.
  */
object Chapter07ZuoYe04 extends App {
    default()

    def default(): Unit = {
        val list = List[PrintSalary](new SE, new PM)
        list.foreach(_.printSalary())
    }

    //定义打印工资的类
    trait PrintSalary {
        def printSalary(): Unit
    }

    //定义工程师类
    class SE() extends PrintSalary {
        override def printSalary(): Unit = println("工程师的工资 基本工资: ￥4000")
    }

    //定义经理类
    class PM() extends PrintSalary {
        override def printSalary(): Unit =
            println("项目经理的工资 基本工资: ￥5000 + 项目奖金: ￥1000")
    }

}
