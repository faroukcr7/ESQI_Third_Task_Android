package com.example.entreprisemanager;

import java.util.ArrayList;

public class ModelClient {

    private String client_name , client_email ,client_phonenumber;

    public ModelClient () {
    }

    public ModelClient ( String client_name ,String client_email ,String client_phonenumber ) {
        this.client_name = client_name;
        this.client_email = client_email;
        this.client_phonenumber = client_phonenumber;

    }
    public String getClient_phonenumber() {
        return client_phonenumber;
    }

    public void setClient_phonenumber(String client_phonenumber) {
        this.client_phonenumber = client_phonenumber;
    }

    public String getClient_email() {
        return client_email;
    }

    public void setClient_email(String client_email) {
        this.client_email = client_email;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    private static int lastContactId = 0;

    public static ArrayList<ModelClient> createEntreprisesList(int numContacts) {
        ArrayList<ModelClient> listclients = new ArrayList<ModelClient>();

        for (int i = 1; i <= numContacts; i++) {
            listclients.add(new ModelClient("farouk "+i, "fa"+i+"@gmail.com", "0035193268609"+i ) );
        }

        return listclients;
    }
}
