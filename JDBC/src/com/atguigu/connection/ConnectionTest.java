package com.atguigu.connection;

import org.junit.Test;

import javax.annotation.processing.SupportedAnnotationTypes;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author: liyijia
 * @version: 0.0
 * @create time: 2024-01-2024/1/13
 * @description: JDBC 测试连接数据库
 */

public class ConnectionTest {

    @Test
    public void  testConnection1() {
        Driver driver = null;
        try {
            // driver = new com.mysql.jdbc.Driver(); // This is deprecated. The new driver class is `com.mysql.cj.jdbc.Driver'
            driver = new com.mysql.cj.jdbc.Driver(); // [缺点] 这里显式出现了第三方数据库的API，阻碍了程序的可移植性
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String url = "jdbs:mysql://localhost:3306/test";

        Properties info = new Properties();
        info.setProperty("user", "root");
        info.setProperty("password", "root");

        Connection conn = null;
        try {
            conn = driver.connect(url, info);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(conn);

    }

    /**
     * 最终版
     * 使用的是 com.mysql.cj.jdbc.Connection 而不是 com.mysql.jdbc.Driver
     */
    @Test
    public void testConnection(){
        //1.加载配置文件
        InputStream is = ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties pros = new Properties();
        try {
            pros.load(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //2.读取配置信息
        String user = pros.getProperty("user");
        String password = pros.getProperty("password");
        String url = pros.getProperty("url");
        String driverClass = pros.getProperty("driverClass");

        //3. 利用反射加载驱动
        try {
            Class.forName(driverClass);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        //4.获取连接
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url,user,password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(conn);
    }




}
