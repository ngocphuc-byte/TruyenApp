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
import com.example.customnav.R;
import com.example.customnav.ultilServer.Server;

import java.util.HashMap;
import java.util.Map;

public class ChangePassword extends AppCompatActivity {
    EditText edtPassword1, edtPassword2;
    Button btnDongY;
    String password1, password2, email;
    String urlUpdate = Server.updatePassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        AnhXa();

    }

    private void getEmail(){

    }

    private void UpdatePassWord(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("success")){
                    finish();
                    Toast.makeText(ChangePassword.this, "Đã thay đổi mật khẩu", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ChangePassword.this, "Lỗi dữ liệu.", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ChangePassword.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> Params = new HashMap<>();
                Params.put("gmail", email);
                Params.put("matkhau", password1);
                return Params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void DK(){
        password1 = edtPassword1.getText().toString().trim();
        password2 = edtPassword2.getText().toString().trim();
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        if(password1.isEmpty() || password2.isEmpty()){
            Toast.makeText(ChangePassword.this, "Vui lòng nhập đầy đủ mật khẩu mới", Toast.LENGTH_SHORT).show();
        } else if(!password1.equals(password2)){
            Toast.makeText(ChangePassword.this, "2 mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
            edtPassword1.setText("");
            edtPassword2.setText("");
        } else {
            UpdatePassWord(urlUpdate);
        }
    }

    public void DongY(View view) {
       DK();
    }

    private void AnhXa() {
        edtPassword1 = findViewById(R.id.edtPassword);
        edtPassword2 = findViewById(R.id.edtPassword2);
        btnDongY = findViewById(R.id.btnDongY);
        password1 = edtPassword1.getText().toString().trim();
        password2 = edtPassword2.getText().toString().trim();
    }
}