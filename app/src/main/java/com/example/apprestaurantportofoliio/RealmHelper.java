package com.example.apprestaurantportofoliio;

import android.util.Log;

//import com.example.apprestaurantportofoliio.detail.DetailModel;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmHelper {
    Realm realm;

    public RealmHelper(Realm realm) {
        this.realm = realm;
    }

    // To save data into database//import com.example.apprestaurantportofoliio.Model;
    public void save(final Model detailModel) {
        realm.executeTransaction(new Realm.Transaction() {

            @Override
            public void execute(Realm realm) {
                if (realm != null) {
                    Log.e("Created", "Database was created");
                    Model Models = realm.copyToRealm(detailModel);
                    Log.d("Realm", "Item saved");
                } else {
                    Log.e("ppppp", "execute: Database not Exist");
                }
            }
        });
    }

    public RealmResults<Model> getRestoById(String id) {
        final RealmResults<Model> results = realm.where(Model.class).equalTo("id", id).findAll();

        return results;
    }

    // TO get all data from database
    public RealmResults<Model> getAllResto() {
        RealmResults<Model> results = realm.where(Model.class).findAll();

        return results;
    }

    // To delete data from database
    public void delete(String id) {
        final RealmResults<Model> models = realm.where(Model.class).equalTo("id", id).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                models.deleteFromRealm(0);
            }
        });
    }
}
