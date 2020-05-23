package es.manguca.assistant;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

public class EditAssistantActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_edit_assistant);

        final Bundle bundle = this.getIntent().getExtras();

        Toolbar bottom_toolbar = (Toolbar) findViewById(R.id.bottom_toolbar);
        setSupportActionBar(bottom_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        bottom_toolbar.setTitle("");
        bottom_toolbar.setSubtitle("");

        Toolbar top_toolbar = (Toolbar) findViewById(R.id.top_toolbar);
        setSupportActionBar(top_toolbar);
        getSupportActionBar().setTitle(R.string.edit_assitant);
        top_toolbar.setNavigationIcon(R.drawable.ic_arrow_back);

        top_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditAssistantActivity.this, AssistantActivity.class));
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

        txtNombre.setText(bundle.getString("name"));
        txtApellidos.setText(bundle.getString("lastname"));
        txtDni.setText(bundle.getString("DNI"));
        txtEmail.setText(bundle.getString("email"));
        txtTlf.setText(bundle.getString("phone"));
        txtBirth.setText(bundle.getString("birth"));
        txtIns.setText(bundle.getString("insDate"));

        txtNombre.addTextChangedListener(textWatcher);
        txtApellidos.addTextChangedListener(textWatcher);
        txtEmail.addTextChangedListener(textWatcher);
        txtDni.addTextChangedListener(textWatcher);
        txtTlf.addTextChangedListener(textWatcher);
        txtBirth.addTextChangedListener(textWatcher);
        txtIns.addTextChangedListener(textWatcher);

        Button btn_home = (Button)findViewById(R.id.button_home);
        Button btn_schedule = (Button)findViewById(R.id.button_schedule);
        Button btn_assistant = (Button)findViewById(R.id.button_assistant);
        Button btn_location = (Button)findViewById(R.id.button_location);
        Button btn_date = (Button)findViewById(R.id.button_date);


        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditAssistantActivity.this, MainActivity.class));
            }
        });

        btn_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditAssistantActivity.this, ProgramActivity.class));
            }
        });

        btn_assistant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditAssistantActivity.this, AssistantActivity.class));
            }
        });

        btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditAssistantActivity.this, LocationActivity.class));
            }
        });

        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditAssistantActivity.this, ImportantDatesActivity.class));
            }
        });

       btn_aceptar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               new PutAssistant().execute(bundle.getString("id"));

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
                new DatePickerDialog(EditAssistantActivity.this, date_birth, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        txtIns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditAssistantActivity.this, date_ins, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    // Para mirar que los inputs esten filled
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            String name = txtNombre.getText().toString().trim();
            String surname = txtApellidos.getText().toString().trim();
            String email = txtEmail.getText().toString().trim();
            String telephone = txtTlf.getText().toString().trim();
            String dni = txtDni.getText().toString().trim();
            String birthday = txtBirth.getText().toString().trim();
            String date_inscription = txtIns.getText().toString().trim();

            btn_aceptar.setEnabled(!name.isEmpty() && !surname.isEmpty() && !email.isEmpty() &&
                    !telephone.isEmpty() && !dni.isEmpty() && !birthday.isEmpty() &&
                    !date_inscription.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    class PutAssistant extends AsyncTask<String,Void,String> {

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
        protected String doInBackground(String... strings) {
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

                URL url = new URL(getResources().getString(R.string.ip_node) + "/" + strings[0]);
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
            Toast.makeText(EditAssistantActivity.this, "El asistente ha sido editado", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(EditAssistantActivity.this, AssistantActivity.class));
        }
    }

}
