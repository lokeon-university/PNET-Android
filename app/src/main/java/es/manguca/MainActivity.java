package es.manguca;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import es.manguca.Adapters.ActivitiesAdapter;
import es.manguca.Adapters.ArtistAdapter;


public class MainActivity extends AppCompatActivity {
    private ListView listViewArtist, listViewActivities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewArtist = (ListView) findViewById(R.id.list_artist);
        setupListViewArtist();
        listViewArtist.setBackgroundColor(Color.WHITE);
        setListViewHeightBasedOnChildren(listViewArtist);


        listViewActivities = (ListView) findViewById(R.id.list_contest);
        setupListViewActivities();
        listViewActivities.setBackgroundColor(Color.WHITE);
        setListViewHeightBasedOnChildren(listViewActivities);

        Toolbar bottom_toolbar = (Toolbar) findViewById(R.id.bottom_toolbar);
        setSupportActionBar(bottom_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        bottom_toolbar.setTitle("");
        bottom_toolbar.setSubtitle("");

        Toolbar top_toolbar = (Toolbar) findViewById(R.id.top_toolbar);
        setSupportActionBar(top_toolbar);
        getSupportActionBar().setTitle(R.string.home);

        Button btn_home = (Button) findViewById(R.id.button_home);
        Button btn_schedule = (Button) findViewById(R.id.button_schedule);
        Button btn_assistant = (Button) findViewById(R.id.button_assistant);
        Button btn_location = (Button) findViewById(R.id.button_location);
        Button btn_date = (Button) findViewById(R.id.button_date);

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
            }
        });

        btn_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProgramActivity.class));
            }
        });

        btn_assistant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AssistantActivity.class));
            }
        });

        btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LocationActivity.class));
            }
        });

        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ImportantDatesActivity.class));
            }
        });
    }

    public static void setListViewHeightBasedOnChildren(ListView listViewArtist) {

        ListAdapter listAdapter = listViewArtist.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            View listItem = listAdapter.getView(i, null, listViewArtist);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listViewArtist.getLayoutParams();
        params.height = totalHeight
                + (listViewArtist.getDividerHeight() * (listAdapter.getCount() - 1));
        listViewArtist.setLayoutParams(params);
    }

    private void setupListViewArtist() {
        String[] titleArray = getResources().getStringArray(R.array.title_main);
        String[] roleArray = getResources().getStringArray(R.array.role_main);
        ArtistAdapter artistAdapter = new ArtistAdapter(this, titleArray, roleArray);

        listViewArtist.setAdapter(artistAdapter);
    }

    private void setupListViewActivities() {
        String[] titleArray = getResources().getStringArray(R.array.title_main_contest);
        String[] roleArray = getResources().getStringArray(R.array.role_main_contest);
        ActivitiesAdapter activitiesAdapter = new ActivitiesAdapter(this, titleArray, roleArray);

        listViewActivities.setAdapter(activitiesAdapter);
    }


}


