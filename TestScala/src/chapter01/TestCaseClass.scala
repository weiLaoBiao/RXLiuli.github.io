package chapter01

/**
  * Created by RXLiuli on 2017/4/2.
  * CaseClass类测试类
  */
object TestCaseClass {
    def default(): Unit = {
        //建立对象
        val myCaseClass = new CaseClass(i = 1, str = "月姬")
        printf("%d\t%s\n", myCaseClass.i, myCaseClass.str)
        //尝试改变myCaseClass对象内部的值,编译出错!
        //        myCaseClass.i = 10 //Reassignment to val(为val字段重新赋值)
        //结论,case标识的类的属性全部都是val类型的字段

        //使用case关键字后会生成一个伴生对象,使用伴生对象更容易实例化一个类
        //实际上这儿隐含调用了apply(),该方法接受和主构造函数相同的参数列表
        //并返回一个对象
        val myCaseClass2 = CaseClass(i = 1, str = "月姬")
        printf("%d\t%s\n", myCaseClass2.i, myCaseClass2.str)
    }
}
