package ua.kharkov.koni.konikharkov;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

public class SearchActivity extends AppCompatActivity {

    private List<Amortizator> amortizators;
    public RecyclerView recyclerView;
    public GridLayoutManager gridLayout;
    public AmortizatorsAdapter adapter;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        amortizators = new ArrayList<>();
        gridLayout = new GridLayoutManager(this, 1);  //(объект, количество колонок)
        recyclerView.setLayoutManager(gridLayout);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem item = menu.findItem(R.id.menu_search);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) item.getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(
                new ComponentName(this, SearchActivity.class)));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getData(newText);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    public void getData(String newText) {

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
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
                        progressBar.setVisibility(View.INVISIBLE);
                        amortizators.clear(); //очистка списка найденных амортизаторов перед каждым поиском
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

        //creating a request queue
        //RequestQueue requestQueue = Volley.newRequestQueue(this);
        RequestQueue queue = Singleton.getInstance(this.getApplicationContext()).getRequestQueue();
        //adding the string request to request queue
        Singleton.getInstance(this).addToRequestQueue(stringRequest);
        //requestQueue.add(stringRequest);
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
            adapter = new AmortizatorsAdapter(this, amortizators);
            recyclerView.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    protected SearchView getSearchView()
    {
        return searchView;
    }

}