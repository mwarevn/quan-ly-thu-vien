package com.myfinal.ph21862.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myfinal.ph21862.R;
import com.myfinal.ph21862.addapter.PhieuMuonAddapter;
import com.myfinal.ph21862.dao.PhieuMuonDAO;
import com.myfinal.ph21862.dao.SachDAO;
import com.myfinal.ph21862.dao.ThanhVienDAO;
import com.myfinal.ph21862.model.PhieuMuon;
import com.myfinal.ph21862.model.Sach;
import com.myfinal.ph21862.model.ThanhVien;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class QLPhieuMuonFragment extends Fragment {
    PhieuMuonDAO phieuMuonDAO;
    ArrayList<PhieuMuon> list;
    PhieuMuonAddapter addapter;
    Dialog dialog;
    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
    RecyclerView recycler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_qlphieumuon, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recycler = view.findViewById(R.id.recyclerQLPhieuMuon);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button btnAdd = view.findViewById(R.id.floatAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAdd();
            }
        });

        loadData();
        super.onViewCreated(view, savedInstanceState);
    }

    private void showDialogAdd() {
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.add_phieu_muon);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Spinner spnThanhVien = dialog.findViewById(R.id.spnThanhVien);
        getThanhVien(spnThanhVien);

        Spinner spnSach = dialog.findViewById(R.id.spnSach);
        getSach(spnSach);

        if (spnSach.getCount() <= 0) {
            Toast.makeText(getContext(), "Vui lòng thêm ít nhất 1 sách !", Toast.LENGTH_SHORT).show();
            return;
        }

        if (spnThanhVien.getCount() <= 0) {
            Toast.makeText(getContext(), "Vui lòng thêm ít nhất 1 thành viên !", Toast.LENGTH_SHORT).show();
            return;
        }

        dialog.findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // lấy mã thành viên
                HashMap<String, Object> hsTV = (HashMap<String, Object>) spnThanhVien.getSelectedItem();
                int matv = (int) hsTV.get("matv");

                // Mã sách
                HashMap<String, Object> hsSach = (HashMap<String, Object>) spnSach.getSelectedItem();
                int masach = (int) hsSach.get("masach");


                int tien = (int) hsSach.get("giathue");

                addPhieuMuon(matv, masach, tien);
            }
        });
        dialog.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    }

    private void getThanhVien(Spinner spnSpinner) {
        ThanhVienDAO thanhVienDAO = new ThanhVienDAO(getContext());
        ArrayList<ThanhVien> list = thanhVienDAO.getList();
        ArrayList<HashMap<String, Object>> maps = new ArrayList<>();

        for (ThanhVien x : list) {
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("matv", x.getMatv());
            hs.put("hoten", x.getHoten());
            maps.add(hs);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), maps, android.R.layout.simple_list_item_1, new String[]{"hoten"}, new int[]{android.R.id.text1});
        spnSpinner.setAdapter(simpleAdapter);
    }

    private void getSach(Spinner spnSpinner) {
        SachDAO sachDAO = new SachDAO(getContext());
        ArrayList<Sach> list = sachDAO.getDSDauSach();
        ArrayList<HashMap<String, Object>> maps = new ArrayList<>();

        for (Sach x : list) {
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("masach", x.getMasach());
            hs.put("tensach", x.getTensach());
            hs.put("giathue", x.getGiathue());
            maps.add(hs);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), maps, android.R.layout.simple_list_item_1, new String[]{"tensach"}, new int[]{android.R.id.text1});
        spnSpinner.setAdapter(simpleAdapter);
    }

    private void addPhieuMuon(int matv, int masach, int tien) {
        SharedPreferences preferences = getContext().getSharedPreferences("USER", Context.MODE_PRIVATE);
        String matt = preferences.getString("matt", "");
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
        String ngay = format.format(date);

        PhieuMuon phieuMuon = new PhieuMuon(matv, matt, masach, ngay, 0, tien);

        boolean check = phieuMuonDAO.addPhieuMuon(phieuMuon);

        if (check) {
            loadData();
            dialog.dismiss();
            Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadData() {
        phieuMuonDAO = new PhieuMuonDAO(getContext());
        list = phieuMuonDAO.getListPhieuMuon();
        addapter = new PhieuMuonAddapter(list, getContext());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recycler.setLayoutManager(manager);
        recycler.setAdapter(addapter);
    }
}
