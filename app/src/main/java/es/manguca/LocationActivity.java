package es.manguca;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        Toolbar bottom_toolbar = (Toolbar) findViewById(R.id.bottom_toolbar);
        setSupportActionBar(bottom_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        bottom_toolbar.setTitle("");
        bottom_toolbar.setSubtitle("");

        Toolbar top_toolbar = (Toolbar) findViewById(R.id.top_toolbar);
        setSupportActionBar(top_toolbar);
        getSupportActionBar().setTitle(R.string.location);

        TextView title_map = (TextView) findViewById(R.id.title_map);
        title_map.setText(R.string.title_map);

        TextView event = (TextView) findViewById(R.id.title_event);
        event.setText(R.string.title_event);

        TextView inf_event = (TextView) findViewById(R.id.inf_event);
        inf_event.setText(R.string.inf_event);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng manguca = new LatLng(36.5285651, -6.2110946);
        mMap.addMarker(new MarkerOptions().position(manguca).title("Manguca festival"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(manguca,18));
    }

}
