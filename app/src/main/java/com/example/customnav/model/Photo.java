package com.example.customnav.model;

import com.example.customnav.MainActivity;
import com.example.customnav.fragment.HomeFragment;

public class Photo{
    private int resourceId;

    public Photo(int resourceId) {
        this.resourceId = resourceId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }


}
