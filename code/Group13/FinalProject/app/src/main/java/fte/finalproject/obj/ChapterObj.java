package fte.finalproject.obj;

import com.google.gson.annotations.SerializedName;

public class ChapterObj {
    // 章节链接
    @SerializedName("link")
    private String link;

    public String getLink() {
        return link;
    }

    // 章节标题
    @SerializedName("title")
    private String title;

    public String getTitle() {
        return title;
    }

    // 是否可读
    @SerializedName("unreadable")
    private boolean unreadable;

    public boolean isUnreadable() {
        return unreadable;
    }

    // 章节内容
    @SerializedName("body")
    private String body;

    public String getBody() {
        return body;
    }
}
