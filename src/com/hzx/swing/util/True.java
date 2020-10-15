package com.hzx.swing.util;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import com.hzx.dao.SqlNovelTab;
import com.hzx.swing.Novel;

public class True extends Novel {

    public True(int id, JPanel jPanel,NovelRead novelRead,String bookName){//默认返回false
        JFrame jf = new JFrame("删除确认");//窗口

        JLabel la = new JLabel("确认删除《"+bookName+"》？");//文本
        jf.add(la);
        la.setBounds(30,30 , 340,30);//组件位置、宽高(x,y,w,h)
        la.setFont(new Font("黑体", Font.BOLD, 18));//字体，大小
        la.setHorizontalAlignment(SwingConstants.CENTER);

        JButton bt = new JButton("取消");
        jf.add(bt);
        bt.setBounds(100,100 , 70,30);//组件位置、宽高(x,y,w,h)
        bt.setFont(new Font("等线", Font.BOLD, 18));//字体，大小
        bt.setBackground(Color.decode("#7dbdff"));//背景颜色
        bt.setFocusPainted(false);//去焦点

        JButton bt2 = new JButton("确认");
        jf.add(bt2);
        bt2.setBounds(220,100 , 70,30);//组件位置、宽高(x,y,w,h)
        bt2.setFont(new Font("等线", Font.BOLD, 18));//字体，大小
        bt2.setBackground(Color.decode("#e7c1c3"));//背景颜色
        bt2.setFocusPainted(false);//去焦点

        //取消
        bt.addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent e) {
                jf.dispose();//只关闭当前的窗体，而不涉及父窗体
            }
            public void mouseEntered(MouseEvent e) {// 处理鼠标移入
                bt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));//鼠标形状
            }public void mouseExited(MouseEvent e) {
                // 处理鼠标离开
            }public void mousePressed(MouseEvent e) {
                // 处理鼠标按下
            }public void mouseReleased(MouseEvent e) {
                // 处理鼠标释放
            }
        });

        //确认
        bt2.addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent e) {
                SqlNovelTab.delete(id);//删除
                //NovelTool.u--;
                Novel.updateBookshelf(str[5],str[6]);
                jf.dispose();//只关闭当前的窗体，而不涉及父窗体
                if (novelRead != null)
                    if (novelRead.httpNovel != null)
                    novelRead.httpNovel.addStatus = true;
            }
            public void mouseEntered(MouseEvent e) {// 处理鼠标移入
                bt2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));//鼠标形状
            }public void mouseExited(MouseEvent e) {
                // 处理鼠标离开
            }public void mousePressed(MouseEvent e) {
                // 处理鼠标按下
            }public void mouseReleased(MouseEvent e) {
                // 处理鼠标释放
            }
        });

//窗口设置
        jf.setSize(400,230);//窗口宽,高
        //jf.setLocation(900, 360);//运行时窗口在屏幕上的默认位置
        Dimension ds = Toolkit.getDefaultToolkit().getScreenSize();
        jf.setLocation(((int) ds.getWidth()-400) / 2, ((int) ds.getHeight()-230) / 2);

        jf.setResizable(false);	//窗口缩放
        jf.setLayout(null);//窗口布局
        jf.getContentPane().setBackground(Color.WHITE);//窗口背景颜色
        Toolkit tk=Toolkit.getDefaultToolkit() ;//标题栏图标
        Image image=tk.createImage("img\\swing\\0.png");//导入栏图片
        jf.setIconImage(image);
        //jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置关闭规则
        jf.setVisible(true);//设置窗口可见

    }

}

