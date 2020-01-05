package com.learn2crack;

/**
 * Created by Bilal on 02/11/2017.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.learn2crack.model.Response;
import com.learn2crack.model.User;
import com.learn2crack.network.NetworkUtil;
import com.learn2crack.utils.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class ExpandableListDataPump extends AppCompatActivity{


//    private CompositeSubscription mSubscriptions; //chawlatian
//    private SharedPreferences mSharedPreferences;//chawlatian
//    private String mToken; //chawlatian
//    private String mEmail; //chawlatian
//
//
//        public ExpandableListDataPump()
//        {
//            Log.d("myTag", "Data Pump constructor started");
//
//            mSubscriptions = new CompositeSubscription();
//            initSharedPreferences();
//
//
//            Log.d("myTag", "Data Pump constructor ended");
//        }
//
//
//    private void initSharedPreferences() {
//        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        mToken = mSharedPreferences.getString(Constants.TOKEN,"");
//        mEmail = mSharedPreferences.getString(Constants.EMAIL,"");
//    }
//
//        ////////Chawlation starts
//        private void loadProfile() {
//
//            mSubscriptions.add(NetworkUtil.getRetrofit(mToken).getProfile(mEmail)
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribeOn(Schedulers.io())
//                    .subscribe(this::handleResponse,this::handleError));
//        }
//
//    private void handleResponse(User user) {
//
//       // mProgressbar.setVisibility(View.GONE);
//        //mTvName.setText(user.getName());
//        //mTvEmail.setText(user.getEmail());
//
//        Log.d("myTag", "The user who logged in is KHURK " + "^^^^^"+user.getSkills()+ "\n county in which he lives is "+user.getCounty()+ "\n And wants services only dash basis" +user.getFrequency());
//
//        //Yuppieee, I am getting the skills here as well
//        // First one I got for gujar@gujarkhan.com and skills are childcare, automatick, abcedfgh,
//        //I just changed one line in profile.js in node.js project ... added skills:1 on line number 9
//        // Here I am getting the name and email of user
//        // Try to exeucte query from database and based on the type, skills, frequencey, etc show the message
//        //this place could be for recommendation engine
//        //Although this will not be the best case, as the above values are coming from db,
//        //so check db connection ...
//        //Now, I am able to get the skills of the user, county of the user, and frequency of user
//
//
//
//        //mTvMessage.setText("Congratulations!!! You will ONLY see volunteer opportunities from "+user.getCounty() + "similar to your skills i.e. "+user.getSkills());
//
//        //mTvDate.setText(user.getCreated_at());
//    }
//
//    private void handleError(Throwable error) {
//
//        //mProgressbar.setVisibility(View.GONE);
//
//        if (error instanceof HttpException) {
//
//            Gson gson = new GsonBuilder().create();
//
//            try {
//
//                String errorBody = ((HttpException) error).response().errorBody().string();
//                Response response = gson.fromJson(errorBody,Response.class);
//                showSnackBarMessage(response.getMessage());
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } else {
//
//            showSnackBarMessage("Network Error !");
//        }
//    }
//
//    private void showSnackBarMessage(String message) {
//
//        //Snackbar.make(findViewById(R.id.activity_profile),message,Snackbar.LENGTH_SHORT).show();
//
//    }
//
//    ////// Chawlatian ends
//
//
//
//
//
//
//
////    public static HashMap<String, List<String>> getData() {
////        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();
////
////        List<String> cricket = new ArrayList<String>();
////        cricket.add("India");
////        cricket.add("Pakistan");
////        cricket.add("Australia");
////        cricket.add("England");
////        cricket.add("South Africa");
////
////        List<String> football = new ArrayList<String>();
////        football.add("Brazil");
////        football.add("Spain");
////        football.add("Germany");
////        football.add("Netherlands");
////        football.add("Italy");
////
////        List<String> basketball = new ArrayList<String>();
////        basketball.add("United States");
////        basketball.add("Spain");
////        basketball.add("Argentina");
////        basketball.add("France");
////        basketball.add("Russia");
////
////
////        List<String> volopportunity = new ArrayList<String>();
////        volopportunity.add("Bilal");
////        volopportunity.add("bilal_uaar@yahoo.com");
////
////        expandableListDetail.put("CRICKET TEAMS", cricket);
////        expandableListDetail.put("FOOTBALL TEAMS", football);
////        expandableListDetail.put("BASKETBALL TEAMS", basketball);
////
////
////        expandableListDetail.put("GARDNDER NEEDED", volopportunity);
////
////
////
////        User user = new User();
////
////        Log.d("myTag","Testing User in Data pump class ... \n" +user.getName() + "......\n"+user.getEmail() );
////
////
////
////
////        return expandableListDetail;
////    }
}
