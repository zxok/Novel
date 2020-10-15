package com.hzx.service;

import com.hzx.entity.Novels;
import com.hzx.entity.Updates;
import com.hzx.dao.SqlChapters;
import com.hzx.dao.SqlNovelTab;
import com.hzx.swing.Novel;
import com.hzx.web.HttpNovel;

import java.util.LinkedList;
import java.util.List;

/**
 * @program: Novel
 * @author: hzx
 * @since: JDK 1.8
 * @create: 2020-09-11 13:38
 **/
public class ServiceDB extends Novel {

    //更新书架封面数据
    public static void updatesNovelsData() {
        LinkedList<Updates> newBook = new LinkedList<>();
        List<Novels> listNovel = SqlNovelTab.queryAllNovel();
        for (Novels n : listNovel) {
            Updates uts = HttpNovel.getUpdates(n.getUrl());
            uts.setId(n.getId());
            newBook.add(uts);
        }
        SqlNovelTab.updatesData(newBook);
    }

    //更新阅读记录
    public static void setReadingRecord(int value, String ids) {
        int id = Integer.valueOf(ids);
        SqlNovelTab.updateReadingRecord(value, id);
    }

    //获取阅读记录
    public static int getReadingRecord(String ids) {
        int id = Integer.valueOf(ids);
        return SqlNovelTab.getReadingRecord(id);
    }

    //添加小说
    public static boolean addNovels(String url) throws Exception {
        //获取小说封面信息
        Novels novel = HttpNovel.getBookInfoByUrl(url);

        //录入数据
        SqlNovelTab.addNovelCoverData(novel,url);
        System.out.println("id"+novel.getId());
        System.out.println("《"+novel.getBookName()+"》 "+"加入书架！");

        //加载书架
        Novel.updateBookshelf(str[5],str[6]);

        //创建新表
        //获取表中最后一行的id
        int id =new SqlNovelTab().finallyID();
        String chapTab = new SqlChapters().createTable(id+"");

        //章节内容
        new HttpNovel().getChapter(url,chapTab);

        return true;
    }

}
