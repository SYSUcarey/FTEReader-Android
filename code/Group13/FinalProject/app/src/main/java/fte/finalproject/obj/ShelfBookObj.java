package fte.finalproject.obj;

import android.graphics.Bitmap;

public class ShelfBookObj {
    int bookId;
    private Bitmap icon;
    private String name;
    int type;
    private String address;
    private int readChapter;

    public ShelfBookObj(int bookId, String name, Bitmap icon, int readChapter, String address,int type) {
        this.bookId = bookId;
        this.name = name;
        this.icon = icon;
        this.readChapter = readChapter;
        this.address = address;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public String getAddress() {
        return address;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    public int getReadChapter() {
        return readChapter;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setReadChapter(int readChapter) {
        this.readChapter = readChapter;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}
