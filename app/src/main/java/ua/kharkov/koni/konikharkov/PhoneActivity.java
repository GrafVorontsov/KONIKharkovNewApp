package ua.kharkov.koni.konikharkov;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class PhoneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Телефоны для связи");
        }

        ImageView vodafone_view = findViewById(R.id.vodafone);
        ImageView kyivstar_view = findViewById(R.id.kyivstar);
        ImageView lifecell_view = findViewById(R.id.life);
        ImageView viber_view = findViewById(R.id.viber);
        final String vodafone_number = "+380957164744";
        final String kyivstar_number = "+380977995980";
        final String lifecell_number = "+380667419347";
        final String viber_number = "+380977995980";

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

    private void callByPhone(String contact_number){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        //Intent callIntent = new Intent(Intent.ACTION_);
        callIntent.setData(Uri.parse("tel:" + contact_number));
        try {
            callIntent.setPackage("com.android.phone");
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            startActivity(callIntent);
        } catch (Exception e) {
            callIntent.setPackage("com.android.server.telecom");
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            startActivity(callIntent);
        }
    }

    public void callByViber(String sphone) {
        Uri uri = Uri.parse("tel:" + Uri.encode(sphone));
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setClassName("com.viber.voip", "com.viber.voip.WelcomeActivity");
        intent.setData(uri);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();  return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
