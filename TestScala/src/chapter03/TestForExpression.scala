package chapter03

/**
  * Created by RXLiuli on 2017/4/13.
  */
object TestForExpression {
    def default(): Unit = {
        //正常使用for循环(只存在副作用的for推导式)
        val list = List[Int](1, 2, 3, 4, 5, 6, 7)
        for (i <- list) {
            print(i)
        }

        //breed <- DogBreeds 被称为generator expression(生成器表达式)
        println("\ngenerator expression(生成器表达式): ")
        for (i <- 0 until 10 by 2 /*此处代表从0到10(不包含),步长为2的Range集合*/ ) {
            print(i)
        }

        //guard(保护式),如果你想,随便添加多少个保护式都可以
        println("\nguard(保护式): ")
        for (i <- 0 until 10
             if i % 2 == 0 /*筛选出是偶数的元素*/
             if i < 5 /*添加第二个筛选条件筛选<5的元素*/ ) {
            print(i)
        }

        //Yielding关键字(使用此关键字将返回一个集合),这才是正规的for推导式
        println("\nYielding(for推导式): ")
        val list2 = for (i <- list) yield i * 2 //注: 此处不得使用花括号！！
        list2 foreach print

        //扩展作用域和值定义
        println("\n扩展作用域和值定义: ")
        val list3 = for {i <- list
                         k = i * 2 /*此处定义了值k,并且在下面使用了k*/}
            yield k
        list3 foreach print //是的,和上面的那个完全一样,是否要用扩展作用域完全取决于该值是否需要多次使用

        //Option(避免使用null值)(暂时还看不出来什么切实的好处)
        println("\n尝试使用Option,避免使用null值: ")
        val dogBreeds = List[Option[String]](Some("Doberman"), None, Some("Yorkshire Terrier"),
            Some("Dachshund"), None, Some("Scottish Terrier"),
            None, Some("Great Dane"), Some("Portuguese Water Dog"))
        for {
            breedOption <- dogBreeds
            breed <- breedOption
            upcasedBreed = breed.toUpperCase()
        } print(upcasedBreed)

        println("\n直接使用Some匹配: ")
        for {
            Some(breed) <- dogBreeds
            upcasedBreed = breed.toUpperCase()
        } print(upcasedBreed)

        //scala.util.control.Breaks(类似于Java中的break)
        println("\nscala.util.control.Breaks: ")
        for (i <- 1 to 10) {
            if (i > 5) scala.util.control.Breaks.break() //这儿会运行出错?
            else print(i)
        }
        println("执行到这儿了么?")

        //注: 以上所有的方法可同时使用
    }
}
