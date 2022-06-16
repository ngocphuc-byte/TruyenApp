package com.example.customnav.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.customnav.Adapter.ChapterAdapter;
import com.example.customnav.Adapter.PhotoViewPageAdapter;
import com.example.customnav.Adapter.StoryAdapter;
import com.example.customnav.Adapter.StoryhotAdapter;
import com.example.customnav.R;
import com.example.customnav.model.Chapter;
import com.example.customnav.model.Photo;
import com.example.customnav.model.Story;
import com.example.customnav.ultilServer.Server;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends Fragment {

    Context thiscontext;

    //================================Slider Banner================================
    private ViewPager mViewPager;
    private CircleIndicator mCircleIndicator;
    private List<Photo> mListPhoto;
    private Handler mHandler= new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if(mViewPager.getCurrentItem()==mListPhoto.size() - 1){
                mViewPager.setCurrentItem(0);
            }
            else {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
            }
        }
    };

    //================================Story and Hot Story================================
    private RecyclerView mRecyclerViewStory, mRecyclerViewStoryHot;
    private StoryAdapter mStoryAdapter;
    private StoryhotAdapter mStoryhotAdapter;
    private List<Story> mStory;
    private List<Story> mStoryHot;
    TextView allStory;
    String url =Server.getStory;
    String urlHot = Server.getHotStory;

    //================================Chapter================================
    private RecyclerView mRecyclerViewChapter;
    private ChapterAdapter mChapterAdapter;
    private List<Chapter> mChapter;
    TextView allchapter;

    String urlchap =Server.getChapter;



    //================================================================================================================================
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_home,container,false);
        if(getArguments()!=null){
            String username = getArguments().getString("taikhoan");
            Toast.makeText(getContext(), username, Toast.LENGTH_SHORT).show();
        }
        thiscontext = container.getContext();

        //================================Slider Banner================================
        mViewPager = v.findViewById(R.id.view_pages);
        mCircleIndicator = v.findViewById(R.id.circle_indicator);
        mListPhoto = getListPhoto();
        PhotoViewPageAdapter adapter = new PhotoViewPageAdapter(mListPhoto);
        mViewPager.setAdapter(adapter);
        mCircleIndicator.setViewPager(mViewPager);
        SliderBanner();

        //================================hotstory and story================================
        AnhXa();
        mRecyclerViewStory = v.findViewById(R.id.Story);
        mRecyclerViewStoryHot=v.findViewById(R.id.Story_Hot);
        allStory = v.findViewById(R.id.Xemtatca);
        ReplacetoAllStory();
        setStory(mStory);
        getData(url);
        setHotStory(mStoryHot);
        getHotData(urlHot);

        //================================Chapter================================
        mRecyclerViewChapter = v.findViewById(R.id.Chapter);
        allchapter = v.findViewById(R.id.XemtatcaChap);
        ReplacetoAllChapter();
        setChapter(mChapter);
        getDataChapter(urlchap);

        //================================on top================================
        ScrollView scrollView = v.findViewById(R.id.scoll);
        FloatingActionButton ontop;
        ontop = v.findViewById(R.id.on_top);
        ontop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollView.fullScroll(View.FOCUS_UP);
            }
        });
        return v;
    }
//================================================================================================================================

    //================================Slider Banner================================
    private void SliderBanner() {
        mHandler.postDelayed(mRunnable, 3000);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mHandler.removeCallbacks(mRunnable);
                mHandler.postDelayed(mRunnable, 3000);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private List<Photo> getListPhoto() {
        List<Photo> list = new ArrayList<>();
        list.add(new Photo(R.drawable.banner1));
        list.add(new Photo(R.drawable.banner2));
        list.add(new Photo(R.drawable.banner3));
        return list;
    }

    //==========================Story and hot story================================
    private void AnhXa() {
        mStory = new ArrayList<>();
        mStoryHot = new ArrayList<>();
        mChapter = new ArrayList<>();
    }
    private void ReplacetoAllStory() {
        allStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment mAllStory = new AllStory();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.content_frame,mAllStory);
                transaction.addToBackStack(String.valueOf(mRecyclerViewStory));
                transaction.commit();
            }

        });
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
    private void getHotData(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(thiscontext);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                mStoryHot.clear();
                for(int i = 0; i < response.length(); i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        mStoryHot.add(new Story(
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
                mStoryhotAdapter.notifyDataSetChanged();
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
        GridLayoutManager gridLayoutManager = new GridLayoutManager(thiscontext, 1);
        gridLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mRecyclerViewStory.setLayoutManager(gridLayoutManager);
        mStoryAdapter = new StoryAdapter(HomeFragment.this,DataList);
        mRecyclerViewStory.setAdapter(mStoryAdapter);
    }
    private void setHotStory(List DataList) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(thiscontext, 1);
        gridLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mRecyclerViewStoryHot.setLayoutManager(gridLayoutManager);
        mStoryhotAdapter = new StoryhotAdapter(HomeFragment.this,DataList);
        mRecyclerViewStoryHot.setAdapter(mStoryhotAdapter);
    }

    //===================================chapter==========================================
    void getDataChapter(String url){
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

    private void setChapter(List DataList) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(thiscontext, 1);
        gridLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mRecyclerViewChapter.setLayoutManager(gridLayoutManager);
        mChapterAdapter = new ChapterAdapter(HomeFragment.this,DataList);
        mRecyclerViewChapter.setAdapter(mChapterAdapter);
    }
    private void ReplacetoAllChapter() {
        allchapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment mAllChapter = new AllChapter();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.content_frame,mAllChapter);
                transaction.addToBackStack(String.valueOf(mRecyclerViewChapter));
                transaction.commit();
            }

        });
    }

}
