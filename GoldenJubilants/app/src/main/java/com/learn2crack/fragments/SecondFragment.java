package com.learn2crack.fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.learn2crack.CustomExpandableListAdapter;
import com.learn2crack.R;
import com.learn2crack.model.Opportunities;
import com.learn2crack.model.Response;
import com.learn2crack.model.User;
import com.learn2crack.network.NetworkUtil;
import com.learn2crack.utils.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

//import android.support.v4.app.Fragment;

public class SecondFragment extends Fragment {

    private View view3;


    private List<String> titleList = new ArrayList<>(); //title of expandable list
    private List<String> descList = new ArrayList<>(); //description of expandable list
    private List<String> addedbyList = new ArrayList<>(); //added by inside description of expanded list
    private List<String> locationList = new ArrayList<>(); //location inside description of expanded list


    public SecondFragment() {
// Required empty public constructor

        Log.d("myTag", "CONSTRUCTOR 2ND FRAGMENT STARTED" + view3);
//        LayoutInflater layoutInflater= null;
//        ViewGroup container= (ViewGroup) view3.findViewById(R.id.simpleFrameLayout);
//        Bundle savedInstanceState;
//        View view = layoutInflater.inflate(R.layout.fragment_second, container, false);

        Log.d("myTag", "CONSTRUCTOR 2ND FRAGMENT ENDED");

    }

    public static final String TAG = SecondFragment.class.getSimpleName();


    private TextView volOppTitle;

    private Button btnAddNew;


    private TextView volTitle;

    //This tutorial was used for creating ExpandableList View [https://www.journaldev.com/9942/android-expandablelistview-example-tutorial]

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;


    private ExpandableListView expLv;


    private CompositeSubscription mSubscriptions;
    private SharedPreferences mSharedPreferences;
    private String mToken;
    private String mEmail;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String email;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    private String location;

    private String title;
    private String description;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("myTag", "ppppppppppp");
        super.onCreate(savedInstanceState);

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("myTag", "QQQQQQQQQQQQQQQQQQ");

        // loadProfile();
        View view = inflater.inflate(R.layout.fragment_second, container, false);


        mSubscriptions = new CompositeSubscription();

        //initSharedPreferences();

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        mToken = mSharedPreferences.getString(Constants.TOKEN, "");
        mEmail = mSharedPreferences.getString(Constants.EMAIL, "");
        initViews(view);
        loadProfile();

        loadOpportunities();



        return view;


    }

    private void initSharedPreferences() {

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        mToken = mSharedPreferences.getString(Constants.TOKEN, "");
        mEmail = mSharedPreferences.getString(Constants.EMAIL, "");
    }

    private void loadProfile() {

        mSubscriptions.add(NetworkUtil.getRetrofit(mToken).getProfile(mEmail)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));
    }


    public void loadOpportunities() {
        Log.d("myTag", "Load Opportunities Method started"+getLocation() + " \t" +mToken);
        mSubscriptions.add(NetworkUtil.getRetrofit(mToken).getOpportunities(mEmail)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse1, this::handleError1));

        Log.d("myTag", "Load Opportunities Method ended");
    }


//    public void loadOpportunities()
//    {
//        Log.d("myTag", "Load Opportunities Method started");
//        mSubscriptions.addAll(NetworkUtil.getRetrofit(mToken).getOpportunities(mEmail)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .toSortedList(this::handleResponse3);
//
//
//
//        Log.d("myTag", "Load Opportunities Method ended");
//    }


