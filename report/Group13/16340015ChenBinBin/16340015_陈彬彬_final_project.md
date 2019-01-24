# 中山大学数据科学与计算机学院本科生实验报告
## （2018年秋季学期）
| 课程名称 | 手机平台应用开发 |   任课老师   |      郑贵锋      |
| :------: | :--------------: | :----------: | :--------------: |
|   年级   |       16级       | 专业（方向） |       计应       |
|   学号   |     16340015     |     姓名     |      陈彬彬      |
|   电话   |   13590883387    |    Email     | 944131226@qq.com |
| 开始日期 |    2018.12.20    |   完成日期   |    2019.01.19    |

---

## 一、实验题目

 **Android小说阅读器FTEReader**

---

## 二、个人实现内容

### (1)分类界面

根据API给出的分类详情，将小说分类具体如下：

- 分类总体区分为男生与女生两个大类
- 男生里面的小说分类有：玄幻、奇幻、武侠、仙侠、都市、职场等
- 女生里面的小说分类有：古代言情、现代言情、青春校园、纯爱等

UI分类界面设置详情：

- 左右滑动页面可以切换男女生大类
- 每个页面帧有对应男女分类的各个小说分类
- 点击每个分类进入书籍分类列表界面TODO

### (2)数据库用户阅读习惯表

按照需求分析，保留用户的阅读习惯：

- 横竖屏阅读状况
- 黑夜/白日模式
- 文字大小

根据需求，设计相应的用户阅读状况保存类 `UserStatusObj` ：

- user_id : int
- hor_or_ver_screen : int
- day_or_night_status : int
- textSize : int

设置相应的数据库表格，实现初始化以及 `get && set ` 接口

```java
public UserStatusObj get_User_Status_Obj(int user_id);
public void updateStatus(int user_id, UserStatusObj u);
```

### (3)小说列表项UI设计

设置美观的小说书籍RecyclerView列表项的UI，给整个项目重复使用，包含以下内容：

- 小说封面
- 小说名
- 小说作者
- 小说所属分类
- 小说短简介

### (4)阅读界面

实现阅读小说章节：

- 异常处理：如未联网返回书架界面等
- 阅读界面能加载显示对应小说的章节内容：章节名+章节内容
- 阅读界面初始加载能返回到之前阅读到的章节数
- 阅读界面能使用之前用户的阅读习惯重新加载内容：例如用户习惯阅读的字体大小，习惯的夜间/白日模式等
- 初始缓存当前阅读章节数的上下十章节
- 后台加载小说内容时使用ProgressBar加载中
- 左右滑动页面可以切换到上一章/下一章
- 当缓存的章节不够看的时候，可以加载更多章节

全屏阅读和底部信息栏：

- 全屏阅读，取消顶部的系统状态栏
- 底部信息栏实现：当前系统电量+当前系统时间+已阅读/总章节数信息

底部功能栏实现：

- 点击屏幕中央部分弹出与消失功能栏

- 功能栏具体阅读功能实现：

  - 夜间/日间模式切换

  - 横屏/竖屏模式切换

  - 字体大小设置：

    弹出字体调整Dialog，点击+/-按钮设置字体大小

  - 目录跳转功能：

    弹出目录Dialog

    显示书籍名称与所有章节目录

    用户通过点击目录章节跳转到指定位置

---

## 三、实验结果
### (1)实验截图

**分类界面**

