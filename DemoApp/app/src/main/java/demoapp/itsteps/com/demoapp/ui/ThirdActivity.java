package demoapp.itsteps.com.demoapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import butterknife.Bind;
import butterknife.ButterKnife;
import demoapp.itsteps.com.adapters.UsersListAdapter;
import demoapp.itsteps.com.callbacks.Callback;
import demoapp.itsteps.com.callbacks.OnUserClickedCallback;
import demoapp.itsteps.com.demoapp.R;
import demoapp.itsteps.com.models.User;
import demoapp.itsteps.com.models.UsersResponse;
import demoapp.itsteps.com.webservices.Wrapper;

public class ThirdActivity extends AppCompatActivity {

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    UsersListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        ButterKnife.bind(this);
        loadUsers();
    }


    private void loadUsers() {
        Wrapper.getInstance(ThirdActivity.this).getUserFollowingList(new Callback<UsersResponse>() {
            @Override
            public void onSuccess(UsersResponse data) {
                initRecyclerView(data.getUsers());
            }

            @Override
            public void onError(String error) {
                Toast.makeText(ThirdActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * Init the selectable followers list recycler view
     */
    private void initRecyclerView(List<User> users) {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(ThirdActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView
                .setLayoutManager(layoutManager);
        //init adapter
        adapter = new UsersListAdapter(ThirdActivity.this, users, new OnUserClickedCallback() {
            @Override
            public void onUserSelected(User user) {
                Intent intent = new Intent(ThirdActivity.this, ViewProfileActivity.class);
                intent.putExtra(ViewProfileActivity.EXTRA_USER_PROFILE_DATA, user);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


//    private List<User> generateListOfUsers() {
//        List<User> list = new ArrayList<>();
//        String[] userName = {"Ivan Petrov", "Georgi Dimitrov", "Kiril Iliev"};
//        String[] userAddres = {"this is the address of the user 1", "this is the address of the user 2", "this is the address of the user 3"};
//        String[] userAvatars = {"https://media.licdn.com/mpr/mpr/AAEAAQAAAAAAAALbAAAAJDI3MDBhNTRkLTY0YTktNDc1ZS1hODFmLWExYmFlZGYyN2FjMg.jpg",
//                "http://top-10-list.org/wp-content/uploads/2013/04/Rowan-Atkinson.jpg", "http://cdn.redmondpie.com/wp-content/uploads/2011/07/zuckerberg.jpg"};
//        for (int i = 0; i <= 100; i++) {
//            int randomNum = ThreadLocalRandom.current().nextInt(0, 3);
//            User user = new User(userName[randomNum], userAddres[randomNum], userAvatars[randomNum]);
//            list.add(user);
//        }
//        return list;
//    }

}
