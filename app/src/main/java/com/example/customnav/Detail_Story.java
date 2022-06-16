package com.example.customnav;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.customnav.Adapter.BinhLuanAdapter;
import com.example.customnav.Adapter.DetailChapterStoryAdapter;
import com.example.customnav.model.BinhLuan;
import com.example.customnav.model.Chapter;
import com.example.customnav.ultilServer.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vn.momo.momo_partner.AppMoMoLib;

public class Detail_Story extends AppCompatActivity {
    Toolbar toolbar;
    SwitchCompat switchCompat;
    ImageView imgDetails;                
    TextView txtTen, txtMota, txtTinhtrang, txtTheloai,txttacgia, txtNhomDich;
    String ten, mota, tinhtrang, img, theloai,tacgia, taikhoan, taikhoannhomdich;
    int idtruyen, idtheloai, sllike, slyeuthich;
    RecyclerView recyclerView, binhluan_recycleView;
    DetailChapterStoryAdapter adapter;
    List<Chapter> chapterList;
    List<BinhLuan> binhLuanList;
    BinhLuanAdapter binhLuanAdapter;
    String urlDetail = Server.getChapter;
    EditText edt_chatbinhluan;
    Button btnLike, btnUnlike;
    Spinner spinner;

    String urlCheckYeuThich = Server.checkYeuThich;
    String urlBinhluan = Server.getBinhLuan;
    String urlInsertBL = Server.insertBinhLuan;
    String urlYeuThich = Server.insertYeuThich;
    String urlCheckLike = Server.checkLike;
    String urlInsertLike = Server.insertLike;
    String urlUpdateLike = Server.updateLike;
    String urlDeleteLike = Server.deleteLike;
    String urlInsertMomo = Server.insertMomo;

    private String amount;
    private String fee = "0";
    int environment = 0;//developer default
    private String merchantName = "h2pteam";
    private String merchantCode = "MOMOPTSF20220609";
    private String merchantNameLabel = "H2P";
    private String description = "Donate H2P";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_story);
        AppMoMoLib.getInstance().setEnvironment(AppMoMoLib.ENVIRONMENT.DEVELOPMENT); // AppMoMoLib.ENVIRONMENT.PRODUCTION
        AnhXa();
        Donate();
        getId();
        setDetails();
        Back();
        darkmode();
        rundarkmode();
        setLayout();
        setLayoutBinhLuan();
        getData(urlDetail);
        getBinhLuan(urlBinhluan);
        likeButton(urlCheckLike);
    }

    private void setDetails(){
        txtTen.setText(ten);
        txtMota.setText(mota);
        txtTinhtrang.setText(tinhtrang);
        txtTheloai.setText(theloai);
        txttacgia.setText(tacgia);
        txtNhomDich.setText(taikhoannhomdich);
        Glide.with(Detail_Story.this).load(img).into(imgDetails);
    }

    private void getId() {
        Intent intent = getIntent();
        ten = intent.getStringExtra("ten");
        mota = intent.getStringExtra("mota");
        tinhtrang = intent.getStringExtra("trangthai");
        theloai = intent.getStringExtra("theloai");
        img = intent.getStringExtra("hinh");
        tacgia=intent.getStringExtra("tacgia");
        taikhoan = intent.getStringExtra("taikhoan");
        idtruyen = intent.getIntExtra("id",0);
        idtheloai = intent.getIntExtra("idtheloai",0);
        sllike = intent.getIntExtra("sllike",0);
        slyeuthich = intent.getIntExtra("slyeuthich",0);
        taikhoannhomdich = intent.getStringExtra("nhomdich");
    }
//
    private void AnhXa() {
        switchCompat = findViewById(R.id.bt_switch);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imgDetails = findViewById(R.id.img_details);
        txtTen = findViewById(R.id.txt_tentruyen);
        txtMota = findViewById(R.id.txt_mota);
        txtTinhtrang = findViewById(R.id.txt_TinhTrang);
        txtTheloai = findViewById(R.id.txt_theloai);
        txttacgia = findViewById(R.id.txt_tacgia);
        txtNhomDich = findViewById(R.id.txt_Nhomdich);
        recyclerView = findViewById(R.id.Details_Chaplist);
        binhluan_recycleView = findViewById(R.id.binhluan_recycleView);
        binhLuanList = new ArrayList<>();
        chapterList = new ArrayList<>();
        edt_chatbinhluan = findViewById(R.id.edt_chatbinhluan);
        btnLike = findViewById(R.id.btn_detail_like);
        btnUnlike = findViewById(R.id.btn_detail_unlike);
        spinner = findViewById(R.id.donate);
    }

    void getData(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                chapterList.clear();
                Intent intent = getIntent();
                int idIntent = intent.getIntExtra("id",0);
                for(int i = 0; i < response.length(); i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        int id = object.getInt("idTruyen");
                        if(idIntent == id){
                            chapterList.add(new Chapter(
                                    object.getString("MaChap"),
                                    object.getInt("idTruyen"),
                                    object.getInt("TenChap"),
                                    object.getString("Tieude"),
                                    object.getString("NoiDung"),
                                    object.getString("NgayCapNhat"),
                                    object.getString("tentruyen"),
                                    object.getString("hinhtruyen")
                            ));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);

    }

    void getBinhLuan(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                binhLuanList.clear();
                Intent intent = getIntent();
                int idIntent = intent.getIntExtra("id",0);
                for(int i = 0; i < response.length(); i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        int id = object.getInt("Idtruyen");
                        if(idIntent == id){
                            binhLuanList.add(new BinhLuan(
                                    object.getInt("Idbinhluan"),
                                    object.getInt("Idtruyen"),
                                    object.getString("Taikhoan"),
                                    object.getString("Noidung")
                            ));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                binhLuanAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);

    }

    private void setLayout() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new DetailChapterStoryAdapter(Detail_Story.this, chapterList);
        recyclerView.setAdapter(adapter);

    }

    private void setLayoutBinhLuan() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binhluan_recycleView.setLayoutManager(gridLayoutManager);
        binhLuanAdapter= new BinhLuanAdapter(Detail_Story.this, binhLuanList);
        binhluan_recycleView.setAdapter(binhLuanAdapter);

    }
