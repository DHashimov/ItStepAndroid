package demoapp.itsteps.com.db.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dhashimov on 5/10/17.
 */

public class Note extends Model {

    private String note;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    public Note() {
    }


    public Note(String note) {
        this.note = note;
    }

    protected Note(Parcel in) {
        super(in);
        this.note = in.readString();
    }

}
