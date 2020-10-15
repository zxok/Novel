package com.hzx.swing;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.hzx.cfg.XML_Dom4J;
import com.hzx.service.ServiceDB;
import com.hzx.dao.SqlSetup;
import com.hzx.swing.util.*;

public class Novel extends NovelTool {

    private static JFrame jf = new JFrame("TT小说");//窗口
    //顶部面板
    JPanel jpa = new JPanel(new FlowLayout(FlowLayout.LEFT,20,10));
    //菜单面板
    JPanel jpaMenu = new JPanel();

    //书架
    private static JPanel jpaBookrack = new JPanel();

    //书城面板
    private static JPanel jpaStore = new NovelStore().getStore(jf);

    //详细信息
    private static JPanel jplDetails;

    //加载面板
    public static JPanel jPlLoading = null;

    //加载书架
    private static NovelRead novelRead = null;

    static int sz[] = new SqlSetup().getSetup();//获取数据库数据
    //public static String[] str = zt(sz[0]);
    public static String[] str = NovelTool.color(sz[0]);

    public void Novelz() {
        jf.setSize(ALL_WHILE,ALL_HEIGHT);//窗口宽,高
        Dimension ds = Toolkit.getDefaultToolkit().getScreenSize();
        jf.setLocation(((int) ds.getWidth()-ALL_WHILE) / 2, ((int) ds.getHeight()-ALL_HEIGHT) / 2);
        //jf.setLocation(470, 150);//运行时窗口在屏幕上的默认位置
        jf.setResizable(false);	//窗口缩放
        jf.setLayout(null);//布局

        //顶部面板
        jpa.setBounds(0,0 , ALL_WHILE,50);//组件位置、宽高(x,y,w,h)
        jpa.setBackground(Color.decode(str[0]));//背景颜色9093d6"#e94c57"
        jf.add(jpa);
        //TT小说
        JLabel la = new JLabel("TT小说");//文本
        la.setPreferredSize(new Dimension(150,30));
        la.setFont(new Font("华文行楷", Font.BOLD, 30));//字体，大小
        la.setForeground(Color.decode("#ffffff"));//字体颜色
        jpa.add(la);
        //刷新
        ImageIcon icon = new ImageIcon("img\\swing\\refresh.png");
        icon.setImage(icon.getImage().getScaledInstance(40,40,Image.SCALE_DEFAULT));
        JLabel update = new JLabel(icon);
        update.setPreferredSize(new Dimension(30,30));
        jpa.add(update);

        //搜索面板
        JPanel jplSeek = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
        jplSeek.setPreferredSize(new Dimension(250,30));
        jplSeek.setOpaque(false);//透明
        jpa.add(jplSeek);
        //搜索框
        JTextField jte = new JTextField();
        jte.setPreferredSize(new Dimension(150,30));
        jte.setOpaque(false);//透明
        jte.setBackground(Color.decode(str[3]));//背景颜色
        jplSeek.add(jte);
        //搜索
        JButton btSeek = new JButton("搜索");
        btSeek.setPreferredSize(new Dimension(70,30));
        btSeek.setFont(new Font("等线", Font.PLAIN, 16));
        btSeek.setForeground(Color.decode("#3d424c"));//字体颜色
        btSeek.setBackground(Color.decode("#ffffff"));//背景颜色
        btSeek.setFocusPainted(false);//去焦点
        btSeek.setBorderPainted(false);//去按钮边框
        jplSeek.add(btSeek);

        //日间
        JLabel textDay = new JLabel(new ImageIcon("img\\swing\\3.png"));
        textDay.setPreferredSize(new Dimension(34,34));
        jpa.add(textDay);
        //夜间
        JLabel testNight = new JLabel(new ImageIcon("img\\swing\\4.png"));
        testNight.setPreferredSize(new Dimension(34,34));
        jpa.add(testNight);
        //用户头像
        JLabel userImg = new JLabel(new ImageIcon("img\\swing\\01.jpg"));
        userImg.setPreferredSize(new Dimension(34,34));
        jpa.add(userImg);
        //用户名
        JLabel userName = new JLabel("未登录...");
        userName.setPreferredSize(new Dimension(100,20));
        userName.setFont(new Font("幼圆", Font.BOLD, 16));
        userName.setForeground(Color.decode("#eae7e7"));//字体颜色 cdcdcd
        jpa.add(userName);
        //设置
        JLabel setup = new JLabel(new ImageIcon("img\\swing\\setup_32px_3.png"));
        setup.setPreferredSize(new Dimension(32,32));
        jpa.add(setup);
        setup.addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent e) {
                new Setup();
            }
            public void mouseEntered(MouseEvent e) {// 处理鼠标移入
                setup.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));//鼠标形状
            }public void mouseExited(MouseEvent e) {
                // 处理鼠标离开
            }public void mousePressed(MouseEvent e) {
                // 处理鼠标按下
            }public void mouseReleased(MouseEvent e) {
                // 处理鼠标释放
            }
        });


        //菜单面板
        jf.add(jpaMenu);
        jpaMenu.setBounds(0,50 , 130,LOCAL_HEIGHT);
        jpaMenu.setLayout(new FlowLayout());//布局
        jpaMenu.setBackground(Color.decode(str[1]));//背景颜色ced4e0"#e7c1c3"
        JButton bt = NovelTool.novelNavigation(jpaMenu, "我的书架");
        JButton bt2 = NovelTool.novelNavigation(jpaMenu,"小说下载");
        JButton bt3 = NovelTool.novelNavigation(jpaMenu,"TT书城");


        //书架面板
        jf.add(jpaBookrack);
        jpaBookrack.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));//布局
        jpaBookrack.setBackground(Color.decode(str[4]));//书架背景颜色
        //滚动条
        JScrollPane jsp = new JScrollPane(jpaBookrack);
        jsp.setBounds(130,50, LOCAL_WHILE,LOCAL_HEIGHT);
        jsp.getVerticalScrollBar().setUnitIncrement(40);
        jf.add(jsp);

        //下载面板
        AddNovel addNovel = new AddNovel();
        JPanel jplAddNovel = addNovel.add(jf);

        //加载书架
        novelRead = new NovelTool().bookshelf(jpaBookrack,str[5],str[6]);

        //加载面板
        jPlLoading = getLoading(jf);

        //删除缓存
        new Thread(() -> {
            for (File f :
                    new File(XML_Dom4J.getFileRoot() + "\\img\\buffer").listFiles()) {
                f.delete();
            }
        }).start();

