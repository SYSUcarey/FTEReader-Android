package fte.finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import fte.finalproject.obj.SearchResultObj;
import fte.finalproject.service.BookService;

public class BookDetailActivity extends AppCompatActivity {

    RadioButton addButton;
    RadioButton readButton;
    RadioButton downloadButton;
    Button moreButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        // 获取控件
        addButton = findViewById(R.id.detail_bottom_add);
        readButton = findViewById(R.id.detail_bottom_read);
        downloadButton = findViewById(R.id.detail_bottom_download);
        moreButton = findViewById(R.id.detail_more);

        // 加入书架
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // 阅读
        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // 下载
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // 查看更多同类书籍
        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
