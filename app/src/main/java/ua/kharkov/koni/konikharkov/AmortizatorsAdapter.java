package ua.kharkov.koni.konikharkov;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class AmortizatorsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<Amortizator> amortizators;
    private Amortizator current;
    private DB db;

    AmortizatorsAdapter(Context context, List<Amortizator> amortizators) {
        this.context = context;
        inflater= LayoutInflater.from(context);
        this.amortizators = amortizators;
        db = new DB(context);
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.card, parent,false);
        return new MyHolder(view);
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Get current position of item in RecyclerView to bind data and assign values from list
        MyHolder myHolder= (MyHolder) holder;
        current=amortizators.get(position);

        try {
            myHolder.marka_name.setVisibility(View.VISIBLE);
            myHolder.marka_name.setText(current.getMarka_name());
        }catch (StringIndexOutOfBoundsException e){
            myHolder.marka_name.setVisibility(View.GONE);
        }

        try {
            myHolder.model_name.setVisibility(View.VISIBLE);
            myHolder.model_name.setText(current.getModel_name());
        }catch (StringIndexOutOfBoundsException e){
            myHolder.model_name.setVisibility(View.GONE);
        }

        if (current.getCar_name().equals("")){
            myHolder.carName.setVisibility(View.GONE);
        }else {
            myHolder.carName.setVisibility(View.VISIBLE);
            myHolder.carName.setText(current.getCar_name());
        }

        if (current.getCorrection().equals("")){
            myHolder.correction.setVisibility(View.GONE);
        }else {
            myHolder.correction.setVisibility(View.VISIBLE);
            myHolder.correction.setText(current.getCorrection());
        }

        myHolder.year.setText(current.getYear());
        myHolder.art_number.setText(current.getArt_number());

        db.open();

        try {
            String columnNumber = db.selectNMBR(current.getArt_number());

            if (current.getArt_number().equals(columnNumber)){
                myHolder.favoritestar.setVisibility(View.VISIBLE);
                myHolder.favoritestar_unchecked.setVisibility(View.GONE);
            }else {
                myHolder.favoritestar.setVisibility(View.GONE);
                myHolder.favoritestar_unchecked.setVisibility(View.VISIBLE);
            }
        }catch (Exception np){
            np.printStackTrace();
        }finally {
            db.close();
        }

        if ((current.getRange().contains("Sport"))||(current.getRange().equalsIgnoreCase("Sport Kit"))) {
            myHolder.range.setTextColor(Color.parseColor("#FFBA00"));
        }else if (current.getRange().contains("Special-Active")){
            myHolder.range.setTextColor(Color.RED);
        }else if (current.getRange().contains("Special")){
            myHolder.range.setTextColor(Color.RED);
        }else if (current.getRange().contains("STR.T")){
            myHolder.range.setTextColor(Color.parseColor("#FF5300"));
        }else if (current.getRange().contains("Coil")){
            myHolder.range.setTextColor(Color.BLACK);
        }else  if (current.getRange().equalsIgnoreCase("Classic")){
            myHolder.range.setTextColor(Color.BLACK);
        }else  if (current.getRange().equalsIgnoreCase("Heavy Track")){
            myHolder.range.setTextColor(Color.RED);
        }

        myHolder.range.setText(current.getRange());
        myHolder.install.setText(current.getInstall());

        if (current.getInfo().equals("")){
            myHolder.info.setText("");
            myHolder.info.setVisibility(View.GONE);
            myHolder.iconInfo.setVisibility(View.GONE);
        }else {
            myHolder.info.setText("");
            myHolder.info.setVisibility(View.GONE);
            myHolder.iconInfo.setVisibility(View.VISIBLE);
            myHolder.info.setText(current.getInfo());
        }

        if (current.getInfo_lowering().equals("")){
            myHolder.info_lowering.setText("");
            myHolder.info_lowering.setVisibility(View.GONE);
            myHolder.iconLowering.setVisibility(View.GONE);

        }else {
            myHolder.info_lowering.setText("");
            myHolder.info_lowering.setVisibility(View.GONE);
            myHolder.iconLowering.setVisibility(View.VISIBLE);
            myHolder.info_lowering.setText(current.getInfo_lowering());
        }

        if (current.getJpg().equals("")){
            myHolder.pic.setText("");
            myHolder.pic.setVisibility(View.GONE);
            myHolder.iconIMG.setVisibility(View.GONE);
        }else {
            myHolder.pic.setText("");
            myHolder.pic.setVisibility(View.GONE);
            myHolder.iconIMG.setVisibility(View.VISIBLE);
            myHolder.pic.setText(current.getJpg());
        }

        myHolder.status.setText(current.getStatus());
        myHolder.price_euro.setText(current.getPrice_euro());
    }

    @Override
    public int getItemCount() {
        return amortizators.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        private TextView carName;
        private TextView art_number;
        private TextView model_name;
        private TextView marka_name;
        private TextView correction;
        private TextView year;
        private TextView range;
        private TextView install;
        private TextView info;
        private TextView info_lowering;
        private TextView pic;
        private TextView status;
        private TextView price_euro;
        private ImageView iconInfo;
        private ImageView iconIMG;
        private ImageView iconLowering;
        private ImageView favoritestar;
        private ImageView favoritestar_unchecked;

        private String art_numberText;
        private String installText;
        private String price_euroText;
        private String rangeText;
        private String statusText;

        // create constructor to get widget reference
        @SuppressLint("WrongViewCast")
        MyHolder(View itemView) {
            super(itemView);
            carName = itemView.findViewById(R.id.car_name);
            art_number = itemView.findViewById(R.id.art_number);
            model_name = itemView.findViewById(R.id.model_name);
            marka_name = itemView.findViewById(R.id.marka_name);
            correction = itemView.findViewById(R.id.correction);
            year = itemView.findViewById(R.id.year);
            range = itemView.findViewById(R.id.range);
            info_lowering = itemView.findViewById(R.id.info_lowering);
            install = itemView.findViewById(R.id.install);
            info = itemView.findViewById(R.id.info);
            pic = itemView.findViewById(R.id.pic);
            status = itemView.findViewById(R.id.status);
            price_euro = itemView.findViewById(R.id.price_euro);
            iconInfo = itemView.findViewById(R.id.iconInfo);
            iconLowering = itemView.findViewById(R.id.iconLowering);
            iconIMG = itemView.findViewById(R.id.iconIMG);
            favoritestar = itemView.findViewById(R.id.favoritestar);
            favoritestar_unchecked = itemView.findViewById(R.id.favoritestar_unchecked);

            //вешаем слушатель на карточку товара
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    intentInfo(context, art_number, info, info_lowering, pic);}
            });

            favoritestar_unchecked.setOnClickListener(clickOnStar());

            favoritestar.setOnClickListener(clickOnStar());
        }

        private void intentInfo(Context context,
                                       TextView art_number,
                                       TextView info,
                                       TextView info_lowering,
                                       TextView pic){
            if (info.getText().toString().equals("") && info_lowering.getText().toString().equals("") && pic.getText().toString().equals("")){
                Toast.makeText(context, R.string.no_info, Toast.LENGTH_SHORT).show();
            }else{

                //Создаем элемент View заполняем его вид с созданного файла toast.xml:
                Intent intent=new Intent(context,ToastActivity.class);
                //Создаем данные для передачи:
                intent.putExtra("info_lowering", info_lowering.getText().toString());
                intent.putExtra("info", info.getText().toString());
                intent.putExtra("pic", pic.getText().toString());
                intent.putExtra("num", art_number.getText().toString());
                //Запускаем переход:
                context.startActivity(intent);
            }
        }

        private View.OnClickListener clickOnStar(){
            return new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    // получаем данные из полей ввода
                    art_numberText = art_number.getText().toString();
                    installText = install.getText().toString();
                    price_euroText = price_euro.getText().toString();
                    rangeText = range.getText().toString();
                    statusText = status.getText().toString();
                    // подключаемся к БД
                    db = new DB(context);
                    db.open();

                    try {
                        String columnNumber = db.selectNMBR(art_numberText);

                        if (art_numberText.equals(columnNumber)){

                            Long id = Long.valueOf(db.selectID(art_numberText));
                            db.delRec(id);
                            favoritestar.setVisibility(View.GONE);
                            favoritestar_unchecked.setVisibility(View.VISIBLE);
                            Toast.makeText(context, "Удалено из избранного", Toast.LENGTH_SHORT).show();
                        }else{
                            db.insertFavorites(art_numberText, installText, price_euroText, rangeText, statusText);
                            favoritestar.setVisibility(View.VISIBLE);
                            favoritestar_unchecked.setVisibility(View.GONE);
                            Toast.makeText(context, "Добавлено в избранное", Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception np){
                        np.printStackTrace();
                    }finally {
                        db.close();
                    }
                }
            };
        }

    }




}