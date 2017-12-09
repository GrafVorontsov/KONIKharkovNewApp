package ua.kharkov.koni.konikharkov;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class InfoAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Info> infos;

    InfoAdapter(Context context, ArrayList<Info> infos) {
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.infos = infos;
    }
    // кол-во элементов
    @Override
    public int getCount() {
        return infos.size();
    }
    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return infos.get(position);
    }
    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }
    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.toast, parent, false);
        }

        Info pInfo = getInfo(position);

        // заполняем View в пункте списка данными из товаров: наименование, цена
        // и картинка
        TextView infoViewIcon = view.findViewById(R.id.infoIcon);
        TextView infoKoniIcon = view.findViewById(R.id.infoKoniIcon);

        infoViewIcon.setVisibility(View.GONE);
        infoKoniIcon.setVisibility(View.GONE);
        try {
            String icon = pInfo.getIcon();
            if (icon.contains("icon")){
                icon = icon.replace("icon", "");
                infoViewIcon.setVisibility(View.VISIBLE);
                infoViewIcon.setText(icon);
            }else if (icon.contains("koni")){
                icon = icon.replace("koni", "");
                infoKoniIcon.setVisibility(View.VISIBLE);
                infoKoniIcon.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/koni2-webfont.ttf"));
                infoKoniIcon.setText(icon);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            ((TextView) view.findViewById(R.id.infoText)).setText(pInfo.getText());
        }catch (Exception e){
            e.printStackTrace();
        }

        return view;
    }
    // товар по позиции
    private Info getInfo(int position) {
        return ((Info) getItem(position));
    }

    public void clear() {
        // TODO Auto-generated method stub
        infos.clear();
    }

}