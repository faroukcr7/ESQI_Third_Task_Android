package com.example.entreprisemanager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    TabledbHelper dbHelper;
    BottomNavigationView BottNav;
    FragmenMyWork fragmentMyWork = new FragmenMyWork();
    FragmentEntreprise fragmentEntreprise = new FragmentEntreprise();
    FragmentProfile fragmentProfile = new FragmentProfile();

    FragmentManager transaction ;

    // contact fragment attribuets
    ListView listView ;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottNav = (BottomNavigationView) findViewById(R.id.BottNav);
        dbHelper = new TabledbHelper(this) ;
        BottNav.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {

                            case R.id.entreprise:
                                transaction = getSupportFragmentManager();
                                transaction.beginTransaction().replace(R.id.framelayout,fragmentEntreprise).commit() ;

                                break;
                            case R.id.mywork:
                                transaction = getSupportFragmentManager();
                                transaction.beginTransaction().replace(R.id.framelayout,fragmentMyWork).commit() ;
                                break;
                            case R.id.myprofile:
                                transaction = getSupportFragmentManager();
                                transaction.beginTransaction().replace(R.id.framelayout,fragmentProfile).commit() ;
                                break;


                        }
                        return false;
                    }
                });
    }


    public void Logout(View view) {
        dbHelper.deleteData();
        Toast.makeText(getApplicationContext(),"Logout successfully ",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext() , LoginActivity.class);
        startActivity(intent);

    }

    public  void  goAddActivity (View view) {
        Intent intent = new Intent(getApplicationContext() , CreateEntrepriseActivity.class);
        startActivity(intent);


    }

}
