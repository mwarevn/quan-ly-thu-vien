package com.myfinal.ph21862.addapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myfinal.ph21862.R;
import com.myfinal.ph21862.dao.LoaiSachDAO;
import com.myfinal.ph21862.dao.SachDAO;
import com.myfinal.ph21862.model.LoaiSach;
import com.myfinal.ph21862.model.Sach;

import java.util.ArrayList;

public class SachAddapter extends RecyclerView.Adapter<SachAddapter.viewHolder> {
    private Context context;
    private SachDAO dao;
    private ArrayList<Sach> list = new ArrayList<>();

    public SachAddapter(Context context, ArrayList<Sach> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_sach, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        dao = new SachDAO(context);
        LoaiSachDAO loaiSachDAO = new LoaiSachDAO(context);
        Sach sach = list.get(position);
        String tenloai = loaiSachDAO.getTenLoai(sach.getMaloai());
        holder.type.setText(tenloai);
        holder.name.setText(sach.getTensach());
        holder.price.setText(sach.getGiathue() + "");
        holder.tvSaleoff.setText("Khuyến mại: " + sach.getSaleoff() + " %");

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thông báo!");
                builder.setMessage("Bạn có chắc chắn muốn xóa mục này không ?");
                builder.setNegativeButton("không", null);
                builder.setPositiveButton("có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String msg = dao.deleteSach(sach.getMasach());
                        if (msg.equals("Xóa thành công.")) {
                            list.remove(sach);
                            notifyDataSetChanged();
                        }
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_edit_sach);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Spinner spinner = dialog.findViewById(R.id.spnLoaiSach);
                EditText edTenSach = dialog.findViewById(R.id.edTenSach);
                EditText edGiaThue = dialog.findViewById(R.id.edGiaThue);
                EditText edSaleoff = dialog.findViewById(R.id.edSaleoff);

                edTenSach.setText(sach.getTensach());
                edGiaThue.setText(sach.getGiathue() + "");
                edSaleoff.setText(String.valueOf(sach.getSaleoff()));

                ArrayList<LoaiSach> listType = loaiSachDAO.getListLoaiSach();
                ArrayList<String> loaisachs = new ArrayList<>();
                int spnIndex = 0;
                for (LoaiSach x : listType) {
                    loaisachs.add(x.getTenLoai());

                    if (x.getId() == sach.getMaloai()) {
                        spnIndex = list.indexOf(x);
                    }
                }
                spinner.setAdapter(new ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, loaisachs));
                spinner.setSelection(spnIndex);
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
                        int masach = sach.getMasach();

                        Log.d("TAG", "onClick: " + maloai);

                        if (tensach.equals("") || giathue.equals("")) {
                            Toast.makeText(context, "Vui lòng không bỏ trống thông tin !", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        boolean insert = dao.updateSach(masach, maloai, tensach, Integer.valueOf(giathue), Integer.valueOf(saleoff));

                        if (insert) {
                            Toast.makeText(context, "Update thành công .", Toast.LENGTH_SHORT).show();
                            sach.setGiathue(Integer.valueOf(giathue));
                            sach.setTensach(tensach);
                            sach.setMaloai(maloai);
                            sach.setSaleoff(Integer.valueOf(saleoff));
                            notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, "Thêm sách thất bại !", Toast.LENGTH_SHORT).show();
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
        TextView type, name, price, tvSaleoff;
        ImageView edit, remove;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.tvLoaiSach);
            name = itemView.findViewById(R.id.tvTenSach);
            price = itemView.findViewById(R.id.tvGiaThue);
            edit = itemView.findViewById(R.id.btnEdit);
            remove = itemView.findViewById(R.id.btnRemove);
            tvSaleoff = itemView.findViewById(R.id.tvSaleoff);
        }
    }
}
