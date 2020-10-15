package com.hzx.web;

import com.hzx.entity.*;
import com.hzx.swing.util.NovelStore;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;

public class JsoupStore {

    private static final String  URL_HOME = "http://www.shuquge.com/";
    private static Document doc = null;

    private static void loadingHomeUrl() {
        try {
            doc = Jsoup.parse(new URL(URL_HOME),10000);
        } catch (IOException e) {
            NovelStore.pgs = true;
            System.err.println("链接超时: JsoupStore.static{}");
        }
    }

    //类型标题
    public static String[] type() {
        loadingHomeUrl();
        Element el = doc.getElementsByClass("nav").get(0);
        String[] types = new String[7];
        for (int i=1; i<=7; i++){
            types[i-1] = el.getElementsByTag("a").get(i).attr("href");
            /*types[i-1] = el.getElementsByTag("li").get(i).text();
            System.out.print(types[i-1]+"\t");*/
        }
        for (String s :
                types) {
            System.out.println(s);
        }
        return types;
    }

    //热门小说
    public static ArrayList<StoreHot> hot(String url) {
        ArrayList<StoreHot> arrHot = getTypes(url);
        return arrHot;
    }

    //推荐
    public static ArrayList<StoreRecommend> recommend() {
        loadingHomeUrl();
        ArrayList<StoreRecommend> arrRmd = new ArrayList<StoreRecommend>();
        for (Element el :
                doc.getElementsByClass("block")) {

            StoreRecommend sr = new StoreRecommend();
            sr.setH2(el.getElementsByTag("h2").text());
            sr.setBookName(el.getElementsByTag("a").get(1).text());
            sr.setBriefing(el.getElementsByTag("dd").text());
            sr.setImg(HttpImg.downloadImg(el.select(".image img").get(0).attr("src"),HttpImg.STORE_BUFFER));
            sr.setUrl(el.select(".image a").get(0).attr("href"));

            Elements els2 = el.getElementsByTag("li");
            String[] names = new String[12];
            String[] urls = new String[12];
            for (int i = 0; i < 12; i++) {
                Element el2 = els2.get(i);
                names[i] = el2.text();
                urls[i] = el2.select("li a").get(0).attr("href");
            }
            sr.setBookNames(names);
            sr.setUrls(urls);
            arrRmd.add(sr);
        }
        return arrRmd;
    }

    //书籍详细信息
    public static Details getData(String urls) {
        loadingHomeUrl();
        try {
            doc = Jsoup.parse(new URL(urls),10000);
        } catch (IOException e) {
            System.err.println("获取书籍详细信息连接超时: JsoupStore.getData()");
        }
        Details dts = new Details();
        Elements els = doc.getElementsByClass("small").get(0).getElementsByTag("span");

        dts.setImg(HttpImg.downloadImg(doc.select(".cover img").get(0).attr("src"),HttpImg.STORE_BUFFER));;
        dts.setName(doc.getElementsByTag("h2").text());
        dts.setAuthor(els.get(0).text());
        dts.setType(els.get(1).text());
        dts.setState(els.get(2).text());
        String sum = els.get(3).text();
        dts.setSum(sum.substring(sum.indexOf('：')+1));
        dts.setTime(els.get(4).text());
        dts.setLatest(els.get(5).text());
        dts.setBrief(doc.getElementsByClass("intro").text());

        els = doc.getElementsByClass("listmain").get(0).getElementsByTag("dd");
        String[] str = new String[12];
        for (int i = 0; i < 12; i++) {
            str[i] = els.get(i).text();
        }
        dts.setLatests(str);

        //System.out.println(dts);
        return dts;
    }

    //图片、书名、作者、简介 img(热门、类型)
    public static ArrayList<StoreHot> getTypes(String urls) {
        loadingHomeUrl();
        try {
            doc = Jsoup.parse(new URL(urls),20000);
        } catch (IOException e) {
            System.err.println("连接超时: JsoupStore.getTypes()");
            e.printStackTrace();
        }
        ArrayList<StoreHot> arsh = new ArrayList<StoreHot>();
        Elements els = doc.getElementsByClass("item");
        for (int i = 0; i < doc.getElementsByClass("item").size(); i++) {
            Element el = els.get(i);
            StoreHot sh = new StoreHot();
            sh.setBookName(el.getElementsByTag("a").text());
            sh.setAuthor(el.getElementsByTag("span").text());
            sh.setBriefing(el.getElementsByTag("dd").text());
            sh.setUrl(el.select(".image a").get(0).attr("href"));
            sh.setImg(HttpImg.downloadImg(el.select(".image img").get(0).attr("src"),HttpImg.STORE_BUFFER));
            arsh.add(sh);
            System.out.println(sh);
        }
        return arsh;
    }

    //类型内容 text
    public static ArrayList<Types> getTypes2(String urls) {
        loadingHomeUrl();
        try {
            doc = Jsoup.parse(new URL(urls),20000);
        } catch (IOException e) {
            System.err.println("连接超时: JsoupStore.getTypes2()");
            e.printStackTrace();
        }
        ArrayList<Types> arty = new ArrayList<Types>();
        for (Element el :
                doc.getElementsByClass("l bd").get(0).getElementsByTag("li")) {
            Types types = new Types();
            types.setType(el.getElementsByTag("span").get(0).text());
            types.setName(el.getElementsByTag("span").get(1).text());
            types.setLatest(el.getElementsByTag("span").get(2).text());
            types.setAuthor(el.getElementsByTag("span").get(3).text());
            types.setTime(el.getElementsByTag("span").get(4).text());
            types.setUrl(el.getElementsByTag("a").get(0).attr("href"));
            arty.add(types);
            System.out.println(types);
        }
        return arty;
    }

    //类型-跳转页面
    public static MorePageStore getMorePageStore(String url) {
        Document doc = getDoc(url);
        Elements els = doc.getElementsByClass("a-btn");
        int sum = els.size();

        MorePageStore mps = new MorePageStore();

        //首页、尾页
        mps.setHead(URL_HOME + els.get(0).attr("href"));
        mps.setTail(URL_HOME + els.get(sum-1).attr("href"));

        //上一页
        els = doc.getElementsByClass("prev");
        if (els.size() != 0)
            mps.setPrev(URL_HOME + els.get(0).attr("href"));

        //下一页
        els = doc.getElementsByClass("next");
        if (els.size() != 0)
            mps.setNext(URL_HOME + els.get(0).attr("href"));

        System.out.println(mps);

        return mps;
    }

    private static Document getDoc(String url) {
        Document document = null;
        try {
            document = Jsoup.parse(new URL(url), 5000);
        } catch (IOException e) {
            System.err.println("链接超时: JsoupStore.getDoc(String url)");
        }
        return document;
    }

}
