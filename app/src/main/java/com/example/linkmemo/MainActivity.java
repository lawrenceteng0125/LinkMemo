package com.example.linkmemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.linkmemo.database.dao.Point_Dao;
import com.example.linkmemo.database.model.PointInfo;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

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

    public String findWord(String word)
    {
        /*
        Point_Dao MyPointDao = new Point_Dao();
        PointInfo MyPoint = MyPointDao.find_center_word(word);

        if (MyPoint != null)
            return MyPoint.getPoint_english() + ";" + MyPoint.getPoint_chinese();
        return "";
         */
       String[] result = new String[1];
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                // 在这里进行你的数据库查询操作
                Point_Dao MyPointDao = new Point_Dao();
                PointInfo MyPoint = MyPointDao.find_center_word(word);

                if (MyPoint != null) {
                    result[0] = MyPoint.getPoint_english() + ";" + MyPoint.getPoint_chinese();
                } else {
                    result[0] = "";
                }
            }
        });
        t.start();

        try {
            t.join();  // 等待线程执行完毕
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result[0];
    }
}