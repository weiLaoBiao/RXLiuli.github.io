package chapter02

/**
  * Created by RXLiuli on 2017/4/2.
  */
//定义Point类,并提供默认的初始化值
case class Point(x: Double = 0.0, y: Double = 0.0) {
    //shift方法,用于现有的Point对象中的点进行平移,从而创建一个新的Point对象
    //他使用了copy方法,copy方法也是case类自动创建的
    def shift(deltax: Double = 0.0, deltay: Double = 0.0): Point = {
        copy(x + deltax, y + deltay)
    }
}
