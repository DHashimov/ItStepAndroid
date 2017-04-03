package demoapp.itsteps.com.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dhashimov on 3/30/17.
 */

public class User implements Parcelable {
    private String userName;
    private String userAddress;
    private String userAvatarUrl;

    public User(String userName, String userAddress, String userAvatarUrl) {
        this.userName = userName;
        this.userAddress = userAddress;
        this.userAvatarUrl = userAvatarUrl;
    }

    public User() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserAvatarUrl() {
        return userAvatarUrl;
    }

    public void setUserAvatarUrl(String userAvatarUrl) {
        this.userAvatarUrl = userAvatarUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userName);
        dest.writeString(this.userAddress);
        dest.writeString(this.userAvatarUrl);
    }

    protected User(Parcel in) {
        this.userName = in.readString();
        this.userAddress = in.readString();
        this.userAvatarUrl = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
