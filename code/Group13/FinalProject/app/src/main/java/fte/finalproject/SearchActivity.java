package fte.finalproject;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fte.finalproject.myRecyclerview.MyRecyclerViewAdapter;
import fte.finalproject.myRecyclerview.MyViewHolder;
import fte.finalproject.obj.SearchResultObj;
import fte.finalproject.service.BookService;

import static fte.finalproject.control.DatabaseControl.getInstance;
import static fte.finalproject.service.BookService.getBookService;

public class SearchActivity extends AppCompatActivity {

    private SearchView searchView;
    private TextView cancelView;
    private ImageView deleteView;
    private ListView historyList;
    private ListView fuzzyList;
    private ConstraintLayout initialLayout;
    private ConstraintLayout searchLayout;
    private RecyclerView resultList;
    private List<String> histories;
    private List<String> tempFuzzy;
    private List<SearchResultObj.book> results;
    private MyRecyclerViewAdapter recyclerViewAdapter;
    private boolean isSubmit;
    public Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        isSubmit = false;
        //初始化列表
        histories = getInstance(getBaseContext()).getAllHistory();
        tempFuzzy = new ArrayList<>();
        results = new ArrayList<>();
        //获取控件
        cancelView = findViewById(R.id.search_cancel_text);
        searchView = findViewById(R.id.search_search_searchView);
        deleteView = findViewById(R.id.search_delete_image);
        historyList = findViewById(R.id.search_history_list);
        fuzzyList = findViewById(R.id.search_fuzzy_list);
        resultList = findViewById(R.id.search_result_list);
        initialLayout = findViewById(R.id.search_initial_layout);
        searchLayout = findViewById(R.id.search_afters_layout);
        final TextView text1 = findViewById(R.id.search_pop_text1);
        final TextView text2 = findViewById(R.id.search_pop_text2);
        final TextView text3 = findViewById(R.id.search_pop_text3);
        final TextView text4 = findViewById(R.id.search_pop_text4);
        final TextView text5 = findViewById(R.id.search_pop_text5);
        final TextView text6 = findViewById(R.id.search_pop_text6);

