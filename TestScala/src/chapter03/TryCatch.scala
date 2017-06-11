package chapter03

/**
  * Created by RXLiuli on 2017/4/15.
  */
object TryCatch {
    def default(): Unit = {
        //try,catch和final子句,书本实例,讲真IO流不怎么会
        //        countLines("""F:\Firefox""")

        //实验,个人理解
        myTest()
    }

    def myTest(): Unit = {
        //try catch和Java中的感觉上差不多,除了catch语句需要用模式匹配
        try {
            val i = 9
            if (i % 2 == 0) println("try语句块执行! ")
            else throw new RuntimeException("i must be event") //抛出异常
        } catch {
            case ex: Exception => { //向下兼容子类型异常
                //toString打印出错误类型和错误消息
                println(s"catch语句块执行! $ex")
                //getMessage仅打印错误消息
                println(s"获取异常的消息: ${ex.getMessage}")
            }
        } finally {
            println("finally语句块执行! ")
        }
    }

    import scala.io.Source
    import scala.util.control.NonFatal

    def countLines(fileName: String): Unit = {
        println("开始执行!")
        var source: Option[Source] = None
        try {
            source = Some(Source.fromFile(fileName))
            val size = source.get.getLines.size
            println(s"file $fileName has $size lines")
        } catch {
            case NonFatal(ex) => println(s"Non fatal exception! $ex")
        } finally {
            source.foreach {
                println(s"Closing $fileName")
                _.close()
            }
        }
    }
}
