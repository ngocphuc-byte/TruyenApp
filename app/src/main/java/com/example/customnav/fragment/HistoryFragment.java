package com.example.customnav.fragment;

import android.os.Bundle;
import android.util.Log;
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
import com.example.customnav.Adapter.HistoryAdapter;
import com.example.customnav.R;
import com.example.customnav.model.LichSu;
import com.example.customnav.ultilServer.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {
    String taikhoan;private
    RecyclerView recyclerView;
    HistoryAdapter adapter;
    List<LichSu> lichSuList;
    String urlChapter = Server.getChapter, urlGetLichSu = Server.getLichSu;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_history,container,false);
        if(getArguments()!=null){
            taikhoan = getArguments().getString("taikhoan");
        }
        lichSuList = new ArrayList<>();
        recyclerView = v.findViewById(R.id.history_list);
        setChapter();
        getDataChapter(urlGetLichSu);
        return  v;

    }
    void getDataChapter(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                lichSuList.clear();
                for(int i = 0; i < response.length(); i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        String tk = object.getString("Taikhoan");
                        if(tk.equals(taikhoan)){
                            lichSuList.add(new LichSu(
                                    object.getInt("Idlichsu"),
                                    object.getInt("MaChap"),
                                    object.getInt("idTruyen"),
                                    object.getInt("TenChap"),
                                    object.getString("Tentruyen"),
                                    object.getString("Tieude"),
                                    object.getString("NoiDung"),
                                    object.getString("NgayCapNhat"),
                                    object.getString("Taikhoan"),
                                    object.getString("Hinhtruyen")
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
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("loihistory", error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

//    private void handleCheckHistory(String url, String chapter){
//        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                if (!response.equals("success")) {
//
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
//            }
//        }){
//            @Nullable
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map Params = new HashMap();
//                Params.put("taikhoan",taikhoan);
//                Params.put("machap",chapter);
//                return Params;
//            }
//        };
//        requestQueue.add(stringRequest);
//    }

    private void setChapter() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new HistoryAdapter(lichSuList, HistoryFragment.this);
        recyclerView.setAdapter(adapter);
    }

}