//
    private void Back(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public void darkmode(){
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });
    }

    public void rundarkmode(){
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.Theme_Dark);
            edt_chatbinhluan.setTextColor(ContextCompat.getColor(this,
                    R.color.white));
        }
        else {
            setTheme(R.style.Theme_Light);
            edt_chatbinhluan.setTextColor(ContextCompat.getColor(this,
                    R.color.black));
        }
    }

    public void send(View view) {
        String bl = edt_chatbinhluan.getText().toString().trim();
        if(bl.isEmpty()){
            Toast.makeText(Detail_Story.this, "Vui lòng nhập thông tin bình luận!!!", Toast.LENGTH_SHORT).show();
        } else {
            insertBinhLuan(urlInsertBL);
        }
    }

    private void insertBinhLuan(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.equals("success")){
                    Toast.makeText(Detail_Story.this, "Lỗi dữ liệu", Toast.LENGTH_SHORT).show();
                }
                edt_chatbinhluan.setText("");
                getBinhLuan(urlBinhluan);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Detail_Story.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map Params = new HashMap();
                Params.put("idtruyen", String.valueOf(idtruyen));
                Params.put("taikhoan", taikhoan);
                Params.put("noidung", edt_chatbinhluan.getText().toString().trim());
                return Params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void yeuthich(View view) {
        checkYeuThich(urlCheckYeuThich);
    }

    private void YeuThich(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("success")){
                    finish();
                } else {
                    Toast.makeText(Detail_Story.this, "Lỗi dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Detail_Story.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map Params = new HashMap();
                Params.put("idtruyen", String.valueOf(idtruyen));
                Params.put("taikhoan", taikhoan);
                return Params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void checkYeuThich(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("success")){
                    Toast.makeText(Detail_Story.this, "Truyện đã có trong mục yêu thích !!!", Toast.LENGTH_SHORT).show();
                } else {
                    YeuThich(urlYeuThich);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Detail_Story.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map Params = new HashMap();
                Params.put("idtruyen", String.valueOf(idtruyen));
                Params.put("taikhoan", taikhoan);
                return Params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void like(View view) {
        sllike+=1;
        insert_like(urlInsertLike);
        update_like(urlUpdateLike);
        Toast.makeText(getApplication(), "LIKE", Toast.LENGTH_SHORT).show();
        btnLike.setEnabled(false);
        btnLike.setVisibility(View.INVISIBLE);
        btnUnlike.setEnabled(true);
        btnUnlike.setVisibility(View.VISIBLE);
    }

    public void unLike(View view) {
        sllike-=1;
        delete_like(urlDeleteLike);
        update_like(urlUpdateLike);

        btnLike.setEnabled(true);
        btnLike.setVisibility(View.VISIBLE);
        btnUnlike.setEnabled(false);
        btnUnlike.setVisibility(View.INVISIBLE);
    }

    private void likeButton(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("success")){
                    btnLike.setEnabled(false);
                    btnLike.setVisibility(View.INVISIBLE);
                    btnUnlike.setEnabled(true);
                    btnUnlike.setVisibility(View.VISIBLE);
                } else {


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplication(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map Params = new HashMap();
                Params.put("idtruyen", String.valueOf(idtruyen));
                Params.put("taikhoan",taikhoan);
                return Params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void insert_like(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("success")){

                } else {
                    Toast.makeText(getApplication(), "Lỗi dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplication(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map Params = new HashMap();
                Params.put("idtruyen",String.valueOf(idtruyen));
                Params.put("taikhoan", taikhoan);
                return Params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void update_like(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("success")){

                }else {
                    Toast.makeText(getApplication(), "Lỗi dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplication(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map Params = new HashMap();
                Params.put("idtruyen", String.valueOf(idtruyen));
                Params.put("sllike",String.valueOf(sllike));
                return Params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void delete_like(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("success")){
                    Toast.makeText(getApplication(), "Hủy like", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplication(), "Lỗi dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplication(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map Params = new HashMap();
                Params.put("idtruyen",String.valueOf(idtruyen));
                Params.put("taikhoan", taikhoan);
                return Params;
            }
        };
        requestQueue.add(stringRequest);
    }



    //Get token through MoMo app
    private void requestPayment(String id) {
        amount = spinner.getSelectedItem().toString();
        AppMoMoLib.getInstance().setAction(AppMoMoLib.ACTION.PAYMENT);
        AppMoMoLib.getInstance().setActionType(AppMoMoLib.ACTION_TYPE.GET_TOKEN);


        Map<String, Object> eventValue = new HashMap<>();
        //client Required
        eventValue.put("merchantname", merchantName); //Tên đối tác. được đăng ký tại https://business.momo.vn. VD: Google, Apple, Tiki , CGV Cinemas
        eventValue.put("merchantcode", merchantCode); //Mã đối tác, được cung cấp bởi MoMo tại https://business.momo.vn
        eventValue.put("amount", amount); //Kiểu integer
        eventValue.put("orderId", id); //uniqueue id cho Bill order, giá trị duy nhất cho mỗi đơn hàng
        eventValue.put("orderLabel", id); //gán nhãn

        //client Optional - bill info
        eventValue.put("merchantnamelabel", "Dịch vụ");//gán nhãn
        eventValue.put("fee", "0"); //Kiểu integer
        eventValue.put("description", description); //mô tả đơn hàng - short description

        //client extra data
        eventValue.put("requestId",  merchantCode+"merchant_billId_"+System.currentTimeMillis());
        eventValue.put("partnerCode", merchantCode);
        //Example extra data
        JSONObject objExtraData = new JSONObject();
        try {
            objExtraData.put("site_code", "008");
            objExtraData.put("site_name", "CGV Cresent Mall");
            objExtraData.put("screen_code", 0);
            objExtraData.put("screen_name", "Special");
            objExtraData.put("movie_name", "Kẻ Trộm Mặt Trăng 3");
            objExtraData.put("movie_format", "2D");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        eventValue.put("extraData", objExtraData.toString());

        eventValue.put("extra", "");
        AppMoMoLib.getInstance().requestMoMoCallBack(this, eventValue);


    }

    //Get token callback from MoMo app an submit to server side
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == AppMoMoLib.getInstance().REQUEST_CODE_MOMO && resultCode == -1) {
            if(data != null) {
                if(data.getIntExtra("status", -1) == 0) {
                    //TOKEN IS AVAILABLE
                    Log.d("thanhcong", data.getStringExtra("message"));
                    String token = data.getStringExtra("data"); //Token response
                    String phoneNumber = data.getStringExtra("phonenumber");
                    String env = data.getStringExtra("env");
                    if(env == null){
                        env = "app";
                    }

                    if(token != null && !token.equals("")) {
                        // TODO: send phoneNumber & token to your server side to process payment with MoMo server
                        // IF Momo topup success, continue to process your order
                        handleInsertDonate(token);

                    } else {
                        Log.d("thanhcong", "khong thanh cong");
                        Toast.makeText(this, "KHONG THANH CONG", Toast.LENGTH_SHORT).show();

                    }
                } else if(data.getIntExtra("status", -1) == 1) {
                    //TOKEN FAIL
                    String message = data.getStringExtra("message") != null?data.getStringExtra("message"):"Thất bại";
                    Log.d("thanhcong", "khong thanh cong");
                    Toast.makeText(this, "KHONG THANH CONG", Toast.LENGTH_SHORT).show();


                } else if(data.getIntExtra("status", -1) == 2) {
                    //TOKEN FAIL
                    Log.d("thanhcong", "khong thanh cong");
                    Toast.makeText(this, "KHONG THANH CONG", Toast.LENGTH_SHORT).show();


                } else {
                    //TOKEN FAIL
                    Log.d("thanhcong", "khong thanh cong");
                    Toast.makeText(this, "KHONG THANH CONG", Toast.LENGTH_SHORT).show();


                }
            } else {
                Log.d("thanhcong", "khong thanh cong");
                Toast.makeText(this, "KHONG THANH CONG", Toast.LENGTH_SHORT).show();


            }
        } else {
            Log.d("thanhcong", "khong thanh cong");
            Toast.makeText(this, "KHONG THANH CONG", Toast.LENGTH_SHORT).show();


        }
    }

    public void donate(View view) {
       requestPayment(taikhoan);

    }

    private void handleInsertDonate(String token){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlInsertMomo, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("success")){
                    Toast.makeText(Detail_Story.this, "Donate thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Detail_Story.this, "Lỗi dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Detail_Story.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map Params = new HashMap();
                Params.put("price", amount);
                Params.put("taikhoan", taikhoan);
                Params.put("nhomdich",taikhoannhomdich);
                Params.put("momo",token);
                return Params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void Donate(){

        List<String> listPrices = new ArrayList<>();
        listPrices.add("10000");
        listPrices.add("30000");
        listPrices.add("50000");
        listPrices.add("100000");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listPrices);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        amount = spinner.getSelectedItem().toString();
    }

}

