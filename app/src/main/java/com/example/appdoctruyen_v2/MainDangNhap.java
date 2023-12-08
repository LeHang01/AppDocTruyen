package com.example.appdoctruyen_v2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appdoctruyen_v2.database.databasedoctruyen;

public class MainDangNhap extends AppCompatActivity {

    EditText edtTaiKhoan, edtMatKhau;
    Button btnDangNhap, btnDangKy;
    TextView tvLoginStatus; // Add this line

    databasedoctruyen databaseDocTruyen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dang_nhap);
        AnhXa();

        databaseDocTruyen = new databasedoctruyen(this);

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainDangNhap.this,MainDangKy.class);
                startActivity(intent);
            }
        });

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tentaikhoan = edtTaiKhoan.getText().toString();
                String matkhau = edtMatKhau.getText().toString();

                Cursor cursor = databaseDocTruyen.getData();
                boolean loginSuccessful = false;

                while (cursor.moveToNext()) {
                    String datatentaikhoan = cursor.getString(1);
                    String datamatkhau = cursor.getString(2);

                    if (datatentaikhoan.equals(tentaikhoan) && datamatkhau.equals(matkhau)) {
                        int phanquyen = cursor.getInt(4);
                        int idd = cursor.getInt(0);
                        String tentk = cursor.getString(1);
                        String email = cursor.getString(3);

                        Intent intent = new Intent(MainDangNhap.this, MainActivity.class);
                        intent.putExtra("phanq", phanquyen);
                        intent.putExtra("idd", idd);
                        intent.putExtra("email", email);
                        intent.putExtra("tentaikhoan", tentk);

                        loginSuccessful = true;
                        startActivity(intent);
                        break;
                    }
                }

                cursor.moveToFirst();
                cursor.close();

                if (!loginSuccessful) {
                    // Hiển thị thông báo lỗi nếu đăng nhập không thành công
                    tvLoginStatus.setVisibility(View.VISIBLE);
                    tvLoginStatus.setText("Vui lòng kiểm tra lại tài khoản hoặc mật khẩu!");
                } else {
                    // Xóa thông báo lỗi nếu đăng nhập thành công
                    tvLoginStatus.setVisibility(View.GONE);
                }
            }
        });

    }
    private void AnhXa() {
        edtTaiKhoan = findViewById(R.id.edtUsername);
        edtMatKhau = findViewById(R.id.edtPasswod);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        btnDangKy = findViewById(R.id.btnDangKy);
        tvLoginStatus = findViewById(R.id.tvLoginStatus); // Add this line
    }
}