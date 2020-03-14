package ua.kharkov.koni.konikharkov.activityes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ua.kharkov.koni.konikharkov.R;

public class DocumentationMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documentation_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
