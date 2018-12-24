package fte.finalproject.control;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseControl extends SQLiteOpenHelper {
    private static final String DB_NAME= "db_name";
    private static final String TABLE_NAME1 = "localbook_table";
    private static final String TABLE_NAME2 = "category_talbe";
    private static final String TABLE_NAME3 = "categorybook_table";
    private static final int DB_VERSION = 1;

    public DatabaseControl(Context context) {
        super(context, DB_NAME, null, DB_VERSION );
    }

    @Override//创建数据库
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE1 = "CREATE TABLE if not exists "
                + TABLE_NAME1
                + " (_id INTEGER PRIMARY KEY, name TEXT, password TEXT, image BLOB)";
        String CREATE_TABLE2 = "CREATE TABLE if not exists "
                + TABLE_NAME2
                + " (_id INTEGER PRIMARY KEY, user TEXT, content TEXT, date TEXT,image BLOB, thumbs INTEGER)";
        String CREATE_TABLE3 = "CREATE TABLE if not exists "
                + TABLE_NAME3
                + " (_id INTEGER PRIMARY KEY, user TEXT, commentID INTEGER, FOREIGN KEY(commentID) REFERENCES comment_table(_id))";
        sqLiteDatabase.execSQL(CREATE_TABLE1);
        sqLiteDatabase.execSQL(CREATE_TABLE2);
        sqLiteDatabase.execSQL(CREATE_TABLE3);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int ii) {

    }
}
