package com.hzx.dao;

import java.sql.*;

public class Sql_JDBC {

    public static String con="org.sqlite.JDBC";
    public static String sqlName="javaNovel.db";
    public static String url="jdbc:sqlite:"+sqlName;

    //加载数据库连接驱动
    public static Connection getConnection() {
        Connection conn = null;
        try {
            //加载驱动，连接SQLite
            Class.forName(con);

            // 建立一个数据库的连接，如果不存在就在当前目录下创建
            conn = DriverManager.getConnection(url);
            System.out.println("加载驱动："+url);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return conn;
    }


    //关闭数据库 ResultSet
    public static void closeResources(Connection conn,Statement stmt,ResultSet rs) {
        try {
            if(stmt != null) {
                stmt.close();
            }
            if(conn != null) {
                conn.close();
            }
            if(rs != null) {
                rs.close();
            }
            System.out.println("关闭数据库连接");
        } catch (SQLException e) {
            System.out.println("数据库连接关闭失败！");
            e.printStackTrace();
        }
    }

    static {
        Connection conn = null;
        Statement stat = null;

        try {
            conn = getConnection();
            stat = conn.createStatement();

            //查询表'setup'是否存在，若不存在着创建该表
            try {
                stat.executeQuery("select * from setup;");
            } catch (SQLException throwables) {
                System.err.println("数据表\'setup\'不存在");
                //创建表setup
                stat.executeUpdate("CREATE TABLE setup(" +
                        "id int(11) NOT 1,\n" +
                        "  menuColor int(11) DEFAULT 0,\n" +
                        "  readBgdColor int(11) DEFAULT 1,\n" +
                        "  readFontSize int(11) DEFAULT 20,\n" +
                        "  PRIMARY KEY (id));");
                //插入数据
                stat.executeUpdate("insert into setup values(1,0,1,20);");
                System.err.println("创建表数据: \'setup\'");
            }

            //查询表'novels'是否存在，若不存在着创建该表
            try {
                stat.executeQuery("select * from novels;");
            } catch (SQLException throwables) {
                System.out.println("数据表\'novels\'不存在");
                //创建表novels
                String sql_novels = "CREATE TABLE novels(" +
                        " id int(12) NOT NULL,\n" +
                        "   bookName text(20) DEFAULT NULL,\n" +
                        "   author text(10) DEFAULT NULL,\n" +
                        "   wordsNumber text(20) DEFAULT NULL,\n" +
                        "   img text(255) DEFAULT NULL,\n" +
                        "   newDate text(20) DEFAULT NULL,\n" +
                        "   url text(255) DEFAULT NULL,\n" +
                        "   newest text(50) DEFAULT NULL,\n" +
                        "   size integer(5) DEFAULT NULL,\n" +
                        "   readingRecord integer(5) DEFAULT 0,\n" +
                        "  PRIMARY KEY (id));";
                stat.executeUpdate(sql_novels);
                System.out.println("创建数据表: \'novels\'");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            closeResources(conn,stat,null);
        }
    }

}
