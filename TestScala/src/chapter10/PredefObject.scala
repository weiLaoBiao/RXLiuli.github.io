package chapter10

import scala.io.StdIn


/**
  * Created by RXLiuli on 2017/5/22.
  */
//Predef对象
object PredefObject {
    def default(): Unit = {
        /**
          * 为了方便起见，只要你编译代码，
          * Scala 编译器就会自动导入顶层 Scala 包（名为 scala ）以及在
          * java.lang 包（就像 javac 的）中的定义。
          * 因此，许多常见的 Java 和 Scala 类型都可以不经过明显地导入或提供完整的名称就可以使用。
          * 另外，编译器还导入了 Predef 对象中的一些定义，
          * 它提供了很多实用的定义，其中大部分的定义我们在之前已经讨论了。
          */
        //隐式转换
        /**
          * 首先， Predef 定义了很多隐式转换规则，以下是一组转换对 AnyVal 类型的包装：
          */
        /*
        @inline implicit def byteWrapper(x: Byte) = new runtime.RichByte(x)
        @inline implicit def shortWrapper(x: Short) = new runtime.RichShort(x)
        @inline implicit def intWrapper(x: Int) = new runtime.RichInt(x)
        @inline implicit def charWrapper(c: Char) = new runtime.RichChar(c)
        @inline implicit def longWrapper(x: Long) = new runtime.RichLong(x)
        @inline implicit def floatWrapper(x: Float) = new runtime.RichFloat(x)
        @inline implicit def doubleWrapper(x: Double) = new runtime.RichDouble(x)
        @inline implicit def booleanWrapper(x: Boolean) = new runtime.RichBoolean(x)
         */
        /**
          * Rich* 类型添加了额外的方法，类似于 <= 和 compare 等比较方法。
          * — @inline 标记鼓励编译器对它做内联，即直接将 new runtime.RichY(x) 逻辑插入到代码中。
          */
        /**
          * 为什么要为每个 AnyVal 类型定义单独的方法？
          * 每个方法都用了自定义的 WrappedArray 的子类，
          * 表明 Java 原生类型的数组要比统一的数组更高效，
          * 因此就避免使用较为低效的、通用的引用类型的实现数组。
          */
        /**
          * String 也有相应的包装类型 scala/collection/immutable/WrappedString
          * 和 scala/collection/immutable/StringOps。
          * 它们给 String 增加了集合方法，将其视为 Char 元素的集合。
          * 所以， Predef 定义了 String 和以上类型的相互转化：
          * implicit def wrapString(s: String): WrappedString
          * implicit def unwrapString(ws: WrappedString): String
          * implicit def augmentString(x: String): StringOps
          * implicit def unaugmentString(x: StringOps): String
          */
        //比如我们可以这么做
        val str = "abcdefg"
        str.foreach(s => print(s"$s\t")) //将String类型当成Char类型的数组使用
        //很好奇formatted方法是做什么的...
        println(s"\n${str.formatted("$%-20s")}") //格式化的方法
        println(f"\n$$$str%-20s") //然而,f方法更好用!
        /**
          * 还有很多其他方法可以实现 Java 包装的原生类型和 Scala 的 AnyVal 类型之间的转换。
          * 它们使得 Scala 和 Java 之间的互操作性更容易：
          * implicit def byte2Byte(x: Byte) = java.lang.Byte.valueOf(x)
          * implicit def short2Short(x: Short) = java.lang.Short.valueOf(x)
          * implicit def char2Character(x: Char) = java.lang.Character.valueOf(x)
          * implicit def int2Integer(x: Int) = java.lang.Integer.valueOf(x)
          * implicit def long2Long(x: Long) = java.lang.Long.valueOf(x)
          * implicit def float2Float(x: Float) = java.lang.Float.valueOf(x)
          * implicit def double2Double(x: Double) = java.lang.Double.valueOf(x)
          * implicit def boolean2Boolean(x: Boolean) = java.lang.Boolean.valueOf(x)
          * implicit def Byte2byte(x: java.lang.Byte): Byte = x.byteValue
          * implicit def Short2short(x: java.lang.Short): Short = x.shortValue
          * implicit def Character2char(x: java.lang.Character): Char = x.charValue
          * implicit def Integer2int(x: java.lang.Integer): Int = x.intValue
          * implicit def Long2long(x: java.lang.Long): Long = x.longValue
          * implicit def Float2float(x: java.lang.Float): Float = x.floatValue
          * implicit def Double2double(x: java.lang.Double): Double = x.doubleValue
          * implicit def Boolean2boolean(x: java.lang.Boolean): Boolean = x.booleanValue
          */

        //类型定义
        /**
          * Predef 定义了若干的类型及类型别名。
          * 为了鼓励使用不可变集合， Predef 为最常用的不可变集合定义了别名：
          * type Map[A, +B] = collection.immutable.Map[A, B]
          * type Set[A] = collection.immutable.Set[A]
          * type Function[-A, +B] = Function1[A, B]
          */
        //所以我们才可以简单的定义集合:
        val map = Map[Int, String](1 -> "one", 2 -> "two", 3 -> "three")
        val set = Set[Char]('1', 99, 'b') //Int和Char相互兼容
        val vector = Vector[Int](1, 'a', '5')
        val function = (i: Int) => s"$i"
        /**
          * 用于保持在 JVM 的类型擦除机制中丢失的类型信息。有个与之类似的类型 OptManifest 。
          */

        //条件检查方法
        /**
          * 有时你希望断言某条件为真，希望它 “ 快速失败 ” （尤其在测试时）。
          * Predef 定义了许多有助于达到这个目的的方法。
          * def assert(assertion: Boolean)
          * 测试条件是否为真，如果不为真，抛出 java.lang.AssertionError 异常。
          * def assert(assertion: Boolean, message: => Any)
          * 类似前面的 assert ，但增加了一个可选参数，该参数将被转为错误信息字符串。
          * def assume(assertion: Boolean)
          * 与 assert 相同，但其表示当一段代码块（如方法）正确时，条件才为真。
          * def assume(assertion: Boolean, message: => Any)
          * 类似前面的 assume ，但增加了一个可选参数，该参数将被转为错误信息字符串。
          * def require(requirement: Boolean)
          * 与 assume 相同，但根据 Scaladoc ，其含义是调用方是否满足某些条件，也可以表示某个实现不能得出特定的结果。
          * def require(requirement: Boolean, message: => Any)
          * 类似前面的 require ，但增加了一个可选参数，该参数将被转为错误信息字符串。
          */
        val i = 10

        def isEven(i: Int): Boolean = i % 2 == 0

        //测试条件是否为真,不为真就抛出: java.lang.AssertionError
        assert(i > 0)
        assert(i > 0, "assert execute !")
        //然而,我们有时候也需要执行某一段代码之后再去判断结果,所以,assume产生了:
        //        assume {
        //            print("请输入一个Int类型的数值: ")
        //            val tempInt = scala.io.StdIn.readInt()
        //            println(s"您输入的值为: $tempInt")
        //            assume { //请注意,他们是可以嵌套的!
        //                assert(true)
        //                true
        //            }
        //            isEven(i)
        //        }
        assume(assumption = true, "assume execute !") //也可以输入错误信息字符串

        //require貌似和assume没什么区别？
        require {
            println("require execute !")
            isEven(0)
        }
        require(requirement = true, "require execute!")

        //输入输出方法
        /**
          * def print(x: Any): Unit
          * 将 x 转为字符串，然后写到标准输出，结尾不会自动添加换行。
          * def printf(format: String, xs: Any*): Unit
          * 用 format 和其他参数 xs 对 printf 风格的字符串进行格式化，然后将结果写到标准输出，结尾不会自动添加换行。
          * def println(x: Any): Unit
          * 类似 print ，但结尾会自动添加换行。
          * def println(): Unit
          * 向标准输出打印空行。
          * Scala 2.10 中的 Predef 还为 stdin 中的读取输入定义了若干方法。然而，这些方法在 Scala 2.11 中被废弃了。在 Scala 2.11 中，它们被定义在新
          * 的 scala.io.ReadStdin 对象中。但是，这些方法的签名和行为是相同的。
          * def readBoolean(): Boolean
          * 从标准输入的一个整行中读取一个 Boolean 值。
          * def readByte(): Byte
          * 从标准输入的一个整行中读取一个 Byte 值。
          * def readChar(): Char
          * 从标准输入的一个整行中读取一个 Char 值。
          * def readDouble(): Double
          * 从标准输入的一个整行中读取一个 Double 值。
          * def readFloat(): Float
          * 从标准输入的一个整行中读取一个 Float 值。
          * def readInt(): Int
          * 从标准输入的一个整行中读取一个 Int 值。
          * def readLine(text: String, args: Any*): String
          * 向标准输出中打印格式化的提示文本，并从标准输入中读取一整行字符串。
          * def readLine(): String
          * 从标准输入中读取一整行字符串。
          * def readLong(): Long
          * 从标准输入的一个整行中读取一个 Long 值。
          * def readShort(): Short
          * 从标准输入的一个整行中读取一个 Short 值。
          * def readf(format: String): List[Any]
          * 根据 format 中的区分符号，从标准输入中读取格式化输入。
          * def readf1(format: String): Any
          * 根据 format 中的区分符号，从标准输入中读取格式化输入。并根据 format 的指定，返回提取的第一个值。
          * def readf2(format: String): (Any, Any)
          * 根据 format 中的区分符号，从标准输入中读取格式化输入。并根据 format 的指定，返回提取的前两个值。
          * def readf3(format: String): (Any, Any, Any)
          * 根据 format 中的区分符号，从标准输入中读取格式化输入。并根据 format 的指定，返 回提取的前三个值。
          */
        //事实上,我们可以直接用StdIn中的读取方法
        //        println(s"input String: ${scala.io.StdIn.readLine}")
        //貌似一直运行错误: java.text.ParseException: MessageFormat parse error!
        //        println(s"input format String : ${scala.io.StdIn.readf1(" ")}")

        //杂项方法
        /**
          * 最后， Predef 中还有一些更有用的方法需要突出介绍。
          * def ???: Nothing
          * 在一个尚未实现的方法的方法体中调用。
          * 它为方法提供了具体的定义，允许编译器将方法所属的类型视为具体（与抽象对应）的类。
          * 然而，如果调用该方法，就会抛出 scala.NotImplementedError 异常。
          * （ http://www.scala-lang.org/api/current/scala/NotImplementedError.html ）
          *
          * def identity[A](x: A): A
          * 直接返回参数 x 。在将方法传给 组合器 （ combinator ）时，如果不需要进行修改，就可以用它。
          * 例如：在一个工作流程中，我们通过给 map 传入一个函数来对集合元素进行转化。
          * 有时我们不需要做任何转化，你可以传入 identity 。
          *
          * def implicitly[T](implicit e: T): T
          * 当隐式参数列表使用简写 [T:M] 时，
          * 编译器会添加形式为 (implicit arg: M[T]) 的隐式参数列表（实际名称不是 arg ，
          * 而是编译器合成的唯一名称）。
          * 调用 implicitly 可以返回参数 arg 。 5.1 节中的 “ 使用隐式 ” 部分我们曾讨论过。
          */
        val isOdd = (i: Int) => ??? //能够通过编译

        //        println(isOdd(1)) //然而,一旦调用就会发生异常!

        //使用identity对容器的元素不做任何处理
        (1 to 10).map(identity).foreach(i => print(s"$i\t"))
        //上面和下面是等价的
        (1 to 10).map(i => i).foreach(i => print(s"$i\t"))

    }
}

