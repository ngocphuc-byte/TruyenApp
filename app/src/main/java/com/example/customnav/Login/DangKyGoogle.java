package com.example.customnav.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.customnav.MainActivity;
import com.example.customnav.R;
import com.example.customnav.model.Chapter;
import com.example.customnav.ultilServer.Server;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DangKyGoogle extends AppCompatActivity {
    GoogleSignInClient gsc;
    GoogleSignInOptions gso;
    EditText edtTaiKhoan, edtGmail, edtMatKhau, edtMatKhau2;
    String taikhoan, gmail, matkhau, matkhau2;
    Button btnDangKy;
    String urlDangKy = Server.dangkyGoogle;
    String urlDangNhap = Server.dangnhapGoogle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky_google);
        AnhXa();
        fillGmail();
        autoLogin(urlDangNhap);

    }

    private void autoLogin(String url){
        gmail = edtGmail.getText().toString().trim();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("success")){
                    finish();
                    taikhoan = edtTaiKhoan.getText().toString().trim();
                    Intent intent = new Intent(DangKyGoogle.this, MainActivity.class);
                    intent.putExtra("taikhoan", taikhoan);
                    startActivity(intent);
                } else {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DangKyGoogle.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> Params = new HashMap<>();
                Params.put("gmail", gmail);
                return Params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void DangKy(View view) {
        fill_Information();
        if(taikhoan.isEmpty() || gmail.isEmpty() || matkhau.isEmpty() || matkhau2.isEmpty()){
            Toast.makeText(DangKyGoogle.this, "Vui lòng nhập đầy đủ thông tin!!", Toast.LENGTH_SHORT).show();
        } else if(!matkhau.equals(matkhau2)){
            Toast.makeText(DangKyGoogle.this, "2 mật khẩu không trùng khớp!!", Toast.LENGTH_SHORT).show();
        } else {
            dangKy(urlDangKy);
        }
    }

    private void dangKy(String url){
        taikhoan = edtTaiKhoan.getText().toString().trim();
        gmail = edtGmail.getText().toString().trim();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("success")){
                    Intent intent = new Intent(DangKyGoogle.this, MainActivity.class);
                    intent.putExtra("taikhoan", edtTaiKhoan.getText().toString());
                    intent.putExtra("gmail", edtGmail.getText().toString());
                    startActivity(intent);
                } else {
                    Toast.makeText(DangKyGoogle.this, "Tài khoản đã tồn tại!!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DangKyGoogle.this, error.toString(), Toast.LENGTH_SHORT).show();
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



    private void fillGmail(){
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null){
            String personEmail = account.getEmail();
            String userEmail = account.getDisplayName();
            edtTaiKhoan.setText(userEmail);
            edtGmail.setText(personEmail);
            edtGmail.setEnabled(false);
            edtTaiKhoan.setEnabled(false);
        }
    }

    private void fill_Information(){
        taikhoan = edtTaiKhoan.getText().toString().trim();
        gmail = edtGmail.getText().toString().trim();
        matkhau = edtMatKhau.getText().toString().trim();
        matkhau2 = edtMatKhau2.getText().toString().trim();
    }

    private void AnhXa() {
        edtTaiKhoan = findViewById(R.id.edtTaiKhoan);
        edtGmail = findViewById(R.id.edtGmail);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        edtMatKhau2 = findViewById(R.id.edtMatKhau2);
        btnDangKy = findViewById(R.id.btnDangKy);
    }
}