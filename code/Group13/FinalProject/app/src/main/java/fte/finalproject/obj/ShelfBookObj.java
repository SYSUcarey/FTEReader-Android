package fte.finalproject.obj;

import android.graphics.Bitmap;

public class ShelfBookObj {
    private Bitmap icon;
    private String name;
    private int readPage;

    public ShelfBookObj(String name, Bitmap icon) {
        this.name = name;
        this.icon = icon;
        readPage = 0;
    }

    public String getName() {
        return name;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public int getReadPage() {
        return readPage;
    }
}
