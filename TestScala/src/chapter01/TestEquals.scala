package chapter01

/**
  * Created by RXLiuli on 2017/4/2.
  */
object TestEquals {
    def default(): Unit = {
        //编写对象
        val testClass1 = new TestClass(Array[String]("月姬", "亚当"))
        val testObject1 = new TestObject(1, "月姬", testClass1)
        val testClass2 = new TestClass(Array[String]("月姬", "亚当"))
        val testObject2 = new TestObject(1, "月姬", testClass1)
        printf("testClass1和testClass2的值比较：%b\n", testClass1 == testClass2)
        printf("testClass1和testClass2里面数组的值比较：%b\n", testClass1.array == testClass2.array)
        printf("testObject1和testObject2的值比较：%b\n", testObject1 == testObject2)
        val array1 = Array[String]("月姬", "亚当")
        val array2 = Array[String]("月姬", "亚当")
        println(array1 == array2)
        //无法直接用==比较引用类型的值是否相等

        val str1 = "月姬"
        val str2 = "月姬"
        println(str1 == str2)
    }
}
