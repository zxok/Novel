package com.hzx.web;

import java.io.IOException;

import java.net.URL;

import com.hzx.entity.Chapters;
import com.hzx.entity.Updates;
import com.hzx.swing.Novel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.hzx.entity.Novels;
import com.hzx.dao.SqlChapters;
import com.hzx.dao.SqlNovelTab;

public class HttpNovel extends Novel {

    public String name;
    public int sum;
    public int size;

    //停止下载
    public boolean addStatus = false;

    public HttpNovel() {}

    public HttpNovel(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //书名
        this.name = doc.getElementsByTag("h2").get(0).text();

        Elements els = doc.select(".listmain dd");
        //章节总数
        this.sum = (doc.getElementsByTag("dd").size()-12);
        System.out.println("章节总数："+this.sum);
    }

    //封面信息
    public static Novels getBookInfoByUrl(String url) {
        Novels novels = null;
        if(url!=null && !url.equals("")) {
            Document doc = null;
            try {
                doc = Jsoup.parse(new URL(url),5000);
                //doc = Jsoup.connect(url).get();
            } catch (IOException e) {
                System.err.println("连接超时: HttpNovel.getBookInfoByUrl()");
            }
            Elements els = doc.getElementsByTag("h2");
            Element h2_el = els.get(0);
            String bookName = h2_el.text();//书名

            //获得其他基本信息
            Elements els2 = doc.select(".small span");
            //作者
            String author = cutWord(els2.get(0).text());

            //字数、时间、最新章节、章节总数
            Updates uts = getUpdates(url);

            //图片
            String img = doc.select(".cover img").get(0).attr("src");//图片链接
            img = HttpImg.downloadImg(img,HttpImg.USER);//调用获取图片方法，返回图片保存地址

            int id =new SqlNovelTab().finallyID();//获取表中最后一行的id
            novels = new Novels(++id,bookName,author,uts.getWordsNumber(),img,uts.getDate(),url,uts.getNewest(),uts.getSize());
            System.out.println(novels);
        }
        return novels;
    }

    //更新封面信息
    public static Updates getUpdates(String url) {
        Updates uts = null;
        Document doc = null;
        try {
            doc = Jsoup.parse(new URL(url),5000);
        } catch (IOException e) {
            System.err.println("连接超时: HttpNovel.getBookInfoByUrl()");
        }
        //获取更新数据
        Elements els = doc.select(".small span");
        //字数
        String wNumber = cutWord(els.get(3).text());
        //时间
        String date = cutWord(els.get(4).text());
        date = date.substring(0,16);
        //最新章节
        els = doc.select(".listmain dd");
        String newest = els.get(0).text();
        //章节总数
        int size = els.size()-12;

        uts = new Updates(1,wNumber,date,newest,size);
        System.out.println(uts);
        return uts;
    }

    public static String cutWord(String word) {
        return word.substring(word.indexOf("：")+1, word.length());
    }

    //章节名、内容
    public void getChapter(String url,String tab) {
        SqlChapters sqlChapters = null;
        try {
            Document doc = Jsoup.parse(new URL(url),5000);
            Elements els = doc.select(".listmain dd");

            //获取章节总数
            int sum = doc.getElementsByTag("dd").size();
            System.out.println("章节总数：" + (sum - 12));

            sqlChapters = new SqlChapters();
            //加载数据库驱动
            sqlChapters.initConnection(tab);

            //获取表中最后一行的id
            int id = sqlChapters.finalID(tab);

            if (id == 0)
                sum = (sum > 22) ? 22 : sum;
            for (int i = id + 12; i <= sum; i++) {
                this.size = i - 12;
                if (addStatus) {
                    System.out.println("停止下载!");
                    break;
                }
                Element el_dd = els.get(i);
                Element el_a = el_dd.child(0);
                String chapterName = el_a.text();//章节名

                String href = el_a.attr("abs:href");
                String content = getContent(href);//章节内容

                //录入章节数据
                sqlChapters.addStudent(++id, chapterName, content);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.err.println("连接超时: HttpNovel.getChapter()");
            e.printStackTrace();
        } finally {
            //关闭数据库
            sqlChapters.closeChr();
            addStatus = true;
        }
    }

    //获取单个章节
    public Chapters getSingle(String url,String tab,int ind) {
        ind += 12;
        Document doc = null;
        try {
            doc = Jsoup.parse(new URL(url),5000);
        } catch (IOException e) {
            System.err.println("连接超时: HttpNovel.getSingle()");
            return null;
        }

        SqlChapters scts = new SqlChapters();
        try {
            Elements els = doc.select(".listmain dd");

            Element el_dd = els.get(ind);
            Element el_a = el_dd.child(0);
            String chapterName = el_a.text();//章节名

            String href = el_a.attr("abs:href");
            String content = getContent(href);//章节内容

            ind -= 11;
            System.out.println(ind);

            //开启数据库
            scts.initConnection(tab);
            //录入章节数据
            scts.addStudent(ind,  chapterName, content);

            return new Chapters(ind,chapterName,content);

        } catch (NullPointerException e) {
           e.getStackTrace();
        } finally {
            //关闭数据库
            scts.closeChr();
        }
        return null;
    }

    //获取章节内容
    public String getContent(String url) {

        String s = null;
        try {
            Document doc = Jsoup.parse(new URL(url),15000);
            Element content_el = doc.getElementById("content");
            s = content_el.html();	//获取文本
        } catch (IOException e) {
            System.err.println("连接超时: HttpNovel.getContent()");
        }
        //处理文本
        s = s.replace("<br>" , "");	//替换
        s = s.replace("&nbsp;" , " ");
        int n = s.indexOf("http");//获取字符第一次出现处的索引
        if(n != -1)
            s = s.substring(0,n);	//截取
        s = "\n".concat(s);	//连接
        return s;
    }


}



