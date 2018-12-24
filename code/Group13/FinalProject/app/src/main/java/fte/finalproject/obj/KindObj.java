package fte.finalproject.obj;

import android.graphics.Bitmap;

import java.util.List;

public class KindObj {
    private String name;
    private Bitmap icon;
    private List<String> subKind;

    public KindObj(String name,Bitmap icon ,List<String> subKind) {
        this.name = name;
        this.icon = icon;
        this.subKind = subKind;
    }

    public String getName() {
        return name;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public List<String> getSubKind() {
        return subKind;
    }
}
