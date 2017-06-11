package chapter01

/**
  * Created by RXLiuli on 2017/4/2.
  */
object InsertString {
    def default(): Unit = {
        //插入字符串
        val name = "月姬"
        val age = 17
        val address = "月世界"
        println(s"$name $age 岁 来源于$address ")
        //下面这句也可以,插入字符串可以和格式化输出同时使用
        printf(s"$name %d 岁 来源于%s \n", age, address)
        //再加上占位符试试
        val list = List[Int](1, 2, 3, 4, 5)
        //这样可以使用
        list.foreach(i => printf(s"%d + $i \n", i))
        //然而下面的就会编译出错,占位符和插入字符串不兼容
        //        list.foreach(printf(s"%d $_", _))

    }
}
