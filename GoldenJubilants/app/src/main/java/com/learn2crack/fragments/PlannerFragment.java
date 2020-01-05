package com.learn2crack.fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.learn2crack.R;

public class PlannerFragment extends Fragment {
    public static final String TAG = PlannerFragment.class.getSimpleName();



    private ImageView addNewItem;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_planner,container,false);
        initViews(view);
        return view;
    }

    private void initViews(View v)
    {
        addNewItem= (ImageView) v.findViewById(R.id.plus_icon);
        addNewItem.setOnClickListener(view -> opeAddNewItemDialog());

    }

    private void opeAddNewItemDialog()
    {
        Log.d("myTag","Add New item dialog started"+R.string.hello_blank_fragment);

//        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getView().getContext(),R.style.Dialog);
//        LayoutInflater inflater = getActivity().getLayoutInflater();
//        final View dialogView = inflater.inflate(R.layout.item_todo, null);
//        dialogBuilder.setView(dialogView);
//
//
//
//        dialogBuilder.setTitle("Custom dialog");
//        dialogBuilder.setMessage("Enter text below");
//        dialogBuilder.show();

        // this link contains info about how to set the border and layout of relativelayout
        // https://android--code.blogspot.co.uk/2015/05/android-relativelayout-border.html
        final Dialog dialog= new Dialog(getView().getContext());
        // dialog.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.custom_title);

        //dialog.setTitle(R.layout.custom_title);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setTitle(R.string.hello_blank_fragment);
        dialog.setContentView(R.layout.item_todo);
        dialog.show();

        Button dialogButton = (Button) dialog.findViewById(R.id.task_delete);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("myTag","Dialog opened");
                // http://android-er.blogspot.co.uk/2013/05/add-and-remove-view-dynamically.html

                LayoutInflater layoutInflater = (LayoutInflater) getView().getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.row, null);
                LinearLayout linearLayout= (LinearLayout) getView().findViewById(R.id.container);

                EditText til= (EditText) dialog.findViewById(R.id.et_descriptiontask);
                //til.getEditText();

                TextView task= (TextView) addView.findViewById(R.id.textout);
                task.setText(til.getText());

                ;

                Button btnRemove= (Button)addView.findViewById(R.id.remove);
                btnRemove.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((LinearLayout)addView.getParent()).removeView(addView);
                    }
                });
                linearLayout.addView(addView);

                dialog.dismiss();

                Log.d("myTag","Dialog closed");
            }
        });

       Log.d("myTag","Add New item dialog ended" +R.layout.custom_title);
    }

}
