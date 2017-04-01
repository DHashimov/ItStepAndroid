package demoapp.itsteps.com.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import demoapp.itsteps.com.callbacks.OnUserClickedCallback;
import demoapp.itsteps.com.demoapp.R;
import demoapp.itsteps.com.demoapp.ui.utils.CircleTransformation;
import demoapp.itsteps.com.models.User;
import demoapp.itsteps.com.singletons.PicassoInstance;

/**
 * Created by dhashimov on 3/30/17.
 */

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.RecyclerViewHolder> implements View.OnClickListener {

    private List<User> usersList;
    private Context context;
    OnUserClickedCallback callback;

    public UsersListAdapter(Context context,
                            List<User> usersList, OnUserClickedCallback callback) {
        this.context = context;
        this.usersList = usersList;
        this.callback = callback;

    }

    //The size of the list is +1 because of an empty item on the end with add new bike button in it
    @Override
    public int getItemCount() {
        return usersList.size();
    }


    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        final User user = usersList.get(position);
        // Load avatar.
        if (StringUtils.isNotEmpty(user.getUserAvatarUrl())) {
            PicassoInstance.get(context).load(user.getUserAvatarUrl())
                    .transform(new CircleTransformation()).into(holder.imgUserAvatar);
        } else {
            PicassoInstance.get(context).load(android.R.drawable.sym_def_app_icon)
                    .transform(new CircleTransformation()).into(holder.imgUserAvatar);
        }
        holder.txtUserName.setText(user.getUserName());
        holder.txtUserAddress.setText(user.getUserAddress());
        holder.grpContainer.setOnClickListener(this);
        holder.grpContainer.setTag(user);
    }

    public void invalidate(List<User> fellowRiderModels) {
        this.usersList = null;
        this.usersList = new ArrayList<User>(fellowRiderModels);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ViewGroup mainGroup = (ViewGroup) inflater.inflate(
                R.layout.item_ueser, viewGroup, false);
        RecyclerViewHolder listHolder = new RecyclerViewHolder(mainGroup);
        return listHolder;
    }

    public List<User> getUsersList() {
        return usersList;
    }

    @Override
    public void onClick(View v) {
        callback.onUserSelected((User) v.getTag());
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.img_user_avatar)
        ImageView imgUserAvatar;
        @Bind(R.id.txt_user_name)
        TextView txtUserName;
        @Bind(R.id.txt_user_address)
        TextView txtUserAddress;
        @Bind(R.id.grp_container)
        LinearLayout grpContainer;

        public RecyclerViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}