package com.hzx.cfg;

import com.hzx.entity.UserXML;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @program: Novel
 * @author: hzx
 * @since: JDK 1.8
 * @create: 2020-09-12 17:56
 **/
public class XML_Dom4J {

    public static final int MENU_COLOR = 0;
    public static final int READ_BGD_COLOR = 1;
    public static final int READ_FONT_SIZE = 2;
    public static final int USER_FILE = 3;

    private static String fileXML = getFileRoot()+"\\xml\\userXML.xml";

    public static String getFileXML() {
        fileXML = getFileRoot()+"\\xml\\userXML.xml";
        return fileXML;
    }
    public static void setFileXML(String fileXML) {
        XML_Dom4J.fileXML = fileXML+"\\xml\\userXML.xml";
    }

    //获取xml文件所有数据
    public static UserXML getXMLAll() {

        //读取xml文件，并返回一个Document对象
        Document doc = getDoc(getFileXML());

        //得到一个xml根节点
        Element root = doc.getRootElement();
        System.out.println(root.getName());

        //获取元素集合
        List<Element> els = root.elements();

        UserXML user = new UserXML();
        user.setMenuColor(Integer.valueOf(els.get(0).getText()));
        user.setReadBgdColor(Integer.valueOf(els.get(1).getText()));
        user.setReadFontSize(Integer.valueOf(els.get(2).getText()));
        user.setUserFile(els.get(3).getText());

        return user;
    }

    //获取xml文件指定数据
    public static int getXMLa(int key) {

        //读取xml文件，并返回一个Document对象
        Document doc = getDoc(getFileXML());

        //得到一个xml根节点
        Element root = doc.getRootElement();
        System.out.println(root.getName());

        //获取元素集合
        List<Element> els = root.elements();

        int data = Integer.valueOf(els.get(key).getText());
        return data;
    }

    //修改用户数据
    public static void updateXML(String value, int key) {
        //获取Document对象
        Document doc = getDoc(getFileXML());
        //得到xml根节点
        Element root = doc.getRootElement();
        List<Element> els = root.elements();

        els.get(key).setText(value);
        saveXML(doc,getFileXML());
    }

    //获取Document对象,的方法
    private static Document getDoc(String file) {
        //获取一个SAXReader对象（来自于dom4j包）
        SAXReader saxr = new SAXReader();

        Document doc = null;
        try {
            //读取xml文件，并返回一个Document对象
            doc = saxr.read(new File(file));

        } catch (DocumentException e) {
            e.printStackTrace();
        }

        //返回 Document 对象
        return doc;
    }

    //保存xml文件
    private static void saveXML(Document doc,String file) {
        OutputFormat of = OutputFormat.createPrettyPrint();
        of.setEncoding("UTF-8");
        try {
            XMLWriter writer = new XMLWriter(new FileWriter(file));
            writer.write(doc);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //获取文件地址
    public static String getFileRoot() {
        Document doc = getDoc("File.xml");

        return doc.getRootElement().getText();
    }
    //修改文件地址
    public static void updateFileRoot(String value) {
        //获取Document对象
        Document doc = getDoc("File.xml");
        //得到xml根节点
        Element root = doc.getRootElement();
        System.out.println(root.getName());
        root.setText(value);
        saveXML(doc,"File.xml");
    }

    //判断文件是否相同，相同返回true
    public static boolean sameFile(File sourceFile, File targetFile) {
        File[] list = sourceFile.listFiles();
        File[] files = targetFile.listFiles();
        for (int i = 0; i < list.length; i++) {
            if (!list[i].getName().equals(files[i].getName()))
                return false;
            if (list[i].isDirectory())
                sameFile(list[i].getAbsoluteFile(),files[i].getAbsoluteFile());
        }
        return true;
    }
}
