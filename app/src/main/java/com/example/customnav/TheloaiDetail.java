package com.example.customnav;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.customnav.Adapter.DetailStoryTheloaiAdapter;
import com.example.customnav.model.Story;
import com.example.customnav.ultilServer.Server;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TheloaiDetail extends AppCompatActivity {

    Toolbar toolbar;
    SwitchCompat switchCompat;
    TextView txtTen;
    String ten;
    RecyclerView recyclerView;
    DetailStoryTheloaiAdapter adapter;
    List<Story> Storylist;
    String urlStory = Server.getStory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theloai_detail);
        AnhXa();
        getId();
        Back();
        darkmode();
        rundarkmode();
        setLayout();
        getData(urlStory);
        FloatingActionButton ontop;
        ontop = findViewById(R.id.on_top);
        ontop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.smoothScrollToPosition(0);
            }
        });
    }


    private void getId() {
        Intent intent = getIntent();
        ten = intent.getStringExtra("ten");
        txtTen.setText(ten);
    }
    //
    private void AnhXa() {
        switchCompat = findViewById(R.id.bt_switch);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtTen = findViewById(R.id.TenTheLoai_Detail);
        recyclerView = findViewById(R.id.Truyen_TheLoai);
        Storylist = new ArrayList<>();

    }

    void getData(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Storylist.clear();
                Intent intent = getIntent();
                int idIntent = Integer.parseInt(intent.getStringExtra("idtheloai"));
                for(int i = 0; i < response.length(); i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        int id = object.getInt("Idtheloai");
                        if(idIntent == id){
                            Storylist.add(new Story(
                                    object.getInt("Idtruyen"),
                                    object.getInt("Idtheloai"),
                                    object.getString("Tacgia"),
                                    object.getString("Tentruyen"),
                                    object.getString("Tomtat"),
                                    object.getString("Tinhtrang"),
                                    object.getString("Hinhtruyen"),
                                    object.getInt("Sllike"),
                                    object.getInt("Slyeuthich"),
                                    object.getString("Taikhoannhomdich"),
                                    object.getString("Tentheloai")
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
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new DetailStoryTheloaiAdapter(TheloaiDetail.this, Storylist);
        recyclerView.setAdapter(adapter);

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
        }
        else {
            setTheme(R.style.Theme_Light);
        }
    }
}