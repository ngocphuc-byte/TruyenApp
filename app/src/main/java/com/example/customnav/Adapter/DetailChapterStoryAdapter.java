package com.example.customnav.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customnav.Detail_Chapter;
import com.example.customnav.Detail_Story;
import com.example.customnav.Interface.ItemClickListener;
import com.example.customnav.R;
import com.example.customnav.model.Chapter;

import java.util.List;

public class DetailChapterStoryAdapter extends RecyclerView.Adapter<DetailChapterStoryAdapter.ViewHolder>{
    Detail_Story context;
    List<Chapter> chapterList;

    public DetailChapterStoryAdapter(Detail_Story context, List<Chapter> chapterList) {
        this.context = context;
        this.chapterList = chapterList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chapter_details,parent,false);
        return new DetailChapterStoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int tenchap = chapterList.get(position).getTenchap();
        int idTruyen = chapterList.get(position).getIdtruyen();
        String tieude = chapterList.get(position).getTieude();
        String date = chapterList.get(position).getNgaycapnhat();
        String idChap = chapterList.get(position).getMachap();
        String noidung = chapterList.get(position).getNoidung();
            holder.txtTenChap.setText(tenchap+"");
            holder.txtNgayCapNhat.setText(date);
            Intent intent = context.getIntent();
            String taikhoan = intent.getStringExtra("taikhoan");
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(context, Detail_Chapter.class);
                intent.putExtra("noidung", noidung);
                intent.putExtra("machap", idChap);
                intent.putExtra("idtruyen", idTruyen);
                intent.putExtra("tenchap",tenchap);
                intent.putExtra("tieude",tieude );
                intent.putExtra("ngaycapnhat",date);
                intent.putExtra("size",chapterList.size());
                intent.putExtra("taikhoan", taikhoan);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return chapterList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView txtTenChap,txtNgayCapNhat;
        ItemClickListener itemClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenChap = itemView.findViewById(R.id.chapter);
            txtNgayCapNhat = itemView.findViewById(R.id.datetime);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener ic) {
            this.itemClickListener = ic;
        }


        @Override
        public void onClick(View view) {
            this.itemClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}
