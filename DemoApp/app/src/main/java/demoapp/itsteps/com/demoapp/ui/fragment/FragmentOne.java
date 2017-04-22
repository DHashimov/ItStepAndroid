package demoapp.itsteps.com.demoapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import demoapp.itsteps.com.demoapp.R;
import demoapp.itsteps.com.demoapp.ui.ViewProfileActivity;
import demoapp.itsteps.com.demoapp.ui.utils.CircleTransformation;
import demoapp.itsteps.com.models.User;
import demoapp.itsteps.com.singletons.PicassoInstance;

public class FragmentOne extends Fragment {

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = getArguments().getParcelable(EXTRA_USER_PROFILE_DATA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_one, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (user == null) {
            throw new IllegalArgumentException("You should provide the User info");
        } else {
            initViews();
        }
    }

    private void initViews() {
        // Load avatar.
        if (StringUtils.isNotEmpty(user.getUserAvatarUrl())) {
            PicassoInstance.get(getActivity()).load(user.getUserAvatarUrl())
                    .transform(new CircleTransformation()).into(imgProfile);
        } else {
            PicassoInstance.get(getActivity()).load(android.R.drawable.sym_def_app_icon)
                    .transform(new CircleTransformation()).into(imgProfile);
        }
        txtProfileName.setText(user.getUserName());
        txtAddress.setText(user.getUserAddress());
    }
}
