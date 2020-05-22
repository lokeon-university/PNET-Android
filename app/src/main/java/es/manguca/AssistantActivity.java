package es.manguca;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import es.manguca.assistant.AddAssistantActivity;
import es.manguca.Adapters.MyRecyclerViewAdapter;

public class AssistantActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener{

    private MyRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<Person> person = new ArrayList<>();
    private Bundle bundle = new Bundle();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assistant);
        //DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
                //DividerItemDecoration.VERTICAL);
        //recyclerView.addItemDecoration(dividerItemDecoration);

        Toolbar bottom_toolbar = (Toolbar) findViewById(R.id.bottom_toolbar);
        setSupportActionBar(bottom_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        bottom_toolbar.setTitle("");
        bottom_toolbar.setSubtitle("");

        Toolbar top_toolbar = (Toolbar) findViewById(R.id.top_toolbar);
        setSupportActionBar(top_toolbar);
        getSupportActionBar().setTitle(R.string.assistant);

        Button btn_home = (Button)findViewById(R.id.button_home);
        Button btn_schedule = (Button)findViewById(R.id.button_schedule);
        Button btn_assistant = (Button)findViewById(R.id.button_assistant);
        Button btn_location = (Button)findViewById(R.id.button_location);
        Button btn_date = (Button)findViewById(R.id.button_date);
        Button btn_add_assistant = (Button) findViewById(R.id.button_add_assistant);

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AssistantActivity.this, MainActivity.class));
            }
        });

        btn_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AssistantActivity.this, ProgramActivity.class));
            }
        });

        btn_assistant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AssistantActivity.this, AssistantActivity.class));
            }
        });

        btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AssistantActivity.this, LocationActivity.class));
            }
        });

        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AssistantActivity.this, ImportantDatesActivity.class));
            }
        });

       btn_add_assistant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AssistantActivity.this, AddAssistantActivity.class));
            }
        });

        new GetAssistant().execute();

    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, AssistantDetailActivity.class);
        bundle.putString("name", adapter.getItem(position).getName());
        System.out.println("Nombre: " + adapter.getItem(position).getName());
        bundle.putString("lastname", adapter.getItem(position).getLastname());
        bundle.putString("email", adapter.getItem(position).getEmail());
        bundle.putString("phone", adapter.getItem(position).getPhone());
        bundle.putString("DNI", adapter.getItem(position).getDni());
        bundle.putString("birth", adapter.getItem(position).getBirth());
        bundle.putString("insDate",adapter.getItem(position).getInsDate());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    class GetAssistant extends AsyncTask<Void,Void,String> {

        @Override
        protected String doInBackground(Void... strings) {
            String text = null;
            HttpURLConnection urlConnection = null;

            try {
                URL url = new URL(getResources().getString(R.string.ip_node));
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(10000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                text = new Scanner(inputStream).useDelimiter("\\A").next();

            } catch (Exception e ) {
                return e.toString();
            } finally {
                if(urlConnection != null)
                    urlConnection.disconnect();
            }
            return text;
        }

        @Override
        protected void onPostExecute(String results) {
            super.onPostExecute(results);

            if(results!= null) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(results);
                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonobject = jsonArray.getJSONObject(i);
                        System.out.println("Name: " + jsonobject.getString("name"));
                        person.add(new Person(jsonobject.getString("_id"),
                                jsonobject.getString("name"),
                                jsonobject.getString("surname"),
                                jsonobject.getString("email"),
                                jsonobject.getString("telephone"),
                                jsonobject.getString("dni"),
                                jsonobject.getString("birthday"),
                                jsonobject.getString("date_insription")));
                    }

                    recyclerView = findViewById(R.id.rvAnimals);
                    recyclerView.setLayoutManager(new LinearLayoutManager(AssistantActivity.this));
                    adapter = new MyRecyclerViewAdapter(AssistantActivity.this, person);
                    adapter.setClickListener(AssistantActivity.this);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();}
            }
        }
    }


}