package com.myfinal.ph21862.addapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myfinal.ph21862.R;
import com.myfinal.ph21862.dao.PhieuMuonDAO;
import com.myfinal.ph21862.model.PhieuMuon;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class PhieuMuonAddapter extends RecyclerView.Adapter<PhieuMuonAddapter.viewHolder> {

    private ArrayList<PhieuMuon> list;
    private Context context;
    String statusColor = "";
    private PhieuMuonDAO phieuMuonDAO;

    public PhieuMuonAddapter(ArrayList<PhieuMuon> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_phieumuon, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        PhieuMuon phieuMuon = list.get(position);
        phieuMuonDAO = new PhieuMuonDAO(context);

        holder.txtMaPM.setText("Mã Phiếu: " + phieuMuon.getMapm());
        holder.txtTenSach.setText("Sách: " + phieuMuon.getTenSach());

        holder.loaiSach.setText("Mã Sách: " + phieuMuon.getMasach());
        holder.thanhVien.setText("Thành Viên: " + phieuMuon.getTenTv());
        holder.thuThu.setText("Thủ Thư: " + phieuMuon.getTenTt());
        holder.ngayMuon.setText("Ngày Mượn: " + phieuMuon.getNgay());
        holder.tienThue.setText("Tiền Thuê: " + phieuMuon.getTienthue());

        if (phieuMuon.getTrasach() == 0)
            statusColor = "#F44336";
        else
            statusColor = "#2C90DF";

        holder.trangthai.setButtonTintList(ColorStateList.valueOf(Color.parseColor(statusColor)));
        holder.txtMaPM.setTextColor(Color.parseColor(statusColor));

        /* set color */

        holder.txtTenSach.setTextColor(Color.parseColor(statusColor));
        holder.loaiSach.setTextColor(Color.parseColor(statusColor));
        holder.thanhVien.setTextColor(Color.parseColor(statusColor));
        holder.thuThu.setTextColor(Color.parseColor(statusColor));
        holder.ngayMuon.setTextColor(Color.parseColor(statusColor));
        holder.tienThue.setTextColor(Color.parseColor(statusColor));

        /* set color */


        holder.trangthai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (phieuMuon.getTrasach() == 0) {
                    statusColor = "#F44336";
                    phieuMuon.setTrasach(1);
                    phieuMuonDAO.status(phieuMuon.getMapm(), 1);
                    Toast.makeText(context, "Đã thay đổi sang trạng thái đã trả sách.", Toast.LENGTH_SHORT).show();
                }

                else {
                    statusColor = "#2C90DF";
                    phieuMuon.setTrasach(0);
                    phieuMuonDAO.status(phieuMuon.getMapm(), 0);
                    Toast.makeText(context, "Đã thay đổi sang trạng thái chưa trả sách.", Toast.LENGTH_SHORT).show();
                }

                holder.trangthai.setButtonTintList(ColorStateList.valueOf(Color.parseColor(statusColor)));
                holder.txtMaPM.setTextColor(Color.parseColor(statusColor));

                /* set color */

                holder.txtTenSach.setTextColor(Color.parseColor(statusColor));
                holder.loaiSach.setTextColor(Color.parseColor(statusColor));
                holder.thanhVien.setTextColor(Color.parseColor(statusColor));
                holder.thuThu.setTextColor(Color.parseColor(statusColor));
                holder.ngayMuon.setTextColor(Color.parseColor(statusColor));
                holder.tienThue.setTextColor(Color.parseColor(statusColor));

                /* set color */
                notifyDataSetChanged();
            }
        });


        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có chắc muốn xóa phiếu mượn này không ?");
                builder.setNegativeButton("không", null);
                builder.setPositiveButton("có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (phieuMuonDAO.deletePhieuMuon(phieuMuon.getMapm())) {
                            Toast.makeText(context, "Xóa thành công.", Toast.LENGTH_SHORT).show();
                            list.remove(phieuMuon);
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
    }

    @Override
    public int getItemCount() {
        if (list != null)
            return list.size();
        return 0;
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        private ImageView btnRemove;
        private RadioButton trangthai;
        private TextView txtMaPM, txtTenSach, loaiSach, thanhVien, thuThu, ngayMuon, tienThue;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            trangthai = itemView.findViewById(R.id.trangthai);
            txtMaPM = itemView.findViewById(R.id.txtMaPM);
            txtTenSach = itemView.findViewById(R.id.txtTenSach);
            loaiSach = itemView.findViewById(R.id.txtLoaiSach);
            thanhVien = itemView.findViewById(R.id.txtTenThanhVien);
            thuThu = itemView.findViewById(R.id.txtTenThuThu);
            ngayMuon = itemView.findViewById(R.id.txtNgayMuon);
            tienThue = itemView.findViewById(R.id.txtTienThue);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }
    }
}
