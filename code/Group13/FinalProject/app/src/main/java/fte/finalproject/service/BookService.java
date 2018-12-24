package fte.finalproject.service;

import fte.finalproject.obj.ResolveObj;
import retrofit2.http.GET;
import rx.Observable;

public interface BookService {

    /*
     * 获取章节列表
     * @param bookid 书籍id
     * @return ResolveObj 解析对象
     */
    @GET("/mix-atoc/{bookid}?view=chapters")
    Observable<ResolveObj> getChapters(String bookid);

    /*
     * 获取章节内容
     * @param link 章节链接
     * @return ResolveObj 章节对象
     */
    @GET("/chapter/{link}")
    Observable<ResolveObj> getChapter(String link);
}
