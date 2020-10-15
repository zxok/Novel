package com.hzx.dao;

import com.hzx.entity.Novels;
import com.hzx.entity.Updates;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SqlNovelTab {

    //添加小说基本数据
    public static void addNovelCoverData(Novels novel, String url) {
        String sql_insert = "insert into novels values(?,?,?,?,?,?,?,?,?,?)";
        Connection conn = Sql_JDBC.getConnection();//加载驱动
        PreparedStatement pre = null;
        try {
            pre = conn.prepareStatement(sql_insert);

            pre.setInt(1, novel.getId());
            pre.setString(2, novel.getBookName());
            pre.setString(3, novel.getAuthor());
            pre.setString(4, novel.getWordsNumber());
            pre.setString(5, novel.getImg());
            pre.setString(6, novel.getDate());
            pre.setString(7, url);
            pre.setString(8, novel.getNewest());
            pre.setInt(9,novel.getSize());
            pre.setInt(10,0);
            pre.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Sql_JDBC.closeResources(conn,pre,null);//关闭数据库
        }
    }

    //查询
    public static List<Novels> queryAllNovel() {
        String sql_select = "select * from novels";
        Connection conn = Sql_JDBC.getConnection();//加载驱动
        PreparedStatement pre = null;
        ResultSet rs = null;
        List<Novels> list = new ArrayList<Novels>();
        try {
            pre = conn.prepareStatement(sql_select);
            rs = pre.executeQuery();

            while(rs.next()) {
                int id = rs.getInt("id");
                String bookName = rs.getString("bookName");
                String author = rs.getString("author");
                String wn = rs.getString("wordsNumber");
                String img = rs.getString("img");
                String date = rs.getString("newDate");
                String url = rs.getString("url");
                String newest = rs.getString("newest");
                int size = rs.getInt("size");
                int readingRecord = rs.getInt("readingRecord");
                list.add(new Novels(id,bookName,author,wn,img,date,url,newest,size,readingRecord));
                System.out.print(id+"\t");
            }
            System.out.println();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Sql_JDBC.closeResources(conn,pre,rs);//关闭数据库
        }
        return list;
    }

    //删除
    public static void delete(int id) {
        Connection conn = Sql_JDBC.getConnection();//加载驱动
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        String chap = "chapters";
        chap += Integer.toString(id); //拼接表名

        try {
            //删除对应表
            stmt.executeUpdate("drop table "+chap+"");

            //删除图片
            rs = stmt.executeQuery("SELECT bookName,img FROM novels WHERE id = "+ id +"");
            new File(rs.getString("img").replace("\\","\\\\")).delete();
            String name = rs.getString("bookName");

            //删除对应id行
            stmt.executeUpdate("delete from novels where id="+id+"");

            System.out.println("《"+name+"》 删除成功！");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            //关闭数据库
            Sql_JDBC.closeResources(conn,stmt,rs);
        }
    }

    //获取表中最后一行的id字段
    public static int finallyID() {
        int id=0 ;
        String sql_select = "SELECT * FROM novels"; //表
        Connection conn = Sql_JDBC.getConnection();//加载驱动
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            stmt = conn.prepareStatement(sql_select);
            rs = stmt.executeQuery();
            while(rs.next())
                id = rs.getInt("id");
            //rs.last();
            //id = rs.getInt("id");

        } catch (SQLException e) {
            System.err.println("数据表 novels 为空！");
        } finally {
            Sql_JDBC.closeResources(conn,stmt,rs);//关闭数据库
        }
        System.out.println("最后一行id： "+id);
        return id;
    }

    //更新数据
    public static void updatesData(LinkedList<Updates> list) {
        //加载驱动
        Connection conn = Sql_JDBC.getConnection();
        PreparedStatement ps =null ;
        try {
            ps = conn.prepareStatement("update novels set wordsNumber=?,newDate=?,newest=?,size=? where id=?  ");
            for (Updates ns: list) {
                ps.setString(1,ns.getWordsNumber());
                ps.setString(2,ns.getDate());
                ps.setString(3,ns.getNewest());
                ps.setInt(4,ns.getSize());
                ps.setInt(5,ns.getId());
                ps.addBatch();
            }
            ps.executeBatch();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            Sql_JDBC.closeResources(conn,ps,null);//关闭数据库
        }
    }

    //更新阅读记录
    public static void updateReadingRecord(int value, int id) {
        Connection conn = Sql_JDBC.getConnection();
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("UPDATE novels SET readingRecord='" + value + "'  WHERE id='" + id + "' ");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            //关闭数据库
            Sql_JDBC.closeResources(conn, stmt,null);
        }
    }

    //获取阅读记录
    public static int getReadingRecord(int id) {
        Connection conn = Sql_JDBC.getConnection();//加载驱动
        Statement stmt = null;
        ResultSet rs = null;
        int readingRecord = 0;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT readingRecord FROM novels WHERE id = "+ id +"");
            readingRecord = rs.getInt("readingRecord");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            //关闭数据库
            Sql_JDBC.closeResources(conn,stmt,rs);
        }
        return readingRecord;
    }

}


