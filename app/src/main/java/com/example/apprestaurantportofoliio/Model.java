package com.example.apprestaurantportofoliio;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Model extends  RealmObject{

    @PrimaryKey
    String id;
    String name, desc, city, address, pictureId;
    Double rating;
    RealmList<String> categories, foods, drinks;
    boolean isFavorite;

    public Model(){}

    public Model(String id, String name, String desc, String city, String address, String pictureId, Double rating, RealmList<String> categories, RealmList<String> foods, RealmList<String> drinks, boolean isFavorite) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.city = city;
        this.address = address;
        this.pictureId = pictureId;
        this.rating = rating;
        this.categories = categories;
        this.foods = foods;
        this.drinks = drinks;
        this.isFavorite = isFavorite;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public RealmList<String> getCategories() {
        return categories;
    }

    public void setCategories(RealmList<String> categories) {
        this.categories = categories;
    }

    public RealmList<String> getFoods() {
        return foods;
    }

    public void setFoods(RealmList<String> foods) {
        this.foods = foods;
    }

    public RealmList<String> getDrinks() {
        return drinks;
    }

    public void setDrinks(RealmList<String> drinks) {
        this.drinks = drinks;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
