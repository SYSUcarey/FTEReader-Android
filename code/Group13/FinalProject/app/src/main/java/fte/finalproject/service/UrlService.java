package fte.finalproject.service;

import fte.finalproject.obj.AllRankingObj;
import fte.finalproject.obj.CategoryObj;
import fte.finalproject.obj.ChapterObj;
import fte.finalproject.obj.ClassificationObj1;
import fte.finalproject.obj.ClassificationObj2;
import fte.finalproject.obj.CptListObj;
import fte.finalproject.obj.SingleRankingObj;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UrlService {
    /*
     * 获取所有排行榜
     * @param 无
     * @return Call<AllRankingObj>
     */
    @GET("/ranking/gender")
    Call<AllRankingObj> getAllRanking();

    /*
     * 获取单一排行榜
     * @param rankingId String _id 周榜、monthRank 月榜、totalRank 总榜
     * @return Call<SingleRankingObj>
     */
    @GET("/ranking/{rankingId}")
    Call<SingleRankingObj> getSingleRanking(@Path("rankingId") String rankingId);


    /*
     * 获取一级分类
     * @param 无
     * @return Call<ClassificationObj1>
     */
    @GET("/cats/lv2/statistics")
    Call<ClassificationObj1> getClassificationObj1();

    /*
     * 获取二级分类
     * @param 无
     * @return Call<ClassificationObj2>
     */
    @GET("/cats/lv2")
    Call<ClassificationObj2> getClassificationObj2();

    /*
     * 获取主题书单列表
     * @param type String hot(热门)、new(新书)、reputation(好评)、over(完结)
     *        major String 玄幻
     *        start String 起始位置，从0开始
     *        limit String 限制获取数量 20
     *        tag String 东方玄幻、异界大陆、异界争霸、远古神话
     *        gender String 性别 male、female
     * @return Call<CategoryObj>
     */
    @GET("/book/by-categories")
    Call<CategoryObj> getBooksByCategory(@Query("type") String type, @Query("major") String major, @Query("start") String start, @Query("limit") String limit, @Query("tag") String tag, @Query("gender") String gender);

    /*
     * 获取章节列表
     * @param bookid 书籍id
     * @return Call<CptListObj>
     */
    @GET("/mix-atoc/{bookid}?view=chapters")
    Call<CptListObj> getChapters(@Path("bookid") String bookid);

    /*
     * 获取章节内容
     * @param link 章节链接
     * @return ChapterObj 章节对象
     */
    @GET("/chapter/{link}")
    Call<ChapterObj> getChapter(@Path("link") String link);

}
