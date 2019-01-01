package fte.finalproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import fte.finalproject.control.DatabaseControl;
import fte.finalproject.obj.BookObj;
import fte.finalproject.obj.CategoryObj;
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

    ImageView bookCover;
    TextView bookTitle;
    TextView bookInfo;
    TextView updateTime;
    TextView follower;
    TextView retentionRatio;
    TextView bookIntro;
    RadioGroup recomRG;

    // 书籍对象
    private BookObj bookObj;

    // 同类书籍
    private CategoryObj categoryObj;

    private List<BookObj> bookObjs = new ArrayList<>();

    private Bitmap cover;

    private List<ChapterLinkObj> linkList;

    private StringBuilder stringBuilder = new StringBuilder();

    Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        // 获取控件
        addButton = findViewById(R.id.detail_bottom_add);
        readButton = findViewById(R.id.detail_bottom_read);
        downloadButton = findViewById(R.id.detail_bottom_download);
        moreButton = findViewById(R.id.detail_more);
        bookCover = findViewById(R.id.detail_cover);
        bookTitle = findViewById(R.id.detail_bookTitle);
        bookInfo = findViewById(R.id.detail_TV);
        updateTime = findViewById(R.id.detail_update);
        follower = findViewById(R.id.detail_follower2);
        retentionRatio = findViewById(R.id.detail_retentionRatio2);
        bookIntro = findViewById(R.id.detail_longIntro);
        recomRG = findViewById(R.id.detail_recomRG);

        Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();
        bookObj = (BookObj)bundle.getSerializable("bookobj");

        // 获取书籍相关信息
        new Thread(new Runnable() {
            @Override
            public void run() {
                bookObj = BookService.getBookService().getBookById(bookObj.getId());
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        bookTitle.setText(bookObj.getTitle());
                        int wordNum = bookObj.getWordCount() / 10000;
                        bookInfo.setText(bookObj.getAuthor() + " | " + bookObj.getMinorCate() + " | " + String.valueOf(wordNum) + "万字");
                        updateTime.setText(bookObj.getUpdated());
                        int followerNum = bookObj.getLatelyFollower() / 10000;
                        follower.setText(String.valueOf(followerNum) + "万人+");
                        retentionRatio.setText(bookObj.getRetentionRatio() + "%");
                        bookIntro.setText(bookObj.getLongIntro());

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                categoryObj = BookService.getBookService().getBooksByCategoty("reputation", bookObj.getMajorCate(), 0, 3, bookObj.getGender()[0]);
                                Log.d("size", String.valueOf(categoryObj.getBooks().size()));
                                for (int i = 0; i < categoryObj.getBooks().size(); i++) {
                                    final int j = i;
                                    final BookObj temp = BookService.getBookService().getBookById(categoryObj.getBooks().get(j).getId());
                                    mHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            bookObjs.add(temp);
                                            final String iconURL = BookService.StaticsUrl +  temp.getCover();
                                            final Button button = (Button)recomRG.getChildAt(j);
                                            button.setText(bookObjs.get(j).getTitle());
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
                                                                    Drawable drawable = new BitmapDrawable(bitmap);
                                                                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                                                    button.setCompoundDrawables(null, drawable, null, null);
                                                                }
                                                            });
                                                        }
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }).start();
                                        }
                                    });


                                }
                            }
                        }).start();
                    }
                });
            }
        }).start();


        // 获取封面图片
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
                                bookCover.setImageBitmap(bitmap);
                            }
                        });
                    }
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();

        // 获取同类书籍

        // 加入书架
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseControl.getInstance(BookDetailActivity.this).addShelfBook(new ShelfBookObj(bookObj.getId(), bookObj.getTitle(), cover, "",0, "online", 0, bookObj.getLongIntro(), bookObj.getAuthor(), bookObj.getMajorCate()));
            }
        });

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
