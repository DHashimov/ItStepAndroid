package demoapp.itsteps.com.db.adapters;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.List;

import demoapp.itsteps.com.db.models.DbUser;

public class UsersDatabaseAdapter extends BaseDatabaseAdapter<DbUser> {
    public static final String DATABASE_TABLE = "users";

    private static final String ID = "_id";
    private static final String USER_NAME = "user_name";
    private static final String ADDRESS = "address";
    private static final String AVATAR = "avatar";

    //Queri
    public static final String CREATE_TABLE_QUERY =
            "create table " + DATABASE_TABLE + " ("
            +ID + " INTEGER PRIMARY KEY, "
            +USER_NAME + " TEXT, "
            +ADDRESS + " TEXT, "
            +AVATAR + " TEXT)";

    public static final String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS " + DATABASE_TABLE;

    public UsersDatabaseAdapter(Context context) {
        super(context);
    }

    @Override
    public DbUser buildModelFromCursor(Cursor cursor) {
        DbUser user = new DbUser();
        user.setId(cursor.getLong(cursor.getColumnIndexOrThrow(ID)));
        user.setUserName(cursor.getString(cursor.getColumnIndexOrThrow(USER_NAME)));
        user.setAddress(cursor.getString(cursor.getColumnIndexOrThrow(ADDRESS)));
        user.setAvatarURL(cursor.getString(cursor.getColumnIndexOrThrow(AVATAR)));
        return user;
    }

    @Override
    protected ContentValues buildModelContentValues(DbUser model) {
        ContentValues values = new ContentValues();
        if (model.getId() > 0) {
            values.put(ID, model.getId());
        }
        values.put(USER_NAME, model.getUserName());
        values.put(ADDRESS, model.getAddress());
        values.put(AVATAR, model.getAvatarURL());
        return values;
    }

    @Override
    protected String getTableName() {
        return DATABASE_TABLE;
    }

    public List<DbUser> getUsers() {
        Cursor cursor = super.query(null, null, null, null, null);
        return buildModelsFromCursorAndClose(cursor);
    }

}
