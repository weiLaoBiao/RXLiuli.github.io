package chapter03

/**
  * Created by RXLiuli on 2017/4/16.
  */
object TraitInitial {
    def default(): Unit = {
        //Scala中的接口和混入
        //调用服务执行了某些工作并返回一些输出
        val service1 = new ServerImportante("uno")
        (1 to 3).foreach(i => println(s"Result: ${service1.work(i)}"))
        //现在要在服务中混入一个标准日志库,声明一个"混入"了日志功能的服务
        val service2 = new ServerImportante("dos") with StdoutLogging { //混入接口
            override def work(i: Int): Int = { //重写了work方法
                info(s"Starting work: i = $i")
                val result = super.work(i) //调用父类的方法
                info(s"Ending work: i = $i,result = $result")
                result
            }
        }
        //再次使用日志服务
        (1 to 3).foreach(i => println(s"Result: ${service2.work(i)}"))
        //注: 为了能注入日志,在该示例中我们修改了服务的行为,但并未修改服务与客户之间的"契约"
        //也即是说,我们并未修改服务的对外行为(严格意义上来说附加的I/O操作已经对代码和外界的交互产生了影响)
        //根据需要可以混入任意多个日志(多继承?!)
        //一些trait也许不会对现在行为做任何修改,只会添加一些有用的方法,并相互独立
    }
}

//在应用中混入日志,编写了下列服务
class ServerImportante(name: String) {
    def work(i: Int): Int = {
        println(s"ServiceImportante: Doing important work! $i")
        i + 1
    }
}

//定义一个日志抽象
trait Logging {
    def info(message: String): Unit

    def warning(message: String): Unit

    def error(message: String): Unit
}

//实现日志抽象
trait StdoutLogging extends Logging {
    override def info(message: String): Unit = println(s"info: $message")

    override def warning(message: String): Unit = println(s"warning: $message")

    override def error(message: String): Unit = println(s"error: $message")
}
