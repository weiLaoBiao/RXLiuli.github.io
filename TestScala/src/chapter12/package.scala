/**
  * Created by RXLiuli on 2017/5/28.
  */
package object chapter12 {
    /**
      * 定义了一个类型别名Seq[T],具体的值为: scala.collection.immutable.Seq[T]
      * 因为Predef中也存在Seq[T]的定义,所以Predef中的Seq[T]被覆盖了.
      * 所有的在这个包中的Seq[T]都被视为scala.collection.immutable.Seq[T],
      * 这意味着你不能使用Seq[T]去声明序列而去引用可变集合(如Array)
      */
    //    type Seq[T] = scala.collection.immutable.Seq[T]

}
