package com.example.entreprisemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EmployeeDetailsActivity extends AppCompatActivity {
    ArrayList<ModelEmployee> listemployees =new ArrayList<ModelEmployee>();
    TabledbHelper dbHelper;
    RecyclerView recyclerView;
    String entreprise_name_intent   ;
    ModelUser modelUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_details);


        entreprise_name_intent = getIntent().getStringExtra("entreprise_name");


        dbHelper = new TabledbHelper(getApplicationContext()) ;
        recyclerView= (RecyclerView)  findViewById(R.id.ReceyclerViewMyemployees);

        modelUser  = viewAll();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Instantiate the RequestQueue.
        final RequestQueue queue = Volley.newRequestQueue(this);
        final String url ="http://192.168.2.124:8080/api/entreprises/";



        JsonObjectRequest jsonObjectReques = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray jsonArray =  response.getJSONArray("list_entreprsies").getJSONObject(0).getJSONArray("employees") ;

                    Log.d("RESPONSE" , jsonArray.toString());

                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonArray.getJSONObject(i);
                        ModelEmployee modelEmployee = new ModelEmployee(
                                jsonArray.getJSONObject(i).getString("employee_name") ,
                                jsonArray.getJSONObject(i).getString("employee_email") ,
                                jsonArray.getJSONObject(i).getString("employee_phonenumber")
                        );

                        listemployees.add(modelEmployee);
                    }

                    // Create adapter passing in the sample user data
                    AdapterEmployee adapter = new AdapterEmployee(listemployees);
                    // Attach the adapter to the recyclerview to populate items
                    recyclerView.setAdapter(adapter);
                    // Set layout manager to position the items
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    // That's all!


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "error "+ String.valueOf(error) , Toast.LENGTH_LONG).show();

            }
        })
        { //no semicolon or coma
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("x-auth-token", modelUser.getToken());

                params.put("user_type",modelUser.getUser_type()  );
                params.put("entreprise_name", entreprise_name_intent);
                params.put("admin_name",modelUser.getFullname());

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


    public void  goAddCreateEmployeeActivity(View view) {
        Intent intent = new Intent(getApplicationContext() , CreateEmployeeActivity.class);
        intent.putExtra("entreprise_name", String.valueOf(entreprise_name_intent) );
        startActivity(intent);

    }

}

