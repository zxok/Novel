package com.hzx.swing.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Novelyh {

    public static void Novelyhs() {

        JFrame jf = new JFrame("用户登录");//窗口
        JLabel jl = new JLabel(new ImageIcon("img\\swing\\14_1.gif"));//加载图标
        JLabel jll = new JLabel("正在连接...");//文本

        jf.add(jl);//下载状态图标
        jl.setBounds(120,50 , 50,50);

        jf.add(jll);//下载状态文本
        jll.setBounds(200,60 , 120,30);
        jll.setFont(new Font("等线", Font.BOLD, 18));

//窗口设置
        jf.setSize(400,200);//窗口宽,高
        jf.setLocation(620, 200);//运行时窗口在屏幕上的默认位置
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
