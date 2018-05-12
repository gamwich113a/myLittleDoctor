package client.mylittledoctor.formation.arlebois.com.mylittledoctor.rapport;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import client.mylittledoctor.formation.arlebois.com.mylittledoctor.MainActivity;
import client.mylittledoctor.formation.arlebois.com.mylittledoctor.R;
import client.mylittledoctor.formation.arlebois.com.mylittledoctor.technical.HttpUtils;
import cz.msebera.android.httpclient.Header;

public class RapportViewActivity extends AppCompatActivity {

    Button viewRapport;
    EditText titre;
    EditText rapport;
    EditText utilisateurId;
    EditText atelierId;
    EditText resultat;
    private View.OnClickListener rapportListener = new View.OnClickListener() {
        public void onClick(View v) {
            RequestParams rp = new RequestParams();

            rp.add("titre", titre.getText().toString());
            rp.add("rapport", rapport.getText().toString());
            rp.add("utilisateur_id", utilisateurId.getText().toString());
            rp.add("atelier_id", atelierId.getText().toString());

            HttpUtils.get("rapport/create", rp, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    resultat.setText("OK1 = " + response);
                    try {
                        titre.setText(response.getString("titre"));
                        rapport.setText(response.getString("rapport"));
                        utilisateurId.setText(response.getString("utilisateur_id"));
                        atelierId.setText(response.getString("atelier_id"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    resultat.setText("OK2 = " + response);
                }


                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    resultat.setText("KO1 = " + throwable);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwf, JSONObject json) {
                    resultat.setText("KO2 = " + json);
                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rapport_view);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.RAPPORT_MESSAGE);

        titre = findViewById(R.id.titre);
        rapport = findViewById(R.id.rapport);
        utilisateurId = findViewById(R.id.utilisateurId);
        atelierId = findViewById(R.id.atelierId);
        resultat = findViewById(R.id.resultat);

        viewRapport = findViewById(R.id.viewRapport);
        viewRapport.setOnClickListener(rapportListener);
    }
}
