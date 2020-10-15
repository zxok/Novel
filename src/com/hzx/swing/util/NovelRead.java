package com.hzx.swing.util;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.hzx.entity.Chapters;
import com.hzx.service.ServiceDB;
import com.hzx.dao.SqlSetup;
import com.hzx.dao.SqlChapters;
import com.hzx.web.HttpNovel;

public class NovelRead {

    //可同时下载数(线程池)
    private static ExecutorService es = Executors.newFixedThreadPool(2);

    //下载
    public HttpNovel httpNovel = null;

    //目录状态
    private int exitCatalog = 0;
    //目录加载数
    private int sumCatalog = 0;

    //窗口宽高
    private int width = 760;
    private int height = 570;

    //通过数据库数据获取用户信息
    private int sz[] = SqlSetup.getSetup();

    //窗口颜色
    private final static String[][] COLORS = {
            //顶部面板, 章节内容背景, 章节内容字体颜色, 章节名字体颜色
            {"#5c5b5b", "#5c5b5b", "#d5d2d2","#e9e6e6"},//灰色
            {"#decebd", "#decebd", "#262626","#9e0d17"},//黄色
            {"#ced4e0", "#ced4e0", "#262626","#9e0d17"},//蓝色
            {"#fefefe", "#fefefe", "#262626","#9e0d17"},//白色
    };

    private final static String[] COLOR_NAME = {"灰色","黄色","蓝色","白色"};

    private final static int[] FONT_SIZE = {18,20,22,24};

    //章节下标
    private int id = 0;
    //小说表名
    private String tabNameID;
    //章节总数
    private int sumChapter;

    public NovelRead(String tabNameID, int sumChapter) {
        this.tabNameID = tabNameID;
        this.sumChapter = sumChapter;
    }

    List<Chapters> list = null;
    Chapters cts = null;



