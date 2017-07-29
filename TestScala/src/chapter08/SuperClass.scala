package chapter08

/**
  * Created by RXLiuli on 2017/5/9.
  */
//父类
object SuperClass {
    def default(): Unit = {
        /**
          * 子类是从父类或基类中派生的派生类，是大部分面向对象语言的核心特征。
          * 这种机制用来重用、封装和实现多态行为（具体行为取决于实例在类型层次结构中的实际类型）。
          * 像 Java 一样， Scala 只支持单一继承，而不是多重继承。
          * 子类（或派生类）可以有且只有一个父类（即基类）。
          * 唯一的例外是， Scala 的类型结构中的根类 Any 没有父类。
          */
        //展示了类型成员用法的例子，并把其中的细节重点列出:
        abstract class BulkReader { //定义了一个抽象类BulkReader
        type In //定义了一个类型,作为参数化类型的补充
        val source: In //定义了一个In参数化类型的抽象字段source
        def read: String //定义了一个抽象方法
        }
        class StringBulkReader(val source: String) extends BulkReader {
            type In = String //确定了父类的类型变量In的实际值
            def read: String = source //实现了方法
        }
        class FileBulkReader(val source: java.io.File) {
            type In = java.io.File

            def read: String = {
                "其他操作..."
            }
        }

        /**
          * 如在 Java 中一样，关键字 extend 表示后面是父类，因此本例中的父类为 BulkReader 。
          * 在 Scala 中，当类继承 trait 时，也用 extend 表示
          * （甚至当该类用 with 关键字混入了其他 trait 时也是如此）。
          * 此外，当 trait 是其他 trait 或类的子 trait 时，也用 extend 。
          * 是的， trait 可以继承类。
          * 如果我们不指定父类，默认父类为 AnyRef 。
          */

        //定义Animal抽象类
        abstract class Animal(var name: String, var age: Int) {
            //重写AnyRef的toString方法
            override def toString: String = s"Animal name: $name,age: $age"
        }
        //定义Dog类并继承Animal
        class Dog(name: String, age: Int, var intro: String)
        extends Animal(name, age) {
            //覆盖父类Animal的toString方法
            override def toString: String =
                s"Dog name: $name,age: $age,intro: $intro"

            //Dog的专有方法(run:运行,跑)
            def run(): Unit = println("The dog is Run!")
        }
        //定义Cat类并继承Animal
        class Cat(name: String, age: Int, val color: String)
        extends Animal(name, age) {
            //覆盖父类Animal的toString方法
            override def toString: String =
                s"Cat name: $name,age: $age,color: $color"

            //Cat的专有方法(climbing:爬)
            def climbing(): Unit = println("The cat is climbing!")
        }

        //测试类
        object test {
            def testFoo(): Unit = {
                val animal: Animal = new Dog("白之兽", 1000, "盖亚家养的狗.")
                val animal2: Animal = new Cat("琉璃", 15, "黑色")
                println(s"animal: $animal")
                println(s"animal2: $animal2")
                val dog = foo[Dog](animal)
                if (dog.isEmpty) println("animal not is Dog!") else dog.get.run()
                val cat = foo[Cat](animal2)
                if (cat.isEmpty) println("animal not is Cat!")
                else cat.get.climbing()
                //不太明白为什么会错...
                //                foo[Dog](animal2).get.run()
                animal2 match {
                    case temp: Dog => temp.run()
                    case _ => println("animal not is Dog!")
                }
            }

            //定义了一个安全的向下转型的方法,莫名其妙的不安全了 Error
            def foo[T <: Animal](animal: Animal): Option[T] = animal match {
                case t: T => Some(t)
                case _ => None
            }

        }
        test.testFoo()

    }
}
