package com.atguigu.connection;

import com.atguigu.bean.Country;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.Properties;

/**
 * @author: liyijia
 * @version: 0.0
 * @create time: 2024-01-2024/1/15
 * @description: Statement 类使用测试
 */
public class StatementTest {
    /**
     * ---------
     * Driver - 数据库驱动
     * Connection - 数据库连接
     * Statement - 数据库操作指令 【缺点：存在拼串操作；存在 SQL 注入的问题】
     *      - 增删改
     *      - 查询
     * PreparedStatementTest - 预编译的 Statement 【防止了 SQL 注入】
     *      - 增删改
     *      - 查询
     */
    @Test
    public void testCountry() {

        String country_id = "AU";

        String sql = "SELECT country_id, country_name, region_id FROM countries WHERE country_id = '"
                + country_id
                + "'";
        // User user = get(sql, User.class);
        Country country = get(sql, Country.class);

        if (country != null) {
            System.out.println("连接成功！");
            System.out.println(country);
        } else {
            System.out.println("用户名或密码错误！");
        }

    }


    public <T> T get(String sql, Class<T> clazz) {
        /**
         * 使用 Statement 实现对数据包的查询操作
         */
        T t = null;

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        // 1. 加载配置文件
        InputStream inputStream = StatementTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 2. 读取配置文件
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String url = properties.getProperty("url");
        String driverClass = properties.getProperty("driverClass");

        // 3. 加载驱动
        try {
            Class.forName(driverClass);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // 4. 获取连接
        try {
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(connection);


        ResultSetMetaData resultSetMetaData = null;
        try {
            // 获取结果集的元数据
            resultSetMetaData = resultSet.getMetaData();
            // 获取结果的行数
            int columnCount = resultSetMetaData.getColumnCount();
            if (resultSet.next()) {
                t = clazz.newInstance();
                for (int i = 0; i < columnCount; ++i) {
                    //     1. 获取列的名称
                    String columnName = resultSetMetaData.getColumnLabel(i + 1);
                    //     2. 根据列名获取对应数据表中的数据
                    Object columnVal = resultSet.getObject(columnName);
                    //     3. 将数据表中得到的数据封装进对象
                    Field field = clazz.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(t, columnVal);
                }
                return t;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
        //     关闭资源
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if ( statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return  null;
    }




}
