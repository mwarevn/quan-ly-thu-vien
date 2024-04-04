package com.myfinal.ph21862.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myfinal.ph21862.R;
import com.myfinal.ph21862.addapter.LoaiSachAddapter;
import com.myfinal.ph21862.dao.LoaiSachDAO;
import com.myfinal.ph21862.model.LoaiSach;

import java.util.ArrayList;

public class QLLoaiSachFragment extends Fragment {

    private EditText edtAdd;
    RecyclerView recycler;
    LoaiSachDAO loaiSachDAO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_qlloaisach, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recycler = view.findViewById(R.id.recyclerLoaiSach);
        loaiSachDAO = new LoaiSachDAO(getContext());

        loadData();

        view.findViewById(R.id.btnAddLoai).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtAdd = view.findViewById(R.id.edtLoaiSach);
                String nameType = edtAdd.getText().toString().trim();

                if (nameType.equals("")) {
                    Toast.makeText(view.getContext(), "Vui lòng nhập tên loại sách!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (loaiSachDAO.addLoaiSach(nameType)){
                    Toast.makeText(view.getContext(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
                    edtAdd.setText("");
                    loadData();
                } else {
                    Toast.makeText(view.getContext(), "Thêm thất bại!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    public void loadData() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(manager);
        ArrayList<LoaiSach> list = loaiSachDAO.getListLoaiSach();
        LoaiSachAddapter addapter = new LoaiSachAddapter(getContext(), list);
        recycler.setAdapter(addapter);
    }
}
