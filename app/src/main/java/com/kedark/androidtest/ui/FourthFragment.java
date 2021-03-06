package com.kedark.androidtest.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kedark.androidtest.R;

/**
 * Created by Shripad Raj on 3/11/2016.
 */
public class FourthFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fourth_frag, container, false);

        TextView tv = (TextView) v.findViewById(R.id.tvFragFourth);
        tv.setText(getArguments().getString("msg"));

        return v;
    }

    public static FourthFragment newInstance(String text) {

        FourthFragment f = new FourthFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
}