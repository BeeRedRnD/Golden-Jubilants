package com.learn2crack.fragments;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.widget.LinearLayout;
import android.widget.Toast;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.learn2crack.CustomExpandableListAdapter;
import com.learn2crack.ExpandableListDataPump;
import com.learn2crack.MainActivity;
import com.learn2crack.ProfileActivity;
import com.learn2crack.R;
import com.learn2crack.model.Response;
import com.learn2crack.network.NetworkUtil;
import com.learn2crack.utils.Constants;

import java.io.IOException;
import java.util.Calendar;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.learn2crack.utils.Validation.validateEmail;
import static com.learn2crack.utils.Validation.validateFields;

//import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout.LayoutParams;

public class LoginFragment extends Fragment {

    public static final String TAG = LoginFragment.class.getSimpleName();

    private EditText mEtEmail;
    private EditText mEtPassword;
    private Button mBtLogin;
    private TextView mTvRegister;
    private TextView mTvForgotPassword;
    private TextInputLayout mTiEmail;
    private TextInputLayout mTiPassword;
    private ProgressBar mProgressBar;

    private CompositeSubscription mSubscriptions;
    private SharedPreferences mSharedPreferences;


    private LinearLayout linearLayoutLayout;
    private LayoutParams lp;
    private Button btn;


    private TextView today;

    //private Button mBtAddNewOpp;











    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;





    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login,container,false);
        mSubscriptions = new CompositeSubscription();
        initViews(view);
        initSharedPreferences();
        return view;
    }

    @SuppressLint("ResourceType")
    private void initViews(View v) {

        mEtEmail = (EditText) v.findViewById(R.id.et_email);
        mEtPassword = (EditText) v.findViewById(R.id.et_password);
        mBtLogin = (Button) v.findViewById(R.id.btn_login);
        mTiEmail = (TextInputLayout) v.findViewById(R.id.ti_email);
        mTiPassword = (TextInputLayout) v.findViewById(R.id.ti_password);
        mProgressBar = (ProgressBar) v.findViewById(R.id.progress);
        mTvRegister = (TextView) v.findViewById(R.id.tv_register);
        mTvForgotPassword = (TextView) v.findViewById(R.id.tv_forgot_password);

       // mBtAddNewOpp= (Button) v.findViewById(R.id.btn_addneed);

        mBtLogin.setOnClickListener(view -> login());
        mTvRegister.setOnClickListener(view -> goToRegister());
        mTvForgotPassword.setOnClickListener(view -> showDialog());

        //mBtAddNewOpp.setOnClickListener(view -> addNewOpportunity());


        linearLayoutLayout= (LinearLayout) v.findViewById(R.id.home_page);
        lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        btn=new Button(getActivity());
        btn.setId(1);

        today= new TextView(v.getContext());

        expandableListView = (ExpandableListView) v.findViewById(R.id.expandableListView);







    }


//    private void addNewOpportunity()
//    {
//        Log.d("myTag","New opportunity window opened");
//        Intent intent = new Intent(getActivity(), AddNewOpportunity.class);
//        startActivity(intent);
//    }



    private void initSharedPreferences() {

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
    }

    private void login() {

        setError();

        String email = mEtEmail.getText().toString();
        String password = mEtPassword.getText().toString();

        int err = 0;

        if (!validateEmail(email)) {

            err++;
            mTiEmail.setError("Email should be valid !");
        }

        if (!validateFields(password)) {

            err++;
            mTiPassword.setError("Password should not be empty !");
        }

        if (err == 0) {

            loginProcess(email,password);
            mProgressBar.setVisibility(View.VISIBLE);

        } else {

            showSnackBarMessage("Enter Valid Details !");
        }
    }

    private void setError() {

        mTiEmail.setError(null);
        mTiPassword.setError(null);
    }

    private void loginProcess(String email, String password) {

        mSubscriptions.add(NetworkUtil.getRetrofit(email, password).login()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));
    }

    private void handleResponse(Response response) {


        //Commented these few lines and then got rid of the error of Unable to connect to editor to retrieve text
//        mProgressBar.setVisibility(View.GONE);
//
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(Constants.TOKEN,response.getToken());
        editor.putString(Constants.EMAIL,response.getMessage());
        editor.apply();

        mEtEmail.setText(null);
        mEtPassword.setText(null);



        //Intent intent = new Intent(getActivity(), ProfileActivity.class);
        //getActivity().startActivity(intent);


        loadVolunteerOpportunities();


        //Commented these 4 lines of 4 November, need to uncomment them after testing the add New Opportunity functionality
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Home fragment = new Home();
        ft.replace(R.id.fragmentFrame,fragment,Home.TAG);
        ft.commit();






        // commented these 4 lines above to create it like the Home.java which is calling another fragment ... lets see

//        Home fragment= new Home();
//        AppCompatActivity appCompatActivity= new AppCompatActivity(); //Not sure about these two lines
//        FragmentManager fm = appCompatActivity.getSupportFragmentManager();
//        android.support.v4.app.FragmentTransaction ftz = fm.beginTransaction();
//        ftz.replace(R.id.fragmentFrame, fragment);
//        ftz.setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//        ftz.commit();



//
//        today();
//
//        Log.d("myTag", "Expandable List method started");
//        expandableListDataPopulation();
//        Log.d("myTag", "Expandable List method ended");



    }


    private void expandableListDataPopulation()
    {

        //expandableListDetail = ExpandableListDataPump.getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(getActivity(), expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getActivity(),
                        expandableListTitle.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getActivity(),
                        expandableListTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getActivity(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();
                return false;
            }});
    }

    private void today()
    {

        Calendar cal = Calendar.getInstance();
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

        String dayOfMonthStr = String.valueOf(dayOfMonth);

        Log.d("myTag","Today is " +dayOfMonthStr);
    }
    private void loadVolunteerOpportunities()
    {
        Log.d("myTag","Load Volunteer Oppportunities Method started");
        //This method will set the front end sections on activity_profile.xml page, that will depict the volunteer opportunities
        // The attributes that will be shown include Title, Description, Added By, Location of the person who added, Time,

       // RelativeLayout relativeLayout= (RelativeLayout)view.findViewById(R.id.profile_section); copy pasted above
        try {
            //TextView textView = new TextView(getActivity());
            //textView.setText("Dynamic Content added");

            today.setText("tHursday");

            btn.setText("Dynamically Added Button");
            linearLayoutLayout.addView(btn, lp);


            // relativeLayout.addView(textView);
            // Button btn= new Button(getActivity());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        Log.d("myTag","Load Volunteer Oppportunities Method ended" +linearLayoutLayout +"........\n");
    }


    private void handleError(Throwable error) {

        mProgressBar.setVisibility(View.GONE);

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

        if (getView() != null) {

            Snackbar.make(getView(),message,Snackbar.LENGTH_SHORT).show();
        }
    }

    private void goToRegister(){

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        RegisterFragment fragment = new RegisterFragment();
        ft.replace(R.id.fragmentFrame,fragment,RegisterFragment.TAG);
        ft.commit();
    }

    private void showDialog(){

        ResetPasswordDialog fragment = new ResetPasswordDialog();

        fragment.show(getFragmentManager(), ResetPasswordDialog.TAG);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }
}
