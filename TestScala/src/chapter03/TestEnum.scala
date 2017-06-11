package chapter03

/**
  * Created by RXLiuli on 2017/4/16.
  */
object TestEnum {

    object sex extends Enumeration {
        type sex = Value
        val man, woman = Value
    }

    def default(): Unit = {
        println(StuClass.cl03) //使用枚举值
        //可以打印枚举列表,values返回了所有枚举值组成的列表
        StuClass.values.foreach(println)
        println(StuClass.values.getClass)
        //直接使用枚举,就没有值...
        println(sex.man)
    }
}

//创建一个测试枚举类型
object StuClass extends Enumeration {
    type StuClass = Value //类型名
    val cl01 = Value("一班")
    val cl02 = Value("二班")
    val cl03 = Value("三班")
    val cl04 = Value("四班")
    val cl05 = Value("五班")
}

