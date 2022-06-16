package com.example.customnav.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.example.customnav.Adapter.StoryAdapter;
import com.example.customnav.Adapter.YeuThichAdapter;
import com.example.customnav.R;
import com.example.customnav.model.Story;
import com.example.customnav.model.YeuThich;
import com.example.customnav.ultilServer.Server;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FavoriteFragment extends Fragment {
    private RecyclerView recyclerView;
    private YeuThichAdapter adapter;
    private List<YeuThich> yeuThichList;
    Context thiscontext;
    String username;
    String urlYeuThich = Server.getYeuThich;
    String urlDeleteYeuThich = Server.deleteYeuThich;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_favorite,container,false);
        thiscontext = container.getContext();
        if(getArguments()!=null){
            username = getArguments().getString("taikhoan");
        }
        AnhXa(v);
        recyclerView = v.findViewById(R.id.recycleView_Favaorite);
        setLayout(yeuThichList);
        getYeuThich(urlYeuThich);

        //================================on top================================
        FloatingActionButton ontop;
        ontop = v.findViewById(R.id.on_top);
        ontop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.smoothScrollToPosition(0);
            }
        });
        return v;
    }
    private void AnhXa(View v) {
        yeuThichList = new ArrayList<>();
    }

    private void getYeuThich(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(thiscontext);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                yeuThichList.clear();
                for(int i = 0; i < response.length(); i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        if(username.equals(object.getString("Taikhoan"))){{
                            yeuThichList.add(new YeuThich(
                                    object.getInt("Idyeuthich"),
                                    object.getInt("Idtruyen"),
                                    object.getInt("Idtheloai"),
                                    object.getInt("Sllike"),
                                    object.getInt("Slyeuthich"),
                                    object.getString("Tentruyen"),
                                    object.getString("Tacgia"),
                                    object.getString("Tinhtrang"),
                                    object.getString("Tomtat"),
                                    object.getString("Taikhoannhomdich"),
                                    object.getString("Hinhtruyen"),
                                    object.getString("Taikhoan"),
                                    object.getString("Tentheloai")
                            ));
                        }}

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(thiscontext, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    private void setLayout(List DataList) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(thiscontext, 2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new YeuThichAdapter(FavoriteFragment.this, DataList);
        recyclerView.setAdapter(adapter);
    }

    public void deleteYeuThich(int id,String taikhoan){
        RequestQueue requestQueue = Volley.newRequestQueue(thiscontext);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlDeleteYeuThich, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("success")){
                    Toast.makeText(getContext(), "Xóa thành công !!!", Toast.LENGTH_SHORT).show();
                    getYeuThich(urlYeuThich);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "ERROR", Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> Params = new HashMap<>();
                Params.put("taikhoan", taikhoan);
                Params.put("idtruyen", String.valueOf(id));
                return Params;
            }
        };
        requestQueue.add(stringRequest);
    }

}


