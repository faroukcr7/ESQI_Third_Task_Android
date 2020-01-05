package com.example.entreprisemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterWork extends RecyclerView.Adapter<AdapterWork.ViewHolder> {


    @NonNull
    @Override
    public AdapterWork.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context =  viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View clientView = inflater.inflate(R.layout.item_work, viewGroup, false);

        // Return a new holder instance
        AdapterWork.ViewHolder viewHolder = new AdapterWork.ViewHolder(clientView);
        return viewHolder;
        }


        @Override
        public void onBindViewHolder(@NonNull AdapterWork.ViewHolder viewHolder, int i) {
        // Get the data model based on position
        ModelWork  modelWork = modelWorkList.get(i);

        // Set item views based on your views and data model

        TextView client_name = viewHolder.client_name;
        client_name.setText("Client Name : "+ modelWork.getClient_name() );

        TextView client_email = viewHolder.entreprise_name;
        client_email.setText("Entreprise  Name : "+ modelWork.getEntreprise_name() );

        TextView client_phonenumber = viewHolder.id_work;
        client_phonenumber.setText("ID work  : "+ modelWork.getId_work() );


        }

        @Override
        public int getItemCount() {
                return modelWorkList.size();
                }

        // Provide a direct reference to each of the views within a data item
        // Used to cache the views within the item layout for fast access
        public class ViewHolder extends RecyclerView.ViewHolder {
            // Your holder should contain a member variable
            // for any view that will be set as you render a row
            ImageView profleimage;
            TextView client_name ,entreprise_name , id_work  ;



            // We also create a constructor that accepts the entire item row
            // and does the view lookups to find each subview
            public ViewHolder(View itemView) {
                // Stores the itemView in a public final member variable that can be used
                // to access the context from any ViewHolder instance.
                super(itemView);
                client_name = (TextView) itemView.findViewById(R.id.client_name);
                entreprise_name = (TextView) itemView.findViewById(R.id.entreprise_name);
                id_work = (TextView) itemView.findViewById(R.id.id_work);

            }
        }

            private List<ModelWork> modelWorkList;

            public AdapterWork(List<ModelWork>  modelWorks) {
                modelWorkList = modelWorks;
            }


}
