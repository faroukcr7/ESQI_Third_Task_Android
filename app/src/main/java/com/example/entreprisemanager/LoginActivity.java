package com.example.entreprisemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    String EmailText, PasswordText, RightPassword;
    CheckBox checkBox;
    ProgressBar progressBar;
    boolean checked;
    TabledbHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        checkBox = (CheckBox) findViewById(R.id.checkbox);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        dbHelper = new TabledbHelper(this);


    }



    public void Login(View view) throws JSONException {
        EmailText = String.valueOf(email.getText());
        PasswordText = String.valueOf(password.getText());
        checked = checkBox.isChecked();

        progressBar.setVisibility(View.VISIBLE);


        if (EmailText.isEmpty() || EmailText == null) {
            email.setError("You have To set the Email ! ");
            progressBar.setVisibility(View.INVISIBLE);
            return;
        }

        if (!isValidEmail(EmailText) ) {
           email.setError("You have To set a Correct Email !");
            progressBar.setVisibility(View.INVISIBLE);
             return;
        }
        if (PasswordText.isEmpty() || PasswordText == null) {
            password.setError("You Have To set the password !");
            progressBar.setVisibility(View.INVISIBLE);
            return;
        }

        // method to check if the  password is correct or not send an http request and return the hash and compare it


        progressBar.setVisibility(View.INVISIBLE);
        // Instantiate the RequestQueue.
        final RequestQueue queue = Volley.newRequestQueue(this);
        final String url ="http://192.168.2.124:8080/api/users/login/";



        final JSONObject U = new JSONObject();
        U.put("email", EmailText);
        U.put("password", PasswordText);

        JsonObjectRequest jsonObjectReques = new JsonObjectRequest(Request.Method.POST, url, U, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dbHelper.deleteData();

                boolean isInserted = false;
                try {
                    dbHelper.deleteData();
                    isInserted = dbHelper.insert(response.getString("_id"),response.getString("fullname"),
                            response.getString("email")  , response.getString("x-auth-token") , response.getString("user_type"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (isInserted) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Data  not inserted !", Toast.LENGTH_LONG).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "That didn't work!", Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();

            }
        });
        // Add the request to the RequestQueue.
        queue.add(jsonObjectReques);


    }


    public void gohome(View view) {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);


    }

    public void Gosignup(View view) {
        Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
        startActivity(intent);

    }


    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

}
