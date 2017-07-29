package myScala

import java.io.{File, PrintWriter}

import scala.io.Source

/**
  * Created by RXLiuli on 2017/5/30.
  */
//IO流初步
object IOStreamPreliminary {
    def default(): Unit = {
        //读取本地文件
        val file = Source.fromFile("""D:\Text\Scala\TestText\TestScala.txt""")
        file.getLines.foreach(println)
        println(file.mkString)
        //只能读取一次...
        file.close()
        //读取网络文件
        //        val url = new URL("https://www.baidu.com/") //创建url对象
        //        val webFile = Source.fromURL(url) //读取url对象并返回迭代器
        //我们也可以一句话写完,直接读取字符串中包含的 url 链接
        val webFile = Source.fromURL("https://www.baidu.com/")
        //        webFile.getLines.foreach(println)
        webFile.close()
        //向文件写入文本
        println(new File("""D:\Text\Scala\TestText\TestScala.txt""").exists)
        val writer = new PrintWriter(new File("""D:\Text\Scala\TestText\TestScala.txt"""))
        (1 to 100).foreach(writer.println)
        writer.close()
        //创建文本
        val newFile = new File("IOStream.txt")
        val writer2 = new PrintWriter(newFile)
        (1 to 100).foreach(writer2.println)
        writer2.close()


    }
}
