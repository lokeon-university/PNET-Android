package es.manguca;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import es.manguca.Utils.LetterImageView;


public class ImportantDatesActivity extends AppCompatActivity {
    private ListView listView;
    private String[] sDates;
    private String[] sTitles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_important_dates);
        sDates = getResources().getStringArray(R.array.Date_Dates);
        sTitles = getResources().getStringArray(R.array.Title_Dates);

        listView = (ListView)findViewById(R.id.lvMainDates);
        setupListView();

        Toolbar bottom_toolbar = (Toolbar) findViewById(R.id.bottom_toolbarDates);
        setSupportActionBar(bottom_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        bottom_toolbar.setTitle("");
        bottom_toolbar.setSubtitle("");

        Toolbar top_toolbar = (Toolbar) findViewById(R.id.top_toolbar);
        setSupportActionBar(top_toolbar);
        getSupportActionBar().setTitle(R.string.importantdates);

        Button btn_home = (Button)findViewById(R.id.button_home);
        Button btn_schedule = (Button)findViewById(R.id.button_schedule);
        Button btn_assistant = (Button)findViewById(R.id.button_assistant);
        Button btn_location = (Button)findViewById(R.id.button_location);
        Button btn_date = (Button)findViewById(R.id.button_date);


        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ImportantDatesActivity.this, MainActivity.class));
            }
        });

        btn_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ImportantDatesActivity.this, ProgramActivity.class));
            }
        });

        btn_assistant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ImportantDatesActivity.this, AssistantActivity.class));
            }
        });

        btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ImportantDatesActivity.this, LocationActivity.class));
            }
        });

        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ImportantDatesActivity.this, ImportantDatesActivity.class));
            }
        });





    }

    private void setupListView(){

        ImportantDatesActivity.SimpleAdapter adapter = new ImportantDatesActivity.SimpleAdapter(this);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Date date= null;
                SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    date = myFormat.parse(sDates[position]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                Date today = new Date();
                long difference = 0;

                if (date.before(today)){
                    difference = ((today.getTime() -date.getTime())/(1000*60*60*24));
                    Toast.makeText(ImportantDatesActivity.this, "Han pasado " + difference + " días desde este evento", Toast.LENGTH_SHORT).show();

                }
                /*else if (date ==today){

                }*/
                else{
                    difference = ((date.getTime() -today.getTime())/(1000*60*60*24));

                    showNotification(getResources().getString(R.string.title_notif),getResources().getString(R.string.message_notif));
                    LinearLayout linearLayout = (LinearLayout) findViewById(R.id.important_dates);
                    Snackbar snackbar = Snackbar
                            .make(linearLayout, "Quedan " + difference + " días para este evento", Snackbar.LENGTH_LONG);

                    snackbar.show();
                }


            }
        });

    }

    private RemoteViews getCustomDesign(String title, String message) {
        RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(), R.layout.notification);
        remoteViews.setTextViewText(R.id.title, title);
        remoteViews.setTextViewText(R.id.message, message);
        remoteViews.setImageViewResource(R.id.icon, R.drawable.logo_gato);
        return remoteViews;
    }

    public void showNotification(String title,String message) {
        Intent intent = new Intent(this, LocationActivity.class);
        String channel_id = getResources().getString(R.string.channel_id);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),channel_id)
                .setSmallIcon(R.drawable.logo_gato)
                .setSound(uri)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000,1000,1000,1000,1000})
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            builder = builder.setContent(getCustomDesign(title,message));
        }
        else {
            builder = builder.setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(R.drawable.logo_gato);
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(channel_id,getResources().getString(R.string.channel_name),NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setSound(uri,null);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        notificationManager.notify(0,builder.build());
    }

    public class SimpleAdapter extends BaseAdapter {
        private LetterImageView ivLogo;
        private Context mContext;
        private LayoutInflater layoutInflater;
        private TextView title, date;


        public SimpleAdapter(Context context) {
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
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.activitiy_import_dates_single_item, null);
            }

            title = (TextView) convertView.findViewById(R.id.tvMainDates);
            date = (TextView) convertView.findViewById(R.id.tvDate);
            ivLogo = (LetterImageView) convertView.findViewById(R.id.ivLetterDates);


            title.setText(sTitles[position]);
            date.setText(sDates[position]);
            ivLogo.setOval(true);
            ivLogo.setLetter(sTitles[position].charAt(0));
            letterBackground(sTitles[position], ivLogo);

            return convertView;

        }

        void letterBackground(String manguca, LetterImageView ivLogo) {
            switch (manguca) {
                case "Bilbao":
                    ivLogo.setBackgroundColorLetter(0);
                    break;
                case "Valencia":
                    ivLogo.setBackgroundColorLetter(1);
                    break;
                case "Madrid":
                    ivLogo.setBackgroundColorLetter(2);
                    break;
                case "Cádiz":
                    ivLogo.setBackgroundColorLetter(3);
                    break;
                case "Sevilla":
                    ivLogo.setBackgroundColorLetter(4);
                    break;
                case "Galicia":
                    ivLogo.setBackgroundColorLetter(5);
                    break;

            }
        }

    }
}