package com.myfinal.ph21862.dao;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.myfinal.ph21862.database.DbHelper;
import com.myfinal.ph21862.model.Sach;

import java.util.ArrayList;

public class ThongKeDAO {
    DbHelper dbHelper;

    public ThongKeDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public ArrayList<Sach> getTop10() {
        ArrayList<Sach> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT pm.masach, book.tensach, COUNT(pm.masach) FROM PHIEUMUON pm, SACH book WHERE pm.masach = book.masach GROUP BY pm.masach, book.tensach ORDER BY COUNT(pm.masach) DESC LIMIT 10", null);

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            do {
                list.add(new Sach(cursor.getInt(0), cursor.getString(1), cursor.getInt(2)));
            } while (cursor.moveToNext());
        }

        return list;
    }

    public int getDoanhThu(String fromDate, String toDate) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT SUM(tienthue) FROM PHIEUMUON WHERE substr(ngay, 7) || substr(ngay, 4, 2) || substr(ngay, 1, 2) BETWEEN ? AND ?", new String[]{fromDate, toDate});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            Log.d(TAG, "getDoanhThu: " + cursor.getInt(0));
            return cursor.getInt(0);
        }

        else return 0;
    }
}
