package chapter01

/**
  * Created by RXLiuli on 2017/4/1.
  */
object MyList {
    def default(): Unit = {
        //List列表的map方法
        val myList = List(1, 2, 3, 4, 5)
        //将List中所有的元素*2并返回一个新的List
        val newList = myList.map(i => i * 2)
        //打印一下,并没有改变元列表的值
        myList.foreach(printf("%d ", _))
        //打印新的列表,是已经改变后的列表
        newList.foreach(printf("%d ", _))

    }
}
