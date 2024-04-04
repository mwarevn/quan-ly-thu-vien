package com.myfinal.ph21862.addapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myfinal.ph21862.R;
import com.myfinal.ph21862.dao.LoaiSachDAO;
import com.myfinal.ph21862.model.LoaiSach;
import com.myfinal.ph21862.model.Sach;

import java.util.ArrayList;

public class LoaiSachAddapter extends RecyclerView.Adapter < LoaiSachAddapter.viewHolder > {

    Context context;
    LoaiSachDAO dao;

    public LoaiSachAddapter(Context context, ArrayList < LoaiSach > list) {
        this.context = context;
        this.list = list;
    }

    ArrayList < LoaiSach > list = new ArrayList < > ();

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_loai_sach, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        dao = new LoaiSachDAO(context);
        LoaiSach sach = list.get(holder.getAdapterPosition());
//        holder.tvMaLoai.setText(sach.getId() + "");
        holder.tvTenLoai.setText(sach.getTenLoai());

        // Xóa loại sách
        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có chắn chắn muốn xóa mục này?");
                builder.setNegativeButton("không", null);
                builder.setPositiveButton("có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int res = dao.removeTypeSach(sach.getId());
                        String msg = "";

                        switch (res) {
                            case -1:
                                msg = "Không thể xóa loại sách này !";
                                break;
                            case 0:
                                msg = "Xóa thất bại !";
                                break;
                            case 1:
                                list.remove(sach);
                                notifyDataSetChanged();
                                msg = "Xóa thành công.";
                                break;
                            default:
                                msg = "Lỗi không xác định, không thể xóa !";
                                break;
                        }

                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setCancelable(false);
                builder.show();
            }
        });

        // Edit loại sách
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.edit_loai_sach_layout);
                EditText nameType = dialog.findViewById(R.id.edNameType);
                TextView tvMaLoai = dialog.findViewById(R.id.tvMaLoai);
                tvMaLoai.setText("Mã Loại: " + sach.getId());
                nameType.setText(sach.getTenLoai());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = nameType.getText().toString().trim();

                        if (name.equals("")) {
                            Toast.makeText(context, "Vui lòng không để trống thông tin !", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (dao.updateLoaiSach(sach.getId(), name)) {
                            Toast.makeText(context, "Đổi tên thành công.", Toast.LENGTH_SHORT).show();
                            sach.setTenLoai(name);
                            notifyDataSetChanged();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(context, "Đổi tên loại thất bại !", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.setCancelable(false);
                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private TextView tvMaLoai, tvTenLoai;
        private ImageView btnRemove, btnEdit;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            tvMaLoai = itemView.findViewById(R.id.tvMaLoai);
            tvTenLoai = itemView.findViewById(R.id.tvTenLoai);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }
    }
}