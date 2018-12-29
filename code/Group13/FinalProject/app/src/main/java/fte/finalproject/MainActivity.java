package fte.finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import fte.finalproject.obj.CategoryObj;
import fte.finalproject.obj.ClassificationObj1;
import fte.finalproject.obj.ClassificationObj2;
import fte.finalproject.service.BookService;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                BookService bookService = new BookService();
                CategoryObj categoryObj = bookService.getBooksByCategoty("hot", "玄幻", "0", "20", "东方玄幻", "male");
                Log.d("ok",  String.valueOf(categoryObj.isOk()));
                for (int i = 0; i < categoryObj.getBooks().size(); i++) {
                    Log.d("title", categoryObj.getBooks().get(i).getTitle());
                }
                Log.d("total", String.valueOf(categoryObj.getTotal()));
            }
        });

        thread.start();
    }
}
