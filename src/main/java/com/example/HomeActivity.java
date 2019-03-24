package com.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import com.example.login.R;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HomeActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private Button bsettings;
    private Button gps;
    private Button goButton;
    private TextView user_name, full_name, email, gender,age, weight;
    private User usr;

    public final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);
        user_name = (TextView) findViewById(R.id.user_name);
        full_name = (TextView) findViewById(R.id.full_name);
        email = (TextView) findViewById(R.id.email);
        gender = (TextView) findViewById(R.id.gender);
        age = (TextView) findViewById(R.id.age);
        weight = (TextView) findViewById(R.id.weight);

        usr = (User) getIntent().getSerializableExtra("user");

        user_name.setText(usr.getUSR_ID());
        full_name.setText(usr.getName());
        email.setText(usr.getEmail());
        gender.setText(usr.getGender());
        age.setText(usr.getAge());
        weight.setText(usr.getWeight());

        mTextMessage = (TextView) findViewById(R.id.message);
        bsettings =(Button) findViewById(R.id.settings);
        gps = (Button) findViewById(R.id.gps);
        goButton = (Button) findViewById(R.id.Go);
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, ChronoActivity.class);
                startActivity(i);
            }
        });
        Button runButton = (Button) findViewById(R.id.runButton);
        runButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://63.33.59.91:8080/";
                try {
                    JSONObject jsonobj = new JSONObject();
                    jsonobj.put("key", "value");
                    usr = (User) getIntent().getSerializableExtra("user");
                    jsonobj.put("user", usr);
                    OkHttpClient cliente = new OkHttpClient();
                    RequestBody requestBody = RequestBody.create(JSON , jsonobj.toString());
                    Request request = new Request.Builder().url(url).post(requestBody).build();
                    //Rebo resposta
                    Response response = cliente.newCall(request).execute();
                    String jsonstring = response.body().string();
                    JsonObject jsonaco = new JsonParser().parse(jsonstring).getAsJsonObject();
                    String  id = jsonaco.get("id").toString();
                    String usr_name = jsonaco.get("user_name").toString();
                    String racename = jsonaco.get("race").toString();
                    String distance = jsonaco.get("distance").toString();
                    String spot = jsonaco.get("spot").toString();
                    String time = jsonaco.get("time").toString();
                    String date = jsonaco.get("date").toString();
                    Race race = new Race(id,usr_name,racename,distance,spot,time,date);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(HomeActivity.this, RecordingsActivity.class);
            }
        });


    }

}
