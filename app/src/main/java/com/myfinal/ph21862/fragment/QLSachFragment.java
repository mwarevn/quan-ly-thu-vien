package com.myfinal.ph21862.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myfinal.ph21862.R;
import com.myfinal.ph21862.addapter.SachAddapter;
import com.myfinal.ph21862.dao.LoaiSachDAO;
import com.myfinal.ph21862.dao.SachDAO;
import com.myfinal.ph21862.model.LoaiSach;
import com.myfinal.ph21862.model.Sach;

import java.util.ArrayList;

public class QLSachFragment extends Fragment {
    private SachDAO dao;
    private RecyclerView recycler;
    private SachAddapter addapter;
    private Button addBook;
    private ArrayList<Sach> list = new ArrayList<>();
    private ArrayList<LoaiSach> listType = new ArrayList<>();
    private LoaiSachDAO loaiSachDAO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quan_ly_sach, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        addBook = view.findViewById(R.id.btnAddSach);
        recycler = view.findViewById(R.id.recyclerSach);
        dao = new SachDAO(getContext());
        loaiSachDAO = new LoaiSachDAO(getContext());

        loadListBook();

        addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_edit_sach);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Spinner spinner = dialog.findViewById(R.id.spnLoaiSach);
                EditText edTenSach = dialog.findViewById(R.id.edTenSach);
                EditText edGiaThue = dialog.findViewById(R.id.edGiaThue);
                EditText edSaleoff = dialog.findViewById(R.id.edSaleoff);
                listType = loaiSachDAO.getListLoaiSach();
                if (listType.size() <= 0) {
                    Toast.makeText(getContext(), "Vui lòng thêm loại sách !", Toast.LENGTH_SHORT).show();
                    return;
                }
                ArrayList<String> loaisachs = new ArrayList<>();
                for (LoaiSach x: listType) {
                    loaisachs.add(x.getTenLoai());
                }
                spinner.setAdapter(new ArrayAdapter(getContext(), android.R.layout.simple_dropdown_item_1line, loaisachs));
                dialog.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String tensach = edTenSach.getText().toString().trim();
                        String giathue = edGiaThue.getText().toString().trim();
                        String saleoff = edSaleoff.getText().toString().trim();

                        if (saleoff.equals(""))
                            saleoff = "0";

                        int maloai = listType.get(spinner.getSelectedItemPosition()).getId();

                        Log.d("TAG", "onClick: " + maloai);

                        if (tensach.equals("") || giathue.equals("")) {
                            Toast.makeText(getContext(), "Vui lòng không bỏ trống thông tin !", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (String.valueOf(maloai).equals("")) {
                            Toast.makeText(getContext(), "Vui lòng thêm loại trước !", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        boolean insert = dao.addSach(maloai, tensach, Integer.valueOf(giathue), Integer.valueOf(saleoff));

                        if (insert) {
                            Toast.makeText(getContext(), "Thêm sách thành công .", Toast.LENGTH_SHORT).show();
                            loadListBook();
                        } else {
                            Toast.makeText(getContext(), "Thêm sách thất bại !", Toast.LENGTH_SHORT).show();
                        }

                        dialog.dismiss();
                    }
                });
                dialog.setCancelable(false);
                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    public void loadListBook() {
        list.clear();
        list = dao.getDSDauSach();
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(manager);
        addapter = new SachAddapter(getContext(), list);
        recycler.setAdapter(addapter);
    }
}
