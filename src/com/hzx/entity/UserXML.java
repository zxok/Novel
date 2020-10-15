package com.hzx.entity;

/**
 * @program: Novel
 * @author: hzx
 * @since: JDK 1.8
 * @create: 2020-09-14 20:28
 **/
public class UserXML {
    private int menuColor;
    private int readBgdColor;
    private int readFontSize;
    private String userFile;

    public UserXML() {
    }

    public UserXML(int menuColor, int readBgdColor, int readFontSize, String userFile) {
        this.menuColor = menuColor;
        this.readBgdColor = readBgdColor;
        this.readFontSize = readFontSize;
        this.userFile = userFile;
    }

    @Override
    public String toString() {
        return "UserXML{" +
                "menuColor=" + menuColor +
                ", readBgdColor=" + readBgdColor +
                ", readFontSize=" + readFontSize +
                ", userFile='" + userFile + '\'' +
                '}';
    }

    public int getMenuColor() {
        return menuColor;
    }

    public void setMenuColor(int menuColor) {
        this.menuColor = menuColor;
    }

    public int getReadBgdColor() {
        return readBgdColor;
    }

    public void setReadBgdColor(int readBgdColor) {
        this.readBgdColor = readBgdColor;
    }

    public int getReadFontSize() {
        return readFontSize;
    }

    public void setReadFontSize(int readFontSize) {
        this.readFontSize = readFontSize;
    }

    public String getUserFile() {
        return userFile;
    }

    public void setUserFile(String userFile) {
        this.userFile = userFile;
    }
}
