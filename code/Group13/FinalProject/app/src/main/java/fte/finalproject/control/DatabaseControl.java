package fte.finalproject.control;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.List;

import fte.finalproject.obj.ShelfBookObj;

public class DatabaseControl extends SQLiteOpenHelper {
    private static final String DB_NAME= "readerBase";
    private static final String TABLE_NAME1 = "shelfbook_table";
    private static final String TABLE_NAME2 = "categorybook_table";
    private static final int DB_VERSION = 1;

    //byte[] 转 Bitmap
    public Bitmap bytesToBimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

    //这个list用于保存所有英雄信息
    private List<ShelfBookObj> allShelfBook = null;
    public List<ShelfBookObj> getAllShelfBook() {
        if (allShelfBook == null) {
            allShelfBook = getAllShelfBookFromDB();
        }
        return allShelfBook;
    }

    //从数据库中查询所有书架书目
    private List<ShelfBookObj> getAllShelfBookFromDB() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME1, null);
        List<ShelfBookObj> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            int readChapter = cursor.getInt(cursor.getColumnIndex("progress"));
            int type = cursor.getInt(cursor.getColumnIndex("type"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String address = cursor.getString(cursor.getColumnIndex("address"));
            byte[] imageByte = cursor.getBlob(cursor.getColumnIndex("hero_icon"));
            list.add(new ShelfBookObj(id,name,bytesToBimap(imageByte),readChapter,address,type));
        }
        cursor.close();
        db.close();
        return list;
    }

    //获取多少条搜索历史
    public int getHistoryCount() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME2, null, null, null, null, null, null);
        db.close();
        cursor.close();
        return cursor.getCount();
    }

    //更新阅读进度
    public void updateLamp(int progress, int id) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put("progress", progress);
        db.update("product", value, "_id=?", new String[] { String.valueOf(id) });
        db.close();
    }

    //搜索历史添加（不超过10个）
    private void addSearchHistory(String s) {
        SQLiteDatabase db = getWritableDatabase();
        if (getHistoryCount() > 9){
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME2, null);
            String name = cursor.getString(cursor.getColumnIndex("name"));
            db.delete(TABLE_NAME2, "content=?", new String[] {  });
        }
        ContentValues cv = new ContentValues();
        cv.put("content", s);
        db.insert(TABLE_NAME2, null, cv);
        db.close();
    }


    public DatabaseControl(Context context) {
        super(context, DB_NAME, null, DB_VERSION );
    }

    @Override//创建数据库
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //书架上的书，含书名、类型（本地还是网络）、阅读进度、资源地址、书的封面
        String CREATE_TABLE1 = "CREATE TABLE if not exists "
                + TABLE_NAME1
                + " (_id INTEGER PRIMARY KEY, name TEXT, type INTEGER ,progress INTEGER, address TEXT,image BLOB)";
        //搜索历史
        String CREATE_TABLE2 = "CREATE TABLE if not exists "
                + TABLE_NAME2
                + " (_id INTEGER PRIMARY KEY, content TEXT)";
        sqLiteDatabase.execSQL(CREATE_TABLE1);
        sqLiteDatabase.execSQL(CREATE_TABLE2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int ii) {

    }
}
