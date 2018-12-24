package fte.finalproject.obj;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResolveObj {
    // 请求结果
    @SerializedName("ok")
    private boolean ok;

    public boolean isOk() {
        return ok;
    }

    /*
     * 章节列表
     */
    @SerializedName("mixToc")
    private mixToc imixToc;

    public mixToc getImixToc() {
        return imixToc;
    }

    private class mixToc {
        @SerializedName("_id")
        private String _id;

        public String get_id() {
            return _id;
        }

        // 书籍id
        @SerializedName("book")
        private String book;

        public String getBook() {
            return book;
        }

        // 章节数
        @SerializedName("chaptersCount1")
        private int chaptersCount;

        public int getChaptersCount() {
            return chaptersCount;
        }

        // 章节列表
        @SerializedName("chapters")
        private List<ChapterObj> chapterObjs;

        public List<ChapterObj> getChapterObjs() {
            return chapterObjs;
        }

        // 更新时间
        @SerializedName("updated")
        private String updated;

        public String getUpdated() {
            return updated;
        }
    }

    /*
     * 章节详情
     */
    @SerializedName("chapter")
    private ChapterObj ichapter;

    public ChapterObj getIchapter() {
        return ichapter;
    }
}
