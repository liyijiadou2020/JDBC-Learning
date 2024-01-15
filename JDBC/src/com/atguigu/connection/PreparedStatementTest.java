package com.atguigu.connection;

import com.atguigu.utils.JDBCUtils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 * @author: liyijia
 * @version: 0.0
 * @create time: 2024-01-2024/1/15
 * @description: 使用 PreparedStatement 实现数据库的增删改查
 */
public class PreparedStatementTest {

    public void update(String sql, Object ... args){
        /**
         * 通用的增删改操作
         */
        Connection connection = null;
        PreparedStatement ps = null;
        try {
        //     1. 获取数据库连接
            connection = JDBCUtils.getConnection();
        //     2. 获取 PreparedStatement 的实例（或预编译sql语句）
            ps = connection.prepareStatement(sql);
        //     3. 填充占位符
            for (int i = 0; i < args.length; ++i) {
                ps.setObject(i + 1, args[i]);
            }
        //     4. 执行sql语句
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        //     5. 关闭资源
            JDBCUtils.closeResource(connection, ps);
        }

    }

    public <T> T getInstance(Class<T> clazz, String sql, Object ... args) {
        /**
         * 通用的针对不同表的查询：返回一个对象 (version 1.0)
         */
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            //     1. 获取数据库连接
            connection = JDBCUtils.getConnection();
            //     2. 获取 PreparedStatement 的实例（或预编译sql语句）
            ps = connection.prepareStatement(sql);
            //     3. 填充占位符
            for (int i = 0; i < args.length; ++i) {
                ps.setObject(i + 1, args[i]);
            }
            //     4. 执行sql语句
            ps.execute();
            //     5. 得到结果集的元数据： ResultSetMetaData
            ResultSetMetaData rsmd = rs.getMetaData();
            //     6.1 通过 ResultSetMetaData 得到 columnCount，columnLabel；通过 ResultSet 得到列值
            int columnCount = rsmd.getColumnCount();
            if (rs.next()) {
                T t = clazz.newInstance();
                for (int i = 0; i < columnCount; i++) { // 遍历每一个列
                    //     1. 获取列的别名： 充当
                    String columnLabel = rsmd.getColumnLabel(i + 1);
                    //     2. 获取列值
                    Object columnVal = rs.getObject(columnLabel);
                    //     6.2. 使用反射，给对象的相应属性赋值
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, columnVal);

                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //     5. 关闭资源
            JDBCUtils.closeResource(connection, ps);
        }

        return null;
    }


}
