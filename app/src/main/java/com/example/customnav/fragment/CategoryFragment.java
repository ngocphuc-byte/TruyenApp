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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.customnav.Adapter.NewsAdapter;
import com.example.customnav.Adapter.TheloaiAdapter;
import com.example.customnav.R;
import com.example.customnav.model.News;
import com.example.customnav.model.Theloai;
import com.example.customnav.ultilServer.Server;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {
    private RecyclerView mRecyclerViewTheloai;
    private TheloaiAdapter mTheloaiAdapter;
    private List<Theloai> mtheloai;
    String urltheloai = Server.getTheloai;
    Context thiscontext;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_category,container,false);
        if(getArguments()!=null){
            String username = getArguments().getString("taikhoan");
            Toast.makeText(getContext(), username.toString(), Toast.LENGTH_SHORT).show();
        }
        thiscontext = container.getContext();

        AnhXa();
        getDataTheloai(urltheloai);
        mRecyclerViewTheloai = v.findViewById(R.id.theloai_list);
        settheloai(mtheloai);

        return v;
    }
    private void AnhXa() {
        mtheloai = new ArrayList<>();
    }
    void getDataTheloai(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(thiscontext);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                mtheloai.clear();
                for(int i = 0; i < response.length(); i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        mtheloai.add(new Theloai(
                                object.getInt("idtheloai"),
                                object.getString("tentheloai")
                        ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                mTheloaiAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(thiscontext, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void settheloai(List DataList) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(thiscontext, 2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerViewTheloai.setLayoutManager(gridLayoutManager);
        mTheloaiAdapter = new TheloaiAdapter(CategoryFragment.this,DataList);
        mRecyclerViewTheloai.setAdapter(mTheloaiAdapter);
    }
}
