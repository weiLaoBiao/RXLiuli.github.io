package chapter14

/**
  * Created by rxliuli on 17-6-12.
  */
// 参数化类型
object ParameterizationTypes {
  def default(): Unit = {
    // 变异标记
    class CSup
    class C extends CSup
    class CSub extends C



  }
}
