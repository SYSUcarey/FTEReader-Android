package fte.finalproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.List;

import fte.finalproject.control.DatabaseControl;
import fte.finalproject.obj.BookObj;
import fte.finalproject.obj.ChapterLinkObj;
import fte.finalproject.obj.ChapterLinks;
import fte.finalproject.obj.ChapterObj;
import fte.finalproject.obj.CptListObj;
import fte.finalproject.obj.SearchResultObj;
import fte.finalproject.obj.ShelfBookObj;
import fte.finalproject.service.BookService;

public class BookDetailActivity extends AppCompatActivity {

    RadioButton addButton;
    RadioButton readButton;
    RadioButton downloadButton;
    Button moreButton;

    private String bookid;

    private BookObj bookObj;

    private Bitmap cover;

    private List<ChapterLinkObj> linkList;

    private StringBuilder stringBuilder = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        // 获取控件
        addButton = findViewById(R.id.detail_bottom_add);
        readButton = findViewById(R.id.detail_bottom_read);
        downloadButton = findViewById(R.id.detail_bottom_download);
        moreButton = findViewById(R.id.detail_more);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        bookid = bundle.getString("id");

        // 获取书籍相关信息
        new Thread(new Runnable() {
            @Override
            public void run() {
                BookService bookService = BookService.getBookService();
                bookObj = bookService.getBookById(bookid);
                cover = bookService.getImg(bookObj.getCover());
                CptListObj cptListObj = bookService.getChaptersByBookId(bookid);
                linkList = cptListObj.getImixToc().getChapterLinks();
            }
        }).start();

        // 加入书架
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseControl.getInstance(BookDetailActivity.this).addShelfBook(new ShelfBookObj(bookid, bookObj.getTitle(), cover, "",0, "online", 0, bookObj.getLongIntro(), bookObj.getAuthor(), bookObj.getMajorCate()));
            }
        });

        // 阅读
        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookDetailActivity.this, ReadPageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("bookid", bookid);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        // 下载
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < linkList.size(); i++) {
                            ChapterObj chapterObj = BookService.getBookService().getChapterByLink(linkList.get(i).getLink());
                            stringBuilder.append(chapterObj.getIchapter().getTitle());
                            stringBuilder.append("\n");
                            stringBuilder.append(chapterObj.getIchapter().getBody());
                        }

                    }
                }).start();*/
                Toast.makeText(BookDetailActivity.this, "功能开发中，敬请期待", Toast.LENGTH_SHORT).show();
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
