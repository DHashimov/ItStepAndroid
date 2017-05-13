package demoapp.itsteps.com.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import demoapp.itsteps.com.db.adapters.NotesDatabaseAdapter;


/**
 * Database manager
 */
public class DatabaseInstance {

    private static final String TAG = "DatabaseInstance";
    private static DatabaseInstance instance;

    public synchronized static DatabaseInstance getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseInstance(context);
            instance.open();
        }

        return instance;
    }

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    private DatabaseInstance(Context context) {
        this.databaseHelper = new DatabaseHelper(context);
    }

    /**
     * Delete the database and reset the Database Instance
     *
     * @param context application/activity context.
     */
    public synchronized void deleteDatabase(Context context) {
        context.deleteDatabase(database.getPath());
        instance = null;
    }

    private synchronized void open() throws SQLException {
        long attempts = 0;
        final long MAX_ATTEMPTS = 100;
        final long ONE_SECOND_IN_MILLIS = 1000;
        while (this.database == null || this.database.isReadOnly()) {
            Log.d(TAG, "Attempting to open database...");
            this.database = this.databaseHelper.getWritableDatabase();
            if (database == null || this.database.isReadOnly()) {
                try {
                    Thread.sleep(ONE_SECOND_IN_MILLIS);
                    Log.d(TAG, "Database could not be opened, retrying...");
                } catch (InterruptedException e) {
                    Log.e(TAG, "Interrupted", e);
                    return;
                }
                attempts += 1;
                if (attempts >= MAX_ATTEMPTS) {
                    throw new IllegalStateException("DATABASE COULD NOT BE OPENED");
                }
            } else {
                Log.d(TAG, "Database is open and writable!");
            }
        }
    }

    public synchronized SQLiteDatabase getDatabase() {
        synchronized (DatabaseInstance.class) {
            return this.database;
        }
    }

    private class DatabaseHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "demoapp.db";
        private static final int DATABASE_VERSION = 1;

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onConfigure(SQLiteDatabase db) {
            super.onConfigure(db);
            db.setForeignKeyConstraintsEnabled(true);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            createTables(db);
        }

        private void createTables(SQLiteDatabase db) {
            db.execSQL(NotesDatabaseAdapter.CREATE_TABLE_QUERY);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            resetTables(db);
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            resetTables(db);
        }

        protected void resetTables(SQLiteDatabase db) {
            db.execSQL(NotesDatabaseAdapter.DROP_TABLE_QUERY);
            createTables(db);
        }
    }
}
