package fte.finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import fte.finalproject.obj.ClassificationObj1;
import fte.finalproject.service.BookService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                BookService bookService = new BookService();
                ClassificationObj1 classificationObj1 = bookService.getClassification1();
                Log.d("ok",  String.valueOf(classificationObj1.isOk()));
                for (int i = 0; i < classificationObj1.getMaleList().size(); i++) {
                    Log.d("male", classificationObj1.getMaleList().get(i).getName());
                }
            }
        });

        thread.start();*/
    }
}
