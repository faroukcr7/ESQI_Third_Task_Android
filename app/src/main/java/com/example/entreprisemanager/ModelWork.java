package com.example.entreprisemanager;

public class ModelWork {
    private  String  id_work , entreprise_name , client_name ,work_times ;


    public ModelWork (String  id_work ,String entreprise_name ,String client_name ) {
        this.id_work = id_work ;
        this.entreprise_name = entreprise_name ;
        this.client_name = client_name ;


    }
    public String getId_work() {
        return id_work;
    }

    public void setId_work(String id_work) {
        this.id_work = id_work;
    }

    public String getEntreprise_name() {
        return entreprise_name;
    }

    public void setEntreprise_name(String entreprise_name) {
        this.entreprise_name = entreprise_name;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }
}
