package com.learn2crack.fragments;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.learn2crack.R;
import com.learn2crack.model.Response;
import com.learn2crack.model.User;
import com.learn2crack.network.NetworkUtil;
import com.learn2crack.utils.Constants;

import java.io.IOException;
import java.util.Calendar;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

//import android.support.v4.app.Fragment;

public class FirstFragment extends Fragment  {

//implements Home.SendMessage




    private ViewPager viewPager;
    private TabLayout tabLayoutRR;

    private String name;

    public void setName(String name)
    {
        this.name= name;
    }

    public String getName()
    {
        return name;
    }


    private Home home = new Home();












    public FirstFragment() {
// Required empty public constructor
    }








//////Chawal starts /////////


    private SharedPreferences mSharedPreferences;
    private String mToken;
    private String mEmail;

    private CompositeSubscription mSubscriptions;





    private void loadProfile() {

        mSubscriptions.add(NetworkUtil.getRetrofit(mToken).getProfile(mEmail)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));
    }

    private void initSharedPreferences() {

//        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getView().getContext());
//        mToken = mSharedPreferences.getString(Constants.TOKEN,"");
//        mEmail = mSharedPreferences.getString(Constants.EMAIL,"");
    }

    private void handleResponse(User user) {

        setName(user.getName());

        Log.d("myTag","XXXXThe logged in users names is" +getName());

        tvCurrentDay = (TextView) getView().findViewById(R.id.current_day);
        tvCurrentDay.setText("\n \n Welcome " +getName() + " ! \n Today is " +today()+ ".\n");
        tvCurrentDay.setTextSize(40.5f);

        RelativeLayout rl = (RelativeLayout)getView().findViewById(R.id.first_fragment_rl);
        rl.setOnClickListener(view -> testmethod()); //it was viewe


        tabLayout = (TabLayout) getView().findViewById(R.id.tab_id); //Owaisio

        //mProgressbar.setVisibility(View.GONE);
        //mTvName.setText(user.getName());
        //mTvEmail.setText(user.getEmail());

       // Log.d("myTag", "The user who logged in is "+mTvEmail.getText() + "^^^^^"+user.getSkills()+ "\n county in which he lives is "+user.getCounty()+ "\n And wants services only dash basis" +user.getFrequency());
        //setEmail(user.getEmail());
        //Yuppieee, I am getting the skills here as well
        // First one I got for gujar@gujarkhan.com and skills are childcare, automatick, abcedfgh,
        //I just changed one line in profile.js in node.js project ... added skills:1 on line number 9
        // Here I am getting the name and email of user
        // Try to exeucte query from database and based on the type, skills, frequencey, etc show the message
        //this place could be for recommendation engine
        //Although this will not be the best case, as the above values are coming from db,
        //so check db connection ...
        //Now, I am able to get the skills of the user, county of the user, and frequency of user



        //mTvMessage.setText("Congratulations!!! You will ONLY see volunteer opportunities from "+user.getCounty() + "similar to your skills i.e. "+user.getSkills());

        //mTvDate.setText(user.getCreated_at());
    }

    private void handleError(Throwable error) {

       // mProgressbar.setVisibility(View.GONE);

        if (error instanceof HttpException) {

            Gson gson = new GsonBuilder().create();

            try {

                String errorBody = ((HttpException) error).response().errorBody().string();
                Response response = gson.fromJson(errorBody,Response.class);
         //       showSnackBarMessage(response.getMessage());

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

           /// showSnackBarMessage("Network Error !");
        }
    }
    //////Chawal endss /////////













    private TextView tvCurrentDay;
    private TabLayout tabLayout;

    public static final String TAG = FirstFragment.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private TabLayout tv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_first,container,false);


        final View view1 = inflater.inflate(R.layout.home, container, false);
        //Button remove=(Button) view1.findViewById(R.id.remove_cart_items);
        tabLayoutRR= (TabLayout) view1.findViewById(R.id.tab_id);

        mSubscriptions = new CompositeSubscription();

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        mToken = mSharedPreferences.getString(Constants.TOKEN,"");
        mEmail = mSharedPreferences.getString(Constants.EMAIL,"");
        loadProfile();

        initiView(view);
        return view;


    }



    private void initiView(View v)
    {


    }



    Home hh= new Home();

    private void testmethod()
    {


//        User user = new User();
//        //String testStr= displaymessage("");
//
//        //Log.d("myTag","The name of the the logged in user is"+user.getName() + "PPPPPPP" +displaymessage(message));
//
//       // tabLayout = (TabLayout) getView().findViewById(16908290); //Owaisio
//
//        //tabLayout.getTabAt(1).select(); //set the current tab to second one i.e. opportunities
//        // https://stackoverflow.com/questions/16315280/how-to-switch-tabs-programatically-in-android-from-fragment
//
//        //TabLayout tabLayout= {252e957 VFED..... ......I. 0,0-0,0 #7f0700b6 app:id/tab_id};
//        Log.d("myTag","HAYE HAYE "+tabLayoutRR);
//
//        //TabLayout tabLayout= (TabLayout)getView().findViewById(Integer.parseInt(home.getSTN()));
//        //Log.d("myTag","UFFFFF"+tabLayout);
//
//        Bundle bundle= this.getArguments();
//        if(bundle!=null)
//        {
//            int myInt= bundle.getInt("whichkey",10);
//            Log.d("myTag","The value I got from other fragment is"+myInt);
//        }
//        else
//        {
//            Log.d("myTag", "BUNDLE IS NULL");
//        }
//
//
//        Fragment fragmentt=new SecondFragment();
//        android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
//        ft.replace(R.id.simpleFrameLayout,fragmentt,SecondFragment.TAG);
//        ft.commit();
//
////        TabLayout.Tab tt= tabLayoutRR.getTabAt(1);
////        tt.select();
//
////        TabLayout.Tab tabz = tabLayoutRR.getTabAt(1);
////        tabz.select();
////        //tabLayoutRR.get
//
//
//
//
//
////        TabActivity tabs= (TabActivity) getParentFragment();
////
////        tabHost.setCurrentTab(1);
////        Home home= new Home();
////        home.


    }


    private String today()
    {

        Calendar cal = Calendar.getInstance();
        //int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        int dayOfMonth = cal.get(Calendar.DATE);
        int month= cal.get(Calendar.MONTH);
        String monthStr=null;
        switch(month)
        {
            case 1:
                monthStr= "January";
                break;
            case 2:
                monthStr= "February";
                break;
            case 3:
                monthStr= "March";
                break;
            case 4:
                monthStr= "April";
                break;
            case 5:
                monthStr= "May";
                break;
            case 6:
                monthStr= "June";
                break;
            case 7:
                monthStr= "July";
                break;
            case 8:
                monthStr= "August";
                break;
            case 9:
                monthStr= "September";
                break;
            case 10:
                monthStr= "October";
                break;
            case 11:
                monthStr= "November";
                break;
            case 12:
                monthStr= "December";
                break;
        }

        //String monthStr = String.valueOf(month);

        String dayOfMonthStr = String.valueOf(dayOfMonth);

        String fullDate= dayOfMonthStr + " " +monthStr;

        Log.d("myTag","Today is " + fullDate);

        return fullDate;
    }


//    @Override
//    public void sendData(String message) {
//        displaymessage(message);
//    }

    private void displaymessage(String message)
    {
    Log.d("myTag", "ZZZZZZ Data received from HOME fragment is"+message);
    }
}