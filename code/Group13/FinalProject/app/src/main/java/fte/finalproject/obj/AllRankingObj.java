package fte.finalproject.obj;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllRankingObj {

    @SerializedName("male")
    private List<subClass> maleList;

    @SerializedName("female")
    private List<subClass> femaleList;

    @SerializedName("ok")
    private boolean ok;

    public List<subClass> getMaleList() {
        return maleList;
    }

    public List<subClass> getFemaleList() {
        return femaleList;
    }

    public boolean isOk() {
        return ok;
    }

    public class subClass {
        @SerializedName("_id")
        private String id;

        @SerializedName("title")
        private String title;

        @SerializedName("cover")
        private String cover;

        @SerializedName("collapse")
        private boolean collapse;

        @SerializedName("monthRank")
        private String monthRank;

        @SerializedName("totalRank")
        private String totalRank;

        @SerializedName("shortTitle")
        private String shortTitle;

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getCover() {
            return cover;
        }

        public boolean isCollapse() {
            return collapse;
        }

        public String getMonthRank() {
            return monthRank;
        }

        public String getTotalRank() {
            return totalRank;
        }

        public String getShortTitle() {
            return shortTitle;
        }
    }

}
