package ua.kharkov.koni.konikharkov;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ToastActivity extends AppCompatActivity {
    TextView infoView;
    TextView infoViewIcon;
    TextView info_loweringView;
    InfoAdapter infoAdapter;
    ArrayList<Info> infos = new ArrayList<Info>();

    String icon;
    String text;
    String[] split;
    String[] splitIedInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast);

        Intent intent = getIntent();
        String info_lowering = intent.getStringExtra("info_lowering");
        String info = intent.getStringExtra("info");

        splitIedInfo = info.split(";");
        fillData();

        infoAdapter = new InfoAdapter(this, infos);
        ListView listView = (ListView) findViewById(R.id.listView);
/*
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, splitIedInfo);
*/
        listView.setAdapter(infoAdapter);
    }
        // генерируем данные для адаптера
        void fillData() {
            for (int i = 0; i < splitIedInfo.length; i++){
                if (splitIedInfo[i].contains("<h3>")){
                    text = splitIedInfo[i].replace("<h3>", "");
                    infos.add(new Info(text));
                }else if (splitIedInfo[i].contains("koni2")){
                    split = splitIedInfo[i].split("</span></span>");
                    icon = split[0];
                    text = split[1];

                    infos.add(new Info(icon,text));
                }else {
                    split = splitIedInfo[i].split("</span>");
                    icon = split[0];
                    text = split[1];

                    infos.add(new Info(icon, text));
                }
            }
        }
/*
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerviewtoast);
        GridLayoutManager gridLayout = new GridLayoutManager(this, 1);  //(объект, количество колонок)
        recyclerView.setLayoutManager(gridLayout);

        infos = new ArrayList<>();

*/


/*
        infoViewIcon.setTypeface(Typeface.createFromAsset(
                getAssets(), "fonts/koni2-webfont.ttf"));
*/
/*
try {
    String[] splitIedInfo = info.split(";");
    for (String splitInfo : splitIedInfo) {
        if (splitInfo.contains("koni2") && splitInfo.contains("icon")){
            String i = "";
            String p = "";
            String[] spl = splitInfo.split("</span></span>");

            for (String s : spl){

                if (s.contains("koni2")){
                    i = s.replace("<p><span class=\"icon\"><span class=\"koni2\">", "");
                }else {
                    p = s.replace("</p>", "");

                }

            }
            Info info1 = new Info(i, p);
            infos.add(info1);



        }else if (splitInfo.contains("icon") && !splitInfo.contains("koni2")){
           // infoView.setText(splitInfo);
        }
        infoAdapter = new InfoAdapter(this, infos);
        recyclerView.setAdapter(infoAdapter);
    }
}catch (NullPointerException e){
    e.printStackTrace();

}
*/

//infoView.setText(info);

        //infoView.setText(Html.fromHtml("![CDATA[" + info + "]]"));
        //info_loweringView.setText(Html.fromHtml("![CDATA[" + info_lowering + "]]"));




}
