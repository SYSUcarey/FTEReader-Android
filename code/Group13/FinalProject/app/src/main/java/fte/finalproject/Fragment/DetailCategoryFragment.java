package fte.finalproject.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import fte.finalproject.BookDetailActivity;
import fte.finalproject.R;
import fte.finalproject.myRecyclerview.MyRecyclerViewAdapter;
import fte.finalproject.myRecyclerview.MyViewHolder;
import fte.finalproject.obj.AllRankingObj;
import fte.finalproject.obj.BookObj;
import fte.finalproject.obj.SingleRankingObj;
import fte.finalproject.service.BookService;

//具体分类和榜单的Fragment
public class DetailCategoryFragment extends Fragment {
    private boolean isRanking;      //排行榜/具体分类
    private boolean isMale;         //男生/女生
    private String title;           //榜单名/类型名
    private String type;            //具体榜单/具体类型

    private RecyclerView recyclerView;
    private List<BookObj> bookObjList = new ArrayList<>();
    private MyRecyclerViewAdapter recyclerViewAdapter;

    //各种榜单/分类的id
    private String rankingid = "";
    private String hotid = "", newid = "", reputationid = "", overid = "";

    public Handler handler = new Handler();

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

        //获取书籍列表
        getBookList();

        recyclerView = view.findViewById(R.id.detail_category_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewAdapter = new MyRecyclerViewAdapter<BookObj>(getActivity(), R.layout.item_book, bookObjList) {
            @Override
            public void convert(MyViewHolder holder, BookObj bookObj) {
                if (isRanking && !bookObjList.isEmpty()) {
                    Log.d("id", bookObjList.get(0).getTitle());
                    ImageView rankingImg = holder.getView(R.id.item_book_rankingImg);
                    if (bookObj.getId().equals(bookObjList.get(0).getId())) {//排行榜第一名
                        rankingImg.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.first, null));
                        rankingImg.setVisibility(View.VISIBLE);
                    } else if (bookObj.getId().equals(bookObjList.get(1).getId())) {//排行榜第二名
                        rankingImg.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.second, null));
                        rankingImg.setVisibility(View.VISIBLE);
                    } else if (bookObj.getId().equals(bookObjList.get(2).getId())) {//排行榜第三名
                        rankingImg.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.third, null));
                        rankingImg.setVisibility(View.VISIBLE);
                    }
                    else rankingImg.setVisibility(View.GONE);
                }
                final ImageView imageView = holder.getView(R.id.item_book_cover);
                TextView bookName = holder.getView(R.id.item_book_name);
                TextView bookAuthor = holder.getView(R.id.item_book_author);
                TextView bookType = holder.getView(R.id.item_book_type);
                TextView bookIntro = holder.getView(R.id.item_book_intro);
                bookName.setText(bookObj.getTitle());
                bookType.setText(bookObj.getMajorCate());
                bookAuthor.setText(bookObj.getAuthor());
                bookIntro.setText(bookObj.getShortIntro());

                //通过网络获取书籍图标
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
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        imageView.setImageBitmap(bitmap);
                                    }
                                });
                            }
                        } catch (Exception e) {
                            System.err.println(e.getMessage());
                        }
                    }}).start();
            }
        };
        recyclerViewAdapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                //跳转到书籍详情界面
                Intent intent = new Intent(getActivity(), BookDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("bookobj", bookObjList.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onLongClick(int position) {

            }
        });
        recyclerView.setAdapter(recyclerViewAdapter);
        return view;
    }

    void getBookList() {
        final BookService bookService = BookService.getBookService();
        new Thread(new Runnable() {
            @Override
            public void run() {
                AllRankingObj allRankingObj = bookService.getAllRankingObj();
                if (allRankingObj.isOk() == false) {
                    Toast.makeText(getContext(), "网络错误", Toast.LENGTH_LONG).show();
                    Log.d("error", "获取全部排行榜失败");
                    return;
                }
                if (isRanking) {        //排行榜
                    //获取具体榜单的id
                    if (isMale) {   //男生
                        for (AllRankingObj.subClass subClass : allRankingObj.getMaleList()) {
                            if (subClass.getShortTitle().equals(title)) {
                                if (title.equals("热搜榜")) rankingid = subClass.getId();
                                else {
                                    if (type.equals("周榜")) rankingid = subClass.getId();
                                    else if (type.equals("月榜")) rankingid = subClass.getMonthRank();
                                    else if (type.equals("总榜")) rankingid = subClass.getTotalRank();
                                    else {
                                        System.exit(1);
                                        Log.d("error", "榜单名错误！");
                                    }
                                }
                                break;
                            }
                        }
                    }
                    else {          //女生
                        for (AllRankingObj.subClass subClass : allRankingObj.getFemaleList()) {
                            if (subClass.getShortTitle().equals(title)) {
                                if (title.equals("热搜榜")) rankingid = subClass.getId();
                                else {
                                    if (type.equals("周榜")) rankingid = subClass.getId();
                                    else if (type.equals("月榜")) rankingid = subClass.getMonthRank();
                                    else if (type.equals("总榜")) rankingid = subClass.getTotalRank();
                                    else {
                                        System.exit(1);
                                        Log.d("error", "榜单名不符！");
                                    }
                                }
                                break;
                            }
                        }
                    }
                    Log.d("21", "rankingid" + rankingid);
                    //得到id后再获取具体榜单的书籍信息
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            SingleRankingObj singleRankingObj = bookService.getSingleRankingObj(rankingid);
                            if (singleRankingObj.isOk() == false) {
                                Toast.makeText(getContext(), "网络错误", Toast.LENGTH_LONG).show();
                                Log.d("error", "获取单一排行榜失败");
                                return;
                            }
                            List<BookObj> objList = singleRankingObj.getRanking().getBookList();
                            //取排行榜前15名
                            for (int i = 0; i < 15 && i < objList.size(); ++i) {
                                BookObj bookObj = objList.get(i);
                                String intro = bookObj.getShortIntro();
                                if (intro.length() > 50) intro = intro.substring(0, 50);
                                intro += "...";
                                bookObj.setShortIntro(intro);
                                bookObjList.add(bookObj);
                            }
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    recyclerViewAdapter.refresh(bookObjList);
                                }
                            });
                        }
                    }).start();
                }
                else {                  //具体分类
                    if (isMale) {   //男生

                    }
                    else {          //女生

                    }
                }
            }
        }).start();
    }
}
