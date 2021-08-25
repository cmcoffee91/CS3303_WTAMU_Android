package com.cscourses.cc916647.cscourses;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by cmcoffee91 on 3/17/18.
 */

public class CourseFragment extends ListFragment {

    boolean mDualPane;
    int position = 0;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // create an array list of courses names
        ArrayAdapter<String> connectArrayToListView = new
                ArrayAdapter<String>(getActivity(), R.layout.row_label,Courses.CourseNames );
        setListAdapter(connectArrayToListView);
        //check to see if we have the a frame layout to add
        // details of the courses
        View detailsFrame = getActivity().findViewById(R.id.details);
        mDualPane = (detailsFrame != null) && (detailsFrame.getVisibility() == View.VISIBLE);
        // get the clicked position value in the list view
        if(savedInstanceState != null)
            position = savedInstanceState.getInt("curChoice", 0);
        // if the view instance exists and is visible
        if(mDualPane){
            // allow only one selection
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            getListView().setVerticalScrollBarEnabled(true);
            showDetails(position);
        }
        // screen is not wide enough
        else{
            getListView().setChoiceMode((ListView.CHOICE_MODE_SINGLE));
            getListView().setItemChecked(position,true);
            getListView().setVerticalScrollBarEnabled(true);
        }
    }
    // if a value for position is known we need to pass
    // key, value pair to the DetailFragment
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", position);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //super.onListItemClick(l, v, position, id);
        showDetails(position);
    }

    public  void showDetails(int index){
        position = index;
        if(mDualPane) {
            // proceed to display both fragment in the same activity
            getListView().setItemChecked(index, true);
            CourseDetails detailsFragment = (CourseDetails) getFragmentManager().
                    findFragmentById(R.id.details);
            // if dual mode or now switched to dual mode
            // create an instance of newInstance method
            if (detailsFragment == null || detailsFragment.getShowIndex() != index) {
                detailsFragment = CourseDetails.newInstance(index);
                // make a new fragment and display in the same UI
                // by calling the its onCreateView() method
                FragmentTransaction ft = getFragmentManager().
                        beginTransaction();
                ft.replace(R.id.details, detailsFragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        }
        else {
            // otherwise we will launch a new activity to display the selection
            Intent intent = new Intent();
            intent.setClass(getActivity(), CourseActivity.class);
            intent.putExtra("index", index);
            startActivity(intent);
        }

    }

}
