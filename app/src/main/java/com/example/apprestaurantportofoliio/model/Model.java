package com.example.apprestaurantportofoliio.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Model extends RealmObject {
    @PrimaryKey
    Integer id;
    double rate;
    String name,image,location;

    public Model(){

    }


    public Model(String image, String name, String location, double rate) {
        this.name = name;
        this.image = image;
        this.location = location;
        this.rate = rate;
    }



    public void setId(int id){ this.id = id; }

    public int getId(){ return id; }

    public void setname(String name) { this.name = name; }

    public String getName() { return name; }

    public void setImage(String image) { this.image = image; }

    public String getImage() { return image; }

    public void setLocation(String location) { this.location = location; }

    public String getLocation() { return location; }

    public void setRate(double rate) { this.rate = rate; }

    public double getRate() { return rate; }


}
