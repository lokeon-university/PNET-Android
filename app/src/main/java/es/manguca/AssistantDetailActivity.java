package es.manguca;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import es.manguca.Utils.LetterImageView;

public class AssistantDetailActivity extends AppCompatActivity {
    private TextView tname, tlastname, tdni, temail, tphone, tbirth, tinsDate;
    private LetterImageView ivLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assistant_detail);

        Bundle bundle = this.getIntent().getExtras();

        tname = (TextView) findViewById(R.id.NameAssistant);
        tlastname = (TextView) findViewById(R.id.LastNameAssistant);
        tdni = (TextView) findViewById(R.id.DniAssistant);
        temail = (TextView) findViewById(R.id.EmailAssistant);
        tphone= (TextView) findViewById(R.id.PhoneAssistant);
        tbirth= (TextView) findViewById(R.id.BirthAssistant);
        tinsDate = (TextView) findViewById(R.id.InsDateAssistant);
        ivLogo = (LetterImageView)findViewById(R.id.iconAssistants);

        tname.setText(bundle.getString("name"));
        tlastname.setText(bundle.getString("lastname"));
        tdni.setText(bundle.getString("DNI"));
        temail.setText(bundle.getString("email"));
        tphone.setText(bundle.getString("phone"));
        tbirth.setText(bundle.getString("birth"));
        tinsDate.setText(bundle.getString("insDate"));
    }
}
