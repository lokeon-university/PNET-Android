package es.manguca;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
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
        setupListView();

        Toolbar bottom_toolbar = (Toolbar) findViewById(R.id.bottom_toolbar);
        setSupportActionBar(bottom_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        bottom_toolbar.setTitle("");
        bottom_toolbar.setSubtitle("");

        Toolbar top_toolbar = (Toolbar) findViewById(R.id.top_toolbar);
        setSupportActionBar(top_toolbar);
        getSupportActionBar().setTitle(R.string.program);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //handle presses on the action bar items
        switch (item.getItemId()) {

            case R.id.menu_program:
                startActivity(new Intent(this, ProgramActivity.class));
                return true;

            case R.id.menu_assitant:
                startActivity(new Intent(this, AssistantActivity.class));
                return true;

            case R.id.menu_home:
                startActivity(new Intent(this, MainActivity.class));
                return true;

            case R.id.menu_location:
                startActivity(new Intent(this, LocationActivity.class));
                return true;

            case R.id.menu_importantdates:
                startActivity(new Intent(this, ImportantDatesActivity.class));
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    private void setupListView(){


        SimpleAdapter adapter = new SimpleAdapter(this);

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
        private String[] sRoles = getResources().getStringArray(R.array.roles);
        private String[] sDescription = getResources().getStringArray(R.array.Description);
        private String[] sTitles = getResources().getStringArray(R.array.Main);

        public SimpleAdapter(Context context){
            mContext = context;
            layoutInflater = LayoutInflater.from(context);
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
            role = (TextView)convertView.findViewById(R.id.tvRole);
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
                case "Seminario":
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





