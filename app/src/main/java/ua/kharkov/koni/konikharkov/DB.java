package ua.kharkov.koni.konikharkov;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DB {

    private static final String DB_NAME = "KoniDBName.db";
    private static final String DB_TABLE = "favorites";
    private static final int DB_VERSION = 1;
    private static final String COLUMN_ID = "_id";
    public static final String NUMBER = "n_umber";
    public static final String INSTALL = "i_nstall";
    public static final String PRICE = "p_rice";
    public static final String RANGE = "r_ange";
    public static final String STATUS = "s_tatus";

    private static final String DB_CREATE =
            "create table " + DB_TABLE + "(" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    NUMBER + " text," +
                    INSTALL + " text," +
                    PRICE + " text, " +
                    RANGE + " text," +
                    STATUS + " text)";

    private final Context mCtx;

    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;

    DB(Context ctx) {
        mCtx = ctx;
    }

    // открыть подключение
    public void open() {
        mDBHelper = new DBHelper(mCtx, DB_NAME, null, DB_VERSION);
        mDB = mDBHelper.getWritableDatabase();
    }

    // закрыть подключение
    public void close() {
        if (mDBHelper!=null) mDBHelper.close();
    }

    // получить все данные из таблицы DB_TABLE
    public Cursor getAllData() {
        return mDB.query(DB_TABLE, null, null, null, null, null, null);
    }

    public String selectNMBR(String id) {
        Cursor mCursor = mDB.query(DB_TABLE, null, NUMBER + " = ?", new String[] {String.valueOf(id)}, null, null, null);
        String title = null;
        if( mCursor != null && mCursor.moveToFirst() ) {
            title = mCursor.getString(1);
            mCursor.close();
        }
        return title;
    }

    // добавить запись в DB_TABLE
    public void insertFavorites (String n_umber, String i_nstall, String p_rice, String r_ange,String s_tatus) {
        //SQLiteDatabase db = this.mDB;
        ContentValues contentValues = new ContentValues();
        contentValues.put(NUMBER, n_umber); // ПИНГВИН
        contentValues.put(INSTALL, i_nstall);
        contentValues.put(PRICE, p_rice);
        contentValues.put(RANGE, r_ange);
        contentValues.put(STATUS, s_tatus);
        mDB.insert(DB_TABLE, null, contentValues);
    }

    // удалить запись из DB_TABLE
    public void delRec(long id) {
        mDB.delete(DB_TABLE, COLUMN_ID + " = " + id, null);
    }
    public void delNumber(String numb) {
        mDB.execSQL("DELETE FROM "+ DB_TABLE + " WHERE " + NUMBER + " LIKE '%" + numb + "%' + ");
    }

    public String selectID(String id) {
        Cursor mCursor = mDB.query(DB_TABLE, null, NUMBER + " = ?", new String[] {String.valueOf(id)}, null, null, null);
        String title = null;
        if( mCursor != null && mCursor.moveToFirst() ) {
            title = mCursor.getString(0);
            mCursor.close();
        }
        return title;
    }

    public boolean deleteAllOrderDetails() {

        int doneDelete;
        doneDelete = mDB.delete(DB_TABLE, null , null);
        Log.w("WEWWEWR", Integer.toString(doneDelete));
        return doneDelete > 0;

    }

/*
    // Define 'where' part of query.
    String selection = FeedEntry.COLUMN_NAME_TITLE + " LIKE ?";
    // Specify arguments in placeholder order.
    String[] selectionArgs = { "MyTitle" };
    // Issue SQL statement.
    int deletedRows = db.delete(FeedEntry.TABLE_NAME, selection, selectionArgs);
*/
    // класс по созданию и управлению БД
    private class DBHelper extends SQLiteOpenHelper {

        DBHelper(Context context, String name, CursorFactory factory,
                        int version) {
            super(context, name, factory, version);
        }

        // создаем и заполняем БД
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }




}
