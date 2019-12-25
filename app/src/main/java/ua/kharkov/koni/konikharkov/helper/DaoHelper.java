package ua.kharkov.koni.konikharkov.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import ua.kharkov.koni.konikharkov.greenDAO.DaoMaster;
import ua.kharkov.koni.konikharkov.greenDAO.DaoSession;

public class DaoHelper {

    private static volatile DaoHelper daoInstance;
    private DaoSession daoSession;

    private DaoHelper(Context context) {
        //Prevent form the reflection api
        if (daoInstance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        } else {
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "Amortizator");
            SQLiteDatabase db = helper.getWritableDatabase();
            DaoMaster daoMaster = new DaoMaster(db);
            daoSession = daoMaster.newSession();
        }
    }

    public static DaoHelper getInstance(Context context) {
        //Double check locking pattern
        if (daoInstance == null) {
            synchronized (DaoHelper.class) {//Check for the second time.
                //if there is no instance available... create new one
                if (daoInstance == null) daoInstance = new DaoHelper(context);
            }
        }

        return daoInstance;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}