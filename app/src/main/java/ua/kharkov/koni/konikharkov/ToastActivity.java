package ua.kharkov.koni.konikharkov;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.github.chrisbanes.photoview.PhotoView;

import java.io.InputStream;
import java.util.ArrayList;

public class ToastActivity extends AppCompatActivity {

    InfoAdapter infoAdapter;
    ArrayList<Info> infos = new ArrayList<>();
    private ProgressBar progressBar;
    String info = null;
    String info_lowering = null;
    String pic = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        infoAdapter = new InfoAdapter(this, infos);
        infoAdapter.notifyDataSetChanged();
        infos.clear();
        progressBar = findViewById(R.id.progressBarToast);

        ListView listView = findViewById(R.id.listView);
        LinearLayout linearLayout = findViewById(R.id.listInfo);

        Intent intent = getIntent();

        try {
            info = intent.getStringExtra("info");

            if (info.equals(null) || info.equals("")){
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0));
            }else {
                fillData();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            info_lowering = intent.getStringExtra("info_lowering");

            if (!info_lowering.equals(null) || !info_lowering.equals("")) {
                fillDataLowering();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            listView.setAdapter(infoAdapter);
        }catch (Exception e){
            e.printStackTrace();
        }

        PhotoView bindImage = findViewById(R.id.img);

        try {
            pic = intent.getStringExtra("pic");

            if (pic.equals(null) || pic.equals("")){
                bindImage.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0));
            }

            String pathToFile = "http://koni.kharkov.ua/catalog/images/products/" + pic;
            DownloadImageWithURLTask downloadTask = new DownloadImageWithURLTask(bindImage);
            downloadTask.execute(pathToFile);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //класс который загружает картинки
    @SuppressLint("StaticFieldLeak")
    private class DownloadImageWithURLTask extends AsyncTask<String, Void, Bitmap> {
        PhotoView bmImage;
        DownloadImageWithURLTask(PhotoView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String pathToFile = urls[0];
            Bitmap bitmap = null;
            try {
                progressBar.setVisibility(View.VISIBLE);
                InputStream in = new java.net.URL(pathToFile).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bitmap;
        }
        protected void onPostExecute(Bitmap result) {
            progressBar.setVisibility(View.GONE);
            bmImage.setImageBitmap(result);
        }
    }
        // генерируем данные для адаптера
        void fillData() {
            String[] splitIedInfo = info.split(";");
            String[] split;
            String icon;
            String text;

            for (String aSplitIedInfo : splitIedInfo) {
                if (aSplitIedInfo.contains("<h3>")) {
                    text = aSplitIedInfo.replace("<h3>", "").replace("</h3>", "");
                    infos.add(new Info(text));
                } else if (aSplitIedInfo.contains("koni2")) {
                    split = aSplitIedInfo.split("</span></span>");
                    icon = split[0].replace("<p><span class=\"icon\"><span class=\"koni2\">", "koni");
                    text = split[1].replace("</p>", "");

                    infos.add(new Info(icon, text));
                } else {
                    split = aSplitIedInfo.split("</span>");
                    icon = split[0].replace("<p><span class=\"icon\">", "icon");
                    text = split[1].replace("</p>", "");

                    infos.add(new Info(icon, text));
                }
            }
        }

    void fillDataLowering() {
        String text;
        String[] splitIedInfoLowering = info_lowering.split(";");
        infos.add(new Info(""));
        for (String aSplitIedInfoLowering : splitIedInfoLowering) {

            if (aSplitIedInfoLowering.contains("<h3>")) {
                text = aSplitIedInfoLowering.replace("<h3>", "").replace("</h3>", "");
                infos.add(new Info(text));
            } else if (aSplitIedInfoLowering.contains("<p>")) {
                text = aSplitIedInfoLowering.replace("<p>", "").replace("</p>", "");
                infos.add(new Info(text));
            }
        }
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