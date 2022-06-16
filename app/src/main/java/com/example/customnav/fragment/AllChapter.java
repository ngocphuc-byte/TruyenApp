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
import com.example.customnav.Adapter.ChapterAdapter;
import com.example.customnav.Adapter.StoryAdapter;
import com.example.customnav.R;
import com.example.customnav.model.Chapter;
import com.example.customnav.model.Story;
import com.example.customnav.ultilServer.Server;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AllChapter extends Fragment {
    Context thiscontext;

    //================================All Story================================
    private RecyclerView mRecyclerViewChapter;
    private ChapterAdapter mChapterAdapter;
    private List<Chapter> mChapter;
    String urlchap = Server.getChapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_allchapter,container,false);
        if(getArguments()!=null){
            String username = getArguments().getString("taikhoan");
            Toast.makeText(getContext(), username.toString(), Toast.LENGTH_SHORT).show();
        }
        thiscontext = container.getContext();

        //================================All Story================================
        AnhXa();
        getData(urlchap);
        mRecyclerViewChapter = v.findViewById(R.id.Chapter);
        setStory(mChapter);

        //================================on top================================
        FloatingActionButton ontop;
        ontop = v.findViewById(R.id.on_top);
        ontop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecyclerViewChapter.smoothScrollToPosition(0);
            }
        });


        return v;
    }

    //================================All story================================
    private void AnhXa() {
        mChapter = new ArrayList<>();

    }
    void getData(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(thiscontext);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                mChapter.clear();
                for(int i = 0; i < response.length(); i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        mChapter.add(new Chapter(
                                object.getString("MaChap"),
                                object.getInt("idTruyen"),
                                object.getInt("TenChap"),
                                object.getString("Tieude"),
                                object.getString("NoiDung"),
                                object.getString("NgayCapNhat"),
                                object.getString("tentruyen"),
                                object.getString("hinhtruyen")
                        ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                mChapterAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(thiscontext, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);

    }
    private void setStory(List DataList) {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(thiscontext, 2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerViewChapter.setLayoutManager(gridLayoutManager);
        mChapterAdapter = new ChapterAdapter(AllChapter.this,DataList);
        mRecyclerViewChapter.setAdapter(mChapterAdapter);

    }
}
