package com.hzx.swing.util;

import com.hzx.entity.MorePageStore;
import com.hzx.entity.StoreHot;
import com.hzx.entity.StoreRecommend;
import com.hzx.entity.Types;
import com.hzx.swing.Novel;
import com.hzx.web.JsoupStore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class NovelStore extends Novel {

    //加载状态
    public static boolean exitLoading = false;
    //页面下标
    private static int typePageSize = 1;
    //页面状态
    private static boolean[] state = new boolean[8];

    //进度条
    private static JProgressBar pr = new JProgressBar();
    //小说显示面板
    private static JPanel jplHot = new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));
    //商店
    private static JPanel jplStore = new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));
    //类型
    private static JPanel jPlType = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
    //类型更新列表
    private static JPanel book = new JPanel(new FlowLayout(FlowLayout.CENTER,10,20));

    public JPanel getStore(JFrame jf) {
        //商店
        //JPanel jplStore = new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));
        jplStore.setBounds(130,50, LOCAL_WHILE,LOCAL_HEIGHT);
        jplStore.setBackground(Color.decode("#f3f6fc"));//背景颜色
        jplStore.setVisible(false);//隐藏
        jf.add(jplStore);

        //类型
        jplStore.add(jPlType);
        jPlType.setBackground(Color.decode("#e7c1c3"));//背景颜色
        jPlType.setPreferredSize(new Dimension(840,50));

        //进度条
        jplStore.add(pr);
        pr.setPreferredSize(new Dimension(80,30));
        //pr.setBounds(190,410, 300,30);
        pr.setFont(new Font("等线", Font.BOLD, 14));//字体，字体大小

        //小说面板
        jplStore.add(jplHot);
        jplHot.setBackground(Color.decode("#f9e9e6"));//背景颜色
        jplHot.setPreferredSize(new Dimension(930,1910));
        //NovelStore.hotNovel();
        //滚动条
        JScrollPane jsp4 = new JScrollPane(jplHot);
        jsp4.getVerticalScrollBar().setUnitIncrement(40);
        jsp4.setPreferredSize(new Dimension(LOCAL_WHILE-10,LOCAL_HEIGHT-70));
        jplStore.add(jsp4);

        return jplStore;
    }

    //加载首页内容
    public synchronized static void loadData() {
        //类型按钮c
        NovelStore.addBtType(jPlType);
        //类型内容
        hotNovel("http://www.shuquge.com/");
        //精品推荐
        addJplRem(jplStore);
    }

    //加载类型内容
    public synchronized static void loadType(String url) {
        hotNovel(url);
        typeTab(url);
    }

    //热门小说 图片、书名、作者、简介(热门、类型)
    private static void hotNovel(String url) {
        ArrayList<StoreHot> arrHot = JsoupStore.hot(url);
        exitLoading = true;
        System.err.println("加载 "+exitLoading);
        for (StoreHot sh :
                arrHot) {
            JPanel book = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
            jplHot.add(book);
            book.setBackground(Color.decode("#ffffff"));//背景颜色
            book.setPreferredSize(new Dimension(450,200));

            //图片
            ImageIcon icon = new ImageIcon(sh.getImg());//获取图片
            icon.setImage(icon.getImage().getScaledInstance(150,200,Image.SCALE_DEFAULT));
            JLabel laImg = new JLabel(icon);
            book.add(laImg);
            laImg.setPreferredSize(new Dimension(150,200));//文本图片大小

            //其他信息
            JPanel bookId = new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));
            bookId.setPreferredSize(new Dimension(300,200));
            bookId.setBackground(Color.decode("#ffffff"));
            book.add(bookId);
            //书名
            JLabel laName = new JLabel(sh.getBookName());
            bookId.add(laName);
            laName.setPreferredSize(new Dimension(290,20));
            laName.setFont(new Font("华文新魏", Font.BOLD, 20));//字体，字体大小

            //作者 Author
            JLabel laAuthor = new JLabel(sh.getAuthor());
            bookId.add(laAuthor);
            laAuthor.setPreferredSize(new Dimension(270,20));

            //简介 Briefing
            JTextArea taBriefing = new JTextArea (sh.getBriefing());
            taBriefing.setLineWrap(true);
            taBriefing.setPreferredSize(new Dimension(285,115));
            taBriefing.setForeground(Color.decode("#686868"));
            bookId.add(taBriefing);

            //字体设置
            NovelTool.fontTools(16,laAuthor,taBriefing);

            //图片
            laImg.addMouseListener(new MouseListener(){
                public void mouseClicked(MouseEvent e) {
                    Novel.getData(sh.getUrl());
                }
                public void mouseEntered(MouseEvent e) {// 处理鼠标移入
                    laImg.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));//鼠标形状
                }public void mouseExited(MouseEvent e) {
                    // 处理鼠标离开
                }public void mousePressed(MouseEvent e) {
                    // 处理鼠标按下
                }public void mouseReleased(MouseEvent e) {
                    // 处理鼠标释放
                }
            });
        }
    }

    //精品推荐 jplRecommend
    private static void addJplRem(JPanel jplStore) {
        ArrayList<StoreRecommend> arrRmd = JsoupStore.recommend();
        for (StoreRecommend sr :
                arrRmd) {
            JPanel jplRecommend = new JPanel(new FlowLayout(FlowLayout.LEFT));
            jplHot.add(jplRecommend);
            jplRecommend.setBackground(Color.decode("#ffffff"));//背景颜色
            jplRecommend.setPreferredSize(new Dimension(450, 480));
            //类型标题
            JLabel title = new JLabel(" "+sr.getH2());
            title.setOpaque(true);
            title.setBackground(Color.decode("#9e8f53"));
            title.setFont(new Font("黑体", Font.BOLD, 24));
            title.setForeground(Color.decode("#ffffff"));
            title.setPreferredSize(new Dimension(440, 30));
            jplRecommend.add(title);
            //图片
            ImageIcon icon = new ImageIcon(sr.getImg());//获取图片
            icon.setImage(icon.getImage().getScaledInstance(105, 140, Image.SCALE_DEFAULT));//强制图片大小
            JLabel laImg = new JLabel(icon);
            laImg.setPreferredSize(new Dimension(105, 140));
            jplRecommend.add(laImg);

            //书名、简介 面板
            JPanel jplBookId = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 15));
            jplBookId.setPreferredSize(new Dimension(320, 200));
            jplBookId.setBackground(Color.decode("#ffffff"));
            jplRecommend.add(jplBookId);
            //书名
            JLabel laName = new JLabel(sr.getBookName());
            laName.setPreferredSize(new Dimension(300, 20));
            laName.setFont(new Font("等线", Font.BOLD, 20));//字体，字体大小
            jplBookId.add(laName);
            //简介
            JTextArea taBriefing = new JTextArea(sr.getBriefing());
            taBriefing.setLineWrap(true);
            taBriefing.setPreferredSize(new Dimension(300, 116));
            taBriefing.setForeground(Color.decode("#686868"));
            NovelTool.fontTools(16,taBriefing);
            jplBookId.add(taBriefing);

            //其他书列表
            JPanel books = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 15));
            books.setPreferredSize(new Dimension(440, 230));
            books.setBackground(Color.decode("#fdf9ee"));
            jplRecommend.add(books);
            //列表书名
            for (int i = 0; i < 12; i++) {
                JLabel laName2 = new JLabel(sr.getBookNames()[i]);
                laName2.setPreferredSize(new Dimension(210, 20));
                NovelTool.fontTools(16,laName2);
                books.add(laName2);

                int finalI = i;
                laName2.addMouseListener(new MouseListener(){
                    public void mouseClicked(MouseEvent e) {
                        Novel.getData(sr.getUrls()[finalI]);
                    }
                    public void mouseEntered(MouseEvent e) {// 处理鼠标移入
                        laName2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));//鼠标形状
                    }public void mouseExited(MouseEvent e) {
                        // 处理鼠标离开
                    }public void mousePressed(MouseEvent e) {
                        // 处理鼠标按下
                    }public void mouseReleased(MouseEvent e) {
                        // 处理鼠标释放
                    }
                });
            }

            //推荐书籍图片
            laImg.addMouseListener(new MouseListener(){
                public void mouseClicked(MouseEvent e) {
                    Novel.getData(sr.getUrl());
                }
                public void mouseEntered(MouseEvent e) {// 处理鼠标移入
                    laImg.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));//鼠标形状
                }public void mouseExited(MouseEvent e) {
                    // 处理鼠标离开
                }public void mousePressed(MouseEvent e) {
                    // 处理鼠标按下
                }public void mouseReleased(MouseEvent e) {
                    // 处理鼠标释放
                }
            });

        }
    }

    //类型-近期更新列表
    private static void typeTab(String url) {
        //JPanel book = new JPanel(new FlowLayout(FlowLayout.CENTER,10,20));
        book.setBackground(Color.decode("#ffffff"));//背景颜色
        jplHot.setPreferredSize(new Dimension(930,2050));
        book.setPreferredSize(new Dimension(910,1400));
        jplHot.add(book);
        //列表标题
        JLabel titlec = new JLabel("近期更新列表");
        titlec.setPreferredSize(new Dimension(890,40));
        titlec.setVerticalAlignment(SwingConstants.CENTER);
        titlec.setHorizontalAlignment(SwingConstants.CENTER);
        titlec.setOpaque(true);
        titlec.setBackground(Color.decode("#9e8f53"));
        titlec.setFont(new Font("黑体", Font.BOLD, 20));
        titlec.setForeground(Color.decode("#ffffff"));
        book.add(titlec);

        ArrayList<Types> types2 = JsoupStore.getTypes2(url);
        for (Types ty :
                types2) {
            //类型
            JLabel laType = new JLabel(ty.getType());
            laType.setPreferredSize(new Dimension(100,20));
            book.add(laType);
            //书名
            JLabel name = new JLabel(ty.getName());
            name.setPreferredSize(new Dimension(200,20));
            book.add(name);
            //最新章节
            JLabel latest = new JLabel(ty.getLatest());
            latest.setPreferredSize(new Dimension(320,20));
            book.add(latest);
            //作者
            JLabel laAuthor = new JLabel(ty.getAuthor());
            laAuthor.setPreferredSize(new Dimension(150,20));
            book.add(laAuthor);
            //更新时间
            JLabel laTime = new JLabel(ty.getTime());
            laTime.setPreferredSize(new Dimension(70,20));
            book.add(laTime);
            NovelTool.fontTools(18,name,laAuthor,laType,laType,laTime,latest);

            name.addMouseListener(new MouseListener(){
                public void mouseClicked(MouseEvent e) {
                    Novel.getData(ty.getUrl());
                }
                public void mouseEntered(MouseEvent e) {// 处理鼠标移入
                    name.setForeground(Color.RED );
                    name.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }public void mouseExited(MouseEvent e) {
                    // 处理鼠标离开
                    name.setForeground(Color.BLACK);
                }public void mousePressed(MouseEvent e) {
                    // 处理鼠标按下
                }public void mouseReleased(MouseEvent e) {
                    // 处理鼠标释放
                }
            });
        }

        //获取数据
        MorePageStore mps = JsoupStore.getMorePageStore(url);
        //页面选择
        JPanel panelPageSelect = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));
        panelPageSelect.setPreferredSize(new Dimension(800,100));
        panelPageSelect.setBackground(Color.WHITE);
        book.add(panelPageSelect);
        //首页
        JButton bt_head = new JButton("首页");
        bt_head.setPreferredSize(new Dimension(70,30));
        panelPageSelect.add(bt_head);
        //上一页
        JButton bt_prev = new JButton("上一页");
        bt_prev.setPreferredSize(new Dimension(80,30));
        if (mps.getPrev() != null)
            panelPageSelect.add(bt_prev);

        //页面跳转
        JPanel jplSkip = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        jplSkip.setPreferredSize(new Dimension(100,90));
        jplSkip.setBackground(Color.WHITE);
        panelPageSelect.add(jplSkip);
        //输入框
        JTextField addText = new JTextField();
        addText.setText(typePageSize+"");
        addText.setPreferredSize(new Dimension(60,30));
        addText.setHorizontalAlignment(SwingConstants.CENTER);
        addText.setFont(new Font("黑体", Font.BOLD, 18));
        jplSkip.add(addText);
        //跳转按钮
        JButton bt_skip = new JButton("跳转");
        bt_skip.setPreferredSize(new Dimension(60,30));
        jplSkip.add(bt_skip);

        //下一页
        JButton bt_next = new JButton("下一页");
        bt_next.setPreferredSize(new Dimension(80,30));
        if (mps.getNext() != null)
            panelPageSelect.add(bt_next);
        //尾页
        JButton bt_tail = new JButton("尾页 - "+getUrl(2, mps.getTail()));
        bt_tail.setPreferredSize(new Dimension(100,30));
        panelPageSelect.add(bt_tail);

        bt_head.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadTypeTab(book,mps.getHead());
            }
        });

        bt_tail.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadTypeTab(book,mps.getTail());
            }
        });

        bt_prev.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadTypeTab(book,mps.getPrev());
            }
        });

        bt_next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadTypeTab(book,mps.getNext());
            }
        });

        //跳转
        bt_skip.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int ind = Integer.valueOf(addText.getText());
                if (ind >0 && ind != typePageSize && ind <= Integer.valueOf(getUrl(2, mps.getTail()))) {
                    String typePage = getUrl(1, mps.getTail());
                    String text = addText.getText();
                    String urlPage = "http://www.shuquge.com//category/"+typePage+"_"+text+".html";
                    loadTypeTab(book,urlPage);
                }
            }
        });
    }

    //重写加载 类型-近期更新列表
    private static void loadTypeTab(JPanel book, String url) {
        typePageSize = Integer.valueOf(getUrl(2, url));
        book.removeAll();
        book.repaint();
        typeTab(url);
        book.revalidate();
    }

    /**
     *  字符串剪切
     *  i = 1 返回页面类型编号
     *  i = 2 返回页面下标
     *  url 需要剪切的字符串
     * */
    private static String getUrl(int i, String url) {
        int ind = url.lastIndexOf('_');
        String typeUrl = null;
        if (i == 1) {
            typeUrl = url.substring(url.lastIndexOf('/')+1, ind);
        }
        if (i == 2) {
            typeUrl = url.substring(ind+1, url.lastIndexOf('.'));
        }
        System.out.println(typeUrl);

        return typeUrl;
    }

    //类型按钮
    private static void addBtType(JPanel jPanel) {
        String[] name = {"玄幻","武侠","都市","历史","侦探","网游","科幻"};
        String[] urls = JsoupStore.type();
        for (int i=0; i<name.length; i++) {
            JButton bt = new JButton(name[i]);
            bt.setPreferredSize(new Dimension(120,50));//按钮大小
            bt.setFont(new Font("等线", Font.BOLD, 20));
            bt.setBackground(Color.decode("#e7c1c3"));//背景颜色
            bt.setForeground(Color.decode("#31353d"));//字体颜色
            bt.setFocusPainted(false);//去焦点
            bt.setBorderPainted(false);//去按钮边框
            jPanel.add(bt);

            int finalI = i;
            bt.addMouseListener(new MouseListener(){
                public void mouseClicked(MouseEvent e) {
                    new Thread(()->{
                        exitLoading = false;
                        //progress();
                        jplHot.removeAll();
                        showHiddenComponent(jPlLoading, jplStore);//加载、书城
                        while (true) {
                            System.out.print("");
                            if (NovelStore.exitLoading) {
                                showHiddenComponent(jplStore, jPlLoading);//书城、加载
                                break;
                            }
                        }
                    }).start();

                    new Thread(()->{
                        loadType(urls[finalI]);
                        jplHot.revalidate();
                    }).start();
                }
                public void mouseEntered(MouseEvent e) {
                    // 处理鼠标移入
                    bt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); //鼠标形状
                    bt.setBackground(Color.decode("#f4e5e5"));//背景颜色
                }public void mouseExited(MouseEvent e) {
                    // 处理鼠标离开
                    bt.setBackground(Color.decode("#e7c1c3"));//背景颜色
                }public void mousePressed(MouseEvent e) {
                    // 处理鼠标按下
                }public void mouseReleased(MouseEvent e) {
                    // 处理鼠标释放
                }
            });
        }
    }

    //进度条
    public static boolean pgs = false;
    public static void progress() {
        pr.setIndeterminate(true);//不确定的进度条样式
        pr.setStringPainted(true);//设置进度条显示提示信息
        pr.setString("正在加载...");//设置提示信息
        new Thread(()->{
            try {
                Thread.sleep(15000);
                if (pgs) {
                    pr.setIndeterminate(false);//确定的进度条样式
                    pr.setString("加载失败！");
                }else {
                    pgs = false;
                    pr.setVisible(false);
                }

            }catch(Exception a) {
                a.printStackTrace();
            }
        }).start();
    }

}
