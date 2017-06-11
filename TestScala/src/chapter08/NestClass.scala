package chapter08

/**
  * Created by RXLiuli on 2017/5/12.
  */
//嵌套类型
object NestClass {
    def default(): Unit = {
        //嵌套类型,也就是在对象中定义类型转义的异常和其他有用的类型而已,实际上是很常见的做法:


        case object Foo
        println(Foo.hashCode)
        println("Foo".hashCode)
        //上面两个输出是相同的?!
        /**
          * 显然，为 case 对象生成的 hashCode 方法仅仅将对象的名称做了散列。
          * 而对象的包像对象的字段一样被忽略了。
          * 这意味着，当需要更强的hashCode 方法时， case 对象是存在风险的。
          */
        /**
          * 所以当需要更强的 hashCode 方法时，避免使用 case 对象，
          * 例如：对象是基于散列运算的映射或集合的键。
          */
    }

    //一个数据库层可能的骨架结构
    object Database { //数据库的一个简单接口
    case class ResultSet(/*...*/) //封装了查询结果的集合。我们把不关心的细节省略了。
    case class Connection(/*...*/) //封装了连接池和其他信息。
    case class DatabaseException(message: String, cause: Throwable) extends
    RuntimeException(message, cause)

        /**
          * 使用 sealed 的继承结构表示状态；所有允许的值都在这里定义。
          * 当实例实际上不携带状态信息时，使用 case 对象。
          * 这些对象表现得像 “ 标志位 ” ，用来表示状态。
          */
        sealed trait Status // 添加了sealed关键字,该类的所有子类必须和它在同一源文件
        case object Disconnected extends Status

        case class Connected(connection: Connection) extends Status

        case class QuerySucceeded(results: ResultSet) extends Status

        case class QueryFailed(e: DatabaseException) extends Status

    }

    class Database {

        import Database._

        /**
          * ??? 是定义在 Predef 中的真实方法。它会抛出一个异常，用来表示某一方法尚未实现的情况。
          * 该方法是最近才引入 Scala 库的。
          */
        def connect(server: String): Status = ??? //如果调用就会报错
        def disconnect(): Status = ???

        def query(/*...*/): Status = ???
    }

}
