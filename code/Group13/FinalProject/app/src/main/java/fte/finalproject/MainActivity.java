package fte.finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import fte.finalproject.obj.CategoryObj;
import fte.finalproject.obj.ChapterObj;
import fte.finalproject.obj.ClassificationObj1;
import fte.finalproject.obj.ClassificationObj2;
import fte.finalproject.obj.CptListObj;
import fte.finalproject.service.BookService;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

   /*     Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                BookService bookService = new BookService();
                ChapterObj chapterObj = bookService.getChapterByLink("http://book.my716.com/getBooks.aspx?method=content&bookId=633074&chapterFile=U_753547_201607012243064741_3164_2.txt");
                Log.d("ok",  String.valueOf(chapterObj.isOk()));
                Log.d("title", chapterObj.getIchapter().getTitle());
                Log.d("body", chapterObj.getIchapter().getBody());
            }
        });

        thread.start();*/
    }
}
