# 中山大学数据科学与计算机学院本科生实验报告
## （2018年秋季学期）
| 课程名称 | 手机平台应用开发 |   任课老师   |      郑贵锋      |
| :------: | :--------------: | :----------: | :--------------: |
|   年级   |       2016       | 专业（方向） |       嵌软       |
|   学号   |     16340016     |     姓名     |      陈冬禹      |
|   电话   |   15989065586    |    Email     | 772840087@qq.com |
| 开始日期 |    2018.12.20    |   完成日期   |    2019.1.19     |

---

## 一、实验题目

 **Android小说阅读器FTEReader**

---

## 二、个人实现内容

### 1. 网络访问API

### 2. 书籍详情界面UI及逻辑

### 3. 相关书籍界面UI及逻辑

---

## 三、实验结果
### 1. 实验截图

书籍详情界面

<img src="img/详情界面.jpg" width=400/>

相关书籍界面

<img src="img/相关书籍界面.jpg" width=400/>

### 2. 实验步骤以及关键代码

#### （1）建立网络访问接口（以所有排行榜为例）

```java
public interface UrlService {
    /*
     * 获取所有排行榜
     * @param 无
     * @return Call<AllRankingObj>
     */
    @GET("/ranking/gender")
    Call<AllRankingObj> getAllRanking();

	...
}
```

#### （2）初始化多个Retrofit实例分别用于API、图片、章节访问、书籍搜索

```java
Retrofit retrofitForApi = new Retrofit.Builder()
    .baseUrl(ApiUrl)
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
    .client(build)
    .build();

Retrofit retrofitForStatics = new Retrofit.Builder()
    .baseUrl(StaticsUrl)
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
    .client(build)
    .build();

Retrofit retrofitForChapter = new Retrofit.Builder()
    .baseUrl(ChapterUrl)
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
    .client(build)
    .build();

Retrofit retrofitForFuzzySearch = new Retrofit.Builder()
    .baseUrl(FuzzySearchUrl)
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
    .client(build)
    .build();

private UrlService ApiService = retrofitForApi.create(UrlService.class);
private UrlService StaticsService = retrofitForStatics.create(UrlService.class);
private UrlService ChapterService = retrofitForChapter.create(UrlService.class);
private UrlService FuzzySearchService = retrofitForFuzzySearch.create(UrlService.class);
```

#### （3）封装数据（以所有排行榜为例）

```java
public class AllRankingObj {

    // 男生
    @SerializedName("male")
    private List<subClass> maleList;

    // 女生
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
        // 周榜id
        @SerializedName("_id")
        private String id;

        // 排行榜全名
        @SerializedName("title")
        private String title;

        // 排行榜大图标
        @SerializedName("cover")
        private String cover;

        @SerializedName("collapse")
        private boolean collapse;

        // 月榜id
        @SerializedName("monthRank")
        private String monthRank;

        // 总榜id
        @SerializedName("totalRank")
        private String totalRank;

        // 排行榜简称
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
```

#### （4）实现获取对象的函数（以所有排行榜为例）

```java
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
```

#### （5）封装书籍信息

```java
public class BookObj implements Serializable {
    // 书籍id
    @SerializedName("_id")
    private String id;

    // 书籍标题
    @SerializedName("title")
    private String title;

    // 书籍作者
    @SerializedName("author")
    private String author;

    // 书籍介绍
    @SerializedName("longIntro")
    private String longIntro;

    // 书籍简介
    @SerializedName("shortIntro")
    private String shortIntro;

    // 书籍封面图
    @SerializedName("cover")
    private String cover;

    @SerializedName("site")
    private String site;

    // 书籍一级分类
    @SerializedName("majorCate")
    private String majorCate;

    // 书籍二级分类
    @SerializedName("minorCate")
    private String minorCate;

    @SerializedName("sizetype")
    private int sizetype;

    @SerializedName("contentType")
    private String contentType;

    @SerializedName("allowMonthly")
    private boolean allowMonthly;

    @SerializedName("banned")
    private int banned;

    // 最近关注人数
    @SerializedName("latelyFollower")
    private int latelyFollower;

    // 字数
    @SerializedName("wordCount")
    private int wordCount;

    // 留存率
    @SerializedName("retentionRatio")
    private float retentionRatio;

    // 最新章节
    @SerializedName("lastChapter")
    private String lastChapter;

    @SerializedName("updated")
    private String updated;

    // 性别
    @SerializedName("gender")
    private String[] gender;

    // 标签
    @SerializedName("tags")
    private String[] tags;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getShortIntro() {
        return shortIntro;
    }

    public void setShortIntro(String shortIntro) {
        this.shortIntro = shortIntro;
    }

    public String getCover() {
        return cover;
    }

    public String getSite() {
        return site;
    }

    public String getMajorCate() {
        return majorCate;
    }

    public String getMinorCate() {
        return minorCate;
    }

    public int getSizetype() {
        return sizetype;
    }

    public String getContentType() {
        return contentType;
    }

    public boolean isAllowMonthly() {
        return allowMonthly;
    }

    public String getLongIntro() {
        return longIntro;
    }

    public String[] getGender() {
        return gender;
    }

    public int getBanned() {
        return banned;
    }

    public int getLatelyFollower() {
        return latelyFollower;
    }

    public float getRetentionRatio() {
        return retentionRatio;
    }

    public String getLastChapter() {
        return lastChapter;
    }

    public String[] getTags() {
        return tags;
    }

    public int getWordCount() {
        return wordCount;
    }

    public String getUpdated() {
        return updated;
    }
}
```

