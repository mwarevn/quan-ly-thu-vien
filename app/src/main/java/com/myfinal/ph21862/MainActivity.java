package com.myfinal.ph21862;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;
import com.myfinal.ph21862.dao.ThuThuDAO;
import com.myfinal.ph21862.fragment.QLLoaiSachFragment;
import com.myfinal.ph21862.fragment.QLPhieuMuonFragment;
import com.myfinal.ph21862.fragment.QLSachFragment;
import com.myfinal.ph21862.fragment.QLThanhVienFragment;
import com.myfinal.ph21862.fragment.QuanLyThuThuFragment;
import com.myfinal.ph21862.fragment.ThongKeDoanhThuFragment;
import com.myfinal.ph21862.fragment.ThongKeTop10Fragment;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    SharedPreferences sharedPreferences;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);
        String level = sharedPreferences.getString("level", "");
        String hoten = sharedPreferences.getString("hoten", "");

        Toolbar toolbar = findViewById(R.id.toolBar);
        FrameLayout frameLayout = findViewById(R.id.frameLayout);
        NavigationView navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerlayout);

        View header = navigationView.getHeaderView(0);
        TextView userFullname = header.findViewById(R.id.userFullname);

        userFullname.setText(hoten + " ( " + level + " )");

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.mQLPhieuMuon:
                        fragment = new QLPhieuMuonFragment();
                        break;

                    case R.id.mQLLoaiSach:
                        fragment = new QLLoaiSachFragment();
                        break;

                    case R.id.mThoat:
                        Intent intent = new Intent(MainActivity.this, Login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        break;

                    case R.id.mDoiMatKhau:
                        showDialogChangPass();
                        break;

                    case R.id.mTop10:
                        if (!level.equals("admin")) {
                            Toast.makeText(getApplicationContext(), "Bạn không có quyền truy cập vào đây !", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        fragment = new ThongKeTop10Fragment();
                        break;

                    case R.id.mDoanhThu:
                        if (!level.equals("admin")) {
                            Toast.makeText(getApplicationContext(), "Bạn không có quyền truy cập vào đây !", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        fragment = new ThongKeDoanhThuFragment();
                        break;
                    case R.id.mQLThanhVien:
                        fragment = new QLThanhVienFragment();
                        break;
                    case R.id.mQLSach:
                        fragment = new QLSachFragment();
                        break;

                    case R.id.mQuanLyThuThu:
                        if (!level.equals("admin")) {
                            Toast.makeText(getApplicationContext(), "Bạn không có quyền truy cập vào đây !", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        fragment = new QuanLyThuThuFragment();
                        break;

                    default:
                        fragment = new QLPhieuMuonFragment();
                        break;
                }

                if (fragment != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit();
                    toolbar.setTitle(item.getTitle());
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameLayout, new QLPhieuMuonFragment()).commit();
    }

    @SuppressLint("SuspiciousIndentation")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) ;
        drawerLayout.openDrawer(GravityCompat.START);
        return super.onOptionsItemSelected(item);
    }

    private void showDialogChangPass() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_change_pass);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        EditText edtOldPass = dialog.findViewById(R.id.edtOldPass);
        EditText edtNewPass = dialog.findViewById(R.id.edtNewPass);
        EditText edtReNewPass = dialog.findViewById(R.id.edtReNewPass);
        dialog.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPass = edtOldPass.getText().toString().trim();
                String newPass = edtNewPass.getText().toString().trim();
                String ReNewPass = edtReNewPass.getText().toString().trim();

                if (oldPass.equals("") || newPass.equals("") || ReNewPass.equals("")) {
                    Toast.makeText(MainActivity.this, "Vui lòng không bỏ trống thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (newPass.equals(ReNewPass)) {
                    SharedPreferences preferences = getSharedPreferences("USER", MODE_PRIVATE);
                    String matt = preferences.getString("matt", "");
                    ThuThuDAO thuThuDAO = new ThuThuDAO(getApplicationContext());
                    int check = thuThuDAO.updatePass(matt, oldPass, newPass);

                    String msg = "";

                    if (check == 1) {
                        Intent intent = new Intent(MainActivity.this, Login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        msg = "Đổi mật khẩu thành công!";
                    }

                    if (check == 0) msg = "Mật khẩu cũ không chính xác!";

                    if (check == -1) msg = "Cập nhật mật khẩu thất bại";

                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(MainActivity.this, "Nhật khẩu nhập lại không trùng khớp!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.setCancelable(false);
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}