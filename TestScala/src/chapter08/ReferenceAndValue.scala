package chapter08

/**
  * Created by RXLiuli on 2017/5/8.
  */
//引用与值类型
object ReferenceAndValue {
    def default(): Unit = {
        //Java中的值传递
        val i = 0
        println(s"i调用函数前的值: $i")
        val add = (i: Int) => i + 1
        add(i)
        println(s"i调用函数后的值: $i")
        //Java中的引用传递
        val array = Array[Int](1, 2, 3, 4, 5)
        println(s"array调用函数前的值: ${array.mkString}")
        val arrayToString = (array: Array[Int]) => array.map(_ + "\t")
        arrayToString(array)
        println(s"array调用函数后的值: ${array.mkString}")
        //事实证明,Scala中并没有引用传递
        //不过,这也保证了函数的引用透明性,即只有一个入口和出口
        /**
          * 所有引用类型都是 AnyRef 的子类型。 AnyRef 是 Any 的子类型，
          * 而 Any 是 Scala 类型层次的根类型。
          * 所有值类型均为 AnyVal 的子类型， AnyVal 也是 Any 的子类型。
          * Any 仅有这两个直接的子类型。需要注意，
          * Java 的根类型 Object 实际上更接近 Scala 的 AnyRef ，而不是 Any 。
          */

        val foo: (Int) => Int = (i) => {
            //            i += 1 //参数是val
            var k = i //可以用一个变量接收
            k += 1 //这样就可以使用变量了(不建议使用变量)
            k
        }
        //另外,Unit也是字面量
        val u = Unit //此处的Unit是类型而非值
        println(s"u: $u,type: ${u.getClass}")
        //空元组字面量
        val t = () //这次是Unit的值
        println(s"u: $t,type: ${t.getClass}")


    }
}
