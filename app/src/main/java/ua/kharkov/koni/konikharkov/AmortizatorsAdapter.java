package ua.kharkov.koni.konikharkov;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class AmortizatorsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<Amortizator> amortizators;
    Amortizator current;
    String num;

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

        myHolder.carName.setText(current.getCar_name());
        if (current.getCorrection().equals("")){
            myHolder.correction.setVisibility(View.GONE);
        }else {
            myHolder.correction.setVisibility(View.VISIBLE);
            myHolder.correction.setText(current.getCorrection());
        }

        myHolder.year.setText(current.getYear());
        myHolder.art_number.setText(current.getArt_number());
        myHolder.range.setText(current.getRange());
        myHolder.install.setText(current.getInstall());
        myHolder.status.setText(current.getStatus());
        myHolder.price_euro.setText(current.getPrice_euro());
    }

    @Override
    public int getItemCount() {
        return amortizators.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView carName;
        public TextView art_number;
        public TextView model_name;
        public TextView marka_name;
        public TextView correction;
        public TextView year;
        public TextView range;
        public TextView install;
        public TextView status;
        public TextView price_euro;

        // create constructor to get widget reference
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
            install = (TextView) itemView.findViewById(R.id.install);
            status = (TextView) itemView.findViewById(R.id.status);
            price_euro = (TextView) itemView.findViewById(R.id.price_euro);
            //jpg.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        // Click event for all items
        @Override
        public void onClick(View view) {
            num = current.getArt_number();
            Toast.makeText(context, "You clicked " + num, Toast.LENGTH_SHORT).show();
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