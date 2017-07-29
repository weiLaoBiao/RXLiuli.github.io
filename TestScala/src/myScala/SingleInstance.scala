package myScala

/**
  * Created by RXLiuli on 2017/5/20.
  */
//尝试实现单例模式
object SingleInstance {
    def default(): Unit = {
        //尝试直接调用私有构造器实例化Person
        //        val person = new Person("琉璃", 15)
        //上面会产生编译错误(无法访问私有的构造器),也即是说构造函数私有化成功了
        //那么,如何提供静态的工厂方法?
        val p = Person("琉璃", 15) //是的,现在我们只能通过工厂方法去实例化Person了
        println(s"Person(${p.name}, ${p.age})")
        //        val emp = new Employee("灵梦", 16) //编译错误
        //        val emp = Employee("灵梦", 16) //看起来工厂对象也被私有化了
    }

    //定义一个简单的Person类
    class Person private(var name: String, var age: Int) //此处主构造器被私有化了

    object Person { //定义一个Person类的伴生对象
        //工厂方法apply,不过居然能够直接调用私有的构造器?!
        def apply(name: String = "暂无", age: Int = 0): Person = new Person(name, age)

        /**
          * 那么,Java中有名的静态工厂为什么没有出现在Scala中呢?
          * 主要原因是语言的设计问题:
          * 静态工厂用于解决构造参数中大量的默认参数,避免构造函数大量重载造成冗余.
          * 而Scala之所以不需要静态工厂,是因为Scala本身就提供了很多方法解决默认参数的问题.
          */
    }

    //我们可以尝试做得更简洁
    //Employee和上面的Person类看起来是等价的...
    case class Employee private(var name: String = "默认", var age: Int = 0) //失败

}
