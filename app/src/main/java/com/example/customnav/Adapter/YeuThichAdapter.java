package com.example.customnav.Adapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.customnav.Detail_Story;
import com.example.customnav.Interface.ItemClickListener;
import com.example.customnav.Interface.ItemLongClickListener;
import com.example.customnav.R;
import com.example.customnav.fragment.FavoriteFragment;
import com.example.customnav.model.YeuThich;

import java.util.List;

public class YeuThichAdapter extends RecyclerView.Adapter<YeuThichAdapter.ViewHolder>{
    FavoriteFragment context;
    List<YeuThich> yeuThichList;
    FavoriteFragment fragment = new FavoriteFragment();
    public YeuThichAdapter(FavoriteFragment context, List<YeuThich> yeuThichList) {
        this.context = context;
        this.yeuThichList = yeuThichList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_yeuthich,parent,false);
        return new YeuThichAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int idtruyen = yeuThichList.get(position).getIdtruyen();
        int idtheloai = yeuThichList.get(position).getIdtheloai();
        String tentruyen = yeuThichList.get(position).getTentruyen();
        String trangthai = yeuThichList.get(position).getTinhtrang();
        String taikhoannhomdich = yeuThichList.get(position).getTaikhoannhomdich();
        int sllike = yeuThichList.get(position).getSllike();
        int slyeuthich = yeuThichList.get(position).getSlyeuthich();
        String tacgia = yeuThichList.get(position).getTacgia();
        String mota = yeuThichList.get(position).getTomtat();
        String theloai = yeuThichList.get(position).getTentheloai();
        String hinh = yeuThichList.get(position).getHinhtruyen();
        holder.txtTenTruyen.setText(tentruyen);
        holder.txtTrangThai.setText(trangthai);
        holder.txtSoluongLike.setText(sllike+"");
        holder.txtSoluongYeuThich.setText(slyeuthich+"");
        Glide.with(context).load(hinh).into(holder.imgTruyen);
        Intent intent = context.getActivity().getIntent();
        String taikhoan = intent.getStringExtra("taikhoan");
        holder.setItemLongClickListener(new ItemLongClickListener() {
            @Override
            public void onItemLongClick(View v, int position) {
                XacNhanXoa(taikhoan, idtruyen, tentruyen);

            }
        });
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(context.getContext(), Detail_Story.class);
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
        return yeuThichList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        ImageView imgTruyen ;
        TextView txtTenTruyen,txtTrangThai,txtSoluongLike,txtSoluongYeuThich;
        ItemClickListener itemClickListener;
        ItemLongClickListener itemLongClickListener;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgTruyen = itemView.findViewById(R.id.img_yeuthich);
            txtTenTruyen = itemView.findViewById(R.id.name_yeuthich);
            txtTrangThai = itemView.findViewById(R.id.trangthai_yeuthich);
            txtSoluongLike = itemView.findViewById(R.id.soluonglike_yeuthich);
            txtSoluongYeuThich = itemView.findViewById(R.id.soluongfavorite);
            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener ic) {
            this.itemClickListener = ic;
        }
        public void setItemLongClickListener(ItemLongClickListener ic) {
            this.itemLongClickListener = ic;
        }

        @Override
        public void onClick(View view) {
            this.itemClickListener.onItemClick(view, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            this.itemLongClickListener.onItemLongClick(view, getAdapterPosition());
            return true;
        }

    }
    public void XacNhanXoa(String taikhoan, int id,String tentruyen){
        AlertDialog.Builder dialog = new AlertDialog.Builder(context.getContext());
        dialog.setMessage("Bạn có muốn xóa "+tentruyen+"?");
        dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.deleteYeuThich(id, taikhoan);
            }
        });
        dialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }


}
