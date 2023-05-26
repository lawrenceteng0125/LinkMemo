package com.example.linkmemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.linkmemo.data.WordRepository;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements HomeFragment.searchClicked, LinkFragment.linkBtnClicked{
    //底部导航栏
    BottomNavigationView bottomNavigationView;
    //Fragments
    FragmentManager manager;
    HomeFragment homeFragment = new HomeFragment();
    LinkFragment linkFragment = new LinkFragment();
    BookFragment bookFragment = new BookFragment();
    SettingFragment settingFragment = new SettingFragment();

    private static MainActivity instance;
    private MainActivityViewModel viewModel;
    public static MainActivity getInstance()
    {
        return instance;
    }
    //切换Fragment
    public void switchFragment(int index, boolean setBottomNavi)
    {
        Fragment fragment;
        switch (index){
            case 1:
                fragment = homeFragment;
                if (setBottomNavi)
                    bottomNavigationView.setSelectedItemId(R.id.home);
                break;
            case 2:
                fragment = linkFragment;
                if (setBottomNavi)
                    bottomNavigationView.setSelectedItemId(R.id.link);
                break;
            case 3:
                fragment = bookFragment;
                if (setBottomNavi)
                    bottomNavigationView.setSelectedItemId(R.id.book);
                break;
            default:
                fragment = settingFragment;
                if (setBottomNavi)
                    bottomNavigationView.setSelectedItemId(R.id.setting);
                break;
        }
        manager.beginTransaction().replace(R.id.container, fragment).commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        instance = this;
        //底边栏切换
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        manager = getSupportFragmentManager();
        switchFragment(1, false);
        viewModel = new MainActivityViewModel();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        switchFragment(1, false);
                        return true;
                    case R.id.link:
                        switchFragment(2, false);
                        return true;
                    case R.id.book:
                        switchFragment(3, false);
                        return true;
                    case R.id.setting:
                        switchFragment(4, false);
                        return true;
                }
                return false;
            }
        });
    }
    @Override
    public void sendDataToBookAndLink(String data){
        bookFragment.updateWord(data);
        linkFragment.updateWord(data);
    }

    @Override
    public void sendDataToBook(String data)
    {
        bookFragment.updateWord(data);
    }


    private final WordRepository wordRepository = new WordRepository();

    public String findWord(String word) {
        String data = "";
        try {
            data =  viewModel.getDefinition(word);
        } catch (Exception e) {
            Toast.makeText(new App().appContext(), "Network error", Toast.LENGTH_SHORT).show();
        }
        Log.d("tag", data);
         return data;
       // return "ask;vi.询问,问,要求vt.问,要求,需要,邀请--beg;claim;demand;entreat;implore;pray;request;require-beg;claim;demand;entreat;implore;pray;request;require";
    }
}