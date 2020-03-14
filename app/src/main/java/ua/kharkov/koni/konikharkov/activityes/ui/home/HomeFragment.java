package ua.kharkov.koni.konikharkov.activityes.ui.home;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import ua.kharkov.koni.konikharkov.R;
import ua.kharkov.koni.konikharkov.activityes.SearchActivity;
import ua.kharkov.koni.konikharkov.beans.UserAccount;
import ua.kharkov.koni.konikharkov.config.Config;
import ua.kharkov.koni.konikharkov.entity.Amortizator;
import ua.kharkov.koni.konikharkov.helper.SQLiteHandler;
import ua.kharkov.koni.konikharkov.helper.SessionManager;
import ua.kharkov.koni.konikharkov.helper.VolleyRequestHelper;

public class HomeFragment extends Fragment {

    private Button clear_button;

    //JSON Arrays
    private JSONArray result_marka;
    private JSONArray result_model;
    private JSONArray result_cars;

    private ArrayAdapter<String> markaAdapter;
    private ArrayAdapter<String> modelAdapter;
    private ArrayAdapter<String> carAdapter;
    private List<String> list_id = new ArrayList<>();
    private String allTypesIds;
    private String id;
    private UserAccount user;
    private String userName;
    private String selected;

    private List<Amortizator> amortizators;
    private String whatFor;

    private SQLiteHandler db;
    private SessionManager session;

    public static HomeFragment newInstance() {

        return new HomeFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            //Restore the fragment's state here
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
        setHasOptionsMenu(true);
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setTitle(R.string.app_name);

    }
    @Override
    public void onSaveInstanceState(@NotNull Bundle outState) {
        //тут пишете необходимые вещи в outState
        super.onSaveInstanceState(outState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
// Inflate the layout for this fragment or reuse the existing one
        if (getView() != null) {
            System.out.println("onCreateView: reusing view");
        }
        View root = getView() != null ? getView() :  inflater.inflate(R.layout.fragment_home, container, false);

        //тянуть и обновить
        final SwipeRefreshLayout swipeRefreshLayout = root.findViewById(R.id.swipe_container);
        swipeRefreshLayout.setColorSchemeResources(R.color.blue, R.color.green, R.color.yellow, R.color.red);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            if(!isNetworkAvailable()){
                Toast.makeText(getActivity(), getString(R.string.no_internet_connection), Toast.LENGTH_LONG).show();
            }else {
                getConnectionForSpinners("marka", Config.MARKA_URL, "null");
            }
            swipeRefreshLayout.postDelayed(() -> swipeRefreshLayout.setRefreshing(false), 1000);
        });

        //SqLite database handler
        db = new SQLiteHandler(getActivity());
        // session manager
        session = new SessionManager(getActivity());
        user = SessionManager.isSessionAlive(session, db);
        clear_button = root.findViewById(R.id.clear_button);

        amortizators = new ArrayList<>();

        final List<String> markas = new ArrayList<>();
        final List<String> models = new ArrayList<>();
        final List<String> cars = new ArrayList<>();

        markaAdapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), R.layout.spinner_textview, markas);

        modelAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_textview, models);
        modelAdapter.setNotifyOnChange(true);

        carAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_textview, cars);
        carAdapter.setNotifyOnChange(true);

        try {
            clear_button.setOnClickListener(view -> {
                modelAdapter.clear(); //очистка спинера модели
                carAdapter.clear();  //очистка спинера автомобилей
                markaAdapter.clear();
                try {
                    getConnectionForSpinners("marka", Config.MARKA_URL, "null");
                    clear_button.setVisibility(View.GONE);
                }catch (Exception x){
                    x.printStackTrace();
                }

            });
        }catch (Exception r){
            r.printStackTrace();
        }

        //спиннер для марок
        final Spinner spinner_marka = root.findViewById(R.id.spinner_marka);
        spinner_marka.setAdapter(markaAdapter);
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
        final Spinner spinner_model = root.findViewById(R.id.spinner_model);
        spinner_model.setAdapter(modelAdapter);
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
                clear_button.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //спиннер для автомобилей
        final Spinner spinner_car = root.findViewById(R.id.spinner_car);
        spinner_car.setAdapter(carAdapter);
        spinner_car.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Выбор авто
                selected = spinner_car.getSelectedItem().toString();
                String car_id;
                car_id = getID("car", position, selected); //получаем id
                amortizators.clear();
                getConnectionForAmortizators(car_id, user); //создаём соединение с БД
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Получение данных для списка марок автомобилей
        try {
            getConnectionForSpinners("marka", Config.MARKA_URL, "null");
        }catch (Exception m){
            m.printStackTrace();
        }

        root.setOnTouchListener((view, motionEvent) -> true);

        return root;
    }

    private void getConnectionForSpinners(String what, String URL, String id){

        String url;
        whatFor = what;

        if (!id.trim().isEmpty()) {

            url = URL + "?id=" + id.trim();
            System.out.println("URL :::::::  " + url);

            StringRequest stringRequest = new StringRequest(url,
                    response -> {
                        try {
                            switch (whatFor) {
                                case "marka":
                                    result_marka = new JSONArray(response);
                                    getNames("marka", result_marka);
                                    break;
                                case "model":
                                    result_model = new JSONArray(response);
                                    getNames("model", result_model);
                                    break;
                                case "cars":
                                    result_cars = new JSONArray(response);
                                    getNames("cars", result_cars);
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> {

                    });

            RequestQueue queue = VolleyRequestHelper.getInstance(this.getActivity()).getRequestQueue();
            VolleyRequestHelper.getInstance(this.getActivity()).addToRequestQueue(stringRequest);
        } else {
            System.out.println("FFFFFFFFFFFFFFFFFF");;
        }
    }

    private void getConnectionForAmortizators(String ids, UserAccount loginedUser){
        id = ids;
        userName = loginedUser.getEmail();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.AMORT_URL,
                response -> {
                    try {
                        JSONObject src = new JSONObject(response);
                        JSONArray cars = src.getJSONArray("cars");
                        getAmortNames(cars);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {})
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("ids", id);
                params.put("email", userName);

                return params;
            }
        };

        RequestQueue queue = VolleyRequestHelper.getInstance(this.getActivity()).getRequestQueue();
        VolleyRequestHelper.getInstance(this.getActivity()).addToRequestQueue(stringRequest);
    }

    //получаем данные для спиннеров
    private void getNames(String what, JSONArray j){
        int numberOfItems = j.length();

        try {
            switch (what) {
                case "marka":   markaAdapter.add(getString(R.string.get_brand));
                    for (int i = 0; i < numberOfItems; i++) {
                        JSONObject json = j.getJSONObject(i);
                        markaAdapter.add(json.getString(Config.MARKA_NAME));
                    }
                    break;

                case "model":   modelAdapter.add(getString(R.string.get_model));
                    for (int i = 0; i < numberOfItems; i++) {
                        JSONObject json = j.getJSONObject(i);
                        modelAdapter.add(json.getString(Config.MODEL_NAME));
                    }
                    break;

                case "cars":    carAdapter.add(getString(R.string.get_auto));
                    carAdapter.add("All types");
                    for (int i = 0; i < numberOfItems; i++) {
                        JSONObject json = j.getJSONObject(i);
                        carAdapter.add(json.getString(Config.CAR_NAME));
                        list_id.add(json.getString(Config.CAR_ID));//для All types
                    }

                    //записываем все ID для SQL запроса IN
                    allTypesIds = TextUtils.join(", ", list_id);
                    break;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getAmortNames(JSONArray cars){
        String marka_bool = "";
        String model_bool = "";
        String car_bool = "";

        try {

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

                String car = amortizator.getCar_name();

                if (!car.equals(car_bool)) {
                    car_bool = car;
                } else {
                    amortizator.setCar_name("");
                }

                String pr_euro = amortizator.getPrice_euro();

                if (pr_euro.equals("null")){
                    amortizator.setPrice_euro("0");
                }else {
                    amortizator.setPrice_euro(pr_euro);
                }

                amortizators.add(amortizator);
            }

            Intent intent = SearchActivity.newIntentShowAbsorbers(getActivity(), amortizators);
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
                        if (selected.equals(getString(R.string.get_auto))){
                            //никаких действий
                            id = "";
                        }else if (selected.equals("All types")){id = allTypesIds;}
                    }
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return id;
    }

    protected boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) Objects.requireNonNull(getContext())
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }




}