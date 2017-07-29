package chapter04

/**
  * Created by RXLiuli on 2017/4/27.
  */
//模式匹配的其他用法(模式匹配这一强大的特性并不仅限于case语句)
object MatchingRests {
    def default(): Unit = {
        //测试类:
        case class Address(street: String, city: String, country: String)
        case class Person(name: String, age: Int, address: Address)

        //提取所需要的对象的属性
        val Person(name, age, Address(_, state, _)) = Person("Dean", 29,
            Address("1 Scala Way", "CA", "USA"))
        println(s"Person name: $name,age: $age,Address state: $state")
        //提取List中的元素
        val head +: tail = List(1, 2, 3, 4, 5)
        println(s"List head: $head,tail: $tail")
        //提取Vector中的元素
        val head21 +: head22 +: tail2 = Vector(1, 2, 3)
        println(s"Vector head21: $head21,head22: $head22,tail2: $tail2")
        //当然,也可以用其他的语法,此种只能匹配同等数量的元素
        val Seq(a, b, c) = List(1, 2, 3)
        println(s"Seq a: $a,b: $b,c: $c")

        //如果想要在if语句中使用,那也是可以的
        val p = Person("Dean", 29, Address("1 Scala Way", "CA", "USA"))
        println {
            if (Person("Dean", 29, Address("1 Scala Way", "CA", "USA")) ==
            p) "yes"
            else ""
        }
        //然而...这儿并不能使用占位符
        //        println {
        //            if (p == Person(_, 29, Address(_, _, "USA"))) "yes"
        //            else "no"
        //        }

        /**
          * 注: 名为$eq的内部函数用于==测试,因为在JVM规范中,只允许字母,数字,_和$作为标识符,
          * Scala对一些非字母数字的字符做了"字符映射",使它们符合JVM规范.=为$eq
          */
        //假设有一个函数,参数为整数序列,将所有的整数之和和整数个数返回
        def sumAndCount(ints: Seq[Int]) = (ints.sum, ints.size)

        //想要知道方法的返回值
        val (sum, count) = sumAndCount(List(1, 2, 3, 4, 5))
        println(s"List sum: $sum,count: $count")
        //        sum = 3 //再次赋值出现编译错误,说明是val

        //甚至于,for循环中,也可以使用模式匹配
        val dogBreeds = Seq(Some("Doberman"), None, Some("Yorkshire Terrier"),
            Some("Dachshund"), None, Some("Scottish Terrier"),
            None, Some("Great Dane"), Some("Portuguese Water Dog"))
        println("second pass:")
        for {
            Some(breed) <- dogBreeds
            upcasedBreed = breed.toUpperCase()
        } println(upcasedBreed)

        //模式匹配与case语句的另外一个便利用法是,它们可以使带复杂参数的函数字面量更易于使用
        case class Address2(street: String, city: String, country: String)
        case class Person2(name: String, age: Int)
        val as = Seq(
            Address2("1 Sdcala Lane", "Anytown", "USA"),
            Address2("2 Clojure Lane", "Othertown", "USA")
        )
        val ps = Seq(
            Person2("Buck Trends", 29),
            Person2("Clo Jure", 28),
            Person2("liuli", 17)
        )
        /**
          * zip函数用于压缩序列
          * 将传入的两个参数(序列)中相应位置上的元素组成一个pair数组(元祖),
          * 如果一个参数元素比较多,那么多余的参数会被删掉
          */
        val pas = ps zip as
        println(s"pas: $pas")
        //不太美观的方法
        //常规函数,带一个元祖参数,使用模式匹配从元组中的两个元素中提取属性值.
        pas map { tup =>
            val Person2(name, age) = tup._1
            val Address2(street, city, country) = tup._2
            s"$name (age: $age) lives at $street, $city, in $country"
        } foreach println
        //不错的方法
        //偏函数,这种写法在语法更简洁,特别是需要从元组和更复杂的结构抽取值时.
        //注: 因为是偏函数,所以case表达式必须精确匹配输入,否则运行时会抛出MatchError.
        pas map {
            case (Person2(name, age), Address2(street, city, country)) =>
                s"$name (age: $age) lives at $street, $city, in $country"
        } foreach println
        /**
          * 模式匹配是一个强大的"协议",用于从数据结构中提取数据.
          */
    }
}
