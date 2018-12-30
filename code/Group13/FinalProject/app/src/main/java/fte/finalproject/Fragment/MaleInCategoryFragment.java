package fte.finalproject.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import fte.finalproject.R;
import fte.finalproject.myRecyclerview.CategoryRecyObj;
import fte.finalproject.myRecyclerview.MyRecyclerViewAdapter;
import fte.finalproject.myRecyclerview.MyViewHolder;
import fte.finalproject.obj.CategoryObj;
import fte.finalproject.obj.ShelfBookObj;

//分类(男生/女生)界面
public class MaleInCategoryFragment extends Fragment {
    // Fragment 的 视图
    View view;

    // Fragment内的RecyclerView
    RecyclerView recyclerView;              // recyclerview
    List<CategoryRecyObj> myCategories;              // recyclerview中的书籍数据
    MyRecyclerViewAdapter<CategoryRecyObj> adapter;     // recyclerview 的 adapter

    // 判断是女性分类还是男性分类
    boolean isMale = true;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // 获取 Fragment 视图
        view = inflater.inflate(R.layout.fragment_male_in_category, null);

        // 获取分类数据
        getMyCategories();

        // 设置 RecyclerView
        setRecyclerView();

        // Inflate the layout for this fragment
        return view;
    }

    // 设置 RecyclerView
    private void setRecyclerView() {
        // 获取页面的 RecyclerView 控件
        recyclerView = view.findViewById(R.id.fragment_male_in_category_recyclerview);

        // 设置 RecyclerView 的布局方式
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        // 设置 Adapter
        adapter = new MyRecyclerViewAdapter<CategoryRecyObj>(getActivity(), R.layout.item_category, myCategories) {
            @Override
            public void convert(MyViewHolder holder, CategoryRecyObj categoryRecyObj) {
                TextView categoryName = holder.getView(R.id.item_category_name);
                categoryName.setText(categoryRecyObj.getCategoryName());
                TextView categoryBookCount = holder.getView(R.id.item_category_count);
                categoryBookCount.setText("(" + categoryRecyObj.getBookCount() + "本" + ")");
            }
        };

        recyclerView.setAdapter(adapter);

    }

    // 获取分类数据
    private void getMyCategories() {
        myCategories = new ArrayList<>();
        String[] maleCategoriesName = {"玄幻", "奇幻", "武侠", "仙侠", "都市", "职场", "历史", "军事", "游戏", "竞技", "科幻", "灵异", "同人", "轻小说"};
        String[] femaleCategoriesName = {"古代言情", "现代言情", "青春校园", "耽美", "玄幻奇幻", "武侠仙侠", "科幻", "游戏竞技", "悬疑灵异", "同人", "女尊", "百合"};
        // 男生向小说分类
        if(isMale) {
            for(int i = 0; i < maleCategoriesName.length; i++) {
                CategoryRecyObj c = new CategoryRecyObj(maleCategoriesName[i], 0);
                myCategories.add(c);
            }
        }
        // 女生向小说分类
        else {
            for(int i = 0; i < femaleCategoriesName.length; i++) {
                CategoryRecyObj c = new CategoryRecyObj(femaleCategoriesName[i], 0);
                myCategories.add(c);
            }
        }

    }

}
