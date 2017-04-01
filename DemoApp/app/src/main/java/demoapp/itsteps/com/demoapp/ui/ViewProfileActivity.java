package demoapp.itsteps.com.demoapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import demoapp.itsteps.com.demoapp.R;
import demoapp.itsteps.com.demoapp.ui.utils.CircleTransformation;
import demoapp.itsteps.com.models.User;
import demoapp.itsteps.com.singletons.PicassoInstance;

public class ViewProfileActivity extends AppCompatActivity {

    /**
     * Needed to provide the {@link User} which holds the profile related data
     */
    public static final String EXTRA_USER_PROFILE_DATA = "user_profile_data";
    @Bind(R.id.img_profile)
    ImageView imgProfile;
    @Bind(R.id.txt_profile_name)
    TextView txtProfileName;
    @Bind(R.id.txt_address)
    TextView txtAddress;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        ButterKnife.bind(this);
        if (getIntent() != null) {
            user = getIntent().getParcelableExtra(EXTRA_USER_PROFILE_DATA);
        }

        if (user == null) {
            throw new IllegalArgumentException("You should provide the User info");
        } else {
            initViews();
        }
    }

    private void initViews() {
        // Load avatar.
        if (StringUtils.isNotEmpty(user.getUserAvatarUrl())) {
            PicassoInstance.get(ViewProfileActivity.this).load(user.getUserAvatarUrl())
                    .transform(new CircleTransformation()).into(imgProfile);
        } else {
            PicassoInstance.get(ViewProfileActivity.this).load(android.R.drawable.sym_def_app_icon)
                    .transform(new CircleTransformation()).into(imgProfile);
        }
        txtProfileName.setText(user.getUserName());
        txtAddress.setText(user.getUserAddress());
    }

    @OnClick(R.id.btn_eddit)
    public void onEditClicked(){
        Intent intent = new Intent(ViewProfileActivity.this , EditActivity.class);
        intent.putExtra(EditActivity.EXTRA_USER_PROFILE_DATA , user);
        startActivity(intent);
    }

}
