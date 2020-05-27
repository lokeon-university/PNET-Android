package es.manguca;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import es.manguca.Adapters.ProgramAdapter;
import es.manguca.Utils.LetterImageView;
import es.manguca.Utils.PDFRenderActivity;
import es.manguca.Utils.PermissionUtils;

public class ProgramActivity extends AppCompatActivity {
    private ListView listView;
    private ParcelFileDescriptor mFileDescriptor;
    private PdfRenderer mPdfRenderer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program);


        listView = (ListView)findViewById(R.id.lvMain);
        setupListView(1);

        Toolbar bottom_toolbar = (Toolbar) findViewById(R.id.bottom_toolbar);
        setSupportActionBar(bottom_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        bottom_toolbar.setTitle("");
        bottom_toolbar.setSubtitle("");

        Toolbar top_toolbar = (Toolbar) findViewById(R.id.top_toolbar);
        setSupportActionBar(top_toolbar);
        getSupportActionBar().setTitle(R.string.program);

        // Meter boton para no hacer nuevo toolbar
        Button btn_pdf = new Button(this);
        btn_pdf.setBackgroundResource(R.drawable.ic_pdf);
        Toolbar.LayoutParams lyt_prm = new Toolbar.LayoutParams(90, 90, Gravity.END);
        lyt_prm.setMarginEnd(20);
        btn_pdf.setLayoutParams(lyt_prm);
        top_toolbar.addView(btn_pdf);

        btn_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProgramActivity.this, PDFRenderActivity.class));
            }
        });



        Button btn_home = (Button)findViewById(R.id.button_home);
        Button btn_schedule = (Button)findViewById(R.id.button_schedule);
        Button btn_assistant = (Button)findViewById(R.id.button_assistant);
        Button btn_location = (Button)findViewById(R.id.button_location);
        Button btn_date = (Button)findViewById(R.id.button_date);

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProgramActivity.this, MainActivity.class));
            }
        });

        btn_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProgramActivity.this, ProgramActivity.class));
            }
        });

        btn_assistant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProgramActivity.this, AssistantActivity.class));
            }
        });

        btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProgramActivity.this, LocationActivity.class));
            }
        });

        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProgramActivity.this, ImportantDatesActivity.class));
            }
        });

        final Button day1 = (Button)findViewById(R.id.day_1);
        final Button day2 = (Button)findViewById(R.id.day_2);
        final Button day3 = (Button)findViewById(R.id.day_3);

        day1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                day1.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.gray, null));
                day1.setTextColor(Color.WHITE);
                day2.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.yellow, null));
                day2.setTextColor(Color.BLACK);
                day3.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.yellow, null));
                day3.setTextColor(Color.BLACK);
                setupListView(1);

            }
        });

        day2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                day1.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.yellow, null));
                day1.setTextColor(Color.BLACK);
                day2.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.gray, null));
                day2.setTextColor(Color.WHITE);
                day3.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.yellow, null));
                day3.setTextColor(Color.BLACK);
                setupListView(2);

            }
        });

        day3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                day1.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.yellow, null));
                day1.setTextColor(Color.BLACK);
                day2.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.yellow, null));
                day2.setTextColor(Color.BLACK);
                day3.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.gray, null));
                day3.setTextColor(Color.WHITE);
                setupListView(3);

            }
        });

    }

    private void setupListView(int day){

        ProgramAdapter adapter = new ProgramAdapter(this, day);

        listView.setAdapter(adapter);

    }



}





