package fte.finalproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fte.finalproject.Fragment.ReadPageFragment;
import fte.finalproject.Fragment.TabFragmentStatePagerAdapter;
import fte.finalproject.obj.ChapterLinkObj;
import fte.finalproject.obj.ChapterLinks;
import fte.finalproject.obj.CptListObj;
import fte.finalproject.service.BookService;


public class ReadPageActivity extends AppCompatActivity {
    private List<Fragment> fragmentList = new ArrayList<>();        //存储所有页面Fragment
    private TabFragmentStatePagerAdapter fragmentAdapter;
    private ViewPager viewPager;

    private String bookid;                      // 书籍id
    private CptListObj cptListObj;            //章节列表
    List<ChapterLinkObj> chapterLinks;
    private int currChapter;                   //当前阅读到的章节
    private int currPage;                      //当前阅读到的页面(对一个章节而言)[0, currTotalPage - 1]
    private int currTotalPage;                 //当前章节总页数

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_page);
        //从传递的参数中获取章节相关信息
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        bookid = bundle.getString("bookid");
        System.out.println(bookid);


        // 获取页面控件
        init_page_control();

        // 获取页面宽高
        get_screen_info();

        // 设置功能按键
        set_functional_button();

        //初始化 Fragment
        init_fragment();
    }


    private void init_fragment() {
        // 判断是否联网了
        Boolean isNetworkConnected = isNetWorkConnected(this);
        // 未联网的响应处理
        if(!isNetworkConnected) {
            // 弹出Toast提示
            Toast.makeText(ReadPageActivity.this, "网络连接状况：未连接", Toast.LENGTH_SHORT).show();
            // ProgressBar等待框消失
            progressBar.setVisibility(View.GONE);
        }
        // 已经联网，获取书籍信息进行阅读
        else {
            //获取到:章节总页数totalPage、章节title、每页的内容content
            new Thread(new Runnable() {
                @Override
                public void run() {
                    cptListObj = BookService.getBookService().getChaptersByBookId(bookid);
                    chapterLinks = cptListObj.getImixToc().getChapterLinks();
                    for(int i = 0; i < chapterLinks.size(); i++) {
                        System.out.println(chapterLinks.get(i).getTitle());
                    }
                }
            }).start();

            // 根据内容适配各帧
            //todo
            int totalPage = 10;
            String title = "todo";
            String content = "todo";
            for (int i = 0; i < totalPage; ++i) {
                ReadPageFragment fragment = new ReadPageFragment();
                Bundle bundle = new Bundle();
                bundle.putString("title", title);
                bundle.putString("content", content);
                bundle.putInt("totalPage", totalPage);
                fragment.setArguments(bundle);
                fragmentList.add(fragment);
                fragmentAdapter = new TabFragmentStatePagerAdapter(getSupportFragmentManager(), fragmentList);
                viewPager.setOnPageChangeListener(new MyPagerChangeListener());
                viewPager.setAdapter(fragmentAdapter);
                viewPager.setCurrentItem(0);
                viewPager.setOffscreenPageLimit(totalPage - 1);
            }

            // 适配完毕，取消ProgressBar, 隐藏功能按键
            progressBar.setVisibility(View.GONE);
            rg_control.setVisibility(View.GONE);

        }


        // 获取书本的章节信息：
        /*Thread get_chapter_thread = new Thread(new Runnable() {
            @Override
            public void run() {
                chatperList = BookService.getBookService().getChaptersByBookId("5816b415b06d1d32157790b1");
            }
        });
        get_chapter_thread.start();*/


    }


    private void get_screen_info() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        SCREEN_HEIGHT = dm.heightPixels;
        SCREEN_WIDTH = dm.widthPixels;
        /*String str = "屏幕宽高：" + SCREEN_WIDTH + ":" + SCREEN_HEIGHT;
        Toast.makeText(ReadPageActivity.this, str, Toast.LENGTH_SHORT).show();*/
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
    //当滑动到当前缓存的最后一页时
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
        public void onPageSelected(int arg0) {
            Log.d("", arg0 + ", " + from);
            if (arg0 == from + 1) {
                //Toast.makeText(ReadPageActivity.this, "向右滑动了一页", Toast.LENGTH_LONG).show();
                currPage++;
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
                }
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
}
