package fte.finalproject.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import fte.finalproject.R;
import fte.finalproject.obj.BookObj;

public class BookShelfFragment extends Fragment {
    // Fragment 的 视图
    View view;

    // Fragment内的RecyclerView
    RecyclerView recyclerView;      // recyclerview
    List<BookObj> books;            // recyclerview中的书籍数据

    // book-item 项的各个控件
    private ImageView book_cover_imageview_control;
    private TextView book_name_textview_control;
    private TextView book_author_textview_control;
    private TextView book_type_textview_control;
    private TextView book_intro_textview_control;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.book_item, null);

        // 初始化获取book-item页面控件
        init_page_control();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_shelf, container, false);
    }

    private void init_page_control() {
        /*book_cover_imageview_control =*/
    }

}
