package chapter01

/**
  * Created by RXLiuli on 2017/4/2.
  */
object TestMatch {
    def default(): Unit = {

        //强大的模式匹配
        val str: Any = 0
        val a = str match {
            case i : Int/*类型匹配*/ if(i>0)/*守卫*/  => 0
            case str: String => "月姬"
            case _ => "空"
        }

        println(a)
    }
}
