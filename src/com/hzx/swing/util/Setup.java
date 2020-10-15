package com.hzx.swing.util;

import com.hzx.cfg.XML_Dom4J;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;

public class Setup {

    private static String userFile;
    {
        userFile = XML_Dom4J.getFileRoot();
        if (!(new File(userFile+"\\xml\\userSetup.xml")).exists()) {
            userFile = "TTNovelUser";
            XML_Dom4J.updateFileRoot(userFile);
        }
    }

    public Setup(){
        //窗口
        JFrame jf = new JFrame("设置");

        //提示文本
        JLabel la = new JLabel("设置文件路径");
        la.setBounds(100,30 , 300,30);//组件位置、宽高(x,y,w,h)
        la.setFont(new Font("黑体", Font.BOLD, 18));//字体，大小
        la.setHorizontalAlignment(SwingConstants.CENTER);
        jf.add(la);

        //文件路径显示框
        JTextField addFileText = new  JTextField(new File(userFile).getAbsolutePath());
        addFileText.setBounds(50,100 , 360,40);
        addFileText.setFont(new Font("等线", Font.PLAIN, 16));
        jf.add(addFileText);
        //文件选择
        JLabel btFile = new  JLabel(new ImageIcon("img\\swing\\file_32px_1.png"));
        btFile.setBounds(420,100 , 40,32);
        jf.add(btFile);
        JFileChooser chooser = new JFileChooser();

        //文件选择
        btFile.addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent e) {
                chooser.setCurrentDirectory(new File("F:\\Java\\Test"));//设置此文件选择器，即可以选择文件夹也可以选择一般的文件
                chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);//打开一个文件选择器的对话框
                int i = chooser.showOpenDialog(jf);
                if(i == JFileChooser.APPROVE_OPTION) {//点击确定按钮关闭
                    File file = chooser.getSelectedFile();//获得被选中的文件或者文件夹
                    addFileText.setText(file.getAbsolutePath());
                }
            }
            public void mouseEntered(MouseEvent e) {// 处理鼠标移入
                btFile.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));//鼠标形状
            }public void mouseExited(MouseEvent e) {
                // 处理鼠标离开
            }public void mousePressed(MouseEvent e) {
                // 处理鼠标按下
            }public void mouseReleased(MouseEvent e) {
                // 处理鼠标释放
            }
        });

        //错误提示
        JLabel error = new JLabel();
        error.setBounds(150,170 , 200,20);
        error.setFont(new Font("等线", Font.PLAIN, 16));
        error.setHorizontalAlignment(SwingConstants.CENTER);
        error.setForeground(Color.RED);
        jf.add(error);

        //退出
        JButton bt = new JButton("取消");
        bt.setBounds(150,230 , 70,30);//组件位置、宽高(x,y,w,h)
        bt.setBackground(Color.decode("#e7c1c3"));//背景颜色
        bt.setFocusPainted(false);//去焦点
        jf.add(bt);
        //确认
        JButton bt2 = new JButton("确认");
        bt2.setBounds(270,230 , 70,30);//组件位置、宽高(x,y,w,h)
        bt2.setBackground(Color.decode("#7dbdff"));//背景颜色
        bt2.setFocusPainted(false);//去焦点
        jf.add(bt2);
        NovelTool.fontTools(18, bt,bt2);

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
                System.out.println("源文件：\t"+userFile+"\n目标文件："+addFileText.getText()+
                        "\n源文件："+ userFile.replace("\\TTNovelUser",""));
                //判断路径是否已经存在
                if (!userFile.equals(addFileText.getText()) &&
                        !userFile.replace("\\TTNovelUser","").equals(addFileText.getText())) {
                    int i = setFile(new File(addFileText.getText()));
                    if ( i == 1 )
                        //只关闭当前的窗体，而不涉及父窗体
                        jf.dispose();
                    else if (i == -1)
                        error.setText("所选路径不是目录！");
                    else if (i == 0)
                        error.setText("设置失败！");

                } else {
                    error.setText("路径已存在！");
                }
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
        jf.setSize(500,350);//窗口宽,高
        Dimension ds = Toolkit.getDefaultToolkit().getScreenSize();
        jf.setLocation(((int) ds.getWidth()-500) / 2, ((int) ds.getHeight()-350) / 2 - 100);

        jf.setResizable(false);	//窗口缩放
        jf.setLayout(null);//窗口布局
        Toolkit tk=Toolkit.getDefaultToolkit() ;//标题栏图标
        Image image=tk.createImage("img\\swing\\0.png");//导入栏图片
        jf.setIconImage(image);
        //jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置关闭规则
        jf.setVisible(true);//设置窗口可见
    }

    //创建
    private int addFile(File file) {
        if (!file.isDirectory()) {
            System.out.println("所选路径不是目录！");
            return 1;
        }else if (!file.exists()) {
            System.out.println("所选路径不存在！");
            file.mkdirs();
        }
        File f = new File(file.getAbsolutePath() + "\\TTNovel");
        f.mkdir();

        new File(f.getAbsolutePath()+"\\buffer").mkdir();
        File fu = new File(f.getAbsolutePath()+"\\user");
        fu.mkdir();

        File fx = new File(fu.getAbsolutePath()+"\\TTNovelUser.xml");
        System.out.println(fx.getAbsolutePath());
        return 0;
    }

    //移动
    private static int setFile(File targetFile) {
        if (!targetFile.isDirectory()) {
            System.out.println("所选路径不是目录！");
            return -1;
        }else if (!targetFile.exists()) {
            System.out.println("所选路径不存在！");
            targetFile.mkdirs();
        }
        File newFile = new File(targetFile.getAbsolutePath() + "\\TTNovelUser");
        newFile.mkdir();

        File sourceFile = new File(userFile);
        //复制(源文件、目标)
        copyFile(sourceFile, newFile);

        //判断是否复制成功
        System.out.println(XML_Dom4J.sameFile(sourceFile,newFile));
        if (XML_Dom4J.sameFile(sourceFile,newFile)) {
            //删除(源文件为项目文件时不进行删除)
            if (!sourceFile.getAbsolutePath().equals(new File("TTNovelUser").getAbsolutePath()))
                delete(new File(userFile));
            //修改XML文件
            XML_Dom4J.updateFileRoot(newFile.getAbsolutePath());
        } else
            return 0;
        return 1;
    }

    //复制文件
    private static void copyFile(File sourceFile, File targetFile) {
        if (! targetFile.exists()){
            targetFile.mkdirs();
        }

        for (File file :
                sourceFile.listFiles()) {
            if (file.isFile()) {
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(file.getAbsoluteFile());
                    FileOutputStream fos = new FileOutputStream(new File(targetFile+"\\"+file.getName()));
                    byte[] b= new byte[1024];
                    while ((fis.read(b)) != -1) {
                        fos.write(b);
                    }
                    fis.close();
                    fos.close();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                //递归
                String target = targetFile+"\\"+file.getName();
                new File(target).mkdir();
                copyFile(new File(file.getAbsolutePath()), new File(target));
            }
        }
    }

    //删除
    private static void delete(File file) {
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isFile()) {
                System.out.println("删除："+f.getAbsolutePath());
                f.delete();
            }else {
                delete(f.getAbsoluteFile());
                System.out.println("删除："+f.getAbsolutePath());
                f.delete();
            }
            System.out.println("删除："+file.getAbsolutePath());
            file.delete();
        }
    }

}


