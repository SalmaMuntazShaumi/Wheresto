package com.example.apprestaurantportofoliio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

//import com.example.apprestaurantportofoliio.detail.DetailModel;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class Bookmark extends AppCompatActivity {

    RecyclerView recyclerView;
    Realm realm;
    RealmHelper realmHelper;
    List<Model> teamlist;
    BookmarkAdapter teamAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_bookmark);

        recyclerView = findViewById(R.id.bm_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Setup Realm
        realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().allowWritesOnUiThread(true).build();
        realm = Realm.getInstance(configuration);

        realmHelper = new RealmHelper(realm);
        teamlist = new ArrayList<>();

        teamlist = realmHelper.getAllResto();

        show();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        teamAdapter.notifyDataSetChanged();
        show();
    }

    public void show(){

        TextView tv_item = findViewById(R.id.tv_item);

        if (teamlist != null) {
            if (teamlist.isEmpty()) tv_item.setVisibility(View.VISIBLE);
            else tv_item.setVisibility(View.INVISIBLE);
        } else tv_item.setVisibility(View.INVISIBLE);

        teamAdapter = new BookmarkAdapter(this, teamlist);
        recyclerView.setAdapter(teamAdapter);

        teamAdapter.setOnItemClickListener(new BookmarkAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("name", teamlist.get(position).getName());
                intent.putExtra("city", teamlist.get(position).getCity());
                intent.putExtra("rating", teamlist.get(position).getRating());
                intent.putExtra("description", teamlist.get(position).getDesc());
                intent.putExtra("image", teamlist.get(position).getPictureId());
                intent.putExtra("Favorite", true);
                intent.putExtra("idResto", teamlist.get(position).getId());
                startActivity(intent);
            }
        });

    }
}
