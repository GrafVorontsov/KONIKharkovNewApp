package ua.kharkov.koni.konikharkov.activityes;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import ua.kharkov.koni.konikharkov.R;

public class Home extends SearchActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_manual, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void onBackPressed(){
        //Диалог подтвердения выхода из приложения
        new AlertDialog.Builder(this)
                .setTitle("Выйти из приложения?")
                .setMessage("Вы действительно хотите выйти?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, (arg0, arg1) -> Home.super.onBackPressed()).create().show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}

/*
case R.id.call:
                    Intent call = new Intent(getApplicationContext(), PhoneActivity.class);
                    startActivity(call);
                    mDrawerLayout.closeDrawers();
                    break;
                case R.id.about:
                    Intent about = new Intent(getApplicationContext(), AboutActivity.class);
                    startActivity(about);
                    mDrawerLayout.closeDrawers();
                    break;
                case R.id.login:
                    Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(login);
                    mDrawerLayout.closeDrawers();
                    break;
 */


