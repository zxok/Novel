package com.hzx.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class SqlSetup {

    //主窗口颜色
    public static final String MAIN_WINDOW_COLOR = "menuColor";
    //阅读窗口背景颜色
    public static final String READ_BACKGROUND_COLOR = "readBgdColor";
    //阅读窗口字体大小
    public static final String READ_FONT_SIZE = "readFontSize";

    //查询
    public static int[] getSetup() {
        int sz[] = {0,0,0};
        String sql_select = "select * from setup"; //表  where id=1
        Connection conn = Sql_JDBC.getConnection();//加载驱动
        PreparedStatement pre = null;
        ResultSet rs = null;
        try {
            pre = conn.prepareStatement(sql_select);
            rs = pre.executeQuery();

            sz[0] = rs.getInt(MAIN_WINDOW_COLOR);
            sz[1] = rs.getInt(READ_BACKGROUND_COLOR);
            sz[2] = rs.getInt(READ_FONT_SIZE);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Sql_JDBC.closeResources(conn,pre,rs);//关闭数据库
        }
        return sz;
    }


    //更新
    public static void setSetup(String value, String field) {
        //加载驱动
        Connection conn = Sql_JDBC.getConnection();
        Statement stmt = null;
        try {
            stmt = conn.createStatement();

            //update 表名 set 列名 = '修改值' where  =
            int e = stmt.executeUpdate("update setup set "+field+" = "+value+"  where id=1 ");
            System.out.println(e);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            Sql_JDBC.closeResources(conn,stmt,null);//关闭数据库
        }
    }

}

