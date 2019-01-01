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
    // 页面数据
    private String title;       //章节名
    private String content;     //本页内容
    int currPage;               //当前页数
    int totalPage;              //当前章节总页数

    // 帧页面View
    View view;
    // 帧页面控件
    private TextView titile_control;        // 标题
    private TextView content_control;       // 阅读页内容
    private TextView progress_control;      // 阅读此章节的进度


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
        // 获取帧页面View
        view = inflater.inflate(R.layout.fragment_read_page, null);

        // 获取页面控件
        init_page_control();

        // 设置页面内容
        init_page_info();

        //todo
        return view;
    }

    private void init_page_info() {
        titile_control.setText(title);      // 设置标题
        content_control.setText(content);   // 设置阅读页内容

    }

    private void init_page_control() {
        titile_control = view.findViewById(R.id.fragment_read_page_title);
        content_control = view.findViewById(R.id.fragment_read_page_content);
        progress_control = view.findViewById(R.id.fragment_read_page_process);
    }
}
