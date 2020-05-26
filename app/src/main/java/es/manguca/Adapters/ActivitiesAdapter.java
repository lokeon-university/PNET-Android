package es.manguca.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import es.manguca.R;

public class ActivitiesAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater layoutInflater;
    private TextView title, role;
    private String[] titleArray;
    private String[] roleArray;
    private ImageView imageView;

    public ActivitiesAdapter(Context context, String[] title, String[] role){
        mContext = context;
        titleArray = title;
        roleArray = role;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return titleArray.length;
    }

    @Override
    public Object getItem(int position) {
        return titleArray[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.activity_main_single_item, null);
        }

        title = (TextView)convertView.findViewById(R.id.tvTitle);
        role = (TextView)convertView.findViewById(R.id.tvRole_main);
        imageView = (ImageView)convertView.findViewById(R.id.ivImage);

        title.setText(titleArray[position]);
        role.setText(roleArray[position]);

        switch(position){
            case 0:
                imageView.setImageResource(R.drawable.act1);
                break;
            case 1:
                imageView.setImageResource(R.drawable.act2);
                break;
        }

        return convertView;

    }
}
