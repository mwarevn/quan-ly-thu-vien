package com.myfinal.ph21862.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.myfinal.ph21862.database.DbHelper;
import com.myfinal.ph21862.model.Sach;

import java.util.ArrayList;

public class SachDAO {
    DbHelper dbHelper;

    public SachDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    // Select All sách trong CSDL
    public ArrayList<Sach> getDSDauSach() {
        ArrayList<Sach> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM SACH", null);

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            do {
                list.add(new Sach(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4)));
            } while (cursor.moveToNext());
        }

        return list;
    }

    public boolean addSach(int maloai, String tensach, int giathue, int saleoff) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maloai", maloai);
        values.put("tensach", tensach);
        values.put("giathue", giathue);
        values.put("saleoff", saleoff);
        long check = database.insert("SACH", null, values);
        Log.d("TAG", "addSach: " + check);
        return check > 0 ? true : false;
    }

    public boolean updateSach(int masach, int maloai, String tensach, int giathue, int saleoff) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maloai", maloai);
        values.put("tensach", tensach);
        values.put("giathue", giathue);
        values.put("saleoff", saleoff);
        long check = database.update("SACH", values, "masach = ?", new String[]{String.valueOf(masach)});
        return check > 0 ? true : false;
    }

    public String deleteSach(int masach) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM PHIEUMUON WHERE masach = ?", new String[]{String.valueOf(masach)});

        if (cursor.getCount() > 0) {
            return "Không thể xóa sách này !";
        } else {
            database = dbHelper.getWritableDatabase();
            long check = database.delete("SACH", "masach = ?", new String[]{String.valueOf(masach)});
            return check == 1 ? "Xóa thành công." : "Xóa thất bại !";
        }
    }
}
