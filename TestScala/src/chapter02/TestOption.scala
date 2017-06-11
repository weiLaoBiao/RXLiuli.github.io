package chapter02

/**
  * Created by RXLiuli on 2017/4/5.
  */
object TestOption {
    def default(): Unit = {
        //创建一个映射表
        val stateCapital = Map(
            "Alabama" -> "Montgomery",
            "Alaska" -> "Juneau",
            "Wyoming" -> "Cheyenne",
            //...
            1 -> '0', //注:映射的元素和名字可以不是同一个类型
            //可以使用对象或其他集合
            Point() -> List[Any](10, "", '\n', Point()),
            '\t' -> None //(i:Int)=>i+1 //无法使用函数字面量
        )

        //直接打印Map映射表中的元素
        println("\nGet the capitals wrapped in Options:" +
        "\nAlabama:" + stateCapital.get("Alabama") +
        "\nWyoming:" + stateCapital.get("Cheyenne") + //Map是单向映射
        "\nUnknown:" + stateCapital.get("Unknown"))

        //使用Option
        println("\nGet the capitals themselves out of the Options:" +
        "\nAlabama:" + stateCapital("Alabama") + //省略掉了Option,直接输出映射的值
        "\nWyoming:" + stateCapital.getOrElse("Wyoming", "Oops!") + //更方便的写法
        //原本的写法是: stateCapital.get("Wyoming").getOrElse("Oops!"),现在的语法更简洁了
        "\nUnknown:" + stateCapital.getOrElse("Unknown", "Oops2!"))

        println("\t:"+stateCapital.get('\t'))
        println("Point():"+stateCapital.getOrElse(Point(1), "Oops3!"))
    }
}
