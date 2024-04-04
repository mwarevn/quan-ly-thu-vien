package com.myfinal.ph21862.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.myfinal.ph21862.database.DbHelper;
import com.myfinal.ph21862.model.PhieuMuon;

import java.util.ArrayList;

public class PhieuMuonDAO {
    DbHelper dbHelper;

    public PhieuMuonDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public ArrayList<PhieuMuon> getListPhieuMuon() {
        ArrayList<PhieuMuon> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT pm.mapm, pm.matv, tv.hoten, pm.matt, tt.hoten, pm.masach, book.tensach, pm.ngay, pm.trasach, pm.tienthue FROM PHIEUMUON pm, THANHVIEN tv, THUTHU tt, SACH book  WHERE pm.matv = tv.matv and pm.matt = tt.matt AND pm.masach = book.masach ORDER BY pm.mapm", null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                list.add(new PhieuMuon(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5), cursor.getString(6), cursor.getString(7), cursor.getInt(8), cursor.getInt(9)));
            } while (cursor.moveToNext());
        }
        return list;
    }

    public boolean status(int maPM, int code) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("trasach", code);
        long check = database.update("PHIEUMUON", contentValues, "mapm = ?", new String[]{String.valueOf(maPM)});
        if (check == -1)
            return false;
        else return true;
    }

    public boolean addPhieuMuon(PhieuMuon phieuMuon) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
//        contentValues.put("mapm", phieuMuon.getMapm());
        contentValues.put("matv", phieuMuon.getMatv());
        contentValues.put("matt", phieuMuon.getMatt());
        contentValues.put("masach", phieuMuon.getMasach());
        contentValues.put("ngay", phieuMuon.getNgay());
        contentValues.put("tienthue", phieuMuon.getTienthue());
        contentValues.put("trasach", phieuMuon.getTrasach());

        long check = database.insert("PHIEUMUON", null, contentValues);

        return check > 0 ? true : false;
    }

    public boolean deletePhieuMuon(int mapm) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long check = db.delete("PHIEUMUON", "mapm = ?", new String[]{String.valueOf(mapm)});
        return check > 0 ? true : false;
    }
}