#### （6）获取书籍相关信息

```java
new Thread(new Runnable() {
    @Override
    public void run() {
        bookObj = BookService.getBookService().getBookById(bookObj.getId());
	   	...
    }
}).start();
```

#### （7）推荐书籍的点击事件

```java
recom1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(BookDetailActivity.this, BookDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("bookobj", bookObjs.get(0));
        intent.putExtras(bundle);
        startActivity(intent);
    }
});
```

#### （8）跳转到阅读界面

```java
// 阅读
readButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(BookDetailActivity.this, ReadPageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("bookid", bookObj.getId());
        intent.putExtras(bundle);
        startActivity(intent);
    }
});
```

#### （9）查看更多同类书籍

```java
// 查看更多同类书籍
moreButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(BookDetailActivity.this, RecomActivity.class);
        Bundle bundle = new Bundle();
        RecomListObj recomListObj = new RecomListObj(bookObjs);
        bundle.putSerializable("list", recomListObj);
        intent.putExtras(bundle);
        startActivity(intent);
    }
});
```

#### （10）推荐书籍列表内容填充

```java
// 内容填充
MyRecyclerViewAdapter<BookObj> adapter = new MyRecyclerViewAdapter<BookObj>(RecomActivity.this, R.layout.item_book, bookObjs) {
    @Override
    public void convert(MyViewHolder holder, BookObj bookObj) {
        final ImageView imageView = holder.getView(R.id.item_book_cover);
        TextView bookName = holder.getView(R.id.item_book_name);
        TextView bookAuthor = holder.getView(R.id.item_book_author);
        TextView bookType = holder.getView(R.id.item_book_type);
        TextView bookIntro = holder.getView(R.id.item_book_intro);
        bookName.setText(bookObj.getTitle());
        bookType.setText(bookObj.getMajorCate());
        bookAuthor.setText(bookObj.getAuthor());
        String intro = bookObj.getLongIntro();
        if (intro.length() > 50) intro = intro.substring(0, 50);
        intro += "...";
        bookIntro.setText(intro);

        //通过网络获取书籍图标
        final String iconURL = BookService.StaticsUrl +  bookObj.getCover();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(iconURL);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(10000);
                    if (connection.getResponseCode() == 200) {
                        InputStream inputStream = connection.getInputStream();
                        final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageBitmap(bitmap);
                            }
                        });
                    }
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }
};
```

#### （11）点击推荐书籍，跳转到详情界面

```java
adapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
    @Override
    public void onClick(int position) {
        //跳转到书籍详情界面
        Intent intent = new Intent(RecomActivity.this, BookDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("bookobj", bookObjs.get(position));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onLongClick(int position) {

    }
});
```

### 3. 实验遇到的困难以及解决思路

#### （1）网络访问异步调用发生错误

主线程使用数据更新UI，但实际上还未获取到数据。

**解决方法：**

BookService中对每个对象维护一个实例，接口函数中使用Response<T>.body()给对象赋值，确保返回对象时已经获取到数据。

新建一个线程，在新线程中调用BookService提供的函数。

---

## 四、实验思考及感想

---

### 1. 同步的网络访问

网络访问的接口返回Call<T>，调用接口时使用Call<T>.excute()获得Response<T>，调用Response<T>.body()获取数据对象。

### 2. 可重用代码

本项目中，书籍信息对象可在多处使用，包括排行榜和分类界面的列表、网络访问返回的对象。

## 五、个人贡献

### 1. 网络访问API

### 2. 书籍详情界面UI及逻辑

### 3. 相关书籍界面UI及逻辑

### 4. 编写接口文档和用户手册