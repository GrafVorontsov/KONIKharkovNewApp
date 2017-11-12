package ua.kharkov.koni.konikharkov;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class AmortizatorsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<Amortizator> amortizators;
    Amortizator current;

    public AmortizatorsAdapter(Context context, List<Amortizator> amortizators) {
        this.context = context;
        inflater= LayoutInflater.from(context);
        this.amortizators = amortizators;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.card, parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Get current position of item in RecyclerView to bind data and assign values from list
        MyHolder myHolder= (MyHolder) holder;
        current=amortizators.get(position);
        //Amortizator current=amortizators.get(position);


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

        if ((current.getRange().contains("Sport"))||(current.getRange().equalsIgnoreCase("Sport Kit"))) {
            myHolder.range.setTextColor(Color.parseColor("#FFBA00"));
        }else if (current.getRange().contains("FSD")){
            myHolder.range.setTextColor(Color.parseColor("#D4AF37"));
        }else if (current.getRange().contains("Special")){
            myHolder.range.setTextColor(Color.RED);
        }else if (current.getRange().contains("STR.T")){
            myHolder.range.setTextColor(Color.parseColor("#FF5300"));
        }else if (current.getRange().contains("Coil")){
            myHolder.range.setTextColor(Color.BLACK);
        }

        myHolder.range.setText(current.getRange());
        myHolder.install.setText(current.getInstall());

        if (current.getInfo().equals("")){
            myHolder.info.setVisibility(View.GONE);
        }else {
            myHolder.info.setVisibility(View.GONE);
            myHolder.info.setText(current.getInfo());
        }
        if (current.getInfo_lowering().equals("")){
            myHolder.info_lowering.setVisibility(View.GONE);
        }else {
            myHolder.info_lowering.setVisibility(View.GONE);
            myHolder.info_lowering.setText(current.getInfo_lowering());
        }

        myHolder.status.setText(current.getStatus());
        myHolder.price_euro.setText(current.getPrice_euro());
    }

    @Override
    public int getItemCount() {
        return amortizators.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        public TextView carName;
        public TextView art_number;
        public TextView model_name;
        public TextView marka_name;
        public TextView correction;
        public TextView year;
        public TextView range;
        //public ImageView rangecolor;
        public TextView install;
        public TextView info;
        public TextView info_lowering;
        public TextView img;
        public TextView status;
        public TextView price_euro;

        // create constructor to get widget reference
        @SuppressLint("WrongViewCast")
        public MyHolder(View itemView) {
            super(itemView);
            carName = (TextView) itemView.findViewById(R.id.car_name);
            //jpg = (ImageView) itemView.findViewById(R.id.jpg);
            art_number = (TextView) itemView.findViewById(R.id.art_number);
            model_name = (TextView) itemView.findViewById(R.id.model_name);
            marka_name = (TextView) itemView.findViewById(R.id.marka_name);
            correction = (TextView) itemView.findViewById(R.id.correction);
            year = (TextView) itemView.findViewById(R.id.year);
            range = (TextView) itemView.findViewById(R.id.range);
            info_lowering = (TextView) itemView.findViewById(R.id.info_lowering);
            install = (TextView) itemView.findViewById(R.id.install);
            info = (TextView) itemView.findViewById(R.id.info);
            status = (TextView) itemView.findViewById(R.id.status);
            price_euro = (TextView) itemView.findViewById(R.id.price_euro);
            //jpg.setOnClickListener(this);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast toast1= Toast.makeText(context, "You clicked " + art_number.getText(), Toast.LENGTH_SHORT);
                    //Создаем элемент View заполняем его вид с созданного файла toast.xml:
                    Intent intent=new Intent(context,ToastActivity.class);
                    //Создаем данные для передачи:
                    intent.putExtra("info_lowering", info_lowering.getText().toString());
                    intent.putExtra("info", info.getText().toString());
                    //Запускаем переход:
                    context.startActivity(intent);
                }
            });
        }
    }
    /*
    private void showPopupMenu(View view, int position) {
        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_context, popup.getMenu());
        popup.setOnMenuItemClickListener(new MenuClickListener(position));
        popup.show();
    }
*/
/*
    class MenuClickListener implements PopupMenu.OnMenuItemClickListener {
        Integer pos;
        public MenuClickListener(int pos) {
            this.pos=pos;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_favourite:
                    Toast.makeText(context, amortizators.get(pos).getCar_name()+" is added to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_watch:
                    Toast.makeText(context, amortizators.get(pos).getCar_name()+" is added to watchlist", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_book:
                    Toast.makeText(context, "Booked Ticket for " + amortizators.get(pos).getCar_name(), Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }
    */
}