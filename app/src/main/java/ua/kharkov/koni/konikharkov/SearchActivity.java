package ua.kharkov.koni.konikharkov;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.io.Serializable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SearchActivity extends SearchMenuActivity {

    private static final String ACTION_SHOW_ABSORBERS = "ua.kharkov.koni.konikharkov.searchActivity.show_absorbers";
    private static final String EXTRA_LIST_OF_ABSORBERS = "ua.kharkov.koni.konikharkov.searchActivity.list_of_absorbers";

    private ProgressBar progressBar;
    private List<Amortizator> amortizators;
    private AmortizatorsAdapter adapter;
    RecyclerView recyclerView;

    public static Intent newIntentShowAbsorbers(Context packageContext, List<Amortizator> absorbers){
        Intent intent = new Intent(packageContext, SearchActivity.class);
        intent.setAction(ACTION_SHOW_ABSORBERS);
        intent.putExtra(EXTRA_LIST_OF_ABSORBERS, (Serializable)absorbers);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        }

        progressBar = findViewById(R.id.progressBar);

        recyclerView = findViewById(R.id.recyclerview);
        GridLayoutManager gridLayout = new GridLayoutManager(this, 1);  //(объект, количество колонок)
        recyclerView.setLayoutManager(gridLayout);

        amortizators = new ArrayList<>();

        adapter = new AmortizatorsAdapter(this, amortizators);
        recyclerView.setAdapter(adapter);

        if(savedInstanceState != null){
            @SuppressWarnings("unchecked")
            List<Amortizator> savedAmortizators = (List<Amortizator>) savedInstanceState.getSerializable(EXTRA_LIST_OF_ABSORBERS);
            Intent intent = newIntentShowAbsorbers(this, savedAmortizators);
            setIntent(intent);
        }

        handleIntent(getIntent());

    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putSerializable(EXTRA_LIST_OF_ABSORBERS, (Serializable)amortizators);
    }

    @Override
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent){
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {

            if(!isNetworkAvailable()){
                Toast.makeText(getApplicationContext(), getString(R.string.no_internet_connection), Toast.LENGTH_LONG).show();
                return;
            }
            String query = intent.getStringExtra(SearchManager.QUERY);
            getData(query);
        }else if(ACTION_SHOW_ABSORBERS.equals(intent.getAction())){
            @SuppressWarnings("unchecked")
            List<Amortizator> absorbers = (List<Amortizator>)intent.getSerializableExtra(EXTRA_LIST_OF_ABSORBERS);
            amortizators.addAll(absorbers);
            adapter.notifyDataSetChanged();
        }
    }

    public void getData(String newText) {
        String searchString = newText.replaceAll("[^A-Za-z0-9]", ""); // удалится все кроме букв и цифр;
        //формируем url для запроса
        String url = Config.SEARCH_URL + searchString;
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
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
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

            Double kurs = rates.getJSONObject(0).getDouble("KURS_EURO");

            for (int i = 0; i < cars.length(); i++) {

                JSONObject object = cars.getJSONObject(i);

                Amortizator amortizator = new Amortizator(
                        object.getString("marka_name"),
                        object.getString("model_name"),
                        object.getString("car_name"),
                        object.getString("correction"),
                        object.getString("year"),
                        object.getString("range_type"),
                        object.getString("install"),
                        object.getString("art_number"),
                        object.getString("info"),
                        object.getString("info_lowering"),
                        object.getString("jpg"),
                        object.getString("pdf"),
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

            adapter.notifyDataSetChanged();

            //прокрутка списка в начало, после каждого поиска
            recyclerView.post(new Runnable() {
                @Override
                public void run() {
                    // Call smooth scroll
                    recyclerView.smoothScrollToPosition(0);
                }
            });
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), getString(R.string.information_not_found), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();  return true;
        }

        if (id == R.id.menu_fav) {

            Intent intent = new Intent(this,Favourites.class);
            this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}