package chapter08

/**
  * Created by RXLiuli on 2017/5/10.
  */
//实现case类
object RealizedCase {
    def default(): Unit = {
        val p = Person("月姬")
        println(s"p: $p")
        //简单使用=赋值,复制了p的堆内存地址还是p所指向的实例本身?
        val p2 = p
        p2.age = 10 //改变了p2所指向的实例的age的值
        println(s"p: $p,p2: $p2") //事实证明简单的赋值只会将堆内存地址复制
        //比较两个case类的实例是否相等?
        val p3 = Person("月姬", 10)
        //        println(s"哈希值 p: ${p.hashCode},p3: ${p3.hashCode}")
        //比较两个实例是否相等
        println(s"p == p3? ${p == p3}") //==
        println(s"p equals p3? ${p equals p3}") //equals
        //两种比较方式完全相同(就是不知道是哪个调用哪个了....)
        //而且看起来==和equals比较的好像都是实例中具体的值而非引用地址...
        p3.info = "真祖" //改变了case类的字段
        println(s"改变了字段info之后的p3: p == p3? ${p == p3}") //仍然为true?!
        p3.age = 100
        println(s"改变了字段age之后的p3: p == p3? ${p == p3}") //此时才为false...
        //
        /**
          * 思考: 比较的时候发生了什么?!
          * 当改变字段info时p和p3仍然相等,而我们改变了age后p和p3才变得不相等.
          * 那么,为什么改变字段info没有用? 猜想:只比较主构造器内的字段是否相等.
          * 为什么会只比较主构造器内的字段? 猜想:和unapply解构方法有关.
          */
        println {
            """
              |//////////////////////////////////////////////////////////////////////
              |/////////////////////////使用普通类做对比////////////////////////////////
              |//////////////////////////////////////////////////////////////////////
            """.stripMargin
        }
        val p4 = new Person2("月姬")
        println(s"p4: $p4") //直接输出了乱码...
        //简单使用=赋值,复制了p4的堆内存地址还是p4所指向的实例本身?
        val p5 = p4
        println(s"p5: $p5")
        p5.age = 10 //改变了p5所指向的实例的age的值
        println(s"p4 name: ${p4.name}") //p4的name也改变了
        //比较两个case类的实例是否相等?
        val p6 = new Person2("月姬", 10)
        //比较两个实例是否相等
        println(s"p4 == p6? ${p4 == p6}") //==
        println(s"p4 equals p6? ${p4 equals p6}") //equals
        p6.info = "真祖" //改变了case类的字段
        println(s"改变了字段info之后的p2_3: p4 == p6? ${p4 == p6}")
        p6.age = 100
        println(s"改变了字段age之后的p2_3: p4 == p6? ${p4 == p6}")
        //不管怎么样,以上全部都是false,因为Object中的==和equals比较的是对象的引用
        //而我们并没有在子类中重写toString和equals方法

        println {
            """
              |//////////////////////////////////////////////////////////////////////
              |/////////////////////////改造普通类为case类//////////////////////////////
              |//////////////////////////////////////////////////////////////////////
            """.stripMargin
        }

        //那么,我们将进行实验,把普通的类改造成case类
        val p7 = Person3("月姬")
        println(s"p7: $p7")
        //简单使用=赋值,复制了p7的堆内存地址还是p7所指向的实例本身?
        val p8 = p7
        p8.age = 10 //改变了p8所指向的实例的age的值
        println(s"p7: $p7,p8: $p8") //事实证明简单的赋值只会将堆内存地址复制
        //比较两个case类的实例是否相等?
        val p9 = Person3("月姬", 10)
        //比较两个实例是否相等
        println(s"p7 == p9? ${p7 == p9}") //==
        println(s"p7 equals p9? ${p7 equals p9}") //equals
        //两种比较方式完全相同(就是不知道是哪个调用哪个了....)
        //而且看起来==和equals比较的好像都是实例中具体的值而非引用地址...
        p9.info = "真祖" //改变了case类的字段
        println(s"改变了字段info之后的p9: p7 == p9? ${p7 == p9}") //仍然为true?!
        p9.age = 100
        println(s"改变了字段age之后的p9: p7 == p9? ${p7 == p9}") //此时才为false...
        //到目前为止,我们改造的case类基本上已经能做到case类能做到的事情了
    }

    //在真实环境中使用case类
    case class Person(name: String, var age: Int = 0) {
        var info: String = "暂无简介"
    }

    //创建一个普通的类进行对比
    class Person2(val name: String, var age: Int = 0) {
        var info: String = "暂无简介"
    }

    //创建一个伴生类
    class Person3(val name: String, var age: Int = 0) {
        var info: String = "暂无简介"

        //重写toString
        override def toString: String = s"Person($name,$age)"

        //重写== 和 equals
        def ==(that: Person3): Boolean =
            that != null && this.name == that.name && this.age == that.age

        //调用==方法,虽然写了equals方法,但还是推荐使用==方法
        def equals(that: Person3): Boolean = this == that
    }

    object Person3 {
        //定义工厂构造方法apply
        def apply(name: String, age: Int = 0): Person3 = {
            new Person3(name, age)
        }

        //定义解构方法unapply
        def unapply(p: Person3): Option[(String, Int)] = {
            if (p == null) None else Some((p.name, p.age))
        }
    }

}


