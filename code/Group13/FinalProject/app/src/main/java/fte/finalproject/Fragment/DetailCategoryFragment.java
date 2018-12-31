package fte.finalproject.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fte.finalproject.R;

//具体分类和榜单的Fragment
public class DetailCategoryFragment extends Fragment {
    private boolean isRanking;      //排行榜/具体分类
    private boolean isMale;         //男生/女生
    private String title;           //榜单名/类型名
    private String type;            //具体榜单/具体类型

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.isRanking = getArguments().getBoolean("isRanking");
        this.isMale = getArguments().getBoolean("isMale");
        this.title = getArguments().getString("title");
        this.type = getArguments().getString("type");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_category, null);
        TextView test = view.findViewById(R.id.test_textView);
        String str = "";
        if (isMale) str += "男生,";
        else str += "女生,";
        if (isRanking) str += "排行榜,";
        else str += "分类,";
        str += "title:" + title + ",type:" + type;
        test.setText(str);
        return view;
    }
}
