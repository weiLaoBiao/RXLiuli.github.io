package chapter12

/**
  * Created by RXLiuli on 2017/5/28.
  */
//通用,可变,不可变,并发以及并行集合
object ScalaCollections {
    def default(): Unit = {
        /**
          * 如果打开 Scaladoc ，并在搜索框中输入 Map ，你会得出 5 种类型！(目前不止5种了)
          * 幸运的是，它们大多数是 trait ，且只声明或定义了你真正关心的具体 Map 类型的一部分。
          * 这些具体类型之间的大部分差异可以归结为几个设计问题。
          * 你是否需要可变性（当然，你要通过分析来确定）？你是否需要并发访问？
          * 你是否需要并行地执行操作？除了正常的键值查找以外，你还需要按固定顺序遍历的能力吗？
          */
        //先来一个简单的Map
        val map = Map(1 -> "one", 2 -> "two", 3 -> "three")
        //打印一下类型,实际上是 scala.collection.immutable.Map$Map3
        println(s"Map type: ${map.getClass}")

        //region 与集合相关的包

        /**
          * collection:
          * 定义了使用和扩展Scala集合库所需的基本特征和对象,
          * 包括子包中的所有定义,你需要的大部分抽象都在这儿定义.
          */
        //        import scala.collection

        /**
          * collection.concurrent:
          * 定义了一个Map特征和具有原子,无锁操作特性的TrieMap类.
          */
        //        import collection.concurrent

        /**
          * collection.generic:
          * 定义了用来构建特定集合(如可变集合,不可变集合)的可重用组件.
          */
        //        import collection.generic

        /**
          * collection.immutable:
          * 定义了最常用的不可变集合.
          */
        //        import collection.immutable

        /**
          * collection.mutable:
          * 定义了可变集合,大部分特定集合类型都以可变或不可变的形式存在,但并非全部.
          */
        //        import collection.mutable

        /**
          * collection.parallel:
          * 定义了用来构建特定集合(如可变集合,不可变集合等)的可重用组件,
          * 可将处理任务分发给并行的多个线程.
          */
        //        import collection.parallel

        /**
          * collection.parallel.immutable:
          * 定义了支持并行的不可变集合.
          */
        //        import collection.parallel.immutable

        /**
          * collection.parallel.mutable:
          * 定义了支持并行的可变集合.
          */
        //        import collection.parallel.mutable

        /**
          * collection.script:
          * 已经废弃的包,包含了用来观察集合操作的工具.
          */
        //        import collection.script

        //endregion

        //region scala.collection包

        /**
          * 先看一下最常见的Seq(序列)集合:
          */
        println(s"Seq type: ${scala.collection.Seq.getClass}")
        //可以引用不可变的List
        val mutableSeq1: Seq[Int] = List(1, 2, 3)
        println(mutableSeq1.getClass)
        //也可以引用可变的Array
        val mutableSeq2: Seq[Int] = Array(1, 2, 3)
        println(mutableSeq2.getClass)

        /**
          * 是的,scala中默认导入的Predef的Seq...并非是绝对不可变的.
          * 这就意味着,如果一个方法声明它返回一个序列,它可能会返回一个可变的序列实例.
          * 同样的,如果一个方法需要一个序列参数,调用者也可以传入一个可变的序列实例.
          */
        //定义一个函数foo,接受一个Seq类型的参数,返回值也是Seq类型
        def foo(seq: Seq[_]): Seq[_] = seq.map(" " + _)

        val array: Array[Int] = Array(1, 2, 3)
        //是的,我们确实可以传入一个Array数组
        println(s"foo(array): ${foo(array).mkString(",")}")
        println(s"array: ${array.mkString(",")}")

        /**
          * 如果需要使用更安全的immutable.Seq作为默认的Seq,常见的方法是:
          * 为你的项目定义一个包对象,其中定义了Seq类型,以覆盖Predef定义的Seq.
          */
        //可以去看: TestScala.src.chapter12.package.scala

        //endregion

        //region collection.concurrent包

        /**
          * 这个包中只定义了两种类型: collection.concurrent.Map 特征和
          * 实现了 trait 的 collection 的 collection.concurrent.TrieMap类.
          * Map 继承了 collection.mutable.Map,
          * 但它使用了原子操作,因此得以支持线程安全的并发访问.
          * collection.mutable.Map 的是显示一个字典树散列类 collection.concurrent.TrieMap.
          * 它实现了并发、无锁的散列数组,其目的是支持可伸缩的并发插入和删除操作,并提高内存使用效率.
          */

        //endregion

        //region collection.convert包

        /**
          * 在这个包中定义的类型是用以实现隐式转换方法的,
          * 隐式转换将Scala的集合包装为Java的集合,反之亦然.
          */

        //endregion

        //region collection.generic包

        /**
          * collection 包声明的抽象适用于所有集合,而collection.generic只为实现特定的可变、
          * 不可变、并行及并发集合提供一些组件,这里的大多数类型只对集合实现者有意义.
          */

        //endregion

        //region collection.immutable包(不可变集合)

        /**
          * BitSet:
          * 非负整数的集合,内存效率高.元素表示为可变大小的比特数组,其中比特被打包为64比特的字.
          * 最大元素个数决定了内存占用量.
          */
        val bitSet = scala.collection.immutable.BitSet(0, 1, 2)
        println(s"bitSet: $bitSet")
        //如果BitSet有负数就会产生异常:
        //IllegalArgumentException(Illegal:非法 Argument:参数,论证)
        //        val bitSet2 = scala.collection.immutable.BitSet(0, -1, 2)

        /**
          * HashMap:
          * 用字典散列实现的映射表
          */
        val imHashMap = scala.collection.immutable.HashMap(
            1 -> "one", 2 -> "two", 3 -> "three")
        println(s"imHashMap: $imHashMap")

        /**
          * HashSet:
          * 用字典实现的集合
          */
        val imHashSet = scala.collection.immutable.HashSet("1", "2", "3")
        println(s"imHashSet: $imHashSet")

        /**
          * List:
          * 用于相连列表的 trait,头节点访问复杂度为 O(1),其他元素为 O(n).
          * 其伴生对象有 apply 方法和其他 "工厂" 方法,可以用来构造 List的子类实例.
          */
        val list = scala.collection.immutable.List(1, 2, 3)
        println(s"imList: $list")

        /**
          * ListMap:
          * 用列表实现的不可变映射表
          */
        val listMap = scala.collection.immutable.ListMap(
            1 -> "one", 2 -> "two", 3 -> "three")
        println(s"imListMap: $listMap")

        /**
          * ListSet:
          * 用列表实现的不可变集合
          */
        val imListSet = scala.collection.immutable.ListSet(1, 2, 3, 1)
        println(s"imListSet: $imListSet")

        /**
          * Map:
          * 为所有不可变的映射表定义的 trait ，随机访问复杂度为 O (1) ，
          * 其伴随对象有 apply 方法和其他 “ 工厂 ” 方法，可以用来构造其子类实例
          */
        val imMap = Map(1 -> "one", 2 -> "two", 3 -> "three")
        println(s"imMap: $imMap")

        /**
          * Nil:
          * 用来表示空列表的对象
          */
        val nil = Nil
        println(s"nil: $nil")

        /**
          * NumericRange:
          * Range 类的推广版本，将适用范围推广到任意完整的类型。使用时，必须提供类型的完整实现
          */
        /**
          * 出现了编译器错误(实际上运行并不会出现什么问题): 需要显式加上类型参数...
          * Object creation impossible,
          * since member copy(start: T, end: T, step: T): NumericRange[T]
          * in scala.collection.immutable.NumericRange is not defined
          * 类型声明:
          * abstract class NumericRange[T]
          * (val start : T,val end : T,val step : T,val isInclusive : scala.Boolean)
          * (implicit num : scala.Integral[T])
          * extends scala.collection.AbstractSeq[T]
          * with scala.collection.immutable.IndexedSeq[T]
          * with scala.Serializable
          * start: 最小值,end: 最大值,step: 步长,isInclusive:是否包含最大值 end
          */
        val imNumericRange =
            new scala.collection.immutable.NumericRange[Int](1, 10, 1, false) {
                //此处确实重写,或者说实现了copy方法
                override def copy(start: Int, end: Int, step: Int):
                scala.collection.immutable.NumericRange[Int] = ???
            }
        imNumericRange.foreach(i => print(s"$i\t"))
        println()

        /**
          * Queue:
          * 不可变 FIFO (先入先出)队列
          */
        val imQueue = scala.collection.immutable.Queue(1, 2, 3, 4)
        (0 +: imQueue :+ 5).foreach(i => print(s"$i\t"))
        imQueue.foreach(i => print(s"$i\t"))
        println()

        /**
          * Seq:
          * 为不可变序列定义的 trait,其伴随对象有 apply 方法和其他 "工厂" 方法,
          * 可以用来构造其子类实例
          */
        val imSeq = scala.collection.immutable.Seq(1, 2, 3, 1)
        println(s"imSeq: $imSeq, type: ${imSeq.getClass}")

        /**
          * Set:
          * trait,为不可变集合定义了操作,其伴随对象有 apply 方法和其他 "工厂" 方法,
          * 可以用来构造其子类实例
          */
        val imSet = scala.collection.immutable.Set(1, 2, 3, 1)
        println(s"imSet: $imSet, type: ${imSet.getClass}")

        /**
          * SortedMap:
          * 为不可变映射表定义的 trait ，包含一个可按特定排列顺序遍历元素的迭代器。
          * 其伴随对象有 apply 方法和其他 “ 工厂 ” 方法，可以用来构造其子类实例
          */
        val imSortedMap = scala.collection.immutable.SortedMap(
            1 -> "one", 2 -> "two", 3 -> "three")
        //可以按照固定的顺序遍历
        imSortedMap.foreach(kv => print(s"${kv._1}, ${kv._2}\t"))
        println()

        /**
          * SortedSet:
          * 为不可变集合定义的 trait ，包含一个可按特定排列顺序遍历元素的迭代器。
          * 其伴随对象有 apply 方法和其他 “ 工厂 ” 方法，可以用来构造其子类实例
          */
        val imSortedSet = scala.collection.immutable.SortedSet(1, 2, 3, 1)
        imSortedSet.foreach(i => print(s"$i\t"))
        println()

        /**
          * Stack:
          * 不可变的 LIFO （后入先出）栈
          */
        val imStack = scala.collection.immutable.Stack(1, 2, 3)
        (0 +: imStack :+ 4).foreach(i => print(s"$i\t"))
        imStack.foreach(i => print(s"$i\t"))
        println()

        /**
          * Stream:
          * 对元素惰性求值的列表，可以支持拥有无限个潜在元素的序列
          */
        val imStream = scala.collection.immutable.Stream(1, 2, 3, 4)
        println(s"imStream: $imStream")
        imStream.foreach(println)

        /**
          * TreeMap:
          * 不可变映射表，底层用红黑树实现，操作的复杂度为 O (log(n ))
          */
        val imTreeMap = scala.collection.immutable.TreeMap(
            1 -> "one", 2 -> "two", 3 -> "three")
        imTreeMap.foreach((kv) => print(s"${kv._1}, ${kv._2}\t"))
        println(s"\nimTreeMap: $imTreeMap")

        /**
          * TreeSet:
          * 不可变集合，底层用红黑树实现，操作的复杂度为 O (log(n ))
          */
        val imTreeSet = scala.collection.immutable.TreeSet(1, 2, 3)
        imTreeSet.foreach(i => print(s"$i\t"))
        println(s"\nimTreeSet: $imTreeSet")

        /**
          * Vector:
          * 不可变、支持下标的序列的默认实现(全能优异的有序集合,操作集合的元素复杂度均为O(1))
          * 如果需要经常进行增删改查,一般可以优先考虑 Vector.
          */
        val imVector = scala.collection.immutable.Vector(1, 2, 3, 4)
        (0 +: imVector :+ 5).foreach(i => print(s"$i\t"))
        println(s"\nimVector: $imVector")


        //endregion

        //region collection.mutable(可变集合)

        /**
          * 有些时候你需要一个在单线程操作中的可变集合类型。
          * 我们已经讨论了不可变集合为何应该成为默认选项的问题。
          * 对这些集合做可变操作不是线程安全的。
          * 然而，为了提高性能等原因，有原则、谨慎地使用可变数据也是恰当的。
          */

        /**
          * AnyRefMap:
          * 为 AnyRef 类型的键准备的映射表，采用开放地址法解决冲突。大部分操作通常都比 HashMap 快
          */
        //        val muAnyRefMap = scala.collection.mutable.AnyRefMap(
        //            1 -> "one", 2 -> "two", 3 -> "three")
        //        muAnyRefMap.foreach(kv => print(s"${kv._1}, ${kv._2}\t"))

        /**
          * ArrayBuffer:
          * 内部用数组实现的缓冲区类，追加、更新与随机访问的均摊时间复杂度为 O (1) ，
          * 头部插入和删除操作的复杂度为 O (n )
          */
        val muArrayBuffer = scala.collection.mutable.ArrayBuffer(1, 2, 3, 4)
        muArrayBuffer += 5 //追加元素
        muArrayBuffer.remove(0) //删除指定下标的元素
        muArrayBuffer -= 4 //删除指定元素
        println(s"muArrayBuffer: $muArrayBuffer")

        /**
          * ArrayOps: Error
          * Java 数组的包装类，实现了序列操作
          */
        //        val muArrayOps = new scala.collection.mutable.ArrayOps[Int]() {
        //            override def update(idx: Int, elem: Int): Unit = ???
        //
        //            override def length: Int = ???
        //
        //            override def apply(idx: Int): Int = ???
        //
        //        override protected[this] def newBuilder:
        //            mutable.Builder[Int, Array[Int]]= ???
        //        }
        //        (1 +: muArrayOps :+ 2).foreach(i => print(s"$i\t"))

        /**
          * ArrayStack:
          * 数组实现的栈，比通用的栈速度快
          */
        val muArrayStack = scala.collection.mutable.ArrayStack(1, 2, 3)
        muArrayStack += 0
        println(s"muArrayStack: $muArrayStack")

        /**
          * BitSet:
          * 内存效率高的非负整数集合，见表 12-2 对 immutable.BitSet 的讨论
          */
        val muBitSet = scala.collection.mutable.BitSet(1, 2, 3)
        muBitSet + 4 + 5
        println(s"muBitSet: $muBitSet")

        /**
          * HashMap:
          * 基于散列表的可变版本的映射
          */
        val muHashMap = scala.collection.mutable.HashMap(
            1 -> "one", 2 -> "two", 3 -> "three")
        muHashMap += 4 -> "four"
        muHashMap -= 1
        println(s"muHashMap: $muHashMap")

        /**
          * HashSet:
          * 基于散列表的可变版本的集合
          */
        val muHashSet = scala.collection.mutable.HashSet(1, 2, 3, 1)
        println(s"muHashSet: $muHashSet")
        muHashSet += 1
        muHashSet -= 3
        println(s"muHashSet: $muHashSet")

        /**
          * HashTable:
          * 用于实现基于散列表的可变集合的 trait
          */
        //        val muHashTable = scala.collection.mutable.HashTable(
        //            1 -> "one", 2 -> "two", 3 -> "three")

        /**
          * ListMap:
          * 基于列表实现的映射
          */
        val muListMap = scala.collection.mutable.ListMap(
            1 -> "one", 2 -> "two", 3 -> "three"
        )
        muListMap += 4 -> "four"
        muListMap -= 1
        println(s"muListMap: $muListMap")

        /**
          * LinkedHashMap:
          * 基于散列表实现的映射，元素可以按其插入顺序进行遍历
          */
        val muLinkedHashMap = scala.collection.mutable.LinkedHashMap(
            1 -> "one", 2 -> "two", 3 -> "three"
        )
        muLinkedHashMap += (4 -> "four")
        muLinkedHashMap -= 1
        println(s"muLinkedHashMap: $muLinkedHashMap")

        /**
          * LinkedHashSet:
          * 基于散列表实现的集合，元素可以按其插入顺序进行遍历
          */
        val muLinkedHashSet = scala.collection.mutable.LinkedHashSet(
            1, 2, 3, 1
        )
        muLinkedHashSet += 2
        muLinkedHashSet -= 3
        println(s"muLinkedHashSet: $muLinkedHashSet")

        /**
          * LongMap:
          * 键的类型为 Long ，基于散列表实现的可变映射，采用开放地址法解决冲突。
          * 部分操作都比 HashMap 快
          */
        val muLongMap = scala.collection.mutable.LongMap[String]()
        muLongMap += (1, "one")
        muLongMap += (2, "two")
        muLongMap -= 1
        muLongMap += (1, "first")
        println(s"muLongMap: $muLongMap")

        /**
          * Map:
          * Map 特征的可变版，其伴随对象有 apply 方法和其他 “ 工厂 ” 方法，
          * 可以用来构造 List 的子类实例
          */
        val muMap = scala.collection.mutable.Map(
            1 -> "one", 2 -> "two", 3 -> "three"
        )
        muMap += (4 -> "four")
        muMap -= 1
        println(s"muMap: $muMap")

        /**
          * MultiMap:
          * 可变的映射，可以对同一个键赋以多个值
          */
        //被取消了...
        //        val muMultiMap = scala.collection.mutable.MultiMap

        /**
          * PriorityQueue:
          * 基于堆的，可变优先队列。对于类型为 A 的元素，必须存在隐含的 Ordering[A] 实例。
          */

        /**
          * Queue:
          * Queue 可变的 FIFO （先入先出）队列
          */
        val muQueue = scala.collection.mutable.Queue(1, 2, 3)
        println(s"muQueue: $muQueue")

        /**
          * Seq:
          * Seq 表示可变序列的 trait ，其伴随对象有 apply 方法和其他 “ 工厂 ” 方法，
          * 可以用来构造 List 的子类实例
          */
        val muSeq = scala.collection.mutable.Seq(1, 2, 3)
        println(s"muSeq: $muSeq")

        /**
          * Set:
          * Set 声明了可变集合相关操作的 trait ，其伴随对象有 apply 方法和其他 “ 工厂 ” 方法，
          * 可以用来构造 List 的子类实例
          */
        val muSet = scala.collection.mutable.Set(1, 2, 3, 1)
        muSet += 4
        muSet -= 1
        println(s"muSet: $muSet")

        /**
          * SortedSet:
          * 表示可变集合的 trait ，包含一个可按特定排列顺序遍历元素的迭代器。
          * 其伴随对象有 apply 方法和其他 “ 工厂 ” 方法，可以用来构造 List 的子类实例
          */
        //实际上构造的是具体子类的实例
        val muSortedSet = scala.collection.mutable.SortedSet(1, 2, 3)
        muSortedSet += 3
        muSortedSet -= 1
        println(s"muSortedSet: $muSortedSet")

        /**
          * Stack:
          * 可变的 LIFO （后入先出）栈
          */
        val muStack = scala.collection.mutable.Stack(1, 2, 3)
        println(s"muStack: $muStack")

        /**
          * TreeSet:
          * 可变集合，底层用红黑树实现，操作的复杂度为 O (log(n ))
          */
        val muTreeSet = scala.collection.mutable.TreeSet(1, 2, 3, 1)
        muTreeSet += 2
        muTreeSet -= 3
        println(s"muTreeSet: $muTreeSet")

        /**
          * WeakHashMap:
          * 可变的散列映射，引用元素时采用弱引用。当元素不再有强引用时，就会被删除。
          * 该类包装了 WeakHashMap
          */
        val muWeakHashMap = scala.collection.mutable.WeakHashMap(
            1 -> "one", 2 -> "two", 3 -> "three"
        )
        muWeakHashMap += (4 -> "four")
        muWeakHashMap -= 1
        println(s"muWeakHashMap: $muWeakHashMap")

        /**
          * WrappedArray: Error
          * Java 数组的包装类，支持序列的操作
          */
        //        val muWrappedArray = new scala.collection.mutable.WrappedArray[Int] {
        //            override def elemTag: ClassManifest[Int] = ???
        //
        //            override def length: Int = this.length
        //
        //            override def apply(index: Int): Int = ???
        //
        //            override def update(index: Int, elem: Int): Unit = ???
        //
        //            override def array: Array[Int] = ???
        //        }
        //        println(s"muWrappedArray: ${muWrappedArray.mkString}")


        //endregion

        //region collection.parallel(并行集合)

        /**
          * 并行集合的思想是利用现代多核系统提供的并行硬件多线程。
          * 根据定义，任何可以并行指定的集合操作都可以利用这种并行性。
          * 具体地说，集合被分成多个片段，操作（如 map ）应用在各个片段上，
          * 然后将结果组合在一起，形成最终结果。
          * 也就是说，这里用了分而治之的策略。
          */
        //使用串行和并行打印集合中的所有元素
        (1 to 10).foreach(i => print(s"$i\t"))
        (1 to 10).par.foreach(i => print(s"$i\t"))
        /**
          * 并行集合无法按照确定的顺序将所有的元素打印出来.
          */

        //使用串行和并行计算集合的总和
        println(s"串行: ${(1 to 10).sum}")
        println(s"并行: ${(1 to 10).par.sum}")
        /**
          * 貌似并没有什么区别 ?
          * 这是因为计算总和符合结合律,即: (a+b)+c==a+(b+c) 应该始终成立.
          * 当然,计算总和同时符合交换律,然而这在并行计算中并非必须的...
          * 具体地讲,操作必须满足结合律,才能在并行操作中返回稳定的,可预测的结果.
          */

        //endregion

        //region 选择集合

        /**
          * 当可能同时有可变和不可变两种选择时,优先使用不可变的集合.
          */
        /**
          * 当可能同时有可变和不可变两种选择时，
          * 我将使用 immutable.List （ mutable.LinkedList ）来表示不可变（可变）的选项。
          * 你需要有序、可遍历的序列吗？
          * 那就考虑 immutable.List （ mutable.LinkedList ）、
          * immutable.Vector 或 mutable.ArrayBuffer 。
          * List 提供了 O (1) 的前插和取头节点复杂度，但追加和读取内部其他元素的复杂度为 O (n)。
          * 由于 Vector 是可持久的数据结构（如前面所讨论的一样），它的所有操作都是 O (1) 复杂度。
          * 如果你需要随机访问， ArrayBuffer 是更好的选择。
          * 追加、更新和随机访问所需要的均摊时间复杂度均为 O (1) ，但前插和删除复杂度为 O (n) 。
          * 所以，当你需要一个序列时，如果你多半与头部元素打交道，主要使用 List ；
          * 如果你需要访问一般元素，则使用 Vector 。
          * Vector 是一个强大、通用的，具有优异全能表现的集合。
          * 不过，在有些情况下， ArrayBuffer 能提供更低的常数时间，从而降低开销，提高性能。
          * 其他的通用场景中，我们需要基于键的、 O (1) 复杂度的元素存取，
          * 也就是数值根据键被存储在 immutable.Map （ mutable.Map ）中。
          * 同样， immutable.Set （ mutable.Set ）被用来测试值是否存在。
          */

        //endregion

    }
}
