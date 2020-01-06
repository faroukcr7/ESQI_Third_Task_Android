package com.example.entreprisemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyWorkDetailsActivity extends AppCompatActivity {
    TextView textView ;
    Button start, pause, reset, lap ;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;
    Handler handler;
    int Seconds, Minutes, MilliSeconds ;
    ListView listView ;
    String[] ListElements = new String[] {};
    List<String> ListElementsArrayList ;
    ArrayAdapter<String> adapter ;
    TabledbHelper dbHelper;
    ModelUser modelUser;
    String text_id_work_intent , text_entreprise_name_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_work_details);


        text_id_work_intent = getIntent().getStringExtra("id_work");
        text_entreprise_name_intent = getIntent().getStringExtra("entreprise_name");

        dbHelper = new TabledbHelper(getApplicationContext()) ;
        textView = (TextView)findViewById(R.id.textView);
        start = (Button)findViewById(R.id.button);
        pause = (Button)findViewById(R.id.button2);
        reset = (Button)findViewById(R.id.button3);
        lap = (Button)findViewById(R.id.button4) ;
        listView = (ListView)findViewById(R.id.listview1);

        handler = new Handler() ;

        ListElementsArrayList = new ArrayList<String>(Arrays.asList(ListElements));

        adapter = new ArrayAdapter<String>(MyWorkDetailsActivity.this,
                android.R.layout.simple_list_item_1,
                ListElementsArrayList
        );

        listView.setAdapter(adapter);
        modelUser  = viewAll();


    }

    @Override
    protected void onStart() {
        super.onStart();

        // Instantiate the RequestQueue.
        final RequestQueue queue = Volley.newRequestQueue(this);
        final String url ="http://192.168.43.139:8080/api/users/me/work/";




        JsonObjectRequest jsonObjectReques = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray jsonArray =  response.getJSONArray("list_work").getJSONObject(0).getJSONArray("work_times");

                    Log.d("RESPONSE" , jsonArray.toString());

                    for(int i = 0; i < jsonArray.length(); i++){
                        ListElementsArrayList.add(jsonArray.getJSONObject(i).getString("time"));
                    }


                        adapter.notifyDataSetChanged();




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
                params.put("id_work", text_id_work_intent);


                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(jsonObjectReques);
    }

    public void start (View view) {


        StartTime = SystemClock.uptimeMillis();
        handler.postDelayed(runnable, 0);

        reset.setEnabled(false);
    }
    public void pause ( View view) {

        TimeBuff += MillisecondTime;

        handler.removeCallbacks(runnable);

        reset.setEnabled(true);
    }

    public void reset ( View view) {
        MillisecondTime = 0L ;
        StartTime = 0L ;
        TimeBuff = 0L ;
        UpdateTime = 0L ;
        Seconds = 0 ;
        Minutes = 0 ;
        MilliSeconds = 0 ;

        textView.setText("00:00:00");


    }


    public void lap ( View view) throws JSONException {

        // Instantiate the RequestQueue.
        final RequestQueue queue = Volley.newRequestQueue(this);
        final String url ="http://192.168.43.139:8080/api/entreprises/work/";

/*
                 "id_work" : "ssssqqbytepitchfarouk" ,
                "entreprise_name" :"ssssqq" ,
                "fullname":"farouk",
                "work_time" : "23.56"

 */
        final JSONObject U = new JSONObject();
        U.put("id_work",text_id_work_intent );
        U.put("entreprise_name", text_entreprise_name_intent);
        U.put("fullname", modelUser.getFullname());
        U.put("work_time", textView.getText());



        JsonObjectRequest jsonObjectReques = new JsonObjectRequest(Request.Method.POST, url, U, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                onStart();
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
                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(jsonObjectReques);
        rest();
        onStart() ;
    }

    public void rest () {
        MillisecondTime = 0L ;
        StartTime = 0L ;
        TimeBuff = 0L ;
        UpdateTime = 0L ;
        Seconds = 0 ;
        Minutes = 0 ;
        MilliSeconds = 0 ;
        textView.setText("00:00:00");
    }









    public Runnable runnable = new Runnable() {

        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            UpdateTime = TimeBuff + MillisecondTime;

            Seconds = (int) (UpdateTime / 1000);

            Minutes = Seconds / 60;

            Seconds = Seconds % 60;

            MilliSeconds = (int) (UpdateTime % 1000);

            textView.setText("" + Minutes + ":"
                    + String.format("%02d", Seconds) + ":"
                    + String.format("%03d", MilliSeconds));

            handler.postDelayed(this, 0);
        }

    };


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

}
