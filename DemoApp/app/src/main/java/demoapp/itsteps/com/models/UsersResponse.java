package demoapp.itsteps.com.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dhashimov on 5/19/17.
 */

public class UsersResponse implements Serializable {
    @SerializedName("users")
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}