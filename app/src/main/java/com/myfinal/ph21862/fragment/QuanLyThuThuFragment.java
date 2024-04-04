package com.myfinal.ph21862.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myfinal.ph21862.R;
import com.myfinal.ph21862.addapter.ThuThuAddapter;
import com.myfinal.ph21862.dao.ThuThuDAO;
import com.myfinal.ph21862.model.ThuThu;

import java.util.ArrayList;

public class QuanLyThuThuFragment extends Fragment {
    RecyclerView recycler;
    ThuThuDAO dao;
    ArrayList<ThuThu> list = new ArrayList<>();
    ThuThuAddapter addapter;
    FloatingActionButton btnAddThuThu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quan_ly_thu_thu, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycler = view.findViewById(R.id.recycler_thuthu);
        btnAddThuThu = view.findViewById(R.id.btnAddThuThu);

        dao = new ThuThuDAO(getContext());
        loadData();

        btnAddThuThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_thu_thu);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                EditText edMatt = dialog.findViewById(R.id.edMatt);
                EditText edhoten = dialog.findViewById(R.id.edHoten);
                EditText edmatkhau = dialog.findViewById(R.id.edMatkhau);

                dialog.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String matt = edMatt.getText().toString().trim();
                        String hoten = edhoten.getText().toString().trim();
                        String matkhau = edmatkhau.getText().toString().trim();

                        if (matt.equals("") || hoten.equals("") || matkhau.equals("")) {
                            Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin !", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (matt.contains(" ")) {
                            Toast.makeText(getContext(), "Mã thủ thư không được chứa dấu cách !", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (!dao.notExistsUser(matt)) {
                            Toast.makeText(getContext(), "Mã thủ thư đã tồn tại trong hệ thống !", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (dao.addThuThu(matt, hoten, matkhau, "thuthu")) {
                            Toast.makeText(getContext(), "Thêm thành công.", Toast.LENGTH_SHORT).show();
                            loadData();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(getContext(), "Thêm thất bại !", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.setCancelable(false);
                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
    }

    public void loadData() {
        list.clear();
        list = dao.getListThuThu();
        recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(new ThuThuAddapter(getContext(), list));
    }
}
