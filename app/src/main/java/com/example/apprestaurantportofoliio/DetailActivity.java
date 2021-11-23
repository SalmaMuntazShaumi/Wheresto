package com.example.apprestaurantportofoliio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;

public class DetailActivity extends AppCompatActivity {

    RealmHelper realmHelper;
    TextView tv_loc;

    Double rate;
    boolean isFavorite;
    String name, image, desc, loc, idResto, address;
    private String API_URL;
    Model teamModel;
    private ProgressDialog progressDialog;
    private MenuAdapter menuAdapter;
    private RecyclerView rv_menu;
    private FloatingActionButton fav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_detail);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        fav = findViewById(R.id.btn_fav);
        tv_loc = findViewById(R.id.tv_alamat);
        TextView tv_name = findViewById(R.id.tv_resto_name);
        TextView tv_rate = findViewById(R.id.tv_rate_detail);
        TextView tv_desc = findViewById(R.id.tv_desc);
        ImageView img_team = findViewById(R.id.img_detail);
        rv_menu = findViewById(R.id.rv_menulist);

        Bundle bundle = getIntent().getExtras();
        Realm.init(DetailActivity.this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().allowWritesOnUiThread(true).build();
        Realm realm = Realm.getInstance(configuration);
        realmHelper = new RealmHelper(realm);

        if (bundle != null) {
            loc = bundle.getString("city");
            name = bundle.getString("name");
            rate = bundle.getDouble("rating");
            desc = bundle.getString("description");
            image = bundle.getString("image");
//            menu = bundle.getString("menus");
            isFavorite = bundle.getBoolean("Favorite");
            idResto = bundle.getString("idResto");

            List<Model> list = realmHelper.getRestoById(idResto);
            isFavorite = list.size() > 0;

            applyImage();

            API_URL = "https://restaurant-api.dicoding.dev/detail/"+idResto;
            getNewData();

            tv_name.setText(name);
            tv_desc.setText(desc);
//            tv_menu.setText(menu);
            tv_rate.setText(rate.toString());
            Picasso.get().load(image).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher_round).into(img_team);

            // Set up
            Realm.init(this);
            RealmConfiguration configuration1 = new RealmConfiguration.Builder().allowWritesOnUiThread(true).build();
            Realm realm1 = Realm.getInstance(configuration1);
            realmHelper = new RealmHelper(realm1);


            fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isFavorite) {
                        realmHelper.delete(idResto);
                        changeFavorite(false);
                        Toast.makeText(DetailActivity.this, "Delete Success", Toast.LENGTH_SHORT).show();
                    } else {
                        realmHelper.save(teamModel);

                        Toast.makeText(DetailActivity.this, "Berhasil Disimpan!", Toast.LENGTH_SHORT).show();
                        changeFavorite(true);
                    }
                }
            });


        }

    }

    private void changeFavorite(boolean favorite) {
        isFavorite = favorite;
        applyImage();
    }

    private void applyImage() {
        if (isFavorite) fav.setImageResource(R.drawable.ic_baseline_bookmark_24);
        else fav.setImageResource(R.drawable.ic_baseline_bookmark_border_24);
    }

    private void getNewData() {
        progressDialog.show();
        Log.d("getData", "yes");
        AndroidNetworking.get(API_URL)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject teamsArray = response.getJSONObject("restaurant");
                            JSONObject menus = teamsArray.getJSONObject("menus");
                            JSONArray categories = teamsArray.getJSONArray("categories");
                            JSONArray foods = menus.getJSONArray("foods");
                            JSONArray drinks = menus.getJSONArray("drinks");

                            RealmList<String> foodsString = new RealmList<>();
                            RealmList<String> drinksString = new RealmList<>();
                            RealmList<String> categoriesString = new RealmList<>();

                            for (int i = 0; i < foods.length(); i++) {
                                JSONObject object = foods.getJSONObject(i);
                                String name = object.getString("name");
                                foodsString.add(name);
                            }

                            for (int i = 0; i < categories.length(); i++) {
                                JSONObject object = categories.getJSONObject(i);
                                String name = object.getString("name");
                                categoriesString.add(name);
                            }

                            for (int i = 0; i < drinks.length(); i++) {
                                JSONObject object = drinks.getJSONObject(i);
                                String name = object.getString("name");
                                drinksString.add(name);
                            }

                            menuAdapter = new MenuAdapter(foodsString);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                            rv_menu.setLayoutManager(layoutManager);
                            rv_menu.setAdapter(menuAdapter);

                            address = teamsArray.getString("address");

                            teamModel = new Model(idResto, name, desc, loc, address, image, rate, categoriesString, foodsString, drinksString, isFavorite);

                            tv_loc.setText(address);


                            Log.d("ok", address);

                            Log.d("arrsize", "" + teamsArray.length());
                            progressDialog.dismiss();

                        } catch (Exception e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        progressDialog.dismiss();
                    }
                });
    }

}