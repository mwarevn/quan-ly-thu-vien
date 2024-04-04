package com.myfinal.ph21862.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myfinal.ph21862.R;
import com.myfinal.ph21862.addapter.ThanhVienAddapter;
import com.myfinal.ph21862.dao.ThanhVienDAO;
import com.myfinal.ph21862.model.Sach;
import com.myfinal.ph21862.model.ThanhVien;

import java.util.ArrayList;
import java.util.Locale;

public class QLThanhVienFragment extends Fragment {
    private RecyclerView recycler;
    private Button btnAddMember;
    private ArrayList<ThanhVien> list;
    private ThanhVienDAO dao;
    private ThanhVienAddapter addapter;
    private EditText edSearch;
    private Button btnSearch, btnHuy;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qlthanhvien, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        dao = new ThanhVienDAO(getContext());
        recycler = view.findViewById(R.id.recyclerMember);

        edSearch = view.findViewById(R.id.edSearch);
        btnSearch = view.findViewById(R.id.btnSearch);
        btnHuy = view.findViewById(R.id.btnCancel);

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMember();
                edSearch.setText("");
                Toast.makeText(getContext(), "Refresed", Toast.LENGTH_SHORT).show();
            }
        });

        loadMember();

        btnAddMember = view.findViewById(R.id.btnAddMember);
        btnAddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAddMember();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edSearch.getText().toString();
                ArrayList<ThanhVien> search = new ArrayList<>();

                if (name.equals("")) {
                    Toast.makeText(getContext(), "Vui lòng nhập tên người dùng !", Toast.LENGTH_SHORT).show();
                    return;
                }
                
                int i = 0;

                for (ThanhVien x: list) {
                    if (x.getHoten().toLowerCase(Locale.ROOT).contains(name.toLowerCase(Locale.ROOT))) {
                        search.add(x);
                        i = 1;
//                        break;
                    }
                }
                
                if (i == 0) {
//                    loadMember();
                    Toast.makeText(getContext(), "Không có user nào trùng khớp !", Toast.LENGTH_SHORT).show();
                } else {
                    loadSearch(search);
                    Toast.makeText(getContext(), "Đã tìm thấy !", Toast.LENGTH_SHORT).show();
                }


            }
        });

        edSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (edSearch.getText().toString().equals(""))
                    loadMember();
                return false;
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    public void showDialogAddMember() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_add_member);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        EditText edName = dialog.findViewById(R.id.edFullName);
        EditText edYear = dialog.findViewById(R.id.edYear);
        dialog.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoten = edName.getText().toString().trim();
                String namsinh = edYear.getText().toString().trim();

                if (hoten.equals("")) {
                    Toast.makeText(getContext(), "Vui lòng không bỏ trống Họ Tên!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (namsinh.equals("")) {
                    Toast.makeText(getContext(), "Vui lòng không bỏ trống Năm Sinh!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (hoten.length() < 5) {
                    Toast.makeText(getContext(), "Họ tên phải có ít nhất 5 ký tự!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (hoten.length() > 15) {
                    Toast.makeText(getContext(), "Họ tên không được phép dài quá 15 ký tự!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check chữ in hoa đầu tiên
                String sss[] = hoten.split("");
                if (!sss[0].matches("[A-Z]")) {
                    Toast.makeText(getContext(), "Chữ cái đầu tiên phải viết hoa!", Toast.LENGTH_SHORT).show();
                    return;
                }


                Toast.makeText(getContext(), dao.addMember(hoten, namsinh), Toast.LENGTH_SHORT).show();
                loadMember();
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
    
    public void loadSearch(ArrayList<ThanhVien> lists) {
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(manager);
        addapter = new ThanhVienAddapter(getContext(), lists);
        recycler.setAdapter(addapter);
    }

    public void loadMember() {
        list = dao.getList();
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(manager);
        addapter = new ThanhVienAddapter(getContext(), list);
        recycler.setAdapter(addapter);
    }
}
