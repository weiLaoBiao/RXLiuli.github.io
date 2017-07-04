import scala.collection.mutable.ArrayBuffer

println("hello scala")

class Person(val name: String, var age: Int) {
    override def toString: String = s"Person($name, $age)"
}

object Person {

}

val p = new Person("liuli", 15)
println(s"${p.name} is ${p.age} years!")
val any = if (p.name.length > 2) p.name
else "default"

//可变数组
val ab = new scala.collection.mutable.ArrayBuffer[String]()
ab += "liuli"
ab += "月姬"
ab.foreach(s => print(s"$s\t"))
//获取元素的长度
val ab2: scala.collection.mutable.ArrayBuffer[Int] =
    ab.map(_.length)
//添加元素
ab2 += (1, 2, 3, 4)
//ab2 += List(1, 2, 3) //编译错误
//可以添加其他容器类型
ab2 ++= List(1, 2, 3, 4)
//虽然ArrayBuffer是可变数组,
//然而每次改变时仍然会产生一个新的数组,
//然后将引用赋值给原数组,类似于String
ab2
"ab2的长度" + ab2.size
ab2.trimEnd(3) //去除后三个元素
s"去除了前三个元素: $ab2"
ab2.trimStart(3) //去除前三个元素
s"去除了前三个元素: $ab2"
//直接对原数组进行了改变！！！
ab2.insert(1, 100)
s"在下标为1的地方插入元素100: $ab2"
ab2.insert(1, 101, 102, 103)
s"从下标1开始插入任意个数的元素: $ab2"

ab2.remove(1)
s"移除下表为1的元素: $ab2"
ab2.remove(1, 2)
s"从下标1开始,移除两个元素: $ab2"
val array = ab2.toArray
s"转化成Array数组: $array"
println(s"数组的类型是: ${array.getClass} ")
//array += 1 //数组不能添加新的元素
for {
    i <- ab2
    if i % 2 == 0 //卫语句
    k = i * 2 //赋值变量
} println(k + i)
//等价的容器方法调用链
ab2.withFilter(_ % 2 == 0).
foreach(i => println(i * 2 + i))
ab2.sum //总和(实际上是相加,数值计算总和,非数值使用toString后连接)
ArrayBuffer("1ad", "2b", "ac").max //排序而已,并不是长度
ab2.sorted //默认升序排列
//ab2.sortBy(_ > 100) //排序后好奇怪
s"使用工具方法排序前: ${array.mkString(",")}"
scala.util.Sorting.quickSort(array)
s"使用工具方法排序后: ${array.mkString(",")}"

val matrix = Array.ofDim[Int](3, 4)
for {
    i <- matrix.indices
    k <- matrix(i).indices
} matrix(i)(k) = i + k

matrix.foreach(x => println(x.mkString(", ")))



//九九乘法表
val multiplication = Array.ofDim[String](9, 9)
for {
    i <- multiplication.indices
    k <- multiplication(i).indices
} multiplication(i)(k) = s"$k * $i = ${k * i}"

multiplication.foreach(x => println(x.mkString("\t")))

multiplication.foreach(x => println(x.mkString("\t")))

//改进版
val mul = new Array[Array[String]](9)
//生成符合长度的矩阵
for {
    i <- mul.indices
} mul(i) = new Array[String](i + 1)

for {
    i <- mul.indices
    k <- mul(i).indices
} mul(i)(k) = s"${k + 1}*${i + 1}=${(k + 1) * (i + 1)}"

//打印九九乘法表
mul.foreach(x => println(x.mkString("\t")))

val map = scala.collection.mutable.Map(1 -> "one", 2 -> "tow", 3 -> "three")
println(map)
map(1) = "1"
//println(map.mkString)
map.remove(1)
map += 4 -> "from"
println(map)
//不存在的键值对
//map(10) //会运行错误

map.getOrElse(10, "")
map -= 1
map.remove(4)
scala.collection.immutable.
SortedMap


