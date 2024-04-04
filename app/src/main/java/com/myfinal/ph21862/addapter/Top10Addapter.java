package com.myfinal.ph21862.addapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myfinal.ph21862.R;
import com.myfinal.ph21862.model.Sach;

import java.util.ArrayList;

public class Top10Addapter extends RecyclerView.Adapter<Top10Addapter.viewHolder> {
    private Context context;
    private ArrayList<Sach> list = new ArrayList<>();

    public Top10Addapter(Context context, ArrayList<Sach> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_top10, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Sach sach = list.get(position);

        holder.masach.setText("Mã Sách: " + sach.getMasach());
        holder.tensach.setText("Tên Sách: " + sach.getTensach());
        holder.soluong.setText("Số Lượng Mượn: " + sach.getSoluondamuon());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView masach, tensach, soluong;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            masach = itemView.findViewById(R.id.txtMaSach);
            tensach = itemView.findViewById(R.id.txtTenSach);
            soluong = itemView.findViewById(R.id.txtSoLuongMuon);
        }
    }
}
