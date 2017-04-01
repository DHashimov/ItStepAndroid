package demoapp.itsteps.com.demoapp.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import demoapp.itsteps.com.demoapp.R;
import demoapp.itsteps.com.demoapp.ui.fragment.FragmentOne;
import demoapp.itsteps.com.demoapp.ui.fragment.FragmentThree;
import demoapp.itsteps.com.demoapp.ui.fragment.FragmentTwo;
import demoapp.itsteps.com.demoapp.ui.utils.CircleTransformation;
import demoapp.itsteps.com.models.User;
import demoapp.itsteps.com.singletons.PicassoInstance;

public class EditActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * Needed to provide the {@link User} which holds the profile related data
     */
    public static final String EXTRA_USER_PROFILE_DATA = "user_profile_data";
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.nav_view)
    NavigationView navView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;
    ImageView imgProfile;
    TextView txtUserName;
    TextView txtAddress;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ButterKnife.bind(this);
        if (getIntent() != null) {
            user = getIntent().getParcelableExtra(EXTRA_USER_PROFILE_DATA);
        }

        if (user == null) {
            throw new IllegalArgumentException("You should provide the User info");
        } else {
            initViews();
            FragmentOne fragment = new FragmentOne();
            startFragment(fragment);
        }
    }

    private void startFragment(Fragment fragment) {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment, "fragment");
        fragmentTransaction.commit();
    }

    private void initViews() {
        imgProfile = (ImageView) navView.getHeaderView(0).findViewById(R.id.imageView);
        txtUserName = (TextView) navView.getHeaderView(0).findViewById(R.id.txt_user_name);
        txtAddress = (TextView) navView.getHeaderView(0).findViewById(R.id.txt_address);
        setSupportActionBar(toolbar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navView.setNavigationItemSelectedListener(this);
        if (StringUtils.isNotEmpty(user.getUserAvatarUrl())) {
            PicassoInstance.get(EditActivity.this).load(user.getUserAvatarUrl())
                    .transform(new CircleTransformation()).into(imgProfile);
        } else {
            PicassoInstance.get(EditActivity.this).load(android.R.drawable.sym_def_app_icon)
                    .transform(new CircleTransformation()).into(imgProfile);
        }
        txtUserName.setText(user.getUserName());
        txtAddress.setText(user.getUserAddress());
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_item_one) {
            startFragment(new FragmentOne());
        } else if (id == R.id.nav_item_two) {
            startFragment(new FragmentTwo());
        } else if (id == R.id.nav_item_three) {
            startFragment(new FragmentThree());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
