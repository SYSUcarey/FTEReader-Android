package fte.finalproject.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fte.finalproject.R;
import fte.finalproject.myRecyclerview.RankRecyObj;

//排行榜界面
public class RankingFragment extends Fragment {
    private boolean isMale;
    private int color1 = Color.parseColor("#FFAEB9");//最热榜
    private int color2 = Color.parseColor("#99ff33");//热搜榜
    private int color3 = Color.parseColor("#DAA520");//潜力榜
    private int color4 = Color.parseColor("#B23AEE");//留存榜
    private int color5 = Color.parseColor("#33ffff");//完结榜

    private RecyclerView recyclerView;
    private List<RankRecyObj> list = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        this.isMale = bundle.getBoolean("isMale");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ranking, null);
        recyclerView = view.findViewById(R.id.ranking_recycler);
        TextView textView = view.findViewById(R.id.test);
        if (isMale) textView.setText("男生");
        else textView.setText("女生");
        return view;
    }
}
