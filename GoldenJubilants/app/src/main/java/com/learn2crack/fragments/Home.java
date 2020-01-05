package com.learn2crack.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.learn2crack.R;

import rx.subscriptions.CompositeSubscription;

//import android.support.v4.app.Fragment;
//import android.support.v4.app.Fragment;
/**
 * Created by Bilal on 02/11/2017.
 */

public class Home extends Fragment {


    private String stn;

    public String getSTN()
    {
        return stn;
    }
    public void setStn(String stn)
    {
        this.stn= stn;
    }


    SendMessage SM;

    interface SendMessage {
        void sendData(String message);
    }

    public static final String TAG = Home.class.getSimpleName();

    private Button btnPressMe;


    private CompositeSubscription mSubscriptions;



    private LinearLayout linearLayoutLayout;
    private LayoutParams lp;
    private Button btn;


   private TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home,container,false);
        mSubscriptions = new CompositeSubscription();
        initViews(view);
        return view;
    }


    private void initViews(View v) {

    //    mEtName = (EditText) v.findViewById(R.id.et_name);


        linearLayoutLayout= (LinearLayout) v.findViewById(R.id.home_page);

        //btnPressMe = (Button) v.findViewById(R.id.press_me);



        tabLayout= (TabLayout) v.findViewById(R.id.tab_id);



        String st= ""+R.id.tab_id;
        Log.d("myTag","The VALUE OF TAB LAYOUT IS "+st);
        setStn(st);

        TabLayout.Tab firstTab = tabLayout.newTab();
        firstTab.setText("HOME"); // set the Text for the first Tab
        tabLayout.addTab(firstTab); // add  the tab at in the TabLayout

        // Create a new Tab named "Second"
        TabLayout.Tab secondTab = tabLayout.newTab();
        secondTab.setText("Opportunities"); // set the Text for the second Tab
        tabLayout.addTab(secondTab); // add  the tab  in the TabLayout

        TabLayout.Tab thirdTab = tabLayout.newTab();
        thirdTab.setText("Testimonials"); // set the Text for the first Tab
        tabLayout.addTab(thirdTab); // add  the tab at in the TabLayout

        TabLayout.Tab fourthTab = tabLayout.newTab();
        fourthTab.setText("News Room"); // set the Text for the first Tab //apni galti
        tabLayout.addTab(fourthTab); // add  the tab at in the TabLayout

        TabLayout.Tab fifthTab = tabLayout.newTab();
        fifthTab.setText("To Do"); // set the Text for the first Tab //apni galti
        tabLayout.addTab(fifthTab); // add  the tab at in the TabLayout


        Fragment fragmentdefault = new FirstFragment();

        Bundle bundle = new Bundle();
        bundle.putInt("whichtab", R.id.tab_id);
        fragmentdefault.setArguments(bundle);

        android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.simpleFrameLayout,fragmentdefault,FirstFragment.TAG);
        ft.commit();

        // perform setOnTabSelectedListener event on TabLayout
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
// get the current selected tab's position and replace the fragment accordingly
                Fragment fragmentt=null;
                android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                switch (tab.getPosition()) {
                    case 0:
                        fragmentt = new FirstFragment();
                        ft.replace(R.id.simpleFrameLayout,fragmentt,FirstFragment.TAG);
                        ft.commit();
                        Log.d("myTag","First Tab Clicked" +tabLayout);
                        //SM.sendData(tabLayout.toString());
                        break;
                    case 1:
                        //fragment = new SecondFragment();
                        Bundle bundle= new Bundle();
                        fragmentt = new SecondFragment();
                        ft.replace(R.id.simpleFrameLayout,fragmentt).commit();
                        //ft.commit();
                        Log.d("myTag","Second Tab Clicked" +android.R.id.content);
                        break;
                    case 2:
                        fragmentt = new TestimonialsFragment();
                        ft.replace(R.id.simpleFrameLayout,fragmentt, TestimonialsFragment.TAG);
                        ft.commit();
                        Log.d("myTag","Third Tab Clicked" +android.R.id.content);
                        break;
                    case 3:
                        fragmentt = new NewsRoomFragment();
                        ft.replace(R.id.simpleFrameLayout,fragmentt, NewsRoomFragment.TAG);
                        ft.commit();
                        Log.d("myTag","Fourth Tab Clicked" +android.R.id.content);
                        break;
                    case 4:
                        fragmentt = new PlannerFragment();
                        ft.replace(R.id.simpleFrameLayout,fragmentt, PlannerFragment.TAG);
                        ft.commit();
                        Log.d("myTag","Fifth Tab Clicked" +android.R.id.content);
                        break;


                }
                //
//                AppCompatActivity appCompatActivity= new AppCompatActivity(); //Not sure about these two lines
//                FragmentManager fm = appCompatActivity.getSupportFragmentManager();
//                FragmentTransaction ft = fm.beginTransaction();
//                ft.replace(R.id.simpleFrameLayout, fragmentt);
//                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                ft.commit();


               // android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                //FirstFragment fragment = new FirstFragment();
                // ft.replace(R.id.simpleFrameLayout,fragmentt,FirstFragment.TAG);
                // ft.commit();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
//                Fragment fragmentt= new FirstFragment();
//                android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
//                ft.replace(R.id.simpleFrameLayout,fragmentt,FirstFragment.TAG);
//                ft.commit();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });




//        btnPressMe.setOnClickListener(view -> testMethod());
    }

    private void testMethod()
    {
        Log.d("myTag", "test method running inside home .... yupieee" + linearLayoutLayout );


        btn=new Button(getActivity());
        btn.setText("Dynamically Added Button");
        lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        linearLayoutLayout.addView(btn,lp);
        Log.d("myTag", "test method running inside home .... yupieee"+linearLayoutLayout + "DOZ DOZ " +lp + "PUK PUK " +btn);

    }


//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//
//        try {
//            SM = (SendMessage) getActivity();
//        } catch (ClassCastException e) {
//            throw new ClassCastException("Error in retrieving data. Please try again");
//        }
//    }


}
