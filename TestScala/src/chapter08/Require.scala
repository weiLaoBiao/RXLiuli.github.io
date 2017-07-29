package chapter08

/**
  * Created by RXLiuli on 2017/5/10.
  */
//验证输入
object Require {
    def default(): Unit = {
        //有参的构造方法有一个坏处,无法保证实例属性的有效性.
        val zipCode = ZipCode(10000, 1000) //使用重载的apply
        println(zipCode)
        val zipCode2 = ZipCode(10000) //使用主apply
        println(zipCode2)
        /*try { //故意定义一个错误的
            val zipCode3 = ZipCode(0, 6789)
            println(zipCode3)
        } catch {
            case e: java.lang.IllegalAccessError => println(e)
        }*/
        /**
          * 定义 ZipCode 这种领域专用的类的充分理由是：
          * 这种类可以在构造时对值的有效性做一次检验，然后类 ZipCode 的使用者就不再需要再次检验了。
          * Predef 中的 ensuring 和 assume 等方法也用于实现类似的目的。
          * 虽然我们在构造器的背景下讨论输入的验证，但实际上我们也可以在任何方法中调用这些断言方法。
          * 然而，价值类的类体是一个例外，它不能使用断言验证，否则就需要调用分配堆。
          * 不过，由于 ZipCode 带有两个构造器参数，它无论如何不会是价值类。
          */
        val i = 0
        val k = 1
        //随处可用require验证输入
        println(require(i == 0 && k == 1, "抛出异常时显示的值"))

    }

    //然而,Scala中我们可以通过require保证实例属性的有效性(无效则在创建对象时抛出异常)
    //思考以下实例: 邮政编码只允许5位数字或者邮政编码+4位数字
    case class ZipCode(zip: Int, extension: Option[Int] = None) {
        //检测zip和extension是否有效,否则抛出异常显示后面的字符串
        require(valid(zip, extension), s"Invalid Zip+4 specified: $toString")

        //判断邮政编码是否合法
        protected def valid(z: Int, e: Option[Int]): Boolean = {
            if (0 < z && z < 100000) e match {
                case None => validUSPS(z, 0)
                case Some(e) => 0 < e && e < 10000 && validUSPS(z, e)
            } else false
        }

        //从USPS承认的邮政编码数据库查询邮政编码是否存在
        protected def validUSPS(z: Int, e: Int): Boolean = true

        //重写toString方法,返回符合人们预期的邮政编码格式,对结尾可能的四位数字进行恰当的处理
        override def toString: String =
            s"$zip${if (extension.isEmpty) "" else "-" + extension.get}"
    }

    //伴生对象
    object ZipCode {
        //重载的apply方法
        def apply(zip: Int, extension: Int): ZipCode = new ZipCode(zip, Some(extension))
    }

}
