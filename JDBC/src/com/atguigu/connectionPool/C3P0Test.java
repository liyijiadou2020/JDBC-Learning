package com.atguigu.connectionPool;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;
import org.junit.Test;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author: liyijia
 * @version: 0.0
 * @create time: 2024-01-2024/1/15
 * @description: 使用数据库连接池
 * 开源的数据库连接池：
 * 1. DBCP
 * 2. C3P0
 * 3. Proxool
 * 4. BoneCP
 * 5. Druid - 阿里提供，据说是集 DBCP、C3P0、Proxool 优点于一身的数据库连接池，但是速度不确定是否有 BoneCP 快
 * DataSource 包含了连接池和连接池管理，习惯上被称为连接池。
 * DataSource 取代 DriverManager 来获取 Connection，速度快，而且提高了访问数据库的速度。
 */
public class C3P0Test {

    @Test
    //方式一，不提倡：
    public void testGetConnection1() throws Exception{
        //获取c3p0数据库连接池
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass( "com.mysql.jdbc.Driver" );
        cpds.setJdbcUrl( "jdbc:mysql://localhost:3306/test" );
        cpds.setUser("root");
        cpds.setPassword("root");
        //通过设置相关的参数，对数据库连接池进行管理：
        //设置初始时数据库连接池中的连接数
        cpds.setInitialPoolSize(10);

        Connection conn = cpds.getConnection();
        System.out.println(conn);

        //销毁c3p0数据库连接池 一般不会做
		// DataSources.destroy( cpds );
    }

    //方式二：使用配置文件
    @Test
    public void testGetConnection2() throws SQLException {
        ComboPooledDataSource cpds = new ComboPooledDataSource("hellc3p0");
        Connection conn = cpds.getConnection();
        System.out.println(conn);
    }


}
