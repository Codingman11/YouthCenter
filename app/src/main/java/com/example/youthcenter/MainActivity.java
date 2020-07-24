package com.example.youthcenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youthcenter.ui.login.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {


    private Button button;
    private String fileXML = "data.xml";
    RecyclerView recyclerView;
    private Button feedBackBtn, readXMLBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //Settings all methods

        //toLoginActivity();
        addEvent();
        recyclerView();


        writeFile(this);
        readXML(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");


        if (username.contains("admin")) {
            menu.add(0, 0, Menu.NONE, "Asetukset");
            menu.add(0, 1, Menu.NONE, "Tapahtumat");
        } else if (username.contains("guest")) {
            menu.add(0, 0, Menu.NONE, "");
        }
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case 0:
                startActivity(new Intent(this, SettingsActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    public void recyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        EventAdapter adapter = new EventAdapter(this);
        recyclerView.setAdapter(adapter);
    }


    public void addEvent() {

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setAlpha(0.7f);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateEventActivity.class);
                startActivity(intent);
            }
        });
    }




    public void writeFile(Context context) {
        WriteAndRead writeAndRead = WriteAndRead.getInstance();

        writeAndRead.writeXML(context);

    }

    public void readXML(final Context context) {
        WriteAndRead writeAndRead = WriteAndRead.getInstance();
        writeAndRead.parseXML(context);
    }
}
