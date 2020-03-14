package ua.kharkov.koni.konikharkov.activityes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ua.kharkov.koni.konikharkov.R;
import ua.kharkov.koni.konikharkov.app.AppController;
import ua.kharkov.koni.konikharkov.config.Config;
import ua.kharkov.koni.konikharkov.helper.SQLiteHandler;
import ua.kharkov.koni.konikharkov.utils.Validate;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private EditText inputFullName;
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    //private SessionManager session;
    private SQLiteHandler db;
    String err;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //добавляем кнопку назад и устанавливаем title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.register);
        }

        inputFullName = findViewById(R.id.name);
        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        final Button btnRegister = findViewById(R.id.btnRegister);
        Button btnLinkToLogin = findViewById(R.id.btnLinkToLoginScreen);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());
        btnRegister.setEnabled(false);
        btnRegister.setBackgroundColor(Color.GRAY);

        // Check if user is already logged in or not
        inputEmail.addTextChangedListener(new TextWatcher(){

                                              @Override
                                              public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                              }

                                              @Override
                                              public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                  TextView myOutputBox = findViewById(R.id.myOutputBox);
                                                  if (s.length() > 5 ) {
                                                      err = String.valueOf(Validate.emailValidate(s));
                                                      if ("true".equals(err)) {
                                                          myOutputBox.setTextColor(Color.GREEN);
                                                          myOutputBox.setText("Вы ввели корректный e-mail.");
                                                      } else {
                                                          myOutputBox.setTextColor(Color.RED);
                                                          myOutputBox.setText("Таких e-mail не бывает!");
                                                      }
                                                  }
                                              }

                                              @Override
                                              public void afterTextChanged(Editable s) {
                                              }
                                          });
        inputEmail.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                try {
                    if (v == inputEmail && !hasFocus && err.equals("true")) {
                        String email = inputEmail.getText().toString().trim();
                        checkUserInDataBase(email);
                        btnRegister.setEnabled(true);
                        btnRegister.setBackgroundResource(R.color.btn_register);
                    }
                    if (err.equals("false")){
                        btnRegister.setEnabled(false);
                        btnRegister.setBackgroundColor(Color.GRAY);
                    }
                } catch (Exception e){
                    Log.d(TAG, "Focus: " + e);
                }
            }
        });

        inputPassword.addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                TextView myOutputBoxPass = findViewById(R.id.myOutputBoxPassword);

                if (s.length() < 5 ) {
                    myOutputBoxPass.setText("Введите минимум 5 символов!");
                } else {
                    myOutputBoxPass.setText("Отличный пароль!");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // Register Button Click event
        btnRegister.setOnClickListener(view -> {
            String name = inputFullName.getText().toString().trim();
            String email = inputEmail.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();

            if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                if (password.length() < 5){
                    TextView myOutputBoxPass = findViewById(R.id.myOutputBoxPassword);
                    myOutputBoxPass.setText("Минимум 5 символов!");
                }   else {
                    registerUser(name, email, password);
                    TextView myOutputBoxRegSucss = findViewById(R.id.myOutputRegisterSucsessfull);
                    myOutputBoxRegSucss.setText("Ожидайте подтверждения администратором!");
                }
            } else {
                Toast.makeText(getApplicationContext(),
                        "Please enter your details!", Toast.LENGTH_LONG)
                        .show();
            }
        });

        // Link to Login Screen
        btnLinkToLogin.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(),
                    LoginActivity.class);
            startActivity(i);
            finish();
        });

    }

    private void checkUserInDataBase(final String email) {
        // Tag used to cancel the request
        String tag_string_req = "req_check";

        StringRequest strReq = new StringRequest(Method.POST,
                Config.URL_CHECK, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Check Response: " + response);

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (error) {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        TextView myOutputBox = findViewById(R.id.myOutputBox);
                        myOutputBox.setTextColor(Color.RED);
                        myOutputBox.setText(errorMsg);
                        //Toast.makeText(getApplicationContext(),
                                //errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, error -> {
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<>();
                params.put("checkEmail", email);
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     * */
    private void registerUser(final String name, final String email,
                              final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Method.POST,
                Config.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response);
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String created_at = user
                                .getString("created_at");
                        int intStatus = user.getInt("user_status");
                        String level = user.getString("level");

                        if (0 == intStatus){
                            // Error in login. Get the error message
                            String errorMsg = "Account not activated";
                            Toast.makeText(getApplicationContext(),
                                    errorMsg, Toast.LENGTH_LONG).show();
                        } else if (1 == intStatus) {
                            String status = "Active";
                            // Inserting row in users table
                            db.addUser(name, email, uid, created_at, status, level);

                            //Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();
                            Toast.makeText(getApplicationContext(), "Вы зарегистрировались! Ожидайте активации учётной записи!", Toast.LENGTH_LONG).show();
                            // Launch login activity
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, error -> {
            Log.e(TAG, "Registration Error: " + error.getMessage());
            Toast.makeText(getApplicationContext(),
                    error.getMessage(), Toast.LENGTH_LONG).show();
            hideDialog();
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    //по кнопке Назад переходим на главную
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();  return true;
        }
        return super.onOptionsItemSelected(item);
    }
}