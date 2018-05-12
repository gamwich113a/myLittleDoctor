package client.mylittledoctor.formation.arlebois.com.mylittledoctor.rapport;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import client.mylittledoctor.formation.arlebois.com.mylittledoctor.MainActivity;
import client.mylittledoctor.formation.arlebois.com.mylittledoctor.R;

public class RapportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rapport);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.RAPPORT_MESSAGE);

    }

    /**
     * Called when the user taps the Send button
     */
    public void toActivity(Class classActivity, Button editText) {
        Intent intent = new Intent(this, classActivity);
        String message = editText.getText().toString();
        intent.putExtra(MainActivity.RAPPORT_MESSAGE, message);
        startActivity(intent);
    }

    /**
     * Called when the user taps the Send button
     */
    public void toRapportCreateActivity(View view) {
        Button editText = findViewById(R.id.createRapport);
        toActivity(RapportCreateActivity.class, editText);
    }

    /**
     * Called when the user taps the Send button
     */
    public void toRapportViewActivity(View view) {
        Button editText = findViewById(R.id.updateRapport);
        toActivity(RapportViewActivity.class, editText);
    }

    /**
     * Called when the user taps the Send button
     */
    public void toRapportUpdateActivity(View view) {
        Button editText = findViewById(R.id.viewRapport);
        toActivity(RapportUpdateActivity.class, editText);
    }

}
