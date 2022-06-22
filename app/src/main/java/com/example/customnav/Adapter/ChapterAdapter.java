package com.example.customnav.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.customnav.Detail_Chapter;
import com.example.customnav.Interface.ItemClickListener;
import com.example.customnav.R;
import com.example.customnav.model.Chapter;

import java.util.ArrayList;
import java.util.List;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ChapterViewHolder> implements Filterable {
    Fragment context;
    private List<Chapter> chapterlist ;
    private List<Chapter> newChapterList;

    public ChapterAdapter(Fragment context, List<Chapter> chapterlist) {
        this.context = context;
        this.chapterlist = chapterlist;
        this.newChapterList = chapterlist;
    }

    @NonNull
    @Override
    public ChapterAdapter.ChapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chapter,parent,false);
        return new ChapterAdapter.ChapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterViewHolder holder, int position) {
        String taikhoan;
        int tenchap = chapterlist.get(position).getTenchap();
        int idTruyen = chapterlist.get(position).getIdtruyen();
        String machap = chapterlist.get(position).getMachap();
        String tieude = chapterlist.get(position).getTieude();
        String noidung = chapterlist.get(position).getNoidung();
        String ngaycapnhat =chapterlist.get(position).getNgaycapnhat();
        String tentruyen = chapterlist.get(position).getTentruyen();
        String hinhtruyen = chapterlist.get(position).getHinhtruyen();
        holder.txtTenTruyen.setText(tentruyen);
        holder.txtTenChap.setText(tenchap+"");
        holder.txtNgayCapNhat.setText(ngaycapnhat);
        Glide.with(context).load(hinhtruyen).into(holder.imgTruyen);
        Intent intent = context.getActivity().getIntent();
        taikhoan = intent.getStringExtra("taikhoan");
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
        return chapterlist.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if(strSearch.isEmpty()) {
                    chapterlist = newChapterList;
                } else {
                    List<Chapter> list = new ArrayList<>();
                    for(Chapter item : newChapterList){
                        if(item.getNoidung().trim().toLowerCase().contains(strSearch.trim().toLowerCase())){
                            list.add(item);
                        }
                    }
                    chapterlist = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = chapterlist;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                chapterlist = (List<Chapter>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class ChapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imgTruyen ;
        TextView txtTenChap,txtNgayCapNhat,txtTenTruyen;
        ItemClickListener itemClickListener;

        public ChapterViewHolder(@NonNull View itemView) {
            super(itemView);
            imgTruyen = itemView.findViewById(R.id.img_story);
            txtTenTruyen = itemView.findViewById(R.id.name_story);
            txtTenChap = itemView.findViewById(R.id.ten_chap);
            txtNgayCapNhat = itemView.findViewById(R.id.ngaycapnhat);
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
