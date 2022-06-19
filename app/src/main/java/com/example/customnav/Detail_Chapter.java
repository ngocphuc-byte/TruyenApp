package com.example.customnav;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
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
import com.example.customnav.Adapter.DetailChapterAdapter;
import com.example.customnav.model.ChapterDetail;
import com.example.customnav.ultilServer.Server;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Detail_Chapter extends AppCompatActivity {
    Toolbar toolbar;
    SwitchCompat switchCompat;
    int tenchap,idtruyen;
    String  tieude, ngaycapnhat, noidung, machap, taikhoan, nd;
    List<ChapterDetail> chapterList;
    DetailChapterAdapter adapter;
    RecyclerView recyclerView;
    int size;
    String url = Server.getDetailChapter,
            urInsertLichSu = Server.insertLichSu,
            urlCheckLichSu = Server.checkLichSu,
            urlUpdateLichSu = Server.updateLichSu;
    TextToSpeech textToSpeech;
    Button volume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_chapter);
        AnhXa();
        getChapter();
        setLayout();
        getData(url);
        Back();
        darkmode();
        rundarkmode();
        ScrollView scrollView = findViewById(R.id.scoll_chap);
        FloatingActionButton ontop;
        ontop = findViewById(R.id.on_top);
        ontop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollView.fullScroll(View.FOCUS_UP);
            }
        });
        volume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        String st = nd.substring(0,1000);
                        if(status == TextToSpeech.SUCCESS){
                            textToSpeech.setLanguage(Locale.ENGLISH);
                            textToSpeech.speak(st, TextToSpeech.QUEUE_FLUSH, null);
                        } else {
                            Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(textToSpeech != null){
            textToSpeech.shutdown();
        }
    }

    void getData(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                chapterList.clear();
                for(int i = 0; i < response.length(); i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        int idTruyen = object.getInt("idTruyen");
                        int tenChap = object.getInt("TenChap");
                        if((idtruyen == idTruyen) && (tenChap == tenchap)){
                            nd = object.getString("NoiDung");
                            chapterList.add(new ChapterDetail(
                                    object.getString("MaChap"),
                                    object.getInt("idTruyen"),
                                    object.getInt("TenChap"),
                                    object.getString("Tieude"),
                                    object.getString("NoiDung"),
                                    object.getString("NgayCapNhat")
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

    private void setLayout() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new DetailChapterAdapter(this, chapterList);
        recyclerView.setAdapter(adapter);

    }

    private void getChapter() {
        Intent intent = getIntent();
        tenchap = intent.getIntExtra("tenchap",0);
        tieude = intent.getStringExtra("tieude");
        ngaycapnhat = intent.getStringExtra("ngaycapnhat");
        machap = intent.getStringExtra("machap");
        noidung = intent.getStringExtra("noidung");
        idtruyen = intent.getIntExtra("idtruyen",0);
        size = intent.getIntExtra("size",0);
        taikhoan = intent.getStringExtra("taikhoan");
    }

    private void AnhXa() {
        volume = findViewById(R.id.volume);
        switchCompat = findViewById(R.id.bt_switch);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.recycleViewChapter);
        chapterList = new ArrayList<>();
        recyclerView.setNestedScrollingEnabled(true);
    }

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
        }
        else {
            setTheme(R.style.Theme_Light);
        }
    }

    public void Next(View view) {
//        if(tenchap < size){
        finish();
        Intent intent = new Intent(this, Detail_Chapter.class);
        tenchap+=1;
        intent.putExtra("tenchap", tenchap);
        intent.putExtra("tieude", tieude);
        intent.putExtra("ngaycapnhat",ngaycapnhat);
        intent.putExtra("machap",machap);
        intent.putExtra("noidung",noidung);
        intent.putExtra("idtruyen", idtruyen);
        intent.putExtra("taikhoan", taikhoan);
        intent.putExtra("size", size);

        startActivity(intent);
//        } else {
//            Toast.makeText(this, "Chưa có chap mới", Toast.LENGTH_SHORT).show();
//        }

    }

    public void Prev(View view) {
        if(tenchap > 1){
            finish();
            Intent intent = new Intent(this, Detail_Chapter.class);
            tenchap-=1;
            intent.putExtra("tenchap", tenchap);
            intent.putExtra("tieude", tieude);
            intent.putExtra("ngaycapnhat",ngaycapnhat);
            intent.putExtra("machap",machap);
            intent.putExtra("noidung",noidung);
            intent.putExtra("idtruyen", idtruyen);
            intent.putExtra("taikhoan", taikhoan);
            intent.putExtra("size", size);
            startActivity(intent);
        }
    }

    public void Handle_Add_Chapter(View view) {
        handleCheckHistory(urlCheckLichSu);
    }

    private void InsertChapter(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("success")){
                    finish();
                    Toast.makeText(getApplication(), "Đã thêm vào lịch sử đọc", Toast.LENGTH_SHORT).show();
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
                Params.put("tenchap", String.valueOf(tenchap));
                Params.put("idtruyen", String.valueOf(idtruyen));
                Params.put("taikhoan", taikhoan);
                return Params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void handleCheckHistory(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("success")){
                    handleUpdateHistory(urlUpdateLichSu);
                } else {
                    InsertChapter(urInsertLichSu);
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
                Params.put("taikhoan", taikhoan);
                return Params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void handleUpdateHistory(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("success")){
                    Toast.makeText(getApplication(), "Đã thêm vào lịch sử đọc", Toast.LENGTH_SHORT).show();
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
                Params.put("tenchap", String.valueOf(tenchap));
                Params.put("idtruyen", String.valueOf(idtruyen));
                Params.put("taikhoan", taikhoan);
                return Params;
            }
        };
        requestQueue.add(stringRequest);
    }


//
//    @Override
//    public boolean onTouch(View view, MotionEvent motionEvent) {
//        return true;
//    }
////zoom
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if(event.getPointerCount() == 2){
//            int action = event.getAction();
//            int mainaction = action & MotionEvent.ACTION_MASK;
//            if(mainaction == MotionEvent.ACTION_HOVER_ENTER){
//                baseDist = getDistance(event);
//                baseRadio = ratio;
//            }
//            else {
//                float scale =(getDistance(event)-baseDist)/move;
//                float factor = (float) Math.pow(2,scale);
//                ratio = Math.min(1024.0f,Math.max(0.1f,baseRadio * factor));
//                txtnoidung.setTextSize(ratio+15);
//            }
//        }
//        return true;
//    }
//
//    private int getDistance(MotionEvent event) {
//        int dx = (int) (event.getX(0)-event.getX(1));
//        int dy = (int) (event.getY(0)-event.getY(1));
//    return  (int) (Math.sqrt(dx*dx+dy*dy));
//    }
}

