package com.myfinal.ph21862.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.myfinal.ph21862.R;
import com.myfinal.ph21862.dao.ThongKeDAO;

import java.util.Calendar;

public class ThongKeDoanhThuFragment extends Fragment {
    private EditText edFromDate, edToDate;
    private Button btnGetDoanhThu;
    private TextView tvDoanhThu;
    private ThongKeDAO dao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thong_ke_doanh_thu, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        edFromDate = view.findViewById(R.id.edFromDate);
        edToDate = view.findViewById(R.id.edToDate);
        btnGetDoanhThu = view.findViewById(R.id.btnGetDoanhThu);
        tvDoanhThu = view.findViewById(R.id.tvDoanhThu);
        Calendar calendar = Calendar.getInstance();
        dao = new ThongKeDAO(getContext());

        edFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        edFromDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });

        edToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        edToDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });

        btnGetDoanhThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fromDate = edFromDate.getText().toString().trim();
                String toDate = edToDate.getText().toString().trim();

                if (fromDate.equals("") || toDate.equals("")) {
                    Toast.makeText(getContext(), "Vui lòng chọn thời gian hợp lệ!", Toast.LENGTH_SHORT).show();
                    return;
                }
                int doanhthu = dao.getDoanhThu(fromDate, toDate);
                tvDoanhThu.setText(doanhthu + "VNĐ");
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }
}
