package demoapp.itsteps.com.db.adapters;

import android.database.Cursor;

import java.util.List;

public interface ModelBuilder<T> {

    T buildModelFromCursor(Cursor cursor);

    List<T> buildModelsFromCursorAndClose(Cursor cursor);
}
