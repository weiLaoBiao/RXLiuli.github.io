package chapter04

/**
  * Created by RXLiuli on 2017/4/26.
  */
object SealedExtends {
    def default(): Unit = {
        //定义了一个封闭的抽象基类,由于该类被定义为封闭的,其子类型必须定义在本文件内
        sealed abstract class HttpMethod() {
            def body: String //为HTTP报文的消息体定义了一个方法
            def bodyLength = body.length
        }
        //定义了8个继承自HttpMethod的case类,每个类均在构造方法中声明了参数body:String
        //由于每个类均为case类,因此该参数是个val,它实现了HttpMethod的抽象方法def
        /** 注: 一个抽象的,不带参数的父类方法,子类可以用val实现,因为val的值是固定的(必定的).
          * 而一个不带参数,返回值为某种类型的方法可以返回任意一个该类型的变量,
          * 这样,使用val实现的方法在返回值上严格符合方法定义,当方法被调用时,
          * 使用val变量与真实调用方法一样安全.事实上,这是透明引用的一个应用.
          * 在透明引用中,我们用一个值代替一个总是返回固定值的表达式!
          */
        case class Connect(body: String) extends HttpMethod
        case class Delete(body: String) extends HttpMethod
        case class Get(body: String) extends HttpMethod
        case class Head(body: String) extends HttpMethod
        case class Options(body: String) extends HttpMethod
        case class Post(body: String) extends HttpMethod
        case class Put(body: String) extends HttpMethod
        case class Trace(body: String) extends HttpMethod
        //这是一个全覆盖模式匹配表达式,即使没有默认子句也可以达到全覆盖
        //因为输入的参数只能是HttpMethod抽象类的8个case子类的实例
        def handle(method: HttpMethod) = method match {
            case Connect(body) => s"connect: (length: ${method.bodyLength}) $body"
            case Delete(body) => s"delete: (length: ${method.bodyLength}) $body"
            case Get(body) => s"get: (length: ${method.bodyLength}) $body"
            case Head(body) => s"head: (length: ${method.bodyLength}) $body"
            case Options(body) => s"options: (length: ${method.bodyLength}) $body"
            case Post(body) => s"post: (length: ${method.bodyLength}) $body"
            case Put(body) => s"put: (length: ${method.bodyLength}) $body"
            case Trace(body) => s"trace: (length: ${method.bodyLength}) $body"
        }

        val methods = Seq(
            Connect("connect body..."),
            Delete("delete body..."),
            Get("get body..."),
            Head("head body..."),
            Options("options body..."),
            Post("post body..."),
            Put("put body..."),
            Trace("trace body..."))
        methods foreach (method => println(handle(method)))
        /**
          * 在父类型中,不带参数的抽象方法可以在子类中用val变量实现,推荐的做法是:
          * 在抽象父类型中声明一个不带参数的抽象方法,这样就给子类型如何实现该方法留下了巨大的自由,
          * 既可以用方法实现,也可以用val变量实现.
          *
          * HttpMethod 的 case 类很小，理论上我们也可以用 Enumeration 代替。但那样会有一个很大的缺陷，就是编译器就无法判断 Enumeration
          * 相应的 match 语句是否全覆盖。如果我们在这里使用了 Enumeration ，而在 match 语句中忘记了匹配 Trace 的语句，那我们也只能在运行时抛
          * 出 MatchError 的时候才知道这个错误的存在。
          * 所以: 当使用类型匹配时避免使用枚举类型,编译器无法判断匹配语句是否全覆盖.
          */


    }
}
