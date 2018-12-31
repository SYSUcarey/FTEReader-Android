package fte.finalproject.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fte.finalproject.R;
import fte.finalproject.ReadPageActivity;
import fte.finalproject.myRecyclerview.MyRecyclerViewAdapter;
import fte.finalproject.myRecyclerview.MyViewHolder;
import fte.finalproject.obj.ShelfBookObj;

//书架界面
public class BookShelfFragment extends Fragment {
    // Fragment 的 视图
    View view;

    // Fragment内的RecyclerView
    RecyclerView recyclerView;      // recyclerview
    List<ShelfBookObj> myBooks;            // recyclerview中的书籍数据
    MyRecyclerViewAdapter<ShelfBookObj> adapter;   // recyclerview 的 adapter

    // book-item 项的各个控件
    private ImageView book_cover_imageview_control;
    private TextView book_name_textview_control;
    private TextView book_author_textview_control;
    private TextView book_type_textview_control;
    private TextView book_intro_textview_control;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // 获取 Fragment 的视图
        view = inflater.inflate(R.layout.fragment_book_shelf, null);

        // 获取书籍数据
        getMyBooks();

        // 设置页面的 RecyclerView
        setRecyclerView();

        // 返回视图
        return view;
    }

    // 获取书籍数据
    private void getMyBooks() {
        myBooks = new ArrayList<>();
        Bitmap bitmap = ((BitmapDrawable)getResources().getDrawable(R.mipmap.bookcover)).getBitmap();
        for(int i = 0; i < 10; i++) {
            ShelfBookObj s = new ShelfBookObj(0, "圣墟", bitmap, 0, "df", 0, " ");
            myBooks.add(s);
        }
        System.out.println("getBookSize: " + myBooks.size());
    }

    // 设置页面的 RecyclerView
    private void setRecyclerView() {
        // 获取页面的 RecyclerView 控件
        recyclerView = view.findViewById(R.id.fragment_book_shelf_recyclerview);

        // 设置 RecyclerView 的布局方式
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // 设置 RecyclerAdapter
        adapter = new MyRecyclerViewAdapter<ShelfBookObj>(getActivity(), R.layout.item_book, myBooks) {
            @Override
            public void convert(MyViewHolder holder, ShelfBookObj shelfBookObj) {
                TextView bookName = holder.getView(R.id.item_book_name);
                bookName.setText(shelfBookObj.getName());
            }
        };
        adapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), ReadPageActivity.class);
                Bundle bundle = new Bundle();
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onLongClick(int position) {

            }
        });
        recyclerView.setAdapter(adapter);
    }


}
