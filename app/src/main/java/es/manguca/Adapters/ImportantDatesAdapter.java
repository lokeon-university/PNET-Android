package es.manguca.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import es.manguca.R;
import es.manguca.Utils.LetterImageView;

public class ImportantDatesAdapter extends BaseAdapter {
    private LetterImageView ivLogo;
    private Context mContext;
    private LayoutInflater layoutInflater;
    private TextView title, date;
    private String[] sDates;
    private String[] sTitles;

    public ImportantDatesAdapter(Context context, String[] sDates, String[] sTitles) {
        mContext = context;
        layoutInflater = LayoutInflater.from(context);
        this.sDates = sDates;
        this.sTitles = sTitles;

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