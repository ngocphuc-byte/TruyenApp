package com.example.customnav.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customnav.Detail_Story;
import com.example.customnav.Interface.ItemClickListener;
import com.example.customnav.R;
import com.example.customnav.model.BinhLuan;

import java.util.List;

public class BinhLuanAdapter extends RecyclerView.Adapter<BinhLuanAdapter.ViewHolder> {
    Detail_Story context;
    List<BinhLuan> binhLuanList;

    public BinhLuanAdapter(Detail_Story context, List<BinhLuan> binhLuanList) {
        this.context = context;
        this.binhLuanList = binhLuanList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_binhluan,parent,false);
        return new BinhLuanAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int id = binhLuanList.get(position).getIdTruyen();
        holder.txtTaikhoan.setText(binhLuanList.get(position).getTaikhoan());
        holder.txtBinhluan.setText(binhLuanList.get(position).getNoidung());
    }

    @Override
    public int getItemCount() {
        return binhLuanList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTaikhoan, txtBinhluan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTaikhoan = itemView.findViewById(R.id.tenuser);
            txtBinhluan = itemView.findViewById(R.id.txt_binhluan);

        }
    }
}
