package fte.finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;


import java.util.List;

import fte.finalproject.obj.SearchResultObj;

import static fte.finalproject.control.DatabaseControl.getInstance;

public class SearchActivity extends AppCompatActivity {

    private SearchView searchView;
    private ImageView deleteView;
    private ListView historyList;
    private RecyclerView resultList;
    private List<String> histories;
    private List<String> tempFuzzy;
    private List<SearchResultObj.book> results;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //获取控件
        searchView = findViewById(R.id.search_search_searchView);
        deleteView = findViewById(R.id.search_delete_image);
        historyList = findViewById(R.id.search_history_list);
        resultList = findViewById(R.id.search_result_list);
        TextView text1 = findViewById(R.id.search_pop_text1);
        TextView text2 = findViewById(R.id.search_pop_text2);
        TextView text3 = findViewById(R.id.search_pop_text3);
        TextView text4 = findViewById(R.id.search_pop_text4);
        TextView text5 = findViewById(R.id.search_pop_text5);
        TextView text6 = findViewById(R.id.search_pop_text6);

        //设置历史删除函数
        deleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInstance(getBaseContext()).deleteHistory();
            }
        });

        //设置热门搜索函数
        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        text3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        text4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        text5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        text6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //设置搜索函数

        //设置历史列表adapter

        //设置结果列表adapter

    }
}
