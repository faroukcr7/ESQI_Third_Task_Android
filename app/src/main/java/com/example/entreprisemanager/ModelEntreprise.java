package com.example.entreprisemanager;

import java.util.ArrayList;

public class ModelEntreprise {
    private String  id  , entreprise_name ,entreprise_email , entreprise_phonenumber ,admin_name;


    public ModelEntreprise() {}

    public ModelEntreprise (String id   ,String entreprise_name , String entreprise_email ,String  entreprise_phonenumber ,String admin_name  ) {
        this.id = id;
        this.entreprise_name = entreprise_name;
        this.entreprise_email = entreprise_email;
        this.entreprise_phonenumber = entreprise_phonenumber;
        this.admin_name = admin_name;
    }

    public ModelEntreprise (String entreprise_name , String entreprise_email ,String  entreprise_phonenumber ,String admin_name  ) {
        this.entreprise_name = entreprise_name;
        this.entreprise_email = entreprise_email;
        this.entreprise_phonenumber = entreprise_phonenumber;
        this.admin_name = admin_name;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEntreprise_name() {
        return entreprise_name;
    }

    public void setEntreprise_name(String entreprise_name) {
        this.entreprise_name = entreprise_name;
    }

    public String getEntreprise_email() {
        return entreprise_email;
    }

    public void setEntreprise_email(String entreprise_email) {
        this.entreprise_email = entreprise_email;
    }

    public String getEntreprise_phonenumber() {
        return entreprise_phonenumber;
    }

    public void setEntreprise_phonenumber(String entreprise_phonenumber) {
        this.entreprise_phonenumber = entreprise_phonenumber;
    }

    public String getAdmin_name() {
        return admin_name;
    }

    public void setAdmin_name(String admin_name) {
        this.admin_name = admin_name;
    }


    private static int lastContactId = 0;

    public static ArrayList<ModelEntreprise> createEntreprisesList(int numContacts) {
        ArrayList<ModelEntreprise> listentreprises = new ArrayList<ModelEntreprise>();

        for (int i = 1; i <= numContacts; i++) {
            listentreprises.add(new ModelEntreprise("farouk "+i, "Cr"+i , "fa"+i+"@gmail.com", "0035193268609"+i ) );
        }

        return listentreprises;
    }

}