![](https://chenbb-1257745007.cos.ap-guangzhou.myqcloud.com/blog/20190124101546.png)

![](https://chenbb-1257745007.cos.ap-guangzhou.myqcloud.com/blog/20190124101609.png)



**小说列表项UI**

小说列表项UI多处重用，以书架列表举例

![](https://chenbb-1257745007.cos.ap-guangzhou.myqcloud.com/blog/20190124135227.png)



**阅读界面**

以阅读《斗罗大陆》为例子：

初始化加载中 

![](https://chenbb-1257745007.cos.ap-guangzhou.myqcloud.com/blog/20190124102043.png)

小说章节内容阅读

- 上下滑动阅读本章节内容
- 左右滑动切换上下章节内容
- 底部信息栏展示：电量+时间+阅读进度

![](https://chenbb-1257745007.cos.ap-guangzhou.myqcloud.com/blog/20190124102144.png)

![](https://chenbb-1257745007.cos.ap-guangzhou.myqcloud.com/blog/20190124102301.png)

![](https://chenbb-1257745007.cos.ap-guangzhou.myqcloud.com/blog/20190124102335.png)

阅读功能栏

- 夜间/日间模式切换

![](https://chenbb-1257745007.cos.ap-guangzhou.myqcloud.com/blog/20190124102712.png)



- 横屏/竖屏模式切换

![](https://chenbb-1257745007.cos.ap-guangzhou.myqcloud.com/blog/20190124103436.png)



- 字体大小设置功能

![](https://chenbb-1257745007.cos.ap-guangzhou.myqcloud.com/blog/20190124102928.png)

![](https://chenbb-1257745007.cos.ap-guangzhou.myqcloud.com/blog/20190124102958.png)



- 目录跳转功能

![](https://chenbb-1257745007.cos.ap-guangzhou.myqcloud.com/blog/20190124103312.png)

![](https://chenbb-1257745007.cos.ap-guangzhou.myqcloud.com/blog/20190124103337.png)



***

### (2)实验步骤以及关键代码

##### (a) 分类界面UI及后台响应实现 

**具体实现步骤** 

- 分类界面帧 `fragment_category.xml` 大体由 `RadioGroup` + `ViewPager` 组成
- `ViewPager` 由两个具体分类帧 `fragment_male_in_category.xml` 组成，每个帧里面是一个`RecyclerView` ，根据API给出的对应的小说分类，写死进 `RecyclerView` 中。
- 完成小说分类的 `RecyclerView` 的单列表项UI，居中显示一个小按钮，加粗分类名以及小字体的该分类下的小说数目。
- 完成男女分类项帧的后台响应，`RecyclerView` 数据绑定，进行网络连接判断处理异常。

**关键代码**

判断是否联网

```java
// 辅助函数：判断网络是否连接
public boolean isNetWorkConnected(Context context) {
    if (context != null) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context
            .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo != null) {
            return mNetworkInfo.isConnected();
        }
    }
    return false;
}
```

获取分类数据（含联网失败的异常处理）

```java
// 获取分类数据
private void getMyCategories() {
    boolean isNetWorkConnected = isNetWorkConnected(getActivity());
    bookService = BookService.getBookService();
    if(isNetWorkConnected) {
        System.out.println("网络连接状况：已连接");
        // 调用子线程进行访问，获取一级、二级分类信息
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                classificationObj2 = bookService.getClassification2();
                Looper.prepare();
                if(classificationObj2 == null) {
                    Toast.makeText(getActivity(), "获取失败", Toast.LENGTH_SHORT).show();
                }
                Looper.loop();
            }
        });
        thread.start();
    }
    else {
        System.out.println("网络连接状况：未连接");
        Toast.makeText(getActivity(), "网络连接状况：未连接", Toast.LENGTH_SHORT).show();
    }
    myCategories = new ArrayList<>();
    // 男生向小说分类
    if(isMale) {
        for(int i = 0; i < maleCategoriesName.length; i++) {
            CategoryRecyObj c = new CategoryRecyObj(maleCategoriesName[i], maleCategoriesBookCount[i]);
            myCategories.add(c);
        }
    }
    // 女生向小说分类
    else {
        for(int i = 0; i < femaleCategoriesName.length; i++) {
            CategoryRecyObj c = new CategoryRecyObj(femaleCategoriesName[i], femaleCategoriesBookCount[i]);
            myCategories.add(c);
        }
    }
}
```

设置 `RecyclerView` 适配：

```java
// 设置 RecyclerView
private void setRecyclerView() {
    // 获取页面的 RecyclerView 控件
    recyclerView = view.findViewById(R.id.fragment_male_in_category_recyclerview);

    // 设置 RecyclerView 的布局方式
    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

    // 设置 Adapter 配置
    adapter = new MyRecyclerViewAdapter<CategoryRecyObj>(getActivity(), R.layout.item_category, myCategories) {
        @Override
        public void convert(MyViewHolder holder, CategoryRecyObj categoryRecyObj) {
            TextView categoryName = holder.getView(R.id.item_category_name);
            categoryName.setText(categoryRecyObj.getCategoryName());
            TextView categoryBookCount = holder.getView(R.id.item_category_count);
            categoryBookCount.setText(categoryRecyObj.getBookCount());
        }
    };

    // 每个主类按钮的点击响应处理
    adapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
        @Override
        public void onClick(int position) {
            //跳转具体分类界面
            Intent intent = new Intent(getActivity(), CategoryActivity.class);
            Bundle bundle = new Bundle();
            bundle.putBoolean("isRanking", false);
            bundle.putBoolean("isMale", isMale);
            if (isMale) bundle.putString("title", maleCategoriesName[position]);
            else bundle.putString("title", femaleCategoriesName[position]);
            intent.putExtras(bundle);
            startActivity(intent);
        }

        @Override
        public void onLongClick(int position) {

        }
    });

    // 给 RecyclerView 适配 Adapter
    recyclerView.setAdapter(adapter);
}
```

***

##### (b) 数据库用户阅读习惯表实现

**用户阅读习惯封装类实现**

```java
public class UserStatusObj {
    int user_id;
    int hor_or_ver_screen;
    int day_or_night_status;
    int textSize;
	// get && set method
}
```

**数据库表格实现并初始化**

```java
// 阅读器状态保存
String CREATE_TABLE3 = "CREATE TABLE if not exists "
    + TABLE_NAME3
        + " (user_id INTEGER PRIMARY KEY, hor_or_ver_screen INTEGER, day_or_night_status INTEGER, textSize INTEGER)";
sqLiteDatabase.execSQL(CREATE_TABLE3);
// 往阅读器状态表格中保存一条初始阅读器状态
UserStatusObj u = new UserStatusObj(0,1,0,18);
values.clear();
values.put("user_id", 0);           // 用户id为0，表示默认状态
values.put("hor_or_ver_screen", u.getHor_or_ver_screen()); // 1表示竖屏，0表示横屏
values.put("day_or_night_status", u.getDay_or_night_status()); // 0表示日间，1表示夜间
values.put("textSize", u.getTextSize()); // 字体大小
sqLiteDatabase.insert(TABLE_NAME3,null,values);
```

**数据库get&&set函数接口**

```java
public UserStatusObj get_User_Status_Obj(int user_id) {
    SQLiteDatabase db = getWritableDatabase();
    String id = Integer.toString(user_id);
    String sql = String.format("SELECT * FROM " + TABLE_NAME3  + " where user_id='%s'" , id);
    Cursor cursor = db.rawQuery(sql, null);
    UserStatusObj res = null;
    while (cursor.moveToNext()) {
        int hor_or_ver_screen = cursor.getInt(cursor.getColumnIndex("hor_or_ver_screen"));
        int day_or_night_status = cursor.getInt(cursor.getColumnIndex("day_or_night_status"));
        int textSize = cursor.getInt(cursor.getColumnIndex("textSize"));
        res = new UserStatusObj(user_id, hor_or_ver_screen, day_or_night_status, textSize);
        return res;
    }
    cursor.close();
    db.close();
    return res;
}
```

```java
// 更新用户阅读器习惯的状态
public void updateStatus(int user_id, UserStatusObj u) {
    SQLiteDatabase db = getWritableDatabase();
    ContentValues value = new ContentValues();
    value.put("hor_or_ver_screen", u.getHor_or_ver_screen()); // 1表示竖屏，0表示横屏
    value.put("day_or_night_status", u.getDay_or_night_status()); // 0表示日间，1表示夜间
    value.put("textSize", u.getTextSize()); // 字体大小
    db.update(TABLE_NAME3, value, "user_id=?", new String[] { Integer.toString(user_id)});
    db.close();
}
```

***

##### (c) 小说列表项UI设计

按照需求样式设计较为美观的小说列表项，包含居中调整，字体样式、大小调整等。总体是左边封面图，右边详细信息。（UI代码略）

***

##### (d) 阅读界面UI及后台响应实现

**UI部分**

- `activity_read_page.xml` 主要由章节内容帧 `ViewPager` +底部信息栏+底部功能栏+显示加载中 `ProgressBar` 实现
- `ViewPager` 的章节内容帧 `fragment_read_page.xml` 由标题+内容两个`TextView` 和 `ScrollView` 上下滑动条组成。
- 底部信息栏包含左部的系统电量+系统时间信息，右部的阅读进度信息。
- 底部功能栏有四个功能，由 `RadioGroup` 实现，包含夜间/日间模式+横竖屏模式+字体大小设置+章节目录功能按钮。
- UI代码略

**后台部分(含关键代码)** 

- **获取页面跳转信息** ：包括小说id，小说名，当前阅读章节

```java
//从传递的参数中获取章节相关信息
Intent intent = this.getIntent();
Bundle bundle = intent.getExtras();
bookid = bundle.getString("bookid");
bookname = bundle.getString("bookname");
currChapter = bundle.getInt("currentChapter");
```

- **获取页面控件** ：通过 `findViewById` 获取页面控件控制
- **从数据库获取用户阅读习惯**

```java
// 从数据库中获取用户的阅读习惯
private void set_Reading_Status() {
    // 获取用户状态(默认用户状态)
    userStatusObj = DatabaseControl.getInstance(this).get_User_Status_Obj(0);
    // 获取默认用户下的横竖屏状态
    int hor_or_ver_screen = userStatusObj.getHor_or_ver_screen();
    if(hor_or_ver_screen == 1) {
        is_vertical_screen = true;
    }
    else {
        is_vertical_screen = false;
        // 切换成横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }
    day_or_night_status = userStatusObj.getDay_or_night_status();
    if(day_or_night_status == 1) whole_layout_control.setBackgroundColor(getResources().getColor(R.color.nightBackGround));
    textSize = userStatusObj.getTextSize();
}
```

​	当然，当退出阅读界面的时候，也要更新数据库，写入用户当前的阅读习惯，页要写入用户当前阅读到的章节数：

```java
@Override
protected void onPause() {
    System.out.println("onPause");
    // 将阅读到的当前章节存入数据库
    DatabaseControl.getInstance(this).updateProgress(currChapter, bookid);
    userStatusObj.setDay_or_night_status(day_or_night_status);
    userStatusObj.setHor_or_ver_screen((is_vertical_screen?1:0));
    userStatusObj.setTextSize(textSize);
    DatabaseControl.getInstance(this).updateStatus(0,userStatusObj);
    super.onPause();
}
```



- **获取APP当前显示屏幕宽高** 

```java
// 获取屏幕宽高等信息
private void get_screen_info() {
    DisplayMetrics dm = getResources().getDisplayMetrics();
    SCREEN_HEIGHT = dm.heightPixels;
    SCREEN_WIDTH = dm.widthPixels;
}
```

- **底部信息栏实现** ： 通过系统广播更新，订阅广播实现获取电量和时间

```java
// 注册底部信息栏的系统接收广播
private void init_info_broadcast() {
    //注册广播接受者java代码
    intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);    // 电量变化广播
    intentFilter.addAction(ACTION_TIME_TICK);
    //创建广播接受者对象
    myReceiver = new MyReceiver();
    //注册receiver
    registerReceiver(myReceiver, intentFilter);
}
```

```java
// 重载onDestroy()中注销广播
unregisterReceiver(myReceiver);
```

```java
// 注册获取系统广播
// 广播获取系统电量和时间
class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        // 判断它是否是为电量变化的Broadcast Action
        // 电量变化广播
        if(Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())){
            //获取当前电量
            int level = intent.getIntExtra("level", 0);
            //电量的总刻度
            int scale = intent.getIntExtra("scale", 100);
            //把它转成百分比
            int percent = level*100/scale;
            battery_percent_control.setText(Integer.toString(percent));
        }
        else if (Intent.ACTION_TIME_TICK.equals(intent.getAction())) {
            // 设置时间格式
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
            // 获取当前时间
            Date curdate = new Date(System.currentTimeMillis());
            // 按照格式将Date 时间转化成格式字符串
            String time = formatter.format(curdate);
            // UI设置显示
            time_control.setText(time);
        }
    }

}
```

- **网络访问获取小说章节内容**  (初始加载上下10章内容)：

  封装在一个子函数中：`private void init_fragment() ` 

  主要实现是将网络访问获取的一章章的内容做成 `ViewPager` 的一个个帧

  该函数的具体步骤如下：

  - 设置等待进度条，隐藏 `ViewPager` 
  - 清空与 `ViewPager` 绑定的数据 `List` 
  - 启用子线程进行网络访问
  - 子线程中：
    - 网络连接判断
    - 再启用子子线程进行获取上下十个章节内容
    - 等待子子线程网络访问结束
    - 对获取的文字数据进行处理
    - 按照章节信息生成一个个章节内容帧
    - ViewPager适配处理后的帧数据
    - RXJAVA2 更新界面UI，显示ViewPager内容，隐藏进度条

  关键代码：

  子子线程获取章节内容:使用 `BookService` 

```java
//获取到:章节总页数totalPage、章节title、每页的内容content
Thread initContentThread  = new Thread(new Runnable() {
    @Override
    public void run() {
        // 获取所有章节信息
        cptListObj = BookService.getBookService().getChaptersByBookId(bookid);
        if(cptListObj != null) {
            chapterLinks = cptListObj.getImixToc().getChapterLinks();
            totalChapter = chapterLinks.size();
            System.out.println("共有章节数目： " + totalChapter);
            chaptersContent = new ArrayList<>(totalChapter);

            // 缓存当前章节以及上下10章的数据
            chapterObjs = new ArrayList<ChapterObj>(totalChapter) {
            };
            cache_chapter_range_min = currChapter - 10;
            cache_chapter_range_max = currChapter + 10;
            for (int i = cache_chapter_range_min; i <= cache_chapter_range_max; i++) {
                System.out.println("正在下载（zero-based）： i---" + i + "  min---" + cache_chapter_range_min + "---max---" + cache_chapter_range_max);
                //超出章节范围
                if (i > totalChapter - 1) {
                    cache_chapter_range_max = totalChapter - 1;
                    break;
                }
                if (i < 0) {
                    cache_chapter_range_min++;
                    continue;
                }
                ChapterObj c = BookService.getBookService().getChapterByLink(chapterLinks.get(i).getLink());
               
                chapterObjs.add(i - cache_chapter_range_min, c);
            }
            System.out.println(cache_chapter_range_min + "----" + cache_chapter_range_max);
        }
        else {
            Looper.prepare();
            // 弹出Toast提示
            Toast.makeText(ReadPageActivity.this, "无法获取书籍源", Toast.LENGTH_SHORT).show();
            ReadPageActivity.this.finish();
            Looper.loop();
        }
    }
});
initContentThread.start();
```

​		等待子子线程更新完毕再适配ViewPager

```java
try {
    initContentThread.join();
} catch (InterruptedException e) {
    Log.d("[Error] ", "线程获取信息失败");
    e.printStackTrace();
}
```

​		处理网络访问得到的章节内容数据，并处理成章节帧

```java
// 根据内容适配各帧
for (int i = cache_chapter_range_min; i <= cache_chapter_range_max; ++i) {
    // 解析章节内容
    String title = chapterLinks.get(i).getTitle();
    String content;
    if(chapterObjs.get(i-cache_chapter_range_min) == null) {
        content = "章节获取失败了呢！客官";
    }
    else content = chapterObjs.get(i-cache_chapter_range_min).getIchapter().getBody();
    // 为段首添加缩进
    content = "\u3000\u3000" + content;
    content = content.replaceAll("\n", "\n\u3000\u3000");
    // 新建对应章节内容帧
    ReadPageFragment fragment = new ReadPageFragment();
    // 给帧传数据
    Bundle bundle = new Bundle();
    bundle.putString("title", title);
    bundle.putString("content", content);
    bundle.putInt("day_or_night_status", day_or_night_status);
    bundle.putInt("textSize", textSize);
    fragment.setArguments(bundle);
    // 将新加的帧放入队列中
    fragmentList.add(fragment);
}

viewPager.setOnPageChangeListener(new MyPagerChangeListener());
fragmentAdapter = new TabFragmentStatePagerAdapter(getSupportFragmentManager(), fragmentList);

// 用rxjava更新主线程
rxjava_update_page(0);
```

- **章节内容帧界面实现**  ： `ReadPageFragment.java`

  主要实现是：

  将传递到帧界面的标题，页面内容等适配到各控件

  另外还有后续的个性化功能例如夜间/白日功能，字体大小设置功能，也通过在这里设置控件信息来实现章节内容阅读帧的生成

```java
private void init_page_info() {
    titile_control.setText(title);      // 设置标题
    content_control.setText(content);   // 设置阅读页内容
    content_control.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
    if(day_or_night_status == 0) {
        whole_layout_control.setBackgroundColor(getResources().getColor(R.color.PapayaWhip));
    }
    else whole_layout_control.setBackgroundColor(getResources().getColor(R.color.nightBackGround));
}
```

- **前/后翻页实现缓存更多章节** 

  具体实现是：

  监听 `ViewPager` 的滑动事件：

  `public class MyPagerChangeListener implements ViewPager.OnPageChangeListener` 

  记录有当前缓存的章节数目区间：

  `[cache_chapter_range_min, cache_chapter_range_max]` 

  当现在阅读到离缓存界限还剩下`cache_left:5` 章的时候，进行新的网络访问，获取更多的`cache_num:10` 章节的内容。

  以往后翻页，获取后10章节内容为例子的具体流程是：

  - 往后翻页，当前阅读到的章节数改变，`currChapter++;`
  - 当达到缓存界限的时候，触发网络访问更新缓存内容
  - 与 `init_fragment` 类似通过子线程和子子线程获取缓存后十章的内容，然后处理文字内容添加段首缩进，转换成章节阅读帧，适配 `ViewPager` 。

  关键代码：

```java
Thread getNewChapterThread = new Thread(new Runnable() {
    @Override
    public void run() {
        if(isNetWorkConnected(context)) {
            for (int i = 1; i <= cache_num; i++) {
                int newChapterNum = cache_chapter_range_max + i;
                // 非法章节数(已超出网络给出的章节数)
                if (newChapterNum >= totalChapter) continue;
                // 合法章节则获取新章节
                ChapterObj c = BookService.getBookService().getChapterByLink(chapterLinks.get(newChapterNum).getLink());
                chapterObjs.add(newChapterNum - cache_chapter_range_min, c);
            }
        }
        else {
            Log.e("ERROR", "网络连接状况：未连接");
        }
    }
});
getNewChapterThread.start();
```

```java
// 新增N个帧
for(int i = 1; i <= cache_num; i++) {
    int newChapterNum = cache_chapter_range_max + 1;
    // 已经超出总章节数
    if(newChapterNum >= totalChapter) continue;
    // 网络连接故障，要获取的章节未缓存成功，不添加新帧
    if(newChapterNum - cache_chapter_range_min >= chapterObjs.size()) break;
    // 合法的章节数
    cache_chapter_range_max++;
    // 解析章节内容
    String title = chapterLinks.get(newChapterNum).getTitle();
    String content;
    // TODO: 后面跟init_fragment()类似
```

***

##### (e) 阅读界面个性化功能实现

个性化功能的切换主要是阅读帧界面的控件样式变换：例如夜间/日间模式切换与文字大小切换，都是转换帧的格式，因此设置一个`changeFrameStyle()` 子函数来实现重新加载帧。

 ```java
// 当使用功能按键切换阅读界面阅读习惯时
// 不必要重新进行网络访问
// 直接改变阅读帧的样式
public void changeFrameStyle() {
    // 设置等待进度条
    progressBar.setVisibility(View.VISIBLE);
    viewPager.setVisibility(View.GONE);
    // 清空当前的FragmentList
    fragmentList.clear();
    for(int i = cache_chapter_range_min; i<= cache_chapter_range_max; i++) {
        // 解析章节内容
        String title = chapterLinks.get(i).getTitle();
        String content;
        if(chapterObjs.get(i-cache_chapter_range_min) == null) {
            content = "章节获取失败了呢！客官";
        }
        else content = chapterObjs.get(i-cache_chapter_range_min).getIchapter().getBody();
        // 为段首添加缩进
        content = "\u3000\u3000" + content;
        content = content.replaceAll("\n", "\n\u3000\u3000");
        // 新建对应章节内容帧
        ReadPageFragment fragment = new ReadPageFragment();
        // 给帧传数据
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("content", content);
        bundle.putInt("day_or_night_status", day_or_night_status);
        bundle.putInt("textSize", textSize);
        fragment.setArguments(bundle);
        // 将新加的帧放入队列中
        fragmentList.add(fragment);
    }
    rxjava_update_page(2);
}
 ```



**夜间/日间模式**

简单根据当前的夜间/日间模式，调用`changeFrameStyle`更新阅读帧样式。另外还要修改图标与图标文字。代码略。

**横屏/竖屏模式**

```java
// 横屏竖屏功能切换
horizontal_and_vertical_rb_control.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        // 当前竖屏状态
        if(is_vertical_screen) {
            // 记录状态数据转变,更新默认用户
            is_vertical_screen = false;
            horizontal_and_vertical_rb_control.setTextColor(Color.BLACK);
            // 切换成横屏
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            Drawable drawable = getResources().getDrawable(R.mipmap.vertical_screen);
            drawable.setBounds(0, 0, 70, 70);
            horizontal_and_vertical_rb_control.setCompoundDrawables(null, drawable, null,null);
            horizontal_and_vertical_rb_control.setText("竖屏");
            horizontal_and_vertical_rb_control.setTextColor(Color.BLACK);
        }
        // 当前横屏状态
        else {
            // 记录状态数据转变
            is_vertical_screen = true;
            horizontal_and_vertical_rb_control.setTextColor(Color.BLACK);
            System.out.println("改成竖屏");
            System.out.println(DatabaseControl.getInstance(context).get_Hor_Or_Ver_Screen_Status(0));
            // 切换成竖屏状态
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            Drawable drawable = getResources().getDrawable(R.mipmap.horizontal_screen);
            drawable.setBounds(0, 0, 70, 70);
            horizontal_and_vertical_rb_control.setCompoundDrawables(null, drawable, null,null);
            horizontal_and_vertical_rb_control.setText("横屏");
            horizontal_and_vertical_rb_control.setTextColor(Color.BLACK);
        }
    }
});
```

**文字字体设置**

弹出一个自定义文字字体大小设置Dialog。

设计这个弹窗Dialog

![](https://chenbb-1257745007.cos.ap-guangzhou.myqcloud.com/blog/20190124124633.png)



根据当前APP应用屏幕宽高调整Dialog的宽高。

点击弹窗里+/-大小改变按钮，简单调用`changeFrameStyle` 更改帧样式。

代码省略。

**章节目录展示和跳转功能**

弹出一个自定义章节目录显示Dialog

设计这个弹窗Dialog

![](https://chenbb-1257745007.cos.ap-guangzhou.myqcloud.com/blog/20190124124748.png)

根据当前APP屏幕宽高调整Dialog的宽高。也即适配横竖屏。

主要构成是 `ScrollView` + `RecyclerView` 

设置点击目录item跳转到相应的章节。

关键代码：

```java
// 目录功能
catalog_rb_control.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        // 弹出一个目录选择框
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.catalog_dialog);
        // 隐藏功能按钮控件框
        rg_control.setVisibility(View.GONE);
        show_functional_button = false;
        bottom_layout_control.setVisibility(View.VISIBLE);
        // 字体不变红色
        catalog_rb_control.setTextColor(Color.BLACK);
        // 设置dialog标题为书名
        TextView title = dialog.findViewById(R.id.catalog_title);
        title.setText(bookname);
        // 书籍目录RecyclerView
        RecyclerView dialog_catalog = dialog.findViewById(R.id.catalog_recylerView);
        // 书籍目录数据初始化
        myCategory = new ArrayList<>();
        for(int i = 0; i < chapterLinks.size(); i++)
            myCategory.add(chapterLinks.get(i).getTitle());

        // 设置 Adapter
        adapter = new MyRecyclerViewAdapter<String>(context, R.layout.item_catalog, myCategory) {
            @Override
            public void convert(MyViewHolder holder, String s) {
                TextView title = holder.getView(R.id.catalog_title);
                title.setText(s);
            }
        };
        // 设置点击目录跳转到相应章节
        adapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                // dialog目录框消失
                dialog.dismiss();
                // 设置等待进度条
                viewPager.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                clickable = false;
                // 清空当前的FragmentList
                fragmentList.clear();
                // 设置跳转章节数
                currChapter = position;
                // 加载帧
                init_fragment();
            }

            @Override
            public void onLongClick(int position) {

            }
        });

        final WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = (int)(SCREEN_WIDTH * 2 / 3);
        params.height = (int)(SCREEN_HEIGHT * 2 / 3);
        dialog.getWindow().setAttributes(params);

        // 适配 Adapter
        dialog_catalog.setAdapter(adapter);

        // 设置 RecyclerView 布局
        dialog_catalog.setLayoutManager(new LinearLayoutManager(context));

        // 设置 dialog 属性并显示
        dialog.setCancelable(true);
        dialog.show();
    }
});
```

***

### (3)实验遇到的困难以及解决思路

- 如何判断联网

  参考博客：[Android中判断网络连接是否可用及监控网络状态](https://www.cnblogs.com/fnlingnzb-learner/p/7531811.html) 

  解决方案：使用 `ConnectivityManager` 

```java
// 辅助函数：判断网络是否连接
public boolean isNetWorkConnected(Context context) {
    if (context != null) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context
            .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo != null) {
            return mNetworkInfo.isConnected();
        }
    }
    return false;
}
```

- 获取屏幕宽高

  参考博客：[Android获取屏幕宽度的4种方法](https://blog.csdn.net/csdn_conda/article/details/80097885) 

  解决方案：使用 `DisplayMetrics` 

```java
// 获取屏幕宽高等信息
private void get_screen_info() {
    DisplayMetrics dm = getResources().getDisplayMetrics();
    SCREEN_HEIGHT = dm.heightPixels;
    SCREEN_WIDTH = dm.widthPixels;
}
```

- 获取系统电量和时间

  参考博客：[Android中获取电池电量](https://www.cnblogs.com/dyllove98/p/3146958.html) 

  解决方案：

  - 对于获取系统电量，使用系统广播，在广播订阅处处理，获取系统电量，然后进行处理展示
  - 同样地，对于获取系统事件，也是通过广播，在广播订阅处处理，获取系统时间，然后进行页面处理。

```java
// 注册底部信息栏的系统接收广播
private void init_info_broadcast() {
    //注册广播接受者java代码
    intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);    // 电量变化广播
    intentFilter.addAction(ACTION_TIME_TICK);
    //创建广播接受者对象
    myReceiver = new MyReceiver();
    //注册receiver
    registerReceiver(myReceiver, intentFilter);
}
```

```java
// 重载onDestroy()中注销广播
unregisterReceiver(myReceiver);
```

```java
// 注册获取系统广播
// 广播获取系统电量和时间
class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        // 判断它是否是为电量变化的Broadcast Action
        // 电量变化广播
        if(Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())){
            //获取当前电量
            int level = intent.getIntExtra("level", 0);
            //电量的总刻度
            int scale = intent.getIntExtra("scale", 100);
            //把它转成百分比
            int percent = level*100/scale;
            // TODO： DO Something
        }
        else if (Intent.ACTION_TIME_TICK.equals(intent.getAction())) {
            // 获取当前时间
            Date curdate = new Date(System.currentTimeMillis());
            // TODO： DO Something
        }
    }
}
```
- 将时间Date类转成指定格式String显示

  参考博客：[获取android中的 SimpleDateFormat——获取当前时间](https://blog.csdn.net/mimi5821741/article/details/42025523) 

   解决方案：使用 `SimpleDateFormat` 

```java
// 设置时间格式
SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
// 获取当前时间
Date curdate = new Date(System.currentTimeMillis());
// 按照格式将Date 时间转化成格式字符串
String time = formatter.format(curdate);
// UI设置显示
time_control.setText(time);
```

- 为小说章节内容段首添加等同于两个中文字体间隔的缩进

  参考博客：[Android布局中的空格以及占一个汉字宽度的空格的实现](https://blog.csdn.net/u014651216/article/details/52411113) 

```java
content = content.replaceAll("\n", "\n\u3000\u3000");
```

- 应用横竖屏切换

  参考博客：[Android屏幕横竖屏切换和生命周期管理的详细总结](https://blog.csdn.net/wenzhi20102321/article/details/68941263) 

  解决方案：

  设置屏幕横屏代码：

```java
setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
```

​		设置屏幕竖屏代码：

```java
setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
```



- 横竖屏切换但不重新 `onCreate` 生命周期的实现

  按照上一点横竖屏切换后，会重新调用 `onCreate` 函数，这样不仅需要时间等待重新的网络访问，而且还十分影响用户体验。

  参考博客：[Android屏幕横竖屏切换和生命周期管理的详细总结](https://blog.csdn.net/wenzhi20102321/article/details/68941263) 

  解决方案：

  - 在`AndroidManifest.xml` 中对阅读界面Activity 设置属性(关键)

    ```xml
    android:configChanges="orientation|keyboardHidden|screenSize"
    android:screenOrientation="portrait"
    ```

  - 在 `ReadPageActivity.java` 后台中，重载`onConfigurationChanged` 函数，重新获取屏幕宽高

    ```java
    // 重载onConfigurationChanged函数处理横竖屏切换
    // 使得不必重新回调Activity整个生命周期
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // 重新设置屏幕宽高
        get_screen_info();
        super.onConfigurationChanged(newConfig);
        Log.e("TAG", "onConfigurationChanged");
    }
    ```


- 全屏阅读消除顶部状态栏实现

  参考博客：[Android隐藏顶部状态栏](https://www.cnblogs.com/shen-hua/p/6082957.html) 

  解决方案：

```java
// 全屏阅读，去除手机状态栏
getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
```


- TextView单行超过字体数目显示省略号…的实现

  参考博客：[android开发textview超过多少字显示省略号](https://blog.csdn.net/u010074743/article/details/78974686) 

  解决方案：

```xml
<TextView
    <!--other attr-->
    android:singleLine="true"
    android:maxEms="12"
    android:ellipsize="end"/>
```


- 自定义Dialog宽高大小的设定

  在展示目录弹出框时，得给上下左右留点空白给用户取消点击。

  因而要根据横竖屏去设置Dialog的宽高

  参考博客：[Android 如何设置自定义dialog的大小](https://blog.csdn.net/yijiaodingqiankun/article/details/80896659) 

  解决方案：

```java
final WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
params.width = (int)(SCREEN_WIDTH * 2 / 3);
params.height = (int)(SCREEN_HEIGHT * 2 / 3);
dialog.getWindow().setAttributes(params);
```

---

## 四、实验思考及感想

- 终于的终于，我们小组总算是完整地完成了整个期末大项目，包括代码与文档。也算是费了很大的精力，尤其是赶工在展示前的一周，可以说是没日没夜地在写。而且那段时间也和其他科目的大作业完美重叠了，真是很辛苦地爆肝期，现在写文档的时候倒是比较悠闲自得了，也蛮感慨的。
- 这个实验主要出发点还是做一个比较实在的应用吧，因此我们项目的重点放在UI的设计以及功能逻辑的全面与完善。尽量做一个可以供使用的应用，而不是单纯一个课程作业。在做完之后，自己也发现确实使用体验也不错，平常正常阅读是没问题的，不用去每本小说找txt下载，或者有成堆成堆的广告影响阅读体验。然后自选的后台搭建我们小组是没有使用的。最后选择的这个API，我们之前学期小组作业有用到过的，它比较稳定而且小说内容全面。还有考虑到这是一门安卓课，而不是一门搭建服务器的课程，另外也是最重要的，我们不可能把上十万本小说内容全部爬进来放进服务器，如果单单爬取书籍Id等信息，不爬取章节内容的话，似乎还是要使用它的API，因此还是选择直接全程使用追书神器的API吧。
- 写在最后，这门安卓课让自己也学到了很多吧，算是安卓入门了，每次的作业也有很认真地完成和认真地写个人报告，回看回去收获很大。最后感谢老师和各位TA的付出，提前新春快乐。

---

## 五、个人贡献

- 主活动界面的分类界面UI及后台响应实现
- 阅读界面章节内容展示UI及后台响应实现
- 阅读界面夜间模式、横竖屏切换、字体大小调整、目录跳转功能等个性化阅读功能实现
- 数据库用户阅读习惯表设计和实现
- 小说列表项UI设计与实现
- 项目APK导出
- 项目演示视频录制