package ua.kharkov.koni.konikharkov.activityes;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import ua.kharkov.koni.konikharkov.R;
import ua.kharkov.koni.konikharkov.activityes.Favourites;

//класс для работы с телефонами
public class PhoneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getScreenOrientation(); //меняем активити в зависимости от ориентации экрана

        //добавляем кнопку назад и устанавливаем title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.fones);
        }

        //view для каждого номера
        ImageView vodafone_view = findViewById(R.id.vodafone);
        ImageView kyivstar_view = findViewById(R.id.kyivstar);
        ImageView lifecell_view = findViewById(R.id.life);
        ImageView viber_view = findViewById(R.id.viber);

        //номера для связи
        final String vodafone_number = "+380503641315";
        final String kyivstar_number = "+380973111100";
        final String lifecell_number = "+380733641315";
        final String viber_number = "+380503641315";

        //вешаем слушатель на каждый view с функцией звонка
        vodafone_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callByPhone(vodafone_number);
            }
        });

        kyivstar_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callByPhone(kyivstar_number);
            }
        });

        lifecell_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callByPhone(lifecell_number);
            }
        });

        viber_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callByViber(viber_number);
            }
        });
    }

    //метод совершения телефонного звонка
    private void callByPhone(String contact_number){

        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + contact_number));
        try {
            startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //метод для звонка в Viber
    public void callByViber(String sphone) {
        Uri uri = Uri.parse("tel:" + Uri.encode(sphone));
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setClassName("com.viber.voip", "com.viber.voip.WelcomeActivity");
        intent.setData(uri);
        startActivity(intent);
    }

    //заставляем кнопку home работать как надо
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();  return true;
        }

        if (id == R.id.menu_fav) {

            Intent intent = new Intent(this, Favourites.class);
            this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //метод для загрузки другого активити в зависимости от ориентации экрана
    private void getScreenOrientation() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_phone);
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_phone_landscape);
        }
    }
}
