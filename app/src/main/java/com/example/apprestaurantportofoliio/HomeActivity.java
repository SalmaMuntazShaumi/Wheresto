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
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
//import com.example.apprestaurantportofoliio.detail.DetailModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

public class HomeActivity extends AppCompatActivity {


    private final String API_URL = "https://restaurant-api.dicoding.dev/list";
    private final String IMAGE_URL = "https://restaurant-api.dicoding.dev/images/large/";
    private RecyclerView recyclerView;
    ArrayList<Model> arrayList;
    RestoAdapter restoAdapter;
    EditText SearchView;
    CharSequence search="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
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

        getData();
    }

    private void getData(){
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
                                String desc = teamObject.getString("description");
                                double rate = teamObject.getDouble("rating");
                                String image = IMAGE_URL +  teamObject.getString("pictureId");
                                String id = teamObject.getString("id");
                                RealmList<String> emptyList = new RealmList<>();

                                Model model = new Model(id, name, desc, location, "", image, rate, emptyList, emptyList, emptyList, false);
                                arrayList.add(model);
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
                                    intent.putExtra("city", arrayList.get(position).getCity());
                                    intent.putExtra("rating", arrayList.get(position).getRating());
                                    intent.putExtra("description", arrayList.get(position).getDesc());
                                    intent.putExtra("address", arrayList.get(position).getAddress());
                                    intent.putExtra("image", arrayList.get(position).getPictureId());
                                    intent.putExtra("idResto", arrayList.get(position).getId());
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