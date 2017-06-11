package myScala

import scala.xml.XML

/**
  * Created by RXLiuli on 2017/5/18.
  */
object MyXML {
    def default(): Unit = {
        //读取XML文件创建对象
        val someXml = XML.loadFile("D:/Text/Scala/TestText/TestXML.xml")
        println(someXml.getClass)
        println(someXml)
        //使用text方法可以读取xml文件(不过居然会受xml的行数影响,吐槽不能...)
        println(someXml.text)
        //也可以使用foreach遍历
        someXml.text.foreach(print)
        //可以使用 \ 方法搜索提取下一子节点
        println(someXml \ "person") //直接输出
        println((someXml \ "person").text) //输出文本形式
        //输出想要的格式文本
        (someXml \ "person").foreach { s =>
            println(s"Person(${(s \ "name").text}, ${(s \ "age").text})")
        }
        //然而,不管任何情况下,你要提取不存在的元素时都会返回null
        println(s"获取子节点none: ${someXml \ "none"}, " +
        s"type: ${(someXml \ "none").getClass}")

        //使用 \\ 方法搜索提取所有子节点
        //找所有名为name的节点
        println(someXml \\ "name")

        //通用遍历方法(递归),(使用函数去自定义格式)
        def myXml(someXml: scala.xml.Elem): String = {
            someXml.text
        }


    }
}
