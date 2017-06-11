package chapter06

import java.util

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
  * Created by RXLiuli on 2017/4/30.
  */
//函数式编程的数据结构
object DataStructure {
    def default(): Unit = {
        /**
          * List(列表),Vector(向量)等序列型集合
          * Array(数组),Map(映射),Set(集合),Tupled(元组)
          */
        /**
          * Seq(序列):许多数据类型是序列型的,也就是说元素可以按特定的顺序访问.
          * scala.collection.Seq是一个trait(特质),是所有可变或不可变序列类型的抽象.
          * 其子trait scala.collection.mutable.Seq(可变序列特质)
          * 及scala.collection.immutable.Seq(不可变序列特质)
          */
        //List是一种序列,是函数式编程中最常用的数据结构,从第一代函数式语言Lisp就有了
        //列表在追加元素时,元素会被追加到列表的开头,形成新的头部.
        //从旧列表中创建新列表的操作是O(1),而计算长度则是O(N),
        //其他需要从头部遍历序列的操作也是如此.
        val list = List("Programming", "Scala") //工厂方法
        //虽然看起来很奇怪,但实际上这些操作复杂度恒为O(1),因为+:是右绑定操作符
        val list2 = "People" +: "Should" +: "read" +: list //向头部追加元素
        //也就是说,实际上追加元素的操作是这样子的
        val list3 = "People" +: ("Should" +: ("read" +: list))
        //是的,感觉有点似曾相识呢,递归!!!
        //也可以凭空开始构造序列
        val list4 = 1 +: 2 +: 3 +: 4 +: 5 +: Nil //Nil与List.empty[Nothing]
        //Nothing是Scala中所有其他类型的子类型
        val list5 = List.empty[Nothing]
        println(s"emptyList: $list5")
        /**
          * 在需要的时候构造List的确是常用的做法,但不推荐方法将List作为参数或返回值.
          * 而应该用Seq,这样Seq的所有子类型都可以用了,包括List和Vector.
          */
        //注: 操作符 +: 等价于 :: (List专用) , ++ 等价于 :::
        //Seq的构造方法是 +: 而非 ::,不过当你对伴随对象时用Seq.apply方法时,
        //将创建一个List,这是因为Seq只是一个特质,而非具体的类.
        val seq1: Seq[Int] = Seq(1, 2, 3, 4)
        //List是离散存储,链表结构,所以增删元素高效!!!
        //其实,如果需要经常随机存取序列的元素,而不需要经常增删元素时,则可以使用Vector.
        //Vector频繁随机访问元素都只需要O(1)的复杂度,但增删元素相当低效.
        val vector = Vector(1, 2, 3, 4)
        println(s"vector index is 3 value: ${vector.apply(0)}")
        //注: Seq默认的实际类型为scala.collection.Seq.因此Seq类型的实例可能是可变的
        val seq2: Seq[Int] = Array[Int](1, 2, 3, 4) //Seq兼容Array....
        println(s"seq2 type: ${seq2.getClass}")
        //List,Vector,Seq初始化方法一览
        val normalList: List[Int] = List[Int](1, 2, 3)
        val nonEmptyList = List(1, 2, 3) //Int是可省略的,编译器会类型推断为Int
        val emptyList: List[Int] = Nil //Nil是List的空列表,Int不可省略
        val emptyList2 = List.empty[Int] //此处的参数化类型不能省略,否则会推断为Any
        println(s"Nil == List.empty? ${Nil == List.empty}") //true
        val normalVector: Vector[Int] = Vector[Int](1, 2, 3)
        val nonEmptyVector = Vector(1, 2, 3) //推断为最近公共父类型
        val emptyVector = Vector.empty[Int]
        //        val emptyVector2: Vector[Int] = Nil //编译错误
        val normalSeq: Seq[Int] = Seq[Int](1, 2, 3)
        val nonEmptySeq = Seq(1, 2, 3)
        val emptySeq = Seq.empty[Int]
        val emptySeq2 = Nil //可以正常运行?
        //实际类型是: scala.collection.immutable.$colon$colon
        println(s"seq practical type is ${nonEmptySeq.getClass}")
        //Scala定义的List是不可变的,不过他还定义了其他可变的列表类型.
        //如ListBuffer和MutableList,只有当必须修改元素时才可以使用可变类型
        val listBuffer = ListBuffer(1, 2, 3, 4)
        listBuffer += 5 //为列表添加新的元素
        listBuffer(1) = 10 //改变元素的值
        println(s"listBuffer: $listBuffer") //打印
        val mutableList = mutable.MutableList(1, 2, 3, 4)
        mutableList += 5
        mutableList(1) = 10
        println(s"mutableList: $mutableList")

        /**
          * Map(映射):
          * 另一种常见的数据结构式映射(scala.collection.immutable.Map)，
          * 在其他语言中有时也被称为散列,散列表,映射表用来存储键值对,
          * 但不该将其与很多数据结构的map方法混淆.映射表与map方法有一定程度的相似,
          * 前者每个键都对应一个值,后者每个输入元素都产生一个输出元素.
          */
        //Scala中的Map初始化语法:
        val stateCapitals = Map(
            "Alabama" -> "Montgomery",
            "Alaska" -> "Juneau",
            "Wyoming" -> "Cheyenne"
        )
        println("lengths: " + stateCapitals.map {
            kv => (kv._1, kv._2.length) //计算Map中字符串value的长度
        })
        //这种实现方式和上面完全相同,不过模式匹配更优雅一点
        println("caps: " + stateCapitals.map {
            case (k, v) => (k, v.toUpperCase)
        })
        //使用+操作符新增一些键值对
        val stateCapitals2 = stateCapitals + (
        "New York" -> "Albany", "Illinois" -> "Springfield")
        println(s"stateCapitals2: $stateCapitals2")
        //实际上key->value的语法形式实际上是用库中的隐式转换实现的,实际调用了Map.apply
        //Map.apply的方法参数是一个两元素的元组(键值对).
        //我们可以用+向Map中添加一个或多个键值对(创建新的Map实例)
        //如果在stateCapitals+("Virginia"->"Richmond")漏掉了小括号呢?
        println(stateCapitals + "New York" -> "Albany")
        /**
          * 注: 如果一个包含+的表达式返回结果类型为String,而这并不是你所期望的结果,
          * 这可能是因为编译器认为这是该表达式唯一可行的解析方式,
          * 就把+两端的子表达转为字符串,再相加.
          */
        /**
          * 实际上,我们并不能调用new Map("Alabama"->"Montgomery",...)方法,
          * 因为它是一个trait.相反,Map.apply则会根据给定的输入数据用最佳的方式构造实例.
          * 与List不同,Map有可变和不可变的两种实现:
          * scala.collection.immutable.Map[A,B]
          * 与scala.collection.mutable.Map[A,B]
          * 可变的实现需要显式导入,不可变的实现则宜用Predef暴露出来了.
          * 两种实现都定义了+和-操作用于增加和移除元素,
          * 以及++和--操作来增加和移除Iterator中定义的元素(Iterator可以为其他集合).
          */

        //Set(集合)
        /**
          * 集合是无序集合类型的一个例子,所以集合不是序列,集合要求元素具有唯一性:
          */
        val states = Set("Alabama", "Alaska", "Wyoming")
        println(s"states: $states")
        val lengths = states.map(_.length)
        println(s"lengths: $lengths")
        val states2 = states + "Virginia"
        println(s"states2: $states2")
        val states3 = states2 + ("New York", "Illinois")
        println(states3) //注: 集合中的元素不保证顺序,所以不能按照下标访问
        //那么,集合的作用是什么?
        //要求元素不重复的无序集合
        val set = Set(1, 2, 3, 4)
        println(set + 5 + 6 + 2) //如果有重复元素则会忽略不加入,用于过滤相同的元素
        //考虑如下情况: 把成绩表中重复的学生过滤掉
        //学生成绩表,此处为了简化将学生姓名认为是学生唯一标识(主键)
        val stuList = List("月姬", "灵梦", "亚当", "月姬", "楚轩", "灵梦")
        println(stuList)
        //学生成绩表过滤掉重复的名字
        val stuSet = stuList.toSet[String]
        println(stuSet)
        //判断那些学生没有考试?
        //学生信息表,不重复(将学号简化为姓名)
        val stuInfoSet = Set("月姬", "灵梦", "亚当", "楚轩", "八云紫", "琉璃")
        //查询某个学生是否存在:
        println(s"月姬是否存在? ${stuInfoSet.contains("月姬")}")
        //查询成绩不存在的学生信息:
        val nonResultSet = stuInfoSet.filterNot(stuSet(_))
        println(s"成绩不存在的学生: $nonResultSet")
        //在学生信息表中增加学生信息,注意两种添加方式的区别!
        println(stuInfoSet + "滑瓢") //此处先调用了Set的+方法,然后调用toString
        println(set + "滑瓢") //此处首先调用了toString方法,之后用字符串连接符连接
        //那么,如何做才能达到我们希望的效果呢?
        println(s"${stuInfoSet}滑瓢") //这样就必然是不会调用Set的+方法了
        val str = set.+("滑瓢")
        println(str.getClass)
        //即便我们使用了.+方法,还是一样的结果,为什么呢?
        //编译器认为使用Set的+方法不可行,所以直接调用了字符串连接符(调用方式完全一样)
        //就像这样也是可以的,不过我从不这样做,我建议你也不要这样写(看起来太蠢了).
        println("hello ".+("world ").+("! "))

    }
}
