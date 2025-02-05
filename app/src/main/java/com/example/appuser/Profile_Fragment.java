package com.example.appuser;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.appuser.databinding.FragmentProfileBinding;

public class Profile_Fragment extends Fragment {
    ScrollView scrollView;
    CardView cardAbout,cardSecurity,cardContact,cardlogout;

    ImageView img;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    FragmentProfileBinding binding;

    public Profile_Fragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = preferences.edit();

        scrollView = view.findViewById(R.id.scrollView);
        scrollView.setVerticalScrollBarEnabled(false);

        cardContact =view.findViewById(R.id.card_contact);
        cardSecurity =view.findViewById(R.id.card_security);
        cardAbout =view.findViewById(R.id.card_about_us);
        cardlogout =view.findViewById(R.id.cd_4_1);
        img = view.findViewById(R.id.img_profile);



        cardContact.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), ContactUsActivity.class);
            startActivity(intent);
        });

        cardAbout.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), About_Us_Activity.class);
            startActivity(intent);
        });

        cardSecurity.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), TermsConditionsActivity.class);
            startActivity(intent);
        });

//        img.setOnClickListener(view1 -> {
//            Intent intent = new Intent(getActivity(), DemoActivity.class);
//            startActivity(intent);
//        });

        cardlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
                ad.setTitle("Logout");
                ad.setMessage("Are you sure you want to Logout ?");
                ad.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                ad.setNegativeButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent =new Intent(getActivity(),SingInActivity.class);
                        editor.putBoolean("islogin",false).commit();
                        startActivity(intent);
                    }
                }).create().show();
            }
        });

        return view;
    }
}