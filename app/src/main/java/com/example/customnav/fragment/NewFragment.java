package com.example.customnav.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
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
import com.example.customnav.Adapter.ChapterAdapter;
import com.example.customnav.Adapter.NewsAdapter;
import com.example.customnav.R;
import com.example.customnav.model.Chapter;
import com.example.customnav.model.News;
import com.example.customnav.ultilServer.Server;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewFragment extends Fragment {
    private RecyclerView mRecyclerViewNew;
    private NewsAdapter mNewsAdapter;
    private List<News> mNews;
    String urlnew = Server.getNew;
    Context thiscontext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_new,container,false);

        thiscontext = container.getContext();

        AnhXa();
        getDataNew(urlnew);
        mRecyclerViewNew = v.findViewById(R.id.itemtintuc);
        setNew(mNews);


        //================================on top================================
        FloatingActionButton ontop;
        ontop = v.findViewById(R.id.on_top);
        ontop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecyclerViewNew.smoothScrollToPosition(0);
            }
        });
        return v;
    }


    private void AnhXa() {
        mNews = new ArrayList<>();
    }
    void getDataNew(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(thiscontext);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                mNews.clear();
                for(int i = 0; i < response.length(); i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        mNews.add(new News(
                                object.getInt("idNews"),
                                object.getString("Tieude"),
                                object.getString("Noidung"),
                                object.getString("url"),
                                object.getString("Image")
                        ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                mNewsAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(thiscontext, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void setNew(List DataList) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(thiscontext, 1);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerViewNew.setLayoutManager(gridLayoutManager);
        mNewsAdapter = new NewsAdapter(NewFragment.this,DataList);
        mRecyclerViewNew.setAdapter(mNewsAdapter);
    }
}
