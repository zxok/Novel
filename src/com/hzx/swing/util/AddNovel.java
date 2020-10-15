package com.hzx.swing.util;


import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

import com.hzx.service.ServiceDB;
import com.hzx.swing.Novel;
import com.hzx.web.HttpNovel;

public class AddNovel extends Novel {

    public static ArrayList<HttpNovel> list = new ArrayList<>();

    public static void setAddList(HttpNovel httpNovel) {
        list.add(httpNovel);
        addTask();
    }

    private JProgressBar pr = new JProgressBar();//进度条
    private JTextField jte = new JTextField();//输入框
    // 下载列表
    public static JPanel addList = new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));

    public JPanel add(JFrame jFrame) {
        //面板
        JPanel jpl = new JPanel();
        jFrame.add(jpl);
        jpl.setBounds(130,50, LOCAL_WHILE,LOCAL_HEIGHT);
        jpl.setLayout(null);//布局
        jpl.setBackground(Color.decode("#f3f6fc"));//背景颜色
        jpl.setVisible(false);//隐藏下载面板

        JButton bt = new JButton("确认下载");//按钮
        JButton bt2 = new JButton("重置输入");
        JLabel la = new JLabel("请输入下载链接：");//提示文本

        bt.setBackground(Color.decode("#72cbff"));//下载按钮背景颜色
        bt2.setBackground(Color.decode("#ffffff"));//重置按钮背景颜色f65ab2

        la.setBounds(120,60 , 120,20); //提示文本
        jte.setBounds(120,90 , 300,40);//输入框
        bt.setBounds(430,90 , 100,40); //下载按钮
        bt2.setBounds(430,40 , 100,40);//重置按钮
        pr.setBounds(600,90 , 300,30);//进度条
        pr.setVisible(false);//隐藏
        bt.setFocusPainted(false);//去焦点
        bt2.setFocusPainted(false);
        jpl.add(jte);
        jpl.add(la);
        jpl.add(pr);
        jpl.add(bt);
        jpl.add(bt2);
        pr.setStringPainted(true);//设置进度条显示提示信息
        //设置进度条的样式为不确定的进度条样式（进度条来回滚动），false为确定的进度条样式（即进度条从头到尾显示）


        //下载列表
        //JPanel addList = new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));
        addList.setPreferredSize(new Dimension(800, 450));
        addList.setBackground(Color.WHITE);
        jpl.add(addList);
        //滚动条
        JScrollPane jsp = new JScrollPane(addList);
        jsp.setBounds(70,180, 820,460);
        jsp.getVerticalScrollBar().setUnitIncrement(40);
        jpl.add(jsp);


        //下载
        bt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pr.setVisible(true);	//显示
                pr.setIndeterminate(true);//采用不确定的进度条样式
                pr.setString("正在下载");//设置提示信息
                String url = jte.getText();
                Progress(url);//下载方法
                b = false;
                b2 = true ;
            }});

        //重置按钮
        bt2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jte.setText("");
            }
        });

        return jpl;
    }

    //加载下载列表
    public static void addTask() {
        addList.removeAll();
        addList.repaint();
        int sumBook = list.size();
        addList.setPreferredSize(new Dimension(800, sumBook * 60));

        for (int i = 0; i < sumBook; i++) {
            JPanel task = new  JPanel(new FlowLayout(FlowLayout.LEFT,20,0));
            task.setPreferredSize(new Dimension(780, 50));
            task.setBackground(Color.decode("#f9e9e6"));
            addList.add(task);
            //书名
            JLabel name = new JLabel("《"+list.get(i).name+"》");
            name.setPreferredSize(new Dimension(280,50));
            task.add(name);

            //进度条
            JProgressBar progressBar = new JProgressBar();
            progressBar.setPreferredSize(new Dimension(200,25));
            progressBar.setFont(new Font("黑体", Font.BOLD, 16));
            progressBar.setForeground(Color.decode("#009fec"));
            progressBar.setStringPainted(true); //显示百分比
            progressBar.setIndeterminate(false);//进度条样式
            task.add(progressBar);
            //已下载进度
            JLabel addSum = new JLabel(list.get(i).size+" / "+list.get(i).sum);
            addSum.setPreferredSize(new Dimension(120,30));
            task.add(addSum);

            int finalI = i;

            TaskAdd taskAdd = new TaskAdd(progressBar, addSum, finalI, list );
            Thread t = new Thread(taskAdd);
            t.start();

            //暂停下载
            JButton bt_Pause = new JButton(new ImageIcon("img\\swing\\pause_32px_1.png"));
            bt_Pause.setPreferredSize(new Dimension(32,32));
            bt_Pause.setFocusPainted(false);
            task.add(bt_Pause);
            bt_Pause.addActionListener(new ActionListener() {
                boolean fnt = true;
                public void actionPerformed(ActionEvent e) {
                    if (fnt) {
                        bt_Pause.setIcon(new ImageIcon("img\\swing\\play_32px_1.png"));
                        fnt = false;
                    } else {
                        bt_Pause.setIcon(new ImageIcon("img\\swing\\pause_32px_1.png"));
                        fnt = true;
                    }
                }
            });

            //停止下载
            JButton bt_Exit = new JButton(new ImageIcon("img\\swing\\close_32px_1.png"));
            bt_Exit.setPreferredSize(new Dimension(32,32));
            bt_Exit.setFocusPainted(false);
            task.add(bt_Exit);
            bt_Exit.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    list.get(finalI).addStatus = true;
                    list.remove(finalI);
                    addTask();
                }
            });
            NovelTool.fontTools(18,name,addSum,bt_Exit);

        }
        addList.revalidate();
    }

    static boolean b=false, b2=true;
    public void Progress(String url) {
        int[] num = {1,3,9,20,40,60,80,100};

        new Thread(()->{	//下载
            try {
                b=new ServiceDB().addNovels(url);//下载

            }catch(Exception a) {
                a.printStackTrace();
            }
        }).start();

        //进度条
        new Thread(()->{
            try {
                System.out.println("B- "+b+"    B2- "+b2);
                while(b2) {
                    pr.setIndeterminate(true);//采用不确定的进度条样式
                    pr.setString("正在下载");//设置提示信息

                    if(b) {
                        b2 = false;
                        System.out.println("B- "+b+"    B2- "+b2);
                        pr.setIndeterminate(false);//采用确定的进度条样式
                        for(int i = 0; i < num.length; i++) {
                            try {
                                pr.setValue(num[i]);
                                Thread.sleep(100);

                            }catch(Exception e) {
                                e.printStackTrace();
                            }
                        }
                        //Novel.bookcase();
                        pr.setString("下载完成！");
                    }}

            }catch(Exception a) {
                a.printStackTrace();
            }
        }).start();
    }

}


class TaskAdd implements Runnable {

    private JProgressBar progressBar;
    private JLabel addSum;
    private int finalI;
    private HttpNovel httpNovel;
    public static ArrayList<HttpNovel> list;

    @Override
    public void run() {
        int sum, size;
        while (list.size() != 0) {
            do {
                sum = list.get(finalI).sum;
                size = list.get(finalI).size;
                try {
                    addSum.setText(" " + size + " / " + sum);
                    System.err.print((int) (((double) size / sum) * 100) + "%");
                    System.err.println("  size: " + size + "\tsum: " + sum);
                    progressBar.setValue((int) (((double) size / sum) * 100));
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (list.get(finalI).addStatus || size == sum) {
                        list.remove(finalI);
                        AddNovel.addTask();
                        System.err.println("下载完成！");
                        break;
                }
            } while (true);
        }
    }

    public TaskAdd(JProgressBar progressBar, JLabel addSum, int finalI, ArrayList<HttpNovel> list ) {
        this.progressBar = progressBar;
        this.addSum = addSum;
        this.finalI = finalI;
        this.list = list;
    }
}

