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
import com.myfinal.ph21862.dao.ThanhVienDAO;
import com.myfinal.ph21862.model.ThanhVien;

import java.util.ArrayList;

public class ThanhVienAddapter extends RecyclerView.Adapter<ThanhVienAddapter.viewHolder> {
    private Context context;
    private ThanhVienDAO dao;
    private ArrayList<ThanhVien> list = new ArrayList<>();

    public ThanhVienAddapter(Context context, ArrayList<ThanhVien> list) {
        this.context = context;
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_thanh_vien, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        dao = new ThanhVienDAO(context);
        ThanhVien mem = list.get(position);
        String statusColor = "";

        holder.tvName.setText(mem.getHoten());
        holder.tvYear.setText(mem.getNamsinh());

        if (holder.getAdapterPosition() %2 == 0)
            statusColor = "#F44336";
        else
            statusColor = "#4CAF50";

        holder.tvName.setTextColor(Color.parseColor(statusColor));
        holder.tvYear.setTextColor(Color.parseColor(statusColor));

        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có chắc muốn xóa mục này không?");
                builder.setNegativeButton("không", null);
                builder.setPositiveButton("có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (dao.deleteMember(mem.getMatv())) {
                            list.remove(mem);
                            notifyDataSetChanged();
                            Toast.makeText(context, "Xóa thành công.", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(context, "Không thể xóa thành viên này!", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_add_member);
                EditText edName = dialog.findViewById(R.id.edFullName);
                EditText edYear = dialog.findViewById(R.id.edYear);
                edName.setText(mem.getHoten());
                edYear.setText(mem.getNamsinh());
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
                        String hoten = edName.getText().toString().trim();
                        String namsinh = edYear.getText().toString().trim();
                        if (hoten.equals("")) {
                            Toast.makeText(context, "Vui lòng không bỏ trống Họ Tên!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (namsinh.equals("")) {
                            Toast.makeText(context, "Vui lòng không bỏ trống Năm Sinh!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (hoten.length() < 5) {
                            Toast.makeText(context, "Họ tên phải có ít nhất 5 ký tự!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (hoten.length() > 15) {
                            Toast.makeText(context, "Họ tên không được phép dài quá 15 ký tự!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        String msg = dao.updateMember(mem.getMatv(), hoten, namsinh);
                        if (msg.equals("Update thành công.")) {
                            mem.setHoten(hoten);
                            mem.setNamsinh(namsinh);
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, "Update thất bại!", Toast.LENGTH_SHORT).show();
                        }

                        dialog.dismiss();
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
        private TextView tvName, tvYear;
        private ImageView btnEdit, btnRemove;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnRemove = itemView.findViewById(R.id.btnRemove);
            tvName = itemView.findViewById(R.id.tvName);
            tvYear = itemView.findViewById(R.id.tvYear);
        }
    }
}
