package demoapp.itsteps.com.db.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Base database model
 */
public abstract class Model implements Serializable, Parcelable {

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Model() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
    }

    protected Model(Parcel in) {
        this.id = in.readLong();
    }
}
