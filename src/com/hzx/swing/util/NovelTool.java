package com.hzx.swing.util;

import com.hzx.entity.Details;
import com.hzx.entity.Novels;
import com.hzx.dao.SqlNovelTab;
import com.hzx.swing.Novel;
import com.hzx.web.JsoupStore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class NovelTool {

    //窗口大小
    public static final int ALL_WHILE = 1100;
    public static final int ALL_HEIGHT = 750;
    public static final int LOCAL_WHILE = 965;
    public static final int LOCAL_HEIGHT = 665;

    //颜色
    public static final String[] color(int i) {
        String[][] s = {
                //顶部面板,	西部面板,   西部字体颜色,   搜索框,   书架面板,   书籍背景 , 字体颜色
                {"#e94c57", "#e7c1c3", "#31353d", "#e94c57","#f3f6fc","#ffffff","#000000"},//红色
                {"#3c3f41", "#6c6a6a", "#550303", "#7c6a6a","#7f7c7c","#686868","#ffffff"},//黑色
                {"#7376ad", "#a9a9d7", "#dbdbf3", "#dadaf9"},//蓝色
                {"#f0bc59", "#edc372", "#fdf5e6", "#fdf5e6"},//黄色
        };
        return s[i];
    }

    //导航按钮
    public static JButton novelNavigation(JPanel jPanel, String name) {
        JButton bt = new JButton(name);
        jPanel.add(bt);
        bt.setPreferredSize(new Dimension(130,50));//按钮大小
        bt.setFont(new Font("等线", Font.BOLD, 20));
        bt.setBackground(Color.decode("#e7c1c3"));//背景颜色
        bt.setForeground(Color.decode("#31353d"));//字体颜色
        bt.setFocusPainted(false);//去焦点
        bt.setBorderPainted(false);//去按钮边框
        return bt;
    }

    //显示、隐藏
    public static final void showHiddenComponent (Component component, Component ... components) {
        if (component != null)
            component.setVisible(true);
        //隐藏
        for (Component c :
                components) {
            if (c != null)
                c.setVisible(false);
        }
    }

    //加载书籍
    private NovelRead novelRead = null;
    public final NovelRead bookshelf(JPanel jPanel,String colors,String fontColor) {
        List<Novels> list = new SqlNovelTab().queryAllNovel();//获取数据库文件
        JPanel jplBook[]=new JPanel[list.size()];

        int sunBook = ((list.size()%3 ==0 ? 0: 1) + (list.size()/3)) * 210 + 10;
        jPanel.setPreferredSize(new Dimension(LOCAL_WHILE-50, sunBook ));//大小

        for(int i=0; i<list.size();i++){
            Novels s = list.get(i);
            jplBook[i]=new JPanel();
            jplBook[i].setPreferredSize(new Dimension(300, 200));//设置JPanel的大小
            jplBook[i].setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
            jPanel.add(jplBook[i]);

            //图片
            ImageIcon icon = new ImageIcon(s.getImg());//获取图片
            icon.setImage(icon.getImage().getScaledInstance(150,200,Image.SCALE_DEFAULT));//强制图片大小
            JLabel laImg = new JLabel(icon);
            laImg.setPreferredSize(new Dimension(150,200));//文本图片大小
            jplBook[i].add(laImg);

            //其他信息面板
            JPanel jplBookId = new JPanel(new FlowLayout(FlowLayout.LEFT,10,15));
            jplBookId.setPreferredSize(new Dimension(150,200));
            jplBookId.setBackground(Color.decode(colors));
            jplBook[i].add(jplBookId);
            //书名
            JLabel laName = new JLabel(s.getBookName());
            jplBookId.add(laName);
            laName.setPreferredSize(new Dimension(140,20));
            laName.setFont(new Font("等线", Font.BOLD, 18));//字体，字体大小
            //作者
            JLabel laAuthor = new JLabel(s.getAuthor());
            jplBookId.add(laAuthor);
            laAuthor.setPreferredSize(new Dimension(140,20));
            //字数
            JLabel laSum = new JLabel("字数："+(double)(Integer.valueOf(s.getWordsNumber())/1000)/10+" 万");
            laSum.setPreferredSize(new Dimension(140,20));
            jplBookId.add(laSum);
            //更新时间
            JLabel laTime = new JLabel(s.getDate());
            laTime.setPreferredSize(new Dimension(140,20));
            jplBookId.add(laTime);
            //删除书籍
            JLabel laDelete = new JLabel(new ImageIcon("img\\swing\\delete_16px_1.png"));
            laDelete.setPreferredSize(new Dimension(20,20));
            jplBookId.add(laDelete);
            //详情
            JLabel more = new JLabel(new ImageIcon("img\\swing\\more_16px_1.png"));
            more.setPreferredSize(new Dimension(20,20));
            jplBookId.add(more);

            //字体颜色
            fontColor(fontColor, laName,laAuthor,laSum,laTime,laDelete);

            //字体设置
            fontTools(16, laAuthor,laSum,laTime,laDelete);

            //ID
            JLabel laId = new JLabel("ID:"+s.getId());
            jplBook[i].add(laId);

            //图片
            laImg.addMouseListener(new MouseListener(){
                public void mouseClicked(MouseEvent e) {
                    String nid = laId.getText();
                    nid=nid.substring(nid.indexOf(":")+1);
                    novelRead = new NovelRead(nid,s.getSize());
                    novelRead.read(s.getBookName(), s.getUrl());//打开小说窗口
                }
                public void mouseEntered(MouseEvent e) {// 处理鼠标移入
                    laImg.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); //鼠标形状

                }public void mouseExited(MouseEvent e) {
                    // 处理鼠标离开
                }public void mousePressed(MouseEvent e) {
                    // 处理鼠标按下
                }public void mouseReleased(MouseEvent e) {
                    // 处理鼠标释放
                }
            });

            //删除
            laDelete.addMouseListener(new MouseListener(){
                public void mouseClicked(MouseEvent e) {
                    String nid = laId.getText();
                    nid=nid.substring(nid.indexOf(":")+1);//截取
                    int n = Integer.parseInt(nid);//String转int
                    System.out.println(n);
                    new True(n, jPanel, novelRead,s.getBookName());
                }
                public void mouseEntered(MouseEvent e) {// 处理鼠标移入
                    //鼠标形状
                    laDelete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }public void mouseExited(MouseEvent e) {
                    // 处理鼠标离开
                }public void mousePressed(MouseEvent e) {
                    // 处理鼠标按下
                }public void mouseReleased(MouseEvent e) {
                    // 处理鼠标释放
                }
            });

            //详情
            more.addMouseListener(new MouseListener(){
                public void mouseClicked(MouseEvent e) {
                    /*new Thread(() -> {
                        Novel.getData(s.getUrl());
                    }).start();*/
                }
                public void mouseEntered(MouseEvent e) {// 处理鼠标移入
                    //鼠标形状
                    more.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }public void mouseExited(MouseEvent e) {
                    // 处理鼠标离开
                }public void mousePressed(MouseEvent e) {
                    // 处理鼠标按下
                }public void mouseReleased(MouseEvent e) {
                    // 处理鼠标释放
                }
            });
        }
        return novelRead;
    }

    //详细信息
    public static boolean exdd = false;
    public static JPanel getDetails(JFrame jf,String url) {
        //详细信息
        JPanel jplDetails = new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));
        jplDetails.setBounds(130,50, LOCAL_WHILE,LOCAL_HEIGHT);
        jplDetails.setBackground(Color.decode("#f3f6fc"));//背景颜色
        jplDetails.setVisible(true);
        jf.add(jplDetails);
        //jplDetails.repaint(); //重绘此组件。

        Details data = JsoupStore.getData(url); //获取数据

        //图片
        ImageIcon icon = new ImageIcon(data.getImg());//获取图片
        icon.setImage(icon.getImage().getScaledInstance(230,300,Image.SCALE_DEFAULT));//强制图片大小
        JLabel laImg = new JLabel(icon);
        jplDetails.add(laImg);
        laImg.setPreferredSize(new Dimension(230,300));//文本图片大小

        //其他信息
        JPanel jplBookId = new JPanel(new FlowLayout(FlowLayout.LEFT,10,15));
        jplBookId.setPreferredSize(new Dimension(640,300));
        jplBookId.setBackground(Color.decode("#ffffff"));
        jplDetails.add(jplBookId);
        //书名
        JLabel laName = new JLabel(data.getName());
        laName.setPreferredSize(new Dimension(500,20));
        laName.setFont(new Font("等线", Font.BOLD, 22));//字体，字体大小
        jplBookId.add(laName);
        //添加图标
        icon = new ImageIcon("img\\swing\\add_48px_1.png");//获取图片
        JLabel add = new JLabel(icon);
        add.setPreferredSize(new Dimension(48,48));
        jplBookId.add(add);

        add.addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent e) {
                new AddNovel().Progress(url);
            }
            public void mouseEntered(MouseEvent e) {// 处理鼠标移入
                add.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));//鼠标形状
            }public void mouseExited(MouseEvent e) {
                // 处理鼠标离开
            }public void mousePressed(MouseEvent e) {
                // 处理鼠标按下
            }public void mouseReleased(MouseEvent e) {
                // 处理鼠标释放
            }
        });

        //作者
        JLabel laAuthor = new JLabel(data.getAuthor());
        laAuthor.setPreferredSize(new Dimension(200,30));
        jplBookId.add(laAuthor);
        //类型
        JLabel laType = new JLabel(data.getType());
        laType.setPreferredSize(new Dimension(150,20));
        jplBookId.add(laType);
        //状态
        JLabel laState = new JLabel(data.getState());
        laState.setPreferredSize(new Dimension(200,20));
        jplBookId.add(laState);
        //更新时间
        JLabel laTime = new JLabel(data.getTime());
        laTime.setPreferredSize(new Dimension(360,20));
        jplBookId.add(laTime);
        //字数
        JLabel laSum = new JLabel("字数："+(double)(Integer.valueOf(data.getSum())/1000)/10+" 万");
        laSum.setPreferredSize(new Dimension(200,20));
        jplBookId.add(laSum);
        //最新章节
        JLabel latest = new JLabel(data.getLatest());
        latest.setPreferredSize(new Dimension(500,20));
        jplBookId.add(latest);
        //简介 Briefing
        JTextArea taBriefing = new JTextArea (data.getBrief());
        taBriefing.setLineWrap(true);
        taBriefing.setPreferredSize(new Dimension(600,120));
        taBriefing.setForeground(Color.decode("#686868"));
        jplBookId.add(taBriefing);
        fontTools(16,taBriefing);
        fontTools(18,laAuthor,laType,laType,laState,laTime,laSum,latest);

        //返回面板
        JPanel jplExit = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
        jplExit.setPreferredSize(new Dimension(50,300));
        jplExit.setBackground(Color.decode("#f3f6fc"));
        jplDetails.add(jplExit);
        //返回
        icon = new ImageIcon("img\\swing\\exit_48px_1.png");//获取图片
        JButton btExit = new JButton(icon);
        btExit.setPreferredSize(new Dimension(48,48));
        btExit.setFont(new Font("等线", Font.BOLD, 20));
        btExit.setBackground(Color.decode("#f3f6fc"));//背景颜色
        btExit.setFocusPainted(false);//去焦点
        btExit.setBorderPainted(false);//去按钮边框
        jplExit.add(btExit);

        //退出
        btExit.addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent e) {
                NovelTool.showHiddenComponent(null,jplDetails);//隐藏
                Novel.showStore();
                jplDetails.removeAll(); //从此容器中移除所有组件。
                exdd = false;
            }
            public void mouseEntered(MouseEvent e) {// 处理鼠标移入
                btExit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));//鼠标形状
            }public void mouseExited(MouseEvent e) {
                // 处理鼠标离开
            }public void mousePressed(MouseEvent e) {
                // 处理鼠标按下
            }public void mouseReleased(MouseEvent e) {
                // 处理鼠标释放
            }
        });

        //目录面板
        JPanel jplCatalogue = new JPanel(new FlowLayout(FlowLayout.LEFT,0,10));
        jplCatalogue.setPreferredSize(new Dimension(940,320));
        jplCatalogue.setBackground(Color.decode("#fdf9ee"));
        jplDetails.add(jplCatalogue);
        //目录标题
        JLabel titlec = new JLabel("《"+data.getName()+"》 最新章节");
        titlec.setPreferredSize(new Dimension(940,50));
        titlec.setHorizontalAlignment(SwingConstants.CENTER);
        titlec.setOpaque(true);
        titlec.setBackground(Color.decode("#9e8f53"));
        titlec.setFont(new Font("黑体", Font.BOLD, 20));
        titlec.setForeground(Color.decode("#ffffff"));
        jplCatalogue.add(titlec);
        //目录内容
        for (String s :
                data.getLatests()) {
            JLabel catalogue = new JLabel("  "+s);
            catalogue.setPreferredSize(new Dimension(440,30));
            fontTools(18,catalogue);
            jplCatalogue.add(catalogue);
        }
        return jplDetails;
    }

    //文字设置
    public static void fontColor(String colors,JComponent ... text) {
        for (JComponent num:
                text) {
            num.setForeground(Color.decode(colors));
        }
    }

    //文字设置
    public static void fontTools(int size, JComponent ... text) {
        for (JComponent num:
             text) {
            num.setFont(new Font("黑体", Font.PLAIN, size));//字体,样式,字体大小
        }
    }

    //加载面板
    public static JPanel getLoading(JFrame jf) {
        JPanel jPlLoading = new JPanel(new FlowLayout(FlowLayout.CENTER,200,100));
        jPlLoading.setBackground(Color.decode("#fffffff"));//背景颜色
        jPlLoading.setBounds(130,50, LOCAL_WHILE,LOCAL_HEIGHT);
        jPlLoading.setVisible(false);
        jf.add(jPlLoading);
        //图片
        ImageIcon icon = new ImageIcon("img\\swing\\loading5.gif");//获取图片
        JLabel laImg = new JLabel(icon);
        jPlLoading.add(laImg);
        laImg.setFont(new Font("等线", Font.BOLD, 56));
        laImg.setPreferredSize(new Dimension(400,300));
        return jPlLoading;
    }

}
