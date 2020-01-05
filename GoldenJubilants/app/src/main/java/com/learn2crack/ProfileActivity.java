
package com.learn2crack;




import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.learn2crack.fragments.ChangePasswordDialog;
import com.learn2crack.model.Response;
import com.learn2crack.model.User;
import com.learn2crack.network.NetworkUtil;
import com.learn2crack.utils.Constants;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class ProfileActivity extends AppCompatActivity implements ChangePasswordDialog.Listener {

    public ProfileActivity()
    {

    }

    public static final String TAG = ProfileActivity.class.getSimpleName();

    private TextView mTvName;
    private TextView mTvEmail;

    private TextView mTvMessage;

    private TextView mTvDate;
    private Button mBtChangePassword;
    private Button mBtLogout;

    private ProgressBar mProgressbar;

    private SharedPreferences mSharedPreferences;
    private String mToken;
    private String mEmail;

    private CompositeSubscription mSubscriptions;



    private Button mBtAddNewOpp;

    //private EditText mEtSkills;



    private String email;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mSubscriptions = new CompositeSubscription();
        initViews();
        initSharedPreferences();
        loadProfile();


    }

    private void initViews() {

        mTvName = (TextView) findViewById(R.id.tv_name);
        mTvEmail = (TextView) findViewById(R.id.tv_email);

        mTvMessage= (TextView) findViewById(R.id.tv_message);


        mTvDate = (TextView) findViewById(R.id.tv_date);
        mBtChangePassword = (Button) findViewById(R.id.btn_change_password);
        mBtLogout = (Button) findViewById(R.id.btn_logout);
        mProgressbar = (ProgressBar) findViewById(R.id.progress);

        //mEtSkills= (EditText) findViewById(R.id.et_list_skills);

        mBtAddNewOpp= (Button) findViewById(R.id.btn_addneed);

        mBtAddNewOpp.setOnClickListener(view -> addNewOpportunity() );


        mBtChangePassword.setOnClickListener(view -> showDialog());
        mBtLogout.setOnClickListener(view -> logout());


    }


    private void addNewOpportunity()
    {
        Log.d("myTag","New opportunity window opened");
        Intent intent = new Intent(this, AddNewOpportunity.class);
        startActivity(intent);
    }


    private void initSharedPreferences() {

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mToken = mSharedPreferences.getString(Constants.TOKEN,"");
        mEmail = mSharedPreferences.getString(Constants.EMAIL,"");
    }

    private void logout() {

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(Constants.EMAIL,"");
        editor.putString(Constants.TOKEN,"");
        editor.apply();
        finish();
    }

    private void showDialog(){

        ChangePasswordDialog fragment = new ChangePasswordDialog();

        Bundle bundle = new Bundle();
        bundle.putString(Constants.EMAIL, mEmail);
        bundle.putString(Constants.TOKEN,mToken);
        fragment.setArguments(bundle);

        fragment.show(getFragmentManager(), ChangePasswordDialog.TAG);
    }

    private void loadProfile() {

        mSubscriptions.add(NetworkUtil.getRetrofit(mToken).getProfile(mEmail)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));
    }

    private void handleResponse(User user) {

        mProgressbar.setVisibility(View.GONE);
        mTvName.setText(user.getName());
        mTvEmail.setText(user.getEmail());

        Log.d("myTag", "The user who logged in is "+mTvEmail.getText() + "^^^^^"+user.getSkills()+ "\n county in which he lives is "+user.getCounty()+ "\n And wants services only dash basis" +user.getFrequency());
        setEmail(user.getEmail());
        //Yuppieee, I am getting the skills here as well
        // First one I got for gujar@gujarkhan.com and skills are childcare, automatick, abcedfgh,
        //I just changed one line in profile.js in node.js project ... added skills:1 on line number 9
        // Here I am getting the name and email of user
        // Try to exeucte query from database and based on the type, skills, frequencey, etc show the message
        //this place could be for recommendation engine
        //Although this will not be the best case, as the above values are coming from db,
        //so check db connection ...
        //Now, I am able to get the skills of the user, county of the user, and frequency of user



        mTvMessage.setText("Congratulations!!! You will ONLY see volunteer opportunities from "+user.getCounty() + "similar to your skills i.e. "+user.getSkills());

        mTvDate.setText(user.getCreated_at());
    }

    private void handleError(Throwable error) {

        mProgressbar.setVisibility(View.GONE);

        if (error instanceof HttpException) {

            Gson gson = new GsonBuilder().create();

            try {

                String errorBody = ((HttpException) error).response().errorBody().string();
                Response response = gson.fromJson(errorBody,Response.class);
                showSnackBarMessage(response.getMessage());

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            showSnackBarMessage("Network Error !");
        }
    }

    private void showSnackBarMessage(String message) {

        Snackbar.make(findViewById(R.id.activity_profile),message,Snackbar.LENGTH_SHORT).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }

    @Override
    public void onPasswordChanged() {

        showSnackBarMessage("Password Changed Successfully !");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

