package com.example.customnav.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customnav.Detail_Story;
import com.example.customnav.Interface.ItemClickListener;
import com.example.customnav.R;
import com.example.customnav.TheloaiDetail;
import com.example.customnav.model.Chapter;
import com.example.customnav.model.Theloai;

import java.util.List;

public class TheloaiAdapter extends RecyclerView.Adapter<TheloaiAdapter.TheloaiViewHolder>{
    Fragment context;
    private List<Theloai> theloailist ;

    public TheloaiAdapter(Fragment context, List<Theloai> theloailist) {
        this.context = context;
        this.theloailist = theloailist;
    }

    @NonNull
    @Override
    public TheloaiAdapter.TheloaiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_theloai,parent,false);
        return new TheloaiAdapter.TheloaiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TheloaiAdapter.TheloaiViewHolder holder, int position) {
        int id = theloailist.get(position).getIdtheloai();
        String ten = theloailist.get(position).getTentheloai();
        Intent intent =  context.getActivity().getIntent();
        String taikhoan = intent.getStringExtra("taikhoan");
        holder.txtTentheloai.setText(ten);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(context.getContext(), TheloaiDetail.class);
                intent.putExtra("idtheloai",String.valueOf(id));
                intent.putExtra("ten",ten);
                intent.putExtra("taikhoan",taikhoan);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return theloailist.size();
    }
    public static class TheloaiViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txtTentheloai;
        ItemClickListener itemClickListener;

        public TheloaiViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTentheloai = itemView.findViewById(R.id.tentheloai);
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
