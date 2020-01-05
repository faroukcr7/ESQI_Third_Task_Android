package com.example.entreprisemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateEmployeeActivity extends AppCompatActivity {
    String  entreprise_name_intent ;
    TabledbHelper dbHelper;
    ModelUser modelUser;
    ProgressBar progressBar;

    EditText entreprise_name , employee_name , employee_email , employee_phonenumber , admin_name , client_name ;
    String   text_employee_name , text_employee_email , text_employee_phonenumber , text_admin_name , text_client_name  ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_employee);


        entreprise_name = (EditText) findViewById(R.id.entreprise_name);
        admin_name = (EditText) findViewById(R.id.admin_name);

        employee_name = (EditText) findViewById(R.id.employee_name);
        employee_email = (EditText) findViewById(R.id.employee_email);
        employee_phonenumber = (EditText) findViewById(R.id.employee_phonenumber);
        client_name = (EditText) findViewById(R.id.client_name) ;

        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        entreprise_name_intent = getIntent().getStringExtra("entreprise_name");
        dbHelper = new TabledbHelper(getApplicationContext()) ;
        modelUser  = viewAll();


        admin_name.setText(modelUser.getFullname());
        entreprise_name.setText(entreprise_name_intent);

        admin_name.setEnabled(false);
        admin_name.setFocusable(false);
        entreprise_name.setEnabled(false);
        entreprise_name.setFocusable(false);

    }



    public void Save( View view) throws JSONException {
        text_employee_name = String.valueOf(employee_name.getText());
        text_employee_email = String.valueOf(employee_email.getText());
        text_employee_phonenumber = String.valueOf(employee_phonenumber.getText());

        text_client_name = String.valueOf(client_name.getText()) ;

        progressBar.setVisibility(View.VISIBLE);


        if (text_employee_name.isEmpty() || text_employee_name == null) {
            employee_name.setError("You have To set the Name ! ");
            progressBar.setVisibility(View.INVISIBLE);
            return;
        }

        if (!isValidName(text_employee_name)) {
            employee_name.setError("You have To set a Correct  Name ! ");
            progressBar.setVisibility(View.INVISIBLE);
            return;
        }

        if (text_employee_email.isEmpty() || text_employee_email == null) {
            employee_email.setError("You have To set the Email ! ");
            progressBar.setVisibility(View.INVISIBLE);
            return;
        }
        if (!isValidEmail(text_employee_email)) {
            employee_email.setError("You have To set a Correct Email !");
            progressBar.setVisibility(View.INVISIBLE);
            return;
        }

        if (text_employee_phonenumber.isEmpty() || text_employee_phonenumber == null) {
            employee_phonenumber.setError("You have To set the Phonenumber ! ");
            progressBar.setVisibility(View.INVISIBLE);
            return;
        }


        // Instantiate the RequestQueue.
        final RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "http://192.168.2.124:8080/api/entreprises/employees/";


        final JSONObject U = new JSONObject();
        U.put("entreprise_name", entreprise_name_intent);
        U.put("user_type", modelUser.getUser_type());
        U.put("fullname", modelUser.getFullname());

        U.put("client_name", text_client_name);

        U.put("employee_name", text_employee_name);
        U.put("employee_email", text_employee_email);
        U.put("employee_phonenumber", text_employee_phonenumber);


        JsonObjectRequest jsonObjectReques = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(String.valueOf(U)), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR", String.valueOf(error));
                Toast.makeText(getApplicationContext(), "That didn't work!", Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), String.valueOf(error), Toast.LENGTH_LONG).show();

            }
        }) { //no semicolon or coma
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("x-auth-token", modelUser.getToken());
                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(jsonObjectReques);
    }




        public ModelUser viewAll() {
        String id="" , token ="" , fullname ="", email="" , user_type ="" ;

        Cursor res = dbHelper.getAllData();
        if(res.getCount() == 0) {
            // show message
            Toast.makeText(getApplicationContext(),"Error"+"Local Db is empty" , Toast.LENGTH_LONG).show();
        }


        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            id=res.getString(0) ;
            fullname=res.getString(1) ;
            email=res.getString(2) ;
            token=res.getString(3) ;
            user_type=res.getString(4) ;


        }
        return  new ModelUser(id ,fullname , email , token , user_type) ;
    }





    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static boolean isValidName (String Name) {
        Pattern pattern = Pattern.compile(new String ("^[a-zA-Z\\s]*$"));
        Matcher matcher = pattern.matcher(Name);
        if(!matcher.matches())
        {
            return  false;
        }
        return true ;

    }
}