//    private  void handleResponse3(List<Opportunities> opportunities)
//    {
//
//
//        Log.d("myTag", "IOTA"+opportunities.getTitle());
//    }

    private void handleResponse1(List<Opportunities> opportunities) {

        Log.d("myTag", "GETTING LIST" + opportunities.size() + "" +getLocation());
        volTitle = (TextView) getView().findViewById(R.id.or_text);


        for (int i = 0; i < opportunities.size(); i++) {
            //if same location of the user logged in than show opportunity
               if(opportunities.get(i).getLocation()!=null && opportunities.get(i).getLocation().equalsIgnoreCase(getLocation())) {
                   titleList.add(opportunities.get(i).getTitle());
                   descList.add(opportunities.get(i).getDescription());
                   addedbyList.add(opportunities.get(i).getAddedby());
                   locationList.add(opportunities.get(i).getLocation());
                   Log.d("myTag", "TTTT" + opportunities.get(i).getAddedby() + "  " + opportunities.get(i).getLocation());
               }

        }

        setTitle(opportunities.get(0).getTitle());
        setDescription(opportunities.get(0).getDescription());
        Log.d("myTag", "Handle response 1 method started" + getDescription() + "\t and title is " + getTitle());
        // So, finally I am able to fetch records from Opportunities table based on email


        ////DDDDD



        ///////
        expLv = (ExpandableListView) getView().findViewById(R.id.expandableListView);
        Log.d("myTag", "Expandable list view is ...... " + expLv);


        //This line was missing
        expandableListView = (ExpandableListView) getView().findViewById(R.id.expandableListView);

        //ExpandableListDataPump epdp= new ExpandableListDataPump(); //just to load the things inside the constructor
        expandableListDetail = getData();
        Log.d("myTag", "Number of items in expandable list detail are" + expandableListDetail.size()); // working till here

        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        Log.d("myTag", "First Key is:" + expandableListTitle.get(0).toString()); // working till here

        expandableListAdapter = new CustomExpandableListAdapter(getView().getContext(), expandableListTitle, expandableListDetail);
        Log.d("myTag", "ExpandableListAdapter is " + expandableListAdapter.isEmpty());
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getView().getContext(),
                        expandableListTitle.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getView().getContext(),
                        expandableListTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Log.d("myTag", "CHILD CLICKED YUPIEE" + childPosition);
                if (childPosition == 1) {
                    Log.d("myTag", "Location Clicked");

                    final Dialog dialog = new Dialog(v.getContext());
                    // dialog.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.custom_title);

                    //dialog.setTitle(R.layout.custom_title);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setTitle(R.string.hello_blank_fragment);
                    dialog.setContentView(R.layout.contact);
                    dialog.setCancelable(true);
                    dialog.show();
                    Window window = dialog.getWindow();
                    window.setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


                    ImageView btnCancel = (ImageView) dialog.findViewById(R.id.btn_cancel);
                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });


                    ImageView btnCall = (ImageView) dialog.findViewById(R.id.btn_call);
                    btnCall.setOnClickListener(view -> callPerson());

                    ImageView btnMessage = (ImageView) dialog.findViewById(R.id.btn_message);
                    btnMessage.setOnClickListener(view -> messagePerson());

                    ImageView btnEmail = (ImageView) dialog.findViewById(R.id.btn_email);
                    btnEmail.setOnClickListener(view -> emailPerson());


                }


                Toast.makeText(
                        v.getContext(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();
                return false;
            }
        });


        /////DDDD

        Log.d("myTag", "Handle response 1 method ended");
    }

    private void handleError1(Throwable error) {
        Log.d("myTag", "Error occured in fetching Opportunities bilal");

    }


    private void handleResponse(User user) {

        Log.d("myTag", "@@@@@@@@@@@@@@@@Testing User attributes inside Second Fragment " + user.getName() + "and email is" + user.getEmail() + " "+user.getCounty()); //ucommented the SharedPrferences.Editor lines to get this data

        name = user.getName();
        email = user.getEmail();
       // location = user.getCounty();
        setLocation(user.getCounty());


        ///////
        expLv = (ExpandableListView) getView().findViewById(R.id.expandableListView);
        Log.d("myTag", "Expandable list view is ...... " + expLv);


        //This line was missing
        expandableListView = (ExpandableListView) getView().findViewById(R.id.expandableListView);

        //ExpandableListDataPump epdp= new ExpandableListDataPump(); //just to load the things inside the constructor
        expandableListDetail = getData();
        Log.d("myTag", "Number of items in expandable list detail are" + expandableListDetail.size()); // working till here

        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        Log.d("myTag", "First Key is:" + expandableListTitle.get(0).toString()); // working till here

        expandableListAdapter = new CustomExpandableListAdapter(getView().getContext(), expandableListTitle, expandableListDetail);
        Log.d("myTag", "ExpandableListAdapter is " + expandableListAdapter.isEmpty());
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getView().getContext(),
                        expandableListTitle.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getView().getContext(),
                        expandableListTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Log.d("myTag", "CHILD CLICKED YUPIEE" + childPosition);
                if (childPosition == 1) {
                    Log.d("myTag", "Location Clicked");

                    final Dialog dialog = new Dialog(v.getContext());
                    // dialog.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.custom_title);

                    //dialog.setTitle(R.layout.custom_title);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setTitle(R.string.hello_blank_fragment);
                    dialog.setContentView(R.layout.contact);
                    dialog.setCancelable(true);
                    dialog.show();
                    Window window = dialog.getWindow();
                    window.setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


                    ImageView btnCancel = (ImageView) dialog.findViewById(R.id.btn_cancel);
                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });


                    ImageView btnCall = (ImageView) dialog.findViewById(R.id.btn_call);
                    btnCall.setOnClickListener(view -> callPerson());

                    ImageView btnMessage = (ImageView) dialog.findViewById(R.id.btn_message);
                    btnMessage.setOnClickListener(view -> messagePerson());

                    ImageView btnEmail = (ImageView) dialog.findViewById(R.id.btn_email);
                    btnEmail.setOnClickListener(view -> emailPerson());


                }


                Toast.makeText(
                        v.getContext(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();
                return false;
            }
        });

    }

    private void handleError(Throwable error) {

        //mProgressbar.setVisibility(View.GONE);

        if (error instanceof HttpException) {

            Gson gson = new GsonBuilder().create();

            try {

                String errorBody = ((HttpException) error).response().errorBody().string();
                Response response = gson.fromJson(errorBody, Response.class);
                showSnackBarMessage(response.getMessage());

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            showSnackBarMessage("Network Error !");
        }
    }

    private void showSnackBarMessage(String message) {

        //Snackbar.make(findViewById(R.id.activity_profile),message,Snackbar.LENGTH_SHORT).show();

    }


    public HashMap<String, List<String>> getData() {


        //The data of User and Opportunites are available via setting them to variables in this class

        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

//       List<String> cricket = new ArrayList<String>();
//       cricket.add("India");
//       cricket.add("Pakistan");
//       cricket.add("Australia");
//       cricket.add("England");
//       cricket.add("South Africa");
//
//       List<String> football = new ArrayList<String>();
//       football.add("Brazil");
//       football.add("Spain");
//       football.add("Germany");
//       football.add("Netherlands");
//       football.add("Italy");
//
//       List<String> basketball = new ArrayList<String>();
//       basketball.add("United States");
//       basketball.add("Spain");
//       basketball.add("Argentina");
//       basketball.add("France");
//       basketball.add("Russia");
//
//
//       List<String> volopportunity = new ArrayList<String>();
//       volopportunity.add("Bilal");
//       volopportunity.add("bilal_uaar@yahoo.com");
//       volopportunity.add(name);
//       volopportunity.add(email);
//       volopportunity.add(getTitle());
//       volopportunity.add(getDescription());

        for (int i = 0; i < titleList.size(); i++) {

            List<String> volopportunity = new ArrayList<String>();
            volopportunity.add("Description: " + descList.get(i) + " [ " + addedbyList.get(i) + " from " + locationList.get(i) + " ]");
            //volopportunity.add("John from Woodhaven");
            volopportunity.add("CONTACT ME!");
            expandableListDetail.put(titleList.get(i), volopportunity);

        }

//       List<String> volopportunnity1= new ArrayList<String>();
//
//       List<String> volopportunity2= new ArrayList<String>();
//
//       volopportunnity1.add(descList.get(0)); //Description of Opportunity
//       volopportunnity1.add("<=== Contact Information ==>");
//       volopportunnity1.add("Name : "+name); //Added By
//       volopportunnity1.add("Area : " +location);//Area
//
//       volopportunnity1.add("Phone Number: 090078601"); //Contact Number
//       volopportunnity1.add("Email Address : "+email); //Contact Email
//
//       volopportunity2.add(descList.get(1));
//
//
//       expandableListDetail.put(getTitle(), volopportunnity1); //add title 1 here
//       expandableListDetail.put("DOOSRI", volopportunity2);
//
//
////       expandableListDetail.put("FOOTBALLLLLL TEAMS", football);
////       expandableListDetail.put("BASKETBALLLLLL TEAMS", basketball);
////
////
////       expandableListDetail.put("GARDNDER NEEDEDDDDD", volopportunity);


        return expandableListDetail;
    }


    private void initViews(View v) {


//        LinearLayout llvolopcontainer= (LinearLayout)v.findViewById(R.id.voloppcontainer);
//
//        Button myButton=null;
//        TextView textView= null;
//        for(int i=0;i<3;i++)
//        {
//
//            myButton = new Button(getActivity());
//            myButton.setText("hI"+i);
//            myButton.setId(i);
//
//            textView= new TextView(getActivity());
//            textView.setText("TextView"+i);
//            textView.setId(i);
//
//            //llvolopcontainer.addView(textView);
//            llvolopcontainer.addView(myButton);
//
//        }
//
//        Log.d("myTag","CHILDREN ARE"+llvolopcontainer.getChildCount());
//
//
//        View.OnClickListener[] onClickListeners= new View.OnClickListener[];
//        myButton.setOnClickListener(onClickListeners[0] = new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//        onClickListeners[0] = new View.OnClickListener(){
//            @Override
//            public void onClick(View view)
//            {
//
//            }
//        };
//
//
//
//
//        for(int i=0; i<llvolopcontainer.getChildCount();i++) {
//            llvolopcontainer.getChildAt(i).findViewById(i).setOnClickListener(onClickListeners[i]);
//            llvolopcontainer.getChildAt(i).findViewById(i).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Log.d("myTag", "SUB k lie" + view.getId());
//
//
////                    if (view.getId() == 0)
////                        Log.d("myTag", "First Button clicked");
////                    if (view.getId() == 1)
////                        Log.d("myTag", "Second Button clicked");
////                    if (view.getId() == 2)
////                        Log.d("myTag", "Third Button clicked");
//
//                }
//            });
//        }
//
//
//
//
//
////            llvolopcontainer.getChildAt(k)
////                    .findViewById(R.id.title_vol_opp)
////                    .setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View vx) {
////
////                   // TextView temptextview= (TextView) vx.findViewById(R.id.title_vol_opp);
////                    Log.d("myTag", "Hare Hare");
////
////
////                }
////            });
//
//
//
//        //Log.d("myTag","")
////        List<LinearLayout> lstll= new ArrayList<LinearLayout>();
////        for(int i=0;i<llvolopcontainer.getChildCount();i++)
////        {
////            lstll.add((LinearLayout)llvolopcontainer.getChildAt(i));
////
////        }
////        Log.d("myTag", "Chawal he oye"+lstll.size());
////
////        lstll.get(0).findViewById(R.id.title_vol_opp).setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                Log.d("myTag", "Chawal he oye");
////            }
////        });
//
//
//        //Clicking All Titles loop ended
//
//
//
////        for(int i=0;i<3;i++)
////        {
////            volOppTitle= (TextView) llvolopcontainer.getChildAt(0).findViewById(R.id.title_vol_opp);
////
////        }
//
//
//        volOppTitle= (TextView) llvolopcontainer.getChildAt(1).findViewById(R.id.title_vol_opp); /// second element .
//
////        llvolopcontainer.getChildAt(1).findViewById(R.id.title_vol_opp).setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                Log.d("myTag", "Hare Hare");
////            }
////        });
//
//        //volOppTitle= (TextView) v.findViewById(R.id.title_vol_opp);
//        Log.d("myTag", "Haal e dil"+volOppTitle);

        //volOppTitle.setOnClickListener(view -> openDetailsOfVolunteerOpportunity());
//            LinearLayout llvolopcontainer= (LinearLayout)v.findViewById(R.id.voloppcontainer);
//            //LinearLayout llvolopcontainer2= (LinearLayout)v.findViewById(R.id.voloppcontainer);
//            LayoutInflater layoutInflater = (LayoutInflater) v.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            final View addView = layoutInflater.inflate(R.layout.item_volunteer_opportunity, null);
//            llvolopcontainer.addView(addView);
//           // llvolopcontainer2.addView(addView);
//


        ////@@@@@


        /////Linear layout containing volunteer opportunity ends

        // Copied all the code from initViews to handleReponse method above ...

        btnAddNew = (Button) v.findViewById(R.id.btn_add_new);
        btnAddNew.setOnClickListener(view -> openNewOpportunityWindow());


        //Images of buttons of calling, texting and email and their respective events
//        TableLayout tblLayout = (TableLayout)v.findViewById(R.id.table_icons);
//        TableRow row = (TableRow)tblLayout.getChildAt(0); // Here get row id depending on number of row
//        ImageView callImg = (ImageView) row.getChildAt(0); // get child index on particular row
//        ImageView msgImg= (ImageView) row.getChildAt(1); //get message image
//        ImageView emailImg= (ImageView) row.getChildAt(2);
//
//
//        Log.d("myTag", "Text/ID of button is YYYYYY" +callImg);
//        callImg.setOnClickListener(view-> callPerson());
//        msgImg.setOnClickListener(view -> messagePerson());
//        emailImg.setOnClickListener(view->emailPerson());


    }


    private void openDetailsOfVolunteerOpportunity() {
        Log.d("myTag", "Open Details of Volunteer Oppportunity method started");

        Log.d("myTag", "Open Details of Volunteer Oppportunity method ended");
    }

    private void callPerson() {
        Log.d("myTag", "Call Person method started");
        //Just get the number of the person you want to call
        //It is taking the application to dialpad on which it is already dialled
        // https://stackoverflow.com/questions/5403308/make-a-phone-call-click-on-a-button
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:123456789"));
        startActivity(callIntent);

        Log.d("myTag", "Call Person method ended");
    }

    private void messagePerson() {
        Log.d("myTag", "Message Person method started");
        // https://stackoverflow.com/questions/10607361/android-send-sms-automatically-on-button-click
        String smsNumber = "0123456789";
        String smsText = "I need your time today please";

        Uri uri = Uri.parse("smsto:" + smsNumber);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", smsText);
        startActivity(intent);


        Log.d("myTag", " Message Person method ended");
    }

    private void emailPerson() {
        Log.d("myTag", "Email Person method started");
        // https://stackoverflow.com/questions/21720640/sending-email-from-android-app-when-click-on-button

        String subject = "Request for your time";
        String message = "I need your time from 6 to 8";

        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "bilal@email.com", null));
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(Intent.createChooser(intent, "Choose an Email client :"));

        Log.d("myTag", "Email Person method ended");
    }


    private void openNewOpportunityWindow() {
        //Add new activity dialog opened

        Log.d("myTag", "Open new opportnity button clicked KKKKKKKK");
//        Intent intent = new Intent(getActivity(), AddNewOpportunity.class);
//        startActivity(intent);


        final Dialog dialog = new Dialog(getView().getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setTitle(R.string.hello_blank_fragment);
        dialog.setContentView(R.layout.add_new_opportunity);
        dialog.setCancelable(true);
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        Button btnSave = (Button) dialog.findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Add the elements in the opportunities table
                EditText etTitle = (EditText) dialog.findViewById(R.id.et_title); //access fields inside dialog
                EditText etDescription = (EditText) dialog.findViewById(R.id.et_description);

                Log.d("myTag", "THE VALUES ENTERED ARE " + etTitle.getText() + " " + etDescription.getText() + " and the logged in user is" + name + " " + location);


                //opportunity title, description, user who entered name and location are available here, add to db now
                Opportunities opportunities = new Opportunities();
                opportunities.setTitle("" + etTitle.getText());
                opportunities.setDescription("" + etDescription.getText());
                opportunities.setAddedby(name);
                opportunities.setLocation(location);

                addNewOpportunity(opportunities);


                dialog.dismiss();
            }
        });

    }


    private void addNewOpportunity(Opportunities opportunity) {
        mSubscriptions.add(NetworkUtil.getRetrofit().register(opportunity)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponseO, this::handleErrorO));
    }

    private void handleResponseO(Response response)
    {

        Log.d("myTag", "New opportunity added successfuly");
    }
    private void handleErrorO(Throwable error)
    {
        Log.d("myTag", "Unable to save new opportunity");
    }





}