package es.manguca.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import es.manguca.R;
import es.manguca.Utils.LetterImageView;

public class ProgramAdapter extends BaseAdapter {
    private LetterImageView ivLogo;
    private Context mContext;
    private LayoutInflater layoutInflater;
    private TextView title, description, role;
    private String[] sRoles;
    private String[] sDescription;
    private String[] sTitles;

    public ProgramAdapter(Context context, int dia) {
        mContext = context;
        layoutInflater = LayoutInflater.from(context);
        switch (dia) {
            case 1:
                sRoles = context.getResources().getStringArray(R.array.roles);
                sDescription = context.getResources().getStringArray(R.array.Description);
                sTitles = context.getResources().getStringArray(R.array.Main);
                break;
            case 2:
                sRoles = context.getResources().getStringArray(R.array.roles_day2);
                sDescription = context.getResources().getStringArray(R.array.Description_day2);
                sTitles = context.getResources().getStringArray(R.array.Main_day2);
                break;
            case 3:
                sRoles = context.getResources().getStringArray(R.array.roles_day3);
                sDescription = context.getResources().getStringArray(R.array.Description_day3);
                sTitles = context.getResources().getStringArray(R.array.Main_day3);
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
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.activity_program_single_item, null);
        }

        title = (TextView) convertView.findViewById(R.id.tvMain);
        description = (TextView) convertView.findViewById(R.id.tvDescription);
        role = (TextView) convertView.findViewById(R.id.tvRole_main);
        ivLogo = (LetterImageView) convertView.findViewById(R.id.ivLetter);


        title.setText(sTitles[position]);
        description.setText(sDescription[position]);
        role.setText(sRoles[position]);
        ivLogo.setOval(true);
        ivLogo.setLetter(sRoles[position].charAt(0));
        roleValue(sRoles[position], ivLogo);

        return convertView;

    }

    void roleValue(String role, LetterImageView ivLogo) {
        switch (role) {
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