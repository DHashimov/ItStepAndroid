package demoapp.itsteps.com.demoapp.ui.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import butterknife.ButterKnife;
import demoapp.itsteps.com.demoapp.R;
import demoapp.itsteps.com.utils.Preferences;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentThree extends Fragment implements View.OnClickListener {

    LinearLayout grpContainer;
    FloatingActionButton fab;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_three, container, false);
        grpContainer = (LinearLayout) view.findViewById(R.id.container);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        fab.setOnClickListener(this);
    }

    private void initViews(View view) {
        Set<String> stringSet = Preferences.getInstance(getActivity()).getStringSet();
        for (String string : stringSet) {
            addVisualNote(string);
        }

    }

    private void addVisualNote(final String string) {
        final TextView txt = new TextView(getActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        txt.setMinHeight(getResources().getDimensionPixelOffset(R.dimen.clickable_cell_height));
        txt.setLayoutParams(lp);
        txt.setGravity(Gravity.CENTER);
        txt.setText(string);
        txt.setOnClickListener(getListener(string, txt));
        grpContainer.addView(txt);
    }

    @NonNull
    private View.OnClickListener getListener(final String string, final TextView txt) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Edit Note");
                final EditText input = new EditText(getActivity());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        getResources().getDimensionPixelOffset(R.dimen.clickable_cell_height));
                input.setLayoutParams(lp);
                input.setText(string);
                builder.setView(input);
                builder.setPositiveButton("Edit", getPositiveListener(input, string, txt));
                builder.setNegativeButton("Delete", getNegativeListener(string, txt));
                builder.create().show();
            }

        };
    }

    @NonNull
    private DialogInterface.OnClickListener getNegativeListener(final String string, final TextView txt) {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Iterator<String> iterator = getStringIterator();
                Set<String> newSet = new HashSet<String>();
                while (iterator.hasNext()){
                    String text = iterator.next();
                    if(text.equals(string)){
                    }else {
                        newSet.add(text);
                    }
                }
                grpContainer.removeView(txt);
                Preferences.getInstance(getActivity()).setStringSet(newSet);
            }
        };
    }

    @NonNull
    private DialogInterface.OnClickListener getPositiveListener(final EditText input, final String string, final TextView txt) {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Iterator<String> iterator = getStringIterator();
                Set<String> newSet = new HashSet<String>();
                while (iterator.hasNext()){
                    String text = iterator.next();
                    if(text.equals(string)){
                        newSet.add(input.getText().toString());
                    }else {
                        newSet.add(text);
                    }
                }
                grpContainer.removeView(txt);
                addVisualNote(input.getText().toString());
                Preferences.getInstance(getActivity()).setStringSet(newSet);
            }
        };
    }

    @NonNull
    private Iterator<String> getStringIterator() {
        Set<String> notes = Preferences.getInstance(getActivity()).getStringSet();
        return notes.iterator();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.add_note_title));
        final EditText input = new EditText(getActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                getResources().getDimensionPixelOffset(R.dimen.clickable_cell_height));
        input.setLayoutParams(lp);
        builder.setView(input);
        builder.setPositiveButton(getString(R.string.add), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Set<String> notes = Preferences.getInstance(getActivity()).getStringSet();
                notes.add(input.getText().toString());
                Preferences.getInstance(getActivity()).setStringSet(notes);
                addVisualNote(input.getText().toString());
            }
        });
        builder.create().show();
    }
}
