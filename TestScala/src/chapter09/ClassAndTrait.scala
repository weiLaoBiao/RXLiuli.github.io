package chapter09

/**
  * Created by RXLiuli on 2017/5/16.
  */
object ClassAndTrait {
    def default(): Unit = {
        /**
          * 对于某些 “ 概念 ” 而言，应该使用 trait 来表示，还是使用类来表示呢？
          * 考虑这一问题时，请牢记一点：
          * trait 是 Scala 实现混入的方法，它适用于大多数的 “ 辅助 ” 行为。
          * 假如你发现某一特定的 trait 在大多数时候都被用作其他类的父类，
          * 那么这些子类表现得就像（ behave as ）这个父特征一样。
          * 为了使逻辑关系更加清晰，此时你应该考虑是否要把这个 trait 修改为类。
          * （在这里，我们并未使用 is a 来表示子类与父特征的关系，而是用了 behaveas 一词。
          * 这是因为根据里氏替换原则， is a 更适用于表示类的继承关系定义。）
          */
        /**
          * 那么,到底是用 trait 还是 class 呢?
          * trait一般适用于辅助行为,比如 Mixin 一些字段和方法.
          * 而类的继承更适合于构建复杂的系统,比如类中可以定义构造函数.
          * 注: 无论如何,尽量使用 trait 替代 abstract class !
          * 注: 复合/组合/聚合 优于 继承 !
          */
    }
}
