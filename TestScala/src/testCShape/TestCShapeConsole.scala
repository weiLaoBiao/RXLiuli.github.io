package testCShape

/**
  * Created by RXLiuli on 2017/5/16.
  */
object TestCShapeConsole extends App {
    default()

    def default(): Unit = {
        //实例化SE对象
        val list = List[Job](
            Job("编码", "购物车模块"),
            Job("测试", "给购物车模块做单元测试")
        )
        val ai = new SE(100, "112", "月姬", 1000, "女", list)

        val list2 = List[Job](
            Job("设计", "数据库建模"),
            Job("编写文档", "详细设计说明书")
        )
        val ai2 = new SE(30, "113", "灵梦", 16, "女", list2)
        val pm = new PM(30, "113", "灵梦", 16, "女", list2)
        //集合
        val empls = List(ai, ai2, pm)
        empls.foreach(em => println(em.doWork()))
    }


    //工作类
    case class Job(name: String, description: String)

    //员工类
    abstract class Employee(var id: String, var name: String, var age: Int,
                            var sex: String, var workList: List[Job]) {
        def doWork(): String
    }

    //职工类
    class SE(popularity: Int, id: String, name: String, age: Int,
             sex: String, workList: List[Job])
    extends Employee(id, name, age, sex, workList) {
        //重写工作方法
        def doWork(): String = {
            workList.map(job => s"${job.name}: ${job.description}\n").mkString
        }
    }

    //经理类

    //yearOfExperience:资历
    class PM(yearOfExperience: Int, id: String, name: String, age: Int,
             sex: String, workList: List[Job])
    extends Employee(id, name, age, sex, workList) {
        //重写工作方法
        def doWork(): String = s"$name: 管理员完成工作内容！"
    }

}