//监听
        //搜索
        btSeek.addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent e) {
                String sts = jte.getText(); //获取搜索框内容
            }
            public void mouseEntered(MouseEvent e) {// 处理鼠标移入
                btSeek.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));//鼠标形状
            }public void mouseExited(MouseEvent e) {
                // 处理鼠标离开
            }public void mousePressed(MouseEvent e) {
                // 处理鼠标按下
            }public void mouseReleased(MouseEvent e) {
                // 处理鼠标释放
            }
        });

        //日间
        textDay.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                //修改数据库
                new SqlSetup().setSetup("0", SqlSetup.MAIN_WINDOW_COLOR);

                str = NovelTool.color(0);
                jpa.setBackground(Color.decode(str[0]));//顶部面
                jpaMenu.setBackground(Color.decode(str[1]));//西部面板
                jpaBookrack.setBackground(Color.decode(str[4]));//书架面板
                updateBookshelf(str[5],str[6]);
                jte.setBackground(Color.decode(str[3]));//搜索框
                jpaStore.setBackground(Color.decode(str[4]));//书城
                jplAddNovel.setBackground(Color.decode(str[4]));//下载面板

                //西部面板字体颜色
                bt.setForeground(Color.decode(str[2]));//字体颜色
                bt2.setForeground(Color.decode(str[2]));//字体颜色
                bt3.setForeground(Color.decode(str[2]));//字体颜色
            } public void mouseEntered(MouseEvent e) {// 处理鼠标移入
                textDay.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); //鼠标形状
            }public void mouseExited(MouseEvent e) {
                // 处理鼠标离开
            }public void mousePressed(MouseEvent e) {
                // 处理鼠标按下
            }public void mouseReleased(MouseEvent e) {
                // 处理鼠标释放
            }});

        //夜间
        testNight.addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent e) {
                //修改数据库
                new SqlSetup().setSetup("1", SqlSetup.MAIN_WINDOW_COLOR);

                str = NovelTool.color(1);
                jpa.setBackground(Color.decode(str[0]));//顶部面
                jpaMenu.setBackground(Color.decode(str[1]));//西部面板
                jpaBookrack.setBackground(Color.decode(str[4]));//书架面板
                updateBookshelf(str[5],str[6]);
                jte.setBackground(Color.decode(str[3]));//搜索框
                jpaStore.setBackground(Color.decode(str[4]));//书城
                jplAddNovel.setBackground(Color.decode(str[4]));//下载面板

                //西部面板字体颜色
                bt.setForeground(Color.decode(str[2]));
                bt2.setForeground(Color.decode(str[2]));
                bt3.setForeground(Color.decode(str[2]));
            } public void mouseEntered(MouseEvent e) {// 处理鼠标移入
                testNight.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); //鼠标形状
            }public void mouseExited(MouseEvent e) {
                // 处理鼠标离开
            }public void mousePressed(MouseEvent e) {
                // 处理鼠标按下
            }public void mouseReleased(MouseEvent e) {
                // 处理鼠标释放
            }});

        //书架
        bt.addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent e) {
                //书架、下载,书城详细信息,加载
                NovelTool.showHiddenComponent(jsp, jplAddNovel,jpaStore,jplDetails,jPlLoading);
            }
            public void mouseEntered(MouseEvent e) {// 处理鼠标移入
                bt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); //鼠标形状
                bt.setBackground(Color.decode("#f4e5e5"));//按钮背景颜色
            }public void mouseExited(MouseEvent e) {
                // 处理鼠标离开
                bt.setBackground(Color.decode("#e7c1c3"));//按钮背景颜色
            }public void mousePressed(MouseEvent e) {
                // 处理鼠标按下
            }public void mouseReleased(MouseEvent e) {
                // 处理鼠标释放
            }
        });

        //下载
        bt2.addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent e) {
                //下载、书城,书架,详细信息,加载
                NovelTool.showHiddenComponent(jplAddNovel, jpaStore,jsp,jplDetails,jPlLoading);
            }
            public void mouseEntered(MouseEvent e) {
                // 处理鼠标移入
                bt2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                bt2.setBackground(Color.decode("#f4e5e5"));//按钮背景颜色
            }
            public void mouseExited(MouseEvent e) {
                // 处理鼠标离开
                bt2.setBackground(Color.decode("#e7c1c3"));//按钮背景颜色
            }public void mousePressed(MouseEvent e) {
                // 处理鼠标按下
            }public void mouseReleased(MouseEvent e) {
                // 处理鼠标释放
            }
        });

        //书城
        bt3.addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent ek) {
                if (!NovelStore.exitLoading) {

                    new Thread(()->{
                        showHiddenComponent(jPlLoading, jplAddNovel,jsp);//下载,书架
                        while (true) {
                            System.out.print("");
                            if (NovelStore.exitLoading) {
                                showHiddenComponent(jpaStore, jPlLoading);//书城、加载
                                break;
                            }
                        }
                    }).start();

                    new Thread(()->{
                        NovelStore.progress();
                        NovelStore.loadData();
                    }).start();

                } else {
                    showHiddenComponent(null, jPlLoading,jplAddNovel,jsp);//加载、下载,书架
                    if (NovelTool.exdd){
                        showHiddenComponent(jplDetails);
                    } else {
                        showHiddenComponent(jpaStore);//书城
                    }
                }
            }
            public void mouseEntered(MouseEvent e) {
                // 处理鼠标移入
                bt3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                bt3.setBackground(Color.decode("#f4e5e5"));//按钮背景颜色
            }
            public void mouseExited(MouseEvent e) {
                // 处理鼠标离开
                bt3.setBackground(Color.decode("#e7c1c3"));//按钮背景颜色
            }public void mousePressed(MouseEvent e) {
                // 处理鼠标按下
            }public void mouseReleased(MouseEvent e) {
                // 处理鼠标释放
            }
        });

        //用户登录
        userImg.addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent e) {
                //jf.dispose();//销毁当前页面
                new Novelyh().Novelyhs();//打开一个新的页面
            }
            public void mouseEntered(MouseEvent e) {
                // 处理鼠标移入
                userImg.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }public void mouseExited(MouseEvent e) {
                // 处理鼠标离开
            }public void mousePressed(MouseEvent e) {
                // 处理鼠标按下
            }public void mouseReleased(MouseEvent e) {
                // 处理鼠标释放
            }
        });

        //刷新
        update.addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent e) {
                new Thread(()->{
                    ServiceDB.updatesNovelsData();
                    updateBookshelf(str[5],str[6]);
                    //NovelStore.progress();
                    //NovelStore.loadData();
                }).start();
            }
            public void mouseEntered(MouseEvent e) {// 处理鼠标移入
                update.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));//鼠标形状
            }public void mouseExited(MouseEvent e) {
                // 处理鼠标离开
            }public void mousePressed(MouseEvent e) {
                // 处理鼠标按下
            }public void mouseReleased(MouseEvent e) {
                // 处理鼠标释放
            }
        });

//窗口
        //jf.getContentPane().setBackground(Color.decode("#e7949a"));//窗口背景颜色
        Toolkit tk=Toolkit.getDefaultToolkit() ;//标题栏图标
        Image image=tk.createImage("img\\swing\\0.png");
        jf.setIconImage(image);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置关闭规则
        jf.setVisible(true);//设置窗口可见
    }

    //详细信息
    public static void getData(String url){
        showHiddenComponent(null, jpaStore);//书城
        jplDetails = NovelTool.getDetails(jf,url);
        NovelTool.exdd = true;
    }
    public static void showStore() {
        showHiddenComponent(jpaStore);//书城
    }

    //从新加载书架
    public static void updateBookshelf(String colors,String fontColor){
        jpaBookrack.removeAll();
        jpaBookrack.repaint();
        novelRead = new NovelTool().bookshelf(jpaBookrack,colors,fontColor);
        jpaBookrack.revalidate();
    }

    public static void main(String[] args) {
        new Novel().Novelz();
    }

}


