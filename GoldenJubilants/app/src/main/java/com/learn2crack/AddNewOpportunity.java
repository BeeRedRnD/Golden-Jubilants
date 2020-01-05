package com.learn2crack;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.learn2crack.fragments.SecondFragment;
import com.learn2crack.model.Opportunities;
import com.learn2crack.model.Response;
import com.learn2crack.network.NetworkUtil;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

//import android.support.v4.app.Fragment;

/**
 * Created by Bilal on 30/10/2017.
 */

public class AddNewOpportunity extends AppCompatActivity {


    private CompositeSubscription mSubscriptions;

    private EditText etTitle;
    private EditText etDescription;

    private Button mBtSave;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.add_new_opportunity);
//
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_opportunity);
       mSubscriptions = new CompositeSubscription(); //uncommented this due to a null object reference error coming
        initViews();
        //initSharedPreferences();
        //loadProfile();
    }

//    @Override
//    protected void onCreate(ViewGroup container, Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.add_new_opportunity);
//
//    }
//
//    private CompositeSubscription mSubscriptions;
//
//    @Nullable
//    //@Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
//        View view = inflater.inflate(R.layout.fragment_register,container,false);
//        mSubscriptions = new CompositeSubscription();
//        initViews(view);
//        return view;
//    }
//
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_profile);
////        mSubscriptions = new CompositeSubscription();
////        initViews();
////        initSharedPreferences();
////        loadProfile();
////    }
//
    private void initViews()
    {

        etTitle= (EditText) findViewById(R.id.et_title);
        etDescription= (EditText) findViewById(R.id.et_description);



        mBtSave= (Button) findViewById(R.id.btn_save);

        mBtSave.setOnClickListener(view -> save());

    }
//
//
    public void save()
    {
        ProfileActivity pa= new ProfileActivity();
        Log.d("myTag","Save button clicked!!!! and the title is " +etTitle.getText().toString()+ "....\n and the description is "+etDescription.getText().toString() + "and the user who aded is ^^^^"+pa.getEmail());

        String title= etTitle.getText().toString();
        String description= etDescription.getText().toString();

        Opportunities opportunities= new Opportunities();
        opportunities.setTitle(title);
        opportunities.setDescription(description);

        Log.d("myTag","Checking the values that are added in object"+opportunities.getTitle()+"\n "+opportunities.getDescription());

        addNewOpportunity(opportunities);


    }



    private void addNewOpportunity(Opportunities opportunity)
    {

        mSubscriptions.add(NetworkUtil.getRetrofit().register(opportunity)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));
    }

    private void handleResponse(Response response) {

        Log.d("myTag","New opportunity added successfuly");
        finish();

        //mProgressbar.setVisibility(View.GONE);
        //showSnackBarMessage(response.getMessage());

//        Intent intent = new Intent(this, ProfileActivity.class);
//        startActivity(intent);

        android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment fragmentt = new SecondFragment();
        ft.replace(R.id.add_new_opportunity,fragmentt,SecondFragment.TAG);
        ft.addToBackStack(null);
        ft.commit();
    }

    private void handleError(Throwable error) {

        //Intent intent = new Intent(this, ProfileActivity.class);
        //startActivity(intent);

        android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment fragmentt = new SecondFragment();
        ft.replace(R.id.add_new_opportunity,fragmentt,SecondFragment.TAG);
        ft.addToBackStack(null);
        ft.commit();

    Log.d("myTag", "Error occured on saving new opportunity \n" +error.getMessage());


    }




}
