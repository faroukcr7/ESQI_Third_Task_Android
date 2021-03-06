package com.example.entreprisemanager;


import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.Iterator;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentEntreprise extends Fragment {


    public FragmentEntreprise() {
        // Required empty public constructor
    }

    ArrayList<ModelEntreprise> listentreprises ;
    TabledbHelper dbHelper;
    RecyclerView recyclerView;




    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listentreprises =new ArrayList<ModelEntreprise>();

        dbHelper = new TabledbHelper(getContext()) ;
        recyclerView= (RecyclerView) view.findViewById(R.id.ReceyclerViewMyEntreprises);

        ItemClickSupport.addTo(recyclerView)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        // do it
                        Log.d("CLICKED" ,String.valueOf(listentreprises.get(position).getEntreprise_email()) );

                        Intent intent = new Intent(getContext(), EntreprsieDetailsActivity.class);
                        intent.putExtra("entreprise_name", String.valueOf(listentreprises.get(position).getEntreprise_name()));
                        startActivity(intent);
                    }
                });


        final String Token = viewAll();
        Log.d("TAG", Token.toString());
        final RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "http://192.168.43.139:8080/api/users/me";

        final JSONObject U = new JSONObject();
        try {
            U.put("x-auth-token", Token);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjectReques = new JsonObjectRequest(Request.Method.GET, url, U ,  new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    // Initialize contacts
                    //
                    JSONArray jsonArray =  response.getJSONArray("entreprises") ;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonArray.getJSONObject(i);
                      ModelEntreprise modelEntreprise = new ModelEntreprise(
                                jsonArray.getJSONObject(i).getString("entreprise_name") ,
                                jsonArray.getJSONObject(i).getString("entreprise_email") ,
                                jsonArray.getJSONObject(i).getString("entreprise_phonenumber") ,
                                jsonArray.getJSONObject(i).getString("admin_name")
                        ) ;

                        listentreprises.add(modelEntreprise);
                    }

                    // Create adapter passing in the sample user data
                    AdapterMyEntreprises adapter = new AdapterMyEntreprises(listentreprises);
                    // Attach the adapter to the recyclerview to populate items
                    recyclerView.setAdapter(adapter);
                    // Set layout manager to position the items
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    // That's all!


                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"That didn't work!" , Toast.LENGTH_LONG).show();
                Toast.makeText(getContext(),String.valueOf(error) , Toast.LENGTH_LONG).show();
            }
        })
        { //no semicolon or coma
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("x-auth-token",Token);
                params.put("Content-Type","application/json");

                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(jsonObjectReques);

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_entreprises, container, false);
    }






    public String viewAll() {
        String id ="" ;
        String token ="" ;

        Cursor res = dbHelper.getAllData();
        if(res.getCount() == 0) {
            // show message
            Toast.makeText(getContext(),"Error"+"Local Db is empty" , Toast.LENGTH_LONG).show();
        }

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            token=res.getString(3) ;

        }
        Toast.makeText(getContext(),"Data"+buffer.toString() , Toast.LENGTH_LONG).show();
        return  token ;
    }

}
