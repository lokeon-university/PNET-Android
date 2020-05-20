package es.manguca.assistant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import es.manguca.AssistantActivity;
import es.manguca.ImportantDatesActivity;
import es.manguca.LocationActivity;
import es.manguca.MainActivity;
import es.manguca.ProgramActivity;
import es.manguca.R;

public class AddAssistantActivity extends AppCompatActivity {

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

        EditText txtNombre = (EditText) findViewById(R.id.TxtNombre);
        EditText txtApellidos = (EditText) findViewById(R.id.TxtApellido);
        EditText txtEmail = (EditText) findViewById(R.id.TxtEmail);
        EditText txtDni = (EditText) findViewById(R.id.TxtEmail);
        EditText txtTlf = (EditText) findViewById(R.id.TxtTlf);
        Button btnAceptar = (Button) findViewById(R.id.BtnAceptar);

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

    }
}
