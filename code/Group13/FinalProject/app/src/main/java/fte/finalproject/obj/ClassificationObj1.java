package fte.finalproject.obj;

import com.google.gson.annotations.SerializedName;

import java.util.List;

// 获取一级分类返回结果
public class ClassificationObj1 {
    // male分类
    @SerializedName("male")
    private List<SubClass> maleList;

    // female分类
    @SerializedName("female")
    private List<SubClass> femaleList;

    // picture分类
    @SerializedName("picture")
    private List<SubClass> picList;

    @SerializedName("press")
    private List<SubClass> pressList;

    @SerializedName("ok")
    private boolean ok;

    public List<SubClass> getMaleList() {
        return maleList;
    }

    public List<SubClass> getFemaleList() {
        return femaleList;
    }

    public List<SubClass> getPicList() {
        return picList;
    }

    public List<SubClass> getPressList() {
        return pressList;
    }

    public boolean isOk() {
        return ok;
    }

    // 一级分类
    public class SubClass {
        // 名称
        @SerializedName("name")
        private String name;

        // 书籍数量
        @SerializedName("bookCount")
        private int bookCount;

        // monthlyCount?
        @SerializedName("monthlyCount")
        private int monthlyCount;

        // 分类图标
        @SerializedName("icon")
        private String icon;

        // 封面图链接
        @SerializedName("bookCover")
        private String[] bookCover;

        public String getName() {
            return name;
        }

        public int getBookCount() {
            return bookCount;
        }

        public int getMonthlyCount() {
            return monthlyCount;
        }

        public String getIcon() {
            return icon;
        }

        public String[] getBookCover() {
            return bookCover;
        }
    }

}
