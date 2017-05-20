package demoapp.itsteps.com.db.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Base database model
 */
public abstract class Model implements Serializable{

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Model() {
    }

    protected Model(Parcel in) {
        this.id = in.readLong();
    }
}
