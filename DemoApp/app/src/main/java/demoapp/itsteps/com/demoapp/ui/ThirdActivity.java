package demoapp.itsteps.com.demoapp.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import demoapp.itsteps.com.adapters.UsersListAdapter;
import demoapp.itsteps.com.callbacks.Callback;
import demoapp.itsteps.com.callbacks.CheckForUpdateCallback;
import demoapp.itsteps.com.callbacks.OnUserClickedCallback;
import demoapp.itsteps.com.db.DatabaseInstance;
import demoapp.itsteps.com.db.adapters.UsersDatabaseAdapter;
import demoapp.itsteps.com.db.models.DbUser;
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
        checkForUpdatesAndValidate();
    }

    private void checkForUpdatesAndValidate(){
           Wrapper.getInstance(ThirdActivity.this).isUserListChanged(new CheckForUpdateCallback<Void>() {
               @Override
               public void doValidation(boolean validate) {
                     if(validate){
                         Toast.makeText(ThirdActivity.this, "Recreate Table", Toast.LENGTH_SHORT).show();
                         DatabaseInstance.getInstance(ThirdActivity.this).dropTables(ThirdActivity.this);
                         loadUsers();
                     }else {
                         Toast.makeText(ThirdActivity.this, "Load from db", Toast.LENGTH_SHORT).show();
                         loadUsersFromDB();
                     }
               }

               @Override
               public void onSuccess(Void data) {

               }

               @Override
               public void onError(String error) {
                   Toast.makeText(ThirdActivity.this, error, Toast.LENGTH_SHORT).show();
               }
           });
    }


    private void loadUsersFromDB() {
        new AsyncTask<Void, Void, List<User>>() {
            @Override
            protected List<User> doInBackground(Void... params) {
                UsersDatabaseAdapter adapter = new UsersDatabaseAdapter(ThirdActivity.this);
                List<User> users = null;
                List<DbUser> dbUsers = adapter.getUsers();
                if (dbUsers != null) {
                    users = new ArrayList<>();
                    for (DbUser model : dbUsers) {
                        User user = new User();
                        user.setUserName(model.getUserName());
                        user.setUserAddress(model.getAddress());
                        user.setUserAvatarUrl(model.getAvatarURL());
                        users.add(user);
                    }
                }
                return users;
            }

            @Override
            protected void onPostExecute(List<User> users) {
                super.onPostExecute(users);
                  if(users != null && users.size() >0) {
                      Toast.makeText(ThirdActivity.this, "Init Recycler View From DB", Toast.LENGTH_SHORT).show();
                      initRecyclerView(users);
                  }else {
                      Toast.makeText(ThirdActivity.this, "Call Web Service", Toast.LENGTH_SHORT).show();
                      loadUsers();
                  }

            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }


    private void loadUsers() {
        Wrapper.getInstance(ThirdActivity.this).getUsersList(new Callback<UsersResponse>() {
            @Override
            public void onSuccess(final UsersResponse data) {
                new AsyncTask<Void, Void, List<User>>() {
                    @Override
                    protected List<User> doInBackground(Void... params) {
                        UsersDatabaseAdapter adapter = new UsersDatabaseAdapter(ThirdActivity.this);
                        for (User user : data.getUsers()) {
                            DbUser dbUser = new DbUser(user.getUserName(), user.getUserAddress(), user.getUserAvatarUrl());
                            adapter.insert(dbUser);
                        }
                        return data.getUsers();
                    }

                    @Override
                    protected void onPostExecute(List<User> users) {
                        super.onPostExecute(users);
                        initRecyclerView(users);
                    }
                }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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
