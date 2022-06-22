package com.example.customnav;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.customnav.Login.Login;
import com.example.customnav.fragment.CategoryFragment;
import com.example.customnav.fragment.FavoriteFragment;
import com.example.customnav.fragment.HistoryFragment;
import com.example.customnav.fragment.HomeFragment;
import com.example.customnav.fragment.NewFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_CATEGORY = 1;
    private static final int FRAGMENT_HISTORY = 2;
    private static final int FRAGMENT_NEW = 3;
    private static final int FRAGMENT_FAVORITE = 4;
    private int mCurrentFragment = FRAGMENT_HOME;
    private NavigationView mNavigationView;
    private BottomNavigationView mBottomNavigationView;
    private DrawerLayout mDrawerLayout;
    SwitchCompat switchCompat;
    TextView txtUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        rundarkmode();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        switchCompat = findViewById(R.id.bt_switch);
        mBottomNavigationView=findViewById(R.id.bottom_nav);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        replaceFragment(new HomeFragment());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.nav_drawer_open,R.string.nav_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mNavigationView = findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        mNavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
        mBottomNavigationView.getMenu().findItem(R.id.bottom_home).setChecked(true);
        View headerView = mNavigationView.getHeaderView(0);
        txtUser = headerView.findViewById(R.id.name_user);
        Intent intent= getIntent();
        String taikhoan = intent.getStringExtra("taikhoan");

        //-----------------------------------Set tai khoan nguoi dung
        txtUser.setText(taikhoan);

        mBottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.bottom_home){
                    openHomeFragment();
                    mNavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
                }
                else if(id == R.id.bottom_category){
                    openCategoryFragment();
                    mNavigationView.getMenu().findItem(R.id.nav_category).setChecked(true);

                }
                else if(id == R.id.bottom_favorite){
                    openFavoriteFragment();
                    mNavigationView.getMenu().findItem(R.id.nav_favorite).setChecked(true);

                }
                else if(id == R.id.bottom_history){
                    openHistoryFragment();
                    mNavigationView.getMenu().findItem(R.id.nav_history).setChecked(true);

                }
                else if(id == R.id.bottom_new){
                    openNewFragment();
                    mNavigationView.getMenu().findItem(R.id.nav_new).setChecked(true);

                }
                return true;
            }
        });
        getSupportActionBar().setTitle(null);
//        darkmode();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.nav_home){
            openHomeFragment();
            mBottomNavigationView.getMenu().findItem(R.id.bottom_home).setChecked(true);
        }
        else if(id == R.id.nav_category){
            openCategoryFragment();
            mBottomNavigationView.getMenu().findItem(R.id.bottom_category).setChecked(true);

        }
        else if(id == R.id.nav_favorite){
            openFavoriteFragment();
            mBottomNavigationView.getMenu().findItem(R.id.bottom_favorite).setChecked(true);

        }
        else if(id == R.id.nav_history){
            openHistoryFragment();
            mBottomNavigationView.getMenu().findItem(R.id.bottom_history).setChecked(true);

        }
        else if(id == R.id.nav_new){
            openNewFragment();
            mBottomNavigationView.getMenu().findItem(R.id.bottom_new).setChecked(true);
        }
        else if(id == R.id.nav_logout){
            finish();
            startActivity(new Intent(MainActivity.this, Login.class));
        }
        getSupportActionBar().setTitle(null);
        mDrawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    public void openHomeFragment(){
        if(mCurrentFragment!=FRAGMENT_HOME){
            replaceFragment(new HomeFragment());
            mCurrentFragment=FRAGMENT_HOME;

        }
    }

    public void openCategoryFragment(){
        if(mCurrentFragment!=FRAGMENT_CATEGORY){
            replaceFragment(new CategoryFragment());
            mCurrentFragment=FRAGMENT_CATEGORY;
        }
    }

    public void openFavoriteFragment(){
        if(mCurrentFragment!=FRAGMENT_FAVORITE){
            replaceFragment(new FavoriteFragment());
            mCurrentFragment=FRAGMENT_FAVORITE;
        }
    }

    public void openHistoryFragment(){
        if(mCurrentFragment!=FRAGMENT_HISTORY){
            replaceFragment(new HistoryFragment());
            mCurrentFragment=FRAGMENT_HISTORY;
        }
    }

    public void openNewFragment(){
        if(mCurrentFragment!=FRAGMENT_NEW){
            replaceFragment(new NewFragment());
            mCurrentFragment=FRAGMENT_NEW;
        }
    }


    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    private void replaceFragment(Fragment fragment){
        Intent intent = getIntent();
        String username = intent.getStringExtra("taikhoan");
        Bundle bundle = new Bundle();
        bundle.putString("taikhoan", username);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame,fragment);
        transaction.commit();
    }


//    public void darkmode(){
//        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked){
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                }
//                else{
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                }
//            }
//        });
//    }

    public void rundarkmode(){
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.Theme_Dark);
        }
        else {
            setTheme(R.style.Theme_Light);
        }
    }


    private void AnhXa(){

    }
}

