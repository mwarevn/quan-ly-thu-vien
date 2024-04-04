package com.myfinal.ph21862.addapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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
import com.myfinal.ph21862.dao.ThuThuDAO;
import com.myfinal.ph21862.model.ThuThu;

import java.util.ArrayList;

public class ThuThuAddapter extends RecyclerView.Adapter < ThuThuAddapter.viewHolder > {
    Context context;
    ArrayList < ThuThu > list = new ArrayList < > ();
    ThuThuDAO dao;

    public ThuThuAddapter(Context context, ArrayList < ThuThu > list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_thu_thu, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        dao = new ThuThuDAO(context);
        ThuThu tt = list.get(holder.getAdapterPosition());

        holder.matt.setText(tt.getMatt());
        holder.hoten.setText(tt.getHoten());
        holder.matkhau.setText(tt.getMatkhau());
        holder.level.setText(tt.getLevel());

        SharedPreferences sharedPreferences = context.getSharedPreferences("USER", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("matt", "");

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Bạn có chắc muốn xóa không ?");
                builder.setNegativeButton("không", null);
                builder.setPositiveButton("có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do remove in database
                        if (dao.delete(tt.getMatt())) {
                            Toast.makeText(context, "Xóa thành công !", Toast.LENGTH_SHORT).show();
                            list.remove(tt);
                            notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, "Xóa thất bại !", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setCancelable(false);
                builder.show();
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_thu_thu);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                EditText edMatt = dialog.findViewById(R.id.edMatt);
                EditText edhoten = dialog.findViewById(R.id.edHoten);
                EditText edmatkhau = dialog.findViewById(R.id.edMatkhau);

                edMatt.setEnabled(false);

                edMatt.setText(tt.getMatt());
                edhoten.setText(tt.getHoten());
                edmatkhau.setText(tt.getMatkhau());

                dialog.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String hoten = edhoten.getText().toString().trim();
                        String matkhau = edmatkhau.getText().toString().trim();
                        
                        if (hoten.equals("") || matkhau.equals("")) {
                            Toast.makeText(context, "Vui lòng điền đầy đủ thông tin !", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (dao.update(tt.getMatt(), hoten, matkhau)) {
                            Toast.makeText(context, "Update thành công.", Toast.LENGTH_SHORT).show();
                            tt.setHoten(hoten);
                            tt.setMatkhau(matkhau);
                            notifyDataSetChanged();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(context, "Update thất bại !", Toast.LENGTH_SHORT).show();
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
        return list != null ? list.size() : 0;
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private TextView matt, hoten, matkhau, level;
        private ImageView edit, remove;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            matt = itemView.findViewById(R.id.tvMatt);
            hoten = itemView.findViewById(R.id.tvHoten);
            matkhau = itemView.findViewById(R.id.tvMatkhau);
            level = itemView.findViewById(R.id.tvLevel);

            edit = itemView.findViewById(R.id.btnEdit);
            remove = itemView.findViewById(R.id.btnRemove);
        }
    }
}