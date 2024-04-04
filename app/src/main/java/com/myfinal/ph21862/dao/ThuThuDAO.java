package com.myfinal.ph21862.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.myfinal.ph21862.database.DbHelper;
import com.myfinal.ph21862.model.ThuThu;

import java.util.ArrayList;

public class ThuThuDAO {
    DbHelper dbHelper;
    SharedPreferences sharedPreferences;

    public ThuThuDAO(Context context) {
        dbHelper = new DbHelper(context);
        sharedPreferences = context.getSharedPreferences("USER", Context.MODE_PRIVATE);
    }

    // Login
    public boolean checkLogin(String mtt, String matkhau) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM THUTHU WHERE matt = ? AND matkhau = ?", new String[]{mtt, matkhau});
        if (cursor.getCount() != 0)
            return true;
        else
            return false;
    }

    public int updatePass(String username, String oldPass, String newPass) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM THUTHU WHERE matt = ? AND matkhau = ?", new String[]{username, oldPass});

        if (cursor.getCount() != 0) {
            ContentValues values = new ContentValues();
            values.put("matkhau", newPass);
            long check = database.update("THUTHU", values, "matt = ?", new String[]{username});
            if (check == -1)
                return -1;
            return 1;
        }
        return 0;
    }

    public String getLevel(String matt) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT level FROM THUTHU WHERE matt = ?", new String[]{matt});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            return cursor.getString(0);
        } else return "Lỗi xác thực !";
    }

    public boolean addThuThu(String matt, String hoten, String matkhau, String level) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("matt", matt);
        values.put("hoten", hoten);
        values.put("matkhau", matkhau);
        values.put("level", level);

        long check = db.insert("THUTHU", null, values);

        return check > 0 ? true : false;

    }

    public ArrayList<ThuThu> getListThuThu() {
        String username = sharedPreferences.getString("matt", "");
        ArrayList<ThuThu> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM THUTHU", null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                if (!cursor.getString(0).equals(username)) {
                    list.add(new ThuThu(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
                }
            } while (cursor.moveToNext());
        }

        return list;
    }

    public boolean notExistsUser(String matt) {
        SQLiteDatabase database =dbHelper.getReadableDatabase();
        Cursor cursor= database.rawQuery("SELECT * FROM THUTHU WHERE matt = ?", new String[]{matt});

        return cursor.getCount() > 0 ? false : true;
    }

    public boolean delete(String matt) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        long check = database.delete("THUTHU", "matt = ?", new String[]{matt});

        return check == 1 ? true : false;

    }


    public boolean update(String matt, String hoten, String matkhau) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("hoten", hoten);
        values.put("matkhau", matkhau);

        long check = database.update("THUTHU", values, "matt = ?", new String[]{matt});

        return check == 1 ? true : false;
    }

    public ArrayList<ThuThu> getInfoThuThu(String matt) {
        ArrayList<ThuThu> list = new ArrayList<>();
        SQLiteDatabase database =dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM THUTHU WHERE matt = ?", new String[]{matt});

        if (cursor.getCount() == 1) {
            cursor.moveToFirst();
            list.add(new ThuThu(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
        }

        return list;
    }
}
