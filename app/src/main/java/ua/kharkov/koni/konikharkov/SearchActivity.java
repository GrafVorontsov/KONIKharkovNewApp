package ua.kharkov.koni.konikharkov;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends SearchMenuActivity {

    public static final String QUERY_DATA = "ua.kharkov.koni.konikharkov.searchActivity.query_data";
    public static final String LIST_OF_AMORTIZATORS = "ua.kharkov.koni.konikharkov.searchActivity.list_of_amortizators";

    private ProgressBar progressBar;
    private List<Amortizator> amortizators;
    private AmortizatorsAdapter adapter;

    protected void onQuerySubmit(final String query){
        getData(query);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        GridLayoutManager gridLayout = new GridLayoutManager(this, 1);  //(объект, количество колонок)
        recyclerView.setLayoutManager(gridLayout);

        amortizators = new ArrayList<>();
        @SuppressWarnings("unchecked")
        List<Amortizator> listOfAmortizators = (List<Amortizator>) getIntent().getSerializableExtra(LIST_OF_AMORTIZATORS);
        if(listOfAmortizators != null){
            amortizators.addAll(listOfAmortizators);
        }
        adapter = new AmortizatorsAdapter(this, amortizators);
        recyclerView.setAdapter(adapter);

        String query = (String) getIntent().getSerializableExtra(QUERY_DATA);
        if(query != null && !query.isEmpty()){
            onQuerySubmit(query);
            return;
        }
    }

    public void getData(String newText) {
        //формируем url для запроса
        String url = Config.SEARCH_URL + newText;
        //making the progressbar visible
        progressBar.setVisibility(View.VISIBLE);

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        progressBar.setVisibility(View.GONE);
                        amortizators.clear(); //очистка списка найденных амортизаторов перед каждым поиском
                        adapter.notifyDataSetChanged();
                        showJSON(response);

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue queue = Singleton.getInstance(this.getApplicationContext()).getRequestQueue();
        Singleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void showJSON(String response) {
        String marka_bool = "";
        String model_bool = "";

        try {
            JSONObject src = new JSONObject(response);

            JSONArray cars = src.getJSONArray("cars");

            JSONArray rates = src.getJSONArray("rates");

            int kurs;

            kurs = rates.getJSONObject(0).getInt("KURS_EURO");

            for (int i = 0; i < cars.length(); i++) {

                JSONObject object = cars.getJSONObject(i);

                Amortizator amortizator = new Amortizator(object.getInt("id"),
                        object.getString("marka_name"),
                        object.getString("model_name"),
                        object.getString("car_name"),
                        object.getString("correction"),
                        object.getString("year"),
                        object.getString("range_type"),
                        object.getString("install"),
                        object.getString("art_number"),
                        object.getString("status"),
                        object.getString("PRICE_EURO"));

                String marka = amortizator.getMarka_name();

                if (!marka.equals(marka_bool)) {
                    marka_bool = marka;

                } else {
                    amortizator.setMarka_name("");
                }

                String model = amortizator.getModel_name();

                if (!model.equals(model_bool)) {
                    model_bool = model;
                } else {
                    amortizator.setModel_name("");
                }

                String pr_euro = amortizator.getPrice_euro();

                try {

                    String price = String.valueOf(Math.round(Double.parseDouble(pr_euro) * kurs));
                    amortizator.setPrice_euro(price);

                } catch (NumberFormatException e) {
                    amortizator.setPrice_euro("0");
                }

                amortizators.add(amortizator);
            }

            //creating custom adapter object
            adapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}