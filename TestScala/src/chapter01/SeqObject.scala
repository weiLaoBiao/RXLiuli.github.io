package chapter01

/**
  * Created by RXLiuli on 2017/4/2.
  */
object SeqObject {
    def default(): Unit = {
        //定义一个Seq返回值的方法
        def mySeq(strings: String*): Seq[String] = {
            //下面两条语句完全相同
            //strings.map((s:String)=>s.toUpperCase())
            strings.map(_.toUpperCase())
        }

        //调用一下方法
        mySeq("RX", "Liuli").foreach(printf("%s ", _))
        //也可以定义一个列表,然后将其当作参数传入方法
        val list = List[String]("RX", "Liuli")
        //_* 指代的是将集合里面的所有元素作为参数传入
        mySeq(list: _*).foreach(printf("%s ", _))
    }

}
