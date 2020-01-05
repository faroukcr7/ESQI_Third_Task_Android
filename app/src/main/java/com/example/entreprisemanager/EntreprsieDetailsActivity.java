package com.example.entreprisemanager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

public class EntreprsieDetailsActivity extends AppCompatActivity {
    String entreprise_name_intent   ;

    String[] Menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entreprsie_details);

        entreprise_name_intent = getIntent().getStringExtra("entreprise_name");





        ListView listView = (ListView) findViewById(R.id.listviewboard);
        // Create the arrays
        this.Menu = getResources().getStringArray(R.array.entreprise_details);

        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,Menu);
        listView.setAdapter(itemsAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> list, View v, int pos, long id) {
                switch (pos) {
                    case 0:
                        Intent intent = new Intent(getApplicationContext() , ClientsDetailsActivity.class );
                        intent.putExtra("entreprise_name", String.valueOf(entreprise_name_intent) );
                        startActivity(intent);
                        break;
                    case 1 :
                        Intent intent2 = new Intent (getApplicationContext() ,EmployeeDetailsActivity.class );
                        intent2.putExtra("entreprise_name", String.valueOf(entreprise_name_intent) );
                        startActivity(intent2);
                        break;

                } }});


    }


    @Override
    protected void onStart() {
        super.onStart();

    }



}
