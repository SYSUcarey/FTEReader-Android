package fte.finalproject.obj;

import com.google.gson.annotations.SerializedName;

import java.util.List;

// 获取章节列表返回结果
public class ResolveObj {
    // 请求结果
    @SerializedName("ok")
    private boolean ok;

    @SerializedName("mixToc")
    private mixToc imixToc;

    public boolean isOk() {
        return ok;
    }

    public mixToc getImixToc() {
        return imixToc;
    }

    /*
     * 章节列表
     */
    private class mixToc {
        @SerializedName("_id")
        private String _id;
        // 书籍id
        @SerializedName("book")
        private String book;
        // 章节数
        @SerializedName("chaptersCount1")
        private int chaptersCount;
        // 章节列表
        @SerializedName("chapters")
        private List<Chapter> chapters;
        // 更新时间
        @SerializedName("updated")
        private String updated;

        public String get_id() {
            return _id;
        }

        public String getBook() {
            return book;
        }

        public int getChaptersCount() {
            return chaptersCount;
        }

        public List<Chapter> getChapterObjs() {
            return chapters;
        }

        public String getUpdated() {
            return updated;
        }

        private class Chapter {
            // 章节链接
            @SerializedName("link")
            private String link;

            // 章节标题
            @SerializedName("title")
            private String title;

            // 是否不可读
            @SerializedName("unreadable")
            private boolean unreadable;

            public String getLink() {
                return link;
            }

            public String getTitle() {
                return title;
            }

            public boolean isUnreadable() {
                return unreadable;
            }
        }

    }
}
