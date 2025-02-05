package com.example.appuser;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Home_Fragment extends Fragment {
    private ArrayList<ItemModel> recycleList;
    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    Button additem;

    private TabLayout tabLayout;
    private ViewPager viewPager;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home, container, false);
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.view_pager);

        MyPagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
        return view;
    }
    private static class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            // Return the Fragment associated with the specified position
            // This is where you would typically return a different Fragment for each tab
            switch (position) {
                case 0:
                    return new PlantFragment();
                case 1:
                    return new SeedsFragment();
                case 2:
                    return new ToolsFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Return the number of tabs
            return 3; // 3 tabs
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Return the title of the tab at the specified position
            switch (position) {
                case 0:
                    return "Plants";
                case 1:
                    return "Seeds";
                case 2:
                    return "Tools";
                default:
                    return null;
            }
        }
    }
}