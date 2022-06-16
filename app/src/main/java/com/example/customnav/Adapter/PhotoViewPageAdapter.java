package com.example.customnav.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.customnav.R;
import com.example.customnav.model.Photo;

import java.util.List;

public class PhotoViewPageAdapter extends PagerAdapter {
    private List<Photo> mPhotoList;

    public PhotoViewPageAdapter(List<Photo> mPhotoList) {
        this.mPhotoList = mPhotoList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_photo,container,false);

        ImageView imgPhoto = view.findViewById(R.id.image_photo);

        Photo photo = mPhotoList.get(position);
        imgPhoto.setImageResource(photo.getResourceId());

        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        if(mPhotoList != null){
            return mPhotoList.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
