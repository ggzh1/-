package test;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import android.content.ContentValues;
public class DBOpenHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "db_test";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "user";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PASSWORD = "password";

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT  NOT NULL, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_PASSWORD + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTable = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTable);
        onCreate(db);
    }

    public void insertData(String name,String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_PASSWORD, password);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void deleteData(String name, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_NAME + " = ? AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = { name, password };
        db.delete(TABLE_NAME, selection, selectionArgs);
        db.close();
    }

    public void updateData(String name, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PASSWORD, newPassword);
        String selection = COLUMN_NAME + " = ?";
        String[] selectionArgs = { name };
        db.update(TABLE_NAME, values, selection, selectionArgs);
        db.close();
    }

    public ArrayList<User> getAllData() {
        ArrayList<User> userList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, COLUMN_NAME + " DESC");
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            String password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));
            User user = new User(name, password);
            userList.add(user);
        }
        cursor.close();
        db.close();
        return userList;
    }
    public void updatePassword(User user, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("password", newPassword);
        db.update(TABLE_NAME, values, "name=?", new String[]{user.getName()});
        db.close();
    }

}

