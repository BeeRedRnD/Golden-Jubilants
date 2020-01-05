package com.learn2crack.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.learn2crack.R;
import com.learn2crack.model.Response;
import com.learn2crack.model.User;
import com.learn2crack.network.NetworkUtil;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.learn2crack.utils.Validation.validateEmail;
import static com.learn2crack.utils.Validation.validateFields;

public class RegisterFragment extends Fragment {

    public static final String TAG = RegisterFragment.class.getSimpleName();

    private EditText mEtName;
    private EditText mEtEmail;
    private EditText mEtPassword;
    private Button   mBtRegister;
    private TextView mTvLogin;
    private TextInputLayout mTiName;
    private TextInputLayout mTiEmail;
    private TextInputLayout mTiPassword;
    private ProgressBar mProgressbar;


    private EditText mEtCounty;
    private EditText mEtSkills;

    private CheckBox cbProvider;
    private CheckBox cbReceiver;
    private CheckBox cbBoth;

    private RadioButton rbWeekly;
    private RadioButton rbFortnight;
    private RadioButton rbMonthly;



    //ArrayList<String> preferences= new Arraylist<String>(); //arraylist for adding the selection of whether user wants to be provider, receiver or both


    private CompositeSubscription mSubscriptions;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register,container,false);
        mSubscriptions = new CompositeSubscription();
        initViews(view);
        return view;
    }

    private void initViews(View v) {

        mEtName = (EditText) v.findViewById(R.id.et_name);
        mEtEmail = (EditText) v.findViewById(R.id.et_email);
        mEtPassword = (EditText) v.findViewById(R.id.et_password);
        mBtRegister = (Button) v.findViewById(R.id.btn_register);
        mTvLogin = (TextView) v.findViewById(R.id.tv_login);
        mTiName = (TextInputLayout) v.findViewById(R.id.ti_name);
        mTiEmail = (TextInputLayout) v.findViewById(R.id.ti_email);
        mTiPassword = (TextInputLayout) v.findViewById(R.id.ti_password);
        mProgressbar = (ProgressBar) v.findViewById(R.id.progress);

        mEtCounty= (EditText) v.findViewById(R.id.et_county);
        mEtSkills= (EditText) v.findViewById(R.id.et_list_skills);


        cbProvider= (CheckBox) v.findViewById(R.id.cb_provider);
        cbReceiver= (CheckBox) v.findViewById(R.id.cb_reciever);
        cbBoth= (CheckBox) v.findViewById(R.id.cb_both);

        rbWeekly= (RadioButton) v.findViewById(R.id.rb_weekly);
        rbFortnight= (RadioButton) v.findViewById(R.id.rb_fortnight);
        rbMonthly= (RadioButton) v.findViewById(R.id.rb_monthly);


        mBtRegister.setOnClickListener(view -> register());
        mTvLogin.setOnClickListener(view -> goToLogin());
    }


//    public void selectItem(View view)
//    {
//        boolean checked= ((CheckBox) view).isChecked();
//
//        switch(view.getId())
//        {
//            case R.id.cb_provider:
//                //preferences.add("Provider");
//                Log.d("myTag","Provider is checked");
//
//            case R.id.cb_reciever:
//                //preferences.add("Provider");
//                Log.d("myTag","Receiver is checked");
//
//            case R.id.cb_both:
//                //preferences.add("Provider");
//                Log.d("myTag","Both is checked");
//
//                break;
//        }
//    }

    private void register() {

        setError();

        String name = mEtName.getText().toString();
        String email = mEtEmail.getText().toString();
        String password = mEtPassword.getText().toString();

        String county = mEtCounty.getText().toString();
        String skills= mEtSkills.getText().toString();
        String frequency="";

        //Boolean values of selected/unselected checboxes
        boolean provider= cbProvider.isChecked();
        boolean receiver= cbReceiver.isChecked();
        boolean both= cbBoth.isChecked();


        ArrayList<String> preferenceList= new ArrayList<String>();
        if(cbProvider.isChecked())
            preferenceList.add("Provider");
        if(cbReceiver.isChecked())
            preferenceList.add("Receiver");
        if(cbBoth.isChecked())
            preferenceList.add("Both");

        //String[] prefListArr= new String[3];

        //for(int i=0; i<preferenceList.size();i++)
        //{
          //  prefListArr[i]= preferenceList.get(i);
        //}



        //Boolean values of selected/unselected radiobuttons
        boolean weekly= rbWeekly.isChecked();
        boolean fortnight= rbFortnight.isChecked();
        boolean monthly= rbMonthly.isChecked();

        //Log.d(TAG,+"\n"+email+"";
        Log.d("myTag",name+"\n"+email + " \n" +county +"\n" +skills + "\n Provider is "+provider + "\n Receiver is " +receiver + "\n Both is"+both + "\n " +
                "Radio Button weekly is " +weekly + "\n fortnight is " +fortnight + "\n monthly is "+monthly + "\n Size of arraylist is "+preferenceList.size() + "\n");

        //for(int i=0;i<prefListArr.length;i++)
        //Log.d("myTag"," lllllll "+prefListArr[i]);


        ///




        // Check which radio button was clicked
        if(rbWeekly.isChecked())
        {
            frequency= "Weekly";
            Log.d("myTag", ""+frequency+" OPTION IS SELECTED");
        }
        else if(rbFortnight.isChecked())
        {
            frequency= "Fortnight";
            Log.d("myTag", ""+frequency+ " OPTION IS SELECTED");
        }
        else if(rbMonthly.isChecked())
        {
            frequency= "Monthly";
            Log.d("myTag", ""+frequency+" OPTION IS SELECTED");
        }

        ////



        int err = 0;

        if (!validateFields(name)) {

            err++;
            mTiName.setError("Name should not be empty !");
        }

        if (!validateEmail(email)) {

            err++;
            mTiEmail.setError("Email should be valid !");
        }

        if (!validateFields(password)) {

            err++;
            mTiPassword.setError("Password should not be empty !");
        }

        if (err == 0) {

            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);

            user.setCounty(county);
            Log.d("myTag", ""+frequency+" OPTION IS SELECTED before saving into db"+user.getFrequency());
            user.setFrequency(frequency);
          //  user.setPreferencesList(prefListArr);

            user.setSkills(skills);
            mProgressbar.setVisibility(View.VISIBLE);
            registerProcess(user);

        } else {

            showSnackBarMessage("Enter Valid Details !");
        }
    }

    private void setError() {

        mTiName.setError(null);
        mTiEmail.setError(null);
        mTiPassword.setError(null);
    }

    private void registerProcess(User user) {

        mSubscriptions.add(NetworkUtil.getRetrofit().register(user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));
    }

    private void handleResponse(Response response) {

        mProgressbar.setVisibility(View.GONE);
        showSnackBarMessage(response.getMessage());
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

        if (getView() != null) {

            Snackbar.make(getView(),message,Snackbar.LENGTH_SHORT).show();
        }
    }

    private void goToLogin(){

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        LoginFragment fragment = new LoginFragment();
        ft.replace(R.id.fragmentFrame, fragment, LoginFragment.TAG);
        ft.commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }
}
