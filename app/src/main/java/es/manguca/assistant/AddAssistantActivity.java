package es.manguca.assistant;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import org.json.JSONObject;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import es.manguca.AssistantActivity;
import es.manguca.ImportantDatesActivity;
import es.manguca.LocationActivity;
import es.manguca.MainActivity;
import es.manguca.ProgramActivity;
import es.manguca.R;

public class AddAssistantActivity extends AppCompatActivity {
    private EditText txtNombre;
    private EditText txtApellidos;
    private EditText txtEmail;
    private EditText txtDni;
    private EditText txtTlf;
    private EditText txtBirth;
    private EditText txtIns;
    private Button btn_aceptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assistant);

        Toolbar bottom_toolbar = (Toolbar) findViewById(R.id.bottom_toolbar);
        setSupportActionBar(bottom_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        bottom_toolbar.setTitle("");
        bottom_toolbar.setSubtitle("");

        Toolbar top_toolbar = (Toolbar) findViewById(R.id.top_toolbar);
        setSupportActionBar(top_toolbar);
        getSupportActionBar().setTitle(R.string.add_assistant);
        top_toolbar.setNavigationIcon(R.drawable.ic_arrow_back);

        top_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddAssistantActivity.this, AssistantActivity.class));
            }
        });

        txtNombre = (EditText) findViewById(R.id.TxtNombre);
        txtApellidos = (EditText) findViewById(R.id.TxtApellido);
        txtEmail = (EditText) findViewById(R.id.TxtEmail);
        txtDni = (EditText) findViewById(R.id.TxtDni);
        txtTlf = (EditText) findViewById(R.id.TxtTlf);
        txtBirth = (EditText) findViewById(R.id.DateNac);
        txtIns = (EditText) findViewById(R.id.fechaIns);
        btn_aceptar = (Button) findViewById(R.id.BtnAceptar);

        Button btn_home = (Button)findViewById(R.id.button_home);
        Button btn_schedule = (Button)findViewById(R.id.button_schedule);
        Button btn_assistant = (Button)findViewById(R.id.button_assistant);
        Button btn_location = (Button)findViewById(R.id.button_location);
        Button btn_date = (Button)findViewById(R.id.button_date);


        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddAssistantActivity.this, MainActivity.class));
            }
        });

        btn_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddAssistantActivity.this, ProgramActivity.class));
            }
        });

        btn_assistant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddAssistantActivity.this, AssistantActivity.class));
            }
        });

        btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddAssistantActivity.this, LocationActivity.class));
            }
        });

        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddAssistantActivity.this, ImportantDatesActivity.class));
            }
        });

       btn_aceptar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               new PostAssistant().execute();
           }
       });

        final Calendar calendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date_birth = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String date_format = "dd/MM/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(date_format, new Locale("es", "ES"));
                txtBirth.setText(sdf.format(calendar.getTime()));
            }
        };

        final DatePickerDialog.OnDateSetListener date_ins = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String date_format = "dd/MM/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(date_format, new Locale("es", "ES"));
                txtIns.setText(sdf.format(calendar.getTime()));
            }
        };

        txtBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddAssistantActivity.this, date_birth, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        txtIns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddAssistantActivity.this, date_ins, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }


    class PostAssistant extends AsyncTask<Void,Void,String> {

        private String name;
        private String surname;
        private String email;
        private String telephone;
        private String dni;
        private String birthday;
        private String date_inscription;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            name = txtNombre.getText().toString();
            surname = txtApellidos.getText().toString();
            email =  txtEmail.getText().toString();
            telephone = txtTlf.getText().toString();
            dni =  txtDni.getText().toString();
            birthday =  txtBirth.getText().toString();
            date_inscription =  txtIns.getText().toString();
        }

        @Override
        protected String doInBackground(Void... strings) {
            String text = null;
            BufferedWriter bufferedWriter = null;
            HttpURLConnection urlConnection = null;

            try {
                JSONObject dataToSend = new JSONObject();

                dataToSend.put("name", name);
                dataToSend.put("surname", surname);
                dataToSend.put("email", email);
                dataToSend.put("telephone", telephone);
                dataToSend.put("dni",dni);
                dataToSend.put("birthday",birthday);
                dataToSend.put("date_insription", date_inscription);

                URL url = new URL(getResources().getString(R.string.ip_node));
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(10000);
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.connect();

                OutputStream outputStream = urlConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                bufferedWriter.write(dataToSend.toString());
                bufferedWriter.flush();

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
            Toast.makeText(AddAssistantActivity.this, "El asistente ha sido añadido", Toast.LENGTH_SHORT).show();
            txtNombre.setText("");
            txtApellidos.setText("");
            txtEmail.setText("");
            txtTlf.setText("");
            txtDni.setText("");
            txtBirth.setText("");
            txtIns.setText("");
        }
    }

}