    public void read(String name, String url) {
        list = new SqlChapters(tabNameID).queryAllData();
        System.out.println("打开数据表："+ tabNameID);
        if (list.size() != 0) {
            //获取阅读记录
            this.id = ServiceDB.getReadingRecord(tabNameID);
            cts = list.get(id);
        } else {
            cts = getChapters(url,id);
        }

        //窗口
        JFrame jf = new JFrame(name);
        jf.setSize(width,height);//窗口宽,高

        //阅读面板
        JPanel jp = new JPanel();

        //目录面板
        JPanel jp2 = jPlCatalog(jp, tabNameID);
        JLabel la = new JLabel(cts.getId()+"."+cts.getNames());//章节名
        //目录滚动条
        JScrollPane jsp2 = new JScrollPane(jp2);
        jsp2.getVerticalScrollBar().setUnitIncrement(40);
        jsp2.setBounds(1,45 , width-20,height-115);
        jsp2.setVisible(false);//隐藏
        jp.add(jsp2);

        //阅读面板
        jf.add(jp);
        jp.setBounds(0,0, jf.getWidth(),jf.getHeight()-35);
        jp.setBackground(Color.decode(COLORS[sz[1]][0]));//面板背景颜色ced4e0
        jp.setLayout(null);//布局

        //章节名
        jp.add(la);
        la.setBounds(10,10 , width-360,30);
        la.setFont(new Font("黑体", Font.PLAIN, 20));
        la.setForeground(Color.decode(COLORS[sz[1]][3]));//字体颜色2d3038

        //按钮面板
        JPanel panBtn = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        panBtn.setBounds(width-340,0,300,45);
        panBtn.setBackground(Color.decode(COLORS[sz[1]][1]));
        jp.add(panBtn);
        JButton bt = getBtn("上一章", panBtn);
        JButton bt2 = getBtn("下一章", panBtn);
        JButton btCatalog = getBtn("目录", panBtn);


        //章节内容
        //文本框
        JTextArea textArea = new JTextArea(cts.getContent());
        //滚动条
        JScrollPane jsp = new JScrollPane(textArea);
        jsp.getVerticalScrollBar().setUnitIncrement(40);
        textArea.setSelectionStart(0);
        textArea.setSelectionEnd(0);
        jp.add(jsp);
        // tex.setOpaque(false);  //透明
        textArea.setLineWrap(true);  //激活自动换行功能
        textArea.setWrapStyleWord(true); // 激活断行不断字功能
        textArea.setCaretPosition(1);
        textArea.setFont(new Font("黑体", Font.PLAIN, sz[2]));
        textArea.setBackground(Color.decode(COLORS[sz[1]][1]));//背景颜色
        textArea.setForeground(Color.decode(COLORS[sz[1]][2]));//字体颜色
        jsp.setBounds(1,45 , width-20,height-115);

        //字体菜单栏
        JMenuBar mb2 = new JMenuBar();//菜单栏
        jf.setJMenuBar(mb2);
        JMenu f2 = new JMenu("字体大小");//菜单
        //菜单项
        JMenuItem z2 = new JMenuItem(""+FONT_SIZE[0]);
        JMenuItem z3 = new JMenuItem(""+FONT_SIZE[1]);
        JMenuItem z4 = new JMenuItem(""+FONT_SIZE[2]);
        JMenuItem z5 = new JMenuItem(""+FONT_SIZE[3]);
        f2.add(z2);
        f2.add(z3);
        f2.add(z4);
        f2.add(z5);

        //下载菜单栏
        JMenuBar mb3 = new JMenuBar();//菜单栏
        jf.setJMenuBar(mb3);
        JMenu meun3 = new JMenu("下载");//菜单
        //菜单项
        JMenuItem add1 = new JMenuItem("全本下载");
        meun3.add(add1);
        JMenuItem add2 = new JMenuItem("停止下载");
        meun3.add(add2);

        //颜色菜单栏
        JMenuBar mb = new JMenuBar();//菜单栏
        jf.setJMenuBar(mb);
        JMenu f = new JMenu("背景颜色");//菜单
        //菜单项
        JMenuItem m2 = new JMenuItem(COLOR_NAME[0]);
        f.add(m2);
        JMenuItem m3 = new JMenuItem(COLOR_NAME[1]);
        f.add(m3);
        JMenuItem m4 = new JMenuItem(COLOR_NAME[2]);
        f.add(m4);
        JMenuItem m5 = new JMenuItem(COLOR_NAME[3]);
        f.add(m5);
        mb.add(f);
        mb.add(f2);
        mb.add(meun3);



        m2.addActionListener(new ActionListener() {//灰色
            public void actionPerformed(ActionEvent e) {
                //修改数据库
                SqlSetup.setSetup("0", SqlSetup.READ_BACKGROUND_COLOR);

                jp.setBackground(Color.decode(COLORS[0][0]));//顶部面板
                textArea.setBackground(Color.decode(COLORS[0][1]));//章节内容
                textArea.setForeground(Color.decode(COLORS[0][2]));//章节内容字体颜色
                la.setForeground(Color.decode(COLORS[0][3]));//章节名字体颜色
            }
        });

        m3.addActionListener(new ActionListener() {//黄色
            public void actionPerformed(ActionEvent e) {
                //修改数据库
                SqlSetup.setSetup("1", SqlSetup.READ_BACKGROUND_COLOR);

                jp.setBackground(Color.decode(COLORS[1][0]));//顶部面板
                textArea.setBackground(Color.decode(COLORS[1][1]));//章节内容
                textArea.setForeground(Color.decode(COLORS[1][2]));//章节内容字体颜色
                la.setForeground(Color.decode(COLORS[1][3]));//章节名字体颜色
            }
        });

        m4.addActionListener(new ActionListener() {//蓝色
            public void actionPerformed(ActionEvent e) {
                //修改数据库
                SqlSetup.setSetup("2", SqlSetup.READ_BACKGROUND_COLOR);

                jp.setBackground(Color.decode(COLORS[2][0]));//顶部面板
                textArea.setBackground(Color.decode(COLORS[2][1]));//章节内容
                textArea.setForeground(Color.decode(COLORS[2][2]));//章节内容字体颜色
                la.setForeground(Color.decode(COLORS[2][3]));//章节名字体颜色
            }
        });

        m5.addActionListener(new ActionListener() {//白色
            public void actionPerformed(ActionEvent e) {
                //修改数据库
                SqlSetup.setSetup("3", SqlSetup.READ_BACKGROUND_COLOR);

                jp.setBackground(Color.decode(COLORS[3][0]));//顶部面板
                textArea.setBackground(Color.decode(COLORS[3][1]));//章节内容
                textArea.setForeground(Color.decode(COLORS[3][2]));//章节内容字体颜色
                la.setForeground(Color.decode(COLORS[3][3]));//章节名字体颜色
            }
        });


        z2.addActionListener(new ActionListener() {//18
            public void actionPerformed(ActionEvent e) {
                textArea.setFont(new Font("黑体", Font.PLAIN, FONT_SIZE[0]));
                //修改数据库
                SqlSetup.setSetup(""+FONT_SIZE[0], SqlSetup.READ_FONT_SIZE);
            }
        });

        z3.addActionListener(new ActionListener() {//20
            public void actionPerformed(ActionEvent e) {
                textArea.setFont(new Font("黑体", Font.PLAIN, FONT_SIZE[1]));
                //修改数据库
                SqlSetup.setSetup(""+FONT_SIZE[1], SqlSetup.READ_FONT_SIZE);
            }
        });

        z4.addActionListener(new ActionListener() {//22
            public void actionPerformed(ActionEvent e) {
                textArea.setFont(new Font("黑体", Font.PLAIN, FONT_SIZE[2]));
                //修改数据库
                SqlSetup.setSetup(""+FONT_SIZE[2], SqlSetup.READ_FONT_SIZE);
            }
        });

        z5.addActionListener(new ActionListener() {//24
            public void actionPerformed(ActionEvent e) {
                //修改数据库
                SqlSetup.setSetup(""+FONT_SIZE[3], SqlSetup.READ_FONT_SIZE);

                textArea.setFont(new Font("黑体", Font.PLAIN, FONT_SIZE[3]));
            }
        });

        //下载
        add1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("chapters"+ tabNameID +" - "+name+" 开始下载");
                /*new Thread(() -> {
                    httpNovel = new HttpNovel(url);
                    AddNovel.setAddList(httpNovel);
                });*/
                httpNovel = new HttpNovel(url);
                AddNovel.setAddList(httpNovel);
                NRTask nrTask = new NRTask(name, url);
                es.submit(nrTask);
            }
        });
        //暂停
        add2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (httpNovel != null)
                    httpNovel.addStatus = true;
            }
        });

