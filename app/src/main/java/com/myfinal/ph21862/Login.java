package com.myfinal.ph21862;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.myfinal.ph21862.dao.ThuThuDAO;
import com.myfinal.ph21862.model.ThuThu;

import java.util.ArrayList;

public class Login extends AppCompatActivity {
    private EditText edUser, edPass;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        ThuThuDAO thuThuDAO = new ThuThuDAO(this);


        edUser = findViewById(R.id.edUser);
        edPass = findViewById(R.id.edPass);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edUser.getText().toString().trim();
                String pass = edPass.getText().toString().trim();

                if (thuThuDAO.checkLogin(user, pass)){

                    ArrayList<ThuThu> list = thuThuDAO.getInfoThuThu(user);
                    // Lưu share references
                    SharedPreferences sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("matt", user);
                    editor.putString("hoten", list.get(0).getHoten());
                    editor.putString("level", thuThuDAO.getLevel(user));
                    editor.commit();
                    Toast.makeText(getApplicationContext(), "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Login.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                } else {
                    Toast.makeText(getApplicationContext(), "Username hoặc Password không chính xác!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}