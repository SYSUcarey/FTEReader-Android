package fte.finalproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Looper;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fte.finalproject.Fragment.ReadPageFragment;
import fte.finalproject.Fragment.TabFragmentStatePagerAdapter;
import fte.finalproject.control.DatabaseControl;
import fte.finalproject.obj.ChapterLinkObj;
import fte.finalproject.obj.ChapterLinks;
import fte.finalproject.obj.ChapterObj;
import fte.finalproject.obj.CptListObj;
import fte.finalproject.service.BookService;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class ReadPageActivity extends AppCompatActivity {
    private List<Fragment> fragmentList = new ArrayList<>();        //存储所有页面Fragment
    private TabFragmentStatePagerAdapter fragmentAdapter;
    private ViewPager viewPager;

    private String bookid;                      // 书籍id
    private CptListObj cptListObj;            //章节列表
    List<ChapterLinkObj> chapterLinks;          // 章节查询链接List
    List<ChapterObj> chapterObjs;    // 章节内容OBJ队列
    private int currChapter;                   //当前阅读到的章节
    private int currPage;                      //当前阅读到的页面(对一个章节而言)[0, currTotalPage - 1]
    private int currTotalPage;                 //当前章节总页数
    List<List<String>> chaptersContent;        // 每一章每一页的内容
    StringBuffer showContent = new StringBuffer();
    StringBuffer currentChapterContent = new StringBuffer();
    private int pageLen = 370;                  //每一页的字节长度-----匹配18sp字体大小
    int totalChapter;                           // 该书总共的章节数
    int cache_chapter_range_min;                // 当前缓冲存储的章节数范围下界
    int cache_chapter_range_max;                // 当前缓冲存储的章节数范围上界


    private RadioGroup rg_control;
    private RadioButton day_and_night_rb_control;
    private RadioButton horizontal_and_vertical_rb_control;
    private RadioButton setting_rb_control;
    private RadioButton download_rb_control;
    private RadioButton catalog_rb_control;
    private ProgressBar progressBar;

    float SCREEN_HEIGHT;
    float SCREEN_WIDTH;

    boolean show_functional_button = false;

    // 活动上下文
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_page);
        //从传递的参数中获取章节相关信息
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        bookid = bundle.getString("bookid");
        currChapter = bundle.getInt("currentChapter");
        System.out.println("bookid: " + bookid + "  currentChapter: " + currChapter);


        // 获取页面控件
        init_page_control();

        // 获取页面宽高
        get_screen_info();

        // 设置功能按键
        set_functional_button();

        // 获取上下文
        context = this;
        // 全屏阅读，去除手机状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //初始化 Fragment
        init_fragment();
    }


    private void init_fragment() {
        // 用线程进行帧初始化，避免进入阅读界面阻塞
        Thread init_fragment_thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // 判断是否联网了
                Boolean isNetworkConnected = isNetWorkConnected(context);
                // 未联网的响应处理
                if(!isNetworkConnected) {
                    Looper.prepare();
                    // 弹出Toast提示
                    Toast.makeText(ReadPageActivity.this, "网络连接状况：未连接", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                    // ProgressBar等待框消失
                    progressBar.setVisibility(View.GONE);
                }
                // 已经联网，获取书籍信息进行阅读
                else {
                    //获取到:章节总页数totalPage、章节title、每页的内容content
                    Thread initContentThread  = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // 获取所有章节信息
                            cptListObj = BookService.getBookService().getChaptersByBookId(bookid);
                            chapterLinks = cptListObj.getImixToc().getChapterLinks();
                            totalChapter = chapterLinks.size();
                            /*for(int i = 0; i < chapterLinks.size(); i++) {
                                System.out.println(chapterLinks.get(i).getTitle() + ": " + chapterLinks.get(i).getLink());
                            }*/
                            chaptersContent = new ArrayList<>(totalChapter);

                            // 缓存当前章节以及上下10章的数据
                            System.out.println(totalChapter);
                            chapterObjs = new ArrayList<ChapterObj>(totalChapter){};
                            cache_chapter_range_min = currChapter - 10;
                            cache_chapter_range_max = currChapter + 10;
                            for(int i = cache_chapter_range_min; i <= cache_chapter_range_max; i++) {
                                //超出章节范围
                                if(i > totalChapter-1) {
                                    cache_chapter_range_max--;
                                    continue;
                                }
                                if(i < 0) {
                                    cache_chapter_range_min++;
                                    continue;
                                }
                                ChapterObj c = BookService.getBookService().getChapterByLink(chapterLinks.get(i).getLink());
                                System.out.println(chapterLinks.get(i).getLink());
                                chapterObjs.add(i-cache_chapter_range_min, c);
                                System.out.println(i);
                            }
                            System.out.println(cache_chapter_range_min + "----" + cache_chapter_range_max);
                            System.out.println(chapterLinks.size());

                            //currentChapterContent.append(c.getIchapter().getBody());
                            //System.out.println(c.getIchapter().getTitle() + "\n" + c.getIchapter().getBody());
                        }
                    });
                    initContentThread.start();

                    try {
                        initContentThread.join();
                    } catch (InterruptedException e) {
                        Log.d("[Error] ", "线程获取信息失败");
                        e.printStackTrace();
                    }

                    // 将章节内容分页
                    // 512字节长度一页
                    /*currTotalPage = content.length()/pageLen + 1;
                    System.out.println("分成了" + currTotalPage + "页");
                    List<String> pages = getStrList(content, pageLen);
                    System.out.println("已经分割成： " + pages.size() + "页");
                    System.out.println(pages.get(0));*/

                    /*System.out.println("Test: ");
                    int len1  = "l".length();
                    int len2 = "好".length();
                    int len3 = ",".length();
                    int len4 = "，".length();
                    int len5 = "\n".length();
                    String test = "\u3000\u3000大漠。\r\n\u3000\u3000YES";
                    System.out.println(test);
                    int len6 = test.length();
                    System.out.println("长度：" + len1 + " " + len2 + " " + len3 + " " + len4 + " " + len5 + " " + len6);*/

                    // 根据内容适配各帧
                    int count =(cache_chapter_range_max-cache_chapter_range_min+1);
                    System.out.println(count);
                    for (int i = cache_chapter_range_min; i <= cache_chapter_range_max; ++i) {
                        // 解析章节内容
                        //String title = chapterObjs.get(0).getIchapter().getTitle(); // 这种获取Title的内容错误的（API问题）
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
                        bundle.putInt("currentChapter", i);
                        bundle.putInt("totalChapter", totalChapter);
                        fragment.setArguments(bundle);
                        // 将新加的帧放入队列中
                        fragmentList.add(fragment);
                    }

                    viewPager.setOnPageChangeListener(new MyPagerChangeListener());
                    fragmentAdapter = new TabFragmentStatePagerAdapter(getSupportFragmentManager(), fragmentList);

                    // 用rxjava更新主线程
                    rxjava_update_page(0);

                }
            }
        });
        init_fragment_thread.start();

    }


    // 获取屏幕宽高等信息
    private void get_screen_info() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        SCREEN_HEIGHT = dm.heightPixels;
        SCREEN_WIDTH = dm.widthPixels;
        /*String str = "屏幕宽高：" + SCREEN_WIDTH + ":" + SCREEN_HEIGHT;
        Toast.makeText(ReadPageActivity.this, str, Toast.LENGTH_SHORT).show();*/
    }

    /*@Override
    protected void onPause() {
        System.out.println("onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        System.out.println("onStop");
        super.onStop();
    }*/

    @Override
    protected void onDestroy() {
        System.out.println("onDestroy");
        // 将阅读到的当前章节存入数据库
        System.out.println("阅读到：" + currChapter);
        DatabaseControl.getInstance(this).updateProgress(currChapter, bookid);
        super.onDestroy();
    }

    /*@Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        float x = 0, y = 0;
        if(MotionEvent.ACTION_DOWN == event.getAction()) {
            x = event.getX();
            y = event.getY();
        }
        // 点击中间区域，弹出功能按键
        if(x > SCREEN_WIDTH/3 && x < SCREEN_WIDTH*2/3 && y > SCREEN_HEIGHT/5 && y < SCREEN_HEIGHT*4/5) {
            System.out.println("中间翻页");
            if(show_functional_button) {
                rg_control.setVisibility(View.GONE);
                show_functional_button = false;
            }
            else {
                rg_control.setVisibility(View.VISIBLE);
                show_functional_button = true;
            }
            return true;
        }
        else if(x <= SCREEN_WIDTH/3) {
            //Toast.makeText(ReadPageActivity.this, "向上翻页", Toast.LENGTH_SHORT).show();
            System.out.println("向上翻页");
            return true;
        }
        else {
            //Toast.makeText(ReadPageActivity.this, "向下翻页", Toast.LENGTH_SHORT).show();
            System.out.println("向下翻页");
            return true;
        }
        //return super.dispatchTouchEvent(event);
    }*/

    // 处理屏幕点击事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = 0, y = 0;
        if(MotionEvent.ACTION_DOWN == event.getAction()) {
            x = event.getX();
            y = event.getY();
            if(x > SCREEN_WIDTH/3 && x < SCREEN_WIDTH*2/3 && y > SCREEN_HEIGHT/5 && y < SCREEN_HEIGHT*4/5) {
                System.out.println("中间" + x + ":" + y);
                if(show_functional_button) {
                    rg_control.setVisibility(View.GONE);
                    show_functional_button = false;
                }
                else {
                    rg_control.setVisibility(View.VISIBLE);
                    show_functional_button = true;
                }

            }
            else if(x <= SCREEN_WIDTH/3) {
                Toast.makeText(ReadPageActivity.this, "向上翻页", Toast.LENGTH_SHORT).show();

            }
            else {
                Toast.makeText(ReadPageActivity.this, "向下翻页", Toast.LENGTH_SHORT).show();
            }
        }


        return super.onTouchEvent(event);
    }

    // 设置功能按键
    private void set_functional_button() {
        // 设置底部功能按钮的大小
        Drawable drawable = getResources().getDrawable(R.mipmap.nighttime);
        drawable.setBounds(0, 0, 70, 70);
        day_and_night_rb_control.setCompoundDrawables(null, drawable , null,null);
        drawable = getResources().getDrawable(R.mipmap.horizontal_screen);
        drawable.setBounds(0, 0, 70, 70);
        horizontal_and_vertical_rb_control.setCompoundDrawables(null, drawable, null,null);
        drawable = getResources().getDrawable(R.mipmap.textsize);
        drawable.setBounds(0, 0, 70, 70);
        setting_rb_control.setCompoundDrawables(null, drawable,null, null);
        drawable = getResources().getDrawable(R.mipmap.download);
        drawable.setBounds(0, 0, 70, 70);
        download_rb_control.setCompoundDrawables(null, drawable,null, null);
        drawable = getResources().getDrawable(R.mipmap.catalog);
        drawable.setBounds(0, 0, 70, 70);
        catalog_rb_control.setCompoundDrawables(null, drawable,null, null);
    }

    // 获取页面控件
    private void init_page_control() {
        rg_control = findViewById(R.id.read_page_bottom_rg);
        day_and_night_rb_control = findViewById(R.id.read_page_day_and_night_rb);
        horizontal_and_vertical_rb_control = findViewById(R.id.read_page_horizontal_and_vertical_rb);
        setting_rb_control = findViewById(R.id.read_page_setting_rb);
        download_rb_control = findViewById(R.id.read_page_download_rb);
        catalog_rb_control = findViewById(R.id.read_page_catalog_rb);
        viewPager = findViewById(R.id.read_page_viewPager);
        progressBar = findViewById(R.id.read_page_progressbar);
    }

    //设置一个ViewPager的监听事件，左右滑动ViewPager时进行处理
    //当滑动到当前缓存的倒数第M章的时候，进行网络资源访问，获取新的N章的资源
    //当滑动到当前缓存的第一页时
    //需要添加新的Fragment
    public class MyPagerChangeListener implements ViewPager.OnPageChangeListener {
        int from;
        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            from = arg0;
        }

        @Override
        public void onPageSelected(final int arg0) {
            Log.d("跳转", arg0 + ", " + from);

            int cache_left = 5;         // 设置当缓存剩余多少章节时，重新开始进行资源获取
            final int cache_num = 10;         // 设置一次获取资源的章节数
            // 帧界面向右滑动了一次
            if (arg0 == from + 1) {
                // 当前章节数+1
                currChapter++;
                // 滑动到当前缓存剩余量不多时，当前再访问剩余量设置是五章节(不包括当前章节)
                if(arg0 == (cache_chapter_range_max-cache_chapter_range_min) - cache_left) {

                    // 已经缓存到网络上的最后一章了，没有更新
                    if(cache_chapter_range_max == totalChapter) {
                        Toast.makeText(ReadPageActivity.this, "已经是最后一章了！客官", Toast.LENGTH_LONG).show();
                    }
                    else {
                        // 隐藏ViewPager避免添加帧的时候滑动ViewPager报错
                        rxjava_update_page(2);
                        //使用子线程进行缓存更新
                        Thread update_cache_thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 再使用子线程进行网络访问，获取下面N个章节的内容（当前N设置为10）
                                /*final int newChapterNum = arg0 + 1;*/
                                Thread getNewChapterThread = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        for(int i = 1; i <= cache_num; i++) {
                                            int newChapterNum = cache_chapter_range_max + i;
                                            // 非法章节数(已超出网络给出的章节数)
                                            if(newChapterNum > totalChapter) continue;
                                            // 合法章节则获取新章节
                                            ChapterObj c = BookService.getBookService().getChapterByLink(chapterLinks.get(newChapterNum).getLink());
                                            System.out.println(chapterLinks.get(newChapterNum).getLink());
                                            chapterObjs.add(newChapterNum-cache_chapter_range_min, c);
                                        }
                                    }
                                });
                                getNewChapterThread.start();

                                // 等待网络访问子线程完成
                                try {
                                    getNewChapterThread.join();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }


                                // 新增N个帧
                                for(int i = 1; i <= cache_num; i++) {
                                    int newChapterNum = cache_chapter_range_max + 1;
                                    // 已经超出总章节数
                                    if(newChapterNum > totalChapter) continue;
                                    // 合法的章节数
                                    cache_chapter_range_max++;
                                    // 解析章节内容
                                    //String title = chapterObjs.get(0).getIchapter().getTitle(); // 这种获取Title的内容错误的（API问题）
                                    String title = chapterLinks.get(newChapterNum).getTitle();
                                    String content;
                                    if(chapterObjs.get(newChapterNum-cache_chapter_range_min) == null) {
                                        content = "章节获取失败了呢！客官";
                                    }
                                    else content = chapterObjs.get(newChapterNum-cache_chapter_range_min).getIchapter().getBody();
                                    // 为段首添加缩进
                                    content = "\u3000\u3000" + content;
                                    content = content.replaceAll("\n", "\n\u3000\u3000");
                                    // 新建对应章节内容帧
                                    ReadPageFragment fragment = new ReadPageFragment();
                                    // 给帧传数据
                                    Bundle bundle = new Bundle();
                                    bundle.putString("title", title);
                                    bundle.putString("content", content);
                                    bundle.putInt("currentChapter", newChapterNum);
                                    bundle.putInt("totalChapter", totalChapter);
                                    fragment.setArguments(bundle);
                                    // 将新加的帧放入队列中
                                    fragmentList.add(fragment);
                                }
                                // 使用rxjava进行主界面UI更新
                                rxjava_update_page(1);
                                System.out.println("更新章节: ");
                            }
                        });
                        update_cache_thread.start();
                    }

                }
                /*currPage++;
                //当前章节阅读页数超过章节总页数
                //代表阅读到了下一章的第一页
                if (currPage >= currTotalPage) {
                    currPage = 0;
                    currChapter++;
                    //todo
                    //更新currTotalPage
                }
                //阅读到缓存的最后一页时，增加下一章节内容到FragmentList
                if (arg0 == fragmentList.size() - 1) {
                    //todo
                }*/
            }
            else if (arg0 == from) {
                //Toast.makeText(ReadPageActivity.this, "向左滑动了一页", Toast.LENGTH_LONG).show();
                currPage--;
                //当前章节阅读页数小于0
                //代表阅读到了上一章的最后一页
                if (currPage < 0) {
                    //更新currTotalPage
                    //todo
                    currChapter--;
                    currPage = currTotalPage - 1;
                }
                //阅读到缓存的第一页时，增加上一章节内容到FragmentList
                if (arg0 == 0) {
                    //todo
                }
            }
        }
    }


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

    // RXJAVA2 更新主界面UI
    private void rxjava_update_page(final int type) {
        final Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(type);
                e.onComplete();
            }

        });

        CompositeDisposable mCompositeDisposable = new CompositeDisposable();

        DisposableObserver<Integer> disposableObserver = new DisposableObserver<Integer>() {
            @Override
            public void onNext(Integer value) {
                Log.d("BackgroundActivity", "onNext");
                // type = 0 界面初始化更新
                if(type == 0) {
                    // 初始化适配阅读帧
                    fragmentAdapter.notifyDataSetChanged();
                    viewPager.setAdapter(fragmentAdapter);
                    // 跳到上次阅读到的章节
                    viewPager.setCurrentItem(currChapter-cache_chapter_range_min);
                    viewPager.setOffscreenPageLimit(currTotalPage - 1);
                    // 适配完毕，取消ProgressBar, 隐藏功能按键
                    progressBar.setVisibility(View.GONE);
                    rg_control.setVisibility(View.GONE);
                }
                // type == 1 界面获取下一章节更新
                else if(type == 1) {
                    fragmentAdapter.notifyDataSetChanged();
                    // 适配完毕，取消ProgressBar, 隐藏功能按键
                    progressBar.setVisibility(View.GONE);
                    viewPager.setVisibility(View.VISIBLE);
                }
                else if(type == 2) {
                    // 等待后台适配阅读帧
                    viewPager.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d("BackgroundActivity", "onError=" + e);
            }

            @Override
            public void onComplete() {
                Log.d("BackgroundActivity", "onComplete");
            }
        };

        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(disposableObserver);
        mCompositeDisposable.add(disposableObserver);

    }

    // 辅助函数 : 将字符串按指定长度分割
    public static List<String> getStrList(String inputString, int length) {
        int size = inputString.length() / length;
        if (inputString.length() % length != 0) {
            size += 1;
        }
        List<String> res = new ArrayList<>();
        for(int i = 0; i < size - 1; i++) {
            String s = inputString.substring(i*length, (i+1)*length-1);
            res.add(s);
        }
        String s = inputString.substring((size-1)*length);
        res.add(s);
        return res;
    }
}
