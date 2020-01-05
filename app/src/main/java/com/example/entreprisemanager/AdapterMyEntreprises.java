package com.example.entreprisemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class AdapterMyEntreprises  extends RecyclerView.Adapter<AdapterMyEntreprises.ViewHolder> {


    @NonNull
    @Override
    public AdapterMyEntreprises.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context =  viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View entrepriseView = inflater.inflate(R.layout.item_entreprise, viewGroup, false);

        // Return a new holder instance
        AdapterMyEntreprises.ViewHolder viewHolder = new AdapterMyEntreprises.ViewHolder(entrepriseView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMyEntreprises.ViewHolder viewHolder, int i) {
        // Get the data model based on position
        ModelEntreprise ModelEntreprise = modelentrepriselist.get(i);

        // Set item views based on your views and data model

        TextView entreprise_name = viewHolder.entreprise_name;
        entreprise_name.setText("Entreprise Name : "+ ModelEntreprise.getEntreprise_email() );

        TextView entreprise_email = viewHolder.entreprise_email;
        entreprise_email.setText("Entreprise Email : " + ModelEntreprise.getEntreprise_email());

        TextView entreprise_phonenumber = viewHolder.entreprise_phonenumber;
        entreprise_phonenumber.setText("Entreprise Phonenumber : " +ModelEntreprise.getEntreprise_phonenumber());


        TextView admin_name = viewHolder.admin_name;
        admin_name.setText("Admin : " + ModelEntreprise.getAdmin_name());





    }


    @Override
    public int getItemCount() {
        return modelentrepriselist.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        ImageView profleimage;
        TextView entreprise_name ,entreprise_email , entreprise_phonenumber ,admin_name ;



        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            entreprise_name = (TextView) itemView.findViewById(R.id.entreprise_name);
            entreprise_email = (TextView) itemView.findViewById(R.id.entreprise_email);
            entreprise_phonenumber = (TextView) itemView.findViewById(R.id.entreprise_phonenumber);
            admin_name = (TextView) itemView.findViewById(R.id.admin_name);

        }

    }

    private List<ModelEntreprise> modelentrepriselist;

    public AdapterMyEntreprises(List<ModelEntreprise>  ModelEntreprise) {
        modelentrepriselist = ModelEntreprise;
    }


}
