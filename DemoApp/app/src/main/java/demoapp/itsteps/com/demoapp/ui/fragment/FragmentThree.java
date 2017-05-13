package demoapp.itsteps.com.demoapp.ui.fragment;


import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
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

import java.util.List;

import butterknife.ButterKnife;
import demoapp.itsteps.com.db.adapters.NotesDatabaseAdapter;
import demoapp.itsteps.com.db.models.Note;
import demoapp.itsteps.com.demoapp.R;

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
        initViews();
        fab.setOnClickListener(this);
    }

    private void initViews() {
        new AsyncTask<Void, Void, List<Note>>() {

            @Override
            protected List<Note> doInBackground(Void... params) {
                NotesDatabaseAdapter adapter = new NotesDatabaseAdapter(getContext());
                return adapter.getNotes();
            }

            @Override
            protected void onPostExecute(List<Note> notes) {
                for (Note note : notes) {
                    addVisualNote(note.getNote());
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }

    private void addVisualNote(String string) {
        TextView txt = new TextView(getActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        txt.setMinHeight(getResources().getDimensionPixelOffset(R.dimen.clickable_cell_height));
        txt.setLayoutParams(lp);
        txt.setGravity(Gravity.CENTER);
        txt.setText(string);
        grpContainer.addView(txt);
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
                new AsyncTask<Void, Void, String>() {
                    public String noteText = input.getText().toString();

                    @Override
                    protected String doInBackground(Void... params) {
                        Note note = new Note(noteText);
                        NotesDatabaseAdapter adapter = new NotesDatabaseAdapter(getContext());
                        adapter.insert(note);
                        return noteText;
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        addVisualNote(s);
                    }
                }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });
        builder.create().show();
    }
}
