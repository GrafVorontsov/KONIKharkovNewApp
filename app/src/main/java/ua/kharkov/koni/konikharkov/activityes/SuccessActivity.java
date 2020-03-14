package ua.kharkov.koni.konikharkov.activityes;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Objects;

import ua.kharkov.koni.konikharkov.R;
import ua.kharkov.koni.konikharkov.helper.SQLiteHandler;
import ua.kharkov.koni.konikharkov.helper.SessionManager;

import static ua.kharkov.koni.konikharkov.beans.UserAccount.getLevelTranslate;

public class SuccessActivity extends AppCompatActivity {

    private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        //добавляем кнопку назад и устанавливаем title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.account);
        }

        TextView txtName = findViewById(R.id.name);
        TextView txtEmail = findViewById(R.id.email);
        TextView txtPriceLevel = findViewById(R.id.level);
        Button btnLogout = findViewById(R.id.btnLogout);

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");
        String level = user.get("level");

        // Displaying the user details on the screen
        txtName.setText(name);
        txtEmail.setText(email);
        txtPriceLevel.setText(getResources().getString(R.string.your_price_level, getLevelTranslate(Objects.requireNonNull(level))));

        // Logout button click event
        btnLogout.setOnClickListener(v -> logoutUser());
    }

    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     */
    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
        finish();
    }

    //по кнопке Назад переходим на главную
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
