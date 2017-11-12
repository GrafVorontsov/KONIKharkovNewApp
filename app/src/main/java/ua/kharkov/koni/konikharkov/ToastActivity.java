package ua.kharkov.koni.konikharkov;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class ToastActivity extends AppCompatActivity {
    TextView infoView;
    TextView info_loweringView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast);
        infoView = (TextView) findViewById(R.id.info);
        info_loweringView = (TextView) findViewById(R.id.info_lowering);
        Intent intent = getIntent();

        String info_lowering = intent.getStringExtra("info_lowering");
        String info = intent.getStringExtra("info");

        infoView.setText(info);
        //infoView.setText(Html.fromHtml("![CDATA[" + info + "]]"));
        info_loweringView.setText(Html.fromHtml("![CDATA[" + info_lowering + "]]"));
/*
        function info($info){
                $infos = explode(";", $info);

        echo "<table>";
        foreach($infos as $info){
            echo "<tr><td>" . $info . "</td></tr>";
        }
        echo "</table>";
}*/
    }
}
