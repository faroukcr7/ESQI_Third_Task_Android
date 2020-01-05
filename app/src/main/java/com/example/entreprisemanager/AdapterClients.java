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

public class AdapterClients extends RecyclerView.Adapter<AdapterClients.ViewHolder> {

    @NonNull
    @Override
    public AdapterClients.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context =  viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View clientView = inflater.inflate(R.layout.item_client, viewGroup, false);

        // Return a new holder instance
        AdapterClients.ViewHolder viewHolder = new AdapterClients.ViewHolder(clientView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterClients.ViewHolder viewHolder, int i) {
        // Get the data model based on position
        ModelClient  modelClient = modelClientslist.get(i);

        // Set item views based on your views and data model

        TextView client_name = viewHolder.client_name;
        client_name.setText("Client Name : "+ modelClient.getClient_name() );

        TextView client_email = viewHolder.client_email;
        client_email.setText("Client Email : "+ modelClient.getClient_email() );

        TextView client_phonenumber = viewHolder.client_phonenumber;
        client_phonenumber.setText("Client Phone : "+ modelClient.getClient_phonenumber() );




    }

    @Override
    public int getItemCount() {
        return modelClientslist.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        ImageView profleimage;
        TextView client_name ,client_email , client_phonenumber  ;



        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            client_name = (TextView) itemView.findViewById(R.id.client_name);
            client_email = (TextView) itemView.findViewById(R.id.client_email);
            client_phonenumber = (TextView) itemView.findViewById(R.id.client_phonenumber);

        }
    }

    private List<ModelClient> modelClientslist;

    public AdapterClients(List<ModelClient>  modelClients) {
        modelClientslist = modelClients;
    }

}
