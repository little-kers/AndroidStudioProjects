package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplication.utils.MyViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final Class[] FRAGMENT_CLASSES = new Class[]{
            RankFragment.class,
            LikeFragment.class,
            FunctionFragment.class,
            UpdateFragment.class,
            PersonalFragment.class
    };
    private final List<Fragment> fragmentList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ViewPager2 viewPager = findViewById(R.id.main_view_pager);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) BottomNavigationView navigationView = findViewById(R.id.main_navigation);
        listDataLoad();
        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(), getLifecycle(), fragmentList));
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                viewPager.setCurrentItem(item.getOrder());
                return true;
            }
        });
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                navigationView.getMenu().getItem(position).setChecked(true);
//                Log.d("TAG", "onPageSelected() returned: " + position);
            }
        });
    }

    private void listDataLoad() {
        for (Class fragmentClass : FRAGMENT_CLASSES) {
            try {
                fragmentList.add((Fragment) fragmentClass.newInstance());
            } catch (IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        }
//        fragmentList.add(new RankFragment());
//        fragmentList.add(new LikeFragment());
//        fragmentList.add(new FunctionFragment());
//        fragmentList.add(new UpdateFragment());
//        fragmentList.add(new PersonalFragment());
    }
}