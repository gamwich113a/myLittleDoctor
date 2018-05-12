package client.mylittledoctor.formation.arlebois.com.mylittledoctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import client.mylittledoctor.formation.arlebois.com.mylittledoctor.atelier.AtelierActivity;
import client.mylittledoctor.formation.arlebois.com.mylittledoctor.technical.MyLittleDoctorVolleyError;
import client.mylittledoctor.formation.arlebois.com.mylittledoctor.technical.RequestSingleton;
import client.mylittledoctor.formation.com.mylittledoctor.entite.Atelier;

public class CentralActivity extends AppCompatActivity {


    Spinner atelierSpinner;
    //EditText resultat;
    TextView titre;
    TextView description;
    TextView lieu;
    List<Atelier> atelierList = new ArrayList<>();
    Map<Long, Atelier> atelierMap = new HashMap<>();
    Long atelierId = 0L;
    Long utilisateurId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_centrale);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        utilisateurId = intent.getLongExtra(MainActivity.UTILISATEUR_ID, -1);

        atelierSpinner = findViewById(R.id.atelierList);
        //resultat = findViewById(R.id.resultat);
        titre = findViewById(R.id.titreAtelier);
        description = findViewById(R.id.descriptionAtelier);
        lieu = findViewById(R.id.lieuAtelier);

        loadSpinnerData();

        atelierSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String atelierTitre = null;
                String atelier = atelierSpinner.getItemAtPosition(atelierSpinner.getSelectedItemPosition()).toString();
                for (Long key : atelierMap.keySet()) {
                    atelierTitre = atelierMap.get(key).getTitre();
                    if (atelierTitre.equals(atelier)) {
                        atelierId = key;
                        break;
                    }
                }
                //resultat.setText("Selected AtelierId " + atelierMap.get(atelierId).getId() + " Titre " + atelierMap.get(atelierId).getTitre());
                titre.setText(atelierMap.get(atelierId).getTitre());
                description.setText(atelierMap.get(atelierId).getDescription());
                lieu.setText(atelierMap.get(atelierId).getLieu());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });
    }

    private void loadSpinnerData() {
        StringRequest atelierRequest = new StringRequest(Request.Method.POST, MainActivity.url + "/atelier/findAllActif", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                atelierList = Arrays.asList(new Gson().fromJson(response, Atelier[].class));
                List<String> atelierTitre = new ArrayList<>();
                for (Atelier atelier : atelierList) {
                    atelierMap.put(atelier.getId(), atelier);
                    atelierTitre.add(atelier.getTitre());
                }
                atelierSpinner.setAdapter(new ArrayAdapter<>(CentralActivity.this, android.R.layout.simple_spinner_dropdown_item, atelierTitre));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyLittleDoctorVolleyError errorManager = new MyLittleDoctorVolleyError();
                errorManager.onErrorResponse(error);
                //resultat.setText("Error: Code: " + errorManager.getStatusCode() + "Msg : " + errorManager.getErrorMessage());
            }
        });
        RequestSingleton.getInstance(CentralActivity.this).addToRequestQueue(atelierRequest);
    }

    public void selectAtelierAction(View view) {

        StringRequest linkToAtelier = new StringRequest(Request.Method.POST, MainActivity.url + "/user/linkToAtelier", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Intent intent = new Intent(CentralActivity.this, AtelierActivity.class);
                intent.putExtra(MainActivity.UTILISATEUR_ID, utilisateurId);
                intent.putExtra(MainActivity.ATELIER_ID, atelierId);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyLittleDoctorVolleyError errorManager = new MyLittleDoctorVolleyError();
                errorManager.onErrorResponse(error);
                //resultat.setText("Error: Code: " + errorManager.getStatusCode() + "Msg : " + errorManager.getErrorMessage());
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("utilisateur_id", utilisateurId.toString());
                params.put("atelier_id", atelierId.toString());
                return params;
            }
        };
        RequestSingleton.getInstance(CentralActivity.this).addToRequestQueue(linkToAtelier);
    }
}
