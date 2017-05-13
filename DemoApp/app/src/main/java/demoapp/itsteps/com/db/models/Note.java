package demoapp.itsteps.com.db.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dhashimov on 5/10/17.
 */

public class Note extends Model implements Parcelable{

    private String note;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.note);
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

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel source) {
            return new Note(source);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };
}
