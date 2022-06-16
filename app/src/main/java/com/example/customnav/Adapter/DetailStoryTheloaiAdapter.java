package com.example.customnav.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.customnav.Detail_Story;
import com.example.customnav.Interface.ItemClickListener;
import com.example.customnav.R;
import com.example.customnav.TheloaiDetail;
import com.example.customnav.model.Story;

import java.util.List;

public class DetailStoryTheloaiAdapter extends RecyclerView.Adapter<DetailStoryTheloaiAdapter.ViewHolder>{
    TheloaiDetail context;
    List<Story> storyList;

    public DetailStoryTheloaiAdapter(TheloaiDetail context, List<Story> storyList) {
        this.context = context;
        this.storyList = storyList;
    }

    @NonNull
    @Override
    public DetailStoryTheloaiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_story,parent,false);
        return new DetailStoryTheloaiAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailStoryTheloaiAdapter.ViewHolder holder, int position) {
        int idtruyen = storyList.get(position).getIdTruyen();
        int idtheloai = storyList.get(position).getIdTheLoai();
        String tentruyen = storyList.get(position).getTentruyen();
        String trangthai = storyList.get(position).getTinhtrang();
        String taikhoannhomdich = storyList.get(position).getNhomdich();
        int sllike = storyList.get(position).getSlLike();
        int slyeuthich = storyList.get(position).getSlYeuThich();
        String tacgia = storyList.get(position).getTacgia();
        String mota = storyList.get(position).getTomtat();
        String theloai = storyList.get(position).getTentheloai();
        String hinh = storyList.get(position).getHinhtruyen();
        holder.txtTenTruyen.setText(tentruyen);
        holder.txtTrangThai.setText(trangthai);
        holder.txtSoluongLike.setText(sllike+"");
        holder.txtSoluongYeuThich.setText(slyeuthich+"");
        Glide.with(context).load(hinh).into(holder.imgTruyen);
        Intent intent = context.getIntent();
        String taikhoan = intent.getStringExtra("taikhoan");
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(context, Detail_Story.class);
                intent.putExtra("id",idtruyen);
                intent.putExtra("idtheloai", idtheloai);
                intent.putExtra("sllike",sllike);
                intent.putExtra("slyeuthich",slyeuthich);
                intent.putExtra("ten",tentruyen);
                intent.putExtra("hinh",hinh);
                intent.putExtra("tacgia",tacgia);
                intent.putExtra("trangthai",trangthai);
                intent.putExtra("mota",mota);
                intent.putExtra("theloai",theloai);
                intent.putExtra("nhomdich", taikhoannhomdich);
                intent.putExtra("taikhoan", taikhoan);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return storyList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imgTruyen ;
        TextView txtTenTruyen,txtTrangThai,txtSoluongLike,txtSoluongYeuThich,txttacgia;
        ItemClickListener itemClickListener;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgTruyen = itemView.findViewById(R.id.img_story);
            txtTenTruyen = itemView.findViewById(R.id.name_story);
            txtTrangThai = itemView.findViewById(R.id.trangthai_story);
            txttacgia=itemView.findViewById(R.id.txt_tacgia);
            txtSoluongLike = itemView.findViewById(R.id.soluonglike_story);
            txtSoluongYeuThich = itemView.findViewById(R.id.soluongfavorite_story);
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