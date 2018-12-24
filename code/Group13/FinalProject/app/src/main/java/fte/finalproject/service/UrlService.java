package fte.finalproject.service;

import fte.finalproject.obj.ChapterObj;
import fte.finalproject.obj.ClassificationObj1;
import fte.finalproject.obj.ResolveObj;
import retrofit2.Call;
import retrofit2.http.GET;
import rx.Observable;

public interface UrlService {
    /*
     * 获取一级分类
     * @param 无
     * @return Observable<ClassificationObj1>
     */
    @GET("/cats/lv2/statistics")
    Call<ClassificationObj1> getClassificationObj1();

    /*
     * 获取章节列表
     * @param bookid 书籍id
     * @return ResolveObj 解析对象
     */
    @GET("/mix-atoc/{bookid}?view=chapters")
    Call<ResolveObj> getChapters(String bookid);

    /*
     * 获取章节内容
     * @param link 章节链接
     * @return ResolveObj 章节对象
     */
    @GET("/chapter/{link}")
    Call<ChapterObj> getChapter(String link);
}
