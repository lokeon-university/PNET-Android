package es.manguca.assistant;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import es.manguca.AssistantActivity;
import es.manguca.R;
import es.manguca.Utils.LetterImageView;


public class AssistantDetailActivity extends AppCompatActivity {
    private TextView tname, tlastname, tdni, temail, tphone, tbirth, tinsDate;
    private LetterImageView ivLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assistant_detail);
        Toolbar bottom_toolbar = (Toolbar) findViewById(R.id.bottom_toolbar);

        setSupportActionBar(bottom_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        bottom_toolbar.setTitle("");
        bottom_toolbar.setSubtitle("");

        Toolbar top_toolbar = (Toolbar) findViewById(R.id.top_toolbar);
        setSupportActionBar(top_toolbar);
        getSupportActionBar().setTitle(R.string.detail_assistant);
        top_toolbar.setNavigationIcon(R.drawable.ic_arrow_back);

        top_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AssistantDetailActivity.this, AssistantActivity.class));
            }
        });

        tname = (TextView) findViewById(R.id.NameAssistant);
        tlastname = (TextView) findViewById(R.id.LastNameAssistant);
        tdni = (TextView) findViewById(R.id.DniAssistant);
        temail = (TextView) findViewById(R.id.EmailAssistant);
        tphone = (TextView) findViewById(R.id.PhoneAssistant);
        tbirth = (TextView) findViewById(R.id.BirthAssistant);
        tinsDate = (TextView) findViewById(R.id.InsDateAssistant);
        ivLogo = (LetterImageView) findViewById(R.id.iconAssistants);


        Bundle bundle = this.getIntent().getExtras();

        new GetOneAssistant().execute(bundle.getString("id"));
    }

    class GetOneAssistant extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String text = null;
            HttpURLConnection urlConnection = null;

            try {
                URL url = new URL(getResources().getString(R.string.ip_node) + "/" + strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(10000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                text = new Scanner(inputStream).useDelimiter("\\A").next();

            } catch (Exception e) {
                return e.toString();
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }
            return text;
        }

        @Override
        protected void onPostExecute(String results) {
            super.onPostExecute(results);

            if (results != null) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(results);
                    JSONObject jsonobject = jsonArray.getJSONObject(0);

                    tname.setText(jsonobject.getString("name"));
                    tlastname.setText(jsonobject.getString("surname"));
                    tdni.setText(jsonobject.getString("dni"));
                    temail.setText(jsonobject.getString("email"));
                    tphone.setText(jsonobject.getString("telephone"));
                    tbirth.setText(jsonobject.getString("birthday"));
                    tinsDate.setText(jsonobject.getString("date_inscription"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
