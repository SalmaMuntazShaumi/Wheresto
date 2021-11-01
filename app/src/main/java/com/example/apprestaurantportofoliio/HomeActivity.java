package com.example.apprestaurantportofoliio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.apprestaurantportofoliio.adapter.RestoAdapter;
import com.example.apprestaurantportofoliio.model.Bookmark;
import com.example.apprestaurantportofoliio.model.Model;
import com.example.apprestaurantportofoliio.model.UserProfile;
import com.example.apprestaurantportofoliio.realm.RealmHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class HomeActivity extends AppCompatActivity {


    private final String API_URL = "https://restaurant-api.dicoding.dev/list";
    private final String IMAGE_URL = "https://restaurant-api.dicoding.dev/images/medium/";
    private RecyclerView recyclerView;
    ArrayList<Model> arrayList;
    RestoAdapter restoAdapter;
    EditText SearchView;
    CharSequence search="";
    Realm realm;
    RealmHelper realmHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FloatingActionButton bm_btn = findViewById(R.id.bm_btn);
        ImageButton user_btn  = findViewById(R.id.profile_btn);


        recyclerView = findViewById(R.id.list);
        SearchView = findViewById(R.id.search_nav);
        arrayList = new ArrayList<>();


        bm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Bookmark.class);
                startActivity(intent);
            }
        });

        user_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserProfile.class);
                startActivity(intent);
            }
        });

        SearchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                restoAdapter.getFilter().filter(charSequence);
                search = charSequence;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });

        //Set up Realm
        Realm.init(HomeActivity.this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().allowWritesOnUiThread(true).build();
        realm = Realm.getInstance(configuration);

        getData();
    }

    private void getData() {
        Log.d("getData", "yes");
        AndroidNetworking.get(API_URL)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray teamsArray = response.getJSONArray("restaurants");
                            for (int i = 0; i < teamsArray.length(); i++) {
                                JSONObject teamObject = teamsArray.getJSONObject(i);
                                String name = teamObject.getString("name");
                                String location = teamObject.getString("city");
                                double rate = teamObject.getDouble("rating");
                                String image = IMAGE_URL +  teamObject.getString("pictureId");
                                arrayList.add(new Model(image, name, location, rate));

                            }

                            Log.d("arrsize", ""+teamsArray.length());

                            RecyclerView recyclerView = findViewById(R.id.list);
                            int numberOfColumns = 2;
                            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), numberOfColumns));
                            restoAdapter = new RestoAdapter(getApplicationContext(), arrayList);
                            recyclerView.setAdapter(restoAdapter);

                            restoAdapter.setOnItemClickListener(new RestoAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position) {
                                    Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                                    intent.putExtra("name", arrayList.get(position).getName());
                                    intent.putExtra("location", arrayList.get(position).getLocation());
                                    intent.putExtra("rate", arrayList.get(position).getRate());
                                    intent.putExtra("image", arrayList.get(position).getImage());
                                    startActivity(intent);
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }
}