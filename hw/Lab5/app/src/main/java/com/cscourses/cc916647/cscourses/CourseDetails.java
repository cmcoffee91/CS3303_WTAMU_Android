package com.cscourses.cc916647.cscourses;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;


/**
 * Created by cmcoffee91 on 3/17/18.
 */

public class CourseDetails extends Fragment {

    public static CourseDetails newInstance(int index){
        CourseDetails detailsFragment = new CourseDetails();
        Bundle args = new Bundle();
        args.putInt("index", index);
        detailsFragment.setArguments(args);
        return detailsFragment;
    }
    // extact the value of index
    public int getShowIndex(){
        return getArguments().getInt("index",0);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //return a ScrollView instance and which will display the selected item detail
        ScrollView scrollView = new ScrollView(getActivity());
        TextView text = new TextView(getActivity());
        text.setTextSize(15);
        text.setBackgroundColor(Color.BLUE);
        text.setTextColor(Color.WHITE);
        int padding = 20;
        scrollView.setPadding(padding,padding,padding,padding);
        int textPadding = 10;
        text.setPadding(padding,textPadding,padding,textPadding);
        scrollView.addView(text);
        // use the index to display courses info
        text.setText(Courses.CourseInfo[getShowIndex()]);
        return scrollView;

    }
}
