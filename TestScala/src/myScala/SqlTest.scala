package myScala

import java.sql.{Connection, DriverManager, ResultSet, Statement}

/**
  * Created by RXLiuli on 2017/5/31.
  */
//Sql 连接测试
object SqlTest {


    def default(): Unit = {
        loadConn()
        query()
    }

    //数据库连接对象
    private[this] var conn: Connection = _

    //启动数据库连接
    def loadConn(): Unit = {
        // Change to Your Database Config(连接字符串)
        val connStr = "jdbc:mysql://localhost:3306/myschool?user=RXLiuli&password=123456"

        // Load the driver(加载连接司机)
        classOf[com.mysql.jdbc.Driver]

        // Setup the connection(设置连接对象)
        conn = DriverManager.getConnection(connStr)

        // Configure to be Read Only(配置为只读)
        val statement = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)

        //Close The Connection(关闭这个连接对象)
        conn.close
    }

    //查询
    def query(): Unit = {
        val connStr = "jdbc:mysql://localhost:3306/myschool?user=RXLiuli&password=123456"
        classOf[com.mysql.jdbc.Driver]
        conn = DriverManager.getConnection(connStr)
        val statement = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)

        val sql = "select * from stuinfo"
        val rs = statement.executeQuery(sql)
        while (rs.next) {
            println(s"${rs.getString("StuNo")}\t${rs.getString("StuName")}\t${rs.getString("StuAge")}")
        }
        conn.close()
    }

}
