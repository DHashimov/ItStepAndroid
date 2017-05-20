package demoapp.itsteps.com.db.models;



public class DbUser extends Model{

    private String userName;
    private String address;
    private String avatarURL;

    public DbUser(String userName, String address, String avatarURL) {
        this.userName = userName;
        this.address = address;
        this.avatarURL = avatarURL;
    }

    public DbUser() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

}
