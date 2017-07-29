package chapter11

/**
  * Created by RXLiuli on 2017/5/27.
  */
//无需区分访问方法和字段: 统一访问原则
object UniformAccessPrinciple {
    def default(): Unit = {
        //重写(实现)零元方法的两种形式
        //虽然看起来我们直接new出了一个trait的实例,实际上这儿隐式的扩展了一个匿名类
        val uapTrait: UAPTrait = new UAPTrait {} //直接使用new创建trait的匿名类
        println(s"uapTrait i: ${uapTrait.i}, type: ${uapTrait.i.getClass}")
        //使用UAPC1类的重写的方法 i
        val uapC1: UAPTrait = new UAPC1
        println(s"uapC1 i: ${uapC1.i}, type: ${uapC1.i.getClass}")
        //使用UAPC2类的重写的字段 i
        val uapC2: UAPTrait = new UAPC2
        println(s"uapC2 i: ${uapC2.i}, type: ${uapC2.i.getClass}")
        /**
          * 虽然使用了field和method两种方式,但我们都成功的重写了trait中的无参方法.
          * 那么,使用这两种方式有什么好处呢?
          * 1.你可以在父类(trait)中定义一个无参方法,然后子类可以根据需要使用具体的重写(实现)方式.
          * 2.你可以将昂贵的初始化过程推迟到必须处理的时候再处理,也可以简单地将其设置为一个具体的值.
          * 注: 可以使用val字段重写无参方法,反过来就不行!!!
          */
        /**
          * 然而结果有点出乎意料,i的类型全部是: int ?!
          */
    }

    //UAP: uniform access principle(uniform:统一 access:访问 principle:原则)
    trait UAPTrait {
        def i = 1 //定义一个返回值为1的零元方法
    }

    class UAPC1 extends UAPTrait {
        override def i = 10 //重写方法 i
    }

    //注: 此处我们使用了val字段直接重写了零元方法 i
    class UAPC2(override val i: Int = 10) extends UAPTrait

}
