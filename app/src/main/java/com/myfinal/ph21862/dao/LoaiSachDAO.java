package com.myfinal.ph21862.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.myfinal.ph21862.database.DbHelper;
import com.myfinal.ph21862.model.LoaiSach;

import java.util.ArrayList;

public class LoaiSachDAO {
    DbHelper dbHelper;

    public LoaiSachDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public ArrayList<LoaiSach> getListLoaiSach() {
        ArrayList<LoaiSach> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM LOAISACH", null);

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                list.add(new LoaiSach(cursor.getInt(0), cursor.getString(1)));
            } while (cursor.moveToNext());
        }

        return list;
    }

    public boolean addLoaiSach(String nameType) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenloai", nameType);
        long check = database.insert("LOAISACH", null, values);

        if (check == -1)
            return false;
        else
            return true;
    }


    // 1 true, 0 false, -1 no permission
    public int removeTypeSach(int maloai) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM SACH WHERE maloai = ?", new String[]{String.valueOf(maloai)});

        if (cursor.getCount() > 0)
            return -1;

        long check = database.delete("LOAISACH", "maloai = ?", new String[]{String.valueOf(maloai)});
        if (check == -1)
            return 0;
        else return 1;
    }

    public boolean updateLoaiSach(int maloai, String tenloai) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenloai", tenloai);
        long check = database.update("LOAISACH", values, "maloai = ?", new String[]{String.valueOf(maloai)});
        return check == 1 ? true : false;
    }

    public String getTenLoai(int maloai) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT tenloai FROM LOAISACH WHERE maloai = ?", new String[]{String.valueOf(maloai)});

        if (cursor.getCount() >0 ) {
            cursor.moveToFirst();
            return cursor.getString(0);
        } else return "Lỗi truy vấn!";
    }
}
