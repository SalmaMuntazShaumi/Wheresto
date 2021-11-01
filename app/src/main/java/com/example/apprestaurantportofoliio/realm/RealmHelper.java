package com.example.apprestaurantportofoliio.realm;

import android.util.Log;

import com.example.apprestaurantportofoliio.model.Model;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmHelper {
    Realm realm;

    public RealmHelper(Realm realm) {
        this.realm = realm;
    }

    // To save data into database
    public void save(final Model Model) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (realm != null) {
                    Log.e("Created", "Database was created");
                    Number currentIdNum = realm.where(Model.class).max("id");
                    int nextId;
                    if (currentIdNum == null) {
                        nextId = 1;
                    } else {
                        nextId = currentIdNum.intValue() + 1;
                    }
                    Model.setId(nextId);
                    Model model = realm.copyToRealm(Model);
                    Log.d("Realm", "Item saved");
                } else {
                    Log.e("ppppp", "execute: Database not Exist");
                }
            }
        });
    }

    // TO get all data from database
    public List<Model> getAllSports() {
        RealmResults<Model> results = realm.where(Model.class).findAll();
        return results;
    }

    // To delete data from database
    public void delete(Integer id) {
        final RealmResults<Model> model = realm.where(Model.class).equalTo("id", id).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                model.deleteFromRealm(0);
            }
        });
    }
}
