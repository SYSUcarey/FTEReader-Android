package fte.finalproject.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fte.finalproject.R;

//阅读界面的Fragment
public class ReadPageFragment extends Fragment {
    private String title;       //章节名
    private String content;     //本页内容
    int currPage;               //当前页数
    int totalPage;              //当前章节总页数

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.currPage = 0;
        this.totalPage = getArguments().getInt("totalPage");
        this.content = getArguments().getString("content");
        this.title = getArguments().getString("title");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_read_page, null);
        //todo
        return view;
    }
}
