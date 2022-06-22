package com.example.customnav.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
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
import com.example.customnav.Adapter.StoryAdapter;
import com.example.customnav.R;
import com.example.customnav.model.Story;
import com.example.customnav.ultilServer.Server;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AllStory extends Fragment{
    Context thiscontext;

    //================================All Story================================
    private RecyclerView mRecyclerViewStory;
    private StoryAdapter mStoryAdapter;
    private List<Story> mStory;
    //String url = "http://192.168.8.102/truyenapp/getStory.php/";
    String url = Server.getStory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_allstory,container,false);
        if(getArguments()!=null){
            String username = getArguments().getString("taikhoan");
        }
        thiscontext = container.getContext();

    //================================All Story================================
        AnhXa();
        getData(url);
        mRecyclerViewStory = v.findViewById(R.id.Story);
        setStory(mStory);
    //================================on top================================
        FloatingActionButton ontop;
        ontop = v.findViewById(R.id.on_top);
        ontop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecyclerViewStory.smoothScrollToPosition(0);
            }
        });


        return v;
    }

//================================All story================================
    private void AnhXa() {
        mStory = new ArrayList<>();

    }
    void getData(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(thiscontext);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                mStory.clear();
                for(int i = 0; i < response.length(); i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        mStory.add(new Story(
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
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                mStoryAdapter.notifyDataSetChanged();
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
        mRecyclerViewStory.setLayoutManager(gridLayoutManager);
        mStoryAdapter = new StoryAdapter(AllStory.this,DataList);
        mRecyclerViewStory.setAdapter(mStoryAdapter);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.action_Search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("Searching here");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mStoryAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mStoryAdapter.getFilter().filter(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }
}
