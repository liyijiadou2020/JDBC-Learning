package com.atguigu.blob;

import com.atguigu.objects.Customer;
import com.atguigu.utils.JDBCUtils;
import org.junit.Test;

import java.io.*;
import java.sql.*;

/**
 * @author: liyijia
 * @version: 0.0
 * @create time: 2024-01-2024/1/15
 * @description: 操作 BLOB 类型字段
 */
public class BlobTest {
    @Test
    public void testInsertBlob() throws Exception {
        /**
         * 测试插入
         */
        Connection connection = JDBCUtils.getConnection();

        String sql = "INSERT INTO customers(name, email, birth, photo) VALUES(?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, "黄药师");
        ps.setString(2, "hys@126.com");
        ps.setString(3, "1992-08-02");

        FileInputStream fileInputStream = new FileInputStream(new File("hys.png"));
        ps.setBlob(4, fileInputStream);

        ps.execute();

        fileInputStream.close();
        JDBCUtils.closeResource(connection, ps);
    }

    @Test
    public void testQueryBlob() throws Exception {
        /**
         * 测试插入
         */
        Connection connection = JDBCUtils.getConnection();
        String sql = "SELECT id, name, email, birth, photo FROM customers WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setInt(1, 19);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Integer id = rs.getInt(1);
            String name = rs.getString(2);
            String email = rs.getString(3);
            Date birth = rs.getDate(4);

            Customer customer = new Customer(id, name, email, birth);
            System.out.println(customer);

        //     读取blob类型的字段
            Blob photo = rs.getBlob(5);
            InputStream inputStream = photo.getBinaryStream();
            OutputStream outputStream = new FileOutputStream("c.png");
            byte[] buffer = new byte[1024];
            int len=0;
            while ((len= inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            JDBCUtils.closeResource(connection, ps, rs);

            if (inputStream != null){
                inputStream.close();
            }

            if (outputStream != null){
                outputStream.close();
            }

        }

    }

}
