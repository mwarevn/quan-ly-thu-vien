package com.myfinal.ph21862.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.myfinal.ph21862.database.DbHelper;
import com.myfinal.ph21862.model.ThanhVien;

import java.util.ArrayList;

public class ThanhVienDAO {
    DbHelper dbHelper;

    public ThanhVienDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public ArrayList<ThanhVien> getList() {
        ArrayList<ThanhVien> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM THANHVIEN", null);

        if (cursor.getCount() != 0) {

            cursor.moveToFirst();
            do {
                list.add(new ThanhVien(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
            } while (cursor.moveToNext());
        }

        return list;
    }

    public boolean isHaveData(int matv) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM PHIEUMUON WHERE matv = ?", new String[]{String.valueOf(matv)});
        return cursor.getCount() > 0 ? true : false;
    }

    public boolean deleteMember(int matv) {
        if (isHaveData(matv))
            return false;
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        long check = database.delete("THANHVIEN", "matv = ?", new String[]{String.valueOf(matv)});
        return check == 1 ? true : false;
    }

    public String addMember(String hoten, String namsinh) {
        String msg = "";
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("hoten", hoten);
        values.put("namsinh", namsinh);
        long check = database.insert("THANHVIEN", null, values);

        if (check > 0)
            msg = "Thêm thành công.";
        else
            msg = "Thêm thất bại";

        return msg;
    }

    public String updateMember(int matv, String hoten, String namsinh) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("hoten", hoten);
        values.put("namsinh", namsinh);
        long check = database.update("THANHVIEN", values, "matv = ? ", new String[]{String.valueOf(matv)});

        String msg = "";

        if (check == 1)
            msg = "Update thành công.";
        else
            msg = "Update thất bại.";

        return msg;
    }
}
