package fte.finalproject.service;

import android.util.Log;

import java.io.IOException;
import java.util.Observable;
import java.util.concurrent.TimeUnit;

import fte.finalproject.obj.ClassificationObj1;
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

    private UrlService ApiService = retrofitForApi.create(UrlService.class);
    private UrlService StaticsService = retrofitForStatics.create(UrlService.class);
    private UrlService ChapterService = retrofitForChapter.create(UrlService.class);

    private ClassificationObj1 classificationObj1;

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

}
