package fte.finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import fte.finalproject.obj.AllRankingObj;
import fte.finalproject.obj.CategoryObj;
import fte.finalproject.obj.ChapterObj;
import fte.finalproject.obj.ClassificationObj1;
import fte.finalproject.obj.ClassificationObj2;
import fte.finalproject.obj.CptListObj;
import fte.finalproject.obj.SingleRankingObj;
import fte.finalproject.service.BookService;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                BookService bookService = new BookService();
                SingleRankingObj singleRankingObj = bookService.getSingleRankingObj("564d853484665f97662d0810");
                Log.d("ok",  String.valueOf(singleRankingObj.isOk()));
                Log.d("title", singleRankingObj.getRanking().getTitle());
                for (int i = 0; i < singleRankingObj.getRanking().getBookList().size(); i++) {
                    Log.d("book", singleRankingObj.getRanking().getBookList().get(i).getTitle());
                }
            }
        });

        thread.start();*/
    }
}
