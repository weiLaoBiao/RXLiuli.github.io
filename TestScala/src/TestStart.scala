/**
  * Created by RXLiuli on 2017/3/23.
  * 开始类，用于调用其它类
  */
object TestStart {

  def main(args: Array[String]): Unit = {

    //region myTrouble(问题)
    //        Trouble.default()
    //endregion

    //region Java(Java方面的测试)
    //    TestJavaClass.TestJava.myDefault()
    TestJavaClass.TestScala.default()
    //endregion

    //region myScala(Scala额外的收获)

    //region MyXML(Scala中的XML)
    //        myScala.MyXML.default()
    //endregion

    //region SingleInstance(Scala中的单例模式)
    //        myScala.SingleInstance.default()
    //endregion

    //region IOStreamPreliminary(IO流初步)
    //        myScala.IOStreamPreliminary.default()
    //endregion

    //region SqlTest(Sql连接测试)
    //        myScala.SqlTest.default()
    //endregion

    //region ComputeStudent(计算一个班中至少有多少个人才能有一半的概率两个人同生日)
    //        myScala.ComputeStudent.default()
    //endregion

    //endregion

    //region chapter01(第一章: 零到十六,Scala简介)

    //region mainArgs(主函数参数)
    /**
      * 在Run选项下面找到 Edit Configurations,在其中配置Program arguments选项
      * 就可以在idea中向main()传入参数,参数之间用空格分开
      */
    //        args.foreach(println)
    //endregion

    //region Test(测试类)
    //        chapter01.Test.default()
    //endregion

    //region PartialFunction(偏函数)
    //        chapter01.TestPartialFunction.default()
    //endregion

    //region MyList(列表List)
    //        chapter01.MyList.default()
    //endregion

    //region CaseClass(类的前面加上case)
    //        chapter01.TestCaseClass.default()
    //endregion

    //region SeqObject(Seq类型,代表序列)
    //        chapter01.SeqObject.default()
    //endregion

    //region TestMatch(Scala强大的模式匹配)
    //        chapter01.TestMatch.default()
    //endregion

    //region TestEquals(深层次的比较两个引用)
    //        chapter01.TestEquals.default()
    //endregion

    //region sString(插入字符串)
    //        chapter01.InsertString.default()
    //endregion

    //endregion

    //region chapter02(第二章: 更简洁,更强大)

    //region TestRange(数字序列)
    //        chapter02.TestRange.default()
    //        println(chapter02.TestRange.JieCheng(1000))
    //        println(chapter02.TestRange.JieCheng2(10))
    //endregion

    //region TestPoint(方法默认值和命名参数列表)
    //        chapter02.TestPoint.default()
    //endregion

    //region FunctionParameter(方法具有多个参数列表)
    //        chapter02.FunctionParameter.default()
    //        chapter02.TestFuture.default()
    //endregion

    //region Resursion(尾递归)
    //        printf("递归: %s", chapter02.Resursion.JieCheng2(5).toString)
    //endregion

    //region Literals(直接量)
    //        chapter02.Literals.default()
    //endregion

    //region TestOption(Option,Some和None:避免使用null)
    //        chapter02.TestOption.default()
    //endregion

    //region TestSealed(封闭类的继承,抽象方法)

    //        val myATestSealed1 = new chapter02.ATestSealed()
    //        myATestSealed1.default("月姬") //执行重写的default方法
    //        myATestSealed1.default(5) //执行重载的default方法
    //        myATestSealed1.default() //执行父类的default方法
    //        val myBTestSealed2 = new chapter02.BTestSealed()
    //        myBTestSealed2.default("亚当") //执行重写的default方法
    //        myBTestSealed2.default() //执行父类的default方法

    //endregion

    //region Parameterization(参数化类型,泛型)
    //        chapter02.Parameterization.default()
    //endregion

    //endregion

    //region chapter03(第三章: 要点详解)

    //region TestOperator(操作符简介)
    //        chapter03.TestOperator.default()
    //endregion

    //region ImplicitConversion(隐式转换)
    //        chapter03.ImplicitConversion.default()
    //endregion

    //region OperatorPriority(操作符优先级)
    //        chapter03.OperatorPriority.default()
    //endregion

    //region IFElse(if else表达式)
    //        chapter03.IfElse.default()
    //endregion

    //region While(循环)
    //        chapter03.TestWhile.default()
    //endregion

    //region TestForExpression(for推导式)
    //        chapter03.TestForExpression.default()
    //endregion

    //region ConditionalOperator(条件操作符)
    //        chapter03.ConditionalOperator.default()
    //endregion

    //region TryCatch(try,catch和final子句)
    //        chapter03.TryCatch.default()
    //endregion

    //region ByNameParameter(按名调用?!）
    //        chapter03.ByNameParameter.default()
    //endregion

    //region TestLazy(惰性赋值)
    //        chapter03.TestLazy.default()
    //endregion

    //region TestEnum(枚举)
    //        chapter03.TestEnum.default()
    //endregion

    //region InterpolatedString(可插入字符串)
    //        chapter03.InterpolatedString.default()
    //endregion

    //region TraitInitial(Scala语言的接口和混入)
    //        chapter03.TraitInitial.default()
    //endregion

    //endregion

    //region chapter04(第四章: 模式匹配)

    //region SimpleMatching(简单匹配)
    //        chapter04.SimpleMatch.default()
    //endregion

    //region ValueMatch(match中的值,变量和类型)
    //        chapter04.ValueMatch.default()
    //endregion

    //region SeqMatch(序列匹配)
    //        chapter04.SeqMatch.default()
    //endregion

    //region TupleMatch(元祖的匹配)
    //        chapter04.TupleMatch.default()
    //endregion

    //region CaseGuard(case中的guard语句)
    //        chapter04.CaseGuard.default()
    //endregion

    //region case类的匹配
    //        chapter04.CaseClassMatch.default()
    //endregion

    //region HeadTail(神秘的head +: tail表达式)
    //        chapter04.HeadTail.default()
    //endregion

    //region UnapplySeq(unapplySeq方法,Seq伴生对象特别实现的方法)[Error]
    //        chapter04.UnapplySeq.default()
    //endregion

    //region VarParameterMatch(可变参数列表的匹配)
    //        chapter04.VarParameterMatch.default()
    //endregion

    //region RegexMatch(正则表达式的匹配)
    //        chapter04.RegexMatch.default()
    //endregion

    //region VariableMatch(变量绑定)
    //        chapter04.VariableBinding.default()
    //endregion

    //region AgainTypeMatch(再谈类型匹配)
    //        chapter04.AgainTypeMatch.default()
    //endregion

    //region SimpleIO(简单的IO流初步)
    //        chapter04.SimpleIO.default()
    //endregion

    //region SealedExtends(封闭继承层级与全覆盖匹配)
    //        chapter04.SealedExtends.default()
    //endregion

    //region MatchingRests(模式匹配的其他用法)
    //        chapter04.MatchingRests.default()
    //endregion

    //endregion

    //region chapter06(第六章: Scala函数式编程)

    //region FunctionProgramming(函数式编程简介)
    //        chapter06.FunctionalProgramming.default()
    //endregion

    //region ScalaFunction(Scala中的函数式编程)
    //        chapter06.ScalaFunction.default()
    //endregion

    //region Recursion(递归)
    //        chapter06.Recursion.default()
    //endregion

    //region TestPartialFunction(偏应用函数与偏函数)
    //        chapter06.TestPartialFunction.default()
    //endregion

    //region TestCurry(Curry化与函数的其他转换)
    //        chapter06.TestCurry.default()
    //endregion

    //region DataStructure(数据结构)
    //        chapter06.DataStructure.default()
    //endregion

    //region Traverse(遍历)
    //        chapter06.Traverse.default()
    //endregion

    //region Mapping(映射)
    //        chapter06.Mapping.default()
    //endregion

    //region FlatMapping(扁平映射)
    //        chapter06.FlatMapping.default()
    //endregion

    //region TestFilter(过滤)
    //        chapter06.TestFilter.default()
    //endregion

    //region FoldAndReduction(折叠与归约)
    //        chapter06.FoldAndReduction.default()
    //endregion

    //region TailrecAndInfinite(尾递归与遍历无限集合)
    //        chapter06.TailrecAndInfinite.default()
    //endregion

    //region Combiner(组合器: 软件最佳组件抽象)
    //        chapter06.Combiner.default()
    //endregion

    //endregion

    //region chapter07(第七章: 深入学习for推导式)

    //region ForElement(for推导式组成元素)
    //        chapter07.ForElement.default()
    //endregion

    //region InternalMechanism(for推导式: 内部机制)
    //        chapter07.InternalMechanism.default()
    //endregion

    //region ForChangeRule(for推导式的转化规则 Error)
    //        chapter07.ForChangeRule.default()
    //endregion

    //region OptionContainer(Option容器 Error)
    //        chapter07.OptionContainer.default()
    //endregion

    //region TestEither(Either: Option类型的逻辑扩展)
    //        chapter07.TestEither.default()
    //endregion

    //region TryType(Try类型)
    //        chapter07.TryType.default()
    //endregion

    //region TestValidation(Scalaz提供的Validation类)
    //        chapter07.TestValidation.default()
    //endregion

    //endregion

    //region chapter08(第八章: Scala面向对象编程)

    //region ObjectPreliminary(类和对象初步)
    //        chapter08.ObjectPreliminary.default()
    //endregion

    //region ReferenceAndValue(引用与值类型)
    //        chapter08.ReferenceAndValue.default()
    //endregion

    //region ValueClass(价值类 Error)
    //        chapter08.ValueClass.default()
    //endregion

    //region SuperClass(父类 Error)
    //        chapter08.SuperClass.default()
    //endregion

    //region Constructor(Scala的构造器)
    //        chapter08.Constructor.default()
    //endregion

    //region ClassField(类的字段)
    //        chapter08.ClassField.default()
    //endregion

    //region Require(验证输入)
    //        chapter08.Require.default()
    //endregion

    //region SuperConstructor(调用父类的构造器: 与良好的面向对象设计)
    //        chapter08.SuperConstructor.default()
    //endregion

    //region NestClass(嵌套类型 Error)
    //        chapter08.NestClass.default()
    //endregion

    //region RealizedCase(实现case类)
    //        chapter08.RealizedCase.default()
    //endregion

    //region InnerClass(内部类)
    //        chapter08.InnerClass.default()
    //endregion

    //endregion

    //region chapter09(第九章: 特征)

    //region TraitPreliminary(特征初步)
    //        chapter09.TraitPreliminary.default()
    //endregion

    //region MixinTrait(混入trait)
    //        chapter09.MixinTrait.default()
    //endregion

    //region ConstructorTrait(构造trait Error)
    //        chapter09.ConstructorTrait.default()
    //endregion

    //endregion

    //region chapter10(第十章: Scala对象系统I)

    //region VarianceUnderInheritance(继承转化)
    //        chapter10.VarianceUnderInheritance.default()
    //endregion

    //region MutableTypeVariance(可变类型的变异)
    //    chapter10.MutableTypeVariance.default()
    //endregion

    //region TypeStruction(Scala的类型层次结构)
    //        chapter10.TypeStruction.default()
    //endregion

    //region NothingAndNull(闲话Nothing/Null Error)
    //        chapter10.NothingAndNull.default()
    //endregion

    //region ProductCaseTuple(Product,case类和元组)
    //        chapter10.ProductCaseTuple.default()
    //endregion

    //region PredefObject(Predef对象)
    //        chapter10.PredefObject.default()
    //endregion

    //region ObjectEquals(判断对象的相等 Error)
    //        chapter10.ObjectEquals.default()
    //endregion

    //endregion

    //region chapter11(第十一章: Scala对象系统II)

    //region CoverMember(覆盖类成员和trait成员)
    //        chapter11.CoverMember.default()
    //endregion

    //region CoverFinal(尝试覆写final声明)
    //        chapter11.CoverFinalStatement.default()
    //endregion

    //region CoverField(覆写抽象和具体字段)
    //        chapter11.CoverField.default()
    //endregion

    //region UniformAccessPrinciple(统一访问原则)
    //        chapter11.UniformAccessPrinciple.default()
    //endregion

    //region LinearAlgorithm(对象层次结构的线性化算法)
    //        chapter11.LinearAlgorithm.default()
    //endregion

    //endregion

    //region chapter12(第十二章: Scala集合库)

    //region ScalaCollections(通用,可变,不可变,并发以及并行集合)
    //    chapter12.ScalaCollections.default()
    //endregion

    //endregion

    //region chapter13(第十三章: 可见性规则)

    //region DefaultVisibility(默认访问修饰符)
    //    chapter13.DefaultVisibility.default()
    //endregion

    //region VisibilityKeyword(可见性关键字)
    //    chapter13.VisibilityKeyword.default()
    //endregion

    //region VisibilityIdea(对可见性的想法 idea:想法)
    //    chapter13.VisibilityIdea.default()
    //endregion

    //endregion

    //region chapter13(第十四章: Scala类型系统)

    //region TypeSystemInitial(类型系统初步)
    //    chapter14.TypeSystemInitial.default()
    //endregion

    //region ParameterizationTypes(参数化类型)
    //    chapter14.ParameterizationTypes.default()
    //endregion

    //endregion

    //region Scala GUI编程

    //region GuiPreliminary(GUI 初步)
    //    ScalaGUI.GuiPreliminary
    //endregion

    //endregion

  }
}

