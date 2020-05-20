package es.manguca;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import es.manguca.Utils.LetterImageView;

public class ProgramActivity extends AppCompatActivity {
    private ListView listView;
    public static SharedPreferences sharedPreferences;
    public static String SEL_DAY;
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

        ////

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


        SimpleAdapter adapter = new SimpleAdapter(this, day);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0: {
                        //startActivity(new Intent(WeekActivity.this, DayDetail.class));
                        sharedPreferences.edit().putString(SEL_DAY, "Monday").apply();
                        break;
                    }
                    case 1: {
                        //startActivity(new Intent(WeekActivity.this, DayDetail.class));
                        sharedPreferences.edit().putString(SEL_DAY, "Tuesday").apply();
                        break;
                    }
                    case 2: {
                        //startActivity(new Intent(WeekActivity.this, DayDetail.class));
                        sharedPreferences.edit().putString(SEL_DAY, "Wednesday").apply();
                        break;
                    }
                    case 3: {
                        //startActivity(new Intent(WeekActivity.this, DayDetail.class));
                        sharedPreferences.edit().putString(SEL_DAY, "Thursday").apply();
                        break;
                    }
                    case 4: {
                        //startActivity(new Intent(WeekActivity.this, DayDetail.class));
                        sharedPreferences.edit().putString(SEL_DAY, "Friday").apply();
                        break;
                    }
                    case 5: {
                        //startActivity(new Intent(WeekActivity.this, DayDetail.class));
                        sharedPreferences.edit().putString(SEL_DAY, "Saturday").apply();
                        break;
                    }
                    default:break;
                }
            }
        });

    }

    public class SimpleAdapter extends BaseAdapter{
        private LetterImageView ivLogo;
        private Context mContext;
        private LayoutInflater layoutInflater;
        private TextView title, description, role;
        private String[] sRoles;
        private String[] sDescription;
        private String[] sTitles;

        public SimpleAdapter(Context context, int dia){
            mContext = context;
            layoutInflater = LayoutInflater.from(context);
            switch(dia){
                case 1:
                    sRoles = getResources().getStringArray(R.array.roles);
                    sDescription = getResources().getStringArray(R.array.Description);
                    sTitles = getResources().getStringArray(R.array.Main);
                    break;
                case 2:
                    sRoles = getResources().getStringArray(R.array.roles_day2);
                    sDescription = getResources().getStringArray(R.array.Description_day2);
                    sTitles = getResources().getStringArray(R.array.Main_day2);
                    break;
                case 3:
                    sRoles = getResources().getStringArray(R.array.roles_day3);
                    sDescription = getResources().getStringArray(R.array.Description_day3);
                    sTitles = getResources().getStringArray(R.array.Main_day3);
                    break;

            }
        }


        @Override
        public int getCount() {
            return sTitles.length;
        }

        @Override
        public Object getItem(int position) {
            return sTitles[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = layoutInflater.inflate(R.layout.activity_program_single_item, null);
            }

            title = (TextView)convertView.findViewById(R.id.tvMain);
            description = (TextView)convertView.findViewById(R.id.tvDescription);
            role = (TextView)convertView.findViewById(R.id.tvRole_main);
            ivLogo = (LetterImageView)convertView.findViewById(R.id.ivLetter);


            title.setText(sTitles[position]);
            description.setText(sDescription[position]);
            role.setText(sRoles[position]);
            ivLogo.setOval(true);
            ivLogo.setLetter(sRoles[position].charAt(0));
            roleValue(sRoles[position], ivLogo);

            return convertView;

        }

        void roleValue(String role, LetterImageView ivLogo){
            switch(role){
                case "Taller":
                    ivLogo.setBackgroundColorLetter(0);
                    break;
                case "Descanso":
                    ivLogo.setBackgroundColorLetter(1);
                    break;
                case "Concierto":
                    ivLogo.setBackgroundColorLetter(2);
                    break;
                case "Actividad":
                    ivLogo.setBackgroundColorLetter(3);
                    break;
                case "Torneo":
                    ivLogo.setBackgroundColorLetter(4);
                    break;
                case "Concurso":
                    ivLogo.setBackgroundColorLetter(5);
                    break;

            }
    }


        }
    }





