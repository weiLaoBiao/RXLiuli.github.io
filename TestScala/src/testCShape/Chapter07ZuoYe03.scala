package testCShape

/**
  * Created by RXLiuli on 2017/5/16.
  */
object Chapter07ZuoYe03 extends App {
    def default(): Unit = {
        val list = Vector[Shape](new Circle, new Rect)
        list.foreach(_.draw())
        val customize = new Customize with Shape {
            override def draw(): Unit = println("自定义绘制图形")
        }
        customize.draw()
    }

    default()

    //Shape(形状类)
    trait Shape {
        //定义绘图抽象方法
        def draw(): Unit
    }

    //Circle(圆形)
    class Circle extends Shape {
        override def draw(): Unit = println("正在绘制圆形")
    }

    //Rect(矩形)
    class Rect extends Shape {
        override def draw(): Unit = println("正在绘制矩形")
    }

    //Customize(自定义)
    class Customize


}
