package ua.kharkov.koni.konikharkov.beans;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ua.kharkov.koni.konikharkov.R;
import ua.kharkov.koni.konikharkov.config.Config;
import ua.kharkov.koni.konikharkov.helper.VolleyRequestHelper;

public class AllPrices {

    private String price;

    public AllPrices() {
    }

    public void getPricesForAmortizators(Context context, String userLevel, String artNumber){
        final String number = artNumber.replaceAll("[^A-Za-z0-9]", ""); // удалится все кроме букв и цифр;

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.PRICE_URL,
                response -> {
                    try {
                        JSONObject src = new JSONObject(response);
                        final JSONArray amortPrice = src.getJSONArray("prices");

                        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                        @SuppressLint("InflateParams")
                        View layout = inflater.inflate(R.layout.custom_toast, null);

                        TextView toastTextView = layout.findViewById(R.id.toastTextView);
                        ImageView toastImageView = layout.findViewById(R.id.toastImageView);
                        // set the text in the TextView
                        toastTextView.setText("Ваша цена: " + getAmortPrices(amortPrice) + " грн. за штуку");
                        // set the Image in the ImageView
                        toastImageView.setImageResource(R.mipmap.ic_launcher_round);
                        // create a new Toast using context
                        Toast toast = new Toast(context);
                        toast.setDuration(Toast.LENGTH_LONG); // set the duration for the Toast
                        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.setView(layout); // set the inflated layout
                        toast.show(); // display the custom Toast

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {}) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("number", number);
                params.put("userLevel", userLevel);

                return params;
            }
        };

        RequestQueue queue = VolleyRequestHelper.getInstance(context).getRequestQueue();
        VolleyRequestHelper.getInstance(context).addToRequestQueue(stringRequest);
    }

    public String getAmortPrices(JSONArray prices){
        try {
            return prices.getJSONObject(0).getString("price");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

