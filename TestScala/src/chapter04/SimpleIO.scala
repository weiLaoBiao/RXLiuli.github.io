package chapter04

import scala.io.Source

/**
  * Created by RXLiuli on 2017/4/26.
  */
object SimpleIO {
    def default(): Unit = {

        //读取本地文件
        val files = Source.fromFile(""".\src\chapter04\AgainTypeMatch.scala""")
        files.foreach(print)
        //读取网络文件
        val webFile = Source.fromURL("http://spark.apache.org")
        webFile.foreach(print)
        webFile.close()
    }
}
