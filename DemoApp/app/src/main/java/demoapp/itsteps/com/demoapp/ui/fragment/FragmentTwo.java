package demoapp.itsteps.com.demoapp.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.Set;

import demoapp.itsteps.com.demoapp.R;
import demoapp.itsteps.com.utils.Preferences;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTwo extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =   inflater.inflate(R.layout.fragment_fragment_two, container, false);
        return view;
    }


}
