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
import com.example.customnav.Detail_Chapter;
import com.example.customnav.Interface.ItemClickListener;
import com.example.customnav.R;
import com.example.customnav.fragment.HistoryFragment;
import com.example.customnav.model.Chapter;
import com.example.customnav.model.LichSu;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    List<LichSu> lichSuList;
    HistoryFragment context;

    public HistoryAdapter(List<LichSu> lichSuList, HistoryFragment context) {
        this.lichSuList = lichSuList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history,parent,false);
        return new HistoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int machap = lichSuList.get(position).getMaChap();
        int tenchap = lichSuList.get(position).getTenChap();
        int idTruyen = lichSuList.get(position).getIdTruyen();
        String tieude = lichSuList.get(position).getTieuDe();
        String noidung = lichSuList.get(position).getNoiDung();
        String ngaycapnhat =lichSuList.get(position).getNgayCapNhat();
        String tentruyen = lichSuList.get(position).getTenTruyen();
        String hinhtruyen = lichSuList.get(position).getHinhTruyen();
        holder.txtTenTruyen.setText(tentruyen);
        holder.txtTenChap.setText(tenchap+"");
        holder.txtNgayCapNhat.setText(ngaycapnhat);
        Glide.with(context).load(hinhtruyen).into(holder.imgTruyen);
        Intent intent = context.getActivity().getIntent();
        String taikhoan = intent.getStringExtra("taikhoan");
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(context.getContext(), Detail_Chapter.class);
                intent.putExtra("tenchap",tenchap);
                intent.putExtra("machap",machap);
                intent.putExtra("idtruyen",idTruyen);
                intent.putExtra("tieude",tieude );
                intent.putExtra("noidung",noidung);
                intent.putExtra("ngaycapnhat",ngaycapnhat);
                intent.putExtra("taikhoan",taikhoan);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lichSuList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgTruyen ;
        TextView txtTenChap,txtNgayCapNhat,txtTenTruyen;
        ItemClickListener itemClickListener;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgTruyen = itemView.findViewById(R.id.img_story);
            txtTenTruyen = itemView.findViewById(R.id.name_story);
            txtTenChap = itemView.findViewById(R.id.ten_chap);
            txtNgayCapNhat = itemView.findViewById(R.id.ngaycapnhat);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            this.itemClickListener.onItemClick(view, getAdapterPosition());
        }
        public void setItemClickListener(ItemClickListener ic) {
            this.itemClickListener = ic;
        }

    }
}
