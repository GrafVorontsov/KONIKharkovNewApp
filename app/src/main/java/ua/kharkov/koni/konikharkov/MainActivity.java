package ua.kharkov.koni.konikharkov;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends SearchActivity{

    //Declaring an Spinner
    private Spinner spinner_marka;
    private Spinner spinner_model;
    private Spinner spinner_car;

    //An ArrayList for Spinner Items
    private ArrayList<String> markas;
    private ArrayList<String> models;
    private ArrayList<String> cars;

    //JSON Arrays
    private JSONArray result_marka;
    private JSONArray result_model;
    private JSONArray result_cars;

    ArrayAdapter<String> markaAdapter;
    ArrayAdapter<String> modelAdapter;
    ArrayAdapter<String> carAdapter;
    List<String> list_id = new ArrayList<String>();
    String allTypesIds;
    String id;
    String selected;

    private List<Amortizator> amortizators;
    String whatFor;

    Button btnSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSearch = (Button) findViewById(R.id.btn_search_x);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        amortizators = new ArrayList<>();

        markas = new ArrayList<>();
        models = new ArrayList<>();
        cars = new ArrayList<>();

        markaAdapter = new ArrayAdapter<>(MainActivity.this, R.layout.spinner_textview, markas);

        modelAdapter = new ArrayAdapter<>(MainActivity.this, R.layout.spinner_textview, models);
        modelAdapter.notifyDataSetChanged();

        carAdapter = new ArrayAdapter<>(MainActivity.this, R.layout.spinner_textview, cars);
        carAdapter.notifyDataSetChanged();

        //спиннер для марок
        spinner_marka = (Spinner) findViewById(R.id.spinner_marka);

        spinner_marka.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String marka_id;
                marka_id = getID("marka",position, ""); //получаем id

                getConnectionForSpinners("model", Config.MODEL_URL, marka_id); //создаём соединение с БД
                modelAdapter.clear(); //очистка спинера модели
                carAdapter.clear();  //очистка спинера автомобилей
                modelAdapter.addAll(models);   //заполняем спиннер модели данными
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //синнер для моделей
        spinner_model = (Spinner) findViewById(R.id.spinner_model);

        spinner_model.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Выбор модели
                String model_id;
                model_id = getID("model",position, ""); //получаем id

                list_id.clear(); //очистка All type IDs
                getConnectionForSpinners("cars", Config.CAR_URL, model_id); //создаём соединение с БД
                carAdapter.clear(); //очистка спинера автомобилей
                carAdapter.addAll(cars); //заполняем спиннер автомобилей данными
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //спиннер для автомобилей
        spinner_car = (Spinner) findViewById(R.id.spinner_car);

        spinner_car.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Выбор авто
                selected = spinner_car.getSelectedItem().toString();
                String car_id;
                car_id = getID("car", position, selected); //получаем id
                amortizators.clear();
                getConnectionForAmortizators(Config.AMORT_URL, car_id); //создаём соединение с БД
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Получение данных для списка марок автомобилей
        getConnectionForSpinners("marka", Config.MARKA_URL, "null");
    }

    private void getConnectionForSpinners(String what, String URL, String id){

        String url;
        whatFor = what;

        if (id == "null"){
            url = URL;
        }else {
            url = URL + "?id=" + id.trim();
        }

        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            switch (whatFor) {
                                case "marka":   result_marka = new JSONArray(response);
                                    getNames("marka", result_marka);
                                    break;
                                case "model":   result_model = new JSONArray(response);
                                    getNames("model", result_model);
                                    break;
                                case "cars":    result_cars = new JSONArray(response);
                                    getNames("cars", result_cars);
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //RequestQueue requestQueue = Volley.newRequestQueue(this);
        RequestQueue queue = Singleton.getInstance(this.getApplicationContext()).getRequestQueue();
        //adding the string request to request queue
        Singleton.getInstance(this).addToRequestQueue(stringRequest);
        //requestQueue.add(stringRequest);
    }

    private void getConnectionForAmortizators(String URL, String ids){

        id = ids;
        String url = URL;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject src = new JSONObject(response);
                            JSONArray cars = src.getJSONArray("cars");
                            JSONArray rates = src.getJSONArray("rates");
                            getAmortNames(cars, rates);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("ids", id);

                return params;
            }
        };

        //RequestQueue requestQueue = Volley.newRequestQueue(this);
        RequestQueue queue = Singleton.getInstance(this.getApplicationContext()).getRequestQueue();
        //adding the string request to request queue
        Singleton.getInstance(this).addToRequestQueue(stringRequest);
        //requestQueue.add(stringRequest);
    }

    //получаем данные для спиннеров
    private void getNames(String what, JSONArray j){
        int numberOfItems = j.length();

        try {
            switch (what) {
                case "marka":   markas.add("Выберите марку");
                    for (int i = 0; i < numberOfItems; i++) {
                        JSONObject json = j.getJSONObject(i);
                        markas.add(json.getString(Config.MARKA_NAME));
                    }
                    spinner_marka.setAdapter(markaAdapter);
                    break;

                case "model":   models.add("Выберите модель");
                    for (int i = 0; i < numberOfItems; i++) {
                        JSONObject json = j.getJSONObject(i);
                        models.add(json.getString(Config.MODEL_NAME));
                    }
                    spinner_model.setAdapter(modelAdapter);
                    break;

                case "cars":    cars.add("Выберите авто");
                    cars.add("All types");
                    for (int i = 0; i < numberOfItems; i++) {
                        JSONObject json = j.getJSONObject(i);
                        cars.add(json.getString(Config.CAR_NAME));
                        list_id.add(json.getString(Config.CAR_ID));//для All types
                    }

                    //записываем все ID для SQL запроса IN
                    allTypesIds = TextUtils.join(", ", list_id);

                    spinner_car.setAdapter(carAdapter);
                    break;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getAmortNames(JSONArray cars, JSONArray rates){
        String marka_bool = "";
        String model_bool = "";

        try {
            int kurs;

            kurs = rates.getJSONObject(0).getInt("KURS_EURO");

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

            Intent intent = new Intent(MainActivity.this, SpinnerResult.class);
            intent.putExtra("AMORTIZATORS_LIST", (Serializable) amortizators);
            startActivity(intent);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //получаем нужный ID
    private String getID(String what, int position, String selected){
        String id = "";
        try {
            switch (what){
                case "marka":   JSONObject json_marka = result_marka.getJSONObject(position - 1);
                    id = json_marka.getString(Config.MARKA_ID);
                    break;
                case "model":   JSONObject json_model = result_model.getJSONObject(position - 1);
                    id = json_model.getString(Config.MODEL_ID);
                    break;
                case "car":
                    try {
                        JSONObject json_car = result_cars.getJSONObject(position - 2);
                        id = json_car.getString(Config.CAR_ID);
                    }catch (JSONException ls){
                        if (selected.equals("Выберите авто")){
                            //никаких действий
                        }else if (selected.equals("All types")){id = allTypesIds;}
                    }
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return id;
    }

/*
    @Override
    protected SearchView getSearchView() {
        return super.searchView;
    }
    */
}