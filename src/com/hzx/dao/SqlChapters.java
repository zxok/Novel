package com.hzx.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.hzx.entity.Chapters;

public class SqlChapters extends Sql_JDBC {

    //数据表编号
    private static String tabID;

    public SqlChapters(){}

    public SqlChapters(String n){
        SqlChapters.tabID = n;
    }

    //创建数据表
    public static String createTable(String id) throws Exception {
        //加载驱动
        Connection conn = Sql_JDBC.getConnection();
        Statement stat = conn.createStatement();

        //拼接表名
        String chap = "chapters"+id;
        System.out.println("新建数据表："+chap);

        String sql_CREATE = "CREATE TABLE "+chap+"("
                + "id int(11) NOT NULL, "
                + "names varchar(255) DEFAULT NULL,"
                + "content mediumtext,"
                + "PRIMARY KEY (id));";
        try {
            stat.executeUpdate(sql_CREATE);
        }catch (Exception e) {
            System.err.println("数据表 "+chap+" 已存在!");
        } finally {
            //关闭数据库
            closeResources(conn,stat,null);
        }

        return chap;
    }

    private Connection  conn = null;
    private PreparedStatement pre = null;
    //加载数据库驱动
    public void initConnection(String chap) {
        String sql_insert = "insert into "+chap+" values(?,?,?)"; //表
        conn = Sql_JDBC.getConnection();
        try {
            pre = conn.prepareStatement(sql_insert);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    //关闭数据库
    public void closeChr() {
        Sql_JDBC.closeResources(conn, pre,null);
    }
    //添加数据
    public void addStudent (int id, String names, String content) {
        try {
            pre.setInt(1, id);
            pre.setString(2, names);
            pre.setString(3, content);
            pre.executeUpdate();
            System.out.println(names);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //查询
    public static List<Chapters> queryAllData() {
        String chap = "chapters";
        chap = chap + tabID;
        String sql_select = "select * from "+chap+"";
        //加载驱动
        Connection conn = Sql_JDBC.getConnection();
        PreparedStatement pre = null;
        ResultSet rs = null;
        List<Chapters> list = new ArrayList<Chapters>();
        try {
            pre = conn.prepareStatement(sql_select);
            rs = pre.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("id");
                String names = rs.getString("names");
                String content = rs.getString("content");
                list.add(new Chapters(id,names,content));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //关闭数据库
            Sql_JDBC.closeResources(conn, pre,rs);
        }
        return list;
    }

    //获取表中最后一行的id字段
    public static int finalID(String tab) {
        int id=0;
        String sql_select = "select * from "+tab+""; //表
        Connection conn = Sql_JDBC.getConnection();//加载驱动
        try {
            Statement pre = conn.createStatement();
            ResultSet rs = pre.executeQuery(sql_select);
            while(rs.next())
                id = rs.getInt("id");
            //rs.last();
            //id = rs.getInt("id");
            System.out.println("已下载： "+id+" 章");

            rs.close();
            Sql_JDBC.closeResources(conn, pre,null);//关闭数据库
        } catch (SQLException e) {
            System.err.println("数据表"+tab+" 为空！");
        }
        return id;
    }

}


