package com.example.customnav.Adapter;

import android.content.Context;
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
import com.example.customnav.model.ChapterDetail;

import java.util.List;

public class DetailChapterAdapter extends RecyclerView.Adapter<DetailChapterAdapter.ViewHolder>{
    Context context;
    List<ChapterDetail> chapterList;

    public DetailChapterAdapter(Context context, List<ChapterDetail> chapterList) {
        this.context = context;
        this.chapterList = chapterList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chapter_row,parent,false);
        return new DetailChapterAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int tenchap = chapterList.get(position).getTenchap();
        int idTruyen = chapterList.get(position).getIdtruyen();
        String tieude = chapterList.get(position).getTieude();
        String date = chapterList.get(position).getNgaycapnhat();
        String idChap = chapterList.get(position).getMachap();
        String noidung = chapterList.get(position).getNoidung();
        holder.txtChapter.setText(tenchap+"");
        holder.txtNgayCapNhat.setText(date);
        holder.txtTieuDe.setText(tieude);
        holder.txtNoiDung.setText(noidung);
    }

    @Override
    public int getItemCount() {
        return chapterList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtNgayCapNhat, txtChapter, txtTieuDe, txtNoiDung;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtChapter = itemView.findViewById(R.id.ten_chapter);
            txtNgayCapNhat = itemView.findViewById(R.id.ngaycapnhat);
            txtTieuDe = itemView.findViewById(R.id.tieu_De);
            txtNoiDung = itemView.findViewById(R.id.noidung);
        }
    }
}
