package chapter07

import com.sun.xml.internal.ws.developer.MemberSubmissionAddressing.Validation

import scala.util.{Failure, Success}


/**
  * Created by RXLiuli on 2017/5/7.
  */
//Scalaz提供的Validation类
object TestValidation {
    def default(): Unit = {
        /**
          * 存在这样一种场景：我们之前讨论的所有类型都无法很好地满足我们的需求。
          * 假如 for 推导式中出现了空值（由 Option 类型提供）或错误，
          * 那么 组合器 （ combinator ）便不会调用后续的表达式。
          * 事实上，我们会在发生第一个错误时便停止执行后续代码。
          * 不过，假如我们正在执行一些相互独立的操作，并希望执行这些操作时收集所有发生的错误，
          * 待操作执行完成后再决定如何处理错误，那该怎么办呢？
          * 对用户输入进行验证便是一个典型的应用场景，这些用户输入可能源自网页表单。
          * 这样，你可以一次将所有的错误信息返回给用户。
          */
        //Scala标准库并没有Scalaz,需要导入包,暂且跳过
        /*def positive(i: Int): Validation[List[String], Int] = {
            if (i > 0) Success(i)
            else Failure(List(s"Nonpositive integer $i"))
        }*/


        /**
          * 本章总结:
          * Either,Try和Validation定义了程序的实际行为.
          * 它们都期望能返回正确值,假如未返回正确值,这两种类型将会对你应该了解的错误信息进行封装.
          * 与之类似,Option的类型签名很明确地表明了该类型对某一值能否出现的场景进行了封装.
          * 使用这些类型能够减少对异常的使用,同时还能解决一个重要的并发问题.
          * 由于我们无法保证异步执行的代码回运行在称为"caller"(调用者)在同一个线程内,
          * 因此调用者无法捕获其他代码所抛出的异常.
          * 不过,如果能够想反悔正常值那样返回异常,caller便能得到异常.
          */
    }
}
