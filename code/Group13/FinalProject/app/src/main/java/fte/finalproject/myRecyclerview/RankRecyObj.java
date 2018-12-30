package fte.finalproject.myRecyclerview;

import android.graphics.Bitmap;

public class RankRecyObj {
    private Bitmap image;
    private String name;
    private int color;
    private boolean isMale;

    public RankRecyObj(Bitmap image, String name, int color, boolean isMale) {
        this.image = image;
        this.name = name;
        this.color = color;
        this.isMale = isMale;
    }

    public String getName() {
        return name;
    }

    public Bitmap getImage() {
        return image;
    }

    public int getColor() {
        return color;
    }

    public boolean isMale() {
        return isMale;
    }
}
