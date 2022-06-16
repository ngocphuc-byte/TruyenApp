package com.example.customnav.Adapter;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.customnav.Interface.ItemClickListener;
import com.example.customnav.R;
import com.example.customnav.model.News;
import com.example.customnav.model.Story;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    Fragment context;
    private List<News> newlist;

    public NewsAdapter(Fragment context, List<News> newlist) {
        this.context = context;
        this.newlist = newlist;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tintuc, parent, false);
        return new NewsAdapter.NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.NewsViewHolder holder, int position) {
        String tieude = newlist.get(position).getTieude();
        String noidung = newlist.get(position).getNoidung();
        String url = newlist.get(position).getUrl();
        String image = newlist.get(position).getImage();
        holder.txtTieude.setText(tieude);
        holder.txtNoidung.setText(noidung);
        holder.btnurl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openlink = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                context.startActivity(openlink);
            }
        });
        Glide.with(context).load(image).into(holder.imgTintuc);
    }

    @Override
    public int getItemCount() {
        return newlist.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        ImageView imgTintuc;
        TextView txtTieude, txtNoidung;
        Button btnurl;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            imgTintuc = itemView.findViewById(R.id.img_tintuc);
            txtNoidung = itemView.findViewById(R.id.noidung_tintuc);
            txtTieude = itemView.findViewById(R.id.tieude_tintuc);
            btnurl=itemView.findViewById(R.id.duongdanmua);

        }
    }
}
