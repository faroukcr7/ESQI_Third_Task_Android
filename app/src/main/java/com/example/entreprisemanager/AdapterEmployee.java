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

public class AdapterEmployee  extends  RecyclerView.Adapter<AdapterEmployee.ViewHolder> {

    @NonNull
    @Override
    public AdapterEmployee.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context =  viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View clientView = inflater.inflate(R.layout.item_employee, viewGroup, false);

        // Return a new holder instance
        AdapterEmployee.ViewHolder viewHolder = new AdapterEmployee.ViewHolder(clientView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterEmployee.ViewHolder viewHolder, int i) {
        // Get the data model based on position
        ModelEmployee  modelEmployee = modelEmployeeList.get(i);

        // Set item views based on your views and data model

        TextView client_name = viewHolder.employee_name;
        client_name.setText("Employee Name : "+ modelEmployee.getEmployee_name() );

        TextView client_email = viewHolder.employee_email;
        client_email.setText("Employee Email : "+ modelEmployee.getEmployee_email() );

        TextView client_phonenumber = viewHolder.employee_phonenumber;
        client_phonenumber.setText("Employee Phone : "+ modelEmployee.getEmployee_phonenumber() );




    }

    @Override
    public int getItemCount() {
        return modelEmployeeList.size();
    }


    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        ImageView profleimage;
        TextView employee_name ,employee_email , employee_phonenumber  ;



        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            employee_name = (TextView) itemView.findViewById(R.id.employee_name);
            employee_email = (TextView) itemView.findViewById(R.id.employee_email);
            employee_phonenumber = (TextView) itemView.findViewById(R.id.employee_phonenumber);

        }
    }

    private List<ModelEmployee> modelEmployeeList;

    public AdapterEmployee(List<ModelEmployee>  modelEmployee) {
        modelEmployeeList = modelEmployee;
    }

}
