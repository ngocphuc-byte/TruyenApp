package com.example.customnav.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.customnav.MainActivity;
import com.example.customnav.R;
import com.example.customnav.ultilServer.Server;

import java.util.HashMap;
import java.util.Map;

public class DangKy extends AppCompatActivity {
    EditText edtTaiKhoan, edtGmail, edtMatkhau, edtMatkhau2;
    String taikhoan, gmail, matkhau, matkhau2;
    String urlDangKy = Server.dangkyKH;
    String urlCheck = Server.checkDangNhap;
    Button btnDangky;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        AnhXa();

    }



    public void DangKy(View view) {
        fill_Information();
        if(taikhoan.isEmpty() || gmail.isEmpty() || matkhau.isEmpty() || matkhau2.isEmpty()){
            Toast.makeText(DangKy.this, "Vui lòng điền đầy đủ thông tin!!", Toast.LENGTH_SHORT).show();
        } else if(!matkhau.equals(matkhau2)){
            Toast.makeText(DangKy.this, "Mật khẩu không khớp nhau!!", Toast.LENGTH_SHORT).show();
        } else {
            check(urlCheck);
        }

    }

    private void check(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("success")){
                    Toast.makeText(DangKy.this, "Gmail hoặc tài khoản đã tồn tại!!", Toast.LENGTH_SHORT).show();
                    edtTaiKhoan.setText("");
                    edtGmail.setText("");
                } else {
                    dangKy(urlDangKy);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DangKy.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> Params = new HashMap<>();
                Params.put("gmail", gmail);
                Params.put("taikhoan", taikhoan);
                return Params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void dangKy(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("success")){
                    Intent intent = new Intent(DangKy.this, MainActivity.class);
                    intent.putExtra("taikhoan", edtTaiKhoan.getText().toString().trim());
                    startActivity(intent);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DangKy.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> Params = new HashMap<>();
                Params.put("taikhoan", taikhoan);
                Params.put("gmail", gmail);
                Params.put("matkhau", matkhau);
                return Params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void fill_Information(){
        taikhoan = edtTaiKhoan.getText().toString().trim();
        gmail = edtGmail.getText().toString().trim();
        matkhau = edtMatkhau.getText().toString().trim();
        matkhau2 = edtMatkhau2.getText().toString().trim();
    }

    private void AnhXa() {
        edtTaiKhoan = findViewById(R.id.edtTaiKhoan);
        edtGmail = findViewById(R.id.edtGmail);
        edtMatkhau = findViewById(R.id.edtMatKhau);
        edtMatkhau2 = findViewById(R.id.edtMatKhau2);
        btnDangky = findViewById(R.id.btnDangKy);
    }

}