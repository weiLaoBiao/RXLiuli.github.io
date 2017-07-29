package testCShape

/**
  * Created by RXLiuli on 2017/5/16.
  */
object Chapter07ZuoYe05 extends App {
    default()

    def default(): Unit = {
        val breeder = new Breeder
        val list = List[AnimalTrait](new Lion, new Tiger, new Pigeon)
        list.foreach(breeder.feed)
    }

    //抽象动物特征
    trait AnimalTrait {
        def eat(): Unit
    }

    //狮子类
    class Lion extends AnimalTrait {
        override def eat(): Unit = println("狮子在进食")
    }

    //狮子类
    class Tiger extends AnimalTrait {
        override def eat(): Unit = println("老虎在进食")
    }

    //狮子类
    class Pigeon extends AnimalTrait {
        override def eat(): Unit = println("鸽子在进食")
    }

    //饲养员类
    class Breeder {
        def feed(animal: AnimalTrait): Unit = {
            print("饲养员在喂食: ")
            animal.eat()
            println("饲养员喂食完成！")
        }
    }

}
