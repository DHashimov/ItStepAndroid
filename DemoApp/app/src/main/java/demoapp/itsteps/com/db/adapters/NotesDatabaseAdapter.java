package demoapp.itsteps.com.db.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import java.util.List;

import demoapp.itsteps.com.db.models.Note;

/**
 * Created by dhashimov on 5/10/17.
 */

public class NotesDatabaseAdapter extends BaseDatabaseAdapter<Note> {
    public static final String DATABASE_TABLE = "notes";

    // Columns
    private static final String ID = "_id";
    private static final String NOTE = "note";

    // Queries
    public static final String CREATE_TABLE_QUERY =
            "create table " + DATABASE_TABLE + " ("
                    + ID + " INTEGER PRIMARY KEY, "
                    + NOTE + " TEXT)";
    public static final String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS " + DATABASE_TABLE;
    private static final String TAG = "StoryPieceDatabase";

    public NotesDatabaseAdapter(Context context) {
        super(context);
    }

    @Override
    public Note buildModelFromCursor(Cursor cursor) {
        Note result = new Note();
        result.setId(cursor.getLong(cursor.getColumnIndexOrThrow(ID)));
        result.setNote(cursor.getString(cursor.getColumnIndexOrThrow(NOTE)));
        return result;
    }

    @Override
    protected ContentValues buildModelContentValues(Note model) {
        ContentValues values = new ContentValues();
        if (model.getId() > 0) {
            values.put(ID, model.getId());
        }
        values.put(NOTE, model.getNote());
        return values;
    }

    @Override
    protected String getTableName() {
        return DATABASE_TABLE;
    }


    public List<Note> getNotes() {
        Cursor cursor = super.query(null, null, null, null, null);
        return buildModelsFromCursorAndClose(cursor);
    }
}
