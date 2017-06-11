package chapter02

/**
  * Created by RXLiuli on 2017/4/2.
  */
abstract class Shape {
    /**
      * draw带两个参数列表,其中一个参数列表带着绘制偏移量的参数
      * 另一个参数列表是函数参数
      */
    //多参数列表,第一个参数列表提供了默认值
    //由于Point对象本身就有默认值,所以不需要再给于赋值了
    def draw(offset: Point = Point())(f: String => Unit): Unit = {
        f(s"draw(offset = $offset),${this.toString}")
    }

    //重载的单参数列表多参数draw方法
    //当然,如果需要改变默认值也是可以从这儿改变的
    //注:即便方法的参数列表看起来不同,但实际上draw() 和 draw2() 的参数是相同的
    //也就是说,实际上多个参数列表和单个参数列表多参数本质上是没有区别的
    def draw2(offset: Point = Point(1,1), f: String => Unit): Unit = {
         f(s"draw(offset = $offset),${this.toString}")
    }
}

//圆形类,参数是圆心和半径
case class Circle(center: Point, radius: Double) extends Shape

//长方形类,参数是左下角的顶点,以及宽度和高度
case class Rectangle(lowerLeft: Point, height: Double, width: Double) extends Shape

//三角形类,参数是三个顶点
case class Triangle(point1: Point, point2: Point, point3: Point) extends Shape