//监听
        //上一章
        bt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (id >= 0)
                    id--;
                if(id >= 0){
                    Chapters s = list.get(id);
                    //刷新章节名
                    la.setText(s.getId()+"."+s.getNames());
                    //刷新文本域
                    textArea.setText(s.getContent());
                    textArea.setSelectionStart(0);
                    textArea.setSelectionEnd(0);
                    //更新阅读记录
                    ServiceDB.setReadingRecord(id,tabNameID);

                } else {
                    textArea.setText("\n\t已是第一章！");
                }
            }
        });
        //下一章
        bt2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int num = list.size();
                if (id < num)
                    id++;
                if(id < num){
                    Chapters s=list.get(id);
                    //刷新章节名
                    la.setText(s.getId()+"."+s.getNames());
                    //刷新文本域
                    textArea.setText(s.getContent());
                    textArea.setSelectionStart(0);
                    textArea.setSelectionEnd(0);

                    //更新阅读记录
                    ServiceDB.setReadingRecord(id,tabNameID);

                } else if (id >= num && id < sumChapter) {
                    Chapters httpChapters = getChapters(url, id);
                    if (httpChapters != null) {
                        if (id != num)
                            id++;
                        //刷新章节名
                        la.setText((id+1)+"."+httpChapters.getNames());
                        //刷新文本域
                        textArea.setText(httpChapters.getContent());
                        textArea.setSelectionStart(0);
                        textArea.setSelectionEnd(0);

                        //更新阅读记录
                        ServiceDB.setReadingRecord(id,tabNameID);
                    }

                } else {
                    textArea.setText("\n\t已是最后一章！");
                }
            }
        });

        //目录按钮
        btCatalog.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (exitCatalog == 0){
                    exitCatalog++;
                    //jp2.removeAll();
                    //jp2.repaint();
                    CatalogAdd(jp2, jp, tabNameID);
                    addCatalog(jp2, jsp, jsp2, la, textArea, tabNameID);
                    //jp2.revalidate();
                    //目录滚动条、章节内容滚动条
                    NovelTool.showHiddenComponent(jsp2,jsp);
                }else {
                    exitCatalog--;
                    NovelTool.showHiddenComponent(jsp,jsp2);
                }
            }
        });

        //窗口设置
        //运行时窗口在屏幕上的默认位置
        Dimension ds = Toolkit.getDefaultToolkit().getScreenSize();
        jf.setLocation(((int) ds.getWidth()-width) / 2, ((int) ds.getHeight()-height) / 2);

        jf.setResizable(true);//窗口缩放
        jf.setLayout(null);//窗口布局
        jf.getContentPane().setBackground(Color.WHITE);//窗口背景颜色
        Toolkit tk=Toolkit.getDefaultToolkit() ;//标题栏图标
        Image image=tk.createImage("img\\swing\\0.png");//导入栏图片
        jf.setIconImage(image);
        //jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置关闭规则
        jf.setVisible(true);//设置窗口可见

        jf.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                width = jf.getWidth();
                height = jf.getHeight();
                jp.setBounds(0,0, width,height-35);
                jsp.setBounds(1,45 , width-20,height-115);//468
                jsp2.setBounds(1,45 , width-20,height-115);
                panBtn.setBounds(width-340,0,300,45);
                la.setBounds(10,10 , width-360,30);
            }
        });

    }

    private Chapters getChapters(String url,int ind) {
        Chapters cts = new  HttpNovel().getSingle(url,"chapters"+ tabNameID,ind);
        return cts;
    }

    //章节跳转按钮
    private JButton getBtn(String btName, JPanel jPanel) {
        JButton btn = new JButton(btName);
        btn.setPreferredSize(new Dimension(80, 25));
        btn.setFocusPainted(false);//按钮去焦点
        btn.setFont(new Font("等线", Font.BOLD, 14));//文字设置
        btn.setBackground(Color.decode("#9093d6"));//按钮背景颜色e9ecf3
        jPanel.add(btn);

        return btn;
    }

    //目录面板
    private JPanel jPlCatalog(JPanel jp,String nid) {
        List<Chapters> list = new SqlChapters(nid).queryAllData();
        System.out.println("已下载章节："+list.size());
        JPanel jp2 = new JPanel();	//目录面板
        jp.add(jp2);
        jp2.setBackground(Color.decode("#ffffff"));//面板背景颜色
        jp2.setLayout(new FlowLayout(FlowLayout.LEFT));//布局
        jp2.setPreferredSize(new Dimension(715 , (((list.size()/3)+1) * 35) +5));//设置大小
        return jp2;
    }
    //重置目录高度
    private void CatalogAdd(JPanel jp2,JPanel jp,String nid) {
        List<Chapters> list = new SqlChapters(nid).queryAllData();
        jp2.setPreferredSize(new Dimension(715 , (((list.size()/3)+1) * 35) +5));//设置大小
    }

    //加载目录
    private void addCatalog(JPanel jp2, JScrollPane jsp, JScrollPane jsp2, JLabel la,JTextArea textArea,String nid) {
        List<Chapters> list = new SqlChapters(nid).queryAllData();
        //目录
        JPanel jp3[]=new JPanel[list.size()];
        for(int i=sumCatalog; i<list.size(); i++, sumCatalog++){
            Chapters c = list.get(i);

            jp3[i] = new JPanel();
            jp2.add(jp3[i]);
            jp3[i].setPreferredSize(new Dimension(232, 30));//设置JPanel的大小
            jp3[i].setLayout(null);//布局new FlowLayout(FlowLayout.LEFT)
            jp3[i].setBackground(Color.decode("#e4e0d7"));//组件背景颜色

            //目录
            JButton ml = new JButton(c.getId()+"."+c.getNames());
            ml.setFont(new Font("等线", Font.BOLD, 14));//文字设置
            ml.setBackground(Color.decode("#e4e0d7"));//组件背景颜色
            ml.setBounds(0,0 , 232,30);
            ml.setFocusPainted(false);//按钮去焦点
            ml.setBorderPainted(false);//去按钮边框
            jp3[i].add(ml);

            ml.addMouseListener(new MouseListener(){//目录
                public void mouseClicked(MouseEvent e) {//切换页面
                    jsp2.setVisible(false);//隐藏
                    jsp.setVisible(true);//显示false

                    //处理文本
                    String gg = ml.getText();
                    gg=gg.substring(0,gg.indexOf("."));
                    id = Integer.valueOf(gg);//String-int

                    Chapters s = list.get(--id);
                    //刷新章节名
                    la.setText(s.getId()+"."+c.getNames());
                    //刷新文本域
                    textArea.setText(s.getContent());
                    textArea.setSelectionStart(0);
                    textArea.setSelectionEnd(0);

                    //更新阅读记录
                    ServiceDB.setReadingRecord(id,tabNameID);

                }
                public void mouseEntered(MouseEvent e) {// 处理鼠标移入
                    ml.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); //鼠标形状
                    ml.setBackground(Color.decode("#cfc8c0"));//组件背景颜色
                }public void mouseExited(MouseEvent e) {
                    // 处理鼠标离开
                    ml.setBackground(Color.decode("#e4e0d7"));//组件背景颜色
                }public void mousePressed(MouseEvent e) {
                    // 处理鼠标按下
                }public void mouseReleased(MouseEvent e) {
                    // 处理鼠标释放
                }
            });
        }
    }

    class NRTask implements Runnable {

        private String name;
        private String url;

        public NRTask(String name, String url) {
            this.name = name;
            this.url = url;
        }

        @Override
        public void run() {
            httpNovel.getChapter(url,"chapters"+ tabNameID);
        }
    }

}