        //设置历史列表adapter
        final ArrayAdapter<String> historyAdapter = new ArrayAdapter<>(this,R.layout.item_listview,histories);
        historyList.setAdapter(historyAdapter);
        //设置结果列表adapter
        recyclerViewAdapter = new MyRecyclerViewAdapter<SearchResultObj.book>(SearchActivity.this,R.layout.item_book,results) {
            @Override
            public void convert(MyViewHolder holder, final SearchResultObj.book book) {
                TextView name = holder.getView(R.id.item_book_name);
                name.setText(book.getTitle());
                TextView author = holder.getView(R.id.item_book_author);
                author.setText(book.getAuthor());
                TextView major = holder.getView(R.id.item_book_type);
                major.setText(book.getContentType());
                TextView intro = holder.getView(R.id.item_book_intro);
                String introString = book.getShortIntro();
                if (introString.length() > 50){
                    introString = introString.substring(0,49)+"……";
                }
                intro.setText(introString);
                final ImageView cover = holder.getView(R.id.item_book_cover);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL( BookService.StaticsUrl + book.getCover());
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            connection.setRequestMethod("GET");
                            connection.setConnectTimeout(10000);
                            if (connection.getResponseCode() == 200) {
                                InputStream inputStream = connection.getInputStream();
                                final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        cover.setImageBitmap(bitmap);
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
        recyclerViewAdapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {

            }

            @Override
            public void onLongClick(int position) {

            }
        });
        resultList.setAdapter(recyclerViewAdapter);
        resultList.setLayoutManager(new LinearLayoutManager(this));
        //设置模糊关联列表adapter
        final ArrayAdapter<String> fuzzyAdapter = new ArrayAdapter<>(this,R.layout.item_listview2,tempFuzzy);
        fuzzyList.setAdapter(fuzzyAdapter);


        //设置热门搜索函数
        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = text1.getText().toString();
                recordClick(s);
            }
        });
        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = text2.getText().toString();
                recordClick(s);
            }
        });
        text3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = text3.getText().toString();
                recordClick(s);
            }
        });
        text4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = text4.getText().toString();
                recordClick(s);
            }
        });
        text5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = text5.getText().toString();
                recordClick(s);
            }
        });
        text6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = text6.getText().toString();
                recordClick(s);
            }
        });


        //设置搜索函数
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String s) {
                isSubmit = true;
                //添加历史
                if (!histories.contains(s)){
                    histories.add(s);
                    getInstance(getBaseContext()).addSearchHistory(s);
                }
                //设置可见
                searchLayout.setVisibility(View.VISIBLE);
                fuzzyList.setVisibility(View.INVISIBLE);
                resultList.setVisibility(View.VISIBLE);
                //填充数据
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<SearchResultObj.book> t = getBookService().getSearchResultObj(s,0,8).getBookList();
                        results.clear();
                        if (t.size() > 0)
                            results.addAll(t);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                recyclerViewAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }).start();
                recyclerViewAdapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String s) {
                //设置可见
                if (!isSubmit) {
                    if (s.equals("")) {
                        initialLayout.setVisibility(View.VISIBLE);
                        searchLayout.setVisibility(View.INVISIBLE);
                    }
                    else {
                        initialLayout.setVisibility(View.INVISIBLE);
                        searchLayout.setVisibility(View.VISIBLE);
                        fuzzyList.setVisibility(View.VISIBLE);
                        resultList.setVisibility(View.INVISIBLE);
                    }
                }
                else {
                    if (s.equals("")) {
                        fuzzyList.setVisibility(View.INVISIBLE);
                        resultList.setVisibility(View.VISIBLE);
                    }
                    else {
                        fuzzyList.setVisibility(View.VISIBLE);
                        resultList.setVisibility(View.INVISIBLE);
                    }
                }
                //填充数据
                if (!s.equals("")) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String[] tss = getBookService().getFuzzySearchResult(s).getData();
                            List<String> t;
                            if (tss != null)
                                t = Arrays.asList(tss);
                            else t = new ArrayList<>();
                            tempFuzzy.clear();
                            if (t.size() > 0)
                                tempFuzzy.addAll(t);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    fuzzyAdapter.notifyDataSetChanged();
                                }
                            });
                        }
                    }).start();
                    fuzzyAdapter.notifyDataSetChanged();
                }
                return false;
            }
        });

        //设置历史删除函数
        deleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInstance(getBaseContext()).deleteHistory();
                histories.clear();
                historyAdapter.notifyDataSetChanged();
            }
        });

        //设置历史点击函数
        historyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String s = histories.get(position);
                recordClick(s);
            }
        });

        //模糊搜索点击函数
        fuzzyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String s = tempFuzzy.get(position);
                isSubmit = true;
                //添加历史
                if (!histories.contains(s)){
                    histories.add(s);
                    getInstance(getBaseContext()).addSearchHistory(s);
                }
                //设置可见
                initialLayout.setVisibility(View.INVISIBLE);
                searchLayout.setVisibility(View.VISIBLE);
                fuzzyList.setVisibility(View.INVISIBLE);
                resultList.setVisibility(View.VISIBLE);
                //填充数据
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<SearchResultObj.book> t = getBookService().getSearchResultObj(s,0,8).getBookList();
                        results.clear();
                        if (t.size() > 0)
                            results.addAll(t);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                recyclerViewAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }).start();
                recyclerViewAdapter.notifyDataSetChanged();
                searchView.setQuery(s,true);
            }
        });
        //取消按钮点击事件
        cancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void recordClick(final String s) {
        isSubmit = true;
        //设置可见
        initialLayout.setVisibility(View.INVISIBLE);
        searchLayout.setVisibility(View.VISIBLE);
        fuzzyList.setVisibility(View.INVISIBLE);
        resultList.setVisibility(View.VISIBLE);
        //添加历史
        if (!histories.contains(s)){
            histories.add(s);
            getInstance(getBaseContext()).addSearchHistory(s);
        }
        //填充数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<SearchResultObj.book> t = getBookService().getSearchResultObj(s,0,8).getBookList();
                results.clear();
                if (t.size() > 0)
                    results.addAll(t);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        recyclerViewAdapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
        recyclerViewAdapter.notifyDataSetChanged();
        searchView.setQuery(s,true);
    }
}
