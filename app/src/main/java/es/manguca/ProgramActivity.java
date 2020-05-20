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


        WeekAdapter adapter = new WeekAdapter(this, R.layout.activity_program_single_item);

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

    public class WeekAdapter extends ArrayAdapter {

        private int resource;
        private LayoutInflater layoutInflater;

        public WeekAdapter(Context context, int resource) {
            super(context, resource);
            this.resource = resource;
            layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            String[] roles = getResources().getStringArray(R.array.roles);
            String[] description = getResources().getStringArray(R.array.Description);
            String[] titles = getResources().getStringArray(R.array.Main);

            ViewHolder holder;
            if(convertView == null){
                holder = new ViewHolder();
                convertView = layoutInflater.inflate(resource, null);
                holder.ivLogo = (LetterImageView)convertView.findViewById(R.id.ivLetter);
                holder.tvMain = (TextView)convertView.findViewById(R.id.tvMain);
                holder.tvDescription = (TextView)convertView.findViewById(R.id.tvDescription);
                holder.tvRole = (TextView)convertView.findViewById(R.id.tvRole);

                convertView.setTag(holder);
            }else{
                holder = (ViewHolder)convertView.getTag();
            }

            holder.tvDescription.setText(description[position]);
            holder.tvMain.setText(titles[position]);
            holder.ivLogo.setOval(true);
            holder.ivLogo.setLetter(roles[position].charAt(0));
            holder.tvRole.setText(roles[position]);
            roleValue(roles[position], holder);


            return convertView;
        }

        class ViewHolder{
            private LetterImageView ivLogo;
            private TextView tvMain;
            private TextView tvRole;
            private TextView tvDescription;

        }

        void roleValue(String role, ViewHolder holder){
            switch(role){
                case "Taller":
                    holder.ivLogo.setBackgroundColorLetter(0);
                    break;
                case "Seminario":
                    holder.ivLogo.setBackgroundColorLetter(1);
                    break;
                case "Concierto":
                    holder.ivLogo.setBackgroundColorLetter(2);
                    break;
                case "Actividad":
                    holder.ivLogo.setBackgroundColorLetter(3);
                    break;
                case "Torneo":
                    holder.ivLogo.setBackgroundColorLetter(4);
                    break;
                case "Concurso":
                    holder.ivLogo.setBackgroundColorLetter(5);
                    break;

            }
        }
    }


    }


