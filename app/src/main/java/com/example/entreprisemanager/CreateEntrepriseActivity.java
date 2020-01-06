package com.example.entreprisemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
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

public class CreateEntrepriseActivity extends AppCompatActivity {
    EditText entreprise_name , entreprise_email , entreprise_phonenumber , admin_name ;
    String  text_entreprise_name ,text_entreprise_email , text_entreprise_phonenumber , text_admin_name  , Token;
    TabledbHelper dbHelper;
    ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_entreprise);

        dbHelper = new TabledbHelper(getApplicationContext()) ;
        entreprise_name = (EditText) findViewById(R.id.entreprise_name);
        entreprise_email = (EditText) findViewById(R.id.entreprise_email);
        entreprise_phonenumber = (EditText) findViewById(R.id.entreprise_phonenumber);
        admin_name = (EditText) findViewById(R.id.admin_name);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        Token= viewAll() ;


        admin_name.setEnabled(false);
        admin_name.setFocusable(false);




    }


    public void Save (View view) throws JSONException {
        text_entreprise_name = String.valueOf(entreprise_name.getText());
        text_entreprise_email = String.valueOf(entreprise_email.getText());
        text_entreprise_phonenumber = String.valueOf(entreprise_phonenumber.getText());
        progressBar.setVisibility(View.VISIBLE);



        if (text_entreprise_name.isEmpty() || text_entreprise_name == null) {
            entreprise_name.setError("You have To set the Name ! ");
            progressBar.setVisibility(View.INVISIBLE);
            return;
        }

        if (! isValidName(text_entreprise_name)) {
            entreprise_name.setError("You have To set a Correct  Name ! ");
            progressBar.setVisibility(View.INVISIBLE);
            return;
        }

        if (text_entreprise_email.isEmpty() || text_entreprise_email == null ) {
            entreprise_email.setError("You have To set the Email ! ");
            progressBar.setVisibility(View.INVISIBLE);
            return;
        }
        if (!isValidEmail(text_entreprise_email) ) {
            entreprise_email.setError("You have To set a Correct Email !");
            progressBar.setVisibility(View.INVISIBLE);
            return;
        }

        if (text_entreprise_phonenumber.isEmpty() || text_entreprise_phonenumber == null) {
            entreprise_phonenumber.setError("You have To set the Phonenumber ! ");
            progressBar.setVisibility(View.INVISIBLE);
            return;
        }



        // Instantiate the RequestQueue.
        final RequestQueue queue = Volley.newRequestQueue(this);
        final String url ="http://192.168.43.139:8080/api/entreprises/";


        final JSONObject U = new JSONObject();
        U.put("entreprise_name",text_entreprise_name);
        U.put("entreprise_email",text_entreprise_email);
        U.put("entreprise_phonenumber",text_entreprise_phonenumber);
        U.put("admin_name",admin_name.getText());

        JsonObjectRequest jsonObjectReques = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(String.valueOf(U)), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Toast.makeText(getApplicationContext(),response.getString("message"), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);

            }
        } , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"That didn't work!" , Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),String.valueOf(error) , Toast.LENGTH_LONG).show();

            }
        })
        { //no semicolon or coma
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String, String> params = new HashMap<String, String>();
            params.put("x-auth-token", Token);
            return params;
        }
        };
        // Add the request to the RequestQueue.
        queue.add(jsonObjectReques);



    }


    public String viewAll() {
        String id ="" ;
        String Token ="" ;

        Cursor res = dbHelper.getAllData();
        if(res.getCount() == 0) {
            // show message
            Toast.makeText(getApplicationContext(),"Error"+"Local Db is empty" , Toast.LENGTH_LONG).show();
        }

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            admin_name.setText(res.getString(1));
            Token=res.getString(3) ;

        }
        return  Token ;
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
