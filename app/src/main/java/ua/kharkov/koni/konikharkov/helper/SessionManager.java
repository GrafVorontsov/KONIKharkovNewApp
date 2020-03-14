package ua.kharkov.koni.konikharkov.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import java.util.HashMap;

import ua.kharkov.koni.konikharkov.beans.UserAccount;

public class SessionManager {
    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    private SharedPreferences pref;

    private Editor editor;
    private Context _context;

    // Shared pref mode
    private int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "AndroidKoniLogin";

    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";

    @SuppressLint("CommitPrefEdits")
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn) {

        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);

        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }

    public static UserAccount isSessionAlive(SessionManager session, SQLiteHandler db){

        if (!session.isLoggedIn()) {
            return new UserAccount("default", "", "active", ""); //создаём дефолтный аккаунт
        } else {
            // Fetching user details from sqlite
            HashMap<String, String> userDetails = db.getUserDetails();

            String name = userDetails.get("name");
            String email = userDetails.get("email");
            String status = userDetails.get("status");
            String level = userDetails.get("level");

            return new UserAccount(name, email, status, level);
        }
    }
}