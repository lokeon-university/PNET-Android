package es.manguca;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

public class ImportantDatesActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_important_dates);

        Toolbar bottom_toolbar = (Toolbar) findViewById(R.id.bottom_toolbar);
        setSupportActionBar(bottom_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        bottom_toolbar.setTitle("");
        bottom_toolbar.setSubtitle("");

        Toolbar top_toolbar = (Toolbar) findViewById(R.id.top_toolbar);
        setSupportActionBar(top_toolbar);
        getSupportActionBar().setTitle(R.string.importantdates);

        Button btn_home = (Button)findViewById(R.id.button_home);
        Button btn_schedule = (Button)findViewById(R.id.button_schedule);
        Button btn_assistant = (Button)findViewById(R.id.button_assistant);
        Button btn_location = (Button)findViewById(R.id.button_location);
        Button btn_date = (Button)findViewById(R.id.button_date);

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ImportantDatesActivity.this, MainActivity.class));
            }
        });

        btn_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ImportantDatesActivity.this, ProgramActivity.class));
            }
        });

        btn_assistant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ImportantDatesActivity.this, AssistantActivity.class));
            }
        });

        btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ImportantDatesActivity.this, LocationActivity.class));
            }
        });

        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ImportantDatesActivity.this, ImportantDatesActivity.class));
            }
        });


        new GetAssistant().execute();
        //new PostAssistant().execute();
        //new PutAssistant().execute();
        //new DeleteAssistant().execute();
    }

    class GetAssistant extends AsyncTask<Void,Void,String>{

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
            TextView textView = (TextView) findViewById(R.id.texto);

            if(results!= null) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(results);
                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonobject = jsonArray.getJSONObject(i);
                        String name       = jsonobject.getString("name");
                        textView.setText(name);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();}
            }
        }
    }

    class PostAssistant extends AsyncTask<Void,Void,String>{
        @Override
        protected String doInBackground(Void... strings) {
            String text = null;
            BufferedWriter bufferedWriter = null;
            HttpURLConnection urlConnection = null;

            try {
                JSONObject dataToSend = new JSONObject();

                dataToSend.put("name", "Pepe");
                dataToSend.put("surname", "Manolo");
                dataToSend.put("email", 1);
                dataToSend.put("telephone", 1);
                dataToSend.put("dni",1231231);
                dataToSend.put("birthday",1111);
                dataToSend.put("date_insription",1231);

                URL url = new URL(getResources().getString(R.string.ip_node));
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(10000);
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.connect();

                if (urlConnection.getResponseCode() == 200) {
                    text = "Post successfully !";
                } else {
                    text =  "Post failed !";
                }

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
            // aqui meter cosa de que ha enviado los datos
        }
    }

    class PutAssistant extends AsyncTask<Void,Void,String> {

        @Override
        protected String doInBackground(Void... stings) {
            String text = null;
            BufferedWriter bufferedWriter = null;
            HttpURLConnection urlConnection = null;

            try {
                JSONObject dataToSend = new JSONObject();

                dataToSend.put("name", "Pepe111111111111");
                dataToSend.put("surname", "Manolo2asdasda");
                dataToSend.put("email", "1@email.com");
                dataToSend.put("telephone", 1);
                dataToSend.put("dni",1231231);
                dataToSend.put("birthday",1111);
                dataToSend.put("date_insription",1231);

                URL url = new URL(getResources().getString(R.string.ip_node) + "/5ec6a2f099915247707e6e6f");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(10000);
                urlConnection.setRequestMethod("PUT");
                urlConnection.setDoOutput(true);
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.connect();

                OutputStream outputStream = urlConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                bufferedWriter.write(dataToSend.toString());
                bufferedWriter.flush();

                if (urlConnection.getResponseCode() == 200) {
                    text = "Update successfully !";
                } else {
                    text =  "Update failed !";
                }

            } catch (Exception e ) {
                return e.toString();
            } finally {
                if(urlConnection != null)
                    urlConnection.disconnect();
            }
            return text;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // aqui meter mensaje de que se ha creado
        }
    }

    class DeleteAssistant extends AsyncTask<Void,Void,String> {

        @Override
        protected String doInBackground(Void... strings) {
            String text = null;
            HttpURLConnection urlConnection = null;

            try {
                URL url = new URL(getResources().getString(R.string.ip_node) + "/5ec65019b5e1930c6d10d3e0");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(10000);
                urlConnection.setRequestMethod("DELETE");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.connect();

                if (urlConnection.getResponseCode() == 200) {
                    text = "Delete successfully !";
                } else {
                    text = "Delete failed !";
                }

            } catch (Exception e) {
                return e.toString();
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }
            return text;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // aqui meter mensaje de lo que sea
        }
    }
}