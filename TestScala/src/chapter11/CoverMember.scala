package chapter11

/**
  * Created by RXLiuli on 2017/5/22.
  */
//覆盖类成员和trait成员
object CoverMember {
    def default(): Unit = {
        /**
          * 我们可以在类中及 trait 中声明抽象成员，包括抽象字段、抽象方法和抽象类型。
          * 在创建实例前，继承类或 trait 必须定义这些抽象成员。
          * 大多数面向对象语言都支持抽象方法，其中某些语言还支持抽象字段和抽象类型。
          */
        val tom = Employee("Tom Jones", 100000.0, Address("MyTown", "XX", "12345"))
        val jane = Employee("Jane Doe", 110000.0, Address("BigCity", "YY", "67890"))
        println(s"tom salary: ${Payroll2017.netPay(tom)}")
        println(s"jane salary: ${Payroll2017.netPay(jane)}")
        /**
          * 这些天，当在代码中看到 override 关键字时，
          * 我便仿佛看到一个潜在的 设计坏味 （ design smell ）。
          * 某人也许正在对某一具体行为进行覆写，这可能会导致细微的 bug 。
          */
        /**
          * 注:
          * 除非具体方法是 toString 这样的无用方法，否则请不要覆写具体方法。
          * 除非你确实是在覆写具体方法，否则不要使用 override 关键字。
          */
    }

    //下面我们给出这样一个示例,说明供美国公司使用的工资计算器的粗略实现:
    //Address类(state:州 zip:邮政编码)
    case class Address(city: String, state: String, zip: String)

    //定义一个测试用的类(Employee:员工 salary:薪水)
    case class Employee(name: String, salary: Double, address: Address)

    //Payroll类(Payroll:工资单)
    abstract class Payroll {
        //计算实际工资方法(net:净赚 pay:工资)
        def netPay(employee: Employee): Double = {
            //调用了抽象方法
            val fedTaxes = calaFedTaxes(employee.salary)
            val stateTaxes = calaStateTaxes(employee.salary, employee.address)
            employee.salary - fedTaxes - stateTaxes
        }

        //计算美国联邦税抽象方法(fed:饲养 taxes:税收)
        def calaFedTaxes(salary: Double): Double

        //计算州税抽象方法
        def calaStateTaxes(salary: Double, address: Address): Double
    }

    //实际计算工资的类
    object Payroll2017 extends Payroll {
        val stateRate = Map("XX" -> 0.05, "YY" -> 0.03, "ZZ" -> 0.0)

        //实现了联邦税的计算方法
        def calaFedTaxes(salary: Double): Double = salary * 0.25

        //实现了计算州税的方法
        def calaStateTaxes(salary: Double, address: Address): Double =
        // Assume the address.state is valid; it's found in the map!
            salary * stateRate(address.state)
    }


}
