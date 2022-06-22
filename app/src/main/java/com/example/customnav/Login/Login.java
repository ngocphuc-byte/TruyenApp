package com.example.customnav.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageView imgGg;
    EditText edtTaiKhoan, edtMatKhau;
    TextView txt;
    Button btnDangNhap;
    String taikhoan, matkhau;
    String url = Server.dangnhapKH;
    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AnhXa();
        loginFB();

//        insertAccountFB();
    }

    private void DieuKien(){
        taikhoan = edtTaiKhoan.getText().toString().trim() ;
        matkhau = edtMatKhau.getText().toString().trim();
        if(taikhoan.isEmpty() || matkhau.isEmpty()){
            Toast.makeText(Login.this, "Vui lòng nhập đầy đủ thông tin!!!", Toast.LENGTH_SHORT).show();
        } else {
            dangNhapDatabase(url);
        }
    }


    private void dangNhapDatabase(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("success")){
                    finish();
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    intent.putExtra("taikhoan", taikhoan);
                    startActivity(intent);
                } else {
                    Toast.makeText(Login.this, "Tài khoản hoặc mật khẩu không đúng, vui lòng nhập lại!!!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> Params = new HashMap<>();
                Params.put("taikhoan", taikhoan);
                Params.put("matkhau", matkhau);
                return Params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void DangNhap(View view) {
        DieuKien();
    }
    public void Google(View view){
        signIn();
    }

    private void signIn(){
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 1000);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            callbackManager.onActivityResult(requestCode, resultCode, data);
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                navigateToSecondActivity();
            } catch (ApiException e) {
                txt.setText(e.toString());
                Toast.makeText(Login.this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        }



//            super.onActivityResult(requestCode, resultCode, data);

    }

    private void navigateToSecondActivity() {
        finish();
        Intent intent = new Intent(Login.this, DangKyGoogle.class);
        startActivity(intent);
    }

    private void AnhXa() {
        edtTaiKhoan = findViewById(R.id.edtTaiKhoan);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        txt = findViewById(R.id.forgotPassword);
        imgGg = findViewById(R.id.google);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

    }


    public void Register(View view) {
        startActivity(new Intent(Login.this, DangKy.class));
    }

    public void ForgotPassword(View view) {
        startActivity(new Intent(Login.this, ForgotPassword.class));
    }

    public void facebook(View view) {

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
    }


    private void loginFB(){
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        startActivity(new Intent(getApplication(), MainActivity.class));
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }});

    }

    private void insertAccountFB(){
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        // Application code
                        try {
                            String name =  object.getString("name");
                            Toast.makeText(getApplication(), name, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link");
        request.setParameters(parameters);
        request.executeAsync();
    }



}