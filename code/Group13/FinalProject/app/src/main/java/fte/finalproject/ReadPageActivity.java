package fte.finalproject;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.DisplayCutout;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class ReadPageActivity extends AppCompatActivity {

    private RadioGroup rg_control;
    private RadioButton day_and_night_rb_control;
    private RadioButton horizontal_and_vertical_rb_control;
    private RadioButton setting_rb_control;
    private RadioButton download_rb_control;
    private RadioButton catalog_rb_control;

    float SCREEN_HEIGHT;
    float SCREEN_WIDTH;

    boolean show_functional_button = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_page);

        // 获取页面控件
        init_page_control();

        // 获取页面宽高
        get_screen_info();

        // 设置功能按键
        set_functional_button();
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
    }
}
