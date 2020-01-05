package com.example.entreprisemanager;


import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentProfile extends Fragment {

    ImageView imageheader ;
    TextView  Username  , Email, Phonenumber, FullName , UserName , Account_type ;
    String   TextFullName , TextUsername , Textemail , Textphonenumber ;
    TabledbHelper dbHelper;

    public FragmentProfile() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbHelper = new TabledbHelper(getContext()) ;
        imageheader = (ImageView) view.findViewById(R.id.header_cover_image);
        Username = (TextView) view.findViewById(R.id.UserName);
        Email = (TextView) view.findViewById(R.id.email);
        Phonenumber = ( TextView) view.findViewById(R.id.PhoneNumber);
        FullName = (TextView) view.findViewById(R.id.FullName);
        Account_type = (TextView) view.findViewById(R.id.Account_type) ;


        Picasso.with(getContext())
                .load("https://images.unsplash.com/photo-1522071820081-009f0129c71c?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=750&q=80")
                .into(imageheader);


        final String Token = viewAll();
        Log.d("TAG", Token.toString());
        final RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "http://192.168.2.124:8080/api/users/me";

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
                boolean isInserted = false;
                try {
                    dbHelper.deleteData();
                    isInserted = dbHelper.insert(response.getString("_id"),response.getString("fullname"),
                            response.getString("email")  , Token  ,   response.getString("user_type"));

                    Log.d("TAG", response.toString());
                    FullName.setText(response.getString("fullname"));
                        Username.setText(response.getString("username"));
                        Email.setText(response.getString("email"));
                        Phonenumber.setText(response.getString("phonenumber"));
                        Account_type.setText(response.getString("user_type"));
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
                params.put("x-auth-token", Token);
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
        return inflater.inflate(R.layout.fragment__profile, container, false);
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

            id =  res.getString(0) ;
            Email.setText(res.getString(1));
            FullName.setText(res.getString(2));
            token=res.getString(3) ;

        }
        Toast.makeText(getContext(),"Data"+buffer.toString() , Toast.LENGTH_LONG).show();
        return  token ;
    }




}
