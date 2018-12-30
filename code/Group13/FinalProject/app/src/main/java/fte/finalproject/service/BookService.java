package fte.finalproject.service;

import android.util.Log;

import java.io.IOException;
import java.util.Observable;
import java.util.concurrent.TimeUnit;

import fte.finalproject.obj.AllRankingObj;
import fte.finalproject.obj.CategoryObj;
import fte.finalproject.obj.ChapterObj;
import fte.finalproject.obj.ClassificationObj1;
import fte.finalproject.obj.ClassificationObj2;
import fte.finalproject.obj.CptListObj;
import fte.finalproject.obj.SingleRankingObj;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class BookService {
    private static String ApiUrl = "http://api.zhuishushenqi.com";
    private static String StaticsUrl = "http://statics.zhuishushenqi.com";
    private static String ChapterUrl = "http://chapter2.zhuishushenqi.com";

    OkHttpClient build = new OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.SECONDS)
            .readTimeout(2, TimeUnit.SECONDS)
            .writeTimeout(2, TimeUnit.SECONDS)
            .build();

    // 用于API访问
    Retrofit retrofitForApi = new Retrofit.Builder()
            .baseUrl(ApiUrl)
            // 设置json数据解析器
            .addConverterFactory(GsonConverterFactory.create())
            // RxJava封装OkHttp的Call函数，本质还是利用OkHttp请求数据
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            // build 即为okhttp声明的变量，下文会讲
            .client(build)
            .build();

    // 用于图片访问
    Retrofit retrofitForStatics = new Retrofit.Builder()
            .baseUrl(StaticsUrl)
            // 设置json数据解析器
            .addConverterFactory(GsonConverterFactory.create())
            // RxJava封装OkHttp的Call函数，本质还是利用OkHttp请求数据
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            // build 即为okhttp声明的变量，下文会讲
            .client(build)
            .build();

    // 用于章节访问
    Retrofit retrofitForChapter = new Retrofit.Builder()
            .baseUrl(ChapterUrl)
            // 设置json数据解析器
            .addConverterFactory(GsonConverterFactory.create())
            // RxJava封装OkHttp的Call函数，本质还是利用OkHttp请求数据
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            // build 即为okhttp声明的变量，下文会讲
            .client(build)
            .build();

    // 用于访问api
    private UrlService ApiService = retrofitForApi.create(UrlService.class);

    // 用于访问图片
    private UrlService StaticsService = retrofitForStatics.create(UrlService.class);

    // 用于访问章节
    private UrlService ChapterService = retrofitForChapter.create(UrlService.class);

    // 所有排行榜
    private AllRankingObj allRankingObj;

    // 单一排行榜
    private SingleRankingObj singleRankingObj;

    // 一级分类
    private ClassificationObj1 classificationObj1;

    // 二级分类
    private ClassificationObj2 classificationObj2;

    // 书籍列表
    private CategoryObj categoryObj;

    // 章节列表
    private CptListObj cptListObj;

    // 章节内容
    private ChapterObj chapterObj;

    /*
     * 获取所有排行榜
     * @param 无
     * @return AllRankingObj
     */

    public AllRankingObj getAllRankingObj() {
        Response<AllRankingObj> response = null;
        try {
            response = ApiService.getAllRanking().execute();
            allRankingObj = response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allRankingObj;
    }

    /*
     * 获取单一排行榜
     * @param rankingId String _id 周榜、monthRank 月榜、totalRank 总榜
     *          _id、monthRank、totalRank均为榜单id，可从AllRankingObj中获得
     * @return SingleRankingObj
     */

    public SingleRankingObj getSingleRankingObj(String rankingId) {
        Response<SingleRankingObj> response = null;
        try {
            response = ApiService.getSingleRanking(rankingId).execute();
            singleRankingObj = response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return singleRankingObj;
    }

    /*
     * 获取一级分类
     * @param 无
     * @return ClassificationObj1
     */

    public ClassificationObj1 getClassification1() {
        Response<ClassificationObj1> response = null;
        try {
            response = ApiService.getClassificationObj1().execute();
            classificationObj1 = response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classificationObj1;
    }

    /*
     * 获取二级分类
     * @param 无
     * @return ClassificationObj2
     */

    public ClassificationObj2 getClassification2() {
        Response<ClassificationObj2> response = null;
        try {
            response = ApiService.getClassificationObj2().execute();
            classificationObj2 = response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classificationObj2;
    }

    /*
     * 获取主题书单列表
     * @param type String hot(热门)、new(新书)、reputation(好评)、over(完结)
     *        major String 玄幻
     *        start String 起始位置，从0开始
     *        limit String 获取数量限制 20
     *        tag String 东方玄幻、异界大陆、异界争霸、远古神话
     *        gender String 性别 male、female
     * @return CategoryObj
     * 示例 bookService.getBooksByCategoty("hot", "玄幻", "0", "20", "东方玄幻", "male");
     */

    public CategoryObj getBooksByCategoty(String type, String major, String start, String limit, String tag, String gender) {
        Response<CategoryObj> response = null;
        try {
            response = ApiService.getBooksByCategory(type, major, start, limit, tag, gender).execute();
            categoryObj = response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return categoryObj;
    }

    /*
     * 获取章节列表
     * @param String bookid 书籍id
     * @return CptListObj 章节列表对象
     */

    public CptListObj getChaptersByBookId(String bookid) {
        Response<CptListObj> response = null;
        try {
            response = ApiService.getChapters(bookid).execute();
            cptListObj = response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cptListObj;
    }

    /*
     * 获取章节内容
     * @param String link 章节链接
     * @return ChapterObj 章节对象
     */

    public ChapterObj getChapterByLink(String link) {
        Response<ChapterObj> response = null;
        try {
            response = ChapterService.getChapter(link).execute();
            chapterObj = response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return chapterObj;
    }

}
