package com.example.apprestaurantportofoliio.model;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.apprestaurantportofoliio.R;
import com.example.apprestaurantportofoliio.adapter.RestoAdapter;
import com.example.apprestaurantportofoliio.realm.RealmHelper;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class Bookmark extends AppCompatActivity {

    Realm realm;
    RealmHelper realmHelper;
    RecyclerView recyclerView;
    RestoAdapter adapter;
    List<Model> models;
    Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        models = new ArrayList<>();

        recyclerView = findViewById(R.id.bm_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        getSupportActionBar().hide();

        // Setup Realm
        RealmConfiguration configuration = new RealmConfiguration.Builder().allowWritesOnUiThread(true).build();
        realm.init(this);
        realm = Realm.getInstance(configuration);
        realmHelper = new RealmHelper(realm);

        models = realmHelper.getAllSports();

        show();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        adapter.notify();
        show();
    }

    public void show(){
        adapter = new RestoAdapter(this, models);
        recyclerView.setAdapter(adapter);
    }

    public boolean onContextItemSelected(MenuItem item){

        switch (item.getItemId()){
            case 1:
                id = models.get(item.getGroupId()).getId();
                realmHelper.delete(id);
                Toast.makeText(this, "Item deleted", Toast.LENGTH_SHORT).show();
                finish();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
